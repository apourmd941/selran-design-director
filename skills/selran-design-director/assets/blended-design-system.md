---
# ═══════════════════════════════════════════════════════════════
#  DESIGN SYSTEM — blended candidate
#  Produced by `selran reference blend ref_01HW0XVZ8NM3K9F7B6Q4D2RTYE
#                                       ref_01HW0XW2P0KJTC4M8N7Y5ZWQAB
#                                       --brief "calm and considered, for a finance product"`
#
#  This is a SUGGESTION. Review before adopting. The `derived_from`
#  block below records exactly which tokens came from which reference,
#  which were dropped because of anti-patterns, and which decisions
#  the blender made along the way.
# ═══════════════════════════════════════════════════════════════

meta:
  project: "Calm Capital"
  created: "2026-04-26"
  version: 1
  blend_source: "selran reference blend"

direction: technical-minimal

color:
  bg_primary:   "#FAFAF9"
  bg_secondary: "#F4F4F4"
  bg_tertiary:  "#EAEAEA"

  fg_primary:   "#191919"
  fg_secondary: "#4A4A4A"
  fg_muted:     "#7A7A7A"

  # Accent: substituted by the blender. Both references suggested purple
  # accents (#5E6AD2 and #635BFF respectively), both flagged PURPLE_DOMINANT.
  # Replaced with a single saturated accent from technical-minimal's palette.
  accent:       "#0A7A5C"
  accent_hover: "#065F47"

  border:       "#E4E4E7"
  border_strong: "#D4D4D8"

  success:      "#15803D"
  warning:      "#B45309"
  danger:       "#B91C1C"

  palette: []

  dark:
    bg_primary:   "#0A0A0B"
    bg_secondary: "#141416"
    fg_primary:   "#F4F4F5"
    accent:       "#22C55E"

type:
  # Display + body class: substituted by the blender. Both references used
  # Inter (one for display, the other for body); INTER_DETECTED fired on
  # both. Replaced with General Sans, technical-minimal's preferred
  # humanist substitute.
  display_class: "humanist-sans"
  display_family: "General Sans"
  body_class: "humanist-sans"
  body_family: "General Sans"
  scale_ratio: 1.25
  tracking: "tight"

spacing:
  base_unit: 4
  rhythm: "8px-grid"

radius:
  # Substituted by the blender. Primary reference used 8px-everywhere
  # (RADIUS_DEFAULT_DETECTED). Replaced with a two-tier scheme.
  primary: 10
  secondary: 4
  pill: 9999

motion:
  duration: 200
  easing: "ease-out"

personality:
  voice: "calm, direct, finance-professional"
  tone: "considered"

derived_from:
  brief: "calm and considered, for a finance product"
  target_direction: "technical-minimal"
  references:
    - id: "ref_01HW0XVZ8NM3K9F7B6Q4D2RTYE"
      url: "https://example.com/notebook-app"
      direction_score: 0.71
      tokens_kept:
        - "color.bg_primary"
        - "color.fg_primary"
        - "spacing.base_unit"
        - "spacing.rhythm"
        - "type.scale_ratio"
        - "type.tracking"
        - "motion.duration"
        - "motion.easing"
      tokens_dropped:
        - "color.accent (PURPLE_DOMINANT)"
        - "type.display_family (INTER_DETECTED)"
        - "type.body_family (INTER_DETECTED)"
        - "radius.primary (RADIUS_DEFAULT_DETECTED)"
    - id: "ref_01HW0XW2P0KJTC4M8N7Y5ZWQAB"
      url: "https://example.com/payments-marketing"
      direction_score: 0.62
      tokens_kept:
        - "motion.duration"
      tokens_dropped:
        - "color.accent (PURPLE_DOMINANT)"
        - "type.body_family (INTER_DETECTED)"
        - "color.bg_primary (consensus override)"
  decisions:
    - "routed brief 'calm and considered, for a finance product' → technical-minimal direction"
    - "blended notebook-app's spacing rhythm with payments-marketing's motion timing (consensus 4px base unit, 200ms duration)"
    - "rejected both references' Inter and purple-pink accents as anti-patterns"
    - "split radius into a two-tier scheme (10 primary / 4 secondary) per RADIUS_DEFAULT_DETECTED substitution"
    - "kept notebook-app's bright background; payments-marketing's slightly tinted background was outvoted by the consensus rule"
---

# Design intent

This design system was blended from two references — a notebook-style application and a payments-product marketing page — under the brief "calm and considered, for a finance product." The chosen direction is `technical-minimal`: both references scored highest there, and the brief reinforces it.

The result is a calm interface tuned for finance: bright background, near-black foreground, a single saturated accent (`#0A7A5C` — a saturated green from the technical-minimal palette, chosen because both references' purple accents were flagged as Stripe-imitation anti-patterns), General Sans for both display and body (because both references used Inter, which is on the anti-pattern list), and a two-tier radius scheme (`10` for primary surfaces, `4` for secondary) to break up the rounded-everything default that the primary reference had.

What survived from the references:

- The 4-pixel base unit and 8px-grid rhythm (both references agreed; consensus kept it)
- The `1.25` typographic scale ratio (a major-third — both references used it; consensus kept it)
- The 200-millisecond ease-out motion timing (averaged across both references' durations, weighted by direction score)
- The bright `#FAFAF9` background and `#191919` foreground from the primary reference (the secondary reference suggested a slightly tinted alternative; consensus rule outvoted it)

What was dropped from the references:

- Both references' purple accents (`#5E6AD2` and `#635BFF`) — `PURPLE_DOMINANT` fired on both. The substitute is a single saturated green from the direction's palette. This is the most visible difference between the references and the blended candidate; if you want a different accent hue, change `color.accent` directly.
- Both references' Inter fonts — `INTER_DETECTED` fired on both. The substitute is General Sans for both display and body. If you want a serif display alongside the General Sans body, change `type.display_family` to (for example) `Fraunces` and the rest follows.
- The primary reference's 8px-everywhere radius — `RADIUS_DEFAULT_DETECTED` fired. The substitute is the two-tier scheme above.

What the brief contributed:

- "Calm and considered" reinforced the technical-minimal direction over the references' slight pull toward dark-premium.
- "Finance product" pushed the foreground darker than the references' default (`#191919` vs. `#222222`) for higher trust contrast and a more conservative read.

This is a candidate, not a verdict. The next step is the Phase 2 tweak loop — open this file in your editor, change anything that doesn't match what you wanted, and the skill will diff your changes against the `derived_from` lineage so subsequent tweaks know what was load-bearing and what was free.

# Components, motion, voice — defaults

The component patterns, motion presets, and voice guidance for technical-minimal apply unchanged. See:

- `references/aesthetic-directions.md` — the technical-minimal direction's full spec
- `references/component-patterns.md` — component defaults
- `references/motion-and-interaction.md` — motion timing and easing patterns
- `references/voice.md` — copy direction (calm, direct, finance-professional)

# Reference handling

This file's `derived_from` block is permanent. Edits you make to the tokens above don't invalidate the block — it's a record of what the blender produced, not a binding contract on what you keep. If you want to re-blend (e.g. with a different brief or a different reference set), run `selran reference blend` again; the new output overwrites this file with a fresh `derived_from` block reflecting the new run.

If you delete a reference (`selran reference delete ref_01HW0XVZ8NM3K9F7B6Q4D2RTYE`), the deletion does NOT invalidate this file. The `derived_from` block remains as a historical record; the reference id is still meaningful as an audit trail even after the underlying summary is deleted from the workspace.
