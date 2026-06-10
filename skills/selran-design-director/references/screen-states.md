# Screen States

Every screen in a real app has at least four states. Most AI-generated UI ships only the happy path — the "populated with data" state — and collapses when there's no data, a slow network, an error, or a successful action completes. This reference fixes that.

The doctrine: **no screen ships without all four states designed.**

---

## The four states

| State | When | Goal |
|---|---|---|
| **Empty** | No data yet (new account, empty folder, first use) | Explain what this screen will show once populated; offer the primary action to get there |
| **Loading** | Data is fetching | Preserve layout (skeleton) or signal progress (spinner) without layout shift |
| **Error** | Request failed, permission denied, form invalid | Explain what went wrong, offer recovery |
| **Success** | Action completed (form submitted, item created, payment succeeded) | Confirm what happened; offer the next action |

A fifth implicit state is **populated** — the happy path. That's what the existing `assets/components/<direction>/` section snippets cover.

## What ships per direction

Pre-built snippets live in `assets/states/<direction>/`. Every direction has:

| File | Pattern |
|---|---|
| `loading-spinner.html` | Full-screen or container-scoped spinner with accessible label |
| `error-inline.html` | Inline error surface for form fields and row-level failures |
| `success.html` | Success confirmation screen or toast (depending on direction) |

Plus these three that ship under `assets/components/<direction>/` (already built, pre-Phase 1):

| File | Pattern |
|---|---|
| `empty-state.html` | Centered empty state with glyph, eyebrow, headline, body, primary + ghost CTA, keyboard hint |
| `loading-skeleton.html` | Layout-preserving skeleton with shimmer + reduced-motion fallback |
| `error-404.html` | Full-viewport error (404, 500, permission denied — same pattern) |

Total coverage: 6 states per direction × 7 directions = 42 state snippets.

## When to use which loading treatment

- **Skeleton** (`loading-skeleton.html`): content has a known layout. Lists, cards, tables, article text. Preserves the user's mental model of the page.
- **Spinner** (`loading-spinner.html`): single action in flight (form submit, button click, modal open). Or the layout is genuinely unknown (search results before query).
- **Optimistic update**: neither. Apply the change immediately and rollback on error. Use for toggles, reorders, favorites.

**Rule:** never show a spinner longer than 2s if a skeleton would work. Spinners rob the user of layout context.

## When to use which error treatment

- **Inline error** (`error-inline.html`): one field, one row, one widget is broken. The rest of the page still works. Surface the error near the thing that failed, with recovery action.
- **Full-page error** (`error-404.html`): the whole screen can't render. Route not found, permission denied, backend down. Take over the viewport with clear status + recovery.
- **Toast error**: transient failures the user can retry (save failed, network blip). Do not use for hard failures — they dismiss themselves and the user misses them.

**Rule:** never show a raw error message like `"Error 500: Internal Server Error"` to users. Translate to human language with a concrete next step.

## When to use which success treatment

- **Inline success** (checkmark + confirmation text): form fields, row operations. Non-disruptive.
- **Success screen** (`success.html`): flow completion. Checkout succeeded, signup done, password reset sent. Takes over the viewport with the confirmation + next-step CTAs.
- **Toast success**: quick confirmations for non-critical actions (saved, copied, archived). Auto-dismiss at 3–4s.

**Rule:** don't over-celebrate. A blue checkmark + "Saved" is plenty for most actions. Reserve confetti and celebratory language for actual milestones (first signup, first invoice paid, course completed).

## State transitions

The transitions between states matter as much as the states themselves:

- **Empty → Loading → Populated**: skeleton, then fade content in at 200ms
- **Populated → Loading → Populated** (refresh): subtle indicator (top progress bar or inline spinner on the affected section); don't show a full skeleton — it's jarring
- **Populated → Error**: inline error surfaces in place; keep the rest of the screen alive
- **Loading → Error**: replace the spinner with the inline error surface; don't leave the spinner spinning forever
- **Action → Success → Next**: 500ms success confirmation, then either auto-advance or prompt for next action. Never leave the user stranded on a success screen with no path forward.

## Accessibility baseline

- Every loading state has `role="status"` + `aria-live="polite"` and announces "Loading..." to screen readers
- Every error has `role="alert"` + `aria-live="assertive"` so it's announced immediately
- Success states use `role="status"` (polite — they're not urgent)
- Skeletons are `aria-hidden="true"` with a sibling `aria-live` announcement ("Loading content")
- Focus moves to the first actionable element in error/success screens
- `prefers-reduced-motion`: skeletons pause their shimmer; spinners fall back to a static "Loading" text; success animations become instant

## Direction-specific notes

- **technical-minimal / editorial / dark-premium**: quiet states. No emoji, no mascots. Mono labels, clean iconography.
- **warm-approachable**: warmer voice in copy ("Looks like it's empty here" vs. "No items"). Still no mascots.
- **vibrant-playful**: the one direction where a brief celebratory animation on success is appropriate. Empty states can use a palette-colored illustration (not a cartoon).
- **brutalist**: ASCII-style states. Empty might be `[NOTHING HERE]`. Errors use `[ERR]` markers.
- **bold-distinctive**: editorial empty states — big serif headlines, restrained copy. Treat empty like a missing chapter opener.

## Anti-patterns

- **No "Oops! Something went wrong" unless the copy file explicitly has it.** It's the most overused error phrase in AI-generated UI. Prefer a concrete description: "We couldn't load your workspaces." See `voice.md` per direction.
- **No "No results found" with a sad-face emoji.** Use the empty state pattern proper — eyebrow, headline, body, action.
- **No spinning for >5 seconds without escalating.** If a request takes that long, show an error with retry option.
- **No success screen without a next-step CTA.** Don't strand the user.
