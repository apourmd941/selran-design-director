# Live iteration

A loop for pushing a design past "fine" without making the user learn the token system. The artifact is on screen. The user points at it in plain language — "the hero feels flat," "this is too tight," "make it louder" — and the skill translates that into concrete token moves, re-renders, and confirms. This exists because there's a gap between editing tokens (what the skill is good at) and editing what you see (what the user actually wants to do). This file closes it.

## When to invoke

Trigger on plain-language visual feedback once a preview or artifact is on screen. The user will not say "adjust the type scale." They'll say one of these:

- "The hero feels flat"
- "This section is too tight"
- "Make this louder"
- "The accent is too loud"
- "This feels cold"
- "Not enough energy"
- "It's bland"
- "Too busy"
- "Feels cheap"
- "Reads corporate"
- "Cluttered"
- "Sterile"
- "Heavier than I want"
- "Floaty"
- "A bit shouty"

Any of these kicks the loop. Do not ask the user to be more specific before engaging — the loop is the thing that makes them specific.

## The loop: Render → Hear → Propose → Apply

### Render

The preview must exist before the loop starts. If it doesn't, run the pipeline from `references/render-pipeline.md` first. No rendering, no loop — you cannot iterate on what neither of you has seen. If the environment can't render, say so and fall back to token-diff discussion without pretending there's a visual.

### Hear

Take the complaint at face value. Do not interpret silently. Repeat back what you heard in one short sentence and name the region — "You're saying the hero headline feels flat." This confirms you're looking at the same thing before you spend moves.

### Propose

Offer **two or three concrete paths**, not one and not five. Each path is a specific token move tied to the complaint, stated in one line. Use the language-to-token map below. Name the tradeoff honestly — "bigger display" vs "more saturated accent" vs "add a rule line" are different futures. Let the user pick.

### Apply

Show the token diff before touching the file. Re-render. Ask "better?" in one word. If yes, the change sticks. If no, revert cleanly and try the next path from the proposal. Never apply and hope.

## The language-to-token map

The centerpiece. Map fuzzy complaints to concrete tokens. Columns: user says → usually means → candidate token moves → when it means something else.

| User says | Usually means | Candidate token changes | Could also mean |
|---|---|---|---|
| "flat" | display not earning its size, or accent lukewarm | `type.scale.xxl` up (56 → 72), `type.leading.display` tighter (1.1 → 1.0), `color.accent` saturation +10%, or add a 1px rule line under the headline | Composition is bottom-heavy — check vertical rhythm before touching type |
| "loud" / "shouty" | accent too saturated, or display too dense | `color.accent` desaturate 15–25%, `type.scale.xxl` down one step, `spacing.section_gap` up 1.5× | User means copy voice, not visual — check voice before tokens |
| "tight" / "cramped" | spacing grid too small for the container | `spacing.base_unit` ×1.5 (4 → 6, 8 → 12), `spacing.radius.md` up one step, `container.max_width` up 80–120px | Copy is too long for the slot — cut copy before adding air |
| "heavy" / "boxy" | radii too sharp, weights too strong, borders too thick | `spacing.radius.md` up (4 → 8, 8 → 12), `type.weight.body` 500 → 400, `border.width` 2px → 1px | Color is doing it — a near-black `fg` reads heavy on warm bg; soften to `#1A1A1A` |
| "floaty" / "weightless" | fg contrast too soft, tracking too open, no anchors | `color.fg_primary` darker (`#3F3F46` → `#18181B`), `type.tracking.display` tighter, `type.weight.display` up one step, add a subtle shadow on the hero card | Composition lacks a grounded base — check footer/anchor weight |
| "cold" / "clinical" | cool palette, geometric sans only, no warmth in motion | `color.bg_primary` warmer (`#FAFAF9` → `#FAF5EC`), add a serif display pairing, `motion.easing` softer | Direction itself is wrong — but **do not swap direction to fix "cold."** Tweak inside it first |
| "bland" / "boring" | no signature gesture from the direction is visible | Add a signature gesture from `aesthetic-directions.md` for the current direction, bump `type.scale.xxl` one step | Copy is the bland thing — flag voice, not tokens |
| "busy" / "noisy" | too many elements competing | **Remove** an element (a card, a badge, a divider). Never add to fix busy. Cap color count at 4 | Grid is broken — check alignment before deletion |
| "cheap" | margins too narrow, display too small, too many accent colors | `container.max_width` down (wider margins), `type.scale.xxl` up two steps, collapse palette to 3 colors | Photography or iconography is the cheap thing — tokens won't save it |
| "corporate" / "generic" | no direction commitment, default geometric sans, blue accent | Apply a direction fusion or pull a signature gesture, swap display to a characterful face (serif for Editorial, condensed display for Bold) | Copy reads like AI — rewrite copy, keep tokens |
| "cluttered" | same as "busy" — too many elements | Remove. Do not add. Audit: can any two adjacent elements merge into one? | User means information architecture, not visual — flag scope |
| "sterile" | warmth absent, no imperfection, perfect grid | `color.bg_primary` warmer by 4–8° of hue, add one deliberate off-grid gesture (a hand-drawn underline, a slightly rotated badge), `motion.duration_base` up 50ms | Direction is wrong for the brief — revisit `aesthetic-directions.md` |

