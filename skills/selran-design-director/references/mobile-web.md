# Mobile web — responsive treatment of web components

Not every project ships a native app. A **mobile web** build — a responsive site that works well on phone browsers — covers most real needs: marketing sites, read-heavy product surfaces, dashboards that people occasionally check on the go. This reference covers the mobile treatment of the existing `assets/components/<direction>/` library.

---

## Mobile web vs. native — which to pick

| Pick mobile web when | Pick native when |
|---|---|
| Content is mostly read (marketing, docs, blog) | Heavy interaction (editing, drawing, gaming) |
| The app already has a web version | Gesture-rich (pinch, drag, multi-touch) |
| Team has no iOS/Android expertise | Needs background tasks, push, deep OS integration |
| Launches in weeks, not quarters | 50+ screens of dense product UI |
| Shared code for all platforms matters | Per-platform polish matters more than shared code |

Mobile web is the pragmatic default. Native is a real investment. The skill supports both — don't assume native without reason.

## The core shift: desktop → mobile

Every component in `assets/components/<direction>/` was built desktop-first. The mobile treatment makes three kinds of changes:

1. **Hit targets** grow from 36–40px to 44–48px minimum
2. **Layouts stack** (multi-column → single-column at `sm`)
3. **Affordances shift** (hover → press, secondary actions → overflow menu)

Everything else — color, type, motion, personality — comes from the same tokens. A mobile landing page in technical-minimal is still technical-minimal, just reflowed.

## The 20 most-common web components and their mobile treatments

Each component in `assets/components/<direction>/` has a mobile-first implementation. The CSS uses mobile-first media queries — default styles are phone-sized, `@media (min-width: …)` scales up.

### 1. Nav (top-of-page)
- **Desktop:** horizontal with 4–8 links + CTA
- **Mobile:** logo left + hamburger right; tap hamburger → full-screen menu or bottom drawer
- **Hit target:** 44×44px for hamburger and each menu item
- **Direction notes:** technical-minimal uses a subtle hamburger icon; brutalist uses the word "MENU"

### 2. Hero
- **Desktop:** display-size headline, optional side image, primary + ghost CTAs
- **Mobile:** stacks fully, headline drops one scale step (e.g. `display` → `xxl`), image below headline or hidden, CTAs full-width stacked
- **Padding:** container padding reduces from 64px to 24px

### 3. Stats band (3–4 large numbers)
- **Desktop:** 3–4 column grid
- **Mobile:** 2-column at `sm`, 1-column below 400px. Numbers retain display-scale; supporting labels shrink.

### 4. Feature grid
- **Desktop:** 3-column grid
- **Mobile:** 1-column stack at `sm`, 2-column at `md`
- **Cards:** full-bleed with 16px margin, not the 24–32px of desktop

### 5. Data table
- **Desktop:** horizontal table with 4–8 columns
- **Mobile:** either horizontal scroll (`overflow-x: auto`) with frozen first column, OR collapse to cards (each row becomes a stacked card). Default is cards — scroll is confusing. See `composition-rubric.md` "Responsive collapse."

### 6. Pricing
- **Desktop:** 3-column pricing cards side-by-side
- **Mobile:** 1-column stack; "most popular" card can show a sticky ribbon instead of being visually elevated
- **Features list:** identical but indented less

### 7. CTA section (footer CTA band)
- **Desktop:** centered headline + CTA, often on a contrasting background
- **Mobile:** same, with bigger CTA (48px+ tall), headline wraps to 2–3 lines

### 8. Footer
- **Desktop:** 4-column grid (product / company / resources / legal)
- **Mobile:** single column with collapsible accordions, OR 2-column for short lists
- **Social icons:** 44×44px hit target, not 32px

### 9. Form (login, signup, contact)
- **Desktop:** centered card, 400–500px wide
- **Mobile:** full-width with 16–24px side padding
- **Inputs:** 48px tall minimum (was 40px on desktop)
- **Labels:** always above input on mobile (never side-by-side); no floating labels unless the direction explicitly allows
- **Submit button:** full-width

### 10. Multi-step form
- **Desktop:** step indicator + form fields side-by-side
- **Mobile:** stacks — step indicator on top (compact "Step 2 of 4"), form below
- **Navigation:** Previous + Next buttons stick to bottom on mobile (sticky footer)

### 11. Modal / dialog
- **Desktop:** centered overlay, 480–640px wide
- **Mobile:** **full-screen sheet** (top-down slide-in), or bottom sheet (iOS-style) for shorter content
- **Close target:** 44×44px in the top-right, ideally outside the content area

### 12. Tag / chip
- **Desktop:** 24–32px tall, text + optional remove X
- **Mobile:** 32–36px tall for comfortable tapping
- **Remove target:** grow invisible hit area to 44×44 via pseudo-element if the visible X is small

### 13. Card
- **Desktop:** fixed-width or grid-placed
- **Mobile:** full-bleed (minus container padding), stacked
- **Hover shift:** disabled (no hover on touch); press state (background shift) replaces it

### 14. List row
- **Desktop:** 40–48px row height with padding
- **Mobile:** 56–64px row height (iOS/Android native list standard)
- **Swipe actions:** optional — reveal secondary actions via `touchstart`/`pointermove`; visible chevron hints interactivity

