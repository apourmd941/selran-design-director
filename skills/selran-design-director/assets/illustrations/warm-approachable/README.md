# warm-approachable — illustration library

Soft, rounded, organic, human. Peach cream backgrounds, warm charcoal line work with rounded stroke caps, terracotta accents, desaturated sage and dusty blush as supporting pastels. Think Calm, Notion marketing, Headspace (restrained), boutique hotel art direction.

**Do NOT ship Corporate Memphis illustrations (faceless figures with wavy limbs) under this direction** — that is the AI-slop default this direction exists to reject.

## What's in this folder

| File | viewBox | Purpose |
|---|---|---|
| `hero.svg` | 1200×600 | Marketing hero — warm still life (teacup with steam curl, rolling pastel hills, soft terracotta sun) with italic serif accent |
| `empty-generic.svg` | 400×400 | Empty state — hand-drawn empty basket with soft blush shadow, italic "it's empty in here." |
| `error-404.svg` | 400×400 | 404 — folded paper map with an off-trail accent pin, italic "a little off the path." |
| `success.svg` | 400×400 | Success — hand-drawn imperfect circle with a rounded accent checkmark, flanking sage sprigs, "all set." |
| `ai-prompts.md` | — | Prompts for generating more, with explicit Corporate Memphis negative prompts |
| `README.md` | — | This file |

## How to use the SVGs

```css
.illustration--warm-approachable {
  --fg:     #2D1B0F;   /* warm charcoal, not black */
  --bg:     #FAF3E7;   /* peach cream */
  --accent: #B04A2C;   /* terracotta */
}
```

### Secondary pastels

These SVGs use two additional colors that are NOT currently wired to CSS variables:

- `#A8B88A` at 65–70% opacity — desaturated sage for leaf/landmass fills
- `#E8B39A` at 60–70% opacity — dusty blush for shadow fills

They're hardcoded because the parent design system only provides one `--accent`. If you want them to retint with a custom palette, add these tokens to your design system:

```css
.illustration--warm-approachable {
  --pastel-sage:  #A8B88A;
  --pastel-blush: #E8B39A;
}
```

Then swap the hex literals inside the SVG for `var(--pastel-sage, #A8B88A)` and `var(--pastel-blush, #E8B39A)`.

### Fonts

Italic serif captions reference `Fraunces, Crimson Pro, Georgia, serif`. If neither of the first two are loaded, Georgia carries the italic gracefully. Load Fraunces for the best result.

### Dark mode

For dark mode, swap:

```css
.illustration--warm-approachable.dark {
  --fg:     #F5E8D3;   /* warm cream */
  --bg:     #1F1612;   /* deep coffee */
  --accent: #E87040;   /* brighter terracotta */
}
```

The pastel fills work on dark with their current opacities — they mute into the background pleasantly.

## How to extend

- **More spots**: use `ai-prompts.md`. The base template includes explicit anti-Corporate-Memphis negative prompts.
- **Authoring SVG directly**: always use `stroke-linecap="round"` and `stroke-linejoin="round"`. Always add a small imperfection to closed shapes (the hand-drawn circle in `success.svg` is slightly asymmetric on purpose). Never use geometric perfection.
- **Adding figures**: if you must include a person, they need a face with features. No faceless silhouettes. If in doubt, illustrate the environment (a desk, a room, a tool) instead of the person.

## What to avoid

- Corporate Memphis in all its forms — wavy limbs, no faces, purple-and-green palette
- Sharp corners anywhere
- Saturated neon colors (you're in warm pastel territory)
- Geometric rigidity — every shape should feel hand-drawn
- Multiple gradients — one soft radial is fine, three overlapping reads as 2014 iOS
- Script fonts beyond a single italic accent phrase

## Direction test

If it could be mistaken for `vibrant-playful` — the palette is too saturated; desaturate. If it could be mistaken for `editorial` — remove cross-hatching and serif discipline; this direction wobbles. If it could be mistaken for stock illustration — add a hand-drawn imperfection to one shape and a small italic accent phrase.
