---
meta:
  project: "Vibrant-playful starter"
  created: "2026-04-17"
  version: 1

direction: vibrant-playful

color:
  bg_primary:   "#FFFBF5"
  bg_secondary: "#F5EFE1"
  bg_tertiary:  "#E8DFC9"

  fg_primary:   "#1A1A1A"
  fg_secondary: "#3F3F3F"
  fg_muted:     "#6A6A6A"

  accent:       "#C44A2E"
  accent_hover: "#A03920"

  border:       "#E8DFC9"
  border_strong: "#D1BF9E"

  success:      "#2A7B60"
  warning:      "#B07420"
  danger:       "#B03245"

  palette:
    - "#C44A2E"
    - "#1F6B88"
    - "#9C6014"
    - "#553E5A"
    - "#2A7B60"

  dark:
    bg_primary:   "#1A1613"
    bg_secondary: "#262019"
    bg_tertiary:  "#332C22"
    fg_primary:   "#FFFBF5"
    fg_secondary: "#D4CCBF"
    fg_muted:     "#998E7D"
    accent:       "#FF6B50"
    accent_hover: "#FF8970"
    border:       "#332C22"
    border_strong: "#4A4030"
    success:      "#5FBA9C"
    warning:      "#E09940"
    danger:       "#E85566"
    palette:
      - "#FF6B50"
      - "#4A9BC4"
      - "#E09940"
      - "#9578A1"
      - "#5FBA9C"

type:
  display: "Satoshi"
  body: "Satoshi"
  mono: "JetBrains Mono"

  weights: [400, 500, 700]

  scale:
    xs:   12
    sm:   14
    base: 16
    lg:   22
    xl:   32
    xxl:  48
    display: 68

  leading:
    body: 1.55
    ui: 1.4
    display: 1.08

  tracking:
    display: -0.02
    body: 0
    caps: 0.06

spacing:
  base_unit: 4
  radius:
    sm: 6
    md: 12
    lg: 20
    full: 9999

motion:
  duration_fast: 200
  duration_base: 280
  duration_slow: 400
  easing: "cubic-bezier(0.34, 1.1, 0.64, 1)"
  easing_dramatic: "cubic-bezier(0.19, 1, 0.22, 1)"
  reduced_motion: true

personality:
  - "section dividers, tag pills, and chart series pull colors from palette in order — each section or category gets its own hue"
  - "soft-rounded corners (12px default, 20px on large cards) without going pill-shaped except on tags"
  - "medium weight (500) dominates — friendly and confident, never childish"

overrides:
  web:
    container_max: 1200
    grid_columns: 12
  document:
    page_margin_mm: 24
    embed_fallbacks: true
  slides:
    aspect_ratio: "16:9"
    dark_deck: false
    min_body_pt: 24

---
