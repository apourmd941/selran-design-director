# Native Android — Jetpack Compose translation

Translating the design system to Android via Jetpack Compose + Material 3. The goal: an Android app that feels native (respecting Material guidelines, edge-to-edge, predictive back, dynamic type) **and** carries the direction's identity unmistakably from the web artifact into the app.

---

## The principle: Material-native, brand-present

Compose + Material 3 ships a strong default system (MaterialTheme, color schemes, typography scale, shapes). **Don't fight it** — override the theme with brand tokens and let Material's behavior handle the rest. Custom-draw everything and you'll ship an app that fails accessibility checks, breaks on large screens, and looks out of place on Pixel.

## Token → Material 3 theme mapping

### Color scheme

Material 3 uses `ColorScheme` with semantic roles. Map design-system colors into the Material slots:

```kotlin
val LightColors = lightColorScheme(
    primary          = AccentColor,
    onPrimary        = OnAccent,
    primaryContainer = AccentHoverColor,  // or a 12% tint
    secondary        = FgSecondary,
    background       = BgPrimary,
    onBackground     = FgPrimary,
    surface          = BgSecondary,
    onSurface        = FgPrimary,
    surfaceVariant   = BgTertiary,
    onSurfaceVariant = FgMuted,
    outline          = BorderStrong,
    outlineVariant   = Border,
    error            = DangerColor,
)

val DarkColors = darkColorScheme(
    primary          = AccentDarkColor,
    // ... mirror from color.dark block
)
```

Wrap the app in `MaterialTheme(colorScheme = ...)` and `useDarkTheme = isSystemInDarkTheme()`.

**Dynamic Color (Material You):** Android 12+ can pull colors from the user's wallpaper. For brand-driven apps, **opt out** — set `dynamicColor = false` in the theme. For user-forward apps (utility, consumer), consider opting in by respecting `dynamicLightColorScheme(LocalContext.current)`.

### Typography

Custom fonts in `res/font/` folder, referenced as `Font(R.font.general_sans_regular, FontWeight.Normal)`:

```kotlin
val GeneralSans = FontFamily(
    Font(R.font.general_sans_regular, FontWeight.Normal),
    Font(R.font.general_sans_medium,  FontWeight.Medium),
    Font(R.font.general_sans_semibold, FontWeight.SemiBold),
)

val AppTypography = Typography(
    displayLarge  = TextStyle(fontFamily = GeneralSans, fontWeight = FontWeight.SemiBold, fontSize = 56.sp, lineHeight = 62.sp, letterSpacing = (-1.1).sp),
    headlineLarge = TextStyle(fontFamily = GeneralSans, fontWeight = FontWeight.SemiBold, fontSize = 40.sp, lineHeight = 44.sp),
    headlineMedium= TextStyle(fontFamily = GeneralSans, fontWeight = FontWeight.SemiBold, fontSize = 28.sp, lineHeight = 32.sp),
    titleLarge    = TextStyle(fontFamily = GeneralSans, fontWeight = FontWeight.Medium,   fontSize = 20.sp, lineHeight = 26.sp),
    bodyLarge     = TextStyle(fontFamily = GeneralSans, fontWeight = FontWeight.Normal,   fontSize = 16.sp, lineHeight = 25.sp),
    bodyMedium    = TextStyle(fontFamily = GeneralSans, fontWeight = FontWeight.Normal,   fontSize = 14.sp, lineHeight = 22.sp),
    labelLarge    = TextStyle(fontFamily = GeneralSans, fontWeight = FontWeight.Medium,   fontSize = 14.sp, lineHeight = 20.sp),
)
```

**Always use `.sp`, not `.dp`, for font sizes** — `.sp` scales with user text-size accessibility preferences; `.dp` does not. This is Android's Dynamic Type equivalent.

### Spacing

```kotlin
object Space {
    val unit: Dp = 4.dp
    val xs: Dp  = 4.dp
    val sm: Dp  = 8.dp
    val md: Dp  = 16.dp
    val lg: Dp  = 24.dp
    val xl: Dp  = 32.dp
    val xxl: Dp = 48.dp
}
```

Use `Modifier.padding(Space.md)`, `Arrangement.spacedBy(Space.sm)`.

