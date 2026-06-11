# Warm-Approachable — Starter Components

Nine self-contained HTML snippets for the warm-approachable direction: warm
neutrals, rounded corners throughout, serif-display headlines with italic
emphasis, paper-like soft shadows instead of hairline borders, and gentle
hover lifts.

## The nine components

| File | Description |
|------|-------------|
| `nav-top.html` | Friendly top nav: circular brand mark, serif wordmark, pill-shaped primary CTA |
| `hero-stat-led.html` | 6/5 split hero with serif display headline beside a soft-shadow figure card carrying one big number |
| `hero-split.html` | 50/50 hero, headline paired with a member testimonial panel (italic pull-quote + byline) |
| `stats-row.html` | 4 rounded stat cards, display-serif numerals in accent, italic serif labels |
| `feature-grid-asymmetric.html` | 2×3 grid with a featured wide tile; circle-backed line icons, soft shadows |
| `table-data.html` | Rounded-corner collection table with italic serif headers, pill status tags |
| `pricing-3tier.html` | 3 tiers with the middle tier lifted and inverted (accent background, cream text) |
| `cta-closing.html` | Rounded panel with two decorative accent circles, serif headline, pill CTAs |
| `footer-minimal.html` | 4-column rounded-top footer with serif italic column headings |
| `empty-state.html` | Centered block with soft organic blob shape carrying a friendly icon; italic-serif eyebrow + pill CTAs |
| `error-404.html` | Full-viewport 404 with two decorative accent-tinted circles; display-serif numeral in accent, pill recovery buttons |
| `loading-skeleton.html` | Rounded card grid (3 cols) with image thumbs + bars; gentle white-sheen sweep, paper shadow kept, pill skeletons |
| `form-login.html` | Rounded 24px card with warm soft shadow, circular brand mark, serif italic labels, pill-shaped CTA with translate-Y lift, Google + GitHub pill buttons |
| `form-multi-step.html` | 3-step flow with circular numbered dots + connector line; active dot scales up with shadow; radio-card cadence picker on step 3 |
| `form-validation.html` | 4 fields via `data-state`; circular badge icons (green check / red X), serif-italic messages, dashed border on disabled |

## Domain / copy context

Built around **{Brand}** — a boutique lifestyle/wellness brand selling curated
home goods (tea, linen, stoneware, beeswax) from named small workshops around
the world. Ten-year mender's promise, Brooklyn + Lisbon retail, 34 makers in
12 countries. Keep the "warm letter to a reader" voice — the direction
collapses into generic-landing-page territory without it.

## Host contract

Each snippet assumes the host page defines:

- `--accent` — warm terracotta / ochre / olive (saturated but not neon)
- `--accent-hover` — optional; falls back to `--accent`
- `--fg` — deep warm brown, not black
- `--fg-muted` — secondary warm gray
- `--bg` — cream / sand / peach primary background
- `--bg-2` — slightly-shifted warm surface (cards, stat tiles, pricing)
- `--border`, `--border-strong` — warm hairlines for subtle dividers
- `--ff-display` — a serif or humanist-serif display family (Fraunces,
  Commissioner fallback). Snippets degrade gracefully to a Georgia stack.
- `--radius-lg` — 16–24px. Defaults to 24px if unset.

No `.mono` class is used — this direction deliberately avoids mono.

## How to use

Paste a snippet into the page where you want that section. All selectors are
prefixed with `.c-wa-*` (the form snippets use `.c-wa-login`, `.c-wa-mstep`,
`.c-wa-validate`) so components coexist with each other and with snippets
from other directions.

Rebrand `{Brand}` wherever it appears. Keep the copy warm and specific —
named places, named people, real sounding numbers. Flatten the voice and the
direction collapses into any-lifestyle-brand.

## Conventions used

- Border-radius: `var(--radius-lg, 24px)` on cards, pills on CTAs
- Soft warm-tinted shadows (`0 4px–18px rgba(61, 40, 23, 0.05–0.18)`) instead
  of hairline borders on cards
- Gentle `translateY(-1px to -3px)` hover lifts with shadow deepening
- 300ms `cubic-bezier(0.4, 0, 0.2, 1)` transitions (longer than Technical-Minimal)
- Italic serif on section eyebrows and pull-quotes — the direction's warm accent
- Circular shapes (brand mark, icon backgrounds, decorative CTA blobs) among
  the orthogonal layout
- Responsive breakpoint at 900px (stack to single column)
