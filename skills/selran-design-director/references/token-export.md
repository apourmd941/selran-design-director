# Token export

The design system is portable. `design-system.md` is the source of truth; every other format is a derivation of it.

This file covers how to emit the tokens in four formats designers and engineers actually use. Invoke when the user asks to export, sync, or hand off the design system.

## Supported targets

| Target | Output file | Best for |
|---|---|---|
| Figma Tokens (Tokens Studio plugin) | `design-tokens.json` | Designers on legacy Tokens Studio plugin workflows |
| Figma Variables (native, modern) | `figma-variables.json` | Teams using Figma's built-in Variables panel or Variables REST API. **Supersedes Tokens Studio** for teams that have migrated. |
| Tailwind CSS config | `tailwind.config.js` | Engineers using Tailwind v3/v4 |
| Style Dictionary | `tokens.style-dictionary.json` | Teams with a platform team / cross-platform builds |
| CSS custom properties | `tokens.css` | Plain HTML/CSS, any framework, universal fallback |
| iOS / SwiftUI | `DesignTokens.swift` | iOS apps using SwiftUI — ships as a single file, drop in target or Swift Package |
| Android (Material 3) | `res/values/colors.xml`, `res/values-night/colors.xml`, `res/values/dimens.xml`, `res/values/themes.xml` | Android apps using Material 3 — standard resource layout |

Templates live in `assets/exports/`. Copy, substitute token values, write out.

## When to trigger

Invoke this workflow when the user says any of:

- "Export the tokens" / "hand off the design system"
- "Give me a Tailwind config / CSS variables / Figma tokens"
- "Sync this to Figma" / "use in my codebase"
- "How do I get this into my app?"

Default behavior when the target isn't specified: emit **all four files** into `exports/` next to the `design-system.md`. It's cheap, and the user picks what they need.

## Transform rules — universal

The YAML shape in `design-system.md` is the input. Every target uses the same source.

**Color tokens** (`color.*`) → hex strings preserved as-is. Semantic names stay semantic — don't rename `accent` to `primary` on export.

**Type families** (`type.display`, `type.body`, `type.mono`) → exact font names, quoted. Include a generic fallback stack (`sans-serif`, `serif`, `monospace`) appended per format.

**Type scale** (`type.scale.*`) → pixel values. For Tailwind/CSS, also emit rem equivalents (divide by the base font size, usually 16).

**Spacing** (`spacing.base_unit` and derived scale) → emit both the unit and the common multiples (1, 2, 3, 4, 6, 8, 12, 16) as named tokens (`space-1`, `space-2`, etc.) so consumers can reference them without math.

**Motion** (`motion.*`) → durations in milliseconds (keep as integers with `ms` unit in CSS/Tailwind, plain number in JSON formats).

## Format-specific notes

### Figma Tokens JSON (Tokens Studio)

Structure: nested object, each leaf has `{ "value": "...", "type": "color" | "typography" | "spacing" | etc. }`.

```json
{
  "color": {
    "bg_primary": { "value": "#FAFAF9", "type": "color" },
    "accent": { "value": "#0A7A5C", "type": "color" }
  },
  "type": {
    "scale_base": { "value": "16", "type": "fontSizes" }
  }
}
```

Template: `assets/exports/figma-tokens.template.json`. The Tokens Studio plugin reads this directly; import via its UI.

### Tailwind config

Emit to `tailwind.config.js` as a CommonJS module. Map design-system tokens onto Tailwind's `theme.extend` namespace. Never overwrite Tailwind's defaults wholesale — `extend` preserves its base.

```js
module.exports = {
  theme: {
    extend: {
      colors: {
        'bg-primary': '#FAFAF9',
        'bg-secondary': '#F4F4F5',
        'accent': '#0A7A5C',
      },
      fontFamily: {
        display: ['General Sans', 'sans-serif'],
        sans: ['General Sans', 'sans-serif'],
        mono: ['JetBrains Mono', 'monospace'],
      },
      fontSize: {
        xs: ['12px', { lineHeight: '1.55' }],
        base: ['16px', { lineHeight: '1.55' }],
      },
      spacing: {
        '1': '4px',
        '2': '8px',
      },
      borderRadius: {
        sm: '4px',
        md: '8px',
      },
      transitionDuration: {
        base: '200ms',
      },
      transitionTimingFunction: {
        out: 'cubic-bezier(0.2, 0, 0, 1)',
      },
    },
  },
};
```

