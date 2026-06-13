# Native Android snippets — selran-design-director

Jetpack Compose component sketches, one folder per aesthetic direction. These are teachable snippets — not a shipped library. Copy what you need into your Android project and wire the local token objects up to your real `MaterialTheme`.

## What's here

```
native-android/
├── technical-minimal/
├── editorial/
├── dark-premium/
├── warm-approachable/
├── vibrant-playful/
├── brutalist/
└── bold-distinctive/
    ├── Button.kt       — primary + secondary + loading state
    ├── Card.kt         — title, body, optional action
    ├── ListRow.kt      — leading icon, title, trailing chevron
    ├── FormField.kt    — labeled text field with helper/error
    └── BottomNav.kt    — Material 3 NavigationBar, 3–5 items
```

Five canonical components × seven directions = 35 files. Each file is self-contained: no shared `Theme.kt`, no cross-file imports. That's intentional — you can lift one file into a new project without dragging along a dependency graph.

## How each file is organized

1. **Local tokens object** at the top — e.g. `TechnicalMinimalTokens`, `EdCardTokens`, `BdNavTokens`. Each color is commented with its schema token name (`color.accent`, `color.fg_primary`, etc.) so mapping from `design-system.md` to Compose is obvious.
2. **One or two composables** — the primary public API (`TechnicalMinimalPrimaryButton`, `EditorialCard`, etc.).
3. **A `@Preview`** at the bottom (light and dark where the direction has both). Preview functions are `private` — they don't leak into the app's public API.

## Usage

**Quickest path (drop-in preview):** copy a single `.kt` file into any Compose module. Android Studio's Preview pane will render it immediately — tokens are inlined, no theme wiring required.

**Integration (real app):**

1. Pick your direction folder.
2. Move each file into your app package; rename the package line.
3. Replace the local `Tokens` object with references to your real `MaterialTheme` — the mapping is already sketched in `references/native-android.md` ("Token → Material 3 theme mapping" section).
4. Swap `FontFamily.Serif` / `FontFamily.Monospace` stubs for your actual fonts: `FontFamily(Font(R.font.fraunces_regular))`, etc.
5. Replace hardcoded hex colors in `@Preview` functions with your theme colors once you have a real `AppTheme { … }` wrapper.

## Why per-direction local token objects

Each direction declares its own `…Tokens` object (`TechnicalMinimalTokens`, `EdCardTokens`, `BrNavTokens`, etc.) as `private` — so if a project mixes directions or if two files end up in the same package, nothing collides. Naming is scoped to the file.

If you're shipping a single direction app-wide, consolidate: lift the repeated colors into one `AppColors` object, build a real `lightColorScheme(...)`, and wrap your app in `MaterialTheme`. The snippets then become one-liners with `MaterialTheme.colorScheme.primary`.

## Conventions followed

- **Hit targets ≥ 48dp** — `Modifier.defaultMinSize(minHeight = 48.dp)` on every interactive element.
- **Font sizes in `.sp`**, not `.dp` — respects user font-scale accessibility.
- **RTL-aware** — logical `start`/`end` padding, `Icons.AutoMirrored.*` for directional icons.
- **No dynamic color (Material You)** — brand direction is fixed by design; wallpaper-sampled colors would fight it.
- **Material 3 used for plumbing** — ripple, focus, a11y semantics, keyboard handling all come from `Button`, `OutlinedTextField`, `NavigationBar`. The direction restyles the surface (shape, color, typography); Material handles the rest.
- **Previews include dark mode** where the direction supports it. `dark-premium` is dark-only by design.

## Extending

To add a 6th component per direction (say, `Chip.kt`, `Snackbar.kt`, or `TopAppBar.kt`):

1. Pick the direction folder.
2. Copy the closest existing file as a template (for shape/typography voice).
3. Open `references/native-android.md` — check "Per-direction adjustments" for the specific restyle cues (e.g. `CenterAlignedTopAppBar` for technical-minimal, `LargeTopAppBar` with uppercase eyebrow for editorial).
4. Wrap the Material 3 primitive, override shape/colors/typography, add a preview. Keep it under ~140 lines.

To add an 8th direction, follow `references/direction-starters.md` — write a starter first, run the a11y audit, then use that starter's tokens to author five components here in a new folder.

## Anti-patterns this set specifically avoids

- Re-implementing `Button` / `TextField` / `NavigationBar` from scratch — loses accessibility, ripple, keyboard handling.
- Hardcoded pixel widths. All layouts use `fillMaxWidth()`, `defaultMinSize`, or intrinsic sizing.
- `.dp` for font sizes.
- One shared `Theme.kt` across directions — a user copying `editorial/Card.kt` shouldn't need to also copy three other files.
- Decorative-only icons without `contentDescription = null` (TalkBack ignores them correctly).

See also:
- `references/native-android.md` — full Material 3 token mapping guide
- `references/aesthetic-directions.md` — what each direction is
- `assets/direction-starters/*.md` — the authoritative token values per direction
