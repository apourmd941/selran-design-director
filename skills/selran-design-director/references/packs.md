# Packs — the add-on system

The core skill (MIT) ships seven directions, a shared component library, and the full four-phase flow. **Packs** are optional add-ons that plug into the core without modifying it: extra directions, extra component libraries, extra references, extra assets. A pack can be MIT, CC-BY, or commercial — its own `LICENSE` travels with it.

This file is the canonical spec for pack authors and for the runtime that discovers and loads them. Read it end-to-end before publishing a pack, and before the core tries to consume one.

> **Core stays MIT.** Packs are the paid surface. The core never ships commercially-licensed content inline — if it did, MIT would be a lie.

---

## What a pack is

A pack is a self-contained folder that extends the core with new capabilities:

- **Additive:** a pack can *add* new directions, components, references, illustrations, assets, shells, states, wizards, charts, motion presets
- **Extendable:** a pack can *extend* existing core directions via `pack-overrides/` (e.g., layer a new accent family onto technical-minimal) without touching core files
- **Never destructive:** a pack **cannot overwrite core files**. The loader refuses packs whose `provides/` paths collide with core paths.

A pack ships as a zip (or a git-clonable folder). When installed, it lives at:

```
~/.claude/skills/selran-design-director/packs/<pack-name>/
```

The core auto-discovers every folder under `packs/` on load and validates each one's manifest before exposing it to the runtime.

---

## Pack manifest (`pack.yaml`)

Every pack has a `pack.yaml` at its root. This is the loader's entry point.

```yaml
# --- Identity ----------------------------------------------------
name: "dashboards-pro"                    # unique, kebab-case, stable
version: "1.0.0"                          # semver
display_name: "Dashboards Pro"            # human-readable name shown in UI
author: "Your Name or Studio"
homepage: "https://example.com/packs/dashboards-pro"
description: >
  18 production-grade dashboard shells across all seven directions —
  analytics overview, admin console, multi-tenant settings, billing,
  observability, incident view, user management.

# --- Licensing ---------------------------------------------------
# One of: MIT | Apache-2.0 | CC-BY-4.0 | Commercial | Proprietary
# Commercial/Proprietary packs require license_key for installs — see "License gate" below
license: "Commercial"
license_file: "LICENSE"                   # path inside the pack (relative to pack.yaml)
license_url: "https://example.com/packs/dashboards-pro/license"
price_usd: 49                             # optional; informational only

# --- Compatibility ----------------------------------------------
requires_core: ">=3.3.0"                  # minimum core SKILL.md version
requires_packs: []                        # other packs this depends on (advanced)

# --- What this pack ships ---------------------------------------
# Every path here is relative to the pack root and must exist under provides/
# The loader validates each path before exposing it.
provides:
  directions: []                          # new aesthetic directions (rare; big investment)
  components:                             # new component snippets (common)
    - "provides/components/dashboard-overview/"
    - "provides/components/billing-table/"
    - "provides/components/settings-panel/"
  shells:                                 # app-shell primitives
    - "provides/shells/admin-console/"
  states: []                              # screen-state variants
  wizards: []                             # wizard flow variants
  charts: []                              # chart CSS additions
  motion: []                              # motion snippets
  illustrations: []                       # SVG + ai-prompts
  references:                             # reference docs (markdown)
    - "provides/references/dashboard-patterns.md"
  exports: []                             # token export formats

# --- Optional: extend (not overwrite) existing core directions ---
# Values land in a per-direction overlay dict the resolver shallow-merges
# on top of the core starter when a user picks that direction AND opts in
# to this pack's overlay. Users opt in via `direction: <name>+<pack-name>`
# in their design-system.md, OR via the "list packs" workflow.
pack_overrides:
  technical-minimal:
    # Only override what genuinely differs. Omit everything else.
    color:
      accent: "#0066FF"                   # saturated electric blue variant
      accent_hover: "#0052CC"
    personality:
      - "live ticker numerals in mono, never regular sans"

# --- Provenance --------------------------------------------------
created: "2026-04-21"
updated: "2026-04-21"
```

### Field rules

- **`name`** — must be unique across all installed packs. Kebab-case, lowercase. This becomes the on-disk folder name and the identifier used in `direction: technical-minimal+<name>` extensions.
- **`license`** — the loader treats `MIT`, `Apache-2.0`, `CC-BY-4.0` as "open." `Commercial` and `Proprietary` trigger the license gate (see below). Packs with no `license` field are rejected.
- **`requires_core`** — a semver range. The loader compares against the core's `version` field in `SKILL.md`. If the core is older, the pack is loaded but a warning fires at first use.
- **`provides`** — every key is a plural noun matching a core asset category. Missing keys default to `[]`. Paths must exist at load time; broken paths fail validation.
- **`pack_overrides`** — optional. If present, each key must be one of the seven core direction names. Shallow-merges onto the core starter when the user opts in.

---

## Install convention

**Install paths:**

- Global (all projects on this machine): `~/.claude/skills/selran-design-director/packs/<pack-name>/`
- Project-local (checked in with a specific repo): `<project-root>/.selran-packs/<pack-name>/`

