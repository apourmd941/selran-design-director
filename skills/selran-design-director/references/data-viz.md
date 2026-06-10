# Data Visualization

How each direction handles charts, tables, and quantitative display. This reference fixes the improvised chart-building problem: series colors, axes, legends, tooltips, and chart-type selection are all specified per direction so dashboards look as intentional as the rest of the system.

Tokens drive everything. Every rule below maps back to values already declared in the direction starter YAML (`color.accent`, `color.palette`, `color.fg_*`, `color.border*`, `type.*`, `spacing.*`). If a chart library needs a hex, pull it from tokens — never hand-pick.

The matching per-direction utility CSS lives under `assets/charts/{direction}.css`, with a `README.md` index.

---

## 1. Philosophy per direction

### technical-minimal
Tufte-leaning. Maximize data-ink, minimize chart-junk. Gridlines almost invisible, axis labels understated, direct labels on series end-points rather than a detached legend. One accent carries interactive series; everything else is neutral gray. Tabular numerals on every axis tick.

### editorial
Data as narrative. Charts are annotated — a pull-quote-style note explaining the peak or the inflection. Usually a single series or a hero series against muted context. Serif labels, generous whitespace, rule lines instead of boxes. The chart is an illustration inside an article, not a dashboard widget.

### dark-premium
High contrast, luminous lines on deep ground. One or two series max; the accent series glows with a soft drop-shadow. Spacious framing, thin accent underlines, small-caps metadata. Numbers are hero elements — a single stat often outsizes the chart itself.

### warm-approachable
Soft, rounded, accessible. Rounded line-caps, gentle curves (`tension: 0.35`), warm-tinted tooltips with paper-like shadows. Labels plain and friendly — no mono, no small-caps screaming. Gridlines barely there, in a warm low-opacity neutral. Color accents pulled from the warm palette so charts feel hand-printed.

### vibrant-playful
Multi-series native. The five-hue `color.palette` maps directly to series 0–4, with two palette-tonal extensions (darken / lighten) for series 5–7. Chart types can be more expressive — stacked bars, grouped bars, donut with exactly four slices. Rounded corners on bars, medium font weight, friendly tooltips.

### brutalist
Raw, blocky, system-font. Grids are sharp 1px black (or white, in dark mode). No gradients, no shadows, no rounded corners — ever. Legends are bracketed `[series-a]` style in Space Mono. ASCII-tinged acceptable: a sparkline rendered as text, a table that looks like `cat data.tsv`.

### bold-distinctive
Editorial-scale numbers. Axis labels can be absurdly large; one value per chart is singled out at display size with an italic annotation line pointing to it. Often single-series or duotone. Grids are functional, not decorative. The chart title is the scene-setter and can run 48–72px.

---

## 2. Series colors per direction

Two strategies: **tonal stepping** from a single accent (most directions) or **direct palette mapping** (vibrant-playful, and warm-approachable when `color.palette` is defined).

### Direct palette mapping — `vibrant-playful`
Series 0–4 pull from `color.palette[0..4]` in order. Series 5–7 extend via tonal steps on palette[0], palette[1], palette[2] (each lightened 12%).

```
series-1: palette[0]              # C44A2E
series-2: palette[1]              # 1F6B88
series-3: palette[2]              # 9C6014
series-4: palette[3]              # 553E5A
series-5: palette[4]              # 2A7B60
series-6: lighten(palette[0], 12%)
series-7: lighten(palette[1], 12%)
series-8: lighten(palette[2], 12%)
```

### Tonal stepping — all single-accent directions
Start from `color.accent`, step through HSL space keeping hue locked and pushing lightness apart by ~10–12% per step. Add a neutral gray anchor near the end for low-emphasis series.

