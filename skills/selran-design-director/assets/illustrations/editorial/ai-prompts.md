# AI prompts — editorial

Use these prompts with Midjourney, Flux, Ideogram, or Stable Diffusion for additional illustrations in the `editorial` voice. Engraving feel, cross-hatch shading, classical composition.

---

## Base template

```
engraving-style illustration of [SUBJECT],
cross-hatching shading, variable line weight (2-3px outlines, 0.75px detail),
ink on warm parchment palette (#1A1A1A on #F5F1EA), single oxblood accent (#7A1F1F) used sparingly,
classical symmetric composition with generous margins, rule lines and ornamental fleurons at corners,
botanical print aesthetic, Atlantic feature illustration feel, Kinfolk editorial spot,
18th-century woodcut influence, NYT Magazine black-and-white illustration
--ar 3:2 --v 6 --style raw
```

---

## Style modifiers

- `cross-hatching, engraving technique, ink stippling`
- `variable line weight` — thin for detail, thicker for outlines
- `two-tone palette` — ink + parchment; optional single accent
- `classical symmetric composition` or `rule-of-thirds with generous white space`
- `ornamental borders, fleurons, rule lines`
- `serif typographic details, Fraunces or EB Garamond context`
- `botanical print, scientific plate, antiquarian aesthetic`

---

## Negative prompts

```
no flat vector cartoons, no bright saturated fills, no modern isometric,
no 3D rendering, no photorealism, no watercolor,
no gradients, no drop shadows, no digital gloss,
no Corporate Memphis, no rainbow palette,
no cartoon characters, no emoji-style faces
```

---

## Example prompts

### Person working (reading / study scene)

```
engraving-style illustration of a figure seated at a writing desk beside a candle and stack of books,
cross-hatched shading defining volumes, variable line weight,
ink on warm parchment, single oxblood accent on the candle flame,
classical plate composition with decorative border and rule lines,
18th-century scientific frontispiece aesthetic,
Atlantic feature illustration feel
--ar 3:2 --v 6 --style raw
```

### Team collaboration

```
engraving-style illustration of a round-table council rendered as classical plate,
three seated figures in profile (no modern clothing cues — timeless silhouettes),
cross-hatched shading on robes and table, ornamental border with fleurons,
central decorative symbol on the table, oxblood accent used once on a wax-seal motif,
ink on parchment palette, Kinfolk editorial feel
--ar 3:2 --v 6 --style raw
```

### Growth chart

```
engraving-style illustration of a cultivated plant rising from a cross-hatched urn,
variable branch line weights, botanical-plate accuracy, specimen sheet composition,
labeled with small caps at leaf nodes (like a Linnaean taxonomy plate),
single oxblood accent on the uppermost bloom,
ink on warm parchment, antiquarian scientific print aesthetic
--ar 3:2 --v 6 --style raw
```

### Celebration / completion

```
engraving-style illustration of a classical laurel wreath encircling a central seal,
cross-hatched leaves with variable line weight, ornamental ribbon at the base,
central medallion filled with diagonal hatch in oxblood, serif initial at center,
symmetric composition, ink on parchment, NYT Magazine illustration feel
--ar 1:1 --v 6 --style raw
```

### Notification / alert

```
engraving-style illustration of a hand-tolled brass bell with decorative rope pull,
cross-hatched shading across the bell body, fine linework on the rope,
ornamental rule line beneath the bell with small-caps label,
oxblood accent only on a small wax seal affixed to the rope,
ink on parchment, 19th-century almanac illustration feel
--ar 1:1 --v 6 --style raw
```

---

## Aspect ratio hints

| Use case | Ratio | Flag |
|---|---|---|
| Marketing hero / feature opener | 2:1 or 3:2 | `--ar 2:1` |
| Article header | 16:9 | `--ar 16:9` |
| Spot (empty, error, success) | 1:1 | `--ar 1:1` |
| Chapter divider / section plate | 2:3 (portrait) | `--ar 2:3` |
| Pull-quote flank | 3:4 | `--ar 3:4` |

---

## Post-processing

1. Trace raster output to SVG (Illustrator Image Trace, svgtrace.com, or `potrace` for monochrome line-only results).
2. Replace hex colors: ink strokes → `var(--fg, currentColor)`; accent fills → `var(--accent, #7A1F1F)`; parchment bg → `var(--bg, transparent)`.
3. Convert cross-hatch shading to SVG `<pattern>` fills where possible — pattern-based hatches retint with CSS variables, baked-in hatches don't.
4. Optimize with `svgo --multipass` targeting ≤ 10 KB. Editorial files often run larger due to ornamental detail — if needed, simplify ornamental corners or drop one layer of cross-hatch density.
5. Run the direction test — if the result reads more "technical-minimal" than "editorial," you need thicker variable-weight outlines and cross-hatch shading. If it reads "warm-approachable," remove any rounded stroke caps and add rule-line ornament.
