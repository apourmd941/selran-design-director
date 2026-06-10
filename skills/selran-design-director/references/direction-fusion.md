# Direction Fusion

Fusion is the controlled borrowing of signature gestures across the seven aesthetic directions — a technical-minimal base that pulls an editorial serif for display, a dark-premium page that borrows one warm-approachable hand-drawn mark. It exists because real briefs don't always land cleanly on one starter: a data tool can need technical density AND warmth, a luxury brand can want dark-premium cinema AND editorial rhythm. Without rules, this is exactly how AI slop gets made — a little of everything, a commitment to nothing, seven fonts and four color families in one artifact. Fusion is permission to borrow, not permission to blend. A fused system still has ONE base direction; it just borrowed one or two specific elements from another. If you can't name the base in one word, you're blending, not fusing.

---

## When fusion is legitimate vs. when it's a mess

Three questions. Answer all three before you fuse. One "no" kills it.

1. **Is the base direction still intact?** Can you point to the base and say "this is editorial" (or technical-minimal, or whatever) without the borrowed element contradicting that? If the borrowed element overrides the base's signature — e.g., you "fused" editorial with brutalist motion so now the motion is instant and the type is monumental serif — you broke the base. Pick a different base.
2. **Is the borrowed element serving a specific purpose?** The borrow needs a reason in one sentence: "editorial display brings authority the sans can't," "warm neutrals soften a technical tool for creative users," "brutalist mono display adds edge a dev audience respects." If the reason is "to make it more interesting," you're decorating, not designing. Don't borrow.
3. **Would this pass as a single coherent design to someone who doesn't know the source directions?** Show it to a designer who has never read this skill. If they can describe it as one thing ("a serif-forward dev tool," "a warm editorial magazine site") rather than listing ingredients ("it's editorial plus warm plus a bit of technical"), it's a fusion. If they list ingredients, it's a mess.

If any answer is no, do not ship a fusion. Pick the strongest single direction and commit.

---

## The three fusion modes

Three modes, ordered from strictest to loosest. Pick one per project; do not mix modes.

### 1. Axis borrowing (strict)

Explicit axis-by-axis override. The base direction wins on every axis except the ones you name. At most **2 axes borrowed**. Axes are: `color`, `type.display`, `type.body`, `type.mono`, `spacing`, `motion`, `personality`.

Schema: `direction.base: X, direction.borrows: { <axis>: Y, <axis>: Z }`. Base wins on every axis not listed.

Use when the brief has specific requirements on specific dimensions ("dense like technical, warm colors like warm-approachable") — the fusion is engineered, not stylistic.

### 2. Anchor + accent (loose)

Direction A is the base; direction B contributes ONE signature gesture — a display serif, a color note, a motion signature, a single hand-drawn mark. You are not overriding an axis; you are borrowing a specific moment.

This is easier than strict axis borrowing because it's harder to overshoot — one gesture can't overwhelm a committed base. Use when the brief is impressionistic ("feels editorial but a bit warmer") rather than engineered.

### 3. Pre-blessed pairings

Named, tested combinations that work. You pick the pairing by name; the schema and borrows are already resolved. Use when the user wants fusion without learning the system, or when the brief matches a pairing the skill already knows.

See the pairings table below.

---

## Schema extension

The `direction:` field becomes either a string (legacy, still works) or an object (fusion form). The resolver reads the object and flattens to concrete tokens before any other field is applied.

Legacy form (unchanged, still canonical for single-direction systems):

```yaml
direction: technical-minimal
```

Fusion form (object):

```yaml
direction:
  base: technical-minimal
  borrows:
    type.display: editorial
    motion: warm-approachable
```

Rules for the fusion form:

- `base` is required and must be one of the seven direction keys.
- `borrows` is a map of axis → source direction. Each key names an axis (`color`, `type.display`, `type.body`, `type.mono`, `spacing`, `motion`, `personality`). Each value names a source direction.
- Maximum 2 entries in `borrows`. 3 entries requires explicit user confirmation (see Rule 1 below).
- Any axis not listed in `borrows` resolves to the `base` direction's defaults.
- Manual overrides (tokens below) still win — borrows only set defaults.

For a pre-blessed pairing, reference it by name and the resolver fills in the borrows:

```yaml
direction:
  pairing: luxury-editorial
```

An audit-friendly alternative is acceptable in briefs, docs, and commit messages — not canonical, doesn't parse programmatically, but human-readable:

```yaml
direction: "technical-minimal + editorial (display) + warm (motion)"
```

Prefer the object form in the actual `design-system.md`. Use the string form only in prose where parsing isn't happening.

---

## Pre-blessed pairings

