# Decision cards

A reference doc for the **decision-card surface** — the visual menu the skill renders whenever there's a multi-option decision pending. It's the same pattern as the Phase 1 visual direction picker, applied consistently across the rest of the four-phase arc.

The template lives at [`assets/decision-card-template.html`](../assets/decision-card-template.html). This doc covers when to use it, how to populate it, and five worked examples.

---

## What it is

A one-shot HTML artifact rendered when the skill is about to ask the user a multi-choice question. Each option is a card with a tiny rendered preview of *that option's outcome*, plus a label and a one-line rationale. The user sees the change next to the change they'd be picking instead — the diff between options is visible, not described.

The chat stays where the chat is. The decision card is a *demonstrator* + a *picking surface*, not a parallel workspace.

In environments that support the host's visual-choice mechanism (SKILL.md § capability ladder) with HTML thumbnail options (claude.ai), the user clicks a card to pick. In environments without that bridge (Claude Code, terminal-only hosts), the user reads the cards in a browser tab and types their pick into the chat. Same template either way.

---

## When to use it (default)

Render the decision card surface for any of these moments:

1. **Phase 3 tweak menu.** The default — render every tweak menu as a decision-card artifact rather than a bullet list. Two-to-three context-aware suggestions plus the two fixed anchors ("Something specific" / "Looks good — build it"). 4 cards is the sweet spot; 6 is the upper bound before the grid feels like a deck.
2. **Phase 2 → Phase 3 first show.** When the design-system.md is first generated and the user is about to enter the tweak loop, render a single big card showing a one-page slice of the artifact alongside the tokens — gives the user something to see before they're asked what to change.
3. **Reference-ingestion confirmation (Phase 22).** When the user pastes a URL or screenshot, render the extracted reference summary as a card per ingested reference: palette swatches, top direction match, caveats firing, plus a Yes/No on "use this as a starting point?"
4. **Token-tweak preview.** When the user asks a free-text question that resolves to N concrete token options ("loosen the spacing" → 4px / 6px / 8px), render the N options as side-by-side cards rather than describing them.
5. **Direction fusion proposal.** When the skill suggests borrowing an axis from another direction ("a technical-minimal base with an editorial display"), render two cards — current state vs. proposed — so the user sees the borrow.
6. **Pre-build sanity check.** Before Phase 4 build, render a single card showing the hero + one card + one button + one form field at full fidelity. The user clicks "ship it" or "back to tweak."

---

## When NOT to use it

Skip the decision-card surface and use a plain text question/list when:

- **The artifact is a trivial one-off.** A single button, a one-line poster, a 5-line code snippet — the framing of "look at the difference between options" is overhead, not help. Use the existing text-list the host's visual-choice mechanism (SKILL.md § capability ladder) form.
- **The follow-up question is a clarification inside the free-text path.** The user typed "warmer" and the skill is asking "do you mean the palette, the accent, or the type?" — those are interpretation choices, not direction-altering tweaks. A text list is faster.
- **The host environment can't display HTML.** A pure-stdout shell, a screen reader without an HTML viewer, an SSH session into a container — fall back to text.
- **There are more than 6 options.** If you need 7+, that's a sign the elicitation is wrong. Either narrow to the top 3 by relevance or fall back to a search-style picker.

---

## How to populate the template

The template is at [`assets/decision-card-template.html`](../assets/decision-card-template.html). Load it as a string and substitute the placeholders below. No templating engine required — simple string replacement is enough.

### Token placeholders (from the active design-system.md)

