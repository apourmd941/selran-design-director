# editorial — illustration library

Engraving-inspired. Cross-hatched shading via SVG `<pattern>`. Variable line weight. Classical symmetric compositions. Ink on parchment with occasional oxblood accent. Think Atlantic feature art, NYT Magazine, Kinfolk, old botanical prints.

## What's in this folder

| File | viewBox | Purpose |
|---|---|---|
| `hero.svg` | 1200×600 | Marketing hero — classical botanical-vase engraving with cross-hatched body, ornamental rules, metadata |
| `empty-generic.svg` | 400×400 | Empty state — ornamental double-rule frame around blank parchment with italic "nothing here yet." |
| `error-404.svg` | 400×400 | 404 — torn manuscript page with serif numeral 404 and an accent ink-blot |
| `success.svg` | 400×400 | Success — engraved laurel wreath around a hatched central seal with ribbon |
| `ai-prompts.md` | — | Engraving-style prompts for Midjourney / Flux / Ideogram |
| `README.md` | — | This file |

## How to use the SVGs

```css
.illustration--editorial {
  --fg:     #1A1A1A;   /* ink */
  --bg:     #F5F1EA;   /* parchment */
  --accent: #7A1F1F;   /* oxblood */
}
```

Or, wired to your tokens:

```css
.illustration--editorial {
  --fg:     var(--color-fg-primary);
  --bg:     var(--color-bg-primary);
  --accent: var(--color-accent);
}
```

**Cross-hatch patterns retint.** Each file declares its hatch patterns inside `<defs>` using `stroke="var(--fg, currentColor)"`. When you flip to dark mode and swap `--fg`, the hatches invert cleanly. Raster-traced SVGs with baked-in hatches do NOT retint — always prefer the pattern-based approach when authoring new spots.

**Serif fallbacks.** Files reference `Fraunces, EB Garamond, Georgia, "Times New Roman", serif` for the typographic details. If `Fraunces` isn't loaded on the host page the SVG falls through to Georgia — still reads as editorial. Load `Fraunces` for the best result.

## How to extend

- **Need a different spot**: start from `ai-prompts.md`. The base template is tuned for engraving output.
- **Authoring SVG directly**: use the `<pattern id="ed-hatch-*">` convention from `hero.svg` — three patterns (dense, cross, light) cover most shading needs. Keep outlines at 2–2.5px and detail at 0.75–1px.
- **Need an ornamental corner**: the `#ed-fleuron` symbol in `hero.svg` is reusable via `<use href="#ed-fleuron" />`.

## What to avoid

- Flat vector fills without cross-hatch shading — reads as "modern illustration" and breaks the voice
- Bright saturated colors (anything neon, anything pastel) — this direction is ink + parchment + one deep accent
- Modern isometric wireframes — that's `technical-minimal` territory
- Corporate Memphis anything
- Rounded stroke caps — use the default `butt` or `square`
- Sans-serif display type — serif only, with small-caps sans reserved for metadata

## Direction test

If a stranger might say "could also be technical-minimal" → add cross-hatch shading, thicken outlines, add ornamental rule lines. If they might say "could also be warm-approachable" → remove any rounded corners and warm gradients; this direction is crisp and authoritative, not soft.
