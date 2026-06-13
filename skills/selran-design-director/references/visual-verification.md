# Visual verification loop

`self-critique.md` judges the artifact from its *code and intent*. That misses a
whole class of failure: a component that compiles but renders blank, text that
overflows its container at 375px, contrast that fails once the real font paints,
a focus ring that's invisible, a layout that breaks in dark mode. Visual
verification closes the gap — **render → screenshot → check the actual pixels →
correct → re-verify.** It's the design analog of running the app instead of just
reading it.

**When to run it.** Whenever a renderer is available and the artifact is a
web/HTML surface: as the final gate before "Done" on any non-trivial build, and
on request ("does it actually render right?", "verify it", "check it in the
browser"). For non-renderable outputs (a `.docx`, a static poster spec) or when
no browser/preview tooling exists, fall back to code-level `self-critique.md`
and **say so** — a code pass is not a verified pass.

## The loop

1. **Render.** Produce the artifact's preview via `render-pipeline.md` /
   `assets/preview-template.html` (or point at the live URL if it's already
   running).
2. **Screenshot the real thing.** Use the available browser/preview tooling
   (Claude Preview, Claude-in-Chrome, or Playwright MCP) to capture it at the
   **key breakpoints** (≥ mobile 375 + desktop 1280, plus any the design
   declares) and in **both themes** (light + dark via `prefers-color-scheme`).
   This is the same grounded path `critique-existing.md` uses for URLs.
3. **Read computed styles, not the source.** Pull actual `font-family`, hex,
   spacing, radius from the rendered DOM. Verification checks what *painted*,
   not what the code *intended* — that's the whole point.
4. **Run the checks against the pixels.** Apply `self-critique.md` (the 10
   categories) and `accessibility-check.md` (contrast on rendered colors, focus
   visibility, hit-target sizes, overflow/scroll) to each screenshot. Findings
   here are higher-confidence than code-reasoned ones because they're observed.
5. **Visual diff (two kinds).**
   - **Intent drift:** does the rendered output match the `design-system.md`
     tokens? A heading that should be 48px rendering at 32px, an accent that
     came out the wrong hue — flag the delta (the visual analog of spec-drift).
   - **Variant diff:** when comparing A/B variants (`variants.md`), diff the
     *rendered* screenshots, not the side-by-side HTML — which actually reads
     better, not which markup looks tidier.
6. **Correct and re-verify.** A rendered failure becomes a token/layout fix via
   `live-iteration.md`'s map; apply, re-render, re-screenshot, re-check. **Cap
   the loop at ~3 cycles** — if it can't converge, surface the issue to the user
   rather than thrashing.

## What this catches that code-reading can't

- Blank / collapsed render (component throws, CSS not applied, missing import).
- Overflow & wrap failures at real widths (the 96px hero still 96px at 375px;
  a button that fix-widths and clips translated copy).
- Contrast that fails on the *rendered* font/weight even though the hex pair
  "should" pass.
- Invisible or clipped focus rings; tap targets that render under 44px.
- Dark-mode breakage (a hardcoded color that didn't get a dark token).
- Webfont fallback flash / wrong family actually loading.

## Output

A short verification report: which breakpoints × themes were captured, the
rendered findings (separated from any code-only findings), the intent-drift /
variant-diff verdict, and what was auto-corrected. Then the normal tight
delivery — *"Verified at 375/1280, light + dark; 1 overflow fixed at mobile.
Anything off?"*

## Honesty guardrails

- **A render is required to claim "verified."** Without a screenshot it's a
  code review — label it that, don't imply pixels were checked.
- **State the matrix.** "Verified at 375 + 1280, light only" is honest;
  silence implies full coverage that didn't happen.
- **Graceful degradation, announced.** No renderer → `self-critique.md` only,
  and say the verification step was skipped and why (same discipline as
  `critique-existing.md`'s ingest ladder).
- **Don't fix the screenshot — fix the system.** Corrections are token/layout
  moves on `design-system.md`, re-rendered; never hand-edit a screenshot to
  look right.
