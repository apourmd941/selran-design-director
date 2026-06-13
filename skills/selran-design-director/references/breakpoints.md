# Breakpoints — responsive thresholds

Responsive layout starts with a breakpoint scale. The schema ships a standard five-step scale (`sm/md/lg/xl/2xl`), but different directions have different opinions about when content should reflow, so the *defaults* per direction differ from the *scale*.

---

## The scale

Five breakpoints, mobile-first (min-width):

| Key  | px   | Typical target           |
|------|------|--------------------------|
| sm   | 640  | phone landscape / small tablet |
| md   | 768  | tablet portrait          |
| lg   | 1024 | tablet landscape / small laptop |
| xl   | 1280 | desktop                  |
| 2xl  | 1536 | wide desktop             |

These are **thresholds**, not target widths. A layout that reflows at `md` doesn't need to look the same at `md` and at `lg-1`.

## Direction-appropriate defaults

Directions tune breakpoints to match their spatial logic:

| Direction | Tendency | Container max | Main gutter |
|---|---|---|---|
| **technical-minimal** | Standard scale. Content-dense, so reflow happens at breakpoints, not in between. | 1200 | 24px |
| **editorial** | Runs wider for readability. Body text keeps a 60–75ch measure across all sizes. | 1280 | 32px |
| **bold-distinctive** | Standard scale. Type is big, so stack early — don't try 3-col at `md`. | 1200 | 32px |
| **dark-premium** | Standard scale. Dense but breathing. | 1200 | 24px |
| **warm-approachable** | Slightly tighter container to keep cards feeling hand-sized. | 1120 | 24px |
| **vibrant-playful** | Standard scale, generous gutters to let color breathe. | 1200 | 32px |
| **brutalist** | Often no max container — content runs edge-to-edge. Gutters zero or full-bleed. | none | 0 or 16px |

Override via `overrides.web.container_max` when you need a direction-specific size.

## Mobile-first vs desktop-first

All references assume **mobile-first**: styles cascade up from the smallest size. Add `@media (min-width: <bp>)` to introduce desktop-only treatments. Don't invert this — desktop-first max-width queries create compounding specificity bugs.

```css
/* Mobile-first (correct) */
.grid { grid-template-columns: 1fr; }
@media (min-width: 768px)  { .grid { grid-template-columns: 1fr 1fr; } }
@media (min-width: 1024px) { .grid { grid-template-columns: repeat(3, 1fr); } }

/* Desktop-first (avoid) */
.grid { grid-template-columns: repeat(3, 1fr); }
@media (max-width: 1023px) { .grid { grid-template-columns: 1fr 1fr; } }
@media (max-width: 767px)  { .grid { grid-template-columns: 1fr; } }
```

## Common reflow patterns

### Stack at sm, 2-col at md, 3-col at lg

```css
.feature-grid {
  display: grid;
  gap: 16px;
  grid-template-columns: 1fr;
}
@media (min-width: 768px) {
  .feature-grid { grid-template-columns: 1fr 1fr; gap: 24px; }
}
@media (min-width: 1024px) {
  .feature-grid { grid-template-columns: repeat(3, 1fr); gap: 32px; }
}
```

### Sidebar collapses at md

```css
.app-shell { display: block; }
@media (min-width: 1024px) {
  .app-shell {
    display: grid;
    grid-template-columns: 240px 1fr;
  }
}
```

Below `lg`, the sidebar becomes a hamburger / drawer — see `app-shell.md` for responsive shell behavior.

### Table → cards at sm

Tables don't fit below `md`. At `sm` the same data becomes stacked cards. See `composition-rubric.md` "Responsive collapse."

## Container queries (the modern option)

For component-level responsiveness (a card that adjusts based on its container, not the viewport), use container queries:

```css
.card { container-type: inline-size; }
@container (min-width: 480px) {
  .card__layout { display: grid; grid-template-columns: 96px 1fr; }
}
```

Prefer container queries for reusable components that might appear at varied sizes (sidebar vs main column). Use media queries for page-level layout shifts.

## Touch vs pointer

Breakpoints are not a proxy for input type. A 1024px laptop may have a touchscreen; a 600px window on a desktop does not. Use `@media (hover: hover)` and `@media (pointer: fine)` for input-specific treatments — never assume "small = touch, big = mouse."

```css
/* Hover effects only on devices that can actually hover */
@media (hover: hover) {
  .card:hover { background: var(--bg-2); }
}
```

## Native platforms

Breakpoints become **size classes** on native:

- **iOS (SwiftUI):** `horizontalSizeClass` is `.compact` (iPhone portrait) or `.regular` (iPad, iPhone landscape, Mac). Bridges roughly to `sm-md` = compact, `lg+` = regular.
- **Android (Compose):** `WindowSizeClass` is `Compact`/`Medium`/`Expanded` (<600dp / 600–839dp / 840+dp). Bridges roughly to `sm-md` = compact, `lg` = medium, `xl+` = expanded.

Native components in `assets/native-ios/` and `assets/native-android/` use size classes directly, not pixel breakpoints. See `references/native-ios.md` and `references/native-android.md`.

## Anti-patterns

- **Breakpoint soup.** More than five tiers means the layout is brittle. Collapse adjacent breakpoints with the same behavior into one.
- **Pixel-perfect at every px.** Don't design 12 layouts for the 12px range between `md-1` and `md`. Design 3–5 layouts and let them hold across ranges.
- **Hiding content at sm.** If it's important at desktop, it's important on mobile. Reflow, don't hide.
- **Fixed widths.** `width: 1200px` on a hero breaks at every size below `xl`. Use `max-width: 1200px; margin-inline: auto; padding-inline: <gutter>` instead.
- **Assuming breakpoint = device.** A 900px window is not a "tablet." A 375px phone in landscape hits `sm`. Design for widths, not devices.

## See also

- `references/web-output.md` — CSS variable consumption, Tailwind mapping
- `references/app-shell.md` — responsive behavior of shell primitives (sidebar collapse, tab bar, bottom nav)
- `references/composition-rubric.md` — table-to-cards collapse at sm
- `references/mobile-web.md` — mobile-first treatment of the 20 common web components
- `references/native-ios.md` / `references/native-android.md` — size-class mappings
