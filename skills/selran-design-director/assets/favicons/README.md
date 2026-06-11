# Favicon templates

Per-direction `favicon.svg` scaffolds. One 24×24 viewBox SVG per aesthetic direction, driven by the same `{{token.path}}` placeholder convention as the other exports in `assets/exports/`.

## Files

| Template | Direction | Visual |
|---|---|---|
| `technical-minimal.svg.template` | technical-minimal | Rounded neutral tile, thin border, accent-color glyph |
| `editorial.svg.template` | editorial | Italic serif letter with thin accent rule underneath |
| `dark-premium.svg.template` | dark-premium | Dark bg, luminous radial glow behind accent glyph |
| `warm-approachable.svg.template` | warm-approachable | Soft-rounded accent tile, bg-color glyph |
| `vibrant-playful.svg.template` | vibrant-playful | Gradient + secondary palette dot, bold glyph |
| `brutalist.svg.template` | brutalist | White tile, 2px black stroke, mono block letter |
| `bold-distinctive.svg.template` | bold-distinctive | Oversized italic glyph bleeding the frame |

## Placeholders

All templates use:

- `{{color.*}}`, `{{type.display}}`, `{{color.palette.*}}` — token paths (same as other exports)
- `{{FAVICON_GLYPH}}` — the single character to render. Default to the first letter of `meta.project`; the user can override.

## Emit workflow

1. Pick the template matching the project's `direction`.
2. Substitute tokens (same `{{token.path}}` flattener used for other exports — see `references/token-export.md`).
3. Substitute `{{FAVICON_GLYPH}}`.
4. Write to `<project>/public/favicon.svg` (or the user's static-asset path).

## Rasterizing to PNG

SVG is the source; rasterize to PNG for legacy browsers and Apple touch icons.

```bash
# Using ImageMagick
magick -background none -density 300 favicon.svg -resize 16x16  favicon-16.png
magick -background none -density 300 favicon.svg -resize 32x32  favicon-32.png
magick -background none -density 300 favicon.svg -resize 180x180 apple-touch-icon.png

# Using rsvg-convert (lighter; part of librsvg)
rsvg-convert -w 32  -h 32  favicon.svg > favicon-32.png
rsvg-convert -w 180 -h 180 favicon.svg > apple-touch-icon.png
```

For a `.ico` multi-res bundle:

```bash
magick favicon-16.png favicon-32.png favicon-48.png favicon.ico
```

## HTML wiring

```html
<link rel="icon" type="image/svg+xml" href="/favicon.svg" />
<link rel="icon" type="image/png" sizes="32x32" href="/favicon-32.png" />
<link rel="icon" type="image/png" sizes="16x16" href="/favicon-16.png" />
<link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png" />
<link rel="manifest" href="/site.webmanifest" />
```

Modern browsers pick the SVG; older ones fall through to the 32px PNG.

## Notes

- SVGs embed font-family names but rasterization may not have the custom face installed. If you need pixel-exact matching to the running app, raster at PNG time on a machine with the fonts, or replace the `<text>` with a `<path>` outlined in a vector tool.
- `vibrant-playful.svg.template` expects `color.palette` to have at least 3 entries. Fall back to `accent` / `accent_hover` / `fg_primary` if the project left `palette: []`.
- Keep the glyph to one character. Two letters at 32×32 goes illegible fast.
