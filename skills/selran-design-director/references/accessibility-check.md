# Accessibility check

Every design-system.md can be verified before it's used. This file specifies what to check, how to compute it, and how to report the results.

## Default entry point: `assets/a11y-audit.py`

For any a11y check on a design-system.md, run:

```
python assets/a11y-audit.py path/to/design-system.md
```

This is the **actionable mode** — it parses the YAML frontmatter (including the nested `color.dark:` sub-block, with inheritance resolved), audits body-text pairs across light and dark modes at AA thresholds, and for each failing pair proposes a minimal HSL adjustment to the foreground token that crosses the threshold.

Flags:

- `--fix`  apply the proposed token changes in place; a snapshot is saved as `design-system.md.bak` before writing
- `--json` emit findings + proposals as JSON (for programmatic use)

Exit code is 1 if any fail, 0 if clean.

For one-off pair checks, `assets/contrast-check.py` remains available as a pair-level utility (see bottom of this file).

Invoke when the user asks any of:

- "Check accessibility" / "run an a11y audit" / "is this WCAG compliant?"
- "Does this pass contrast?"
- "Verify the design system"

Automatic trigger: run the audit silently after every fresh design-system.md generation, and surface **only** the failures in the tweak menu. Don't interrupt with passes — no one needs to hear "contrast checks ok" seven times.

## What the audit checks

### 1. Color contrast (WCAG 2.1 AA / AAA)

Compute the contrast ratio for every foreground-on-background pair the design actually uses.

**Pairs to test** (minimum set — add more if the design uses them):

| Foreground | Background | Minimum |
|---|---|---|
| `fg_primary` | `bg_primary` | AA normal text (4.5:1) |
| `fg_primary` | `bg_secondary` | AA normal text (4.5:1) |
| `fg_secondary` | `bg_primary` | AA normal text (4.5:1) |
| `fg_muted` | `bg_primary` | AA large text (3:1) — muted is assumed large-only |
| `accent` | `bg_primary` | AA non-text (3:1) — buttons, focus rings |
| `accent` on solid | `fg_primary` (white or near-white text on accent) | AA normal text (4.5:1) |
| `success` / `warning` / `danger` | `bg_primary` | AA non-text (3:1) |
| `border` | `bg_primary` | Informational — must be visible but not a hard fail |

**Math** (WCAG 2.x formula):

1. Convert each hex to sRGB (0–255 per channel)
2. Normalize each channel to 0–1, then apply the sRGB-to-linear transform: `c ≤ 0.03928 ? c / 12.92 : ((c + 0.055) / 1.055) ^ 2.4`
3. Relative luminance `L = 0.2126·R + 0.7152·G + 0.0722·B`
4. Contrast ratio `= (L_lighter + 0.05) / (L_darker + 0.05)`

`assets/a11y-audit.py` wraps this math and runs it across every body-text pair in the design-system.md — use it as the entry point. `assets/contrast-check.py` is the pair-level secondary utility if you need to spot-check a single `fg/bg` combination. Don't re-derive the math inline; call the scripts.

**Thresholds:**

| Use case | AA | AAA |
|---|---|---|
| Body text (< 18pt regular or < 14pt bold) | 4.5:1 | 7:1 |
| Large text (≥ 18pt regular or ≥ 14pt bold) | 3:1 | 4.5:1 |
| Non-text (icons, borders, button outlines) | 3:1 | — |

Default target is **AA**. Only escalate to AAA if the user asks, or if the project is a long-form reading experience.

### 2. Focus ring visibility

Visible focus indicators are non-negotiable for keyboard users.

**Rule:** the focus ring color (typically `accent`) must have ≥ 3:1 contrast against `bg_primary` AND ≥ 3:1 against the component's own background.

**Fail modes:**
- Accent is too close to background (light-yellow focus on white → invisible)
- No explicit focus-ring token at all → the design will inherit browser default (ugly, but at least visible) — flag it as a soft warning

### 3. Motion / reduced-motion

The schema has `motion.reduced_motion: true`. Verify it's set. If not, flag.

