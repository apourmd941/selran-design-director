---
# ═══════════════════════════════════════════════════════════════
#  DESIGN SYSTEM — tokens (source of truth for values)
#  Edit values here to change the design exactly.
#  Or edit the prose below and ask to regenerate these from intent.
# ═══════════════════════════════════════════════════════════════

# --- Inheritance (OPTIONAL) -------------------------------------
# When this design system is a child of a parent brand, declare the
# parent file here. The skill will shallow-merge this file on top of
# the parent — scalar/object fields: child wins; array fields: child
# replaces. Only include fields that differ from the parent below.
# Keep inheritance one level deep (parent → child). See multi-brand.md.
# Omit `extends` entirely if this file is standalone.
#
# extends: "parent.design-system.md"

meta:
  project: "Untitled"
  created: "2026-04-17"
  version: 1

# --- Aesthetic direction ---------------------------------------
# One of: editorial | technical-minimal | bold-distinctive
#         dark-premium | warm-approachable | vibrant-playful | brutalist
# This drives defaults below. Override any specific token freely.
#
# FUSION FORM (optional, Phase 5+). Replace the string with an object to
# borrow axes across starters. At most 2 axes borrowed; 3+ triggers a flag.
# See references/direction-fusion.md for pre-blessed pairings and rules.
#   direction:
#     base: technical-minimal
#     borrows:
#       type.display: editorial        # serif display from editorial
#       motion: warm-approachable      # softer motion from warm
direction: technical-minimal

# --- Boldness (OPTIONAL) ---------------------------------------
# How loud the direction plays: restrained | confident (default) | maximal.
# A single dial that scales type contrast, accent saturation/usage, motion,
# spatial drama, and background treatment together — WITHOUT touching the
# accessibility floors (WCAG AA contrast, hit targets, reduced-motion) or
# the anti-slop rules (no Inter even at maximal). "experimental" = maximal +
# avant-garde signature gestures (maximalist / retro-futurist / art-deco /
# brutalist-raw), still token-driven and a11y-audited. See boldness.md.
# Omit for confident.
boldness: confident

# --- Color ------------------------------------------------------
color:
  # Background surfaces (layered: primary is furthest back)
  bg_primary:   "#FAFAF9"   # main page background
  bg_secondary: "#F4F4F5"   # cards, raised surfaces
  bg_tertiary:  "#E4E4E7"   # input fields, deep inset

  # Foreground (text)
  fg_primary:   "#18181B"   # body text, headings
  fg_secondary: "#52525B"   # secondary text, captions
  fg_muted:     "#71717A"   # tertiary, metadata, placeholders (AA-large compliant at 4.63:1 on bg_primary)

  # Accent — the one color that does work
  accent:       "#0A7A5C"   # CTAs, focus, highlights
  accent_hover: "#065F47"   # darker on interaction

  # Borders
  border:       "#E4E4E7"   # standard dividers
  border_strong: "#D4D4D8"  # emphasized boundaries

  # Semantic (leave defaults unless direction demands change)
  success:      "#15803D"
  warning:      "#B45309"
  danger:       "#B91C1C"

  # Coordinated palette (OPTIONAL) — 3 to 8 hues for chart series, section color-coding,
  # tag categories, deck section dividers, poster zones. Leave as an empty array [] for
  # directions that want the discipline of a single accent (technical-minimal, brutalist,
  # editorial, dark-premium). vibrant-playful uses it by design; warm-approachable and
  # bold-distinctive can use a 3-hue variant. When present, palette[0] should match
  # accent for continuity. Never mix with a rainbow — these are coordinated families,
  # not random swatches.
  palette: []

  # Dark-mode variant (OPTIONAL) — when present, web-output.md and slide-output.md can
  # consume these values under `prefers-color-scheme: dark` or via a manual toggle.
  # Same keys as the light mode above; only override what needs to change. The primary
  # accent usually stays the same hue but may shift lightness/saturation for contrast.
  # Skip this block when the direction IS dark at top level (dark-premium), or when the
  # artifact is print-only (.docx, .pptx for projector). vibrant-playful's palette also
  # gets a muted dark-mode version under dark.palette.
  dark:
    bg_primary:   "#0A0A0B"
    bg_secondary: "#151517"
    bg_tertiary:  "#1F1F22"
    fg_primary:   "#F4F4F5"
    fg_secondary: "#A1A1AA"
    fg_muted:     "#71717A"
    accent:       "#10B981"   # same accent family, tuned for dark bg contrast
    accent_hover: "#059669"
    border:       "#27272A"
    border_strong: "#3F3F46"

