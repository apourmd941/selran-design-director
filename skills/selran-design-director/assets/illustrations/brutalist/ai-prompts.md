# AI prompts — brutalist

Use these for Midjourney / Flux / Ideogram / Stable Diffusion output in the `brutalist` voice. Raw, photocopy-aesthetic, zine energy. Black + one safety-orange accent. No smoothing, no curves, no softness.

---

## Base template

```
brutalist zine illustration of [SUBJECT],
raw photocopy aesthetic, halftone dot pattern shading, high contrast black on white,
single safety orange accent (#B84400) used once,
hard rectangular edges, no rounded corners, intentional registration offset between layers,
monospace typography (Courier New, Space Mono, JetBrains Mono) as part of the composition,
stark asymmetric composition, elements cropped at edges,
zine aesthetic, Dazed & Confused, early Bloomberg Businessweek, David Carson Ray Gun magazine,
"HTML view-source" feel, punk flyer energy
--ar 3:2 --v 6 --style raw
```

---

## Style modifiers

- `halftone dot pattern` — use for shading, not gradients
- `registration offset` — layers shifted 4–8px from each other, like a misprint
- `monospace typography` in the composition — Space Mono, Courier New
- `hard rectangular edges` — no rounded corners anywhere
- `photocopy grain, overprint` — not digital smoothing
- `stark asymmetric, cropped brutally at edges`
- `black + ONE accent color` — never more

---

## Negative prompts

```
no smooth gradients, no rounded corners, no drop shadows, no soft curves,
no friendly cartoon characters, no pastel colors, no rainbow,
no Corporate Memphis, no faceless wavy limbs, no playful shapes,
no 3D render, no glass morphism, no gloss,
no watercolor, no hand-drawn organic softness,
no more than one accent color, no Helvetica, no clean modernism
```

---

## Example prompts

### Person working

```
brutalist zine illustration of a heavily pixelated figure at a CRT monitor,
halftone dot pattern across the figure's shirt, black silhouette,
hard blocky edges, the monitor a solid safety-orange rectangle with mono CODE text visible,
registration offset between figure and monitor layers (~6px shift),
monospace filename "terminal.txt" stamped in corner,
white background, David Carson / Ray Gun aesthetic, zine punk energy
--ar 3:2 --v 6 --style raw
```

### Team collaboration

```
brutalist zine illustration of three overlapping pixelated head-silhouettes,
halftone shading across two faces, one face a solid black silhouette,
single safety-orange speech-rectangle crossing all three silhouettes with mono text "CONSENSUS/003",
hard edges everywhere, registration-offset duplicate layer visible behind,
stark black on white, Bloomberg Businessweek redesign aesthetic
--ar 3:2 --v 6 --style raw
```

### Growth chart

```
brutalist zine illustration of an ascending bar chart rendered as solid black rectangles,
each bar with a halftone dot-pattern section,
single safety-orange rectangle as the final/tallest bar,
monospace labels at the base ("Q1 Q2 Q3 Q4"), mono numeric ticks on the y-axis,
no axes smoothed, no rounded corners, hard pixel-perfect edges,
zine aesthetic, photocopy-grain feel
--ar 3:2 --v 6 --style raw
```

### Celebration / completion

```
brutalist zine illustration of a rubber-stamp "OK" in monospace type inside a halftone-patterned rectangle,
safety-orange stamp-block with black outline registered 6px offset,
"STATUS/200 ACCEPTED" header bar in black reverse-out mono text at top,
"TRANSACTION COMPLETE" footer bar, hard edges, zine aesthetic,
no celebratory shapes, no confetti — just the stamp
--ar 1:1 --v 6 --style raw
```

### Notification / alert

```
brutalist zine illustration of a solid black rectangle with an exclamation-mark cut out revealing safety orange beneath,
halftone dot-pattern surrounding the rectangle, registration offset between the cut-out and the colored layer,
mono label "ALERT/01" across the top, mono "SEE ABOVE" footer,
white background, photocopy-grain feel, no soft edges
--ar 1:1 --v 6 --style raw
```

---

## Aspect ratio hints

| Use case | Ratio | Flag |
|---|---|---|
| Zine cover / hero | 3:2 | `--ar 3:2` |
| Marketing hero banner | 2:1 | `--ar 2:1` |
| Spot illustration | 1:1 | `--ar 1:1` |
| Poster (tall) | 2:3 | `--ar 2:3` |
| Footer strip | 6:1 | `--ar 6:1` |

---

## Post-processing

1. Trace to SVG. For brutalist you want the crunchy edges preserved — use a low-precision tracer, or hand-author in SVG. `potrace` tends to over-smooth; consider `svg-trace` with low-detail settings.
2. Replace:
   - Black ink → `var(--fg, #000000)`
   - White / paper → `var(--bg, #FFFFFF)`
   - Safety orange → `var(--accent, #B84400)`
3. Convert any halftone bitmap fills to SVG `<pattern>` with small circles — see `hero.svg` for the `#br-halftone` pattern syntax.
4. Keep the registration-offset — duplicate a shape, shift it by 4–8px, stack it behind or beside the original. This is the signature.
5. Optimize with `svgo`. Pattern-heavy brutalist SVGs run 5–9 KB.
6. Run direction test — if it reads "technical-minimal" the halftone isn't heavy enough; add more pattern coverage and thicker outlines. If "bold-distinctive" you have too much display type that isn't monospace; switch typeface.