The core checks both locations on load. Project-local wins on name collision (useful for pinning a pack version to a repo).

**Install flow (user):**

```bash
# From a downloaded zip
unzip dashboards-pro-1.0.0.zip -d ~/.claude/skills/selran-design-director/packs/

# From git (for MIT / open packs)
git clone https://github.com/example/dashboards-pro \
  ~/.claude/skills/selran-design-director/packs/dashboards-pro

# From a license-gated source (hypothetical CLI, future work)
selran-pack install dashboards-pro --license-key $SELRAN_LICENSE_KEY
```

**No package registry is mandated by the core.** Packs are distributed however the author wants — marketplace site, GitHub release, direct zip, private git repo. The core just consumes what's on disk.

---

## Layering rules (what packs can and cannot do)

Three tiers, in priority order from least to most invasive:

### 1. ADD (always allowed)

A pack can ship brand-new artifacts that live alongside core artifacts. Examples:

- New component in `provides/components/dashboard-overview/` — loaded under the pack's namespace, doesn't touch `assets/components/`
- New reference doc in `provides/references/dashboard-patterns.md` — accessible via `references/packs/<pack-name>/dashboard-patterns.md` at runtime
- New chart CSS, new motion preset, new illustration set — all additive

**Rule:** additive content is namespaced under the pack name. No collisions with core.

### 2. EXTEND (allowed via `pack_overrides`)

A pack can overlay token adjustments on top of a core direction. Examples:

- Swap the accent family for a "brand-X flavored technical-minimal"
- Add a personality rule
- Nudge the type scale for a denser variant

**Rule:** only keys under `color`, `type`, `spacing`, `motion`, `personality`, `overrides` can be overridden. Overrides shallow-merge (scalars/objects: pack wins per key; arrays: pack replaces). **`direction.name` is never overridden** — the base identity is fixed.

Users opt in per project by writing `direction: technical-minimal+dashboards-pro` in their design-system.md, or by picking the overlay from the tweak-loop menu.

### 3. OVERWRITE (never allowed)

A pack **cannot**:

- Replace a file at `assets/components/technical-minimal/nav.html`
- Replace `references/aesthetic-directions.md`
- Change the core schema at `assets/design-system-schema.md`
- Modify `SKILL.md` itself
- Swap the loader, the a11y audit, the anti-patterns list

**Rule:** if a pack's `provides/` or `pack_overrides/` path would collide with a core path, the loader refuses to install the pack and surfaces the colliding path to the user.

This rule is how the core stays coherent across packs. A user who installs five packs still gets one consistent design director, not five.

---

## Discoverability

**At runtime, the user can ask:**

- *"What packs do I have?"* → the core lists every installed pack with name, version, license, provides summary
- *"Install the dashboards pack"* → the core explains install convention and (for MIT packs) offers to clone the repo; for commercial packs it prints the license-gate instructions
- *"What packs extend technical-minimal?"* → the core scans `pack_overrides:` across all installed packs and returns matches

**In design-system.md,** installed packs appear in a comment block at the top after Phase 2 drafting:

```yaml
# --- Installed packs (read-only) -------------------------------
# dashboards-pro v1.0.0 (Commercial) — components, shells
# editorial-serifs v2.1.0 (MIT) — directions, references
```

**External marketplace (out of scope for this reference):**

There's no official registry. Pack authors list their own packs on their own sites. If a centralized marketplace emerges, we'll document it here — but the core doesn't require one.

---

## License gate

The loader enforces a simple rule:

> **Commercial / Proprietary packs cannot be used without proof of license.**

Proof of license is one of:

1. A `license_key` file in the pack root matching the author's published validator (authors implement their own — the core doesn't run arbitrary validation code)
2. An environment variable `SELRAN_PACK_LICENSE_<PACK_NAME>` set to a value the author's README tells the user to set
3. An explicit user acknowledgment recorded in `~/.claude/skills/selran-design-director/.pack-licenses.yaml`:

```yaml
# ~/.claude/skills/selran-design-director/.pack-licenses.yaml
acknowledged:
  dashboards-pro:
    version: "1.0.0"
    license_type: "Commercial"
    acknowledged_at: "2026-04-21"
    acknowledged_by: "user@example.com"
```

If none of the three is present and the pack's `license` is Commercial or Proprietary, the loader:

1. Loads the pack's `pack.yaml` metadata (so it shows up in "list packs")
2. Refuses to serve any content from the pack
3. Tells the user: *"`dashboards-pro` is commercially licensed. Set `SELRAN_PACK_LICENSE_DASHBOARDS_PRO` or acknowledge your purchase in `.pack-licenses.yaml` to enable it."*

**The core never runs payment code.** Payment and license distribution happen outside the skill — on the author's site, via Stripe, via whatever. The core just checks: "do you have the key, yes or no?"

**For MIT / Apache-2.0 / CC-BY packs:** no gate. The pack loads freely.

---

## Pack authoring guide

To build a new pack, start from `assets/pack-template/` in the core. Copy it, rename, and fill in.

