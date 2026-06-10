# Figma sync

Bidirectional sync between `design-system.md` (the code-side source of truth) and a Figma file's Variables and Styles. `token-export.md` already handles one-way emit (markdown → JSON → Figma import); this file extends that into a round-trip: read deltas out of Figma back into markdown, and push markdown changes into Figma Variables via the REST API. In scope: color, type family/scale, spacing, radius. Out of scope: component instances, auto-layout, illustration SVGs, screen layouts — those stay in Figma or in code, not in token sync.

## The two modes

Figma sync runs in one of two directions per invocation. Never both at once, never silently.

### Read (Figma → design-system.md)

Designer iterates in Figma — tweaks the accent, adjusts a radius, re-tunes the type scale. Engineer pulls the changes into `design-system.md` so code rebuilds from the new tokens.

Trigger phrases: "sync from Figma", "pull from Figma", "what did the designer change", "update tokens from Figma".

Use when: design leads the loop. Figma is the working surface, markdown is the record.

### Write (design-system.md → Figma)

Markdown is canonical. Engineers edit YAML, run the skill, Figma Variables update to match. Designer opens Figma and sees the new values.

Trigger phrases: "push to Figma", "sync to Figma", "update Figma Variables", "write tokens to Figma".

Use when: engineering leads the loop. Code is canonical, Figma is a view onto it.

Pick one mode per run. If both sides have changed since last sync, fall through to conflict resolution (below).

## Auth and setup

Figma's Variables REST API requires a Personal Access Token. The user generates one themselves — the skill never handles OAuth flows here.

**Token creation.** Figma → Settings → Account → Personal access tokens → Generate new token. Scope: `file_variables:read`, `file_variables:write` (both, for bidirectional sync). `files:read` for style reads.

**Storage.** Token lives in the shell environment as `FIGMA_TOKEN`. Never commit it. The skill reads `process.env.FIGMA_TOKEN` at call time.

Drop a `.env.example` next to the project (no value, just the key name) so the pattern is documented:

```
# .env.example
FIGMA_TOKEN=
```

Add `.env` (the real one) to `.gitignore`. If the user hasn't set the env var, the skill prompts once and writes to `.env`; subsequent runs read from the environment.

**File key.** Every Figma file URL contains the key. Extract from either URL shape:

```
https://www.figma.com/file/abc123XYZ/Project-Name        → key = abc123XYZ
https://www.figma.com/design/abc123XYZ/Project-Name      → key = abc123XYZ
```

Regex: `/(?:file|design)/([a-zA-Z0-9]+)/`. Ask the user for the URL on first run; cache the extracted key in `design-system.md`'s `meta:` block as `figma_file_key`.

**Enterprise gating — the honest caveat.** The Variables REST API has asymmetric tier access:

- **Reads** (`GET /v1/files/:key/variables/local`) — available on Enterprise and some Organization plans, not Professional.
- **Writes** (`POST /v1/files/:key/variables`) — Enterprise only as of 2024.

If write mode runs on a non-Enterprise file, fall back to the **Tokens Studio plugin** path: emit `exports/figma-tokens.json` using the existing template and tell the user to import it manually via Figma → Plugins → Tokens Studio → Import. Don't silently fail or pretend the push worked.

Detect tier by calling `GET /v1/files/:key/variables/local` first. A `403` with `"reason": "variables_not_enabled"` means non-Enterprise — fall back to plugin export. A `200` means Enterprise and the full API is available.

## Token mapping

This table is the contract for both directions. `design-system.md` key on the left, Figma representation on the right.

| design-system.md key | Figma object | Collection | Mode |
|---|---|---|---|
| `color.bg_primary` | Color Variable `color/bg/primary` | Color | Light |
| `color.bg_secondary` | Color Variable `color/bg/secondary` | Color | Light |
| `color.bg_tertiary` | Color Variable `color/bg/tertiary` | Color | Light |
| `color.fg_primary` | Color Variable `color/fg/primary` | Color | Light |
| `color.fg_secondary` | Color Variable `color/fg/secondary` | Color | Light |
| `color.fg_muted` | Color Variable `color/fg/muted` | Color | Light |
| `color.accent` | Color Variable `color/accent/default` | Color | Light |
| `color.accent_hover` | Color Variable `color/accent/hover` | Color | Light |
| `color.border` | Color Variable `color/border/default` | Color | Light |
| `color.border_strong` | Color Variable `color/border/strong` | Color | Light |
| `color.success` | Color Variable `color/semantic/success` | Color | Light |
| `color.warning` | Color Variable `color/semantic/warning` | Color | Light |
| `color.danger` | Color Variable `color/semantic/danger` | Color | Light |
| `color.dark.bg_primary` | Color Variable `color/bg/primary` | Color | Dark |
| `color.dark.bg_secondary` | Color Variable `color/bg/secondary` | Color | Dark |
| `color.dark.fg_primary` | Color Variable `color/fg/primary` | Color | Dark |
| `color.dark.accent` | Color Variable `color/accent/default` | Color | Dark |
| `color.dark.*` (remaining) | matching `color/*` variable | Color | Dark |
| `type.display` | Text Style family + String Variable `type/family/display` | Type Scale | — |
| `type.body` | Text Style family + String Variable `type/family/body` | Type Scale | — |
| `type.mono` | Text Style family + String Variable `type/family/mono` | Type Scale | — |
| `type.scale.xs` … `type.scale.display` | Number Variable `type/scale/{key}` | Type Scale | — |
| `spacing.base_unit` | Number Variable `spacing/base` | Spacing | — |
| `spacing.radius.sm` … `radius.full` | Number Variable `radius/{key}` | Spacing | — |
| `motion.duration_*` | — (not representable in Variables today) | — | — |
| `motion.easing`, `motion.easing_dramatic` | — (not representable) | — | — |

