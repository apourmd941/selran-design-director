<!-- HANDOFF.md skeleton — fill from design-system.md + the built artifact.
     See references/design-handoff.md. Reference tokens, never raw values. -->

# Developer handoff — <Artifact / feature name>

- **Design system:** `design-system.md` v<N> · direction: <direction>
- **Target stack:** <React + Tailwind | Vue | vanilla HTML/CSS | SwiftUI | Compose>
- **Live preview:** <path/URL>  ·  **Token files:** `exports/` (CSS / Tailwind / Figma Tokens / Style Dictionary)
- **Status:** <ready to build | needs decisions — see §10>

## 1. Overview
<One paragraph: what this is, who builds it, any non-obvious intent.>

## 2. Design tokens
| Token | Value | CSS var | Used for |
|---|---|---|---|
| color.accent | #______ | `--accent` | primary actions, links |
| space.md | __px | `--space-md` | component padding |
| radius.card | __px | `--radius-card` | cards, inputs |
| type.scale.xxl | __px | `--text-xxl` | hero headline |
| … | | | |
> Reference these tokens in code — do not hardcode the values.

## 3. Layout & grid
- **Container max:** __px · **gutter:** __px · **columns:** __
| Breakpoint | Width | Behavior |
|---|---|---|
| sm (≤640) | | stacks to 1 col; nav → hamburger |
| md (768) | | 2 col |
| lg (1024) | | full layout |
| xl (1280+) | | container caps, margins grow |

## 4. Components
### <ComponentName>
- **Anatomy:** <parts>
- **Variants:** <primary / secondary / …>
- **Props / API:**
  | Prop | Type | Default | Notes |
  |---|---|---|---|
- **Tokens consumed:** `--accent`, `--space-md`, …
- **States:** default / hover / focus-visible / active / disabled (token per state)
- **Native:** see `native-ios.md` / `native-android.md` snippet if applicable
<Repeat per component.>

## 5. States & interactions
| Screen state | Treatment | Tokens |
|---|---|---|
| Empty | <copy + illustration spot> | |
| Loading | <skeleton / spinner> | |
| Error | <inline / toast> | |
| Success | <confirmation> | |

Interaction states: default → hover → **focus-visible (≥3:1, never removed)** → active → disabled.

## 6. Motion
| Trigger | Property | Duration | Easing |
|---|---|---|---|
| page load | opacity/translateY | __ms | <curve> |
| hover | <prop> | __ms | <curve> |
| **prefers-reduced-motion** | — | 0 | none (required) |

## 7. Accessibility (build requirements)
- **Focus order:** <sequence>
- **Keyboard:** <Tab/Enter/Esc/Arrow behavior>
- **ARIA:** icon-only controls need `aria-label`; <roles>
- **Contrast pairs:**
  | fg | bg | ratio | required | pass? |
  |---|---|---|---|---|
- **Hit targets:** ≥44px (web/iOS) / 48dp (Android)

## 8. Edge cases
- Long text / truncation: <rule>
- Empty / zero-data: <rule>
- Overflow / scroll: <rule>
- **i18n:** worst-case expansion <×__>; never fix-width buttons. RTL: <flips / no-flip>

## 9. Assets
- Tokens: `exports/{tokens.css, tailwind.config, figma-tokens.json, style-dictionary}`
- Favicons: `favicons/` · OG image: `og-image/`

## 10. Open questions & tickets
- [ ] <decision the engineer needs>
- [ ] <…>
<If a project tracker is connected: parent issue + one sub-task per component/section, shown before filing.>
