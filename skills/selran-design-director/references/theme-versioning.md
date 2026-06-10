# Theme versioning

A design-system.md isn't always one-shot. Users iterate. This file covers how to fork, save, and diff themes so exploration stays reversible.

Invoke when the user says any of:

- "Save this as a version" / "snapshot this"
- "Fork the design" / "make a variant"
- "Compare this to the previous version"
- "Revert to v1" / "go back to the original"
- "What changed since last time?"

## File naming convention

One project, multiple themes — disambiguate with a suffix.

```
project/
├── design-system.md              ← the current, active design system
├── design-system.v1.md           ← saved snapshot
├── design-system.v2.md           ← saved snapshot
├── design-system.editorial.md    ← named variant (fork)
└── design-system.dark.md         ← named variant (fork)
```

**Rules:**

- The unsuffixed `design-system.md` is always the active one Claude reads from.
- Numeric suffixes (`.v1.md`, `.v2.md`) are auto-generated snapshots — timeline.
- Named suffixes (`.editorial.md`, `.dark.md`) are intentional forks — parallel options.
- Never overwrite a saved version silently. If a user asks to save and a `.v3.md` already exists, bump to `.v4.md`.

## When to snapshot

Automatic snapshot triggers — take one without asking:

- **Before any destructive regeneration.** If the user asks to "regenerate tokens from intent" or "start over with a different direction," save the current state as `.vN.md` first.
- **On an explicit save request.** "Save this" or "snapshot this" → create `.vN.md` from the current state, keep the active file unchanged.

Don't snapshot after every tweak — the tweak loop is the exploration space. Snapshot is for when a design is "done enough to keep around."

## The save flow

1. Find the next available version number: scan for existing `.vN.md` files, take N+1.
2. Copy the current `design-system.md` to `design-system.v{N+1}.md` verbatim (YAML + prose, whatever exists).
3. Add a `snapshot:` field to the new file's `meta:` block: snapshot date and a one-line label if the user gave one.

```yaml
meta:
  project: "Untitled"
  created: "2026-04-17"
  version: 3
  snapshot: "2026-04-17 — after accent tweak to oxblood"
```

4. Confirm to the user, one line:

> Saved as `design-system.v3.md`. Current stays active.

## The fork flow

Forking differs from snapshotting: the fork becomes a parallel working file the user can switch between.

1. Ask the user for a short name (one or two words): "What should I call this variant?"
2. Copy `design-system.md` to `design-system.{name}.md`.
3. The active file doesn't change — the user can switch by renaming, or by telling Claude "work from the `editorial` variant now" (Claude copies the named variant over the active file).

Fork is the right tool when the user says things like "what if we also tried a warmer version?" — they want both to coexist.

## The diff flow

On "what changed?" or "compare":

1. Identify both files (active + target version, or two named versions).
2. Parse the YAML from both.
3. Produce a field-by-field diff, ignoring whitespace and comment drift.

Report format — only the changes, not the full file:

```
Diff: design-system.v1.md → design-system.md (current)

Color
  accent:        #0A7A5C  →  #6B0F1A
  accent_hover:  #065F47  →  #4F0A14

Type
  display:       "General Sans"  →  "Fraunces"

Motion
  duration_base: 200ms  →  250ms

Unchanged: bg/fg colors, spacing, leading, tracking, personality.
```

**Rules for the diff:**

- Group by top-level YAML key (Color, Type, Spacing, Motion, etc.)
- Show old → new, not a patch syntax
- End with a one-line "unchanged" summary so the user knows the diff is complete
- If prose sections differ, note "+N lines added / -M lines removed" — don't dump the full text diff inline

## The revert flow

On "revert" or "go back":

1. Ask which version — list them with their labels (from the `snapshot:` metadata):

> Which version to restore?
> - v1 — original (2026-04-17)
> - v2 — after accent tweak to oxblood (2026-04-17)
> - editorial (named variant)

2. After user picks, snapshot the current state first (auto-save), then overwrite `design-system.md` with the chosen version.
3. Confirm:

> Restored v1. Current state saved as v4 in case you want it back.

Never destroy the "current" state on revert — always snapshot first. The user should be able to un-revert.

## What not to do

- Don't version automatically on every tweak (noisy, wasteful).
- Don't merge variants without asking — merging YAML is interpretive and usually wrong.
- Don't delete old versions without explicit confirmation. "Clean up old versions?" is a fine offer, but don't act unprompted.
- Don't diff the prose sections line-by-line — summarize, or the output buries the real changes.