Color values in Figma are `{r, g, b, a}` floats in `0..1`. Use the hex↔RGBA helpers described in `token-export.md` for the conversion in both directions.

**Motion asymmetry — be honest.** Figma Variables has no native representation for durations in ms or cubic-bezier easing strings. Skill behavior: surface these as a plain text block pinned to a frame called "Design tokens — motion (not synced)" so the designer can see the values even though they don't flow through Variables. Never claim motion synced when it didn't.

## Read flow (Figma → design-system.md)

1. **Fetch.** Call `GET https://api.figma.com/v1/files/:key/variables/local` with `X-Figma-Token: $FIGMA_TOKEN`. If non-Enterprise (`403`), fall back to `GET /v1/files/:key` and extract color styles + text styles from the `styles` block — less complete but universal.
2. **Parse.** Walk the response. For each variable whose `name` matches a row in the mapping table, extract its `valuesByMode[<modeId>]` and convert back to the `design-system.md` shape:
   - RGBA float → hex string (6-char `#RRGGBB`, or 8-char `#RRGGBBAA` if alpha < 1)
   - FLOAT → integer where `design-system.md` expects int (type scale, spacing, radius)
   - STRING → bare string (font family)
3. **Diff.** Compare incoming values against the current `design-system.md` YAML. Produce a list of `{key, old, new}` entries. Ignore keys that are unchanged.
4. **Menu.** Ask the user present as a visual choice (see SKILL.md § capability ladder):
   - `apply all` — overwrite every changed key
   - `review each` — walk one key at a time, yes/no/skip
   - `cancel` — do nothing, no snapshot, no write
5. **Snapshot before write.** Before overwriting `design-system.md`, invoke the save flow from `theme-versioning.md` to stamp the current state as `design-system.v<N>.md`. Non-negotiable — sync is a destructive edit.
6. **Write.** Apply the approved changes to `design-system.md`'s YAML frontmatter. Leave the prose intact. Bump `meta.version`.
7. **Verify.** Run `assets/a11y-audit.py` on the new token set if any color changed. Flag any new contrast failures to the user — don't silently pass a sync that breaks accessibility.

## Write flow (design-system.md → Figma)

1. **Tier check.** `GET /v1/files/:key/variables/local` to confirm Enterprise access and fetch the existing variable IDs (so the write targets UPDATE, not CREATE, for already-synced tokens).
2. **Build payload.** Read `design-system.md` YAML, map each key per the mapping table, transform colors to `{r,g,b,a}` floats. The `figma-variables.template.json` in `assets/exports/` is the starting shape — the write payload shares its structure but adds `variableCollectionId`, `variableId`, and `action` fields per Figma's API.
3. **Dry-run preview.** Build the diff against the current file state (fetched in step 1). Produce a summary:

   ```
   Figma write plan:
     CREATE  color/accent/default  (light: #0A7A5C, dark: #10B981)
     UPDATE  color/bg/primary      (light: #FAFAF9 → #F9F9F7)
     DELETE  (none)
   15 unchanged.
   ```

4. **Confirm.** Ask the user present as a visual choice (see SKILL.md § capability ladder): `proceed / cancel`. Never write without explicit confirmation — this edits someone else's Figma file.
5. **Write.** Enterprise path: `POST https://api.figma.com/v1/files/:key/variables` with the batched payload. Body shape per Figma's docs: `{ "variableCollections": [...], "variables": [...], "variableModeValues": [...] }`. On success Figma returns the new/updated IDs — cache them in `design-system.md`'s `meta.figma_variable_ids` so subsequent writes UPDATE in place.
6. **Non-Enterprise fallback.** If step 1 returned 403, skip the API write entirely. Run the emit path from `token-export.md` to write `exports/figma-tokens.json`. Report to the user: "Variables REST API requires Enterprise. Emitted `figma-tokens.json` — import via Tokens Studio plugin (Figma → Plugins → Tokens Studio → Import)."
7. **Record.** Update `meta.last_figma_sync` in `design-system.md` with an ISO timestamp so the next conflict check has a baseline.

## Conflict resolution (both sides edited)

Both ends can diverge between syncs. The skill must detect this, not merge blindly.

**Detection.** Compare:

