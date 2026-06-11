# technical-minimal — illustration library

Isometric, wireframe, hairline-stroke line-art. Monochrome zinc base plus one emerald (or accent-color) highlight used sparingly. Think Stripe, Linear, Vercel, Raycast.

## What's in this folder

| File | viewBox | Purpose |
|---|---|---|
| `hero.svg` | 1200×600 | Marketing hero — isometric wireframe stack with annotation callouts |
| `empty-generic.svg` | 400×400 | Empty state — isometric tray awaiting input, dashed accent insert cue |
| `error-404.svg` | 400×400 | 404 — two wireframe page schematics with a broken-link gap + accent X-marker |
| `success.svg` | 400×400 | Success — isometric cube with an accent tick across the top face |
| `ai-prompts.md` | — | Midjourney / Flux / Ideogram templates for generating more spots in this voice |
| `README.md` | — | This file |

## How to use the SVGs

Inline the SVG into your page, then wire the three CSS variables on a parent:

```css
.illustration--tech-minimal {
  --fg:     #18181B;   /* zinc-900 */
  --bg:     #FAFAF9;   /* panel */
  --accent: #0A7A5C;   /* emerald 20% opacity inside the SVG */
}
```

Or inherit from your design tokens:

```css
.illustration--tech-minimal {
  --fg:     var(--color-fg-primary);
  --bg:     var(--color-bg-primary);
  --accent: var(--color-accent);
}
```

The SVGs resolve strokes via `var(--fg, currentColor)` — if you don't set the variable, they fall back to the element's `color`. That means they'll also retint correctly if you just `color: #18181B` the parent element.

Accent fills are rendered at `opacity: .2` inside the SVG, so your accent token should be its full saturation value — the SVG handles the softening.

Dark mode: swap the three variable values. The line art inverts cleanly because nothing is pinned to a specific background.

## How to extend

- **Need a different spot** (e.g. `empty-search`, `settings`): open `ai-prompts.md`, substitute your subject into the base template. Run the output through `svgo` and wire the CSS variables before committing.
- **Authoring SVG directly**: match the existing files' discipline — `stroke-width: 1` or `1.25`, `vector-effect: non-scaling-stroke`, mono-font labels in small caps, a single accent used at most once per composition.

## What to avoid

- Filled shapes (other than the single 20%-opacity accent)
- Rounded stroke caps (`stroke-linecap: round`) — use the default `butt` / `square`
- Gradients, drop shadows, 3D rendering, photorealism
- Cartoon characters, any Corporate Memphis element
- More than one accent color — the whole point is restraint
- Illustrations scaled up from 24×24 icon sources — they'll look thin and empty

## Direction test

Can a stranger, shown just the illustration, correctly guess `technical-minimal`? If they'd say "could also be dark-premium" — push the annotation callouts harder (dimension lines, mono labels, dashed rule lines). If they'd say "could also be brutalist" — soften the contrast and add whitespace; this direction is quiet, not confrontational.
