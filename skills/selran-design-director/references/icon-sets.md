# Icon sets & starter wordmarks

DD's anti-emoji rule says "use a real icon set, not 🚀✨🔒" — but until now it
told you to *find* one, never to *make* one. This generates a cohesive icon
family (and a starter wordmark) **matching the direction**, so a brand's icons
share its line weight and radius instead of looking borrowed.

## Custom icon set

What makes a set look *designed* (vs a grab-bag) is **one spec applied
consistently** — that consistency is mechanical, which is exactly what
generation is good at. Derive the spec from the design system:

| Spec | Source | Example (technical-minimal) |
|---|---|---|
| Grid | fixed | 24×24 with 2px live-area padding |
| Stroke weight | the direction's line feel | 1.5px |
| Corner radius | `--radius` family | 1.5px (slightly soft) |
| Caps / joins | direction | round vs miter |
| Style | direction | outline / two-tone / filled |
| Optical sizing | fixed | hairlines thicken at small sizes |

Per direction: **technical-minimal** → 1.5px outline, geometric, slight radius;
**editorial** → thin or filled, refined; **bold / vibrant** → heavier stroke or
filled, confident; **dark-premium** → fine outline, restrained; **brutalist** →
thick, hard corners, raw; **warm** → rounded caps/joins, friendly.

**Output:** an SVG sprite / set, each glyph on the same grid, **retinting via
`currentColor`** (or `--fg` / `--accent`) so icons follow the theme. Ship the
spec alongside so new icons stay consistent. Feeds the brand book's iconography
section (`brand-book.md`) and `imagery.md`'s icon pick ("…or generate a set").

**Scope honesty:** generate the **common UI set** the product actually needs
(nav, actions, status, objects) — 20–40 glyphs — not an open-ended thousand.
Name what you didn't draw so gaps are visible, not silently missing.

## Starter wordmark (upgrades the brand book's placeholder)

`brand-book.md` punts on logo with a labeled starter wordmark; this makes that
starter real (while staying honest about its ceiling):

- **Derive from the type system** — set the brand name in the display face,
  tune letter-spacing/weight, optionally construct a monogram or a single
  ligature/joint as the one distinctive move.
- **Emit the variants the brand book needs** — light / dark / mono, horizontal /
  stacked, and a favicon crop — all SVG, retinting via CSS vars.
- **Stay starter-grade and say so.** A wordmark from the type system is a strong
  *placeholder and a fast option*, not a finished identity. A real logo is often
  a human-designer deliverable — DD's lasting value is the **usage system around
  the mark** (clear-space, misuse, variants from `brand-book.md`), not claiming
  the mark itself is final. Never present a generated logo as definitive.

## Discipline

- **Consistency is the product.** A set where one icon has a different stroke or
  grid reads as broken — verify every glyph against the spec before shipping.
- **No emoji, no clip-art, no mixed libraries** (`anti-patterns.md`,
  `imagery.md`). A generated set must be *more* coherent than a borrowed one, or
  it isn't worth generating.
- **Icons are copy too** — pair icon-only controls with `aria-label`
  (`accessibility-check.md` / `microcopy.md`), and don't rely on an icon alone
  to carry meaning a label should.
- **Logos stay starter-grade** — see above; the honesty rule is non-negotiable.
