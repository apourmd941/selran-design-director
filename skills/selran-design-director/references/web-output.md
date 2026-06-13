# Web output

Rules for HTML, React, and other web UIs. Inherits from design tokens already generated.

## File structure

For a single artifact (landing page, standalone component): inline everything into one HTML file with CSS custom properties at the top.

For a multi-file project: create a tokens file and import it.

```
project/
├── styles/
│   └── tokens.css          ← design tokens (colors, spacing, type, motion)
├── components/
└── pages/
```

## Tailwind vs. hand-written CSS

Tailwind is fine as long as:
1. You use design tokens via `@theme` directive or CSS variables, not arbitrary values
2. You don't lean on `rounded-xl shadow-lg` defaults (see anti-patterns)
3. You extract repeated combinations into components or utility classes

For artifacts: tailwind via CDN works. For production: use the configured version.

When NOT using Tailwind: write semantic HTML, use CSS custom properties, use modern CSS (container queries, `:has()`, logical properties).

## The non-negotiables

### Accessibility

- Every interactive element reachable by keyboard
- Visible focus states (`:focus-visible` with a ring that actually shows)
- Color contrast ratios pass WCAG AA (see color-systems.md)
- Form inputs have labels (visible or `aria-label`)
- Headings in hierarchical order (h1 → h2 → h3, no skipping)
- `alt` text on images, `""` if decorative
- Skip link at top of page for keyboard users

### Responsive

Mobile-first. Test at 375px, 768px, 1024px, 1440px.

Don't scale desktop designs down — rethink for small screens. Hamburger menu is a lazy default; consider what's actually on the nav.

### Performance

- Defer non-critical JS
- Lazy load below-the-fold images
- Use `next/image` or native `loading="lazy"`
- Subset web fonts or use `font-display: swap`
- No layout shift on load (set image dimensions)

## Component patterns that elevate

### Buttons

```html
<!-- Primary -->
<button class="btn-primary">
  <span>Start free</span>
</button>

<!-- Secondary with icon -->
<button class="btn-secondary">
  <span>See pricing</span>
  <svg>...</svg>  <!-- Lucide icon, NOT emoji -->
</button>
```

- Height: 36–44px on desktop, 44px+ on mobile (touch target)
- Horizontal padding: 1.5x vertical minimum
- Loading state: spinner replaces text, keeps width to prevent layout shift

### Cards

- Subtle border OR subtle shadow — not both
- Radius consistent with aesthetic direction (0–4px for technical, 8–12px for friendly, 16px+ for bold)
- Padding: at least the spacing scale's `md` (typically 24px)

### Forms

- Labels above inputs, not placeholders-as-labels
- Input height matches button height for visual rhythm
- Error states with both color AND text/icon (not color alone — WCAG)
- Required field indicator: `*` in accent color with `aria-required="true"`

### Navigation

- Logo on left, primary nav center or right, CTA on far right
- Mobile: hamburger that actually reveals a thoughtful menu, not just a dropdown list
- Sticky nav on long pages, but with reduced height after scroll

## Layout rhythms

### Hero sections

Break the "headline + subhead + CTA + screenshot right" cliché when possible:

- **Asymmetric:** headline takes 7 columns, image takes 5, but image breaks out of its column
- **Full-bleed image, text overlay:** with gradient for legibility
- **Split-panel:** left = content, right = interactive demo or animated graphic
- **Centered + oversized type:** let the headline be 10vw if it's good

### Feature sections

The "3 cards in a row" is fine but overused. Alternatives:

- **Bento grid** with varied cell sizes — one large feature, two smaller, one wide
- **Alternating left/right** — text-image pairs stacked vertically
- **Single-column with strong hierarchy** — scroll-driven reveal of features

### Pricing

- 3-tier comparison is fine because users expect it
- Highlight the middle (recommended) tier with a subtle accent border
- Feature list uses consistent checkmark/cross icons
- Annual/monthly toggle if relevant

## Dark mode

Include both. Use `prefers-color-scheme` OR a toggle. Tokens should flip cleanly.

```css
:root { /* light tokens */ }
@media (prefers-color-scheme: dark) { :root { /* dark tokens */ } }
[data-theme="dark"] { /* manual override */ }
[data-theme="light"] { /* manual override */ }
```

## Semantic HTML

Use real elements:

- `<button>` not `<div onclick>`
- `<a>` for navigation, `<button>` for actions
- `<nav>`, `<main>`, `<article>`, `<section>`, `<aside>`, `<footer>`
- `<h1>` to `<h6>` in order

Screen readers, search engines, and maintainability all benefit.

## What not to import

- `shadcn/ui` is fine but customize it — do not ship default shadcn styling as-is
- Don't import 17 components from 4 libraries
- Don't use `framer-motion` for simple transitions that CSS handles
- Don't reach for a full UI kit for a landing page

## Artifacts specifically

When rendering in Claude's artifact environment:

- Single file with inline `<style>` is best
- Load fonts from Google Fonts CDN via `<link>`
- Use `tailwindcss` via CDN script if you want Tailwind
- Don't rely on build tools or imports
- Test that it works without internet (fonts will fall back, shouldn't break)