- `design-system.md` file mtime (or `meta.last_figma_sync` timestamp) against Figma's `version` field from `GET /v1/files/:key` metadata (Figma returns a file version number that increments on every file save).
- If both changed since the last sync recorded in `meta.last_figma_sync`, there's a conflict.

**Resolution.** Surface the conflict, list the divergent keys (tokens changed in markdown AND in Figma), and ask present as a visual choice (see SKILL.md § capability ladder):

1. **Keep design-system.md** — overwrite Figma with markdown values. Proceed to write flow.
2. **Keep Figma** — overwrite markdown with Figma values. Snapshot the current markdown first (theme-versioning.md save flow), then proceed to read flow.
3. **Review each conflict individually** — walk the divergent keys one by one. For each: show markdown value, show Figma value, let the user pick. Accumulate the choices into a single write (in whichever direction has more wins, or split if needed).
4. **Cancel** — do nothing.

Never silently auto-merge. A conflict means two humans disagreed; resolving it is a decision, not a merge algorithm.

## Scope caveats

- **Motion tokens** (`motion.duration_*`, `motion.easing*`) don't flow through Figma Variables. Surfaced as a pinned text frame "Design tokens — motion (not synced)" for the designer's reference only. Read/write operations on these tokens are no-ops against Figma.
- **Font files.** Figma needs the font uploaded independently per-file or via the org's shared library. The sync transfers the font *name* as a string variable; the actual `.woff2` doesn't travel. Point users to `fonts.google.com` or the foundry for the upload.
- **Breakpoints** (`breakpoints.*`) — out of scope. Figma has no native breakpoint primitive; these stay in markdown and code.
- **i18n** (`i18n.*`), **illustration SVGs**, **imagery queries**, **personality prose** — all out of scope. They live in markdown only.
- **Text Styles vs. Variables.** Figma's Text Styles (which compose family + size + leading + tracking into one style) are not fully bindable to Variables yet. The skill writes the family/scale as Variables AND upserts matching Text Styles by name (e.g., `Display/XL`, `Body/Base`) so both surfaces in Figma stay current. If a Text Style already exists with a matching name that the skill didn't create, it prompts before overwriting — see anti-patterns below.

## Worked example

Designer opens `https://www.figma.com/design/abc123XYZ/Project` and darkens the accent from `#0A7A5C` to `#0B6B4E`. Engineer runs the skill.

```
> sync from Figma

Fetching variables from Figma file abc123XYZ...
Found 1 change:
  color.accent: #0A7A5C → #0B6B4E

Apply? [apply all / review each / cancel]
> apply all

Snapshotting current as design-system.v3.md...
Writing design-system.md (color.accent updated).
Running WCAG audit on new accent...
  #0B6B4E on #FAFAF9 — contrast 6.2:1 — passes AA and AAA

Synced 1 change. Snapshot saved as v3. A11y passes.
```

If the new accent had failed contrast, the report would call it out and leave the decision to the user: "Contrast dropped to 3.1:1 — fails AA. Keep anyway? (yes/no/revert)."

## Anti-patterns and guard rails

- **Auto-writing to Figma without dry-run.** Every write mode run shows the full plan (CREATE/UPDATE/DELETE) and gates on explicit confirmation. No exceptions, not even for "tiny" changes.
- **Overwriting user-authored Text Styles by name collision.** Before upserting a Text Style named `Body/Base`, check its `description` field — if the skill didn't write it (no `selran-design-director` marker), prompt the user before overwriting.
- **Running write mode on a file without write access.** Before the POST, send a `HEAD /v1/files/:key` (or equivalent permission probe). A `403` means read-only — abort write mode and surface the error plainly.
- **Claiming motion synced when it didn't.** The summary report after every sync must be accurate: list what flowed, list what didn't. Don't hide the asymmetry.
- **Silently dropping tokens Figma can't represent.** If a new token appears in `design-system.md` that has no mapping row, surface it: "No Figma mapping for `color.gradient_start` — not synced. Add a mapping in `references/figma-sync.md` if this should sync."
- **Skipping the pre-sync snapshot.** Any read-mode write to `design-system.md` takes a `.vN.md` snapshot first. Users will undo syncs sometimes; the snapshot is the undo button.
- **Hardcoding `FIGMA_TOKEN` anywhere.** Token reads come from the environment every run. Never write the token into `design-system.md`, never log it, never echo it into terminal output.
- **Syncing without checking a11y.** Any color change that lands via read mode gets `assets/a11y-audit.py` run against it. A failed audit doesn't block the sync but does get loudly reported.

## Related files

- `references/token-export.md` — the one-way emit this file extends. Reuses hex↔RGBA helpers and the `figma-variables.template.json` shape.
- `references/theme-versioning.md` — the snapshot flow invoked before every destructive read-mode write.
- `assets/exports/figma-variables.template.json` — the Variables payload shape for Enterprise writes.
- `assets/exports/figma-tokens.template.json` — the Tokens Studio plugin format for non-Enterprise fallback.
- `assets/a11y-audit.py` — the contrast-check script run after any color sync.
- `assets/figma-sync/README.md` — assets-side notes and `.env` pattern documentation.