| Placeholder | Source | Example |
|---|---|---|
| `{{COLOR_BG}}` | `color.bg` | `#FAFAF5` |
| `{{COLOR_PAPER}}` | `color.surface` (or `color.bg` if absent) | `#FFFFFE` |
| `{{COLOR_FG}}` | `color.fg` | `#1A1A1A` |
| `{{COLOR_FG_MUTED}}` | `color.fg_muted` (or computed at 60% fg) | `#5C5C5C` |
| `{{COLOR_BORDER}}` | `color.border` | `#E5E1D8` |
| `{{COLOR_BORDER_SOFT}}` | `color.border` mixed 50% with bg | `#EFECE4` |
| `{{COLOR_ACCENT}}` | `color.accent` | `#6B0F1A` |
| `{{COLOR_ACCENT_BOLD}}` | accent darkened ~15% | `#8B1A28` |
| `{{COLOR_ACCENT_MONOCHROME}}` | fg-tinted accent (near-monochrome) | `#2A2A2A` |
| `{{TYPE_DISPLAY_FAMILY}}` | `type.display.family` + system fallbacks | `"Source Serif 4", Georgia, serif` |
| `{{TYPE_BODY_FAMILY}}` | `type.body.family` + system fallbacks | `"General Sans", -apple-system, sans-serif` |

### Header placeholders

| Placeholder | Purpose |
|---|---|
| `{{EYEBROW}}` | Uppercase eyebrow above the title — the *kind* of decision pending. Examples: `"Tweak menu"`, `"Reference confirmation"`, `"Token preview"`, `"Pre-build check"`. |
| `{{TITLE}}` | One-line serif headline asking the question. Examples: `"What should change?"`, `"Use these as a starting point?"`, `"How tight should the spacing be?"`. |
| `{{BREADCRUMB_HTML}}` | Direction · artifact · version path. May contain `<b>`, `<br>`, `&middot;`. Example: `<b>Editorial</b> · landing page hero<br />v3 · two tweaks so far` |

### Card placeholders (one fragment per option, joined into `{{CARDS_HTML}}`)

```html
<article class="card card--{{KIND}}">
  <div class="card__preview">{{PREVIEW_HTML}}</div>
  <div class="card__body">
    <h2 class="card__label">{{LABEL}}</h2>
    <p class="card__rationale">{{RATIONALE}}</p>
    <div class="card__pick">{{PICK_LABEL}}</div>
  </div>
</article>
```

| Card kind | When to use | Preview behavior |
|---|---|---|
| `bolder` | Saturating an accent | `.card__stat` colored `--accent-bold` |
| `dialed` | Reducing accent saturation | `.card__stat` colored `--accent-monochrome` |
| `swap` | Typography substitution | Preview switches from serif to sans throughout |
| `palette` | Palette shift (warmer/cooler) | Preview shows alt token swatches |
| `neutral` | Generic option, no special preview style | Default tokens |
| `commit` | Destination card (ends the loop) | Centered check mark + "ready to ship" |

### Footer placeholders (both optional)

| Placeholder | Purpose |
|---|---|
| `{{FREE_TEXT_HTML}}` | The escape-hatch link — `None of these — <a href="#free-text">describe what you want</a>.` Pass an empty string to suppress. |
| `{{NUDGE_HTML}}` | The rabbit-hole nudge after 3+ tweaks (`<b>Three tweaks in.</b> Want a quick summary?`). Pass an empty string to suppress. |

### Populator pseudo-code

```python
template = read("assets/decision-card-template.html")
tokens = current_design_system_tokens()  # from design-system.md

substitutions = {
    "COLOR_BG": tokens.color.bg,
    "COLOR_FG": tokens.color.fg,
    # ...etc
    "EYEBROW": "Tweak menu",
    "TITLE": "What should change?",
    "BREADCRUMB_HTML": f"<b>{tokens.direction.title()}</b> · landing page hero<br />v3 · two tweaks so far",
    "CARDS_HTML": "\n".join(render_card(opt) for opt in options),
    "FREE_TEXT_HTML": 'None of these — <a href="#free-text">describe what you want</a>.',
    "NUDGE_HTML": "" if tweak_count < 3 else "<b>Three tweaks in.</b> Want a quick summary?",
}

for placeholder, value in substitutions.items():
    template = template.replace("{{" + placeholder + "}}", value)

write_artifact(template)  # then present the rendered options as a visual choice (capability ladder)
```

