# Self-Critique Rubric

A pre-delivery checklist. The skill runs this silently against its own artifact before the "Done" message. If any check hard-fails, fix before shipping. If a check is borderline, flag it honestly in the delivery message.

This exists because "Anything off?" is not enough. By the time the user answers, low-grade work has already shipped. The rubric catches the issues a careful reviewer would catch.

---

## 1. When to run

- **Always.** No artifact ships without it. Small or large, UI or doc or deck — run it.
- **Silently.** Do not narrate the critique to the user. Do not list checks as they pass.
- **Between building and delivering.** Never after the "Done" message. Never instead of building. Never as a substitute for `references/anti-patterns.md` — that's a subset of this rubric (category 10 below).
- **If any hard fail appears:** fix it, then re-run the rubric. Do not ship with a known hard fail.
- **If any soft flag appears:** ship, and mention it in the delivery message using the template in section 5.

---

## 2. The rubric — 10 categories

Each category lists concrete pass/fail questions. Answer each question. One "no" in a hard-fail category = hard fail. One "no" in a soft-flag category = soft flag.

### 2.1 Typography hierarchy
- Are there **≤ 3 display sizes** actually used? (Not declared — used.)
- Does body type use **one weight by default** (400 for most directions, 500 for vibrant-playful)? Emphasis uses a second weight, not a third.
- Is **tracking/leading consistent** within each size? (Not 1.4 in one place, 1.6 in another, on the same body size.)
- Does the display size at its largest earn its size — is it the thing the eye should land on? If it's just decoratively big, downsize it.

### 2.2 Alignment discipline
- Is there a **clear grid**? Can you name the column count?
- Are **CTAs aligned to the same edge as body content** in their section? (No left-aligned paragraph with a center-aligned button below it.)
- Do **section headers align** with the content beneath? Not drifting 8px off.
- Is anything centered that shouldn't be? Default to left-align for anything over ~15 words.

### 2.3 Spacing rhythm
- Does vertical spacing **use the token grid** (4px or 8px base — whatever `spacing.base_unit` specifies)?
- Any **arbitrary values** (13px, 27px, 41px) that didn't come from the grid? If yes, why — and is the reason good?
- Is the **rhythm consistent between like-elements**? Two cards in a row should have the same internal padding; two section gaps should match.

### 2.4 Color discipline
- Is the **accent used purposefully** — for CTAs, key highlights, single strategic moments — and not sprinkled decoratively?
- Are **backgrounds restrained**? One dominant surface per section. Not three tints stacked.
- Does anything **fight WCAG AA**? Body text ≥ 4.5:1, large text ≥ 3:1, UI controls ≥ 3:1. Run the contrast check mentally on every fg/bg pair on screen.
- If palette-equipped (vibrant-playful, or Warm/Bold with palette): is each hue **assigned a job** (CTAs, charts, tags, section dividers), not used randomly?

### 2.5 Density coherence
- Does the density **match the direction**?
  - Technical-minimal → dense but calm. If sections are empty and floaty, it's wrong.
  - Editorial → breathable, generous margins. If it feels cramped, it's wrong.
  - Bold-distinctive → dramatic negative space around big type. If type is small and spacing is average, it's wrong.
  - Vibrant-playful → dynamic but generous (48–64px section gaps). If it's tight dashboard-style, it's wrong.
  - Warm-approachable → soft and breathing, never clinical.
  - Dark-premium → full-bleed, vertical rhythm, cinematic.
  - Brutalist → functional, not elegant. Tight is okay; elegant isn't.

### 2.6 Copy fit
- Does copy **length match the container**? No headline wrapping to 4 lines when it was designed for 2.
- **No orphans or widows** in headlines (a single word on its own line).
- **CTAs under 4 words.** "Get started," "Start free trial," "Book a demo." Not "Click here to learn more about our platform."
- Do supporting paragraphs **fit the column** at the intended measure (~45–75 characters per line)?

### 2.7 Voice match
- Does copy **match the direction's voice**? Technical-minimal = precise, low-adjective. Editorial = considered, with rhythm. Warm = human, first-person plural ok. Vibrant-playful = confident and friendly, never cutesy. Brutalist = blunt, lower-case often. Dark-premium = declarative, stat-led.
- If `references/voice.md` exists in the project, **cross-reference it**.
- No generic AI phrasing: "elevate," "seamlessly," "empower," "unlock," "unleash," "revolutionize." Ban on sight.

