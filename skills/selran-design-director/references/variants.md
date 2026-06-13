# A/B Variant Generation

Often the right next step isn't "build me one hero" — it's "give me three hero options so I can pick." The skill supports this as a first-class pattern: generate 2–3 coherent variants of any artifact, without the variants degenerating into random reshuffles.

Distinct from the direction three-variation explorer in `direction-starters.md`, which forks *tokens*. This workflow forks *artifacts* (layouts, copy, compositions) while keeping tokens fixed.

---

## When to use

Triggered by user phrases like:

- *"give me 3 hero options"*
- *"2 versions of this pricing block"*
- *"what are some ways to lay out the feature grid?"*
- *"show me a few CTA treatments"*
- *"I want options"*

## What "variant" means here

A variant is **a different composition of the same tokens**. Tokens don't change between variants. What changes:

- **Layout** — split vs. centered vs. asymmetric
- **Content pattern** — stat-led vs. testimonial-led vs. problem-led
- **Visual weight distribution** — all-left vs. alternating vs. balanced
- **Proportions** — 50/50 vs. 60/40 vs. 70/30
- **Hierarchy** — big display + small body, or medium display + stat support
- **Copy tone** — within the direction's voice, but with different framing (see `voice.md`)

What doesn't change:
- Accent color, font families, spacing scale, radius, motion duration (these come from tokens)
- Direction's signature gestures (mono eyebrows for technical-minimal, Roman numerals for editorial, etc.)
- Anti-patterns enforcement (all variants pass the same audits)

## Variant count

- **2 variants** — when the brief has a clear primary, and you're offering an alternative for contrast
- **3 variants** — the default. Enough to show range, not so many they overwhelm
- **More than 3** — only on explicit request. Diminishing returns set in fast; users usually pick from 3

## Composition rules

The rule is **meaningful variance, not random noise**. Three bad variants:

- Variant A: `<h1>` at 56px, 2-col grid
- Variant B: `<h1>` at 54px, 2-col grid
- Variant C: `<h1>` at 58px, 2-col grid

Those are noise. Three good variants:

- Variant A: stat-led hero (one giant number as the focal point)
- Variant B: split hero (headline left, sample UI right)
- Variant C: text-led hero (typography does the work, no imagery)

Each represents a genuinely different answer to "what should this hero be doing?"

## Rendering variants

For web artifacts, render each variant as a separate HTML file (or a separate preview in a comparison grid). The skill's `render-pipeline.md` pattern scales: variant files go into `variants/` next to the design-system.md, with an `index.html` grid showing all three side by side.

```
project/
├── design-system.md
├── index.html              ← the user's current pick (or the default)
└── variants/
    ├── hero-a.html
    ├── hero-b.html
    ├── hero-c.html
    └── index.html          ← side-by-side grid for comparison
```

For documents and decks, the variants go into the same directory with a suffix (`.hero-a.pptx`, `.hero-b.pptx`). For posters and social cards, `.v-a.png`, `.v-b.png`.

## Coherence guards

Every variant must:

1. **Share tokens.** No silent token shifts. If a variant looks dramatically different from another, it's because of layout/content, not because one used a different accent.
2. **Hit the same hierarchy.** Headline, subline, CTA pair — all three are still present, just arranged differently. Don't drop the CTA in one variant.
3. **Pass the self-critique rubric.** Each variant runs through the same checks as a single-artifact build. A variant that only passes because it's "just a variant" fails the rubric.
4. **Pass a11y.** Contrast, hit targets, keyboard flow — per variant.

If a variant can't pass these, drop it from the set. Three coherent variants beat four where one is broken.

## Framing the presentation

When delivering variants, give each a short name that describes its *intent*, not its styling. Good:

- **Stat-led** — anchor on one big number
- **Split** — headline + visual proof
- **Text-led** — typography as hero

Bad:
- **Version A** — doesn't help the user pick
- **Big number hero** — describes the output, not the intent
- **Alternative 2** — numeric ordering with no semantic

Then ask the user to pick. Don't recommend one unless the brief clearly points to one.

## Variant ↔ surface split ↔ multi-brand

Clear conceptual boundaries so these don't blur:

| Pattern | Tokens | Artifacts | Use when |
|---|---|---|---|
| **Variants** | Same | Multiple of the same artifact | Exploring layout/composition options |
| **Surface split** | Same base + per-surface overrides | One per surface (marketing site, product UI) | One brand, multiple surfaces |
| **Multi-brand** | Different (parent + children) | Per-brand artifacts | Siblings that share 80% of a system |
| **Direction variations** | Different (fork 1–2 axes) | Direction thumbnails | Picking a direction early on |

You can stack: a multi-brand project can have per-brand variants. A surface-split brand can have variants per surface. Just don't mix the mechanisms in your head — each answers a different question.

## Anti-patterns

- **Don't generate variants that differ only in color.** Unless the user explicitly asked to explore color, color is fixed by tokens.
- **Don't generate variants of different directions.** That's the direction explorer's job — see `direction-starters.md`.
- **Don't over-produce.** 3 variants of a hero is good; 3 variants of every section on a 10-section landing page is 30 artifacts no one will review.
- **Don't recommend one without reason.** If the user asked for variants, they wanted to pick. Defer unless the brief clearly points to a primary.
- **Don't skip the self-critique on variants.** Variance is not an excuse for weak work.

## Example: three hero variants for a developer tool landing page

User brief: "Build me a landing page for a developer CI tool, direction: technical-minimal. Give me three hero options."

The skill produces:

**Variant A — "Stat-led"**
- Giant `clamp()`-sized number: `12 min` (build time improvement)
- Mono eyebrow: `BUILD ACCELERATION`
- Short subheadline, single primary CTA

**Variant B — "Split"**
- Headline left (`Ship faster without breaking things`), code-sample UI right showing a CI log streaming
- Small mono eyebrow above headline
- Primary + ghost CTAs

**Variant C — "Text-led"**
- No visual. Pure typography: oversized headline spanning the column, followed by a tight 2-line subheadline
- Single primary CTA below
- Relies on whitespace and type rhythm

All three use the same emerald accent, General Sans + JetBrains Mono, hairline borders, 200ms motion. All three pass WCAG. User picks one; skill commits to it; builds the rest of the page against the winner.

## See also

- `surface-split.md` — marketing vs. product variance
- `multi-brand.md` — inheritance across sibling brands
- `direction-starters.md` — direction-level exploration (different mechanism, different phase)
- `self-critique.md` — the rubric every variant must pass
- `voice.md` — copy variance within a direction
