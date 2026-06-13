# Brand book

Produce a presentation-grade brand guidelines document from a `design-system.md`
— cover, logo usage, color, typography, spacing, voice, imagery, application
examples, and a do/don't grid — output as a self-contained HTML book, a print
PDF, and a `.docx`.

**Why this is a lead, not a catch-up.** No competitor generates a real brand
book. Anthropic's `brand-guidelines` skill only *re-skins* artifacts in
Anthropic's own identity and explicitly omits **logo usage, voice/tone, and
spacing** — the three sections that make a brand book a brand book. DD has all
three already (`design-system.md` spacing scale, `voice.md`, and — for logo —
at least mark/clear-space rules), and one structural advantage nobody else has:
**DD's brand book is the literal system it enforces.** The book and the built
artifacts can't drift, because both read the same `design-system.md`. It's
documentation that is true by construction, not aspiration.

## Input

- **Required:** a `design-system.md` (the brand book is its presentation form).
- **Optional but recommended:** brand name + tagline; a logo/wordmark asset
  (SVG/PNG path); a one-line mission and any voice notes.
- No design system yet? Run the normal Phase 1–3 flow to author one first — a
  brand book without a system underneath is just a mood board.

## The book — 10 sections

Fill `assets/brand-book-template.html` (token-driven, styled *in the chosen
direction* so the book demonstrates the brand). Each section, and its source:

1. **Cover** — brand name, tagline, the direction's signature gesture as the hero. (`direction` + `personality`)
2. **Brand essence** — what it feels like in 3 adjectives + a sentence. (`personality`, `aesthetic-directions.md`)
3. **Logo** — clear-space (express as × the logo's cap-height / x-unit), minimum size, approved color variants (on light / dark / photo), and a **misuse grid** (don't stretch, recolor, add effects, rotate, crowd). *No logo supplied?* Generate a clean **wordmark** from the type system and label it a starter — never fabricate a logo and present it as final.
4. **Color** — primary / secondary / neutral roles with hex, plus an **accessible-pairings table** (which fg-on-bg combinations pass WCAG AA — from `accessibility-check.md`) and the `color.dark` variants.
5. **Typography** — display + body families, the type scale (size / weight / leading per step), usage rules (never body in a display face), web-font + fallback chain. (`typography.md`)
6. **Spacing & layout** — base unit, the spacing scale, radius, grid principles. *(The section `brand-guidelines` omits.)*
7. **Voice & tone** — sentence length, verb energy, vocabulary, headline/body/CTA patterns, forbidden phrases, and tone-per-context (success / error / marketing). From `voice.md`. *(The section `brand-guidelines` omits.)*
8. **Imagery & iconography** — photography style, illustration voice, icon library pick, the anti-emoji rule. (`imagery.md`, `illustration.md`)
9. **Application examples** — real mockups built from DD's own component library in the direction (`assets/components/<direction>/`): a web hero, a card, a button set, a mobile screen, an email, a slide. This is the proof the system works — pulled from the same components the build flow ships.
10. **Do & Don't** — the slop defaults to refuse from `anti-patterns.md` (Inter, purple-on-white gradient, centered-everything, emoji-as-icons, Corporate Memphis) shown as a **don't grid paired with the right do**.

## Output formats

- **HTML** (primary): self-contained multi-section book, inline CSS, no network,
  dark/light via `prefers-color-scheme`, print-friendly (`@media print` page
  breaks per section). The book is styled in the direction — it *is* a sample of
  the brand.
- **PDF**: render the HTML via `render-pipeline.md` (print-grade, one section
  per page).
- **.docx**: via `document-output.md`, for stakeholders who live in Word — same
  content, Word styles mapped from the tokens.

Default to HTML; produce PDF/`.docx` on request or when the user names a format.

## Discipline

- **A view, never a second source of truth** — the book reads `design-system.md`;
  regenerate when tokens change. If the book and the system disagree, the system
  wins.
- **Don't fabricate a logo** — wordmark-from-type as a labeled starter, or mark
  the section "supply a logo." A brand book that invents a final logo is worse
  than one honest about the gap.
- **The book is a DD artifact** — run it through `anti-patterns.md` +
  `self-critique.md` before delivering. A brand book that itself commits slop
  has no authority.
- **Lead with the differentiators** — logo usage, voice, and spacing are the
  three sections competitors skip; they're not optional here.
