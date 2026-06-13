---
meta:
  project: "Bold-distinctive starter"
  created: "2026-04-17"
  version: 1

direction: bold-distinctive

color:
  bg_primary:   "#F5F0E8"
  bg_secondary: "#ECE4D4"
  bg_tertiary:  "#0A0A0B"

  fg_primary:   "#0A0A0B"
  fg_secondary: "#2B2B2B"
  fg_muted:     "#5F5F5F"

  accent:       "#C72500"
  accent_hover: "#991C00"

  border:       "#0A0A0B"
  border_strong: "#0A0A0B"

  success:      "#0A6A3C"
  warning:      "#B85C00"
  danger:       "#C81D0B"

  dark:
    bg_primary:   "#0A0A0B"
    bg_secondary: "#151517"
    bg_tertiary:  "#ECE4D4"
    fg_primary:   "#F5F0E8"
    fg_secondary: "#D4CEB8"
    fg_muted:     "#9F9880"
    accent:       "#FF5530"
    accent_hover: "#FF7755"
    border:       "#F5F0E8"
    border_strong: "#F5F0E8"
    success:      "#22C55E"
    warning:      "#F59E0B"
    danger:       "#EF4444"

type:
  display: "PP Editorial New"
  body: "General Sans"
  mono: ""

  weights: [400, 500, 900]

  scale:
    xs:   13
    sm:   15
    base: 17
    lg:   24
    xl:   40
    xxl:  72
    display: 120

  leading:
    body: 1.5
    ui: 1.3
    display: 0.95

  tracking:
    display: -0.03
    body: 0
    caps: 0.1

spacing:
  base_unit: 4
  radius:
    sm: 0
    md: 0
    lg: 0
    full: 9999

motion:
  duration_fast: 150
  duration_base: 250
  duration_slow: 500
  easing: "cubic-bezier(0.2, 0, 0, 1)"
  easing_dramatic: "cubic-bezier(0.19, 1, 0.22, 1)"
  reduced_motion: true

personality:
  - "display type at 120px+ used as layout architecture, sometimes bleeding off the edge"
  - "large numeric section markers (01, 02, 03) styled as display elements"
  - "one staggered reveal per page on hero, nothing else animates without reason"

overrides:
  web:
    container_max: 1440
    grid_columns: 12
  document:
    page_margin_mm: 20
    embed_fallbacks: true
  slides:
    aspect_ratio: "16:9"
    dark_deck: false
    min_body_pt: 28

---