Also verify motion durations don't violate common-sense limits:
- Hover feedback < 200ms (above that feels sluggish)
- Page transitions < 800ms (above that feels broken)
- Loading spinners cycle ≥ 600ms (faster looks frantic)

### 4. Type minimums

- Body base size ≥ 14px on web (16px preferred)
- Slide body min 24pt (from `overrides.slides.min_body_pt`)
- Line-height for body ≥ 1.4 (1.5–1.6 preferred for long-form)
- No fonts with weight 300 or lighter used for body text at small sizes (legibility fail)

### 5. Color-only cues

Scan the personality prose and overrides for patterns that imply color-only communication. Flag any language like "red for errors, green for success" without mention of icons/text supplements. Color-only fails WCAG 1.4.1.

This check is heuristic, not mechanical — if the prose is clean, skip.

## Report format

Output a compact, scannable report. Group by severity: **fail** (blocks), **warn** (soft issue, surface but don't block), **pass** (don't show individually — just the summary count).

```
Accessibility audit — design-system.md

✗ Fail  (2)
  — fg_muted #A1A1AA on bg_primary #FAFAF9 → ratio 2.4:1, AA large requires 3:1
  — accent #0A7A5C on solid button with fg_primary #18181B → ratio 3.1:1, AA text requires 4.5:1

! Warn  (1)
  — no explicit focus-ring token; will inherit browser default

✓ Pass  (8 checks)

Next step: bump fg_muted toward #52525B, or use it only for non-essential metadata (≥ 18pt or decorative).
```

**Tone rules for the report:**
- State the number, state the threshold, state the rule. No hedging.
- For each fail, offer **one** concrete fix — not a menu of alternatives (that's the tweak loop's job).
- Don't moralize. "Accessibility is important" adds nothing; the failure count is the message.

## When a check fails

Never silently fix. Surface the failure and wait for the user:

1. Show the report
2. Offer in the tweak menu: *"Fix contrast fails automatically"* as a one-tap option that bumps failing colors to the nearest compliant shade
3. If the user taps the auto-fix, run `a11y-audit.py --fix` — it applies minimal HSL-step changes to each failing foreground token (darker in light mode, lighter in dark mode), not a wholesale redesign

### The `--fix` flag

`python assets/a11y-audit.py design-system.md --fix` applies the proposed changes in place:

- A snapshot is written to `design-system.md.bak` first (overwriting any previous .bak). If the user doesn't like the result, restore from the backup.
- Light-mode token lines are rewritten in the top-level `color:` block; dark-mode token lines in the `color.dark:` sub-block. If a dark-mode token inherits from light but needs a different value to pass dark backgrounds, a new override line is inserted inside `color.dark:`.
- Only hex values change — the surrounding YAML structure, comments, and ordering are preserved (targeted per-token regex replace).
- Conflict handling: if a single token fails against multiple backgrounds with different severity, the script picks the largest adjustment that resolves the worst pair. Any still-unresolved pair is flagged in the report as `! conflict`.

Alternative: the user may say *"ship with the warnings"* — accessible fails stay as fails (do not auto-fix), but if the user accepts them explicitly, proceed. Note their override in a comment at the top of the design-system.md:

```yaml
# a11y: 2 AA fails accepted by user (2026-04-17)
```

## Pair-level utility: `contrast-check.py`

Use `assets/contrast-check.py` for one-off checks of a specific foreground/background pair — e.g. debugging a single override or verifying a candidate fix. It's a secondary utility; the default entry point for any design-system-wide audit is `a11y-audit.py`.

```
python assets/contrast-check.py "#18181B" "#FAFAF9"
python assets/contrast-check.py "#71717A" "#F4F4F5" --large
```

Prints ratio + AA/AAA status and exits 0 on pass, 1 on fail.

## What not to do

- Don't run the audit on every tweak — it gets noisy. Run on initial generation, then only when a color changes.
- Don't report passes individually — only the count.
- Don't block delivery on warnings, only on fails (and even fails can be user-overridden).
- Don't claim AAA compliance unless every checked pair hits AAA. Silent over-claim is worse than honest AA.

---

# Part 2 — A11y v2 (beyond contrast)

