# AI prompts — technical-minimal

Use these prompts with Midjourney, Flux, Ideogram, or Stable Diffusion to generate additional illustrations that match the `technical-minimal` voice. The four canonical SVGs in this folder are the reference — every generated asset should sit comfortably next to them.

---

## Base template

```
isometric line illustration of [SUBJECT],
thin 1px strokes, monochrome zinc grayscale (#18181B on #FAFAF9), single emerald accent (#0A7A5C) used ONCE at ~20% opacity,
wireframe aesthetic, precise geometric forms, orthogonal grid-aligned composition,
technical drawing feel, dimension lines and annotation callouts,
white or off-white background, no fills except the single accent,
style of Stripe illustration system, Linear empty state, Raycast spot art, Vercel dashboard diagrams
--ar 3:2 --v 6 --style raw
```

---

## Style modifiers (keep these in every prompt)

- `thin 1px hairline strokes`
- `monochrome` — grayscale base only
- `single accent color` — one deliberate highlight, never two
- `orthogonal composition, grid-aligned`
- `isometric projection` or `axonometric` when depth is needed
- `dimension lines, dashed callouts, technical annotation`
- `vector illustration, flat, 2D`

---

## Negative prompts (always include)

```
no rounded cartoon characters, no gradients, no drop shadows, no 3D rendering,
no filled shapes except the single accent, no photorealism, no textures,
no painterly effects, no watercolor, no hand-drawn wobble,
no rainbow palette, no Corporate Memphis, no faceless people with wavy limbs,
no stock-icon-scaled-up feel
```

---

## Example prompts

### Person working

```
isometric line illustration of a focused individual at a standing desk with multi-monitor setup,
thin 1px strokes, monochrome zinc, single emerald accent on the active screen at 20% opacity,
wireframe desk and computer, figure shown as simple geometric armature (no cartoon face),
dimension annotations beside the monitor, grid lines faint in the background,
style of Stripe illustration
--ar 3:2 --v 6 --style raw
```

### Team collaboration

```
isometric line illustration of three abstract geometric forms connected by thin annotation arrows,
each form a different platonic solid (cube, octahedron, cylinder), thin 1px strokes,
single accent arrow highlighting the central connection point,
monochrome zinc on off-white, dashed callout lines with mono-font labels,
no human figures, abstract collaboration diagram, Linear empty state feel
--ar 1:1 --v 6 --style raw
```

### Growth chart

```
isometric line illustration of a rising bar-chart rendered as a wireframe staircase,
each bar a transparent box with visible edges only, thin 1px strokes,
single emerald accent on the final (tallest) bar at 20% fill opacity,
dashed guide lines extending upward, small mono-font tick labels on the axis,
monochrome zinc on off-white, Vercel analytics aesthetic
--ar 3:2 --v 6 --style raw
```

### Celebration / success

```
isometric line illustration of a precise checkmark drawn across the top face of a transparent wireframe cube,
thin 1px strokes for cube edges, 1.5px accent stroke for the checkmark in emerald,
faint concentric circles radiating from cube, no confetti, no starbursts,
monochrome zinc on off-white, style of Raycast spot illustration
--ar 1:1 --v 6 --style raw
```

### Notification / alert

```
isometric line illustration of a single bell-shape rendered as wireframe only,
thin 1px zinc strokes, a single accent dot hovering at the bell's tip pulse-point,
dashed radial lines suggesting a faint ring indication,
orthogonal grid faint in the background, mono-font label "01 UNREAD" aligned to left rule,
Stripe Atlas illustration aesthetic, pure linework
--ar 1:1 --v 6 --style raw
```

---

## Aspect ratio hints

| Use case | Ratio | Flag |
|---|---|---|
| Marketing hero | 2:1 | `--ar 2:1` |
| Blog feature image | 3:2 | `--ar 3:2` |
| Spot illustration (empty, error, success) | 1:1 | `--ar 1:1` |
| Settings header / section banner | 4:1 | `--ar 4:1` |
| Card thumbnail | 4:3 | `--ar 4:3` |

---

## Post-processing

1. Export as SVG where the tool allows (Ideogram and Flux sometimes do; Midjourney doesn't — use a raster-to-SVG tracer like [svgtrace](https://www.svgtrace.com) or Adobe Illustrator's Image Trace).
2. Replace every hardcoded color in the SVG with `var(--fg, currentColor)` (for zinc grays) or `var(--accent, #0A7A5C)` (for the accent).
3. Optimize with `svgo`:
   ```bash
   svgo input.svg -o output.svg --multipass
   ```
4. Target ≤ 10 KB uncompressed. If it's over, simplify strokes or reduce unique paths.
5. Run the "direction test" — does the result read unambiguously as `technical-minimal`? If it could be mistaken for `editorial` or `dark-premium`, push the wireframe annotation cues harder.
