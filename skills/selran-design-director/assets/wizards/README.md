# Wizards & Multi-Step Flows

Three wizard primitives per direction, supplementing the `form-multi-step.html` progressive-form pattern already shipped in `assets/components/<direction>/`.

## Coverage

| Primitive | Where | All directions |
|---|---|---|
| `form-multi-step.html` | `components/<dir>/` | ✓ |
| `step-indicator.html` | `wizards/<dir>/` | ✓ |
| `review-confirm.html` | `wizards/<dir>/` | ✓ |
| `tooltip-tour.html` | `wizards/<dir>/` | ✓ |

Total: 4 primitives × 7 directions = 28 wizard snippets (+ 7 already in `components/`).

## When to use which

- **Progressive form** (`form-multi-step.html`): 2–4 steps of form fields. Signup, onboarding, order intake. Has its own progress bar inline.
- **Standalone step indicator** (`step-indicator.html`): use alone for non-form flows (tutorial, setup checklist) or above a form when you want the indicator sticky/separated.
- **Review + confirm** (`review-confirm.html`): final step of any flow with >3 inputs or irreversible actions. Shows all entries, inline Edit links, commit CTA.
- **Tooltip tour** (`tooltip-tour.html`): first-run orientation. 3–6 tips max. Always skippable.

## Canonical signup composition

```
[step-indicator]  ← sticky above form
[form-multi-step step 1]
→ step 2
→ step 3
→ [review-confirm]
→ [states/<dir>/success.html]
→ [tooltip-tour] on first dashboard view (optional)
```

## Host contract & class prefixes

Same as shells — see `assets/shells/README.md` for the full table.

## See also

- `references/wizard-flows.md` — full spec, composition, edit-from-review pattern, accessibility, direction notes, anti-patterns