### 2.8 Overflow & responsive integrity
- Does the layout **survive at 375px, 768px, 1280px**?
- **No horizontal scroll** at any of those widths (unless intentional, e.g., a horizontal chart carousel).
- **Touch targets ≥ 44px** in the tappable dimension on mobile.
- **Images scale** without breaking layout. Nothing overflows its container.
- **Type scales down** intentionally on mobile — a 96px hero at desktop should not still be 96px at 375px.

### 2.9 State completeness
- Are **empty / error / loading states present** for any component that needs them? A table, a form, a list, a search — all need an empty state. A fetch implies a loading state.
- Do interactive elements have **hover, focus, active, disabled** styles? Focus rings visible.
- Does `prefers-reduced-motion` **kill or soften** any motion that would otherwise fire?
- For documents and decks: does the artifact handle **no-data / placeholder sections** gracefully? A blank chart slot should not show "null" or "undefined."

### 2.10 Anti-pattern check
Run `references/anti-patterns.md` against the artifact. Hard fail if it hit:
- **Inter / Roboto / Poppins / Space Grotesk** as the display or body font (unless the user explicitly asked)
- **Purple-to-pink gradient on white**
- **Emoji as UI icons**
- **Three-feature-card row** as the default hero-follower section
- **Generic stock photos** — handshakes, lightbulbs, "diverse team at whiteboard"
- **Clipart icons** in a deck
- **Transition wipes** between slides
- **Pure `#000` on pure `#FFF`** body text
- **Gradient-on-everything** (hero + buttons + cards all gradient)
- **Bouncy springs** on professional UI

If any of those slipped in, revise before delivering — no flag, just fix it. The user didn't ask for AI slop.

---

## 3. How to actually run each check

For every category, a concrete inspection technique. Don't skim; inspect.

- **Typography hierarchy** — list every `font-size` in the artifact. If more than three distinct values for display (> 24px), collapse. If more than two weights on body copy, collapse.
- **Alignment discipline** — drop an imaginary vertical line at each column edge of the grid. Does every major element sit on one of those lines? Anything that drifts off is suspect.
- **Spacing rhythm** — grep every margin/padding value. Every number should be a multiple of the `spacing.base_unit`. Flag anything that isn't.
- **Color discipline** — list every color used. Count them. If the direction is single-accent and the count is > 5 (bg, fg, muted-fg, border, accent), something leaked in. For WCAG: run `assets/contrast-check.py` on every fg/bg pair on screen, or eyeball via the built-in audit.
- **Density coherence** — imagine the direction's reference brand (Stripe for technical-minimal, The New Yorker for editorial, Duolingo for vibrant-playful). Does the artifact's density feel like it could sit next to that reference? If no, it's off.
- **Copy fit** — open the artifact at 1280px. Every headline: how many lines does it wrap to? If > 2 when designed for 1, or > 3 when designed for 2, fix copy or container.
- **Voice match** — read the copy out loud. Does it sound like a person in the direction's register, or does it sound like ChatGPT? If the second, rewrite.
- **Overflow & responsive** — open the page at 375px. If the hero headline wraps to 5 lines, it's too long. If anything horizontally scrolls, fix. If any button is < 44px tall, fix.
- **State completeness** — for each interactive or data-dependent component, ask: "what does this look like with zero data? with an error? while loading?" If the answer is "I didn't think about it," add the state.
- **Anti-pattern check** — literally scan `references/anti-patterns.md` top to bottom against the artifact. Do not skip it because "I would have noticed." You would not have.

---

## 4. Severity tiers

### Hard fail — must fix before delivery
- Any anti-pattern hit (category 10)
- Any WCAG AA contrast failure on body or UI control text
- Broken responsive at 375/768/1280 (horizontal scroll, overflowing images, unreachable content)
- Missing required state (form has no error state, table has no empty state)
- Copy in the wrong voice for the direction
- Orphans/widows on marquee headlines
- Off-grid spacing without a defensible reason

### Soft flag — mention in the delivery message
- Density slightly off the direction's norm (but within range)
- One ambiguous spacing decision you made on the fly
- A judgment call on copy length, cropping, or imagery
- A deliberate asymmetry that reads as intentional but might read as error to the user
- Color accent at the quieter/louder edge of the direction's range

