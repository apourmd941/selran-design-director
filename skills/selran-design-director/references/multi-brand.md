# Multi-Brand — Inheritance for Sibling Brands

Some projects ship multiple brands that share 80% of a design system. A parent company with three product brands. A design agency managing five clients in the same workspace. A white-label platform customizing per-tenant. The skill's inheritance model handles this without forcing you to maintain five near-identical token files by hand.

---

## When to use inheritance

Use it when **all three** are true:
1. Two or more brands share the majority of their tokens (type scale, spacing, radius, motion, often body font)
2. The differences are bounded — primarily accent color, project name, sometimes display font, sometimes one signature gesture
3. You want changes to the shared parts to propagate across all brands automatically

**Not inheritance:** two separate brands with no real overlap → just build two separate design-system files.

**Not inheritance:** one brand with a marketing surface + a product surface → see `surface-split.md`.

**Not inheritance:** one brand exploring 3 variants for A/B → see `variants.md`.

## The model

A child `design-system.md` declares `extends: <path-to-parent>` at the top. The child can then override any field. Anything not overridden is inherited from the parent.

```yaml
---
# Child file — child-a.design-system.md
extends: parent.design-system.md

meta:
  project: "Child A"
  # created + version inherited from parent

color:
  accent:       "#2563EB"  # child overrides accent
  accent_hover: "#1D4ED8"
  # all other color tokens inherited

# type, spacing, motion, personality — all inherited

---

# Design intent

## What makes Child A different from the parent

Two sentences about what the accent shift represents — why this child.
```

## Resolution rules

When the skill loads a child, it computes the effective tokens by:

1. Reading the parent file (and resolving its own `extends` if the parent itself inherits — though deep chains are discouraged; keep it to one level)
2. Shallow-merging the child on top:
   - Scalar fields (e.g., `direction`, `color.accent`) — child wins
   - Object fields (e.g., `color`, `type`, `spacing`) — merged key-by-key; child wins per-key
   - Array fields (e.g., `color.palette`, `personality`, `type.weights`) — child **replaces** the parent array entirely; use `personality` carefully (more below)
   - `overrides.marketing` / `overrides.product` — merged object-by-object

3. Running the same validation the base would (WCAG audit, schema check)

## Inheriting personality

Personality is the one tricky one. It's an array of short strings. By default, arrays replace. But personality gestures usually want to **add** to the parent, not replace.

Convention: if a child wants to preserve parent personality and add its own, include both in the child array explicitly. The skill does not auto-merge personality arrays. This is deliberate — auto-merging would make it hard to *remove* an inherited gesture.

```yaml
# Parent
personality:
  - "tabular numerals on all numeric data"
  - "1px hairline borders instead of shadows on cards"

# Child — keeps first gesture, drops second, adds its own
personality:
  - "tabular numerals on all numeric data"
  - "duotone icon treatment in child brand"
```

If you want strict accumulation without the copy-paste, we can add `extends_personality: true` to the schema later. For now, be explicit.

## When to create a parent

Pull common tokens up into a parent when you have **two or more** children that would share them. If you only have one brand right now, don't preemptively create a parent — you'll guess wrong about what's shared.

**Parent typically owns:**
- `direction` (the aesthetic family)
- `type.display`, `type.body`, `type.mono`, `type.weights`, `type.scale`, `type.leading`, `type.tracking`
- `spacing.base_unit`, `spacing.radius`
- `motion.easing`, `motion.duration_*`, `motion.reduced_motion`
- `color.bg_*`, `color.fg_*`, `color.border*` (the neutral stack)
- `overrides.web.container_max`, `overrides.web.grid_columns`
- Brand-wide personality gestures

**Children typically override:**
- `meta.project` (always — this is what names the brand)
- `color.accent` + `color.accent_hover`
- Optionally `color.palette` (if some children are multi-hue and others single-accent)
- Optionally `type.display` (when one child wants a different display face)
- Per-brand personality additions
- Per-brand `overrides.marketing.personality` or `overrides.product.personality`

## Anti-patterns

- **Don't inherit direction inconsistently.** If three children all extend a `direction: technical-minimal` parent, they should all read as technical-minimal. If one child needs to feel brutalist, it shouldn't extend the technical-minimal parent — just make it a separate file.
- **Don't daisy-chain `extends`.** Parent → child is fine. Parent → child → grandchild works but gets hard to reason about. If you need three levels, your parent is probably wrong.
- **Don't inherit only to change 10 fields.** If the child overrides more than it inherits, it's not a child — it's a standalone.
- **Don't couple brands.** If brands genuinely need different motion, different spacing, different direction — they're not siblings. Ship them separately.
- **Don't skip per-child WCAG audit.** A new accent needs to pair with the parent's bg/fg. The skill runs the audit per resolved child, so failures surface per brand. Don't push to production with failing children.

## Example use cases

### Holding company with three product brands

- `parent.design-system.md` — technical-minimal baseline, Inter Tight body, 4px grid, 200ms motion
- `product-a.design-system.md` → `extends: parent` — emerald accent (`#0A7A5C`)
- `product-b.design-system.md` → `extends: parent` — blue accent (`#2563EB`)
- `product-c.design-system.md` → `extends: parent` — amber accent (`#D97706`) + one additional personality gesture

Change the type scale in parent → all three children update. Change the emerald in product-a → only product-a updates.

### White-label platform (per-tenant)

- `platform.design-system.md` — your platform's core tokens
- `tenant-acme.design-system.md` → `extends: platform` — acme's accent + logo token (in meta) + two personality gestures
- `tenant-beta.design-system.md` → `extends: platform` — beta's accent + different display font

You ship new platform features with a single token update; per-tenant branding follows.

### Design agency with multiple clients

One direction (e.g., editorial) as a parent; each client is a child with their own accent, display font, and personality gestures. Shared infrastructure tokens (scale, spacing, motion) stay in the parent — updated once, applied across the roster.

## Resolution + exports

Token exports (CSS, Tailwind, Figma Variables, Style Dictionary) run on the **resolved** tokens, not the child file alone. So you get a complete, standalone export per child — no inheritance chain to resolve at consume-time. This keeps downstream (engineering, Figma) simple.

If you want a single multi-brand export (e.g., one Tailwind config with three theme sections, one Figma Variables file with three modes), the `export.multi-brand` workflow handles that. See `token-export.md` for the pattern. Short version: the skill collapses the children into a mode-aware export where each child is a mode of the same variable set.

## See also

- `surface-split.md` — for marketing vs. product within a single brand
- `variants.md` — for generating 2–3 exploratory variants of one artifact
- `token-export.md` — how multi-brand exports work
- `design-system-schema.md` — the `extends` field spec