```yaml
# data-viz-colors.yaml — the formula used by every tonal direction
base: color.accent
steps:
  series-1: hsl(accent.h, accent.s,       accent.l)          # accent as-is
  series-2: hsl(accent.h, accent.s,       min(accent.l+12, 72))   # accent lightened
  series-3: hsl(accent.h, max(accent.s-15, 25), max(accent.l-12, 20))  # accent darkened + desat
  series-4: hsl(accent.h, accent.s,       min(accent.l+24, 82))   # accent soft
  series-5: hsl(fg_muted.h, fg_muted.s,   fg_muted.l)        # neutral anchor
  series-6: hsl(fg_muted.h, fg_muted.s,   min(fg_muted.l+15, 78))
  series-7: hsl(accent.h, max(accent.s-30, 15), max(accent.l-25, 18))  # near-black of accent
  series-8: hsl(fg_muted.h, fg_muted.s,   max(fg_muted.l-20, 30))

notes:
  - Never rotate hue. That's how you get rainbow-by-accident.
  - Series 5 / 6 / 8 intentionally read as "other / rest" — assign low-emphasis data to them.
  - For dark-mode variants, start from the dark-mode accent; add +5% lightness across the board.
  - Color-blind check every palette; if any pair falls under 3:1 luminance contrast, add a shape/pattern marker.
```

Cooked values are provided inline in each direction's CSS file so you don't have to compute them at runtime.

---

## 3. Chart type selection

| Chart type | Use when | Avoid when |
|-|-|-|
| Line | Trends over time; comparing 2–4 series across continuous x | Categories with no ordering |
| Bar | Categorical comparisons; ranked items | Time-series (line reads better) |
| Stacked bar | Part-of-whole across categories; totals matter | You need to compare non-bottom segments |
| 100% stacked bar | Composition where totals don't matter | Totals do matter |
| Area | Cumulative / volume-over-time (single series preferred) | Multi-series — overlaps deceive. Use line. |
| Scatter | Correlation between two continuous variables | <15 points; a table reads better |
| Pie / donut | ≤5 categories where part-of-whole is the story AND a bar wouldn't read clearer | >5 slices, ever. Or when a ranked bar suffices. |
| Heatmap | Dense matrix (day × hour, cohort × week) | <5×5 cells — a table is more honest |
| Sparkline | Inline trend context in a table or stat card | Anything where precision matters |

### Direction-specific chart-type notes

- **editorial** — leans heavily on annotated line. One series, one callout, one "here is the moment" note. Slope charts for before/after.
- **technical-minimal** — line and bar dominate. Small multiples over one crowded chart. Sparklines on every stat card.
- **dark-premium** — sparse line with a glowing accent series and one muted context series. Donut acceptable at 3–4 slices, very large, with a single huge center number.
- **warm-approachable** — line with `tension: 0.35`, bar with 8–12px `borderRadius`. No stacked bars without labels on each segment.
- **vibrant-playful** — grouped bar, stacked bar, and multi-series line shine here. Donut with ≤4 slices works because the palette handles multi-color honestly.
- **brutalist** — stacked bar with raw 1px black rules between segments. Tables-as-charts. ASCII sparklines (`▁▂▃▄▅▆▇█`) in a mono font are on-brand.
- **bold-distinctive** — a single huge bar, a single huge number, an italic annotation. Don't try to cram a dashboard into this direction.

---

## 4. Axis, gridline, legend, tooltip rules

All rules below pair with the utility classes in `assets/charts/{direction}.css`.

### technical-minimal
- **Axis** — `type.mono` (JetBrains Mono) at 11px, `fg_muted`, weight 400. Tabular numerals required.
- **Gridline** — horizontal only, `border` color at 60% opacity, 1px solid. Vertical gridlines off.
- **Legend** — inline series-end labels preferred. If legend is used: top-right, mono 11px, caret-dots at 6px.
- **Tooltip** — 1px hairline border in `border_strong`, no shadow, 8px padding, mono values. White/dark bg matches page.

### editorial
- **Axis** — serif display stack at 12px, `fg_muted`. Italic on unit labels ("per capita").
- **Gridline** — one baseline rule at the x-axis only (1px `border`, solid). No horizontal gridlines; pull numbers into the chart as direct labels.
- **Legend** — direct annotation preferred. Legend suppressed except in small multiples where a left-aligned italic caption lists series.
- **Tooltip** — cream paper tooltip, 1px `border`, soft 2px offset shadow tinted warm. Serif numbers.

