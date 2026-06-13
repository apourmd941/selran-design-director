---
meta:
  project: "Brutalist starter"
  created: "2026-04-17"
  version: 1

direction: brutalist

color:
  bg_primary:   "#FFFFFF"
  bg_secondary: "#F0F0F0"
  bg_tertiary:  "#000000"

  fg_primary:   "#000000"
  fg_secondary: "#1A1A1A"
  fg_muted:     "#595959"

  accent:       "#B84400"
  accent_hover: "#000000"

  border:       "#000000"
  border_strong: "#000000"

  success:      "#007A3D"
  warning:      "#A88100"
  danger:       "#D10000"

  dark:
    bg_primary:   "#000000"
    bg_secondary: "#111111"
    bg_tertiary:  "#FFFFFF"
    fg_primary:   "#FFFFFF"
    fg_secondary: "#DDDDDD"
    fg_muted:     "#999999"
    accent:       "#FF6B00"
    accent_hover: "#FFFFFF"
    border:       "#FFFFFF"
    border_strong: "#FFFFFF"

type:
  display: "Space Mono"
  body: "Space Mono"
  mono: "Space Mono"

  weights: [400, 700]

  scale:
    xs:   12
    sm:   14
    base: 16
    lg:   20
    xl:   28
    xxl:  40
    display: 56

  leading:
    body: 1.5
    ui: 1.3
    display: 1.1

  tracking:
    display: 0
    body: 0
    caps: 0.05

spacing:
  base_unit: 4
  radius:
    sm: 0
    md: 0
    lg: 0
    full: 0

motion:
  duration_fast: 0
  duration_base: 0
  duration_slow: 0
  easing: "linear"
  easing_dramatic: "linear"
  reduced_motion: true

personality:
  - "hover-inversion on buttons — bg becomes fg, fg becomes bg, no transition"
  - "2px solid #000000 borders on every container, no shadows anywhere"
  - "file-size / word-count / timestamp metadata displayed prominently in UI"

overrides:
  web:
    container_max: 1280
    grid_columns: 12
  document:
    page_margin_mm: 20
    embed_fallbacks: true
  slides:
    aspect_ratio: "16:9"
    dark_deck: false
    min_body_pt: 24

---
