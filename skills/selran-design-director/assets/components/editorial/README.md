# Editorial — Starter Components (partial)

Three snippets tuned for the editorial direction: magazine-style typography,
rule lines instead of boxes, italic as a real typographic choice. Built from
scratch (not lifted from the technical-minimal Meridian page, which is the
wrong aesthetic).

## The three components

| File | Description |
|------|-------------|
| `hero-split.html` | Asymmetric 7/5 split — serif headline with drop-cap intro, sticky sidebar with pull-quote |
| `feature-grid-asymmetric.html` | Chapter-style section with multi-column body text and a sticky figure card on the right |
| `table-data.html` | Serif-headed table with italic categories and mono data columns; footnote in italic |
| `empty-state.html` | Rule-lined article block: quiet SVG icon, serif headline with italic accent, drop-cap body, inline recovery links |
| `error-404.html` | Full-viewport "Not found &mdash; try the archive." serif headline; mono byline rule; underlined archive link |
| `loading-skeleton.html` | 3×2 grid of article placeholders with soft horizontal bands that sweep left-to-right; 2.4s ease-in-out |
| `form-login.html` | "Members Entrance" card with serif headline + italic em, bottom-border-only inputs, forgotten link in italic, "or continue with" rule, Google + GitHub buttons |
| `form-multi-step.html` | 3-part essay signup with Roman numerals (I / II / III) as progress — italic for active, 2px accent underline; serif display title with italic em |
| `form-validation.html` | 4 fields demonstrating default / focus / valid / invalid / disabled via `data-state`; serif-italic messages with mono "Verified —" / "Error —" chip markers |

## Host contract

Each snippet assumes the host page defines:

- `--accent`, `--fg`, `--fg-muted`, `--bg`, `--bg-2`, `--border` on `:root`
- `--ff-display` — a serif display family (Fraunces, Instrument Serif, EB
  Garamond). Falls back to a serif stack if missing.
- A `.mono` class for JetBrains Mono (uppercase, tracked) — used for bylines
  and figure captions.

## How to use

Paste the snippet into your page where you want the section. All selectors
are prefixed with `.c-ed-*` (`.c-ed-login`, `.c-ed-mstep`, `.c-ed-validate`
for the form snippets) so components don't collide with each other or with
other-direction snippets.

Rebrand `{Brand}` in the copy. The tone is deliberately editorial — keep
the "reported by", "issue no.", "chapter" voice or the direction collapses
into generic landing-page territory.

## Conventions used

- Rule lines (`1px solid var(--border)`) as section dividers, never shadows
- `::first-letter` drop cap on the hero intro paragraph
- `column-count: 2` for long-form body copy
- Italic + accent color combined for emphasis within headlines
- Pull-quote uses a left accent rule, not a box
