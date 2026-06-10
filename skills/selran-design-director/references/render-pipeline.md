# Render pipeline

How to turn a `design-system.md` into a visual preview — a rendered HTML page and, optionally, a screenshot.

This is the infrastructure layer. It unlocks several features, all shipping as of v3.3:

- **Previewing a design before committing to build** (user asks "show me what this looks like")
- **Visual elicitation** — the seven aesthetic directions rendered as thumbnails via `assets/thumbnail-template.html`
- **Three-variation explorer** — three candidate token sets rendered side by side
- **Self-critique loop** — screenshot the final build and audit it with vision

All four consume the same infrastructure documented below.

Invoke when the user says any of:

- "Show me a preview" / "what would this look like?"
- "Render this" / "mock it up before we build"
- "Let me see it first"

## The template

`assets/preview-template.html` is a self-contained HTML file with CSS custom-property placeholders. It renders a single page that exercises the major tokens:

- Heading hierarchy (h1 → h4) using the display + body fonts and the type scale
- Body paragraph with real prose
- A button in the accent color with hover state
- A card with border + radius tokens
- A section showing the color palette as swatches
- A small table demonstrating tabular numerals (if the mono font is set)

The page is intentionally modest — one screen, not a full site. It's a **design system preview**, not a landing-page mock. The goal is to let the user see the tokens applied, not to design the final artifact prematurely.

## The render flow

1. **Read** `design-system.md` (YAML frontmatter only).
2. **Read** `assets/preview-template.html`.
3. **Substitute** every `{{token.path}}` placeholder with the corresponding value from the YAML. Use the same substitution logic as the export templates (same placeholder format, identical paths).
4. **Write** the result to `preview/design-system-preview.html` next to the `design-system.md`.
5. **Display** the preview to the user.

Display mechanics depend on the environment:

- **Claude Code / filesystem-aware environments:** write the file and open it in the user's browser (or instruct them to open it).
- **Claude.ai web with artifact support:** render the HTML as an inline artifact the user can view directly.
- **Any environment with Playwright available:** additionally take a PNG screenshot at 1280×800, save as `preview/design-system-preview.png`, and present the image.

## Screenshot rules (when Playwright is available)

- Viewport: 1280×800 (desktop standard)
- Wait for fonts to load before capturing — Google Fonts or similar web fonts need a network round-trip; `await page.waitForFunction(() => document.fonts.ready)` before the screenshot
- Full-page capture (`fullPage: true`) so nothing is cropped
- PNG format, not JPEG (type edges must stay sharp)
- 1x for inline display, 2x if the user asks for a shareable export

## When NOT to render

- Trivial one-off artifacts (single button, short note). The design-system.md wasn't generated in the first place for these; don't render what doesn't exist.
- Before the tweak loop stabilizes. Rendering after every micro-tweak is wasteful. Render on explicit request, or once just before the "build it" commit.
- If the render pipeline isn't available in the current environment (no filesystem, no Playwright), fall back to presenting the tokens as a clean YAML summary with swatches displayed inline using color blocks. Don't force a half-broken render.

## Substitution reference

The preview template uses the same `{{path.to.token}}` placeholders as the export templates. Canonical paths:

| Placeholder | Source in design-system.md |
|---|---|
| `{{color.bg_primary}}` | `color.bg_primary` |
| `{{color.fg_primary}}` | `color.fg_primary` |
| `{{color.accent}}` | `color.accent` |
| `{{type.display}}` | `type.display` (font name) |
| `{{type.body}}` | `type.body` |
| `{{type.mono}}` | `type.mono` (may be empty string) |
| `{{type.scale.base}}` | `type.scale.base` (number, px implied) |
| `{{type.scale.xxl}}` | `type.scale.xxl` |
| `{{spacing.base_unit}}` | `spacing.base_unit` |
| `{{spacing.radius.md}}` | `spacing.radius.md` |
| `{{motion.duration_base}}` | `motion.duration_base` |
| `{{motion.easing}}` | `motion.easing` |

If a placeholder has no matching token (e.g., `mono` is an empty string), substitute a safe default (`monospace`) and continue.

## Font loading in the preview

The preview needs real fonts to be useful. Two strategies:

1. **Google Fonts CDN** — for fonts that are on Google Fonts (General Sans, Geist, Inter, DM Sans, etc.), emit a `<link>` to fonts.googleapis.com in the template's `<head>`. The template already has a placeholder for this.
2. **System-safe fallback** — if the font isn't on Google Fonts (rare for the recommended set), use the font's generic family (`serif`, `sans-serif`) and note in the preview "font unavailable in browser — fallback shown."

Never break the render because a font didn't load — always fall back visibly.

## What the preview is *not*

- Not the final artifact. Don't build the user's actual page into the preview template.
- Not interactive. One static page. Hover state on the button is the only affordance.
- Not responsive. Desktop-sized. Mobile preview is its own feature, not shipped here.

## Quality check before displaying

Before handing the preview to the user:

1. Validate every placeholder was substituted — grep the output for `{{`. If any remain, something's missing from the source YAML.
2. Validate the HTML parses (no unclosed tags, no broken CSS).
3. If taking a screenshot, check it's not blank (file size > a few KB).

If any check fails, surface the error honestly rather than shipping a broken preview.