Eight tested combinations. Each is a committed base with 1–2 named borrows and a spelled-out reason. These are the pairings the skill should reach for first when a brief asks for fusion — they've been pair-tested, WCAG-audited at their default tokens, and character-checked.

| Name | Base + borrows | Why it works | Example use |
|------|----------------|--------------|-------------|
| **Editorial-Warm** | Editorial base; `color` from warm-approachable (cream/peach/terracotta); `type.body` soft humanist serif | Editorial's grid and rule-line discipline stays intact; warm color temperature makes long-read content feel lived-in instead of institutional. Typography pairing is already adjacent — both prefer serif display and humanist body. | Nonprofit magazine, lifestyle publication, boutique hotel blog |
| **Stripe-era technical** | Technical-minimal base; `type.display` from editorial (Fraunces or similar serif) | Pre-2024 SaaS marketing — serif headline on a dense, systematic product site. Display serif buys warmth and authority the geometric sans can't; the body and spatial logic stay rigorously technical. | SaaS marketing site, B2B product page with a human voice |
| **Brutalist scale** | Brutalist base; `type.display` sizing from bold-distinctive (80–200px monumental display) | Brutalist's raw edges and monospace character stay — but the display type is sized as architecture instead of a statement-in-lowercase. For brutalist that needs to feel monumental, not zine-sized. | Art biennale site, label for a confrontational product |
| **Luxury editorial** | Dark-premium base; `type.display` from editorial (high-contrast serif); one warm-approachable signature (single hand-drawn mark or italic script accent on one phrase) | Dark-premium's cinematic restraint carries; editorial serif buys narrative authority; the one warm mark breaks the sterility that pure dark-premium can slip into. Three elements total, one is a single gesture not an axis. | Luxury watch brand storytelling, arthouse film site, premium wine |
| **Dense vibrant** | Vibrant-playful base; `spacing` from technical-minimal (4px grid, tight density, hairline borders instead of soft shadows) | Vibrant-playful's coordinated palette and motion stay; the spatial logic gets professionalized. Consumer dashboards that need both warmth and information density without feeling cramped-and-childish. | Consumer finance dashboard, fitness/habit tracker, learning platform |
| **Warm-vibrant** | Warm-approachable base; `color` borrow — add ONE saturated vibrant-playful accent on top of the warm palette | Warm-approachable stays soft and human; a single saturated pop (electric blue, citrus yellow) adds contemporary energy without turning the palette loud. Note: borrow is a single accent, not the full coordinated palette. | Modern consumer lifestyle brand, food/DTC with a pulse |
| **Quiet brutalist** | Technical-minimal base; `type.display` from brutalist (monospace display, raw edges, hairline stays) | Developer tools that want edge without abandoning calm. Technical-minimal's density and motion stay; the monospace display says "we know how you work" to an engineering audience. | Dev tooling, internal engineering dashboards, terminal-adjacent UIs |
| **Premium technical** | Technical-minimal base; `color` from dark-premium (ivory `#F4F4F5` on near-black `#0A0A0B`, gold `#D4AF37` accent) | Technical-minimal's grid and type discipline carry; the palette shift from off-white-and-emerald to black-and-gold lifts the whole system into bespoke territory. Motion stays technical-minimal fast — premium color, technical speed. | Bespoke tooling for agencies, high-end internal tools, design studio sites |

If the brief matches a pairing, reach for it by name. Users who don't know the system get a working fusion without having to specify axes.

---

## The coherence guard

Before shipping any fused design system, run the standard `references/self-critique.md` rubric (all 10 categories) AND these five fusion-specific rules. Treat any rule violation as a hard fail — revise, re-run, then ship.

### Rule 1 — Max 2 axes borrowed

`borrows` entries ≤ 2. Three entries = automatic flag. Ask the user: "You're borrowing from three directions. That's the zone where fusion becomes blend. Which of these can we drop?" Do not ship a 3-borrow system without explicit confirmation.

Four or more axes borrowed is not a fusion — it's AI slop. Refuse and force the user to pick. See anti-patterns below.

### Rule 2 — Motion direction

Motion borrowed from a **slower** direction into a **faster** one is almost always OK. The reverse is almost always wrong.

| Direction | Motion tempo |
|-----------|--------------|
| Brutalist | Instant / none |
| Editorial | Slow (200–300ms, fades only) |
| Dark-premium | Slow (250–400ms, ease-out-expo) |
| Technical-minimal | Fast (120–200ms, ease-out) |
| Warm-approachable | Medium-gentle (300–400ms, ease-in-out) |
| Vibrant-playful | Medium-playful (250–350ms, ease-out-back) |

