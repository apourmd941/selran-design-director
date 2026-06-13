# Design → developer handoff spec

Turn a finished design into the document an engineer needs to build it exactly:
tokens, component specs, states, motion, responsive behavior, accessibility,
edge cases — plus the real exportable token files. This is the packaging layer
over data Design Director already produces; it doesn't re-derive anything.

**Why DD's handoff beats a Figma-reading one.** The competing `design-handoff`
skill *reads* measurements out of Figma and tabulates them. DD **authored** the
design system, so every value is exact (not inferred), the component specs come
from DD's own pattern library (`component-patterns.md`) rather than reverse-
engineered layers, and DD ships the **actual token files** (`token-export.md` →
CSS / Tailwind / Figma Tokens / Style Dictionary) next to the prose — the
engineer can `npm install` the tokens, not retype them.

## Inputs (best → acceptable)

| Input | Result |
|---|---|
| **A DD-built artifact + its `design-system.md`** | Best — every token, state, and breakpoint is known exactly; spec is authoritative |
| **`design-system.md` alone** | Component- and token-level spec without per-screen layout; flag layout as TBD |
| **A de-slopped/critiqued design** (from `critique-existing.md`) | Hand off the *fixed* version; carry the token diff into the spec |
| **A screenshot only** | Possible but weakest — measurements are *inferred*; say so, and recommend rebuilding in a direction first so the handoff is exact |

## Output: `HANDOFF.md` (skeleton in `assets/handoff-template.md`)

Fill the template. The sections, and where each pulls from:

1. **Overview** — what's being built, **target stack** (detect from the artifact or ask: React+Tailwind / Vue / vanilla / SwiftUI / Compose), design-system version, links to the live preview and the token files.
2. **Design tokens** — table `token → value → CSS var → where used`, generated from `design-system.md` via `token-export.md`. Principle: **the spec references tokens, never raw values** (`--space-md`, not `16px`) so drift can't creep in.
3. **Layout & grid** — container widths + the breakpoint scale (`breakpoints.md`) + a **responsive-behavior row per breakpoint** (what reflows/stacks/hides at sm/md/lg/xl).
4. **Component specs** — per component (from `component-patterns.md` + the artifact): anatomy, variants, **props/API table**, the exact tokens it consumes, and its states. For native targets, point at the `native-ios.md` / `native-android.md` snippet.
5. **States & interactions** — the four screen states (empty / loading / error / success, from `screen-states.md`) **and** interaction states (default / hover / focus-visible / active / disabled) with the token each uses.
6. **Motion** — table `trigger → property → duration → easing` from the direction's `assets/motion/<direction>.css` + `motion-and-interaction.md`, with the mandatory `prefers-reduced-motion` fallback row.
7. **Accessibility (build requirements)** — focus order, keyboard map, ARIA roles/labels on icon-only controls, the **contrast pairs table** (fg/bg/ratio/pass) and 44px/48dp hit-target note, drawn from `accessibility-check.md` Parts 1+2. These are *implementation requirements*, not just an audit.
8. **Edge cases** — long text / empty / overflow / loading-into-error, plus **i18n expansion + RTL** behavior per `i18n.md` `copy-length` (e.g. "button must not fix-width; German +35%").
9. **Assets** — the exported token files (`exports/`), favicons (`favicons/`), OG image (`og-image/`) where they exist.
10. **Open questions & tickets** — list decisions the engineer needs. If a project-tracker MCP (Linear / Jira / Asana) is connected, **offer** to create a parent issue + one sub-task per component/section (show them first, never auto-file); otherwise leave the list inline.

## Annotated / redline option (optional, render-gated)

A *tabular* spec is the default and is complete on its own. If the user wants
visual redlines (spacing/measurement callouts overlaid on the design), that
needs the rendered artifact — produce it through `render-pipeline.md` and add
measurement annotations from the known spacing tokens. Full pixel-diff redline
verification belongs to the visual-verification loop (roadmap D6); until then,
annotate from the authored tokens (exact for DD-built artifacts) and say so.

## Discipline

- **Tokens, not values** — every spec'd number is a token reference; raw px/hex
  only appears in the token table itself.
- **Don't invent what you don't know** — `design-system.md`-only input has no
  per-screen layout; mark it TBD rather than guessing. Screenshot input →
  measurements are inferred; label them.
- **Ship the files, not just the doc** — always emit/point at the `exports/`
  token files; a handoff the engineer has to retype is half a handoff.
- **One source of truth** — the handoff is a *view* of `design-system.md` +
  the artifact. If they change, regenerate; never let `HANDOFF.md` drift into a
  second spec.
