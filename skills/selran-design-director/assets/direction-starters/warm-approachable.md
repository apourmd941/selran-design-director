---
meta:
  project: "Warm-approachable starter"
  created: "2026-04-17"
  version: 1

direction: warm-approachable

color:
  bg_primary:   "#FAF3E7"
  bg_secondary: "#F3E8D3"
  bg_tertiary:  "#EAD9BA"

  fg_primary:   "#2D1B0F"
  fg_secondary: "#5A4332"
  fg_muted:     "#7A6250"

  accent:       "#B04A2C"
  accent_hover: "#8E381A"

  border:       "#E0CEB0"
  border_strong: "#C9B18C"

  success:      "#6B8E3A"
  warning:      "#B07420"
  danger:       "#B94A2A"

  dark:
    bg_primary:   "#1F1612"
    bg_secondary: "#2B1F18"
    bg_tertiary:  "#3A2B21"
    fg_primary:   "#F5E8D3"
    fg_secondary: "#D4C2A5"
    fg_muted:     "#A89478"
    accent:       "#E87040"
    accent_hover: "#F48856"
    border:       "#3A2B21"
    border_strong: "#4F3D2E"

type:
  display: "Fraunces"
  body: "Commissioner"
  mono: ""

  weights: [400, 500, 700]

  scale:
    xs:   13
    sm:   15
    base: 17
    lg:   22
    xl:   30
    xxl:  44
    display: 64

  leading:
    body: 1.65
    ui: 1.45
    display: 1.15

  tracking:
    display: -0.01
    body: 0
    caps: 0.06

spacing:
  base_unit: 4
  radius:
    sm: 8
    md: 16
    lg: 24
    full: 9999

motion:
  duration_fast: 200
  duration_base: 300
  duration_slow: 450
  easing: "cubic-bezier(0.4, 0, 0.2, 1)"
  easing_dramatic: "cubic-bezier(0.19, 1, 0.22, 1)"
  reduced_motion: true

personality:
  - "rounded corners throughout — 16px cards feel like paper, not glass"
  - "italic serif on a single phrase per section as a warm accent"
  - "warm-tinted soft shadows instead of hairline borders on cards"

overrides:
  web:
    container_max: 1120
    grid_columns: 12
  document:
    page_margin_mm: 28
    embed_fallbacks: true
  slides:
    aspect_ratio: "16:9"
    dark_deck: false
    min_body_pt: 24

---
