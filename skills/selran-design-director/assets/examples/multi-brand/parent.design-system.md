---
# Parent brand tokens for a holding company with three product brands.
# Children extend this file and override only what differs (usually accent).
# See multi-brand.md for the inheritance model.

meta:
  project: "Holding Company — Parent"
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
  accent:       "#18181B"   # children override; parent default is neutral near-black
  accent_hover: "#000000"
  border:       "#E4E4E7"
  border_strong: "#D4D4D8"
  success:      "#15803D"
  warning:      "#B45309"
  danger:       "#B91C1C"
  palette: []
  dark:
    bg_primary:   "#0A0A0B"
    bg_secondary: "#151517"
    bg_tertiary:  "#1F1F22"
    fg_primary:   "#F4F4F5"
    fg_secondary: "#A1A1AA"
    fg_muted:     "#71717A"
    border:       "#27272A"
    border_strong: "#3F3F46"

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

# Design intent — Parent

A technical-minimal baseline for the holding company's three product brands.
Each child inherits this shape (scale, spacing, motion, neutrals) and differs
only in accent — so all three read as siblings at a glance while still having
their own identity in the CTA color. Change the type scale here and all three
children update. Change the accent on one child and only that child updates.
