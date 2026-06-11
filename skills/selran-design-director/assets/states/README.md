# Screen States

Three state primitives per direction, supplementing the three states already shipped under `assets/components/<direction>/` (empty-state, loading-skeleton, error-404).

## Coverage

| State | Where | All directions |
|---|---|---|
| `empty-state.html` | `components/<dir>/` | ✓ |
| `loading-skeleton.html` | `components/<dir>/` | ✓ |
| `error-404.html` | `components/<dir>/` | ✓ |
| `loading-spinner.html` | `states/<dir>/` | ✓ |
| `error-inline.html` | `states/<dir>/` | ✓ |
| `success.html` | `states/<dir>/` | ✓ |

Total: 6 states × 7 directions = 42 state snippets.

## The doctrine

Every screen has ≥4 states: empty, loading, error, success. AI-generated UI ships only the populated happy path. This folder (plus the empty/skeleton/404 in `components/`) covers the rest.

## When to use which

- **Empty** (`empty-state.html`): no data yet. Explain + primary CTA to get data.
- **Loading skeleton** (`loading-skeleton.html`): content has known layout. Preserves mental model.
- **Loading spinner** (`loading-spinner.html`): single action in flight, or unknown layout. Full-screen or inline variants.
- **Error inline** (`error-inline.html`): one field/row/widget broken; rest of page alive.
- **Error 404 / full-page** (`error-404.html`): whole screen can't render.
- **Success** (`success.html`): flow completion. Takes viewport with next-step CTAs.

Plus toast (inline, handled by the host app's toast library — not in the snippet set).

## See also

- `references/screen-states.md` — full spec, transitions, accessibility, direction notes, anti-patterns
