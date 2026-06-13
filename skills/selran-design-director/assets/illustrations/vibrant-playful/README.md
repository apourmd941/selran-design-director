# vibrant-playful — illustration library

Bold, saturated, confident. Coordinated 5-hue palette (terracotta, teal, amber, plum, forest green) on warm cream. Dynamic asymmetric compositions with rotated shapes, pattern fills as texture, and 3px black outlines. Figma, Loom, Framer marketing energy — never rainbow, never childish.

## What's in this folder

| File | viewBox | Purpose |
|---|---|---|
| `hero.svg` | 1200×600 | Marketing hero — collage of rotated filled shapes, stripe & dot patterns, full palette in play |
| `empty-generic.svg` | 400×400 | Empty state — dashed container with a teal circle, amber wedge, accent squiggle, "Nothing here yet!" |
| `error-404.svg` | 400×400 | 404 — huge stroked 404 numeral with stripe-fill circle, dot-fill blob, palette bolt |
| `success.svg` | 400×400 | Success — rotated green square with giant cream check, confetti dashes in all 5 palette hues |
| `ai-prompts.md` | — | Prompts tuned for the palette and pattern-fill aesthetic |
| `README.md` | — | This file |

## How to use the SVGs

This direction is the only one with a **multi-hue palette**, so wiring is richer:

```css
.illustration--vibrant-playful {
  --fg:        #1A1A1A;   /* softened black */
  --bg:        #FFFBF5;   /* warm cream */
  --accent:    #C44A2E;   /* terracotta — palette[0] */
  --palette-2: #1F6B88;   /* deep teal */
  --palette-3: #9C6014;   /* amber */
  --palette-4: #553E5A;   /* plum */
  --palette-5: #2A7B60;   /* forest green */
}
```

Or inherit from the vibrant-playful starter tokens:

```css
.illustration--vibrant-playful {
  --fg:        var(--color-fg-primary);
  --bg:        var(--color-bg-primary);
  --accent:    var(--color-palette-0);
  --palette-2: var(--color-palette-1);
  --palette-3: var(--color-palette-2);
  --palette-4: var(--color-palette-3);
  --palette-5: var(--color-palette-4);
}
```

Each SVG falls back to the hardcoded palette hex if a `--palette-N` variable isn't set, so partial wiring still renders cleanly.

### Pattern retinting

The dot, stripe, and checker `<pattern>` elements reference the palette variables directly. When you swap palette hues, the pattern fills follow automatically — this is the core retint mechanic for this direction.

## How to extend

- **More spots**: open `ai-prompts.md`. The palette discipline section is the most important — if a generator returns colors outside the 5-hue set, regenerate.
- **Authoring SVG directly**: the hero demonstrates all three pattern fills (`#vp-dots`, `#vp-stripes`, `#vp-check`). Copy those `<defs>` into any new SVG. Always rotate at least one main shape 5–12°. Never center-compose.

## What to avoid

- Rainbow palettes — stick to the 5 declared hues
- Pastel softness — this direction is saturated, not desaturated
- Symmetric / centered compositions — the direction is asymmetric by rule
- Script fonts
- 3D rendering, glass morphism, gradient mesh
- Corporate Memphis (faceless wavy-limb figures)
- More than 5 distinct hues per illustration (plus cream + black outline)

## Direction test

If it could be mistaken for `warm-approachable` → boost saturation and sharpen edges. If for `bold-distinctive` → reduce the display-type dominance; the shapes carry the composition here, not typography. If for Corporate Memphis → check faces. Every figure must have a distinct face, or no figure at all.
