# AI prompts — warm-approachable

Use these with Midjourney / Flux / Ideogram / Stable Diffusion for illustrations in the `warm-approachable` voice. Soft, rounded, organic, human — **but never Corporate Memphis**. That's the #1 AI default for this category, and it's exactly what the direction rejects.

---

## Base template

```
soft warm illustration of [SUBJECT],
rounded stroke caps, 2-3px hand-drawn line work with gentle wobble,
warm palette: peach cream background (#FAF3E7), warm charcoal lines (#2D1B0F), terracotta accent (#B04A2C),
secondary pastels for fills: desaturated sage (#A8B88A at 70% opacity) and dusty blush (#E8B39A at 60% opacity),
gently asymmetric composition, organic shapes no sharp corners,
boutique hotel illustration feel, Calm app aesthetic, Notion marketing spot, Headspace spot art,
warm still-life or natural subject, small italic serif accent phrase if space allows
--ar 2:1 --v 6 --style raw
```

---

## Style modifiers (include to stay in-voice)

- `rounded stroke caps, hand-drawn line weight 2-3px`
- `warm pastel palette` — peach, sand, cream, sage, blush, terracotta
- `organic shapes, no sharp corners, no geometric rigidity`
- `gently asymmetric, hand-drawn imperfection`
- `warm still life, plants, textiles, ceramics, natural subjects`
- `italic serif accent, Fraunces / Crimson Pro detail` when text is appropriate

---

## Negative prompts — CRITICAL for this direction

```
no Corporate Memphis, no faceless people with wavy limbs, no purple and green palette,
no impossibly long noodle arms, no geometric cartoon people,
no Pablo Stanley style, no "office workers at laptops" clip art,
no bright saturated neon, no rainbow, no pastel-plus-navy combo,
no 3D render, no photorealism, no isometric wireframes,
no gradient mesh, no glass morphism, no gloss,
no script font overload, no Comic Sans
```

**If the output contains a faceless figure with wavy limbs — reject and regenerate.** That is the exact AI-slop fallback this direction exists to avoid.

---

## Example prompts

### Person working (avoid the person being the subject)

```
soft warm illustration of a cozy workspace from above — open notebook, ceramic mug, small potted plant, wooden pencil,
NO person in frame, subject is the still life of the desk itself,
hand-drawn 2-3px warm charcoal lines with rounded caps, gentle wobble,
peach cream background, terracotta accent on the mug rim, sage fill on the plant leaves, blush on the notebook edge,
slightly asymmetric composition, Calm app aesthetic
--ar 3:2 --v 6 --style raw
```

### Team collaboration

```
soft warm illustration of three overlapping circles forming a Venn diagram,
each circle a different warm pastel fill (terracotta, sage, dusty blush), warm charcoal line work with rounded caps,
small hand-drawn sprigs of leaves in the three outer crescents,
italic serif phrase "better together" in Fraunces beneath, gentle asymmetric composition,
Notion marketing illustration feel
--ar 3:2 --v 6 --style raw
```

### Growth chart

```
soft warm illustration of a small potted plant with three ascending leaves, each leaf larger than the previous,
warm charcoal line work with rounded caps, sage pastel fills on leaves, terracotta on the pot,
peach cream background, hand-drawn imperfection, tiny dashed line trailing upward from the tallest leaf,
boutique hotel brand illustration aesthetic, no data axes, no labels
--ar 3:2 --v 6 --style raw
```

### Celebration / completion

```
soft warm illustration of a hand-drawn imperfect circle enclosing a gentle checkmark,
2-3px warm charcoal outline with rounded caps, terracotta stroke on the checkmark,
two small sage leaf sprigs flanking the circle, soft blush halo behind,
italic serif caption "all set." below, warm peach cream background,
Headspace completion screen aesthetic
--ar 1:1 --v 6 --style raw
```

### Notification / alert

```
soft warm illustration of a small ceramic bell on a curved shelf,
warm charcoal rounded outline, terracotta clapper, sage shadow beneath,
hand-drawn wobble, peach cream background,
italic serif "a gentle reminder" text if prompt allows,
boutique brand spot illustration, Notion empty-state feel
--ar 1:1 --v 6 --style raw
```

---

## Aspect ratio hints

| Use case | Ratio | Flag |
|---|---|---|
| Marketing hero | 2:1 or 3:2 | `--ar 2:1` |
| Feature image | 16:9 | `--ar 16:9` |
| Spot illustration | 1:1 | `--ar 1:1` |
| Vertical poster / onboarding | 3:4 | `--ar 3:4` |
| Email hero | 2:1 | `--ar 2:1` |

---

## Post-processing

1. Trace output to SVG. If the generator gives rough edges, keep them — warm-approachable benefits from hand-drawn imperfection.
2. Replace hex colors:
   - Warm charcoal lines → `var(--fg, currentColor)`
   - Peach cream background → `var(--bg, transparent)`
   - Terracotta accent → `var(--accent, #B04A2C)`
   - Secondary pastels (sage, blush) → leave as hex, since `--accent` only covers one value. These are the direction's secondary pastels — don't parameterize them unless the host design system provides `--pastel-sage` / `--pastel-blush` tokens.
3. Apply `stroke-linecap="round"` and `stroke-linejoin="round"` to every stroke. This is non-negotiable for this direction.
4. Optimize with `svgo`. These SVGs run 5–7 KB because of the pastel fills and rounded paths.
5. Run direction test — if the result reads "vibrant-playful" the colors are too saturated; desaturate by 20% and add warmth. If it reads "editorial" you're using cross-hatching; remove it.
6. **Face test**: if there's a figure with limbs in the output, does it have a recognizable face with features? If yes, it may work. If no, reject — faceless figures = Corporate Memphis.
