# Dark-Premium — Starter Components (partial)

Three snippets tuned for the dark-premium direction: deep background, soft
white text, one muted jewel/metallic accent, one hero moment per section
expressed as a soft radial glow. Slow ease-out-expo on interactions.

## The three components

| File | Description |
|------|-------------|
| `hero-stat-led.html` | Centered stat hero with a soft accent glow under the display number + gradient-clipped text |
| `feature-grid-asymmetric.html` | 2×3 dark grid on hairline dividers; accent reserved for hover glow + link color |
| `cta-closing.html` | Centered closing CTA with accent rule, radial glow behind, shadowed primary button |
| `empty-state.html` | Centered block with single luminous glyph disc (accent halo), mono eyebrow, headline with accent em, primary + ghost CTA |
| `error-404.html` | Full-viewport 404 with dot-in-pill eyebrow, gradient-clipped numeral + drop-shadow glow, radial backdrop |
| `loading-skeleton.html` | 3-column layered dark card grid with slow luminous accent-tinted sweep (`ease-out-expo`) and pulsing status dot |
| `form-login.html` | Layered dark card with radial accent glow; eyebrow pill with glowing dot; inputs with inset fill, accent-ring + soft glow focus; Google + GitHub buttons |
| `form-multi-step.html` | 3-step flow with numbered-circle stepper connected by hairlines; active node glows; slow ease-out-expo transitions on all controls |
| `form-validation.html` | 4 fields via `data-state`; valid and error states both use glow-style focus shadows (accent vs. red); mono uppercase messages |

## Host contract

Each snippet assumes the host page defines:

- `--accent` — the jewel/metallic hue (muted emerald, sapphire, gold, copper)
- `--fg` — soft white (`#F4F4F5`, not pure white)
- `--fg-muted` — muted gray text
- `--bg` — deep background (`#0A0A0B` to `#18181B`, never pure black)
- `--bg-2` — layered card surface (slightly lifted)
- `--bg-3` — hover/elevated surface
- `--border` — mid-gray hairline (`#27272A` region)

And a `.mono` class for JetBrains Mono (uppercase, tracked, ~11px).

## How to use

Paste into the page. Class prefixes are `.c-dp-hero`, `.c-dp-feat`,
`.c-dp-cta`, `.c-dp-login`, `.c-dp-mstep`, `.c-dp-validate` so they won't
collide.

Uses `color-mix(in oklab, ...)` for the glow layers — modern browsers only.
If you need wider support, replace the `color-mix(...)` calls with a
pre-computed rgba for the accent.

## Conventions used

- `ease-out-expo` (`cubic-bezier(0.19, 1, 0.22, 1)`) at 300–400ms — slower
  than technical-minimal
- Accent reserved: rule lines, hover state, one hero glow per section
- Radial-gradient glow behind hero/CTA headlines
- Micro-lift (`translateY(-1px)` + shadow) on primary buttons, never on cards
