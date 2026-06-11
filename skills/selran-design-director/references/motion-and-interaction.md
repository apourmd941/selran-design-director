# Motion and interaction

Motion isn't decoration. It's a communication layer. Use it deliberately.

## Starter snippets

Don't re-invent transitions. Each direction ships a drop-in CSS utility library at `assets/motion/{direction}.css`:

- `assets/motion/technical-minimal.css` — `.m-tm-*` prefix
- `assets/motion/editorial.css` — `.m-ed-*` prefix
- `assets/motion/dark-premium.css` — `.m-dp-*` prefix
- `assets/motion/warm-approachable.css` — `.m-wa-*` prefix
- `assets/motion/vibrant-playful.css` — `.m-vp-*` prefix
- `assets/motion/brutalist.css` — `.m-br-*` prefix
- `assets/motion/bold-distinctive.css` — `.m-bd-*` prefix

Each file covers: transitions (button, card, link underline, input focus), entrance animations (`fade-up`, `stagger`), micro-interactions (press, tap-scale, icon spin), the direction's signature gesture, and a `prefers-reduced-motion` override. They consume host CSS vars (`--motion-duration`, `--motion-easing`, `--color-accent`) with direction-tuned fallbacks. See `assets/motion/README.md` for the full index and usage.

Reach for these first. Everything below is the *intent* behind them — read it when a snippet doesn't fit and you need to tune.

## The two-value rule

Pick ONE duration and ONE easing curve as your defaults. Override only with reason.

**Default duration:** 200ms (feels instant but still smooth) — good for most UI
**Default easing:** `cubic-bezier(0.2, 0, 0, 1)` — ease-out, feels natural

For longer motions (page transitions, hero reveals): 400–600ms with `cubic-bezier(0.16, 1, 0.3, 1)` — expo-out.

## Duration by context

| Context | Duration | Why |
|---|---|---|
| Hover state | 150–200ms | Fast enough to feel responsive |
| Button click feedback | 100–150ms | Crisp |
| Dropdown / menu open | 200ms | Quick reveal |
| Modal / dialog open | 250–300ms | Deliberate |
| Page transition | 400–600ms | Enough time to orient |
| Hero animation on load | 600–1000ms | Let it breathe |
| Loading spinner cycle | 600–1000ms | Calm, not frantic |

Never use 50ms (feels broken) or 1000ms+ on UI (feels slow).

## Easing curves

```css
/* Standard ease-out — most UI */
--ease-out: cubic-bezier(0.2, 0, 0, 1);

/* Ease-in-out — state changes where both sides matter */
--ease-in-out: cubic-bezier(0.4, 0, 0.2, 1);

/* Expo-out — dramatic entrances */
--ease-expo-out: cubic-bezier(0.16, 1, 0.3, 1);

/* Linear — progress bars, loading indicators */
--ease-linear: linear;

/* Spring (for bold-distinctive direction only) */
--ease-spring: cubic-bezier(0.34, 1.56, 0.64, 1);
```

**Avoid:** default CSS `ease` (it's ease-in-out but ugly), `ease-in` alone (feels laggy), bouncy springs in professional contexts.

## Interaction states — the five every interactive element needs

1. **Default** — the resting state
2. **Hover** — subtle change, only on pointer devices
3. **Focus** — visible ring for keyboard users (NEVER remove `outline` without replacement)
4. **Active / pressed** — momentary state during click
5. **Disabled** — reduced opacity + `cursor: not-allowed`

```css
.btn {
  background: var(--color-accent);
  color: var(--color-accent-on);
  transition: background 200ms var(--ease-out);
}
.btn:hover { background: var(--color-accent-hover); }
.btn:focus-visible {
  outline: 2px solid var(--color-accent);
  outline-offset: 2px;
}
.btn:active { background: var(--color-accent-pressed); }
.btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
```

## Scroll-triggered motion

Use sparingly. If everything fades in on scroll, nothing feels special.

**Good uses:**
- Hero section reveal on page load (one time)
- Section heading appears as user scrolls to it
- Stats counter animates when visible

**Bad uses:**
- Every paragraph fades in
- Cards stagger in on every section
- Parallax on multiple elements simultaneously

Respect `prefers-reduced-motion`:

```css
@media (prefers-reduced-motion: reduce) {
  *, *::before, *::after {
    animation-duration: 0.01ms !important;
    animation-iteration-count: 1 !important;
    transition-duration: 0.01ms !important;
  }
}
```

## Loading states

- **Under 300ms** — no indicator, would flicker
- **300ms–1s** — subtle spinner or pulse
- **1s–10s** — spinner with text ("Loading…") or skeleton screens
- **10s+** — progress indicator with actual progress if known

Skeleton screens beat spinners for content-heavy pages.

## Optimistic UI

Update the UI immediately when success is likely. Reconcile on server response. On failure, show an error and either roll back or offer Undo.

This is a motion principle even though it doesn't look like one — the "motion" of the UI is forward, not waiting.

## Micro-interactions worth including

- Subtle scale on button hover (`transform: scale(1.02)` — NOT more)
- Underline slide-in on text links
- Color fade on icon hover
- Checkmark reveal after form submission
- Count-up animation on stats (once, not every scroll)

## Motion to avoid

- Bouncing anything that isn't a playful consumer app
- Rotation as the primary feedback for a click
- Flashing / strobing effects (accessibility issue)
- `animation: spin infinite` on anything that isn't actually loading
- Hover effects on touch devices (they stick weirdly)

## For animation-heavy work

If the user asks for a genuinely animation-rich site (portfolio, product launch, creative agency), break these rules deliberately. But keep the core principles:

1. Motion should communicate, not decorate
2. Reduce motion should still deliver information
3. Performance matters — use `transform` and `opacity`, not `top`/`left`/`width`
