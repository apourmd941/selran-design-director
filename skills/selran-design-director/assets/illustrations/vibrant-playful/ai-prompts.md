# AI prompts — vibrant-playful

Use these with Midjourney / Flux / Ideogram / Stable Diffusion for illustrations in the `vibrant-playful` voice. Bold, saturated, confident, alive — not childish, not rainbow.

---

## Base template

```
bold flat vector illustration of [SUBJECT],
saturated coordinated 5-hue palette: terracotta (#C44A2E, primary), deep teal (#1F6B88), amber (#9C6014), plum (#553E5A), forest green (#2A7B60),
warm cream background (#FFFBF5), 3px black outlines where strokes appear,
dynamic asymmetric composition, shapes rotated 5-12 degrees, elements overlapping and breaking frame,
pattern fills (dots, diagonal stripes, checkerboard) used as texture on 1-2 shapes,
bold rounded geometry, 12-20px corner radii on rectangles,
Figma marketing illustration aesthetic, Loom brand spot, Framer marketing art, Notion illustration style
--ar 2:1 --v 6 --style raw
```

---

## Style modifiers

- `coordinated 5-hue palette` (not rainbow — pulls from the declared `color.palette` only)
- `asymmetric composition` — never centered, never symmetric
- `rotated elements` — 5° to 15° tilt on main shapes
- `pattern fills as texture` — dots, stripes, checkers
- `bold 3-4px black outlines` where strokes are visible
- `soft rounded corners` — 12–20px radius, never pill except for tags
- `overlapping shapes` — composition breathes through layering

---

## Negative prompts

```
no rainbow, no pastel softness, no 3D render, no glass morphism,
no gradient mesh, no Corporate Memphis, no faceless wavy limbs,
no script font, no Comic Sans, no childish cartoon,
no photorealism, no watercolor, no cross-hatching,
no symmetric composition, no centered layouts,
no more than 5 palette hues, no neon eye-bleed
```

---

## Example prompts

### Person working

```
bold flat vector illustration of an abstract figure at a desk seen from three-quarter front,
the figure stylized as geometric filled shapes (not realistic), with a clear simple face (two dots for eyes, small curve for mouth — expressive not cartoonish),
5-hue palette: terracotta shirt, teal laptop screen, amber background panel, plum desk, forest-green plant in corner,
dot-pattern fill on one panel, diagonal-stripe fill on the chair,
warm cream overall background, 3px black outlines, dynamic asymmetric composition with shapes rotated slightly,
Figma marketing illustration aesthetic, NOT Corporate Memphis
--ar 3:2 --v 6 --style raw
```

### Team collaboration

```
bold flat vector illustration of three overlapping abstract figures in conversation,
geometric filled bodies in terracotta, teal, and plum — each face a simple geometric set of features,
forest-green speech bubbles floating between them with dot-pattern texture fill,
warm cream background, asymmetric composition with figures at different heights and slight rotations,
3px black outlines, Loom marketing brand illustration feel
--ar 3:2 --v 6 --style raw
```

### Growth chart

```
bold flat vector illustration of an ascending bar chart where each bar is a different palette hue,
terracotta, teal, amber, plum, forest-green bars in ascending height,
one bar has a diagonal-stripe pattern fill as texture,
a bold arrow in black outline rising above the tallest bar at a slight angle,
scattered celebratory dashes in palette colors around the top,
warm cream background, 3-4px black outlines where visible, Figma marketing aesthetic
--ar 3:2 --v 6 --style raw
```

### Celebration / completion

```
bold flat vector illustration of a large rotated rounded square (forest-green) with a giant cream-colored checkmark over it,
terracotta circle overlapping the top corner with black outline, amber pill shape floating nearby,
scattered intentional confetti dashes in the full palette (terracotta, teal, amber, plum, green) around the composition,
dot-pattern fill on a small wedge bottom-left,
warm cream background, asymmetric, rotated, dynamic, Framer marketing aesthetic
--ar 1:1 --v 6 --style raw
```

### Notification / alert

```
bold flat vector illustration of a stylized bell shape in terracotta with a black outline,
small amber dot as the clapper, diagonal-stripe fill on the bell body,
three teal sound-wave arcs radiating from the bell, plum accent circle behind,
warm cream background, slight rotation on the bell (5-8 degrees), asymmetric composition,
Notion marketing notification illustration
--ar 1:1 --v 6 --style raw
```

---

## Aspect ratio hints

| Use case | Ratio | Flag |
|---|---|---|
| Marketing hero | 2:1 or 3:2 | `--ar 2:1` |
| Feature banner | 16:9 | `--ar 16:9` |
| Spot illustration | 1:1 | `--ar 1:1` |
| Vertical onboarding card | 3:4 | `--ar 3:4` |
| Section header banner | 4:1 | `--ar 4:1` |

---

## Palette discipline

This direction is built around a **coordinated** multi-hue palette, not a rainbow. Every output should use ONLY these 5 hues plus cream and black:

| Role | Hex | Usage |
|---|---|---|
| `palette[0]` / accent | `#C44A2E` | Primary CTA, main focus |
| `palette[1]` | `#1F6B88` | Secondary shapes, cold tones |
| `palette[2]` | `#9C6014` | Warm secondary, pills/tags |
| `palette[3]` | `#553E5A` | Accent-cool, typography moments |
| `palette[4]` | `#2A7B60` | Highlights, "go" signals |
| background | `#FFFBF5` | Warm cream base |
| outlines | `#1A1A1A` | Softened black, never pure |

If a generator returns hues outside this set (a bright pink, a neon yellow), regenerate. A confident palette is the signature move.

---

## Post-processing

1. Trace to SVG. If the tool gives you bitmap pattern fills, convert them to SVG `<pattern>` elements — the canonical spots show the pattern syntax for dots, stripes, and checkers.
2. Replace colors:
   - Outlines → `var(--fg, #1A1A1A)`
   - Background → `var(--bg, #FFFBF5)`
   - palette[0] → `var(--accent, #C44A2E)`
   - palette[1..4] → `var(--palette-2, #1F6B88)` etc. (optional; default to direct hex if host doesn't expose palette vars)
3. Keep rotations. Reset-to-orthogonal kills the direction.
4. Optimize with `svgo`. Pattern-fill SVGs can run larger (6–10 KB) because of the pattern defs.
5. Run direction test — if the result reads "warm-approachable" the palette is too desaturated or rounded; boost saturation and add sharp geometric edges. If "bold-distinctive," the display type is dominating — reduce the numeral scale.
