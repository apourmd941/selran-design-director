# Chart utility CSS — per-direction

One stylesheet per aesthetic direction, each exposing `--chart-series-1..8` custom properties and utility classes for axis, gridline, legend, tooltip, annotation, and title/subtitle. Wrap your chart container in the direction's root class (`.dv-tm-root`, `.dv-ed-root`, etc.) and the cascade takes care of the rest.

See `references/data-viz.md` for philosophy, chart-type selection, anti-patterns, responsive, and accessibility rules.

## Color strategy

| Direction | Strategy | Source |
|-|-|-|
| technical-minimal | Tonal stepping | `color.accent` (#0A7A5C emerald) |
| editorial | Tonal stepping | `color.accent` (#7A1F1F oxblood) |
| dark-premium | Tonal stepping | `color.accent` (#D4AF37 warm gold) |
| warm-approachable | Tonal stepping | `color.accent` (#B04A2C terracotta) |
| vibrant-playful | **Direct palette mapping** | `color.palette` (5 hues) + 3 tonal extensions |
| brutalist | Tonal + signal stack | `color.accent` (#B84400) + black/white/warning/success/danger |
| bold-distinctive | Tonal stepping | `color.accent` (#C72500 hot red) |

---

## Files

### technical-minimal.css — `.dv-tm-*`
**Series palette:** emerald ramp (`#0A7A5C`, `#3BA684`, `#064D3A`, `#7CC8AE`, `#71717A`, `#A1A1AA`, `#033224`, `#52525B`)
**Philosophy:** Tufte-leaning. Maximize data-ink, minimize chart-junk. Mono axis labels with tabular nums, hairline gridlines, legend suppressed in favor of direct end-labels.

### editorial.css — `.dv-ed-*`
**Series palette:** oxblood ramp with warm-gray context (`#7A1F1F`, `#A04040`, `#4D0F0F`, `#C06868`, `#6B6B6B`, `#9C9C9C`, `#2A0808`, `#3D3D3D`)
**Philosophy:** Data as narrative. Serif labels, single-series preferred, annotated line as the signature chart. Italic pull-quote annotations.

### dark-premium.css — `.dv-dp-*`
**Series palette:** gold ramp on deep ground (`#D4AF37`, `#E8C96B`, `#8A7020`, `#F2DE9E`, `#71717A`, `#A1A1AA`, `#4A3A10`, `#52525B`)
**Philosophy:** High contrast, luminous lines. Hero series gets a soft glow filter. Spacious framing, small-caps metadata, tooltips on lifted surfaces.

### warm-approachable.css — `.dv-wa-*`
**Series palette:** terracotta ramp (`#B04A2C`, `#D27253`, `#7A2F18`, `#E89C7D`, `#7A6250`, `#A89478`, `#4A1A08`, `#5A4332`)
**Philosophy:** Soft and rounded. Line caps and joins rounded by default, humanist sans labels, warm-tinted soft-shadow tooltips. No hard borders.

### vibrant-playful.css — `.dv-vp-*`
**Series palette:** direct `color.palette` map (`#C44A2E`, `#1F6B88`, `#9C6014`, `#553E5A`, `#2A7B60`) + lightened tonal extensions for series 6–8 (`#D97259`, `#4F8FA8`, `#B78439`)
**Philosophy:** Multi-series native. Each series gets its own palette hue. Rounded bars, friendly tooltips, Satoshi medium weight everywhere.

### brutalist.css — `.dv-br-*`
**Series palette:** accent plus stark signal colors (`#000000`, `#B84400`, `#595959`, `#A88100`, `#007A3D`, `#D10000`, `#1A1A1A`, `#999999`)
**Philosophy:** Raw, blocky, monospace. Full visible 1px grid, bracketed `[series]` legend, hover-inversion tooltips, zero radius, zero shadow. ASCII sparkline helper included.

### bold-distinctive.css — `.dv-bd-*`
**Series palette:** hot red ramp + stark neutrals (`#C72500`, `#E85539`, `#8C1A00`, `#F38570`, `#0A0A0B`, `#5F5F5F`, `#4D0E00`, `#2B2B2B`)
**Philosophy:** Editorial-scale numerics. Hero number at 120px, display-scale anchor ticks, italic PP-Editorial-New annotations, stamp-style tooltips. One chart, one loud idea.

---

## Usage pattern

```html
<link rel="stylesheet" href="/assets/charts/vibrant-playful.css">

<figure class="dv-vp-root">
  <h3 class="dv-vp-title">Weekly active users</h3>
  <p class="dv-vp-subtitle">Last 12 weeks, by cohort</p>

  <div class="dv-vp-legend">
    <span class="dv-vp-legend-item">
      <span class="dv-vp-legend-dot" style="background: var(--chart-series-1)"></span>
      Product
    </span>
    <!-- repeat per series -->
  </div>

  <svg><!-- Chart rendered by Chart.js / Recharts / D3, pulling CSS vars --></svg>

  <div class="dv-vp-tooltip" role="tooltip">Tue, Apr 9 — 12,400</div>
</figure>
```

Swap the stylesheet and the `.dv-*-root` prefix to re-skin the whole dashboard. All tokens come from `assets/direction-starters/{direction}.md`.