---

## Five worked examples

### Example 1 — editorial tweak menu (the canonical case)

```
Eyebrow:     "Tweak menu"
Title:       "What should change?"
Breadcrumb:  "<b>Editorial</b> · landing page hero<br />v3 · two tweaks so far"
Cards:
  card--bolder  → "Bolder accent"        → stat-led preview, accent at #8B1A28
  card--dialed  → "Dial it back"          → stat-led preview, accent near-monochrome
  card--swap    → "Swap to a sans display" → stat-led preview, sans throughout
  card--commit  → "Looks good — build it"  → check mark + "v3 · ready to ship"
Footer:      free-text link + nudge
```

### Example 2 — reference confirmation (Phase 22)

```
Eyebrow:     "Reference confirmation"
Title:       "Use these as a starting point?"
Breadcrumb:  "Linear · 1 reference ingested · technical-minimal scored 0.71"
Cards:
  card--neutral → "Use the reference"  → palette swatches + caveats firing
  card--commit  → "Skip — start fresh" → check mark + "your brief only"
Footer:      free-text link, no nudge
```

### Example 3 — spacing preview

```
Eyebrow:     "Token preview"
Title:       "How tight should the spacing be?"
Breadcrumb:  "<b>Technical-minimal</b> · dashboard · v2"
Cards:
  card--neutral → "Tight (4px)"   → cramped grid preview
  card--neutral → "Default (6px)" → current grid preview
  card--neutral → "Generous (8px)" → roomy grid preview
  card--commit  → "Looks good — build it" → check mark
Footer:      free-text link
```

### Example 4 — direction fusion proposal

```
Eyebrow:     "Direction fusion"
Title:       "Borrow a serif display from editorial?"
Breadcrumb:  "<b>Technical-minimal</b> base · proposing 1 axis borrow"
Cards:
  card--neutral → "Keep current"       → sans display, current state
  card--swap    → "Borrow editorial display" → serif display, proposed
Footer:      free-text link, no nudge (this is a yes/no decision)
```

### Example 5 — pre-build sanity check

```
Eyebrow:     "Pre-build check"
Title:       "Ready to build?"
Breadcrumb:  "<b>Editorial</b> · landing page hero · all 4 token sections set"
Cards:
  card--commit  → "Ship it"           → hero + button + form field at full fidelity
  card--neutral → "One more tweak"   → tiny token swatch grid
Footer:      no free-text (the user already passed through the tweak loop)
```

---

## Anti-patterns

- **Don't render previews of full landing pages inside 200-px cards.** The preview is a *slice*, not the whole artifact. A stat, a button + caption, a paragraph + drop-cap, a single form field — pick the smallest slice that demonstrates the option's effect.
- **Don't put different content in different cards.** All cards in a menu show the same slice; only the variable being decided changes between them. Otherwise the user is comparing two things at once and the surface fails.
- **Don't ship a deck.** 4 cards is the sweet spot. 6 is the upper bound. If you need more options, the elicitation is wrong — narrow first.
- **Don't dress up the commit card.** It's a destination, not another tweak. Center the check mark, show the version, label it "build it" / "ship it" / "commit." Resist the urge to make it a celebration.
- **Don't show a card for a fight-the-direction option.** If the chosen direction is editorial, "add bouncy animations" is not a card. Same rule as the existing Phase 3 menu composition.

---

## Cross-references

- [`SKILL.md`](../SKILL.md) — Phase 3 § "Render the menu as a decision-card artifact (default)"
- [`assets/decision-card-template.html`](../assets/decision-card-template.html) — the template
- [`references/direction-starters.md`](./direction-starters.md) — the Phase 1 visual direction picker is the same pattern, applied to direction selection
- [`references/aesthetic-directions.md`](./aesthetic-directions.md) — token sources for the placeholders
- [`references/anti-patterns.md`](./anti-patterns.md) — every preview must pass the anti-patterns audit
