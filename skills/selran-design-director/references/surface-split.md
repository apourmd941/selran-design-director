# Surface Split — Marketing vs. Product

Most real brands don't have one voice. They have **two**: a louder marketing voice for landing pages, launch campaigns, social content, and pricing — and a quieter product voice for the app UI, admin dashboards, and day-to-day tooling. Using the same tokens for both makes the marketing site feel too restrained *or* makes the product feel too loud. The surface-split solves this.

---

## The model

A single `design-system.md` file defines **base tokens**. Two optional override blocks sit beneath `overrides:`:

- `overrides.marketing` — louder surface for landing pages, campaigns, launch pages, pricing pages, social OG images
- `overrides.product` — quieter surface for the app UI, dashboards, settings, admin tools

Both override blocks are **partial** — they only specify what changes from the base. Anything not overridden inherits from the base tokens. This keeps the brand coherent while letting each surface calibrate its loudness.

## Schema

```yaml
overrides:
  # ... existing web/document/slides overrides ...
  marketing:
    type:
      scale:
        xxl: 56          # bigger than base
        display: 72      # much bigger than base
      tracking:
        display: -0.03   # tighter for impact
    spacing:
      radius:
        md: 10           # slightly rounder than base
    motion:
      duration_slow: 500 # allow longer reveals on marketing
    personality:
      # appends to base personality
      - "generous vertical rhythm — 160–200px section padding on desktop"
      - "one saturated gradient per page allowed (hero background); banned elsewhere"
  product:
    type:
      scale:
        xxl: 32          # smaller than base
        display: 40      # noticeably smaller
    spacing:
      radius:
        md: 6            # tighter than base
    motion:
      duration_base: 150 # snappier in-app
    personality:
      - "never use display type in the app — xl is the ceiling"
      - "hover states are color-only, never scale or shadow changes"
```

Only override what genuinely differs. Don't duplicate the base.

## The loudness spectrum

Every direction has a natural spot on the loudness spectrum. Marketing surface pulls louder; product surface pulls quieter:

| Direction | Base position | Marketing pull | Product pull |
|---|---|---|---|
| technical-minimal | quiet | → slightly bigger type, more whitespace | → even tighter density, snappier motion |
| editorial | quiet-medium | → bigger display serif, dramatic leading | → minimal display; body-first |
| dark-premium | quiet | → one allowed hero glow per page | → no glow; border-only emphasis |
| warm-approachable | medium | → full palette in hero, friendlier copy | → just accent; professional copy |
| vibrant-playful | loud | → full palette freely, micro-bounce motion | → one hue per screen; motion restrained |
| brutalist | loud | → hero-scale type, full hover-inversion | → screen-scale type only; instant swaps |
| bold-distinctive | medium-loud | → magazine-scale numerals | → editorial restraint; smaller numerals |

## When to create a split

You need a split when **both** of these are true:
1. The project spans marketing and product surfaces (landing page + app, not just one)
2. The same tokens on both would feel wrong — marketing feels restrained, or product feels shouty

If only one is true, stay single-token. Don't split preemptively.

**Signals you need the split:**
- User asks for "a landing page *and* the app UI"
- User says "the marketing site should feel bigger than the app"
- User references a brand where you can see the split (Stripe's marketing vs. Dashboard, Linear's site vs. app, Notion.so vs. Notion itself)
- Multi-artifact project: pitch deck + product UI, press kit + product UI

**Signals you don't need the split:**
- One-off artifact (just a landing page, just a dashboard)
- User explicitly wants "the same energy everywhere"
- Internal tool with no marketing presence

## How to apply

During Phase 4 Build:

1. Detect which surface the current artifact belongs to:
   - Landing page, marketing email, launch announcement, pricing page, OG image → **marketing**
   - Dashboard, admin, settings, in-app modals, onboarding flows → **product**
   - Pitch deck → **marketing** (external audience) unless specifically internal
   - Investor report, whitepaper → **product** (restrained) by default, **marketing** if the brief says "make it feel like a launch"

2. Compose the effective tokens: start with base, then shallow-merge the relevant override block on top.

3. Render the artifact using the composed tokens. Components and shells consume the composed values through the host CSS variables — no code changes needed per surface.

4. If a single artifact crosses surfaces (rare — e.g., a product tour that starts on the landing page and ends in the app), hand-pick per-section overrides. Don't mix the two override blocks on one artifact.

## When to override personality

The `personality` block is additive, not replacing. Marketing personality gestures *add* to the base; product personality gestures *add* to the base. So a full stack of personality gestures at render time is: base + surface-specific. This means you can keep brand-level gestures (e.g., "tabular numerals on all numeric data") in the base and add surface-specific gestures (e.g., "one saturated gradient per page allowed") in the override.

Don't contradict the base. If the base says "1px hairline borders instead of shadows" and marketing says "subtle shadows on cards," you've got tension — resolve it by picking one globally, or scoping the hairline rule to product only.

## Silent validation

After applying overrides, run the same anti-patterns and a11y audits as always. Marketing's bigger type still needs to pass WCAG. Marketing's one-allowed-gradient still needs to avoid the purple-to-pink default. Overrides don't exempt you from the rules — they scale the loudness within the rules.

## Anti-patterns

- **Don't diverge visually.** Marketing and product should feel like the same brand. If the marketing site is warm-approachable and the product is technical-minimal, you're not in split territory — you're in multi-brand territory (see `multi-brand.md`).
- **Don't over-split.** If your override block has more keys than your base, you've built a second design system pretending to be an override.
- **Don't use marketing type sizes in product screens.** Users trust a calm product; flashy type in-app reads as unpolished.
- **Don't motion-blast the product.** Product motion should be snappier (shorter durations), not more dramatic.
- **Don't skip the split when it's clearly needed.** A landing page that feels as quiet as the app underwhelms; an app that feels as loud as the landing page overwhelms.

## Example compositions

### Stripe-adjacent (technical-minimal + surface split)

- **Base:** technical-minimal default — quiet, hairlines, 200ms motion
- **Marketing:** display type at 72px, generous section padding, one allowed purple hero gradient (explicit exception to anti-patterns because the user asked for Stripe-adjacent)
- **Product:** display type capped at xl (32px), 150ms motion, never the purple gradient

### Warm brand + restrained product (warm-approachable + surface split)

- **Base:** warm-approachable default — cream bg, coral accent, pill chrome
- **Marketing:** full 5-hue palette in hero, friendly micro-bounce on CTAs, "You're going to love this" copy energy
- **Product:** single accent only (no palette rotation), instant hover states, "Save" / "Cancel" copy energy

### Editorial publication + reading app (editorial + surface split)

- **Base:** editorial default — serif display, hairlines, Roman numerals
- **Marketing:** hero-scale serif (display: 96px), drop caps on landing copy, generous leading
- **Product:** display capped at xl, drop caps only in article body (not UI), quieter serif

## See also

- `multi-brand.md` — for when you have genuinely different brands that share 80% of a system (vs. one brand with two surfaces)
- `variants.md` — for when you need 2–3 versions of the same artifact (vs. one artifact per surface)
- `design-system-schema.md` — canonical schema including `overrides.marketing` and `overrides.product` blocks
