---
meta:
  project: "Editorial starter"
  created: "2026-04-17"
  version: 1

direction: editorial

color:
  bg_primary:   "#F5F1EA"
  bg_secondary: "#EEE8DC"
  bg_tertiary:  "#E3DBCA"

  fg_primary:   "#1A1A1A"
  fg_secondary: "#3D3D3D"
  fg_muted:     "#6B6B6B"

  accent:       "#7A1F1F"
  accent_hover: "#5C1717"

  border:       "#D6CFBE"
  border_strong: "#B8AE96"

  success:      "#3F6B3A"
  warning:      "#9C6B1F"
  danger:       "#7A1F1F"

  dark:
    bg_primary:   "#14110D"
    bg_secondary: "#1D1A15"
    bg_tertiary:  "#2A2620"
    fg_primary:   "#F5F1EA"
    fg_secondary: "#C4BDAC"
    fg_muted:     "#8D8775"
    accent:       "#D85E5E"
    accent_hover: "#EA7A7A"
    border:       "#2A2620"
    border_strong: "#443D31"
    success:      "#6BBF7A"
    warning:      "#D4A04A"
    danger:       "#E07474"

type:
  display: "Fraunces"
  body: "Söhne"
  mono: ""

  weights: [400, 500, 600]

  scale:
    xs:   13
    sm:   15
    base: 17
    lg:   22
    xl:   32
    xxl:  48
    display: 72

  leading:
    body: 1.65
    ui: 1.4
    display: 1.1

  tracking:
    display: -0.015
    body: 0
    caps: 0.12

spacing:
  base_unit: 4
  radius:
    sm: 2
    md: 4
    lg: 6
    full: 9999

motion:
  duration_fast: 180
  duration_base: 250
  duration_slow: 400
  easing: "cubic-bezier(0.2, 0, 0, 1)"
  easing_dramatic: "cubic-bezier(0.19, 1, 0.22, 1)"
  reduced_motion: true

personality:
  - "thin rule lines between sections, 1px #D6CFBE"
  - "small-caps byline metadata beneath headings in tracked sans"
  - "drop caps acceptable for long-form; italic treated as a real typographic choice"

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
    min_body_pt: 22

---