Rules of the map:

- Never apply more than two token changes per proposal path. Three-change bundles are the skill gold-plating.
- Prefer one change that moves the feel meaningfully over three small changes that each move it a bit.
- When a complaint has multiple paths, surface them as distinct options, not a shopping list.

## Region pointing

The user says "this section," "that button," "the hero." Resolve before proposing.

- **Single candidate** — "the hero" when there's one hero. Proceed.
- **Multiple candidates** — "the button" when there are primary and secondary CTAs. Ask one clarifying question with the exact candidates by name: "The primary CTA (Start free trial) or the secondary (See docs)?" Not an open question.
- **Ambiguous region** — "this section." Name the two most likely by their first few words of copy and ask.
- **Gesture words** — "up there," "that corner." Same resolution: name the candidates.

One clarifying question, then commit. Do not ping-pong.

## Preview-loop integration

The pipeline lives in `references/render-pipeline.md`. The loop consumes it. Rules:

- Always show the token delta before applying. The user should never be surprised by what changed.
- Always re-render after applying. Never ask "better?" without the new render on screen.
- Never apply blind. If the render fails, halt the loop and surface the failure — do not keep stacking changes on a broken preview.
- Re-render only the preview, not the full artifact. Full builds are for commit time.

## The diff format

Show the change as a compact before/after in chat. Two or three token keys, one line each, old value → new value. No full YAML dump.

```
type.scale.xxl:          56 → 72
type.leading.display:    1.1 → 1.0
```

Or with a single change:

```
color.accent:  #4F46E5 → #3730A3  (saturation -12%, value -8%)
```

Keep it dense. The user is looking at the render, not the diff.

## Iteration hygiene

Cap the loop at **5 cycles** on a single region. If you're on cycle 6 still adjusting the hero, something systemic is off — the direction, the copy, the composition. Offer a step-back: "Want to pull back and look at the whole thing, or keep pushing on the hero?" Do not narrate the counter out loud. Do not say "this is attempt 4." Just gently surface the offer when the loop hits the wall.

If the user is happy before cycle 5, stop. Do not propose improvements they didn't ask for.

## What NOT to do

- Do not apply a token change without showing the diff first.
- Do not accept "better" without knowing what changed — if the user says "better" after you silently tweaked three things, you learned nothing.
- Do not spiral into 2px micro-tweaks. If the change you propose is below the user's perceptual threshold, it's not worth proposing.
- Do not change tokens the user didn't ask about without flagging. If "fix the hero" forces a `spacing.base_unit` change that ripples through the whole page, say so before applying.
- Do not fix "cold" by swapping to a warm direction. That defeats the point — tweak inside the chosen direction. The loop is for tuning, not for re-choosing.
- Do not propose five options. Two or three. Five is abdication.
- Do not over-explain the tokens. "Display size up, leading tighter" is enough. The user doesn't need a typography lecture.
- Do not silently revert. If a change didn't land, say "reverting, trying the next path" before rendering again.

## Worked example

User is looking at a technical-minimal landing page preview. The hero is a 56px headline, a subhead, and a CTA.

> **User:** the hero feels flat

Confirm the target and offer paths.

> **Skill:** Hero headline reads flat. Three paths:
> 1. Display bigger — 56 → 72, tighten leading 1.1 → 1.0. Headline becomes the page's weight center.
> 2. Accent hotter — bump accent saturation 12%. The CTA pulls the eye, hero earns by contrast.
> 3. Add a 1px rule line above the headline with a small-caps kicker ("Dev tools, 2026"). Anchors the hero, makes it feel placed.
>
> Which one?

User picks path 1.

> **Skill:**
> ```
> type.scale.xxl:        56 → 72
> type.leading.display:  1.1 → 1.0
> ```
> Re-rendering.

Render appears.

> **Skill:** Better?

If yes, change sticks. If no:

> **Skill:** Reverting. Trying path 2 — accent saturation +12%.
> ```
> color.accent:  #0A7A5C → #0A8A68
> ```

Re-render. Ask again. Same loop.

If paths 1, 2, and 3 all fail, stop and step back:

> **Skill:** Three paths didn't land. Might be the composition, not the hero tokens — the eye needs something beneath the headline to push against. Want to look at the whole page, or keep iterating on the hero?

The loop ends when the user is satisfied or when the step-back is accepted. No further action without a fresh call.
