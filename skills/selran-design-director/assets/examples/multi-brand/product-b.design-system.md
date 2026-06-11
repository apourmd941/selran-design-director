---
# Child brand B — blue accent. Demonstrates that additional personality gestures
# are additive: B inherits parent's three gestures and adds its own.

extends: "parent.design-system.md"

meta:
  project: "Product B"
  created: "2026-04-21"
  version: 1

color:
  accent:       "#2563EB"   # blue
  accent_hover: "#1D4ED8"
  dark:
    accent:       "#3B82F6"
    accent_hover: "#2563EB"

# Arrays replace. To keep the parent's gestures AND add one, list all four.
personality:
  - "tabular numerals on all numeric data"
  - "1px hairline borders instead of shadows on cards"
  - "uppercase section labels in mono, 12px, +0.08em tracking"
  - "inline code snippets get a subtle blue tint in body copy"

---

# Design intent — Product B

Blue accent for the developer-facing product in the portfolio. Adds one
additional gesture — inline code gets a subtle accent tint — because this
brand's audience spends more time reading code in docs than the siblings do.
