# Poster output

Rules for static single-image deliverables — posters, flyers, single-slide graphics, PNG exports, social cards. Inherits from design tokens.

## When this applies

- Event posters, conference graphics, gallery prints
- Recruiting or marketing flyers
- Social cards (Twitter, LinkedIn, Open Graph images)
- Single-slide hero graphics used outside a deck
- Any one-off visual where the whole message fits in one frame

Not this file: flowing documents (`document-output.md`), multi-slide decks (`slide-output.md`), interactive web (`web-output.md`).

## The typography dominance rule

A poster is ~80% type, 20% everything else. The headline is the design.

- Pick ONE typographic moment — the headline does the heavy lifting
- Everything else supports it: the subhead, the date, the logo, the info block
- If you find yourself adding a second large element to "balance" the composition, the headline isn't doing its job yet

Counterexample that's still valid: a full-bleed image with type overlay. Even there, the type is the message — the image is atmosphere.

## The three archetypes

Pick one and commit. Mixing archetypes produces mush.

### 1. Typographic grid

Pure type. No imagery. The headline is oversized, the grid is obvious, and the secondary information sits in disciplined alignment below.

- Use a strong display face (see typography.md for options by direction)
- Headline can be 200–500pt — make it large enough to feel architectural
- Mono or small-caps labels anchor secondary info
- Works for: lecture series, type-driven events, editorial-aesthetic pieces

### 2. Full-bleed image with type overlay

One dominant photograph or illustration fills the frame. Type sits on top, usually with a gradient or scrim for legibility.

- Image must be intentional — no stock imagery of handshakes, no generic gradients
- Place type in the quiet zone of the image (sky, wall, blurred background)
- Use a gradient overlay (`linear-gradient` from transparent to 40–60% black) only if needed for contrast
- Works for: events, products with strong visual identity, cinematic announcements

### 3. Asymmetric stack

Type and visual elements stack off-center on a strong grid. Deliberate negative space on one side, density on the other.

- Rotate pure-type sections against a single graphic element (a shape, a chart, a photo)
- Negative space is half the design
- Works for: editorial posters, brand announcements, anything trying to feel "designed" rather than produced

## Rendering paths

| Goal | Path | Why |
|---|---|---|
| Quick draft, vector output | SVG directly | Total control, text stays text |
| Polished, printable result | HTML + Playwright → PDF or PNG | Web-quality typography, any size |
| Heavy composition (layers, effects) | `canvas-design` skill patterns | Purpose-built for this |
| Raster export for social | HTML + Playwright → PNG at 2x | Sharp, predictable sizing |

For print: export as PDF with fonts embedded and CMYK color if the print vendor requires it (most web-to-print doesn't — sRGB is fine).

## Sizing and aspect ratios

Common targets:

- **A-series print:** A3 (297×420mm), A2 (420×594mm), A1 (594×841mm)
- **US print:** Letter (8.5×11"), Tabloid (11×17"), 18×24" (standard poster)
- **Social:** 1080×1080 (square), 1080×1350 (portrait), 1200×630 (OG card)
- **Screen hero:** 1920×1080 or 2560×1440

Set the viewport or SVG canvas to the target size exactly. Design at 1x, export at 2x for raster social output.

## Type scale for posters

Poster type sits well above web/document scales. Don't port those numbers.

| Role | Size range |
|---|---|
| Headline | 120–500pt |
| Subhead | 40–80pt |
| Body / info block | 18–28pt |
| Label / credit | 10–14pt |

Tighten tracking on the largest sizes (`letter-spacing: -0.03em` or similar). Loosen tracking on the smallest labels if they're in uppercase (`letter-spacing: 0.08em`).

## Color on posters

Restraint wins. One or two colors plus the neutral ground.

- The accent color from `design-system.md` does the emphasis work
- Full-bleed backgrounds in the accent color can work if the type is confident
- Dark backgrounds feel premium; stark white feels editorial; saturated color feels like an event
- Gradients are risky on posters — use flat color unless the aesthetic direction calls for one specifically

## Poster-specific anti-patterns

| Avoid | Instead |
|---|---|
| Centered-everything layout | Asymmetric grid or deliberate full-bleed |
| Geometric shapes in the corners (triangles, circles as "decoration") | Nothing — or type that reaches the edge |
| Cramped info bar along the bottom with all the details | One clean info block, aligned to the grid |
| Six fonts because "each element needs its own look" | One display + one sans (two max) |
| Drop shadows on type | Flat type on solid ground, or high-contrast overlay |
| Stock imagery of diverse people smiling | A specific, intentional image — or no image |
| Starburst "NEW!" badges, ribbon banners | Scale and weight for emphasis, not ornament |
| Filling every pixel because blank space feels wasteful | Blank space is the design — leave it alone |
| Gradient text | Flat color; gradients on type read as dated |

## The quality checkpoint

Before delivering: shrink the poster to thumbnail size (about 200px tall) and look at it. The headline should still read. The composition should still feel intentional. If it turns into noise at thumbnail scale, it's over-designed.