### 15. Breadcrumbs
- **Desktop:** full chain (Home / Products / Widget)
- **Mobile:** collapse middle to `…` or show only last 2 levels ("... / Widget")

### 16. Tabs
- **Desktop:** horizontal row, all visible
- **Mobile:** horizontal **scroll** if more than 3 tabs; sticky tab header below nav
- **Tap target:** 44×44px

### 17. Dropdown / select
- **Desktop:** custom dropdown with keyboard nav
- **Mobile:** use native `<select>` — iOS and Android render a bottom sheet picker that users expect. Custom dropdowns on mobile are almost always worse.

### 18. Tooltip
- **Desktop:** hover to reveal
- **Mobile:** hover doesn't exist. Options: (a) replace with a persistent info icon that toggles on tap; (b) inline the information; (c) use `popover` API on tap. Default: inline it.

### 19. Command palette (⌘K)
- **Desktop:** centered modal with search + results
- **Mobile:** either hide (primary nav covers it) or full-screen sheet from the bottom
- **Trigger:** hamburger → "Search" menu item, or a search icon in the top bar

### 20. Toast / snackbar
- **Desktop:** bottom-right, 300–400px wide, 4–6s
- **Mobile:** bottom-center, full-width minus 16px margin, same duration
- **Safe area:** respect iOS home indicator / Android nav bar — `padding-bottom: env(safe-area-inset-bottom)`

## Cross-cutting mobile rules

### Touch targets
Every tappable element ≥ 44×44px (iOS minimum) or ≥ 48×48dp (Android Material minimum). 44px works for both. Use invisible hit-area expansion if the visible element is smaller:

```css
.icon-button {
  position: relative;
  width: 24px;
  height: 24px;
}
.icon-button::before {
  content: "";
  position: absolute;
  inset: -10px;    /* grows hit area to 44×44 */
}
```

### Hover → press
No `:hover` on touch devices. Use `@media (hover: hover)` to gate hover effects. For press feedback, use `:active` (fires on touch-down) or `@media (pointer: coarse)` to apply different state styles.

### Fixed headers and bottom bars
Use `position: sticky` (not `fixed`) when possible — it respects scroll containers better. For fixed elements, always apply `padding-top` / `padding-bottom` to the content area to prevent overlap:

```css
main { padding-bottom: calc(64px + env(safe-area-inset-bottom)); }
```

### Safe areas (notches, home indicator)
Always use `env(safe-area-inset-*)` for any fixed/full-bleed element:

```css
.bottom-nav {
  padding-bottom: env(safe-area-inset-bottom);
}
.top-bar {
  padding-top: env(safe-area-inset-top);
}
```

### Viewport meta
Every page needs:

```html
<meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
```

`viewport-fit=cover` is required for edge-to-edge rendering under notches.

### Scroll chaining
Long modals on mobile can scroll the page underneath. Prevent with `overscroll-behavior: contain` on the modal body.

### Input zoom prevention
iOS zooms into inputs with `font-size < 16px`. Either use 16px+ body font on inputs, or add `user-scalable=no` in viewport (not recommended — hurts accessibility).

### Reduced motion
All mobile animations respect `prefers-reduced-motion: reduce`. Same as desktop.

## Performance checklist

Mobile networks and devices are slower. Before shipping:

- [ ] Fonts: 2 weights max, `font-display: swap`, subset to Latin (plus any declared `i18n.scripts`)
- [ ] Hero images: AVIF → WebP → JPG fallback via `<picture>`; responsive `srcset` with 2–3 sizes
- [ ] Above-the-fold CSS inline or preloaded; below-the-fold deferred
- [ ] No render-blocking JS; defer or async
- [ ] Total JS < 150 KB gzipped for a marketing page
- [ ] LCP < 2.5s on 4G, CLS < 0.1, INP < 200ms
- [ ] No client-side routing overhead for a content page (use MPA)

See `references/performance.md` (Phase 4) for the full performance guidance.

## Anti-patterns

- **Desktop-only hover-dependent interactions.** Tooltips, hover dropdowns, hover reveals — all invisible to touch users.
- **Tiny tap targets.** 32×32px icon buttons, 24px toggle thumbs — all below accessibility minimums.
- **Fixed widths.** `width: 1200px` on a desktop layout that breaks at every screen below. Use `max-width`.
- **Horizontal scroll on tables by default.** Collapse to cards unless the data is genuinely numerical and benefits from columns.
- **Modal takeovers without back button.** Mobile users expect back to close the modal — ensure the hardware/system back does the right thing.
- **Animation-heavy marketing pages.** Heavy scroll-driven animations tank performance on mid-tier Android devices.
- **Ignoring safe-area insets.** Bottom CTAs overlapped by the iOS home indicator; content clipped by the notch.
- **iframe embeds for video.** Use native `<video>` or lazy-load iframe — iframes double memory use.

## See also

- `references/breakpoints.md` — the breakpoint scale and reflow patterns
- `references/component-patterns.md` — desktop component coverage (mobile-web is the responsive treatment of these)
- `references/app-shell.md` — responsive shell primitives (sidebar → drawer, tab bar ↔ bottom nav)
- `references/web-output.md` — the CSS variable contract applies identically to mobile
- `references/native-ios.md` / `references/native-android.md` — when mobile web isn't enough
