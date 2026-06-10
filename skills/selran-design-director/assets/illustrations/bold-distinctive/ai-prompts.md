# AI prompts — bold-distinctive

Use these for Midjourney / Flux / Ideogram / Stable Diffusion output in the `bold-distinctive` voice. Oversized display typography as the illustration itself. Asymmetric, manifesto-scale, earns attention.

**Important:** for this direction, Ideogram often outperforms Midjourney because Ideogram handles large typographic compositions better. Midjourney can generate the mood but rarely sets legible type at the scale this direction demands. Consider hand-authoring SVG for final output when type fidelity matters.

---

## Base template

```
editorial poster illustration of [SUBJECT],
oversized display typography as the primary graphic element, set 150-300pt,
high-contrast serif (PP Editorial New, Fraunces Black, Tiempos Headline) or monumental sans (Druk, Migra),
cream background (#F5F0E8), near-black ink (#0A0A0B), single hot-red accent (#C72500) used as a graphic bar or block,
asymmetric composition with intentional text crop at edges,
large numeric section marker (01, 02) styled as layout architecture,
typography overlaps a solid color block, one wildcard rotated tag element,
Pentagram poster aesthetic, Pitchfork feature art, Migra typeface magazine layouts,
fashion magazine editorial, manifesto typography, kickstarter with design discipline
--ar 2:1 --v 6 --style raw
```

---

## Style modifiers

- `oversized display type as graphic element` — not a label on the illustration, IS the illustration
- `huge numeric section marker` — 01, 02, 03 styled at 300–600pt
- `intentional text crop at edge` — letterforms bleeding off canvas
- `asymmetric composition` — never centered
- `one hot accent block` — red, oxblood, cobalt — used once as a graphic bar behind type
- `italic as a deliberate choice` — italic + roman mix inside the same headline
- `small-caps metadata labels` with rule lines

---

## Negative prompts

```
no generic sans-serif layouts, no Helvetica, no modern clean minimalism,
no cute characters, no rounded organic shapes, no pastel colors,
no 3D render, no glass morphism, no photorealism,
no isometric, no wireframe, no cross-hatching,
no symmetric composition, no centered alignment,
no more than two colors (ink + accent + cream),
no Corporate Memphis, no rainbow
```

---

## Example prompts

### Person working

```
editorial poster illustration with the phrase "MAKE. BETTER. WORK." set in huge PP Editorial New Black at 200pt,
each word on its own line, italic "better." in contrast,
a single hot-red horizontal bar passing behind the second line,
tiny rotated "Manifesto" tag upper-right, small-caps "Issue No. 03" with thin rule at top-left,
cream background, near-black ink, asymmetric layout with intentional left-margin crop,
Pentagram poster aesthetic
--ar 2:1 --v 6 --style raw
```

### Team collaboration

```
editorial poster illustration with huge italic "&" ampersand set at 400pt as the central figure,
three tiny mono labels ("you" "me" "us") placed around the ampersand's curves,
hot-red vertical bar on the left edge cropping through the ampersand,
"Collective · Vol II" small-caps metadata with rule at bottom,
cream background, asymmetric, Pitchfork feature layout feel
--ar 3:2 --v 6 --style raw
```

### Growth chart

```
editorial poster illustration with massive "UP." set in italic PP Editorial New at 280pt,
a single diagonal hot-red bar ascending behind the text,
tiny small-caps metadata "+42%" stamped at the bar's endpoint,
cream background, near-black ink, asymmetric composition with UP cropped at the right edge,
numeric section marker "04" huge ghosted behind in outline,
Migra typeface magazine layout
--ar 2:1 --v 6 --style raw
```

### Celebration / completion

```
editorial poster illustration with huge italic "Yes." set in PP Editorial New at 220pt,
hot-red thick underline bar beneath,
supporting display text "That's the one." at 40pt below,
tiny rotated "Approved" tag at upper-right corner, small-caps "Status · Complete" at top-left with rule,
cream background, near-black ink, asymmetric
--ar 1:1 --v 6 --style raw
```

### Notification / alert

```
editorial poster illustration with a single huge italic exclamation mark "!" set at 400pt in Fraunces Black,
hot-red dot replacing the bottom dot of the exclamation,
small-caps "Attention · Required" metadata label at top-left with rule line,
tiny "See item 03" rotated tag lower-right,
cream background, asymmetric composition, manifesto typography feel
--ar 1:1 --v 6 --style raw
```

---

## Aspect ratio hints

| Use case | Ratio | Flag |
|---|---|---|
| Marketing hero / manifesto | 2:1 or 3:2 | `--ar 2:1` |
| Editorial feature | 16:9 | `--ar 16:9` |
| Spot illustration | 1:1 | `--ar 1:1` |
| Vertical poster | 2:3 | `--ar 2:3` |
| Header banner | 4:1 | `--ar 4:1` |

---

## Typography fallbacks in SVG

Because this direction LIVES on its display type, font fallbacks matter. Order matters:

```
font-family: "PP Editorial New", "Fraunces", "Tiempos Headline", Georgia, "Times New Roman", serif;
```

- `PP Editorial New Black` is the ideal if you can license it
- `Fraunces` at weight 900 is a free alternative with similar character
- `Tiempos Headline` is a paid alternative with similar contrast
- Georgia is the fallback that preserves reading experience
- Avoid `serif` alone — too generic and varies wildly by OS

For the meta labels, use `"General Sans", "Söhne", "Inter", ui-sans-serif, sans-serif`.

---

## Post-processing

1. When tracing rasters from Midjourney, the typography is usually garbled. Replace it with real SVG `<text>` elements in the target typeface — copy the pattern from `hero.svg`.
2. Replace colors:
   - Ink → `var(--fg, #0A0A0B)`
   - Cream → `var(--bg, #F5F0E8)`
   - Hot red → `var(--accent, #C72500)`
3. Keep the asymmetry. If a post-processor "cleans up" by centering the composition, it's broken the direction.
4. Optimize with `svgo`. Typography-heavy SVGs run 4–7 KB because text is lightweight; the accent bars and solid blocks add very little.
5. Run direction test — if it reads "editorial" the display type isn't BIG enough and the accent bar is missing; push type to 200pt+ and add the graphic block. If it reads "brutalist," swap monospace for serif display.
