# Motion snippets library

Drop-in, CSS-only motion utilities — one file per direction. Each file ships:

- Transitions for button, card, link underline, input focus
- Entrance animations (`.m-{prefix}-enter-fade-up`, `.m-{prefix}-stagger`)
- Micro-interactions (press, tap-scale, icon spin)
- A signature gesture unique to the direction
- A `@media (prefers-reduced-motion: reduce)` block

All utilities consume from host design-system CSS vars when present (`--motion-duration`, `--motion-easing`, `--color-accent`, etc.) with direction-tuned fallbacks. Class prefixes are unique so multiple files can load side-by-side without collision.

## Files

| Direction          | File                       | Prefix     | Signature gesture                                                        |
|--------------------|----------------------------|------------|--------------------------------------------------------------------------|
| technical-minimal  | `technical-minimal.css`    | `.m-tm-*`  | Precise 160ms ease-out "snap" — single curve, no overshoot.              |
| editorial          | `editorial.css`            | `.m-ed-*`  | `clip-path` page-turn reveal — like a page settling or letterpress.      |
| dark-premium       | `dark-premium.css`         | `.m-dp-*`  | Slow luminous accent glow — breathing hero light, one per page.          |
| warm-approachable  | `warm-approachable.css`    | `.m-wa-*`  | Soft settle — tiny overshoot then ease, like placing on a cushion.       |
| vibrant-playful    | `vibrant-playful.css`      | `.m-vp-*`  | Bouncy overshoot `cubic-bezier(0.34, 1.35, 0.5, 1)` — a smile, not a spring. |
| brutalist          | `brutalist.css`            | `.m-br-*`  | Hard snap via `steps(1, end)` — no easing, no duration, no apology.      |
| bold-distinctive   | `bold-distinctive.css`     | `.m-bd-*`  | Confident slide — long horizontal entrance for display-type architecture.|

## When to reach for each

- **technical-minimal** — Dense product UIs (dashboards, dev tools, SaaS). You want motion invisible; tight, 120–200ms, single-property transitions only.
- **editorial** — Long-form content, magazines, publishing. You want quiet reveals that feel typographic, not animated; clip-path over bounce.
- **dark-premium** — High-end product pages, luxury, cinematic. You want one orchestrated hero moment per screen with subtle accent glow; never on everything.
- **warm-approachable** — Hospitality, nonprofits, consumer brands with heart. You want gentle lifts and paperlike shadows; never jolty, never sharp.
- **vibrant-playful** — Consumer apps with personality (Notion/Mailchimp/Duolingo energy). You want friendly overshoot on pops, chips, toasts — confident, never childish.
- **brutalist** — Art/zine/manifesto/dev-culture sites. You want instant inversion, step-end reveals, plain-HTML aesthetic. If it eases, it's wrong.
- **bold-distinctive** — Launches, manifestos, fashion, editorial statements. You want one big orchestrated entrance (slide + curtain + marker); hold the rest restrained.

## Usage

```html
<link rel="stylesheet" href="/path/to/motion/editorial.css">

<button class="m-ed-btn">Read more</button>
<article class="m-ed-enter-fade-up">…</article>
<h1 class="m-ed-page-turn">The Quiet Revolution</h1>
```

Or import just the one you need into a CSS bundle:

```css
@import "motion/technical-minimal.css";
```

## Overrides

Each file declares its defaults in `:root` as `--m-{prefix}-duration-*` / `--m-{prefix}-easing*` vars, falling back through the host tokens (`--motion-duration`, `--motion-easing`) first. To re-tune globally:

```css
:root {
  --motion-duration: 180ms;
  --motion-easing: cubic-bezier(0.2, 0, 0, 1);
}
```

To re-tune just one direction, override its prefixed vars:

```css
:root { --m-vp-duration-base: 320ms; }
```
