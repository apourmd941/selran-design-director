# Brutalist — Starter Components

Nine self-contained HTML snippets for the brutalist direction: monospace
everywhere, 2px solid black borders, sharp corners (radius 0), single
confrontational accent, full hover-inversion on interactive elements, no
transitions, file-size / timestamp / version metadata displayed prominently.

## The nine components

| File | Description |
|------|-------------|
| `nav-top.html` | Top nav with 2px borders, all-caps mono links, timestamp meta, invert-on-hover |
| `hero-stat-led.html` | Huge tabular `142.` marker, 2/1 grid of manifesto + by-the-numbers list |
| `hero-split.html` | 50/50 grid, left = manifesto copy, right = three release cards with catalog numbers |
| `stats-row.html` | 4 columns with 2px dividers, tabular-num display, dashed metadata rules |
| `feature-grid-asymmetric.html` | "Five rules we don't break" — numbered rules (01–05) in bordered badges, 2×3 grid |
| `table-data.html` | Actual HTML-table-feel catalog listing with monospace, zebra stripes, sold-out/left-in-stock status borders |
| `pricing-3tier.html` | **SUBSTITUTED** — manifesto block (accent-block headline + 7 numbered rules). See notes. |
| `cta-closing.html` | Full-width inverted (black bg, light fg) CTA with accent-block verbs, stacked action buttons |
| `footer-minimal.html` | 5-column mono footer with dashed rules, file-size metadata, underline-only links |
| `empty-state.html` | 2px-bordered box with ASCII "[ EMPTY CRATE ]" art block, mono metadata, accent-block em in headline, hover-invert CTAs |
| `error-404.html` | Full-viewport 2px-bordered page with giant "PAGE MISSING." (accent-block word), 2/1 split recovery with dashed link list |
| `loading-skeleton.html` | 2px-bordered 3×2 grid; bars flash via `steps(1)` at 0.9s, staggered per cell; blinking cursor in meta rule |
| `form-login.html` | Mono `[ EMAIL ]` bracket labels, 2px borders, 4px solid accent outline on focus, hover-inversion everywhere, Google + GitHub oauth with bracket labels |
| `form-multi-step.html` | Horizontal bar stepper (`01 / SUBSCRIBER · 02 / SHIPMENT · 03 / PAYMENT`) — active segment inverted to accent; mono 2px-bordered inputs; mail-order domain |
| `form-validation.html` | 6px left stripe per state (accent for valid, fg for invalid); diagonal-pattern background on invalid; `[✓]`/`[!]` ASCII markers — **no red/green** |

## Substitutions

**`pricing-3tier.html`** — an indie record label does not sell itself via three
pricing tiers. The snippet is instead a **manifesto block** that preserves the
same structural purpose (one hero statement on the left + supporting list on
the right + primary and secondary CTAs). Drop it into any page that would
otherwise host pricing, with the same spatial footprint.

**`form-validation.html`** — brutalist disallows accent-color drift, so
the valid/invalid states skip red/green entirely. Valid fields gain a 6px
accent left-stripe; invalid fields gain a 6px `--fg` left-stripe plus a
diagonal-pattern background tint. `[✓]` and `[!]` ASCII characters carry
the semantic load instead of SVG icons.

## Domain / copy context

Built around **{Brand}** — an independent record label and zine based in
Brooklyn, since 2014. 142 releases, 38 artists, 300 copies per pressing, 68%
of revenue to artists, 0 sponsored posts. Keep the typographic voice — all
caps, `//` section labels, file-size and timestamp metadata — or the
direction collapses into generic-dark-mode territory.

## Host contract

Each snippet assumes the host page defines:

- `--accent` — the one confrontational color (safety orange, hazmat red,
  neon green, warning yellow)
- `--fg` — pure black in light mode, pure white in dark mode
- `--fg-muted` — mid gray for secondary metadata
- `--bg` — pure white in light mode, pure black in dark mode
- `--bg-2` — slight off-white / off-black for zebra stripes
- `--border` — same as `--fg` by design. Falls back to `--fg` if unset.
- `--ff-mono` — monospace family (Space Mono, JetBrains Mono, Courier New).
  Snippets fall back through the stack.

No `.mono` utility class is needed — everything is mono already. The
direction deliberately does not consume `--radius-*` tokens (radius is 0).

## How to use

Paste snippets into your page. All selectors are prefixed with `.c-br-*`
(`.c-br-login`, `.c-br-mstep`, `.c-br-validate` for the form snippets).

Rebrand `{Brand}` wherever it appears. Keep "// section label" comments, all
caps on display type, and visible file/size/count metadata. Remove those and
the direction flattens into any-dark-tech-page.

## Conventions used

- 2px solid borders on everything; no shadows; no radius; no gradients
- Full hover-inversion on buttons and links: background becomes foreground,
  foreground becomes background (sometimes via `--accent`)
- No transitions — state changes are instant (`duration: 0`)
- Underlined links, always; `text-decoration: underline` with offset
- `text-transform: uppercase` + `letter-spacing: 0.04–0.08em` on all labels
- Tabular numerals, file-size and timestamp chips, "// section X" markers
- Responsive breakpoint at 900px (grids collapse to single column but borders
  stay 2px)
