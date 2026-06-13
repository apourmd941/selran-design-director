# bold-distinctive — illustration library

Oversized display typography IS the illustration. Cream background, near-black ink, hot-red accent bars. Asymmetric, manifesto-scale, deliberately cropped at edges. Huge numeric section markers as architecture. Pentagram, Pitchfork, Migra-typeface magazine layout energy.

## What's in this folder

| File | viewBox | Purpose |
|---|---|---|
| `hero.svg` | 1200×600 | Marketing hero — huge "01" numeric section marker, "EARN attention. Not the AVERAGE." two-line headline with accent bar |
| `empty-generic.svg` | 400×400 | Empty state — oversized italic "Nil." with accent slash, small-caps metadata |
| `error-404.svg` | 400×400 | 404 — massive outlined 404 numeral over diagonal accent slash, italic "Nothing to see here." |
| `success.svg` | 400×400 | Success — huge italic "Yes." with accent underline bar, rotated "Approved" tag |
| `ai-prompts.md` | — | Editorial-poster prompts (use Ideogram for best type fidelity) |
| `README.md` | — | This file |

## How to use the SVGs

```css
.illustration--bold-distinctive {
  --fg:     #0A0A0B;   /* near-black ink */
  --bg:     #F5F0E8;   /* cream */
  --accent: #C72500;   /* hot red */
}
```

### Alternate accents

The direction works with other saturated single accents — swap `--accent` for:

| Hex | Name | Mood |
|---|---|---|
| `#C72500` | Hot red (default) | Loud, confident |
| `#1F3B5A` | Deep cobalt | Editorial gravitas |
| `#5C1A12` | Oxblood | Literary, considered |
| `#CCCC00` | Acid yellow (on charcoal bg) | High-fashion rebellion |

If you use acid yellow, invert the palette: `--bg: #0A0A0B`, `--fg: #F5F0E8`, `--accent: #CCCC00`.

### Typography

Each SVG references `"PP Editorial New", "Fraunces", "Tiempos Headline", Georgia, serif` for display and `"General Sans", "Söhne", "Inter", sans-serif` for metadata. The display fallback to Georgia preserves the voice acceptably — this direction survives missing PP Editorial New better than others survive missing their signature fonts. Small-caps metadata works in any of the sans fallbacks.

**Loading recommendation:** load `Fraunces` at weight 900 via Google Fonts as the always-available display anchor. Then license or self-host `PP Editorial New Black` for the preferred-match experience.

### Text retinting

All `<text>` elements inside each SVG use `fill="var(--fg, #0A0A0B)"` or the accent variable. Swap the variables and the type retints cleanly.

### Dark mode

```css
.illustration--bold-distinctive.dark {
  --fg:     #F5F0E8;
  --bg:     #0A0A0B;
  --accent: #FF5530;
}
```

Works cleanly because the accent bars are full-saturation — the only change is ink/paper inversion.

## How to extend

- **More spots**: open `ai-prompts.md`. The note about preferring Ideogram is important — Midjourney rarely sets legible type at manifesto scale.
- **Authoring SVG directly**: take any of the four spots as a template. Replace the main display text with your own phrase. Replace the accent bar dimensions to match your new type's width. Keep the asymmetry and the intentional edge crop.
- **Oversized numeric markers**: the `hero.svg` demonstrates a 520pt "01" rendered as outlined type behind the headline. Copy that pattern to use numeric section markers as layout architecture in any spot.

## What to avoid

- Centered, symmetric layouts — the direction is asymmetric by rule
- Small type — if the display is under 100pt, it reads as "editorial," not "bold-distinctive"
- Rounded shapes — every shape here is rectangular
- Multiple accent colors
- Clean sans-serif display (Inter, Helvetica) — this direction needs character
- Drop shadows, gradients, 3D
- Corporate Memphis

## Direction test

If it reads `editorial` → display type isn't oversized enough, accent bar is missing. Push type to 200pt+ and add the graphic block. If `brutalist` → swap monospace back to serif display. If `vibrant-playful` → remove supporting palette hues; this direction is two colors (plus cream), not five.