Safe borrows: slower motion into vibrant-playful or warm-approachable (calms them). Dangerous borrows: vibrant-playful motion into editorial (breaks editorial's quiet), warm-approachable bounce into brutalist (breaks brutalist's instant-state character). Speeding up a slow direction breaks its register faster than any other single change.

### Rule 3 — Color across temperature lines

Color palettes borrowed across temperature boundaries (cool-neutral ↔ warm-neutral, or high-contrast signal ↔ muted jewel) require re-running the WCAG audit on the resolved tokens.

Cool-neutral directions: technical-minimal, brutalist.
Warm-neutral directions: editorial, warm-approachable, vibrant-playful.
Dark/high-contrast directions: dark-premium (muted), brutalist (loud), bold-distinctive (saturated).

A color swap can flip a body-text pair from AA-passing to AA-failing silently — the hue changed, the contrast ratio dropped below 4.5:1. Always run `assets/contrast-check.py` on the resolved `fg_primary` × `bg_primary`, `fg_secondary` × `bg_primary`, `accent` × `bg_primary` (large), and `bg_primary` × `accent` (button text — normal) after any color borrow.

### Rule 4 — Typography pairing

Typography borrowing requires pair-testing. Some pairs work; some look stock.

| Display | Body | Verdict |
|---------|------|---------|
| Editorial serif (Fraunces, Instrument Serif) | Humanist sans (Work Sans, Söhne) | Works — classic magazine pairing |
| Brutalist mono (Space Mono, JetBrains Mono) | Editorial humanist body | Works — edge + readability |
| Brutalist mono display | Brutalist mono body | Works — committed brutalist |
| Display serif (Canela, Tiempos) | Brutalist mono body | Rarely works — serif authority vs. mono functionality fight |
| Warm serif italic (Caveat, script) | Technical-minimal geometric sans | Rarely works — hand-drawn warmth fights geometric precision |
| Bold-distinctive monumental sans (Druk, Migra) | Technical-minimal sans | Works — scale borrow, typographic DNA compatible |

Before shipping any type-borrow fusion, pair-test at actual sizes: 48px display + 16px body, live rendered, read out loud. If the pairing fights itself, change one side.

### Rule 5 — The forbidden pair

**Never fuse brutalist and warm-approachable directly.** They are aesthetic opposites — brutalist's unapologetic rawness and warm-approachable's soft humanity cancel each other.

You can borrow ONE signature from one into the other (a single monospace label inside a warm-approachable page, or a single warm accent photo in a brutalist zine) but never two. Two borrows across this axis produces a design that feels confused to every viewer. Refuse the combination even if the user asks. Suggest `bold-distinctive` (which can hold both energy and humanity) as the probable actual answer to what they wanted.

---

## Anti-patterns

Things to flag hard or refuse outright. Fusion attracts these because it feels like permission to do everything.

- **Four+ axes from four+ directions.** This is AI slop, not fusion. Force the user to pick at most 2 borrows. Respond: "That's blending, not fusion. Pick 2 borrows max. Which 2 matter most?"
- **"A little bit of everything."** The user who says "I like editorial but also warm but also technical but also a bit of vibrant" is describing AI slop. Refuse to ship until they commit to a base. Fusion requires commitment; if they can't pick a base, run the picker (`references/direction-starters.md`) and let them choose by eye.
- **Fusing illustration libraries across directions.** Corporate-memphis illustrations on a technical-minimal page; engraving-style line art on vibrant-playful; flat illustrations in brutalist. Reject. Illustration style is a signature gesture of the base direction — it doesn't borrow cleanly. Use photography or no imagery before you mix illustration libraries.
- **Fusing motion systems.** One page with bouncy springs + ease-out + ease-out-expo + linear hovers. Pick one motion language. Motion is a single decision, not a palette.
- **Fusion as a substitute for variations.** If the user wants options, they want variations. Give them 3 variations of a committed direction (`references/variants.md`), not 3 different fusions. Fusing is "this project wants this specific combination"; variations are "I'm not sure which of these works, show me a few."
- **Fusion to avoid committing.** Some briefs come in vague because the user hasn't decided. Running fusion to paper over indecision produces mush. Push back: "What's the one thing this has to feel like above everything else?" Base the direction on that answer; optionally borrow one gesture from somewhere else.

---

## Worked example

**Brief:** "Data tool for creative agencies — needs to be dense like a pro app but should feel less cold than a typical dev tool. Agency leads are the primary user."

**Analysis:** Dense + pro = technical-minimal base. "Less cold" = warmth borrow. Agency audience respects typographic craft = a display serif earns its slot. Three cues, map to one base + two borrows.

**Decision:**

