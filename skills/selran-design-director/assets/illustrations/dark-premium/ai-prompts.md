# AI prompts — dark-premium

Use these for Midjourney / Flux / Ideogram / Stable Diffusion generation in the `dark-premium` voice. Sparse, cinematic, mostly empty canvas with one precise gilded element.

---

## Base template

```
minimalist atmospheric illustration of [SUBJECT],
1px hairline soft-white strokes on deep near-black background (#F4F4F5 on #0A0A0B),
single muted gold accent (#D4AF37) at less than 10% canvas coverage,
mostly negative space, one precise element only,
cinematic restraint, luxury brand aesthetic, Roger Dubuis website feel, Porsche editorial,
fine-dining invitation, museum wayfinding, arthouse film poster,
no multiple subjects, one focused gesture
--ar 2:1 --v 6 --style raw
```

---

## Style modifiers

- `mostly empty canvas` — negative space is the subject
- `single gilded element` — one gold accent, not multiple
- `hairline 1px strokes` — never thick
- `subtle radial glow behind the accent` — not around it, behind it
- `muted jewel tones` only if not using gold — emerald-muted, sapphire-muted, copper
- `soft white (#F4F4F5)` — never pure white
- `deep charcoal (#0A0A0B)` — never pure black

---

## Negative prompts

```
no bright saturated colors, no rainbow, no gradients across the whole image,
no cartoon characters, no playful shapes, no rounded organic forms,
no cross-hatching, no engraving texture, no halftone,
no isometric wireframes, no annotation callouts,
no Corporate Memphis, no multiple focal points,
no busy composition, no pattern fills, no photorealism
```

---

## Example prompts

### Person working (cinematic)

```
minimalist atmospheric illustration of a single figure in profile at a desk,
only the silhouette as a thin 1px white line, deep charcoal background,
one small gilded dot representing a lit screen (less than 5% canvas),
vast empty space around the figure, cinematic negative space,
luxury watch brand website feel, Porsche editorial aesthetic
--ar 2:1 --v 6 --style raw
```

### Team collaboration

```
minimalist atmospheric illustration of three thin vertical lines of varying heights,
converging to a single gilded point at the top, near-black background,
soft glow behind the convergence point, hairline 1px strokes,
mostly empty canvas, arthouse film poster feel,
no figures, abstract convergence, restraint
--ar 2:1 --v 6 --style raw
```

### Growth chart

```
minimalist atmospheric illustration of a single thin ascending line,
one gilded terminal dot at the peak, hairline weight, no axis labels,
subtle radial glow behind the dot, deep charcoal background,
80% negative space, fine-dining menu illustration feel
--ar 3:2 --v 6 --style raw
```

### Celebration / completion

```
minimalist atmospheric illustration of a single thin circle 80% traced in gilded gold,
remaining 20% gap suggesting completion, terminal dot where the arc meets,
soft radial glow behind the closure point, deep charcoal background,
cinematic restraint, museum object-label aesthetic
--ar 1:1 --v 6 --style raw
```

### Notification / alert

```
minimalist atmospheric illustration of a single vertical line with one gilded dot at its tip,
faint concentric rings radiating from the dot at 10-20% opacity,
hairline weights throughout, near-black background,
high-end tech product-detail page aesthetic
--ar 1:1 --v 6 --style raw
```

---

## Aspect ratio hints

| Use case | Ratio | Flag |
|---|---|---|
| Hero (cinematic) | 2:1 | `--ar 2:1` |
| Spot illustration | 1:1 | `--ar 1:1` |
| Feature image | 16:9 | `--ar 16:9` |
| Vertical poster section | 2:3 | `--ar 2:3` |
| Banner | 4:1 | `--ar 4:1` |

---

## Post-processing

1. Flatten the deep background to a solid `#0A0A0B` — don't keep photographic background gradient.
2. Trace to SVG. Because the direction uses so few elements, hand-authoring the SVG from a reference image is often faster than tracing.
3. Wire: soft-white strokes → `var(--fg, #F4F4F5)`; gold accent fills/strokes → `var(--accent, #D4AF37)`; background → `var(--bg, #0A0A0B)`.
4. The subtle radial glow around the accent should be an SVG `<radialGradient>` stopping on `var(--accent)` with `stop-opacity` under 0.2 — see `hero.svg` for the pattern.
5. Optimize with `svgo`. These files should be very small (2–4 KB) because the direction is sparse.
6. Run direction test — if it reads "technical-minimal" add the radial glow and remove annotation callouts; if it reads "editorial" remove cross-hatches and soften stroke weight.
