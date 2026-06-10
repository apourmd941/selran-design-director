# Native iOS — SwiftUI translation

Translating the design system to iOS SwiftUI. The goal: an iOS app that feels native (respecting iOS HIG conventions, dynamic type, dark mode, size classes) **and** carries the direction's identity unmistakably from the web artifact into the app.

---

## The principle: iOS-native, brand-present

The worst failure mode is a SwiftUI app that looks like a web page: custom everything, ignoring iOS controls, breaking gestures. The second-worst is a vanilla iOS app with nothing of the brand in it. Aim for the middle: **use iOS controls and conventions**, but tune the tokens (color, type, radius, motion) so the brand reads through.

## Token → SwiftUI mapping

### Color (semantic + asset catalog)

Export tokens to an `.xcassets` Color Set with both light and dark variants from `color:` and `color.dark:`.

```swift
// DesignTokens.swift
extension Color {
    static let bgPrimary   = Color("bg_primary")
    static let bgSecondary = Color("bg_secondary")
    static let fgPrimary   = Color("fg_primary")
    static let fgSecondary = Color("fg_secondary")
    static let fgMuted     = Color("fg_muted")
    static let accent      = Color("accent")
    static let accentHover = Color("accent_hover")
    static let border      = Color("border")
}
```

Each Color Set contains `Any Appearance` (light) and `Dark` variants. Xcode/SwiftUI auto-switches with `@Environment(\.colorScheme)`.

Use `.tint(.accent)` at the root view to propagate brand color to all iOS default controls (Toggle, ProgressView, etc.).

### Typography (Font)

Load custom fonts via `.ttf`/`.otf` in the bundle + `Info.plist` `UIAppFonts` entry. Create a `Font` extension that mirrors the type scale:

```swift
extension Font {
    static let display = Font.custom("GeneralSans-Semibold", size: 56, relativeTo: .largeTitle)
    static let h1      = Font.custom("GeneralSans-Semibold", size: 40, relativeTo: .title)
    static let h2      = Font.custom("GeneralSans-Semibold", size: 28, relativeTo: .title2)
    static let h3      = Font.custom("GeneralSans-Medium",   size: 20, relativeTo: .title3)
    static let body    = Font.custom("GeneralSans-Regular",  size: 16, relativeTo: .body)
    static let caption = Font.custom("GeneralSans-Regular",  size: 14, relativeTo: .callout)
    static let mono    = Font.custom("JetBrainsMono-Regular", size: 14, relativeTo: .body).monospaced()
}
```

**Always pass `relativeTo:`** — this opts into Dynamic Type, so users with larger text settings still get readable UI. Skipping this is the #1 iOS accessibility failure.

### Spacing

```swift
struct Space {
    static let unit: CGFloat = 4
    static let xs: CGFloat  = 4
    static let sm: CGFloat  = 8
    static let md: CGFloat  = 16
    static let lg: CGFloat  = 24
    static let xl: CGFloat  = 32
    static let xxl: CGFloat = 48
}
```

Use in `.padding(Space.md)`, `.spacing: Space.sm` on `VStack`/`HStack`.

### Radius

```swift
struct Radius {
    static let sm: CGFloat = 4
    static let md: CGFloat = 8
    static let lg: CGFloat = 12
    static let full: CGFloat = 9999   // clamp with .clipShape(Capsule())
}
```

### Motion

iOS native motion is `.easeOut` by default, duration ~0.25s. For brand-tuned motion:

```swift
extension Animation {
    static let brandFast     = Animation.timingCurve(0.2, 0, 0, 1, duration: 0.15)
    static let brandBase     = Animation.timingCurve(0.2, 0, 0, 1, duration: 0.20)
    static let brandSlow     = Animation.timingCurve(0.2, 0, 0, 1, duration: 0.35)
    static let brandDramatic = Animation.timingCurve(0.19, 1, 0.22, 1, duration: 0.5)
}
```

Apply via `.animation(.brandBase, value: someState)`. Respect reduced motion:

```swift
@Environment(\.accessibilityReduceMotion) var reduceMotion
// then pick: reduceMotion ? .linear(duration: 0.01) : .brandBase
```

## Size classes → breakpoints

SwiftUI uses size classes, not pixel breakpoints. Map them:

| Schema breakpoint | SwiftUI size class |
|---|---|
| sm, md | `horizontalSizeClass == .compact` |
| lg, xl, 2xl | `horizontalSizeClass == .regular` |

```swift
@Environment(\.horizontalSizeClass) var hSize

var body: some View {
    if hSize == .compact {
        VStack { ... }          // iPhone portrait
    } else {
        HStack { ... }          // iPad, iPhone landscape
    }
}
```

For granular width-based decisions (rare), use `GeometryReader` with the schema pixel breakpoints.

## Per-direction adjustments

Each direction tunes SwiftUI conventions differently. The rule: change tokens and specific component treatments; keep iOS controls, gestures, and navigation primitives intact.

### technical-minimal
- `.tint(.accent)` for the emerald throughout
- `Font.display.tracking(-1.1)` for tight display type
- Buttons use `.borderedProminent` with corner radius 8
- Dividers via `Rectangle().frame(height: 1).foregroundColor(.border)` — iOS default dividers are lighter; use explicit hairlines
- Navigation bars: `.navigationBarTitleDisplayMode(.inline)` for density

### editorial
- Display font is a serif (e.g., Fraunces) — wrap with `Font.custom` + `.italic()` where specified
- Wider horizontal padding: `Space.xl` on iPad, `Space.lg` on iPhone
- Navigation: `.large` title mode, uppercase mono eyebrow above
- Dividers: 1px hairline, generous whitespace above/below

