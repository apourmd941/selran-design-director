# Vibrant-Playful — Starter Components

Nine self-contained HTML snippets for the vibrant-playful direction: warm
off-white background, a coordinated 5-hue palette used deliberately for
categorical color-coding, soft-rounded corners (12–20px), medium weight (500)
for confident friendliness, ease-out-back motion without sproing.

## The nine components

| File | Description |
|------|-------------|
| `nav-top.html` | Nav with gradient brand mark; link underlines pull palette hues by `data-cat` |
| `hero-stat-led.html` | Centered hero with soft blurred palette glows, gradient-clipped display number and accent words |
| `hero-split.html` | 50/50 hero paired with a mini canvas preview — 4 palette-tinted tiles + a cursor pill |
| `stats-row.html` | 4 cards with a 4px top rule in the card's palette hue + matching label + "+24%" pulls in the hue |
| `feature-grid-asymmetric.html` | 2×3 grid, one tile per feature assigned a palette hue (icon, tag, link all match) |
| `table-data.html` | Table with 4px palette-hued left rule per row, color-matched avatar + tag, hover arrow in the hue |
| `pricing-3tier.html` | 3 tiers, middle tier inverted (dark surface) and lifted; each tier gets its own palette top-rule |
| `cta-closing.html` | Dark-surface panel with two blurred palette glows, 3 category chips above the headline |
| `footer-minimal.html` | 4-column footer; each column heading tinted in its palette hue (section color-coding) |
| `empty-state.html` | Centered block with gradient organic blob (palette[0→2→3]), floating accent dots, gradient-clipped em + pill CTAs |
| `error-404.html` | Full-viewport 404 with oversized rotated gradient "?" tile + two blurred palette glows; chip status label, pill CTAs |
| `loading-skeleton.html` | 3-col card grid with palette-hue top rule + palette-tinted bars that hue-shift in a 2.8s cycle (staggered per card) |
| `form-login.html` | Card with blurred palette glows (`::before`/`::after`), gradient brand mark, accent-hue `box-shadow` ring on focus, Google + GitHub OAuth buttons |
| `form-multi-step.html` | 3-step flow; each step's progress dot takes a palette hue (`data-cat`); swatch-style radio picker for accent color on step 3; ease-out-back motion |
| `form-validation.html` | 4 fields via `data-state`; valid state uses `--p2` (lime-green palette hue) rather than stock green; inline SVG icon per message |

## Domain / copy context

Built around **{Brand}** — a collaborative illustration studio for design
teams (think Figma × Procreate × Miro). Real-time canvas, 140k creators,
brand kits, handoff links, Lottie export. Categories used across sections:
Canvas, Community, Templates, Export, Teams — each consistently bound to a
palette hue.

## Host contract

Each snippet assumes the host page defines:

- `--accent`, `--accent-hover` — primary CTA color (usually `palette[0]`)
- `--fg` — softened near-black (not pure)
- `--fg-muted` — secondary gray
- `--bg` — warm off-white (cream / `#FFFBF5`)
- `--bg-2` — slightly-shifted surface for cards and table headers
- `--border`, `--border-strong` — subtle hairlines
- `--p0`, `--p1`, `--p2`, `--p3`, `--p4` — the five palette hues. Fallback
  to `--accent` if a hue is missing, so the snippets don't crash when dropped
  onto a non-palette host.
- `--radius-md` (12px), `--radius-lg` (20px) — defaults baked in

Some snippets use `color-mix(in oklab, …)` for tinted chip backgrounds —
supported in modern browsers; degrade to a flat accent tint otherwise.

## How to use

Paste the snippet into your page. All selectors are prefixed with `.c-vp-*`
(`.c-vp-login`, `.c-vp-mstep`, `.c-vp-validate` for the form snippets).

Sections use `data-cat="0"` … `data-cat="4"` to pick a palette hue
consistently. Keep categories mapped the same way across the page —
"Marketing is always p0, Product is always p1" — or the system collapses into
random-color-for-every-element soup (the direction's anti-pattern).

## Conventions used

- Border-radius: `--radius-md` (12px) everywhere, `--radius-lg` (20px) on
  large surfaces, pills (`9999px`) only on tags
- Medium weight (500) for buttons, labels, links — never 400, rarely 700
  (reserved for display)
- 280ms `cubic-bezier(0.34, 1.1, 0.64, 1)` soft ease-out-back — a hint of
  overshoot, never a spring
- Palette-hued ornaments: top rules on stat/pricing cards, left rules on
  table rows, tinted chip backgrounds via `color-mix`
- One gradient moment per section max (hero display text, CTA headline em,
  brand mark); everywhere else colors are flat
- Responsive breakpoint at 900px (stack to single column)
