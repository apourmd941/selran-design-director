# Microcopy system

The words *are* the interface. `voice.md` sets the brand's writing voice; this
file turns that voice into the specific UI strings — buttons, errors, empty
states, validation, onboarding, tooltips, toasts — with formulas, per-direction
tone, and real character budgets. It's the state-level layer beneath voice, and
it slots directly into the states `screen-states.md` already defines.

**Edge over a generic UX-copy skill.** `design:ux-copy` writes good microcopy in
the abstract. DD writes it **in the artifact's actual direction voice** (a
technical-minimal error doesn't say "Oops! 🙈"; an editorial empty state reads
like a sentence, not a toast), wired to the **components and states already
built** and to the **i18n character budgets** (`i18n.md` copy-length) so a label
that fits in English doesn't blow out the button in German.

## The patterns (formula → per-direction tone → budget)

For each, apply the formula, set the tone from `voice.md` for the active
direction, and respect the char budget:

| Pattern | Formula | Notes |
|---|---|---|
| **Button / CTA** | verb-first, ≤ 3–4 words | name the action, not "OK"/"Submit". technical-minimal: "Save changes"; bold: "Ship it"; editorial: "Continue reading" |
| **Error** | what happened **+ why (if useful) + how to fix** | never blame the user, never expose internals/stack/SQL; give the next step |
| **Empty state** | what this is **+ why it's empty + the one action to fill it** | the highest-leverage screen — it's a chance to onboard, not a dead end (pairs `screen-states.md` empty) |
| **Loading** | honest, scaled to gravity | serious/somber topics → plain ("Loading…"); otherwise the brand's voice may show personality. Never fake progress |
| **Validation (inline)** | specific + actionable, in real time where possible | "Use 8+ characters" not "Invalid input"; validate on blur/submit per field type |
| **Confirmation / destructive** | name the consequence; button verb = the action | "Delete 3 files" not "OK"; the destructive button is the labeled one |
| **Tooltip / helper** | ≤ 1 line; only when the UI can't say it inline | not a dumping ground; if everything needs a tooltip, the UI is unclear |
| **Onboarding** | value-first, progressive, skippable | one idea per step; show, don't lecture |
| **Toast / notification** | outcome (+ optional action) | "Saved" / "Couldn't save — Retry"; auto-dismiss success, persist errors |
| **Success / confirmation** | confirm + the next step | "Invite sent. Add another?" |

## Cross-cutting rules

- **Voice per context (within the one brand voice).** `voice.md` sets the
  register; shift *tone* by moment — success warmer, error calmer and plainer,
  warning direct, neutral quiet — without leaving the brand's voice. A playful
  brand still writes a calm error; a serious brand still writes a clear success.
- **Character budgets are constraints, not afterthoughts.** Pull worst-case
  expansion from `i18n.md` copy-length (German ~+35%, Finnish/Russian long);
  **never fix-width a button**; avoid idioms and puns that don't translate;
  respect sentence-case vs title-case per locale.
- **No emoji as a crutch** (`anti-patterns.md`). Emoji are not error icons and
  not a substitute for a clear sentence.
- **Accessibility is copy too** — `aria-label` on icon-only controls, error text
  associated with its field, link text that means something out of context
  ("View invoice", not "click here").

## Output format

For each string the user needs, deliver:

```
<slot> (e.g. "empty state — projects list")
  Recommended:  <the copy>            (NN chars)
  Alternatives:
    | option | copy | tone | best for |
  Why: <one line tying it to the formula + direction voice>
  Localization: <expansion risk / idioms to avoid / case>
```

Use `assets/microcopy-worksheet.md` as the checklist of every string a product
needs — it doubles as a "have we written copy for every state?" audit, so empty
and error states don't ship as lorem ipsum or a raw stack trace.

## Discipline

- **Follow the brand voice, not a default.** The single most common microcopy
  slop is the "friendly startup" voice (`Oops!`, `Hang tight!`, exclamation
  everywhere) pasted onto every brand. Match `voice.md`.
- **Errors are a UX surface, not a log.** Plain, blameless, actionable; the raw
  detail goes to diagnostics, not the user.
- **Every state gets words.** An unwritten empty/error/loading state is an
  incomplete design — flag it the way `self-critique.md` flags a missing state.