### dark-premium
- `.preferredColorScheme(.dark)` at the root if the app is dark-only
- One hero glow per screen max — `.background(LinearGradient(...))` behind the hero view, never multiple
- Accent is usually a warm amber/gold; `.tint(.accent)` applies universally
- Thin `.borderedProminent` with radius 4–8

### warm-approachable
- `RoundedRectangle(cornerRadius: 16)` for cards — rounder than base
- Motion: `brandSlow` on entrance; feels hospitable
- Micro-bounce on CTAs: `.scaleEffect(isPressed ? 0.98 : 1.0)` with spring animation

### vibrant-playful
- Use `color.palette` — rotate hues across sections/tabs
- Friendly motion: spring(response: 0.4, dampingFraction: 0.7)
- Tab icons animate on select: subtle scale + color change

### brutalist
- Tight radii or 0: `cornerRadius: 0` on buttons, cards
- Hard borders: `.border(Color.fgPrimary, width: 2)`
- Display type at 40pt+ in hero screens
- Motion: instant (`.linear(duration: 0)` for state flips)

### bold-distinctive
- Oversized numerals: use `Font.custom(..., size: 72)` for stat screens
- Strong H1 with tight tracking
- Magazine-scale whitespace around hero content

## Canonical component list

Ship these five per direction in `assets/native-ios/<direction>/`:

1. **Button.swift** — primary + secondary + ghost variants, correct hit target (44pt min)
2. **Card.swift** — standard card with header, body, action
3. **ListRow.swift** — iOS list row respecting `Section`, `Divider` treatment, swipe actions
4. **FormField.swift** — labeled `TextField` with inline validation, keyboard types
5. **TabBar.swift** — custom `TabView` style matching the direction

Additional patterns common across directions (build on demand, don't pre-bake all):
- NavigationStack with large title
- Sheet with detents (`.medium`, `.large`)
- Alert / ConfirmationDialog
- ProgressView (linear + circular)
- SearchBar (iOS 15+ `.searchable`)

## Accessibility (iOS specifics)

- **Hit targets:** 44×44pt minimum. Use `.frame(minWidth: 44, minHeight: 44)` on buttons. Test with Accessibility Inspector.
- **Dynamic Type:** always pass `relativeTo:` when creating fonts. Test at XXL.
- **VoiceOver labels:** any icon-only button needs `.accessibilityLabel("Close")`. Decorative images: `.accessibilityHidden(true)`.
- **Reduce motion:** honor `@Environment(\.accessibilityReduceMotion)`.
- **Reduce transparency:** honor for blur/vibrancy effects.
- **Differentiate without color:** don't rely on color alone for meaning — add icons or labels.
- **Contrast:** pulled from the same WCAG AA audit that covers web; same pass criteria.

## iOS-native conventions to preserve

These are HIG rules, not brand choices. Don't override:

- **System back gesture** (swipe from left edge) — never intercept
- **Navigation hierarchy** — use `NavigationStack` / `NavigationLink`
- **Modal presentation** — `.sheet` for flows, `.fullScreenCover` sparingly
- **Pull-to-refresh** — `.refreshable`
- **Context menus** — `.contextMenu` on long-press
- **Haptic feedback** — use `UIImpactFeedbackGenerator` on primary CTAs (light; don't spam)
- **Safe areas** — respect them; use `.ignoresSafeArea()` only deliberately (backgrounds, not content)
- **Tab bar presence** — 3–5 tabs; don't hide when scrolling unless iOS does it natively

## Build checklist per screen

Before shipping a SwiftUI screen:

- [ ] All strings are localized (`String(localized:)` or `.strings` catalog — not hardcoded)
- [ ] Dynamic Type tested at `.accessibilityExtraExtraExtraLarge`
- [ ] Dark mode tested (`preview.preferredColorScheme(.dark)`)
- [ ] Both size classes tested (iPhone + iPad previews)
- [ ] VoiceOver labels on icon-only controls
- [ ] No hardcoded colors outside `DesignTokens.swift`
- [ ] No hardcoded fonts outside `DesignTokens.swift`
- [ ] Animation respects reduce-motion
- [ ] Hit targets ≥ 44pt
- [ ] RTL: test with `.environment(\.layoutDirection, .rightToLeft)` if i18n.rtl

## Anti-patterns

- **Reinventing iOS controls.** Custom `TextField` implementations from scratch break keyboard handling, paste menus, accessibility. Always start from the iOS control and style it.
- **Hardcoded pixels for every screen size.** Use size classes + `GeometryReader` sparingly, not conditional code per device.
- **Ignoring Dynamic Type.** Fonts without `relativeTo:` look fine in previews and fail accessibility audits in App Review.
- **Over-animating.** iOS users expect a fast UI. `duration > 0.4` should be rare and reserved.
- **Wrong tint color scope.** Setting `.tint` on every button manually — set it once at the root and let it cascade.
- **Drop-shadow elevation.** iOS uses blur backgrounds and subtle borders. Avoid web-style `shadow: 0 2px 8px rgba(0,0,0,0.1)` — use `.background(.regularMaterial)` and explicit borders instead, tuned per direction.

## See also

- `references/native-android.md` — Jetpack Compose counterpart
- `references/breakpoints.md` — size class mappings
- `references/i18n.md` — iOS-specific localization (NSLocalizedString, Dynamic Type intersection with translation)
- `references/accessibility-check.md` — WCAG still applies; iOS adds Dynamic Type + VoiceOver
- `assets/native-ios/<direction>/` — canonical SwiftUI snippets per direction
- `assets/exports/ios.template.swift` — token export template (from Phase 2.7)