### Shape (radius)

Material 3 has a `Shapes` palette:

```kotlin
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small      = RoundedCornerShape(8.dp),
    medium     = RoundedCornerShape(8.dp),
    large      = RoundedCornerShape(12.dp),
    extraLarge = RoundedCornerShape(16.dp),
)
```

### Motion

```kotlin
object Motion {
    const val fast: Int = 150
    const val base: Int = 200
    const val slow: Int = 350
    val easing = CubicBezierEasing(0.2f, 0f, 0f, 1f)
    val dramatic = CubicBezierEasing(0.19f, 1f, 0.22f, 1f)
}

// Usage
AnimatedVisibility(
    visible = visible,
    enter = fadeIn(animationSpec = tween(Motion.base, easing = Motion.easing)),
)
```

Respect reduced motion:

```kotlin
val reduceMotion = LocalAccessibilityManager.current?.isReduceMotionEnabled ?: false
val duration = if (reduceMotion) 0 else Motion.base
```

## Window size classes → breakpoints

Compose uses `WindowSizeClass`:

| Schema breakpoint | WindowWidthSizeClass |
|---|---|
| sm (<640) | Compact |
| md (640–839) | Compact (still) |
| lg (840–1239) | Medium |
| xl+ (≥1240) | Expanded |

```kotlin
val windowSizeClass = calculateWindowSizeClass(activity)
when (windowSizeClass.widthSizeClass) {
    WindowWidthSizeClass.Compact  -> PhonePortrait()
    WindowWidthSizeClass.Medium   -> FoldableOrSmallTablet()
    WindowWidthSizeClass.Expanded -> TabletOrLargeScreen()
}
```

Don't conditional-render based on exact pixel widths — use size classes.

## Per-direction adjustments

### technical-minimal
- `Shapes(medium = RoundedCornerShape(8.dp))` — tight rounding
- Outlined buttons with 1.dp border, filled buttons with no elevation
- Dividers via `HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)`
- Top app bar: `CenterAlignedTopAppBar` — minimal, compact

### editorial
- Serif display font (Fraunces via `Font(R.font.fraunces_semibold_italic)`)
- Wider content padding on Medium/Expanded
- `LargeTopAppBar` with uppercase eyebrow text above title
- Dividers: 1.dp hairline, generous `Modifier.padding(vertical = Space.lg)`

### dark-premium
- Dark theme default — `MaterialTheme(colorScheme = DarkColors)`
- Subtle `Box` with radial gradient behind hero content — one per screen
- Accent usually amber/gold; `primary` in Material maps to it
- Thin elevated containers, not drop shadows

### warm-approachable
- `RoundedCornerShape(16.dp)` on cards — rounder
- Micro-bounce on pressed state: `Modifier.scale(if (pressed) 0.98f else 1.0f)`
- Motion: spring(dampingRatio = 0.7, stiffness = Spring.StiffnessMedium)

### vibrant-playful
- Palette rotation via `color.palette` — store as a list, rotate across tabs/sections
- Friendly spring motion throughout
- Icon tabs animate: `animateFloatAsState` on scale + color when selected

### brutalist
- `RoundedCornerShape(0.dp)` — no rounding
- Solid `BorderStroke(2.dp, MaterialTheme.colorScheme.onBackground)` on buttons and cards
- Display type at 40.sp+ in hero screens
- Motion: `tween(durationMillis = 0)` for instant state flips — or explicit `snap()`

### bold-distinctive
- Oversized numerals via `displayLarge` at 72.sp+ for stat screens
- Strong `headlineLarge` with tight letterSpacing
- Magazine-scale vertical padding on hero sections

## Canonical component list

Ship these five per direction in `assets/native-android/<direction>/`:

1. **Button.kt** — Primary (`Button`), Secondary (`OutlinedButton`), Ghost (`TextButton`) — all with correct 48dp hit target
2. **Card.kt** — Material 3 `Card` or `ElevatedCard` wrapper with direction-appropriate elevation
3. **ListRow.kt** — `ListItem` with leading/trailing slots, direction-tuned divider treatment
4. **FormField.kt** — `OutlinedTextField` / `TextField` with label, supporting text, error state
5. **BottomNav.kt** — `NavigationBar` with 3–5 `NavigationBarItem` entries