### dark-premium
- **Axis** — `type.body` (Söhne) at 12px, `fg_muted`. Small-caps on axis titles with `caps` tracking.
- **Gridline** — horizontal only, `border` (#27272A) at 50% opacity, 1px solid. The chart area can sit inside a subtly lifted surface (`bg_secondary`).
- **Legend** — top-right; series dots at 8px with 2px `border` ring matching `bg_secondary`. Small-caps labels.
- **Tooltip** — `bg_tertiary` with 1px `border_strong`, 12px padding, soft accent glow on the active series dot. No drop shadow — use `box-shadow: 0 0 0 1px border, 0 20px 40px rgba(0,0,0,0.5)`.

### warm-approachable
- **Axis** — humanist sans (Commissioner) at 12px, `fg_muted`. Weight 400.
- **Gridline** — horizontal only, `border` at 50% opacity, 1px solid.
- **Legend** — top-left, series dots 10px rounded, labels 13px weight 500.
- **Tooltip** — cream card, `radius.md` (16px), warm-tinted soft shadow (`0 8px 24px rgba(45,27,15,0.12)`), no hard border.

### vibrant-playful
- **Axis** — Satoshi at 12px, `fg_muted`, weight 500. Tabular numerals.
- **Gridline** — horizontal only, `border` at 60% opacity. Vertical gridlines off except on grouped bars where they separate groups.
- **Legend** — top-left; series dots 10px, labels weight 500, 13px. One palette color per series.
- **Tooltip** — white card, `radius.md` (12px), 1px `border`, soft shadow (`0 6px 20px rgba(0,0,0,0.08)`). Active series dot ringed in its own color at 2px.

### brutalist
- **Axis** — Space Mono 12px, `fg_primary`, weight 400. Lowercase.
- **Gridline** — full grid (horizontal and vertical), 1px solid `border` (pure black / pure white dark). No opacity tricks.
- **Legend** — bottom, bracketed `[series-a] [series-b]` in mono. Dots are 8px solid squares, never circles.
- **Tooltip** — 2px solid black border, zero radius, zero shadow. Inverts on hover (bg becomes fg). White/black flat fills only.

### bold-distinctive
- **Axis** — General Sans at 14px for minor, 24–32px for anchor labels (the "0" and the max). Weight 500.
- **Gridline** — one horizontal rule at zero (2px solid `fg_primary`). Everything else off. Let negative space carry structure.
- **Legend** — suppressed. Direct annotation with italic (PP Editorial New italic) and a 1px leader line.
- **Tooltip** — flat `fg_primary` background with `bg_primary` text, sharp 0-radius corners, no shadow. Reads like a stamp.

---

## 5. Anti-patterns (forbidden regardless of direction)

- **Rainbow color-mapping** for ordinal data — use a single-hue luminance ramp.
- **3D charts** of any kind. Never. Even if the library supports it.
- **Pie charts with more than 5 slices** — the eye can't compare angles that fine. Use a ranked bar.
- **Dual-axis charts without a compelling reason** — almost always reads as misleading. If you must, lock scales and label both axes explicitly.
- **Chart-junk** — decorative backgrounds, clip-art icons inside chart area, skeuomorphic bars.
- **Axis labels below 10pt** — unreadable. Minimum 11px / 10pt web, 14pt print.
- **Legend-only line labeling** — forces the eye to bounce between legend and line. Direct-label series endpoints when possible.
- **Truncated y-axes that start mid-range without a visible axis break** — deceptive. Start at zero or mark the break.
- **Stacked line charts** — almost always better as a stacked area, and stacked area is already risky. Prefer small multiples.

---

## 6. Responsive charts

- **Desktop (>= 1024px)** — full chart, full labels, legend as specified above.
- **Tablet (768–1023px)** — axis labels shrink one step (12 → 11). Legend can wrap to two rows if needed.
- **Mobile (< 768px)**:
  - X-axis labels rotate 45° or abbreviate (Jan 2024 → `J'24`) or show every other tick.
  - Legends stack below chart, full-width, one row per series.
  - Secondary charts collapse to inline sparklines in the associated stat card.
  - Tooltips trigger on tap; active state persists until outside-tap.
  - Minimum chart height 220px even if container shrinks — don't let charts become unreadable slivers.

---

## 7. Accessibility

- **Never color-only differentiation between series.** Add at least one of:
  - Shape markers (circle, triangle, square, diamond) at line vertices and legend dots.
  - Line patterns (solid, dashed, dotted) per series.
  - Direct text labels on each series.
- **Minimum 3:1 luminance contrast** between any two adjacent series colors. Run the color-blind check (deuteranopia + protanopia + tritanopia) against every palette.
- **Announce data updates in live regions** when charts are interactive (filter changes, range selections) — `aria-live="polite"` on a visually hidden summary updated with the new headline value.
- **Color-blind-safe defaults** — the tonal-stepping formula and the vibrant-playful palette have been checked; if you override colors, re-check.
- **Keyboard navigation** — focusable data points with visible focus rings (the direction's standard focus style, not a browser default).
- **Tooltip content available on focus**, not only hover.
- **Sufficient hit areas** on mobile — minimum 24×24px per interactive point.

---

## 8. Implementation pointer

Render with **Chart.js**, **Recharts**, **Visx**, or **D3** — the CSS in `assets/charts/` applies regardless. The pattern:

1. Load `assets/charts/{direction}.css` on the page or document.
2. Wrap the chart container in the direction-prefix class (`.dv-tm-root`, `.dv-ed-root`, etc.) so custom properties cascade.
3. Pull series colors with `getComputedStyle(el).getPropertyValue('--chart-series-1')` in your chart config. Never hardcode.
4. Apply utility classes to axis labels (`.dv-tm-axis-label`), legend (`.dv-tm-legend`), tooltip container (`.dv-tm-tooltip`), and annotations (`.dv-ed-annotation`).
5. For SVG-native libraries (D3, Visx), set `stroke="currentColor"` or reference CSS vars directly in the SVG style attribute.

Chart.js quick-start snippet (technical-minimal):

```js
const root = document.querySelector('.dv-tm-root');
const css = getComputedStyle(root);
const series = (n) => css.getPropertyValue(`--chart-series-${n}`).trim();

new Chart(ctx, {
  type: 'line',
  data: { datasets: [
    { label: 'Revenue', borderColor: series(1), backgroundColor: 'transparent', pointStyle: 'circle' },
    { label: 'Costs',   borderColor: series(3), backgroundColor: 'transparent', pointStyle: 'triangle', borderDash: [4, 4] },
  ]},
  options: {
    scales: {
      x: { grid: { display: false }, ticks: { font: { family: 'JetBrains Mono', size: 11 } } },
      y: { grid: { color: css.getPropertyValue('--chart-gridline'), drawBorder: false } },
    },
    plugins: { legend: { display: false } },
  },
});
```

The same pattern rebinds Recharts / Visx / D3 with trivial changes. Tokens drive everything; swap the direction-prefix class and the whole chart re-skins.

---

## 9. Small multiples

When you'd otherwise reach for a crowded multi-series chart (5+ series), consider small multiples instead: a grid of tiny identical charts, one per series, sharing scales. This almost always reads clearer than a spaghetti line chart.

Direction notes:
- **technical-minimal** — 3×3 grid of 180×120 charts. Mono labels above each. The native format for this direction.
- **editorial** — 2×3 grid at generous size. Each gets an italic caption. Feels like an atlas.
- **vibrant-playful** — each multiple gets its palette hue. Coordinated, not rainbow.
- **brutalist** — literal HTML table of sparklines with mono labels. On-brand.
- **bold-distinctive** — avoid. This direction wants one big statement, not a grid of small ones.

Shared scales are mandatory — if each multiple uses its own y-axis, the comparison is meaningless.

---

## 10. Tables-as-charts

Sometimes the right chart is a table. If precision matters, if the reader needs to scan multiple dimensions, or if n < 10, a well-typeset table beats a chart.

- Right-align numeric columns. Left-align text.
- Tabular numerals (`font-variant-numeric: tabular-nums`) always.
- Zebra striping optional and direction-dependent: technical-minimal and brutalist yes; editorial, warm, dark-premium no.
- Inline sparklines in a dedicated trend column — use the `.dv-{prefix}-sparkline` pattern or the `.dv-br-sparkline-ascii` helper for brutalist.
- Thousand-separators in the locale that matches content. No `1K` abbreviations in financial tables.

---

## 11. When to break direction rules

The only legitimate reason to deviate: the data itself demands it. A safety dashboard uses red regardless of brand hue. A stock chart uses green-up / red-down (or culturally-inverted in some markets). A medical chart uses a specific reference palette.

When you break the rules, break them loudly — add a chart-level annotation noting the convention and why. Don't silently sneak an off-brand color in and hope nobody notices.