### Silent pass — everything else
Don't narrate what passed. The user doesn't need a checklist.

---

## 5. Delivery message template

Default (no flags):

> Done — `<artifact>` saved to `<path>`. Anything off?

With one soft flag:

> Done — `<artifact>` saved to `<path>`. One judgment call: `<one sentence>`. Push back if it's wrong.

With two soft flags (cap at two — more than that, the flags probably aren't soft):

> Done — `<artifact>` saved to `<path>`. Two judgment calls: (1) `<one sentence>`. (2) `<one sentence>`. Push back on either.

**Rules for the flag sentence:**
- Name the choice and the reason in one breath.
- Do not apologize. ("Sorry, the hero came out lighter" → no. "Hero density came in lighter because the copy was short" → yes.)
- Do not list alternatives unsolicited. That's Phase 4's defend step, triggered by pushback.
- Do not hedge. "Maybe a bit off" is useless. Either flag it or don't.

**Example soft-flag sentences:**
- "Hero density came in lighter than technical-minimal usually sits because the copy was short."
- "Pulled the accent one shade warmer than the starter to match the photography's tone."
- "Left the stats section asymmetric on purpose; reads as considered, but could read as broken."
- "Used two display sizes instead of three — the 32px intermediate size didn't earn its slot."

---

## 6. Integration note (for SKILL.md Phase 4 wiring)

Wire this into SKILL.md Phase 4 as a step **between "Build" and "Deliver"**:

1. Build the artifact.
2. Run `references/anti-patterns.md` self-audit (existing step — stays).
3. **New:** Run `references/self-critique.md` rubric silently.
   - Hard fails → fix, re-run.
   - Soft flags → carry into the delivery message.
4. Deliver with the template in section 5 above.

Hard rule: run the critique **before writing the "Done" message, never after**. Running it after is just explaining what you should have caught. The point is to catch it before the user sees it.

The anti-patterns step remains as its own gate because it's simpler and more mechanical. The self-critique is the wider net.

---

## 7. Worked example

**Artifact:** Landing page for a developer productivity tool. Direction: technical-minimal. Built in Phase 4.

**Rubric run:**

- **Typography hierarchy** — 3 display sizes used (48/32/20). Body 16px at 400. Emphasis at 500. Pass.
- **Alignment discipline** — 12-column grid. Hero headline, subhead, CTA all left-aligned to column 2. Pass. BUT: footer copyright drifts 4px right of the grid. **Hard fail** → fix before shipping.
- **Spacing rhythm** — 4px base. All margins/paddings multiples of 4. One stat block used 18px internal padding. **Hard fail** → change to 16px or 20px.
- **Color discipline** — bg `#FAFAF9`, fg `#18181B`, accent emerald `#0A7A5C`, border `#E4E4E7`. Four colors total. Accent used on primary CTA and one inline stat highlight. Pass. Contrast all ≥ 7:1. Pass.
- **Density coherence** — dense but calm, 4px rhythm visible, hairline borders on cards. Matches technical-minimal. Pass.
- **Copy fit** — hero headline "Ship faster with fewer meetings" wraps to 2 lines at 1280px, 3 at 375px. Pass. Secondary CTA reads "Start your 14-day free trial right now" — **hard fail** (> 4 words) → change to "Start free trial."
- **Voice match** — copy is precise, low-adjective, technical. No "elevate," no "seamlessly." Pass.
- **Overflow & responsive** — tested at 375/768/1280. No horizontal scroll. Touch targets all ≥ 44px. Pass.
- **State completeness** — waitlist form has no error state for invalid email. **Hard fail** → add error state.
- **Anti-pattern check** — Geist Sans + Geist Mono (not Inter). No gradients. Lucide icons, no emoji. No three-card row. Pass.

**Actions taken:**
1. Fix footer alignment (4px drift).
2. Change stat block padding 18px → 16px.
3. Rewrite CTA to "Start free trial."
4. Add error state to waitlist form.
5. Re-run rubric — all pass. One soft flag remains: stat section asymmetry is deliberate, worth naming.

**Final delivery message:**

> Done — landing page saved to `landing-page.html`. One judgment call: left the stats section asymmetric (big number on the left, three smaller below and right) because the 12-column grid earned the tension. Push back if it reads broken.

No hard fails left. One soft flag surfaced honestly. User can accept or push back without feeling the skill hid anything.
