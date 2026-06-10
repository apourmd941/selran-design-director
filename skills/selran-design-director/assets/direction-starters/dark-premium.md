---
meta:
  project: "Dark-premium starter"
  created: "2026-04-17"
  version: 1

direction: dark-premium

color:
  bg_primary:   "#0A0A0B"
  bg_secondary: "#151517"
  bg_tertiary:  "#1F1F22"

  fg_primary:   "#F4F4F5"
  fg_secondary: "#A1A1AA"
  fg_muted:     "#71717A"

  accent:       "#D4AF37"
  accent_hover: "#B89428"

  border:       "#27272A"
  border_strong: "#3F3F46"

  success:      "#34D399"
  warning:      "#FBBF24"
  danger:       "#F87171"

type:
  display: "Tiempos Headline"
  body: "Söhne"
  mono: "Söhne Mono"

  weights: [400, 500, 600]

  scale:
    xs:   12
    sm:   14
    base: 16
    lg:   20
    xl:   30
    xxl:  48
    display: 72

  leading:
    body: 1.6
    ui: 1.4
    display: 1.05

  tracking:
    display: -0.02
    body: 0
    caps: 0.1

spacing:
  base_unit: 4
  radius:
    sm: 4
    md: 6
    lg: 10
    full: 9999

motion:
  duration_fast: 200
  duration_base: 350
  duration_slow: 500
  easing: "cubic-bezier(0.19, 1, 0.22, 1)"
  easing_dramatic: "cubic-bezier(0.19, 1, 0.22, 1)"
  reduced_motion: true

personality:
  - "one hero moment with soft accent glow per page — a product, a stat, a chart"
  - "thin accent underlines beneath headings, 1px in the accent color"
  - "layered dark-on-dark cards — secondary bg lifted one step above primary"

overrides:
  web:
    container_max: 1280
    grid_columns: 12
  document:
    page_margin_mm: 22
    embed_fallbacks: true
  slides:
    aspect_ratio: "16:9"
    dark_deck: true
    min_body_pt: 24

---
