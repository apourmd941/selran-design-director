# Technical-Minimal — Starter Components

Nine standalone HTML snippets, extracted from the tested Meridian landing page.
Each file is a self-contained `<style>` block plus markup — drop it into any
page that satisfies the host contract below.

## The nine components

| File | Description |
|------|-------------|
| `nav-top.html` | Sticky top nav: logo mark + 3 links + ghost sign-in + primary CTA |
| `hero-stat-led.html` | Giant `clamp()`-sized stat hero with mono context label, caption, and CTA row |
| `hero-split.html` | 50/50 text + sample ledger split — quieter alternative to the stat hero |
| `stats-row.html` | 3-stat band with deltas, 1px hairline separators |
| `feature-grid-asymmetric.html` | 2×3 grid with one tile spanning two rows (the featured one) |
| `table-data.html` | 4-column data table with mono currency column and category pills |
| `pricing-3tier.html` | Starter / Operator / Scale, featured middle tier on `var(--bg-2)` |
| `cta-closing.html` | Bold 2-column closing CTA with headline on the left, actions on the right |
| `footer-minimal.html` | 2-column minimal footer with copyright and policy links |
| `empty-state.html` | Centered ~520px block: hairline-bordered glyph tile, mono eyebrow, headline + CTA pair, keyboard-shortcut hint |
| `error-404.html` | Full-viewport 404 with mono status label, huge tabular numeral, primary "Back to dashboard" + ghost "Search", requested-path chip |
| `loading-skeleton.html` | 3-column card grid (2 rows) with hairline borders and subtle opacity-pulse bars; staggered delays; `prefers-reduced-motion` fallback |
| `form-login.html` | ~400px card: logo mark + headline, email/password with labels, submit CTA, forgotten-password link, "or continue with" divider, Google + GitHub OAuth buttons |
| `form-multi-step.html` | 3-step signup with thin segmented progress bar, "Step 02 / 03" mono eyebrow, 2–3 fields per step, Back (disabled on step 1) + Next/Submit |
| `form-validation.html` | 4 fields demonstrating default / focus / valid / invalid / disabled via `data-state`; inline SVG check + error icons; submit disabled while invalid |

## Host contract

Each snippet assumes the host page defines these CSS variables on `:root`:

- `--accent` — primary action color (the single saturated accent)
- `--fg` — primary text on `--bg`
- `--fg-muted` — secondary text / labels
- `--bg` — page background
- `--bg-2` — slightly-raised surface (featured tier, table header)
- `--bg-3` — optional third surface slot
- `--border` — hairline dividers and card edges

And a `.mono` class that switches to JetBrains Mono (or similar), uppercase,
letter-spacing ~0.08em, small size (~12px). The host page owns the `@font-face`.

## How to use

Paste a snippet into the page where you want that section. Components are
CSS-scoped with per-component class prefixes (`.c-nav-top`, `.c-hero-stat`,
`.c-hero-split`, `.c-stats`, `.c-feat`, `.c-table`, `.c-price`, `.c-cta`,
`.c-footer`, `.c-tm-login`, `.c-tm-mstep`, `.c-tm-validate`) so multiple
snippets coexist without collision.

Rebrand `{Brand}` where it appears (logo wordmark + eyebrow label). Numbers
are realistic Meridian-flavored placeholders — swap to your domain's figures.

## Conventions used

- No shadows; 1px hairlines in `var(--border)` do the work
- `font-variant-numeric: tabular-nums` on numeric columns
- `clamp()` for the stat-hero display number
- 200ms `cubic-bezier(0.2, 0, 0, 1)` transitions
- Responsive breakpoint at 900px (stack to single column)
