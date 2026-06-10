# dark-premium — illustration library

Cinematic, restrained, mostly empty. Deep charcoal backgrounds, soft-white hairline strokes, a single gilded accent at less than 10% of the canvas. Subtle radial glow behind the accent is the signature move. Think Porsche, Roger Dubuis, Tiempos-set luxury editorial.

## What's in this folder

| File | viewBox | Purpose |
|---|---|---|
| `hero.svg` | 1200×600 | Marketing hero — single elegant arc with a gilded pinpoint + soft radial glow |
| `empty-generic.svg` | 400×400 | Empty state — concentric rings with one gilded accent arc |
| `error-404.svg` | 400×400 | 404 — broken circle with gilded terminal dots + small serif 404 |
| `success.svg` | 400×400 | Success — gilded 270° arc terminating at a glowing pinpoint |
| `ai-prompts.md` | — | Prompts for generating more in this voice |
| `README.md` | — | This file |

## How to use the SVGs

The background is baked into each SVG as `<rect class="dp-bg" fill="var(--bg, #0A0A0B)">`. If you want the illustration to inherit the page background instead (transparent), override with:

```css
.illustration--dark-premium svg .dp-bg {
  fill: transparent;
}
```

Standard wiring:

```css
.illustration--dark-premium {
  --fg:     #F4F4F5;   /* soft white strokes */
  --bg:     #0A0A0B;   /* deep charcoal */
  --accent: #D4AF37;   /* muted gold */
}
```

The radial glow gradients inside each SVG reference `var(--accent)` directly, so the glow retints when you swap the accent (to copper, emerald-muted, sapphire-muted). Keep accent values saturated — the glow opacity stops do the softening.

**Light-mode adaptation:** this direction is built for dark. A "light dark-premium" is a contradiction. If the host page flips to light, consider showing a different direction's illustration instead. If you must port, swap `--fg` to a deep ink, `--bg` to a warm cream, and `--accent` to a deeper gold — but expect the voice to soften into something closer to editorial.

## How to extend

- **More spots**: use `ai-prompts.md`. The hardest part of this direction is resisting the urge to add elements. One gesture, one gilded point. That's the whole move.
- **Authoring SVG directly**: copy the `<radialGradient id="dp-*-glow">` pattern from any of the spots. Opacity stops at `.16 → .04 → 0` give the right soft falloff.

## What to avoid

- More than one focal point
- More than ~10% accent coverage
- Gradients that aren't the accent glow (no silver sheens, no smoke)
- Pattern fills, halftone, cross-hatching — this direction is not textural
- Anything remotely playful, rounded, or cartoon
- Pure black (`#000`) or pure white (`#FFF`) — always the softened versions
- Multiple strokes of equal weight — accent line should feel distinct from structure lines

## Direction test

If a stranger might say "could also be technical-minimal" → the canvas isn't empty enough. Remove elements. Add the radial glow. If "could also be editorial" → remove any ornamental rule lines or fleurons; this direction doesn't decorate.