Additional common patterns (generate on demand):
- `TopAppBar` variants (CenterAligned, Small, Medium, Large)
- `ModalBottomSheet`
- `AlertDialog`
- `Snackbar` + `SnackbarHost`
- `SegmentedButton`
- `NavigationRail` (Medium size class)
- `NavigationDrawer` (Expanded size class)

## Accessibility (Android specifics)

- **Hit targets:** 48×48dp minimum. `Modifier.sizeIn(minWidth = 48.dp, minHeight = 48.dp)` on tappable elements.
- **TalkBack labels:** `Modifier.semantics { contentDescription = "..." }` on icon-only buttons. Decorative: `Modifier.clearAndSetSemantics {}`.
- **Font scaling:** always `.sp` for text sizes; test at 130% and 200% system font scale.
- **Reduced motion:** `LocalAccessibilityManager.current?.isReduceMotionEnabled`.
- **Color contrast:** same WCAG AA as web.
- **Touch feedback:** `Modifier.clickable` gives ripple; don't remove ripple on buttons.

## Android-native conventions to preserve

- **Predictive back gesture** (Android 13+) — use `BackHandler` for custom handling, but let the system animation run
- **Edge-to-edge display** — `enableEdgeToEdge()` at Activity level; use `WindowInsets` for padding
- **System bars** — status bar, navigation bar colors match the app's top/bottom context
- **Back navigation stack** — use `NavHostController` for Compose Navigation
- **Material Motion** — use `AnimatedContent` + transitions; don't hand-roll if Material has a pattern
- **Ripple feedback** — leave it on for all tappables (only remove in brutalist + dark-premium where explicitly tuned)
- **Long-press** — use `combinedClickable` for long-press menus

## Build checklist per screen

Before shipping a Compose screen:

- [ ] All strings in `strings.xml` (translatable) — never hardcoded `Text("Save")`
- [ ] Font scale tested at 130% and 200% (`fontScale` in Preview)
- [ ] Dark mode tested (`uiMode = UI_MODE_NIGHT_YES` in Preview)
- [ ] All size classes tested (Preview with different `widthDp`)
- [ ] `contentDescription` on icon-only controls
- [ ] No hardcoded colors outside the theme
- [ ] No hardcoded fonts outside `AppTypography`
- [ ] Edge-to-edge; `WindowInsets` applied correctly
- [ ] 48dp minimum hit targets
- [ ] RTL: test with `LocalLayoutDirection.current = LayoutDirection.Rtl` in Preview

## Anti-patterns

- **Fighting Material.** Re-implementing `TextField`, `Button`, `TopAppBar` from scratch loses accessibility, keyboard handling, ripple, and platform updates. Start with Material, override theme tokens + shape/color.
- **Hardcoded pixel widths.** Compose's `Modifier.width(320.dp)` breaks on foldables. Use `fillMaxWidth()`, `widthIn(max = ...)`, or `BoxWithConstraints`.
- **Ignoring window insets.** Content under the status bar or nav bar = bug report. Apply `WindowInsets.safeDrawing` or equivalent.
- **`.dp` for font sizes.** Breaks accessibility font scaling.
- **One layout for all size classes.** Phone and tablet should render different compositions — not the same layout stretched.
- **Dynamic color on brand apps.** If the brand accent is emerald, Material You can override it with the user's wallpaper colors. Opt out explicitly.
- **Heavy shadows.** Material 3 uses tinted surfaces + elevation tokens, not drop shadows. Use `Card(elevation = CardDefaults.cardElevation(defaultElevation = 1.dp))` — let Material tint the surface.

## See also

- `references/native-ios.md` — SwiftUI counterpart
- `references/breakpoints.md` — size class mappings
- `references/i18n.md` — Android-specific i18n (strings.xml, per-language `values-xx/`)
- `references/accessibility-check.md` — WCAG still applies; Android adds font scale + TalkBack
- `assets/native-android/<direction>/` — canonical Compose snippets per direction
- `assets/exports/android/` — token export XML templates (colors.xml, typography.xml, from Phase 2.7)
