# Generative backgrounds

Atmosphere and depth are where AI-generated pages most often fall flat — solid
fills, no texture, no sense of light. `frontend-design` explicitly chases
"gradient meshes, noise textures, geometric patterns, layered transparencies."
This is DD's disciplined version: **token-driven, seeded, bounded** generative
backgrounds that serve the composition — never decoration for its own sake.

## The rules (what keeps this from becoming slop)

1. **Token-driven.** Every generative surface reads `--accent` / `--bg` /
   `--fg` — it retints with the direction, never introduces an off-palette
   color. No rainbow noise; no purple-on-white gradient (it's still an
   anti-pattern even when "generative").
2. **Seeded & reproducible.** Parametric output uses an explicit seed (the Art
   Blocks discipline) — same seed → same background, so a design is stable
   across regenerations and the user can lock the one they like.
3. **Bounded by the boldness dial** (`boldness.md`). restrained = barely-there
   grain; confident = a quiet gradient mesh or dot-grid; maximal = a full
   flow-field / particle hero. The dial sets intensity; it never overrides a11y.
4. **Behind the content, never competing with it.** Foreground text contrast
   must still pass WCAG AA *over the generated surface* — re-check after
   placing it (`accessibility-check.md`). A background that hurts legibility is
   a failure, however pretty.
5. **Motion respects `prefers-reduced-motion`.** Animated fields (flow,
   particles) ship a static fallback frame; default to static unless motion
   earns its place.

## The technique catalogue (pick by direction)

| Technique | Best for | Output |
|---|---|---|
| **Grain / noise overlay** | any direction at low intensity — adds tactility | inline SVG `feTurbulence` at low opacity, or a tiled PNG |
| **Dot-grid / hairline grid** | technical-minimal, brutalist | CSS `background-image` (radial/linear), token-spaced |
| **Gradient mesh** | dark-premium, vibrant, bold | layered radial-gradients in accent tints; one surface only |
| **Contour / topographic lines** | editorial, technical | seeded SVG paths; quiet, structural |
| **Flow field / particle field** | bold, vibrant, experimental | seeded p5.js/canvas (animated) or a rendered static frame |
| **Geometric tiling / halftone** | brutalist, bold | SVG pattern tile; hard edges |
| **Organic blobs / soft shapes** | warm-approachable | SVG with soft blur; never the Corporate-Memphis people |

Per-direction defaults: **technical-minimal** → subtle dot-grid or hairline
contours; **editorial** → paper grain + faint rule lines; **bold / vibrant** →
gradient mesh or flow field; **dark-premium** → deep gradient mesh + a hint of
particle glow; **brutalist** → halftone or exposed grid; **warm** → soft organic
shapes + grain. Match the direction's signature gesture — don't bolt a flow
field onto technical-minimal.

## Output

- **Static (default):** inline SVG or CSS `background` — self-contained, retints
  via CSS variables, zero network, performant. Use `assets/generative-backgrounds.html`
  as the starting gallery (grain, dot-grid, gradient-mesh, contour, flow-field
  still-frame — all token-driven).
- **Parametric / animated (on request):** a seeded p5.js sketch (the
  `algorithmic-art` register), with the seed recorded in `design-system.md` so
  it's reproducible, a static fallback frame, and a `prefers-reduced-motion`
  guard. Note: **animated GIF/video export is deferred** — it needs an encoder
  beyond DD's markdown surface; capture a still via the render tooling
  (`visual-verification.md`) instead, or hand the sketch to a build step.

## Discipline

- **Atmosphere, not ornament.** If removing the background changes nothing about
  how the content reads, it's working (depth); if it competes for attention,
  it's slop. Run the result through `self-critique.md` (color discipline +
  density categories).
- **One generative surface per view.** A generative hero is great; a generative
  hero *plus* generative cards *plus* a generative footer is noise — the same
  rule `anti-patterns.md` applies to gradients.
- **Performance budget holds** (`performance.md`): prefer CSS/SVG; a canvas
  animation must stay within the page's INP/CLS budget and pause off-screen.
