---
meta:
  project: "Technical-minimal starter"
  created: "2026-04-17"
  version: 1

direction: technical-minimal

color:
  bg_primary:   "#FAFAF9"
  bg_secondary: "#F4F4F5"
  bg_tertiary:  "#E4E4E7"

  fg_primary:   "#18181B"
  fg_secondary: "#52525B"
  fg_muted:     "#71717A"

  accent:       "#0A7A5C"
  accent_hover: "#065F47"

  border:       "#E4E4E7"
  border_strong: "#D4D4D8"

  success:      "#15803D"
  warning:      "#B45309"
  danger:       "#B91C1C"

  dark:
    bg_primary:   "#0A0A0B"
    bg_secondary: "#151517"
    bg_tertiary:  "#1F1F22"
    fg_primary:   "#F4F4F5"
    fg_secondary: "#A1A1AA"
    fg_muted:     "#71717A"
    accent:       "#10B981"
    accent_hover: "#34D399"
    border:       "#27272A"
    border_strong: "#3F3F46"

type:
  display: "General Sans"
  body: "General Sans"
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
  - "uppercase section labels in mono, 12px, +0.08em tracking"

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

---
