# App Shell Patterns

Real apps aren't landing pages. They're chrome wrapping screens. This reference covers the seven app-shell primitives — the structural elements that decide how a user moves through your app — and how to pick between them.

Consult this when building any multi-screen app, dashboard, admin tool, or product interface. For marketing pages, stick with `component-patterns.md` (nav-top, hero, etc.).

---

## The seven primitives

Pre-built HTML snippets live in `assets/shells/<direction>/`. Every direction ships all seven. Same host-variable contract as `component-patterns.md` (`--accent`, `--fg`, `--fg-muted`, `--bg`, `--bg-2`, `--bg-3`, `--border`, `.mono`).

| File | What it is | When to use |
|---|---|---|
| `sidebar.html` | Left rail nav with sections, items, collapse toggle | Desktop app with >5 destinations |
| `topbar.html` | Horizontal app top bar — workspace switcher, search, user menu | Always — every app has one |
| `tab-bar.html` | In-page horizontal tabs for switching views within a screen | Settings, analytics dashboards, detail pages |
| `bottom-nav.html` | Fixed bottom nav with 3–5 icon+label destinations | Mobile-first web apps only |
| `command-palette.html` | ⌘K overlay — search + quick actions | Any app with >10 destinations or power users |
| `breadcrumbs.html` | Path trail: `Workspace › Project › Item` | Nested data (≥3 levels deep) |
| `split-view.html` | Master-detail pane — list left, content right | Mail, files, conversations, any "browse then read" flow |

## Composition rules

**Every app needs:** a top bar. That's it. Everything else is optional.

**Desktop apps (≥1024px) typically compose:**
- Top bar + sidebar (standard SaaS shell)
- Top bar + sidebar + tab bar (sidebar for destination, tabs for subview within a destination)
- Top bar + split view (mail/file apps — the sidebar *is* the list pane)
- Top bar + command palette (always pair palette with something — palette alone is not a nav)

**Mobile web (<768px) typically composes:**
- Top bar (minimal — logo + menu) + bottom nav
- Top bar + drawer (collapsed sidebar, not a separate component — the `sidebar.html` in each direction supports a mobile drawer mode via `[data-mode="drawer"]`)

**Tablet (768–1024px):**
- Top bar + collapsible sidebar. Use the sidebar's collapsed state by default at this width.

## Common mistakes to avoid

- **Don't nest two sidebars.** If you need two levels of nav, use sidebar + tab bar.
- **Don't put primary nav in a bottom nav on desktop.** Bottom nav is mobile-only. Desktop users read top-down-left-to-right.
- **Don't add a command palette if you only have 5 destinations.** It's clutter at that scale; surface nav directly.
- **Don't hide the user menu in a command palette.** Identity and logout live in the top bar, always.
- **Don't use breadcrumbs on flat hierarchies.** If you have 2 levels, page title is enough.

## Responsive behavior

All seven snippets honor the 900px breakpoint (consistent with existing components):
- Sidebar collapses to icons-only at ≤1100px, drawer at ≤900px
- Topbar loses secondary items (search → icon, workspace → initial)
- Tab bar scrolls horizontally on overflow
- Bottom nav only renders at ≤768px
- Command palette sizes to viewport; palette overlay never exceeds 640px width
- Breadcrumbs truncate middle segments (first › … › last) on narrow
- Split view collapses to stacked (list, tap to drill into detail) on ≤900px

## Accessibility baseline

Every shell snippet ships with:
- Semantic landmarks: `<header>`, `<nav>`, `<main>`, `<aside>`
- `aria-current="page"` on the active nav item
- `aria-expanded` on collapse toggles
- Keyboard support: Tab order follows visual order; Escape closes overlays; arrow keys navigate within nav groups
- `:focus-visible` styling per direction (matches the form-validation convention)
- Hit targets ≥44×44px on touch

## Scope prefixes

Each direction uses its prefix convention: `.c-tm-*` (technical-minimal), `.c-ed-*` (editorial), `.c-dp-*` (dark-premium), `.c-wa-*` (warm-approachable), `.c-vp-*` (vibrant-playful), `.c-br-*` (brutalist), `.c-bd-*` (bold-distinctive).

## How to pick

The brief usually tells you which shell primitives you need. Examples:

- *"build a CRM"* → topbar + sidebar + command palette + breadcrumbs (nested records)
- *"build a mail app"* → topbar + split view (the split view's left pane *is* the inbox list)
- *"build a settings page"* → topbar + tab bar (settings is one destination with many subviews)
- *"build a mobile-first expense tracker"* → topbar + bottom nav
- *"build a dashboard"* → topbar + sidebar (+ tab bar within dashboards if they have sub-views)

When in doubt: topbar + sidebar covers ~70% of SaaS apps.
