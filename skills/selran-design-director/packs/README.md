# packs/

Installed packs live here. Each subfolder is one pack, installed via the `selran` CLI (recommended since v3.4), unpacked from a distribution zip, or cloned from a git repo.

## Install convention

**CLI (recommended, v3.4+):**

```bash
# Signature-verified install from the canonical registry
selran pack install fintech
selran pack install developer-tools@0.2.0

# Reproducible CI install from a lockfile
selran pack install --from-lock
```

**Manual (always supported):**

```bash
# From a zip
unzip my-awesome-pack-1.0.0.zip -d ~/.claude/skills/selran-design-director/packs/

# From git (for MIT / open packs with a public repo)
git clone https://github.com/author/my-awesome-pack \
  ~/.claude/skills/selran-design-director/packs/my-awesome-pack
```

The core auto-discovers every folder here on load. Each folder must have a `pack.yaml` at its root — folders without a valid manifest are skipped with a warning. The `selran` CLI additionally verifies ed25519 signatures on every tarball before extraction; the zip / git paths skip signature verification, so prefer the CLI for commercial and production-critical installs.

## Disabled packs

To temporarily disable a pack without uninstalling:

```bash
mv ~/.claude/skills/selran-design-director/packs/my-awesome-pack \
   ~/.claude/skills/selran-design-director/packs/.disabled/
```

The loader skips anything under `.disabled/`. Re-enable by moving it back.

## Project-local packs

Packs can also live at `<project-root>/.selran-packs/<pack-name>/` — checked into a specific repo so the pack version is pinned to that project. Project-local wins on name collision with a global pack.

## See also

- `references/packs.md` in the core — the canonical pack-system spec
- `references/pack-distribution.md` — end-to-end CLI + registry workflow (v3.4+)
- `references/pack-authoring.md` — the full authoring guide (v3.4+)
- `references/pack-enterprise.md` — private-pack hosting under an enterprise license (v3.4+)
- `assets/pack-template/` in the core — the copy-and-fill scaffold for authoring new packs
- [`cli/README.md`](../../cli/README.md) — complete CLI command reference