# --- Typography ------------------------------------------------
type:
  # Font families — exact names, as they'd appear in CSS or embed
  display: "General Sans"       # headings, hero type
  body: "General Sans"          # paragraphs, UI
  mono: "JetBrains Mono"        # code, labels, numbers (or empty string to skip)

  # Weight discipline — use ONLY these weights
  weights: [400, 500, 600]

  # Scale (px) — headings + body sizes used in the design
  scale:
    xs:   12
    sm:   14
    base: 16
    lg:   20
    xl:   28
    xxl:  40
    display: 56

  # Line height ratios by size bucket
  leading:
    body: 1.55       # paragraphs
    ui: 1.4          # buttons, labels, dense UI
    display: 1.1     # headings

  # Letter spacing (em units — negative tightens)
  tracking:
    display: -0.02   # tighten big type
    body: 0          # default
    caps: 0.08       # small caps / uppercase labels

# --- Spacing ---------------------------------------------------
spacing:
  base_unit: 4       # px — everything is a multiple of this
  # Derived scale (multiples of base_unit)
  # 1=4px  2=8px  3=12px  4=16px  6=24px  8=32px  12=48px  16=64px
  radius:
    sm: 4            # inputs, small elements
    md: 8            # cards, buttons
    lg: 12           # modals, large surfaces
    full: 9999       # pills, circles

# --- Breakpoints ----------------------------------------------
# Responsive breakpoints in px. Directions tune these: editorial
# runs wider (readability); technical-minimal stays tighter (density);
# vibrant-playful/warm-approachable default to standard mobile-first.
# Consumed by web (@media), mobile-web variant selection, and native
# platforms as size-class hints. See references/breakpoints.md.
breakpoints:
  sm:  640     # phone landscape / small tablet
  md:  768     # tablet portrait
  lg:  1024    # tablet landscape / small laptop
  xl:  1280    # desktop
  "2xl": 1536  # wide desktop

# --- Motion ----------------------------------------------------
motion:
  duration_fast: 150     # ms — hover, button feedback
  duration_base: 200     # ms — most transitions (the default)
  duration_slow: 350     # ms — modals, page-level
  easing: "cubic-bezier(0.2, 0, 0, 1)"   # ease-out (default)
  easing_dramatic: "cubic-bezier(0.19, 1, 0.22, 1)"   # ease-out-expo
  reduced_motion: true   # respect prefers-reduced-motion

# --- Personality -----------------------------------------------
# 2–3 specific gestures this design leans on. Free-form strings.
# These get referenced during execution as style reminders.
personality:
  - "tabular numerals on all numeric data"
  - "1px hairline borders instead of shadows on cards"
  - "uppercase section labels in mono, 12px, +0.08em tracking"

# --- Internationalization (OPTIONAL) ---------------------------
# Declare which scripts/locales this design system needs to support.
# Drives font fallback stacks, RTL mirror behavior, and copy-length
# tolerance at build time. Omit entirely for English-only / LTR-only
# projects. See references/i18n.md and assets/i18n/.
i18n:
  default_locale: "en"
  # Scripts to support, in priority order. Common values: latin,
  # cjk (Chinese/Japanese/Korean), arabic, cyrillic, devanagari,
  # hebrew, thai. Font fallbacks in assets/i18n/font-fallbacks.yaml
  # will be applied for any script listed here that the primary
  # display/body/mono font doesn't cover.
  scripts: ["latin"]
  # If true, layout must support RTL (Arabic, Hebrew). Components
  # use logical properties (inline-start/end) and flip directional
  # icons per assets/i18n/rtl-mirror.yaml.
  rtl: false
  # Copy-length tolerance: maximum expansion factor from English
  # the UI must accommodate. German ≈1.3, Russian ≈1.2, Arabic ≈1.25.
  # Set to the worst case for supported locales. Higher = more room
  # in buttons, labels, nav items. See i18n.md for per-direction
  # overflow strategy (truncate vs wrap vs reflow).
  max_expansion: 1.0

