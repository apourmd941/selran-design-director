# provides/exports/

Extra token export formats beyond the core's defaults (CSS, Tailwind, Figma Tokens, Figma Variables, Style Dictionary, iOS Swift, Android XML).

Use this directory for engineering-stack exports the core doesn't ship — Jetpack Compose `Color.kt` files, SwiftUI `ShapeStyle` extensions, Unity UXML, Flutter `ThemeData` builders, React Native `StyleSheet` builders, Tokens Studio plugin configs, anything else your target audience consumes.

## Typical layout

```
provides/exports/
├── compose-colors.kt.template
├── swiftui-shapestyle.swift.template
└── tokens-studio.json.template
```

## Register in pack.yaml

```yaml
provides:
  exports:
    - "provides/exports/compose-colors.kt.template"
```

## Rules

- One file per format. The filename is the artifact the core emits at export time.
- Emit the pack's tokens by reading the merged `direction + {{PACK_NAME}}` overlay. Never hardcode hex values into an export template.
- Use the same placeholder syntax as the core's export templates — `{{color.accent}}`, `{{type.body_family}}`. The core resolves these against the merged token tree.

This directory is optional — delete it if your pack doesn't ship export formats.

See `../../../references/token-export.md` in the core for the export philosophy, the merged-token contract, and the template conventions.
