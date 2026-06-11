# brutalist — illustration library

Raw photocopy aesthetic. Halftone dot-pattern shading. Hard rectangular edges. Monospace typography integrated into the composition. Black + one safety-orange accent. Intentional registration offset between layers. Zine energy — Dazed & Confused, early Bloomberg Businessweek, David Carson.

## What's in this folder

| File | viewBox | Purpose |
|---|---|---|
| `hero.svg` | 1200×600 | Marketing hero — halftone circle, layered box stack with registration offset, huge mono 007 numeral |
| `empty-generic.svg` | 400×400 | Empty state — halftone rectangle with mono "EMPTY.STATE" header and accent block |
| `error-404.svg` | 400×400 | 404 — huge mono 404 over offset accent block, file-metadata footer |
| `success.svg` | 400×400 | Success — stamped "OK" block with registration-offset accent rectangle, "STATUS/200 ACCEPTED" header |
| `ai-prompts.md` | — | Photocopy/zine prompts for Midjourney/Flux/Ideogram |
| `README.md` | — | This file |

## How to use the SVGs

```css
.illustration--brutalist {
  --fg:     #000000;   /* pure black, on purpose */
  --bg:     #FFFFFF;   /* pure white, on purpose */
  --accent: #B84400;   /* safety orange */
}
```

Note: brutalist is the one direction where **pure black and pure white ARE correct**. The other directions soften both — brutalist is intentional rawness.

### Alternate accents

The direction works with other confrontational accents — swap `--accent` for:

| Hex | Effect |
|---|---|
| `#B84400` | Safety orange (default, shipped) |
| `#D10000` | Alarm red |
| `#FFD000` | Warning yellow |
| `#00A33D` | Hazmat green |

Pick one per composition. Never mix.

### Monospace fallbacks

Files reference `Space Mono, JetBrains Mono, "Courier New", ui-monospace, monospace`. Courier New is the ultimate fallback and carries the voice acceptably — this direction embraces system mono on purpose.

### Halftone pattern retinting

Each SVG declares its halftone pattern as a `<defs><pattern>` with circles filled via `var(--fg)`. When you invert to dark mode (swap `--fg` to white, `--bg` to black), the halftone inverts cleanly.

### Dark mode

For dark mode:

```css
.illustration--brutalist.dark {
  --fg:     #FFFFFF;
  --bg:     #000000;
  --accent: #FF6B00;   /* brighter orange on black */
}
```

## How to extend

- **More spots**: open `ai-prompts.md`. The registration-offset technique is the hardest to preserve through AI generation; you may need to add the offset manually in SVG post-processing.
- **Authoring SVG directly**: every shape should be a rectangle, polygon, or solid circle. No `<path>` with smooth curves. Use `<pattern>` for halftone. Always duplicate one major shape with a 4–8px offset and a color swap — that's the signature.

## What to avoid

- Smooth curves, rounded corners, gradients
- Drop shadows — brutalist never has shadows
- Cartoon characters, friendly imagery
- Pastel or desaturated colors — accents must confront
- Sans-serif clean typography (Helvetica, Inter) — use mono
- More than one accent color
- Motion or animation — this direction is static

## Direction test

If it reads "technical-minimal" → halftone isn't heavy enough, outlines aren't thick enough, and there's probably no registration offset. Fix. If it reads "bold-distinctive" → the display type is the wrong typeface; switch to monospace. Brutalist is always mono.