Template: `assets/exports/tailwind.config.template.js`.

### Style Dictionary JSON

Amazon's Style Dictionary schema — used for cross-platform token pipelines (web + iOS + Android from one source).

```json
{
  "color": {
    "bg": {
      "primary": { "value": "#FAFAF9" }
    },
    "accent": { "value": "#0A7A5C" }
  },
  "size": {
    "spacing": {
      "1": { "value": "4px" },
      "2": { "value": "8px" }
    }
  }
}
```

Template: `assets/exports/style-dictionary.template.json`.

### CSS custom properties

Plainest target. Works in every browser, every framework.

```css
:root {
  --color-bg-primary: #FAFAF9;
  --color-bg-secondary: #F4F4F5;
  --color-fg-primary: #18181B;
  --color-accent: #0A7A5C;

  --font-display: "General Sans", sans-serif;
  --font-body: "General Sans", sans-serif;
  --font-mono: "JetBrains Mono", monospace;

  --text-xs: 12px;
  --text-base: 16px;
  --text-lg: 20px;

  --space-1: 4px;
  --space-2: 8px;
  --space-4: 16px;

  --radius-sm: 4px;
  --radius-md: 8px;

  --duration-base: 200ms;
  --ease-out: cubic-bezier(0.2, 0, 0, 1);
}
```

Template: `assets/exports/tokens.template.css`.

## Export workflow

1. **Read** `design-system.md` (YAML frontmatter only — the prose is not exported).
2. **Write** each template with values substituted. Create the `exports/` directory if needed.
3. **Name** each file with the skill name as a suffix so it doesn't collide with user's existing files: e.g., `exports/tailwind.selran.config.js` *only if* the user already has a `tailwind.config.js` — otherwise keep the standard name.
4. **Confirm** to the user which files were written, one line each:

> Exported:
> - `exports/tokens.css` — CSS custom properties
> - `exports/tailwind.config.js` — Tailwind theme extension
> - `exports/design-tokens.json` — Figma Tokens Studio format
> - `exports/tokens.style-dictionary.json` — Style Dictionary

That's the whole workflow. No elicitation, no tweak loop — this is a pure transform.

## What not to do

- Don't rename semantic token names on export (keep `accent`, not `primary`)
- Don't invent new tokens during export — only transform what's in the source
- Don't emit Tailwind's full default palette (pollutes the output)
- Don't skip the fallback stack on font families (fonts may not load)
- Don't emit hex as `0x...` — use `#...` everywhere

---

## Additional formats (appended)

### Figma Variables JSON (modern, native)

Figma's built-in Variables system (shipped 2023) has a richer JSON schema than the Tokens Studio plugin — it supports modes (Light/Dark/any custom mode) natively, and colors must be `{r,g,b,a}` floats in `0..1`, not hex.

Template: `assets/exports/figma-variables.template.json`.

Schema shape:

```json
{
  "collections": [{
    "name": "Project",
    "modes": [
      { "name": "Light", "modeId": "light" },
      { "name": "Dark",  "modeId": "dark"  }
    ],
    "variables": [
      {
        "name": "color/accent/default",
        "type": "COLOR",
        "valuesByMode": {
          "light": { "r": 0.04, "g": 0.48, "b": 0.36, "a": 1 },
          "dark":  { "r": 0.06, "g": 0.73, "b": 0.51, "a": 1 }
        }
      }
    ]
  }]
}
```

**Hex → RGBA transform (emit step).** The template stores hex strings as placeholders (`"light": "{{color.accent}}"`) for readability. The emitter must post-process: wherever a color-typed value lands, parse `#RRGGBB` or `#RRGGBBAA` and replace with `{r,g,b,a}` float dict. Reference Python helper for the emit script:

```python
def hex_to_rgba_float(hex_str):
    s = hex_str.lstrip("#")
    if len(s) == 6:
        s += "FF"
    r = int(s[0:2], 16) / 255.0
    g = int(s[2:4], 16) / 255.0
    b = int(s[4:6], 16) / 255.0
    a = int(s[6:8], 16) / 255.0
    return { "r": round(r, 4), "g": round(g, 4), "b": round(b, 4), "a": round(a, 4) }
```

