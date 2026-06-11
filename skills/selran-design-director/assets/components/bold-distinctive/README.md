# Bold-Distinctive — Starter Components

Nine self-contained HTML snippets for the bold-distinctive direction:
oversized display type (serif italic for emphasis), strong black-on-color
blocks, 2px solid borders, large numeric section markers (01., 02., 05., 07.)
styled as display architecture, editorial-poster energy.

## The nine components

| File | Description |
|------|-------------|
| `nav-top.html` | Heavy-serif wordmark with italic accent, small-caps tracked nav links, primary button with accent-inversion hover |
| `hero-stat-led.html` | Massive `07.` section marker, editorial metadata rule, serif headline + drop-cap lead + aside pull-quote |
| `hero-split.html` | 50/50 grid — serif headline + cover-story excerpt inside a solid accent-color panel with oversized italic glyph |
| `stats-row.html` | 4 cells, each a different surface (cream / accent-block / cream / inverted black), huge serif numbers |
| `feature-grid-asymmetric.html` | 2×3 grid with one big inverted tile, one accent tile; display-italic headlines inside each |
| `table-data.html` | Table-of-contents table; serif display titles, hover flips row to accent, numbered display-serif column |
| `pricing-3tier.html` | 3 tiers, middle = accent-block, third = inverted black; display-serif amounts with italic accent tails |
| `cta-closing.html` | Inverted section with a giant bleeding-off-edge `07.` in accent, big italic CTA headline, stacked action buttons |
| `footer-minimal.html` | Massive wordmark; link columns use display-serif at 18px, hover-italicizes to accent |
| `empty-state.html` | Oversized `00.` display-serif marker with italic accent stop; rule-bounded block, italic headline em, uppercase CTAs |
| `error-404.html` | Full-viewport "04." marker bleeding up to 440px; italic-serif subtext ("The piece you wanted isn't on this page.") + 2/1 recovery |
| `loading-skeleton.html` | 3×2 grid separated by 2px rules; each cell holds a display-serif number marker above bars that sweep in accent |
| `form-login.html` | "Welcome back." serif headline with italic em accent, 2px black borders, `6px 6px 0 var(--accent)` offset block shadow on focus, Google + GitHub buttons |
| `form-multi-step.html` | Giant `02.` accent-block marker with small-caps "Delivery" subhead; 3-node stepper row with italic active segment; serif display title with italic em |
| `form-validation.html` | 8px colored rule on the left edge of valid/invalid fields (accent vs. fg); serif-italic messages with inverted "ok"/"err" chip markers |

## Domain / copy context

Built around **{Brand}** — a quarterly magazine of essays, reporting, and
dispatches about the web. 7 issues, 214 essays, 42k readers, reader-funded
with 0 ads. Writers include Iris Park, Nuno Abreu, Hale Andrade, Junie Vidal,
Theo Arak. Keep the editorial voice — "Issue 07", "cover story", "Friday
note", drop caps, italic-for-emphasis — or the direction collapses into
generic loud-marketing.

## Host contract

Each snippet assumes the host page defines:

- `--accent` — the one saturated accent color (hot red, acid yellow,
  orange). Used as surface color, not just a text accent.
- `--fg` — the dominant ink (near-black for light mode, cream for dark)
- `--fg-secondary` — softened ink (optional; falls back to `--fg`)
- `--fg-muted` — metadata gray (optional; falls back to `--fg-secondary`)
- `--bg` — cream / paper / near-black depending on mode
- `--bg-2` — subtle off-surface (used only in table header row)
- `--ff-display` — the heavy serif or monumental sans display family
  (PP Editorial New, Fraunces 500/900, Canela). Snippets fall back through a
  serif stack.

No `.mono` utility class — the direction uses display serif for structural
emphasis instead. Radius is 0 everywhere (the direction's spatial logic).

## How to use

Paste the snippet into your page. All selectors are prefixed with `.c-bd-*`
(`.c-bd-login`, `.c-bd-mstep`, `.c-bd-validate` for the form snippets).

Rebrand `{Brand}` — in some snippets (nav and footer) the wordmark splits
`{Bra<em>nd</em>}` so the italic-accent gesture renders. Keep it.

Numbered section markers (01., 02., 05., 07.) are load-bearing across
snippets — they tie the page together as one editorial publication. Keep
them consistent if you're using multiple sections on the same page.

## Conventions used

- Display type as layout architecture: 80–200px serif numbers and headlines
- Black-on-color blocks (accent surface with cream type, inverted surface
  with accent italic) for rhythm between sections
- 2px solid borders, radius 0, no shadows
- Italic emphasis in the serif display is the primary accent gesture
- Rule lines with small-caps metadata (`Issue No. 07`, `Essay · 6,400 words`)
  at the top and bottom of sections
- 200ms `cubic-bezier(0.2, 0, 0, 1)` transitions — not sluggish, not fast
- Responsive breakpoint at 900px (grids stack, display type scales via
  clamp, borders stay 2px)
