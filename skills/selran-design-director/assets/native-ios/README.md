# native-ios — SwiftUI snippets per direction

Thirty-five SwiftUI component files — five canonical components × seven aesthetic directions from the `selran-design-director` skill. Each file is a single self-contained snippet you can drop into an iOS project to see how a direction expresses itself in SwiftUI.

```
native-ios/
├── technical-minimal/     Stripe / Linear feel — emerald accent, hairline borders, 8pt radius
├── editorial/             magazine feel — serif display, warm cream, rule lines
├── dark-premium/          cinematic — inky bg, gold accent, letter-spaced caps, soft glow
├── warm-approachable/     neighborhood brand — peach cream, terracotta, 16pt radius, bouncy
├── vibrant-playful/       coordinated 5-hue palette, soft rounding, spring micro-motion
├── brutalist/             0 radius, Space Mono, 2px hard borders, press-inverts
└── bold-distinctive/      hot red, PP Editorial New display, oversized indices, asymmetric
```

Each direction folder ships five files, in the order you should read them:

1. **Button.swift** — primary + secondary variants, loading state, 44pt hit target
2. **Card.swift** — title / body / optional action
3. **ListRow.swift** — leading icon + title + trailing chevron (RTL-mirroring)
4. **FormField.swift** — labeled `TextField` with helper + error
5. **TabBar.swift** — 3–5 items, selected state appropriate to the direction

## How to use

These files are **snippets, not a shipping library**. Purpose: show, unambiguously, what the direction means in SwiftUI terms so an engineer can either copy-paste into a real iOS project or port the tokens into an existing design system.

### Copy into a project

1. Drop the `.swift` file into your target.
2. Rename the `DS<Direction><Component>` struct to whatever your project uses — or prefix with your own namespace.
3. Each file's header comment lists the tokens it consumes (e.g. `color.accent → #0A7A5C`). Replace the inlined `Color(red:green:blue:)` literals with lookups into your real `DesignTokens.swift` (see `references/native-ios.md` for the canonical export shape).
4. Make sure the custom fonts referenced (`GeneralSans-Medium`, `Fraunces-Medium`, `SpaceMono-Bold`, `Söhne-Buch`, `Satoshi-Medium`, `PPEditorialNew-Ultrabold`, `Commissioner-Medium`, `JetBrainsMono-Regular`, `Tiempos Headline`) are bundled and registered in `Info.plist` under `UIAppFonts`. If a font is missing, SwiftUI falls back silently to system — which defeats the direction. Don't ship without the custom fonts installed.
5. Run the `#Preview` block to see it in isolation (Xcode canvas, light + dark where relevant).

### Wire to real tokens

Each file inlines colors/fonts as literal values to keep the snippet portable. In a real app:

- Export the direction's YAML tokens (from `assets/direction-starters/<direction>.md`) into an `.xcassets` Color Set with light + dark variants — see `references/native-ios.md` for the exact shape.
- Register custom fonts via `Info.plist` `UIAppFonts`.
- Replace inline `Color(red:green:blue:)` literals with `Color("accent")`, `Color("bg_primary")` etc.
- Replace inline `Font.custom(...)` calls with a `Font` extension (`.dsBody`, `.dsDisplay` etc.) so Dynamic Type stays wired via `relativeTo:`.
- Apply `.tint(.accent)` at the root view to propagate to iOS default controls.

## Why the `DS` / `<Direction>` prefix

Every component struct is named `DS<Direction><Component>` (e.g. `DSTechnicalCard`, `DSBrutalistButton`, `DSBoldListRow`). The prefix exists to:

- **Avoid collision** if you paste more than one direction into a single project while comparing — `Button` alone would clash with SwiftUI's own `Button`, and two directions' `Button`s would fight each other.
- **Signal non-final.** These are design-system components, not finished UIKit controls. The prefix tells a reader "this came from the skill's snippet library."

When adopting one direction for real, rename the prefix to whatever your project uses (`Acme`, `BrandUI`, etc.) and delete the other six folders.

## Standards every file follows

1. **Self-contained `#Preview`** — every file renders in Xcode canvas with no project dependencies. Light and dark modes both previewed where the direction has a dark variant. (Dark-premium is dark-default, so one preview; brutalist shows both because it supports both.)
2. **Tokens called out explicitly.** The file header comment lists which design tokens the component consumes and the hex values used. Don't silently drift from the starter's YAML.
3. **Hit target ≥ 44pt.** All buttons and tappable rows use `.frame(minHeight: 44)` or padding that exceeds it. Checked against the iOS HIG.
4. **Dynamic Type.** Every `Font.custom(...)` passes `relativeTo:` so large-type users still get usable UI. The single deliberate exception is display-scale type in bold-distinctive / brutalist hero moments where the direction is specifically about monumental typography; even there, body text honors Dynamic Type.
5. **Dark mode via `@Environment(\.colorScheme)`** where the direction ships a dark variant (editorial, technical-minimal, warm-approachable, vibrant-playful, bold-distinctive, brutalist). Dark-premium is always dark; no switch needed.
6. **RTL-aware.** Layouts use `.leading` / `.trailing`. Chevrons use `chevron.forward` (auto-mirrors) never `chevron.right`.
7. **No emoji in labels.** Per the skill's anti-patterns file.
8. **No shared `Tokens.swift`.** Each file stands alone so you can read one in isolation and understand the direction. When you adopt one for real, merge the repeated color/font values into a proper `DesignTokens.swift`.

## Extending

Add a sixth component (e.g. `Badge.swift`, `Alert.swift`, `SegmentedControl.swift`) by following the same pattern:

1. Open the direction folder.
2. Copy an existing file as a template.
3. Update the header comment with the tokens the new component consumes.
4. Build the struct using the same inlined Color / Font pattern.
5. Ensure 44pt hit target, Dynamic Type (`relativeTo:`), RTL-safe icons, both-scheme previews where appropriate.
6. Keep it under ~120 lines — these are teachable snippets, not production components.

Add a new direction by creating a new folder (`assets/native-ios/<direction>/`) and shipping the same five canonical components. Source its tokens from `assets/direction-starters/<direction>.md`.

## See also

- `references/native-ios.md` — the full SwiftUI translation of the token system
- `references/aesthetic-directions.md` — the seven directions in prose
- `assets/direction-starters/<direction>.md` — the YAML token source for each direction
- `assets/exports/ios.template.swift` — a production-ready `DesignTokens.swift` template
