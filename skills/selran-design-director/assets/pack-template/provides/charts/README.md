# provides/charts/

Chart CSS additions — extra series palettes, axis treatments, annotation layers, locale-specific conventions (e.g. red-up for Japanese finance, red-down everywhere else).

One file per direction your pack targets. Consume the same CSS variables as core `assets/charts/` — `--chart-series-1` through `--chart-series-n`, `--chart-axis`, `--chart-grid`. Extend, don't shadow.

## Typical layout

```
provides/charts/
├── {{BASE_DIRECTION}}.css        # pack's extensions to the base direction
└── <other-direction>.css         # optional: if you target a second direction
```

## Register in pack.yaml

```yaml
provides:
  charts:
    - "provides/charts/{{BASE_DIRECTION}}.css"
```

## Why pack charts exist

Pack charts are where domain-specific conventions live: a fintech pack swaps the default series palette for an institutional-blue-led one; an academic pack adds a colorblind-safe categorical palette as the default; a healthcare pack constrains series count to three so clinicians never have to read a ten-line chart. The core's palette is neutral on purpose — packs opinionate.

This directory is optional — delete it if your pack doesn't ship chart extensions.

See `../../../references/data-viz.md` in the core for the data-viz philosophy, palette discipline, and the locale trap with red/green.
