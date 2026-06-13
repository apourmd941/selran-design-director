# Critique & de-slop an existing design

Design Director normally *generates* work that doesn't look AI-made. This
workflow points the same discipline **outward** — at a design that already
exists (yours or someone else's): critique it, score how AI-generated it reads,
and de-slop it. It is the inbound mirror of `self-critique.md` (which judges the
skill's own output) and reuses `anti-patterns.md` (the slop list) and
`live-iteration.md` (the language→token fixes). No competitor ships this: the
others avoid the AI look *at generation time*; this removes it from work that's
already on the page or in the deck.

## What this is — and is NOT

- It **is** a critique that names where a design reads as generic/AI-default and
  turns each tell into a concrete, system-grounded fix.
- It is **not** a tool for defeating "AI-detection." The goal is design that is
  *distinctive and considered*, not deceiving a classifier. Say this plainly if
  the user frames it as evasion — and proceed to make the work genuinely better.

## Phase A — Ingest (be honest about grounding)

Pick the richest input available; each rung trades grounding for convenience:

| Input | How to read it | Grounding |
|---|---|---|
| **Live URL** | Load it with the available browser/preview tooling (Claude-in-Chrome / Claude Preview / Playwright MCP); screenshot it AND read **computed styles** (font-family, hex, px, radius) + the DOM | **Highest** — exact values are facts |
| **Pasted HTML/CSS** | Read the code directly | High — exact tokens |
| **.pptx / .docx** | Read the theme part (`theme1.xml`: fonts + color scheme) and slide/section layouts; or extract text + describe | Medium-high — fonts/colors exact, layout described |
| **Screenshot / image** | Read it visually | **Lowest** — you can judge composition/feel but must *infer* fonts/hex; say "appears to be…", never assert a value you can't read |

State the grounding in the report. A screenshot critique that asserts "uses
Inter at #6366F1" as fact is exactly the false confidence DD exists to kill —
write "the body face reads like Inter or another neutral grotesk."

## Phase B — Two-layer critique

**Layer 1 — Design quality.** Run the `self-critique.md` 10-category rubric
*outward* (typography hierarchy, alignment, spacing rhythm, color discipline,
density coherence, copy fit, voice match, overflow/responsive, state
completeness, anti-patterns). Tag each finding 🔴 hard / 🟡 soft / 🟢 strength.
Be specific and locate it ("the three pricing cards are dead-center with
identical weight — no focal point").

**Layer 2 — the AI-tell scorecard (the differentiator).** Score the artifact
against the `anti-patterns.md` signatures. Each detected tell = one row: what it
is, where it appears, and how strongly it signals "default AI output." The
canonical tells:

| Tell | Signature |
|---|---|
| Default fonts | Inter / Roboto / Space Grotesk / Poppins / system-only; Calibri/Aptos in decks |
| Purple→pink gradient on white | `#667eea`→`#764ba2` and relatives — the single loudest tell |
| Centered-everything | center-aligned hero + section + CTA, no asymmetry |
| Three-feature-card row | "here are three things we do," icon + heading + line, ×3 |
| Uniform geometry | one border-radius and one shadow on every element |
| Emoji as UI icons | 🚀✨🔒 standing in for an icon set |
| Corporate Memphis | faceless wavy-limbed purple people |
| Rainbow accents | one different color per card/section |
| Pure `#000` on `#FFF` | harsh max-contrast body text; untinted `#888` grays |
| Generic motion | everything fades-up 0.5s on scroll, identically |

Close Layer 2 with a verdict: **reads as AI-made: HIGH / MEDIUM / LOW**, citing
the 2–4 tells that drive it. If the design is genuinely distinctive, say LOW and
don't manufacture tells — a padded scorecard is its own slop.

## Phase C — De-slop (turn tells into fixes)

For each tell, propose the concrete remediation, mapped to the design system —
reuse `anti-patterns.md`'s "instead use" lists and `live-iteration.md`'s
language→token map:

- Inter/Roboto → a characterful pairing (General Sans / Geist / Fraunces…),
  display + body with real contrast.
- Purple-pink gradient → a single-saturation accent from a chosen direction;
  gradient used *once* if at all.
- Centered-everything → asymmetry, left-alignment, a real focal point.
- Three-card row → break the grid; vary weight/size; merge or re-sequence.
- Emoji icons → a real icon set (per `imagery.md`).
- Uniform radius/shadow → a deliberate elevation scale.
- Pure #000/#fff → lifted black `#0A0A0B` on warm off-white `#FAFAF9`.

If no direction is named, infer the closest fit from what the artifact is
*trying* to be and say which you assumed (the user can redirect).

## Phase D — Apply or rebuild

- **Editable input** (pasted HTML/CSS, or a file you can write): offer to apply
  the fixes and emit the **de-slopped version** — same content, slop removed,
  tokens coherent. Show the before/after token diff (old → new) first.
- **Non-editable** (screenshot / URL you can't modify): deliver the prioritized
  de-slop spec, then offer to **rebuild it in a DD direction** — this hands off
  to the normal build flow (`web-output.md` / `slide-output.md` etc.) using the
  inferred or chosen direction. "Want me to rebuild this landing page in
  technical-minimal?"
- **Decks (.pptx):** the de-slop maps to `slide-output.md` — replace
  Calibri/Aptos + center-stacked title/bullet + clip-art with the direction's
  type, asymmetric layouts, and real iconography.

Then run the standard `self-critique.md` on anything you *produce* (a de-slopped
artifact is DD output and ships under the same bar), and stop with the usual
tight delivery — *"De-slopped — AI-tell index HIGH → LOW; 5 fixes applied.
Anything off?"*

## Honesty guardrails (carry into every critique)

1. **Grounding before assertion** — infer from screenshots, state facts only
   from computed styles / code.
2. **No padded findings** — a good design gets a short report and a LOW verdict.
3. **De-slop ≠ detector evasion** — the deliverable is better, more distinctive
   design; never frame it as fooling a classifier.
4. **Critique the work, not the author** — "this layout reads as a default
   template," not "whoever made this used AI."
