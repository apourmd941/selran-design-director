# Surface-split example — one brand, marketing vs. product

A single `design-system.md` with two override blocks:

- `overrides.marketing` — louder (bigger type, warmer radius, longer motion, allowed gradient)
- `overrides.product` — quieter (tighter type, tighter radius, snapper motion, no gradient)

## When this pattern fits

You have **one brand** that spans **both** a marketing surface (landing page, launch content, pricing) **and** a product surface (dashboard, admin, in-app UI). Using the same tokens on both feels wrong — marketing reads too restrained or product reads too shouty. The split lets each surface calibrate its loudness without fragmenting the brand.

## When it doesn't

- **Just one surface** (landing page OR app, not both) → single-token, no split
- **Two genuinely different brands** (parent company with sibling products) → `multi-brand.md`
- **Exploring 2–3 hero options** → `variants.md`

## How resolution works at build time

1. Detect surface: landing page → marketing, dashboard → product
2. Start with base tokens
3. Shallow-merge the relevant override block on top (scalar/object fields: surface wins; `personality` arrays ADD to base)
4. Render with the merged tokens

## See also

- `references/surface-split.md` — full spec with the loudness-per-direction table
- `assets/examples/multi-brand/` — the sibling-brands alternative
