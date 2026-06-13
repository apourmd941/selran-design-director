---
# Child brand A — inherits from parent, overrides accent only.
# All type, spacing, motion, and neutral colors come from parent.

extends: "parent.design-system.md"

meta:
  project: "Product A"
  created: "2026-04-21"
  version: 1

color:
  accent:       "#0A7A5C"   # emerald
  accent_hover: "#065F47"
  dark:
    accent:       "#10B981"
    accent_hover: "#059669"

---

# Design intent — Product A

Emerald accent signals precision and quiet confidence. Everything else comes
from the parent — same type rhythm, same hairline borders, same 200ms motion.
Product A and its siblings should feel like they came from one house.