Contrast is one axis of accessibility. A contrast-clean design can still fail keyboard users, screen-reader users, motion-sensitive users, and users with motor impairments. The following rules run alongside the contrast audit and are **required** for any artifact shipped to real users.

These rules check the rendered artifact, not just the tokens. They run after Build, before the self-critique pass.

## Rule set v2

### 1. Focus indicator visibility

Every focusable element (button, link, input, custom widget with `tabindex`) must have a visible focus ring that:
- Has contrast ratio ≥ 3:1 against the adjacent background
- Is at least 2px thick (or equivalent via outline-offset + outline)
- Is NOT hidden by `outline: none` without a replacement
- Is distinguishable from the element's hover/active state

**How to check (web):** serialize the stylesheet and look for any `*:focus { outline: none }` or `*:focus-visible { outline: 0 }` without a compensating style on the same selector.

**Default pattern per direction:**

| Direction | Focus ring pattern |
|---|---|
| technical-minimal | `outline: 2px solid var(--accent); outline-offset: 2px` |
| editorial | `outline: 2px solid var(--fg); outline-offset: 3px` (thicker offset, ink tone) |
| dark-premium | `outline: 1px solid var(--accent); outline-offset: 2px; box-shadow: 0 0 0 3px rgba(gold, .15)` (subtle gold glow) |
| warm-approachable | `outline: 3px solid var(--accent); outline-offset: 2px; border-radius: inherit` |
| vibrant-playful | `outline: 3px solid var(--accent); outline-offset: 3px` (chunky, confident) |
| brutalist | `outline: 3px solid var(--fg); outline-offset: 0` (hard edge, no gap) |
| bold-distinctive | `outline: 3px solid var(--accent); outline-offset: 2px` |

**Native platforms:** iOS and Android handle focus rings natively when using standard controls (`Button`, `TextField`, etc.). If you build a custom control, implement `accessibilityTraits` + `isAccessibilityElement` (iOS) or `Modifier.focusable()` with custom focus visualization (Compose).

**Failure mode:** shipped artifact removes all focus rings ("we thought it looked cleaner"). This is a hard fail. Keyboard users cannot navigate.

### 2. Hit target size

Every interactive element must be ≥ 44×44px on touch devices (iOS minimum, WCAG 2.5.5). Android recommends 48×48dp (Material minimum). Use 48px to satisfy both.

**How to check (web):**
- For each `<button>`, `<a>` with an href, `<input type=checkbox|radio|submit>`, and element with `onclick` / `role=button`, compute rendered size including padding
- If width × height < 44 × 44 on any viewport ≤ 1024px (the touch-likely viewports), flag

**Mitigations for small visible elements** (icon buttons, tags with close X, compact toggles):

```css
.icon-button {
  position: relative;
  width: 24px;
  height: 24px;
}
.icon-button::before {
  content: "";
  position: absolute;
  inset: -10px;  /* expands hit area to 44×44 */
}
```

**Native:** iOS Buttons using `.buttonStyle(.automatic)` hit 44pt automatically. For custom buttons, `.frame(minHeight: 44)` is required. Android Compose: `Modifier.defaultMinSize(minWidth = 48.dp, minHeight = 48.dp)`.

**Failure mode:** 32×32 icon buttons, 18px toggle thumbs, tag close X at 12×12. Users with motor impairment cannot tap reliably.

### 3. Keyboard trap detector

Every interactive component must be reachable and dismissible with keyboard alone. Specifically:

