# pack-overrides/

Optional per-direction token overlays. One YAML file per direction this pack extends. The filename must match the core direction name exactly (e.g. `technical-minimal.yaml`, `editorial.yaml`, `dark-premium.yaml`).

Users opt in by writing `direction: <direction>+<pack-name>` in their `design-system.md` — the loader shallow-merges your overlay on top of the core starter and produces the combined token set.

## The only-override-what-differs principle

Every line in an overlay is a deliberate deviation from the core starter. If you'd write the same value the core already has, delete the line — inheritance does the work for you.

See `../../../references/pack-authoring.md#6-overlay-authoring-the-only-override-what-differs-principle` for the full discipline.

## Good overlay

```yaml
# pack-overrides/technical-minimal.yaml
color:
  accent: "#0B4A8C"           # institutional blue — different from core's slate
  accent_hover: "#083869"
personality:
  - "tabular numerals mandatory on all numeric data"
```

Four lines. Every one is a real deviation. Everything else inherits.

## Over-specified overlay (avoid)

```yaml
# Don't do this — most of these values are identical to core
color:
  accent: "#0B4A8C"
  accent_hover: "#083869"
  bg: "#ffffff"               # same as core — delete
  fg: "#111111"               # same as core — delete
  border: "rgba(0,0,0,0.08)"  # same as core — delete
type:
  body_family: "system-ui"    # same as core — delete
spacing:
  base_unit: 8                # same as core — delete
```

Over-specification looks thorough but it locks your pack to the core starter's current values. When the core tunes a token, your pack won't inherit the fix — you shadow it. Only list deviations.

## What you can override

- `color` — accent, accent_hover, gray_family, etc.
- `type` — display_family, body_family, mono_family, scale_max
- `spacing` — base_unit and derived scale
- `motion` — duration_base, easing_standard
- `personality` — the direction's signature gesture list (array — pack replaces, does not append)
- `overrides` — direction-specific opinionated knobs

## What you cannot override

- `direction.name` — the base identity is fixed
- Anything outside the merge-allowed keys above (SKILL.md, core components, core references)