- Base: `technical-minimal` (spatial logic, motion, density, body type, information hierarchy).
- Borrow `color` from `warm-approachable` — warmer neutrals (sand/cream background instead of zinc off-white), a muted terracotta accent instead of emerald.
- Borrow `type.display` from `editorial` — Fraunces at weight 500 for display sizes, paired with the technical-minimal body sans.
- Motion stays technical-minimal. Spacing stays technical-minimal. Personality inherits technical-minimal (tabular nums, hairline borders) — no personality borrow.

**Resolved tokens:**

```yaml
direction:
  base: technical-minimal
  borrows:
    color: warm-approachable
    type.display: editorial

color:
  bg_primary:   "#FAF6EE"   # warm cream, borrowed temperature
  bg_secondary: "#F2ECDD"
  bg_tertiary:  "#E4DCC7"
  fg_primary:   "#2D1F14"   # deep warm brown, not zinc black
  fg_secondary: "#5A4A3A"
  fg_muted:     "#8A7860"
  accent:       "#B34A2A"   # muted terracotta
  accent_hover: "#923A20"
  border:       "#E4DCC7"
  border_strong: "#C9BEA5"

type:
  display: "Fraunces"         # borrowed from editorial
  body: "General Sans"        # technical-minimal default
  mono: "JetBrains Mono"      # technical-minimal default
  weights: [400, 500, 600]
  scale:
    xs: 12
    sm: 14
    base: 16
    lg: 20
    xl: 28
    xxl: 40
    display: 56
  leading:
    body: 1.55
    ui: 1.4
    display: 1.1
  tracking:
    display: -0.02
    body: 0
    caps: 0.08

spacing:
  base_unit: 4                # technical-minimal stays
  radius:
    sm: 4
    md: 8
    lg: 12
    full: 9999

motion:
  duration_fast: 150          # technical-minimal stays
  duration_base: 200
  duration_slow: 350
  easing: "cubic-bezier(0.2, 0, 0, 1)"
  reduced_motion: true

personality:
  - "tabular numerals on all numeric data"
  - "1px hairline borders instead of shadows on cards"
  - "uppercase section labels in mono, 12px, +0.08em tracking"
```

**Coherence check:**

- **Rule 1 (max 2 axes):** 2 borrows (`color`, `type.display`). Pass.
- **Rule 2 (motion):** Motion not borrowed. Pass.
- **Rule 3 (color temperature):** Cool-neutral → warm-neutral borrow — re-audit WCAG. `#2D1F14` on `#FAF6EE` = 12.1:1 (pass AA-normal). `#5A4A3A` on `#FAF6EE` = 5.8:1 (pass AA-normal). `#B34A2A` on `#FAF6EE` = 4.7:1 (pass AA-normal for body, large for UI). `#FAF6EE` on `#B34A2A` (button text) = 4.7:1 (pass AA-normal). Pass.
- **Rule 4 (type pairing):** Fraunces display + General Sans body — editorial serif display + humanist geometric body, a pair-tested combination (see pairings table, row 2). Pass.
- **Rule 5 (forbidden pair):** Not brutalist + warm-approachable. Pass.
- **Self-critique rubric:** Runs cleanly against the 10 categories in `references/self-critique.md`.

Ships. The resolved system reads as one thing — a warm, typographically considered data tool — not as ingredients.

---

## Integration with other workflows

Fusion output is a committed design system like any other. It wires into the rest of the skill without special cases.

- **Variants** — `references/variants.md` can generate 3 variations of a fused base. The variations respect the fusion: swap accent within the borrowed palette, try a second display candidate in the borrowed family, adjust spacing within the base's range. Variations do not undo the fusion.
- **Token export** — `references/token-export.md` exports fused systems the same as single-direction ones. The resolver flattens the fusion to concrete tokens before export; downstream consumers (CSS custom properties, Tailwind config, iOS/Android tokens) never see the `base` + `borrows` structure.
- **Multi-brand** — `references/multi-brand.md` children can inherit a fused parent. The child's `extends:` pointer resolves against the flattened parent tokens; the child can override any token or even re-fuse (override `direction` with a different base or different borrows). Keep inheritance one level deep — fused parent, tuned child, no grandchildren.
- **Surface split** — `references/surface-split.md` can split a fused system into marketing vs. product surfaces. The split operates on the resolved tokens; the fusion is already baked in when the split happens.
- **Self-critique** — `references/self-critique.md` runs the 10-category rubric on the rendered artifact, not the token file. A fused system passes or fails the rubric on the same criteria as a single-direction system. The fusion-specific rules in this file run *additionally*, before any artifact is rendered.
- **Starters** — `references/direction-starters.md` is the entry point for single-direction commits. Fusion is a second move: pick a starter first, then — if the brief demands it — apply one of the fusion modes above on top. Never present fusion as the first choice in the picker.
