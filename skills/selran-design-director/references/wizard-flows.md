# Wizard & Multi-Step Flows

Multi-step flows — signup wizards, checkout, onboarding tours, guided setup — are where AI-generated UI most often fails in the middle. The happy path works; step 2 breaks the layout; the review step is a dump of fields; tooltips get in the user's way.

This reference covers the three canonical multi-step primitives and how to compose them.

---

## The three primitives

Pre-built snippets live in `assets/wizards/<direction>/`. Every direction ships all three.

| File | Pattern |
|---|---|
| `step-indicator.html` | Standalone progress indicator — horizontal dots, numbered segments, or labeled pills depending on direction |
| `review-confirm.html` | Summary screen at the end of a wizard — shows all the user's entries, lets them edit, and has a final submit |
| `tooltip-tour.html` | Guided tour with sequenced tooltips pointing at UI elements, with skip/next/prev controls |

Plus the already-existing `form-multi-step.html` in `assets/components/<direction>/`, which covers the **progressive form** pattern (2–4 step signup with step-by-step field reveal).

Total: 4 wizard primitives per direction.

## When to use which

### Progressive form (`form-multi-step.html`)
- User is filling in data across multiple steps
- Each step has 2–4 fields
- Typical use: signup, onboarding setup, project creation, order intake
- Rule of thumb: if you have >6 fields total, use multi-step rather than one long form

### Step indicator (`step-indicator.html`)
- Use alone when you have a multi-step flow that's *not* a form (e.g., tutorial, video progression, setup checklist)
- Or compose with a progressive form when you want the indicator separated from the form markup (useful for sticky-top indicators above scrollable forms)

### Review + confirm (`review-confirm.html`)
- Final step of any flow that collects data before a commit — checkout, signup, publish, submit
- Shows every entry the user has made, organized by step, with inline "Edit" links back to each step
- Primary CTA is the commit action (Pay, Submit, Publish, Create); secondary is "Back"
- Never skip this step on flows with >3 inputs or irreversible actions

### Tooltip tour (`tooltip-tour.html`)
- Use sparingly — for first-run orientation only
- 3–6 tips maximum; any more and users quit
- Must have a clear "Skip tour" option always visible, not hidden behind a menu
- Does *not* replace a proper onboarding flow — use it to highlight the main workspace after signup, not to teach the entire product

## Composition: canonical signup flow

A real signup wizard typically composes like this:

```
[step-indicator]  ← Step 1 of 4
[progressive-form step 1]  ← account basics

→ Step 2: workspace details
→ Step 3: team invites
→ Step 4: review-confirm
→ Success screen (from screen-states.md)
→ tooltip-tour on first dashboard view (optional)
```

Use the `step-indicator` component above the form if you want it sticky at the top. If you want the indicator inline with the form, the `form-multi-step.html` snippet already includes its own progress bar — don't double up.

## The "edit from review" pattern

The biggest UX mistake on review screens: requiring the user to navigate backward through each step to edit one field. The `review-confirm.html` snippet implements this correctly:

- Each section has an "Edit" link that jumps directly to that step
- After editing, the user returns to the review screen (not forward through untouched steps)
- Validation re-runs on return

If your wizard's state management doesn't support non-linear step navigation, redesign the wizard before shipping — linear-only is a trap.

## Tooltip tour: the three rules

1. **Never block input.** The user must always be able to click through / dismiss with Escape.
2. **Position tooltips away from their target on small viewports.** On mobile, use a bottom sheet instead of a popover.
3. **Persist the "dismissed" state.** Don't re-show the tour after the user skips it. Ever.

## Progress & pacing

- **Show total steps upfront.** "Step 2 of 4" is always more reassuring than "Step 2."
- **Allow backward navigation** at every step except after an irreversible commit (payment, publish).
- **Save partial state.** If the user leaves mid-wizard, they should return to the same step. This is a backend concern, but design the UI assuming it works — show "Welcome back, continue where you left off" on re-entry.
- **Estimate time on long flows.** "Takes about 3 minutes" in the intro helps completion rates.

## Accessibility baseline

- Step indicators use `role="list"` + `aria-current="step"` on the active step
- Each step announces its position ("Step 2 of 4") to screen readers on entry
- The review screen uses semantic `<dl>` (definition list) for the summary — keys + values read naturally
- Tooltip tours use `role="dialog"` + `aria-labelledby` + `aria-describedby`; focus is trapped within the tooltip until dismissed
- Tour controls (Next / Prev / Skip) are real `<button>` elements with clear labels
- Escape closes the tour from any step; the close button is the last focusable element in the tour tooltip

## Direction-specific notes

- **technical-minimal**: numbered segments, mono labels ("01 / 04"), tight spacing. Review screen uses hairline-divided `<dl>` rows.
- **editorial**: Roman numerals for step indicator (I, II, III). Review screen uses serif display for section labels.
- **dark-premium**: numbered dots with accent fill on active. Review screen has subtle accent glow on primary CTA.
- **warm-approachable**: pill-shaped indicators with word labels ("Basics · Team · Review"). Warmer copy ("Nearly there").
- **vibrant-playful**: indicator uses palette rotation — each step gets its own palette hue. Success screen pops.
- **brutalist**: indicator is a raw `[■■■□]` progress block with mono. Review screen reads like a receipt.
- **bold-distinctive**: indicator is minimal — just "STEP 2" in oversized display type. Review screen uses big serif headlines per section.

## Anti-patterns

- **No auto-advance without confirmation.** Let the user hit Next. Surprising auto-advance breaks flow.
- **No hiding "Back" until the last step.** Back should always be there except after an irreversible commit.
- **No resetting input on Back.** Persist state across navigation.
- **No validating all fields at once on first step entry.** Validate on blur per field, not eagerly on entry.
- **No tooltip tour longer than 6 tips.** Shorter = completion. Longer = skip.
- **No review screen without inline "Edit" links.** Users must navigate back directly to the field in question.