```bash
cp -r ~/.claude/skills/selran-design-director/assets/pack-template \
      ~/my-packs/my-awesome-pack

cd ~/my-packs/my-awesome-pack
# Edit pack.yaml — set name, license, provides
# Drop your content into provides/
# (Optional) Add pack_overrides/ adjustments
# Write your LICENSE file
# Zip it for distribution: zip -r my-awesome-pack-1.0.0.zip my-awesome-pack/
```

### Directory layout

```
my-awesome-pack/
├── pack.yaml                    # manifest (required)
├── README.md                    # human-facing overview (required)
├── LICENSE                      # full license text (required)
├── CHANGELOG.md                 # version history (optional but encouraged)
├── provides/                    # what the pack ships (every path declared in pack.yaml)
│   ├── components/
│   │   └── dashboard-overview/
│   │       ├── technical-minimal.html
│   │       ├── editorial.html
│   │       └── ... (one per direction you support)
│   ├── shells/
│   ├── states/
│   ├── wizards/
│   ├── charts/
│   ├── motion/
│   ├── illustrations/
│   ├── references/
│   └── exports/
└── pack-overrides/              # optional overlays on core directions
    ├── technical-minimal.yaml   # shallow-merged onto the core technical-minimal starter
    └── editorial.yaml
```

### Content discipline

Everything in a pack must still follow core rules:

- **Anti-patterns still apply.** A pack cannot ship Corporate Memphis illustrations even if it's an illustration pack. See `references/anti-patterns.md`.
- **A11y audits still run.** Pack components go through the same contrast + focus + hit-target checks.
- **The seven directions are fixed in spirit.** A "dashboards" component for `technical-minimal` must *look* technical-minimal. Authors can't smuggle a different aesthetic under a direction name.
- **Token-driven.** Pack components consume the same CSS variables (`--accent`, `--fg`, `--bg`, etc.) as core — no hardcoded hex in components.

### Versioning

Use semver. Breaking changes to `pack.yaml.provides` paths or to `pack_overrides` shapes are major-version bumps. Adding new files is a minor bump. Typo fixes in provided content are patch bumps.

---

## Runtime workflows

The core exposes three user-facing workflows for pack management:

### "List packs" / "What packs do I have?"

Scan `~/.claude/skills/selran-design-director/packs/` and (if in a project) `<project-root>/.selran-packs/`. For each pack, print:

```
• dashboards-pro v1.0.0 (Commercial)
  Provides: 3 components, 1 shell
  License: licensed ✓
  Overlays: technical-minimal

• editorial-serifs v2.1.0 (MIT)
  Provides: 1 direction, 4 references
  License: open ✓
  Overlays: —
```

### "Install this pack" / "Add the X pack"

Explain the install convention. For MIT packs with a public git URL in their `pack.yaml.homepage`, offer to clone directly. For commercial packs, surface the author's purchase URL and explain the license-gate options.

### "Disable this pack" / "Remove the X pack"

Move the pack folder to `~/.claude/skills/selran-design-director/packs/.disabled/` (don't delete — the user's license may still be valid). The loader skips disabled packs on next load.

### Pack distribution (v3.4+)

Starting in core v3.4, packs can be installed, verified, and published via the `selran` CLI against a JSON-indexed registry. The canonical registry at `https://registry.selran.design/index.json` lists Selran's official packs; self-hosted registries work by serving the same schema from any URL and pointing the CLI via `SELRAN_REGISTRY_URL` or `--registry`.

- **User install:** `selran pack install fintech` (resolves latest, verifies ed25519 signature, extracts)
- **Reproducible CI install:** `selran pack install --from-lock` (reads `selran-packs.lock.yaml`, fails on any drift)
- **Pack author publish:** `selran pack publish ./my-pack/ --key ~/.selran/private.pem` (validates, signs, uploads)

The copy-the-folder install pattern documented above still works unchanged. Installing via the CLI is additive — both paths write to the same `~/.claude/skills/selran-design-director/packs/` directory.

For the full workflow, see [`references/pack-distribution.md`](./pack-distribution.md). For the CLI command reference, see [`cli/README.md`](../../cli/README.md). For the registry schema and seed index, see [`registry/README.md`](../../registry/README.md).

---

## What packs are NOT

- **Packs are not forks.** If you want to fork the core, fork the whole skill. Packs plug in; they don't rewrite.
- **Packs are not themes.** Themes are what `design-system.md` already is. A pack is a collection of *capabilities*, not a single brand identity.
- **Packs are not configuration.** Configuration lives in `design-system.md`. A pack adds *what can be configured*.
- **Packs are not plugins to other tools.** This system is Selran-specific. Don't try to load a VS Code extension as a pack.

---

## See also

- `assets/pack-template/` — copy-and-fill scaffold for new packs
- `references/anti-patterns.md` "Pack-development anti-patterns" — what a well-behaved pack never does
- `references/theme-versioning.md` — how pack versions interact with design-system.md snapshots
- `SKILL.md` Phase 4 build routing — how pack content is loaded during a build