- Focus moves INTO modals/drawers/menus when they open
- Focus moves OUT cleanly when they close (back to the trigger)
- `Tab` cycles within the modal (doesn't escape to underlying page)
- `Escape` closes dismissible overlays
- No element captures focus indefinitely

**How to check:** 
- Simulate keyboard nav: tab through the artifact, ensure every interactive element is reachable
- For each modal/drawer/menu: check that `aria-hidden` or `inert` is applied to background when open, restored when closed
- Check that `role="dialog"` containers have proper `aria-labelledby` and manage `initialFocus` / `restoreFocus`

**Known trap sources:**
- Custom select menus that capture focus without an exit
- Infinite scroll containers that refocus on every item render
- Carousel widgets without skip-to-end controls
- `autoFocus` on input fields that also hide the page chrome

**Implementation pattern:** use `react-focus-lock`, `focus-trap` (vanilla JS), or native `<dialog>` element (handles this by spec).

### 4. Semantic HTML enforcement

Interactive elements must use the right element:

- Buttons → `<button>`, never `<div onClick>` or `<span role=button>` if a real button will do
- Links → `<a href>`, never `<button>` styled as a link
- Form inputs → `<input>`, `<select>`, `<textarea>`, not custom div trees without roles
- Landmarks → `<main>`, `<nav>`, `<header>`, `<footer>`, `<aside>` for page structure
- Lists → `<ul>` / `<ol>` / `<li>` for lists, not sequences of divs

**How to check (web):**
- Parse the rendered HTML
- For every element with a click handler, verify it's `<button>`, `<a>`, or has `role` + `tabindex=0` + keyboard handler
- Flag `<div onClick>` without role/tabindex/keyboard handler — that's a pure mouse-only control

**Failure modes:**
- "Button" that doesn't respond to Enter/Space
- "Link" that doesn't appear in the browser's link list / doesn't open in new tab via Cmd+Click
- Custom dropdown that's invisible to screen readers because it's all divs

**Native:** SwiftUI/Compose enforce this at the type system level — you use `Button`, `Text`, `TextField`. Custom controls need explicit accessibility traits/semantics.

### 5. Screen reader labels for icon-only controls

Every icon-only button, icon link, or icon-as-control must have an accessible name:

```html
<!-- Bad -->
<button><svg>...</svg></button>

<!-- Good -->
<button aria-label="Close dialog"><svg aria-hidden="true">...</svg></button>
```

Rules:
- `aria-label` on the interactive container
- `aria-hidden="true"` on the decorative SVG (so it's not announced twice)
- Text content inside the button takes priority — only use `aria-label` if no visible text
- Images that convey information: `alt` is required; decorative images get `alt=""` (empty, not missing)

**How to check (web):**
- For every `<button>` and `<a href>`: check for accessible name via visible text OR `aria-label` OR `aria-labelledby` OR `title` (last resort)
- For every `<img>`: check for `alt` attribute (empty-string allowed for decorative)
- For every icon rendered via icon font: check for surrounding `aria-label` or visible text

**Native:** iOS uses `.accessibilityLabel("Close dialog")` on the control; Android uses `contentDescription = "Close dialog"` on the Composable or View.

### 6. Motion sensitivity enforcement

Every animated artifact must respect `prefers-reduced-motion: reduce`. 

At minimum:
- Scroll-driven animations: disabled or reduced to fade
- Auto-playing carousels: paused
- Parallax: removed
- Loading spinners: fade or static (not infinite spin, which triggers vestibular issues for some users)
- Bounce/spring animations: replaced with fade or instant

**How to check (web):**
- Audit CSS for `@media (prefers-reduced-motion: reduce)` blocks
- Every `transition:`, `animation:`, `transform:` with timing > 0ms should have a `prefers-reduced-motion` override
- The `assets/motion/{direction}.css` files ship with these overrides — verify they're present

**Pattern (preferred):**

```css
.hero-animation {
  animation: slide-in 600ms ease-out;
}

@media (prefers-reduced-motion: reduce) {
  .hero-animation {
    animation: none;
    opacity: 1;  /* jump to final state */
  }
}
```

**Native iOS:** use `@Environment(\.accessibilityReduceMotion)` to gate motion. `.animation(reduceMotion ? nil : .spring(), value: ...)`

**Native Android:** query `Settings.Global.TRANSITION_ANIMATION_SCALE` or use `LocalAccessibilityManager.current.isReduceMotionEnabled` (Compose). Or simply honor Material's built-in motion scaling.

**Failure mode:** marketing page with scroll-hijacking animations that makes users with vestibular disorders physically ill. This is a hard fail — ship it and you're excluding users.

### 7. Form accessibility

Every form field needs:
- A visible `<label>` associated via `for`/`id` (or wrapping the input)
- Error messages programmatically linked via `aria-describedby`
- Required state via `aria-required="true"` + visual indicator
- Invalid state via `aria-invalid="true"` when error is present

```html
<label for="email">Email</label>
<input 
  id="email" 
  type="email" 
  required 
  aria-required="true"
  aria-describedby="email-error"
  aria-invalid="false">
<span id="email-error" role="alert"></span>
```

**How to check:** for every `<input>`, `<select>`, `<textarea>`: verify an associated `<label>` exists.

### 8. Page structure and landmarks

Every page needs:
- `<main>` for primary content (exactly one per page)
- `<nav>` for navigation sections
- `<h1>` for the primary heading (exactly one per page)
- Heading hierarchy that doesn't skip levels (`<h1>` → `<h2>` → `<h3>`, not `<h1>` → `<h4>`)

**How to check:** count headings; verify h1 count = 1; verify no level skips.

---

## The a11y v2 audit script

Run via:

```bash
python assets/a11y-audit.py --v2 path/to/design-system.md --target path/to/built-artifact.html
```

With `--v2` the audit extends beyond contrast to check:
- Focus ring presence (parses CSS)
- Hit-target size on interactive elements (parses rendered HTML + CSS)
- Semantic HTML (div-as-button detection)
- Icon-button labels (aria-label presence)
- Motion sensitivity (prefers-reduced-motion overrides present)
- Form label associations
- Landmark presence + heading hierarchy

**Output format:** same as v1 — report grouped by severity (fail / warn / pass-count), with suggested fix for each finding. Exit code 1 on any fail.

**When to run:**
- Automatic: before any "Done" delivery message, run silently. Fails block shipping; warnings are mentioned in the delivery message.
- Manual trigger: "run a full a11y audit", "check a11y v2", "run the advanced audit"

---

## Direction-specific a11y considerations

### technical-minimal
- Focus rings must not blend into accent colors — verify 3:1 contrast against backgrounds
- Hairline borders must still be 3:1 against adjacent background (not just on white — also on hover-state backgrounds)

### editorial
- Serif fonts at small sizes struggle for low-vision users; never size body text < 16px
- Italic at small sizes compounds the problem — avoid italicized small text

### dark-premium
- Darkest-on-dark combinations need extra scrutiny — dark-premium's ink tokens are close to its gold-on-black background
- Low-emphasis text must still pass 4.5:1 minimum

### warm-approachable
- Soft pastels often fail contrast — run the full audit, don't trust "it looks soft"
- Rounded shapes obscure focus ring boundaries — use `outline-offset` to keep them visible

### vibrant-playful
- Bright-on-bright combinations frequently fail contrast — run the full audit
- Animated elements need motion-reduced fallbacks (this direction has the most motion by default)

### brutalist
- High-contrast direction has easy contrast passes — but sharp flicker animations (stutter effects) need motion-reduced overrides
- Monospace body text OK for a11y; just size ≥16px

### bold-distinctive
- Oversized display type is fine a11y-wise; body text still needs 16px+ rules
- Strong accent colors must pass 3:1 against both light and dark backgrounds

---

## Anti-patterns (hard fails)

1. `outline: none` without a replacement focus style
2. 32×32 icon buttons with no invisible hit-area expansion
3. Modals without focus trap (tab escapes to underlying page)
4. `<div onClick>` as a button
5. Icon-only buttons without `aria-label`
6. Auto-playing carousel / scroll animations without `prefers-reduced-motion` override
7. Forms without labels
8. Multiple `<h1>` tags per page
9. Skipping heading levels (`<h1>` → `<h3>` with no `<h2>`)
10. Images used as interactive elements without `role` + keyboard handler

Each of these fails the v2 audit and blocks shipping. The user can override with an explicit "ship with the warnings" — mirroring the contrast override — but the failure is recorded at the top of the design-system.md in a comment.

---

## See also

- `references/anti-patterns.md` — the AI-slop defaults list; many a11y v2 anti-patterns are also design anti-patterns
- `references/mobile-web.md` — "Cross-cutting mobile rules" section covers touch targets, hover→press, safe areas (all a11y-adjacent)
- `references/native-ios.md` / `references/native-android.md` — platform-specific accessibility (Dynamic Type, VoiceOver, TalkBack)
- `references/motion-and-interaction.md` — motion tokens include the reduced-motion defaults
