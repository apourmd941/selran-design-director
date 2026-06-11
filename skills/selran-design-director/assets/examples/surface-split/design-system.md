---
# Surface-split example — one brand, two voices.
# Base tokens are quiet (technical-minimal default).
# overrides.marketing scales up for landing pages / launch content.
# overrides.product scales down for in-app density.

meta:
  project: "Stripe-adjacent brand"
  created: "2026-04-21"
  version: 1

direction: technical-minimal

color:
  bg_primary:   "#FAFAF9"
  bg_secondary: "#F4F4F5"
  bg_tertiary:  "#E4E4E7"
  fg_primary:   "#18181B"
  fg_secondary: "#52525B"
  fg_muted:     "#71717A"
  accent:       "#635BFF"   # indigo — stripe-adjacent
  accent_hover: "#4F46E5"
  border:       "#E4E4E7"
  border_strong: "#D4D4D8"
  success:      "#15803D"
  warning:      "#B45309"
  danger:       "#B91C1C"
  palette: []

type:
  display: "Inter Tight"
  body: "Inter Tight"
  mono: "JetBrains Mono"
  weights: [400, 500, 600]
  scale:
    xs:   12
    sm:   14
    base: 16
    lg:   20
    xl:   28
    xxl:  40
    display: 56
  leading:
    body: 1.55
    ui: 1.4
    display: 1.1
  tracking:
    display: -0.02
    body: 0
    caps: 0.08

spacing:
  base_unit: 4
  radius:
    sm: 4
    md: 8
    lg: 12
    full: 9999

motion:
  duration_fast: 150
  duration_base: 200
  duration_slow: 350
  easing: "cubic-bezier(0.2, 0, 0, 1)"
  easing_dramatic: "cubic-bezier(0.19, 1, 0.22, 1)"
  reduced_motion: true

personality:
  - "tabular numerals on all numeric data"
  - "1px hairline borders instead of shadows on cards"

overrides:
  web:
    container_max: 1200
    grid_columns: 12
  document:
    page_margin_mm: 25
    embed_fallbacks: true
  slides:
    aspect_ratio: "16:9"
    dark_deck: false
    min_body_pt: 24

  # ── Marketing surface — louder ────────────────────────────────
  marketing:
    type:
      scale:
        xxl: 56
        display: 88          # hero-scale on landing pages
      tracking:
        display: -0.03       # tighter for impact
    spacing:
      radius:
        md: 10               # slightly rounder for warmth
    motion:
      duration_slow: 500     # allow longer hero reveals
    personality:
      - "generous vertical rhythm — 160–200px section padding on desktop"
      - "one saturated gradient per page allowed (hero background); banned elsewhere"

  # ── Product surface — quieter ─────────────────────────────────
  product:
    type:
      scale:
        xl: 24               # dashboards don't need xl at 28
        xxl: 32              # cap before display
        display: 40          # display in-app only for empty-state hero text
    spacing:
      radius:
        md: 6                # tighter for precision
    motion:
      duration_base: 150     # snappier in-app
    personality:
      - "never use display type in the app — xl is the ceiling for dense views"
      - "hover states are color-only, never scale or shadow changes"

---

# Design intent

A single brand that ships both a marketing site and a product app. The base
tokens are set at the product's natural density — quiet, hairline borders,
fast motion. The marketing overrides scale up for landing pages (bigger hero
type, one allowed gradient, longer reveals). The product overrides tighten
everything further for in-app density.

## How the effective tokens resolve

At build time, the skill detects the surface:

- Building a landing page, launch page, pricing page, OG image → merge `overrides.marketing` on top of base
- Building a dashboard, settings screen, admin tool, in-app modal → merge `overrides.product` on top of base
- Building a doc or deck → base tokens unless the brief says "make it feel like a launch" (then marketing)

## Personality is additive

Both surface blocks' `personality` arrays **add** to the base personality (they don't
replace). So a landing page gets all four gestures: two base + two marketing. A
dashboard gets four: two base + two product. This is the one place the skill does
merge arrays — deliberately, because gestures accumulate naturally.

## See also

- `references/surface-split.md` — full model, loudness spectrum, when to split
- `references/multi-brand.md` — for siblings (different brands), not one brand with two surfaces
