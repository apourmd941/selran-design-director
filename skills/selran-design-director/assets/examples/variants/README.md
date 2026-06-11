# A/B variants example — three hero options from one design system

Variants share the **same tokens** but differ in **composition** (layout,
content pattern, hierarchy). They're the right answer when the user asks
"give me a few options" for a single artifact.

## Recommended layout

```
project/
├── design-system.md
├── index.html              ← default / current pick
└── variants/
    ├── hero-a.html         ← Variant A — "Stat-led"
    ├── hero-b.html         ← Variant B — "Split"
    ├── hero-c.html         ← Variant C — "Text-led"
    └── index.html          ← side-by-side comparison grid
```

## Naming variants

Name each variant by **intent**, not styling:

| Good | Bad |
|---|---|
| "Stat-led" | "Version A" |
| "Split" | "Big number hero" |
| "Text-led" | "Alternative 2" |

The user is picking a *direction* for this artifact — the name should describe
what the variant is doing, not how it looks.

## Coherence guards

Every variant must:

1. **Share tokens.** Same accent, same fonts, same spacing, same motion. No silent token shifts between variants.
2. **Hit the same hierarchy.** Headline, subline, CTA pair — all present, just arranged differently. Don't drop the CTA in one variant.
3. **Pass the same self-critique.** A variant that only passes because it's "just a variant" fails the rubric.
4. **Pass a11y individually.** Run contrast + keyboard flow per variant, not just on the average.

If a variant can't meet these, drop it from the set.

## Anti-patterns

- **Don't generate variants that differ only in color** — color is fixed by tokens.
- **Don't generate variants of different directions** — that's `direction-starters.md`'s job, different phase.
- **Don't over-produce** — 3 variants of a hero is good; 3 variants of every section on a 10-section page is noise.
- **Don't recommend one** — if the user asked for variants, they wanted to pick.

## See also

- `references/variants.md` — full pattern spec with a worked example
- `references/surface-split.md` — for marketing vs. product variance (different mechanism)
- `references/multi-brand.md` — for sibling brands (different mechanism)
- `references/direction-starters.md` — for direction-level exploration (different phase)