# --- Format-specific hints -------------------------------------
# Optional overrides for specific output types.
# Most projects don't need these — defaults from direction work.
overrides:
  web:
    container_max: 1200   # px — max content width
    grid_columns: 12
  document:
    # For .docx/.pdf — fonts must be OS-available or embed-friendly
    page_margin_mm: 25
    embed_fallbacks: true   # substitute OS-safe fonts when the tokens' fonts don't embed cleanly
  slides:
    # For .pptx/HTML decks
    aspect_ratio: "16:9"
    dark_deck: false        # true = inverted color for slides
    min_body_pt: 24

  # --- Surface split (OPTIONAL) --------------------------------
  # Use when one brand spans both marketing and product surfaces and
  # the same tokens feel wrong on both (marketing feels restrained OR
  # product feels shouty). Only override what genuinely differs —
  # anything not listed inherits from the base tokens above.
  # Personality arrays here ADD to the base personality; they don't
  # replace. See surface-split.md for when to split and which
  # directions need bigger/smaller pulls.
  #
  # marketing:
  #   type:
  #     scale:
  #       xxl: 56           # bigger than base for landing pages
  #       display: 72
  #     tracking:
  #       display: -0.03    # tighter for hero impact
  #   spacing:
  #     radius:
  #       md: 10            # slightly rounder for marketing warmth
  #   motion:
  #     duration_slow: 500  # allow longer reveals on marketing
  #   personality:
  #     - "generous vertical rhythm — 160–200px section padding on desktop"
  #     - "one saturated gradient per page allowed (hero background); banned elsewhere"
  #
  # product:
  #   type:
  #     scale:
  #       xxl: 32           # smaller than base for in-app density
  #       display: 40
  #   spacing:
  #     radius:
  #       md: 6             # tighter for product precision
  #   motion:
  #     duration_base: 150  # snappier in-app
  #   personality:
  #     - "never use display type in the app — xl is the ceiling"
  #     - "hover states are color-only, never scale or shadow changes"

---

# Design intent

*This prose section is optional. Include it when the project spans multiple artifacts (UI + doc, UI + deck) or is substantial (full landing page, long document, full deck). Skip it for trivial one-offs — the YAML above is enough.*

## What this design feels like

A calm, information-dense interface in the lineage of Stripe and Linear. Off-white paper background (`#FAFAF9`) with near-black text — not pure black, which reads as harsh. A single saturated accent (`#0A7A5C` — a deep emerald) does all the work of drawing attention: CTAs, focus rings, the occasional highlight. Everything else is in the cool-neutral zinc gray family so the accent has somewhere to stand out from.

## Typography voice

General Sans throughout — one family, used at 400 for body, 500 for emphasis, 600 for headings. Enough character to not read as generic but restrained enough to stay out of the way. JetBrains Mono appears in small doses: numeric data, inline code, uppercase small-cap labels above section headings. Display type tightens slightly (-0.02em tracking) to read as intentional.

## Spatial logic

Everything on a 4px grid. Cards use hairline 1px borders, not shadows — shadows feel floaty where this aesthetic wants precision. Radius is conservative (4–12px); nothing is pill-shaped unless it's a badge. Generous whitespace between sections; dense packing within sections.

## Motion

Fast (200ms default) and ease-out. No springs, no bounces. Hover states are short color shifts. One "hero moment" per page — usually a staggered fade-up on load — and nothing else animates without reason.

## Three things someone will remember

1. **The numbers line up.** Tabular numerals throughout any data view, so columns of figures have mathematical alignment.
2. **Section labels in mono small-caps.** Every major section begins with a 12px uppercase label in JetBrains Mono, sitting above the heading like a dateline.
3. **Hairlines, not shadows.** The whole interface has a "drawn" quality — thin precise lines separating content instead of soft-shadow elevation.

---

## How to edit this file

**To change values exactly:** edit the YAML above. Claude will honor the new values verbatim when building.

**To change the design's intent:** edit the prose in this second half, then tell the skill "regenerate tokens from the new intent." Claude reads your prose and updates the YAML to match.

**To swap the direction entirely:** change `direction:` at the top and ask for regeneration. The other fields will update to that direction's defaults (but any values you changed manually are kept — manual edits win).

**To fork a variant:** duplicate this file, rename the project in `meta:`, and edit. Multiple design-system files can coexist in a project.
