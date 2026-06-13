# Multi-brand example — holding company with sibling products

Three files:

- `parent.design-system.md` — the shared baseline (technical-minimal, Inter Tight, 4px grid, 200ms motion, neutral near-black accent)
- `product-a.design-system.md` → `extends: parent` — emerald accent
- `product-b.design-system.md` → `extends: parent` — blue accent + one additional personality gesture

## What to notice

1. **Children are tiny.** Each child file only declares what differs. No type scale, no spacing, no motion — all inherited.
2. **Scalars and objects shallow-merge.** `color.accent` is overridden; `color.bg_primary` is inherited.
3. **Arrays replace, not merge.** `product-b.design-system.md` lists all four personality gestures (three inherited + one new) because arrays replace by default. This is deliberate — it lets a child *remove* a parent gesture if needed.
4. **Dark mode inherits too.** Parent's `color.dark` block is inherited; children only override `dark.accent` and `dark.accent_hover`.

## Change propagation

- Edit the type scale in `parent.design-system.md` → both children update.
- Edit the emerald in `product-a.design-system.md` → only Product A updates.
- Add a third child (`product-c.design-system.md`) with `extends: parent.design-system.md` and a different accent → done, no duplication.

## See also

- `references/multi-brand.md` — full inheritance spec, resolution rules, anti-patterns
- `references/surface-split.md` — for marketing vs product within **one** brand (not siblings)
- `references/variants.md` — for 2–3 variants of **one** artifact (not brands)