Walk the parsed JSON, and for any variable with `"type": "COLOR"`, replace each `valuesByMode[*]` string with the dict above. Write back as JSON. Leave `STRING`/`FLOAT` variables untouched.

**Upload workflow.** Either import via the Figma Variables panel (paste JSON) or POST to `https://api.figma.com/v1/files/:file_key/variables` with a Personal Access Token. See Figma's Variables REST API docs for the full PATCH shape when updating existing variables.

**Why supersedes Tokens Studio.** Native Variables bind directly to design-surface properties without a plugin; modes live in the file, not in plugin storage; designers can't accidentally lose them during plugin-version churn. Teams on Tokens Studio can keep the old export during migration; emit both and let the designer choose.

### iOS (SwiftUI)

Template: `assets/exports/ios.template.swift`. Emits to `DesignTokens.swift`.

Structure: `enum DesignTokens` with nested `Color`, `Typography`, `Spacing`, `Radius`, `Motion` enums. Colors expose both static Light/Dark accessors and adaptive tokens that flip with `UITraitCollection` via a `dynamicColor(light:dark:)` helper. A `Color(hex:)` initializer extension at the top handles the string-parse so the template stays readable.

**Integration.** Drop into an Xcode target, or create a Swift Package with the file as the only source and depend on it from app + watchOS/iOS extensions. Reference in views as `DesignTokens.Color.accent`, `DesignTokens.Typography.display(.init(DesignTokens.Typography.Size.display))`, `DesignTokens.Spacing.md`, etc.

**Font loading.** `.custom("General Sans", size:)` requires the font be registered. Ship the font files in the bundle and add the filenames to `UIAppFonts` in Info.plist. If missing, SwiftUI falls back to the system font silently.

### Android (Material 3)

Templates: `assets/exports/android/colors.template.xml`, `dimens.template.xml`, `themes.template.xml`.

**Emit layout:**
```
app/src/main/res/
  values/
    colors.xml    ← light-mode values (from {{color.*}})
    dimens.xml    ← spacing, radius, type scale (shared day/night)
    themes.xml    ← Material 3 theme wiring tokens to material slots
  values-night/
    colors.xml    ← dark-mode values (from {{color.dark.*}})
    themes.xml    ← identical to values/themes.xml (@color refs resolve per mode)
```

**Material 3 slot mapping** (already wired in `themes.template.xml`):

| M3 slot | Design token |
|---|---|
| `colorPrimary` | `accent` |
| `colorOnPrimary` | `bg_primary` |
| `colorSurface` | `bg_primary` |
| `colorOnSurface` | `fg_primary` |
| `colorSurfaceVariant` | `bg_secondary` |
| `colorOutline` | `border` |
| `colorOutlineVariant` | `border_strong` |
| `colorError` | `danger` |

**Gradle.** The generated files drop into the standard Android resource folders — no `build.gradle` changes needed. Apply the theme via `android:theme="@style/Theme.<ProjectName>"` in `AndroidManifest.xml`. For Jetpack Compose, read colors with `colorResource(R.color.accent)` or generate a Compose `ColorScheme` mapping the same tokens.

### Hex → RGBA emit helper (shared)

Only Figma Variables needs the hex-to-float transform. The other formats preserve hex as-is. Add the conversion as a one-time post-process step scoped to `figma-variables.json` only — don't generalize it across every export.

## Related asset generators

Beyond token files, the skill also ships per-direction brand assets driven by the same tokens:

- **`assets/favicons/<direction>.svg.template`** — 32px favicon scaffolds, one per direction. See `assets/favicons/README.md` for rasterization to 16/32/180px PNG and HTML wiring.
- **`assets/og-image/<direction>.html.template`** — 1200×630 Open Graph / Twitter-card templates rendered to PNG via Playwright/Puppeteer. See `assets/og-image/README.md` for the render command and meta-tag wiring.

These consume the same `{{color.*}}`, `{{type.*}}` placeholders as the token exports, so the same emit pass can fill them in.
