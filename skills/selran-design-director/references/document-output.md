# Document output

Rules for .docx reports and PDF documents. Inherits from design tokens already generated.

## The core problem with AI-generated documents

Every report looks like Word opened itself and typed. Calibri 11pt, 1.15 line-height, a few bolded words, maybe a bullet list. The document is technically correct and visually invisible — there is no design there, only defaults.

Fix: treat a document like a printed artifact. Type has weight, the page has margins for a reason, and headings are supposed to be felt at a glance.

## Format selection

| Use case | Format | Why |
|---|---|---|
| Will be edited further by humans | .docx | Track changes, comments, widely editable |
| Read-only deliverable, pixel-exact | PDF | Fonts embed, layout locks |
| Long-form narrative, print-ready | PDF from HTML | Full typographic control |
| Report that must match an org template | .docx with template | Inherits existing styles |

For .docx: use `python-docx`. For polished PDFs: render HTML (with CSS using the design tokens) and export via the `pdf` skill. Playwright → PDF is the polished path — do not generate PDFs with `python-docx` when visual quality matters.

## Reading tokens into python-docx

Every value is driven by the YAML in `design-system.md`. Nothing is hardcoded.

```python
from docx import Document
from docx.shared import Pt, Mm, RGBColor

doc = Document()

# Page setup from overrides.document.page_margin_mm (fallback: 25mm)
for section in doc.sections:
    section.top_margin = Mm(page_margin_mm)
    section.bottom_margin = Mm(page_margin_mm)
    section.left_margin = Mm(page_margin_mm)
    section.right_margin = Mm(page_margin_mm)

# Body style — driven by type.body, type.scale.base, type.leading.body
body_style = doc.styles["Normal"]
body_style.font.name = type_body
body_style.font.size = Pt(type_scale_base)
body_style.font.color.rgb = RGBColor.from_string(color_fg_primary.lstrip("#"))
body_style.paragraph_format.line_spacing = type_leading_body
body_style.paragraph_format.space_after = Pt(type_scale_base * 0.5)
```

The mapping is mechanical: a token corresponds to a python-docx call. Don't invent new sizes, don't substitute "readable" fonts because python-docx doesn't render them in your preview — the file renders correctly when opened in Word.

## Heading hierarchy

Heading sizes derive from the type scale. Never hardcode.

| Level | Size token | Weight | Use |
|---|---|---|---|
| h1 (title) | `type.scale.display` or `type.scale.xxl` | 600 | Document title, one per file |
| h2 | `type.scale.xl` | 600 | Major sections |
| h3 | `type.scale.lg` | 500 | Sub-sections |
| h4 | `type.scale.base` with caps+tracking | 500 | Labels, dense sub-hierarchy |

Line-height: `type.leading.display` on h1–h3, `type.leading.body` on everything else. Letter-spacing: `type.tracking.display` on h1/h2 (tightens large type), default on lower levels.

## Font embeddability in .docx

Not every font embeds cleanly. Google Fonts generally don't embed into .docx without the font file installed on the reader's system. Plan for the common case: the recipient opens the file on a system that doesn't have your display font.

**Embeds reliably (cross-platform):**
- Georgia, Source Sans Pro, Source Serif Pro, Verdana, Trebuchet MS
- Microsoft Cloud Fonts (Aptos, Bahnschrift, Seaford, Grandview) — modern and ship with recent Office
- Any font installed locally on both the author and reader machines

**Doesn't embed cleanly (will fall back):**
- Most Google Fonts (General Sans, Satoshi, Geist, DM Sans, Fraunces, Instrument Serif)
- Paid foundry fonts unless the license and .docx embedding flag allow it

**How `overrides.document.embed_fallbacks: true` works:**

When the flag is true, substitute an OS-safe pair that honors the aesthetic direction:

| Direction | Display fallback | Body fallback |
|---|---|---|
| editorial | Georgia | Source Serif Pro → Georgia |
| technical-minimal | Aptos / Verdana | Source Sans Pro → Verdana |
| bold-distinctive | Georgia (big) | Source Sans Pro |
| dark-premium | Georgia | Source Sans Pro |
| warm-approachable | Georgia | Source Sans Pro |
| brutalist | Verdana / Trebuchet MS | Verdana |

When `embed_fallbacks: false` (or unset), keep the original fonts and trust the recipient has them. Good for internal docs, risky for external.

## Paragraph rhythm

- Body paragraphs: space-after equal to half the base font size (e.g., 16pt base → 8pt after)
- Headings: space-before 1.5x the heading size, space-after 0.5x
- No first-line indents unless the aesthetic is explicitly Editorial and indents are appropriate for long prose
- Lists: indent 12–16pt, bullet character is a simple `•` or en-dash — never ▶, ❯, ✓, or emoji

## Defaults by aesthetic direction

| Direction | Body font | Display font | Margins | Notes |
|---|---|---|---|---|
| editorial | Source Serif Pro | Fraunces | 30–35mm | Serif body, wider margins, longer measure |
| technical-minimal | Source Sans Pro | General Sans | 20–25mm | Tight, functional, sans throughout |
| bold-distinctive | Source Sans Pro | Big display face | 20mm | Display type is the statement |
| dark-premium | Source Serif Pro | Playfair / Fraunces | 25–30mm | Serif, generous tracking on display |
| warm-approachable | DM Sans / Figtree | DM Sans | 25mm | Round, friendly, no serifs |
| brutalist | Verdana / Trebuchet | Same as body | 15–20mm | Small margins, jammed, utilitarian |
| vibrant-playful | DM Sans / Satoshi | Satoshi | 22–26mm | Warm sans, palette-driven accents, chapter color-coding |

## Using `color.palette` in documents (when the direction has one)

For directions that carry a populated `color.palette` (vibrant-playful always; optional on others), use it to bring structure and variety to long documents without fighting the primary `accent`:

- **Chapter / section color-coding** — each top-level section (H1 or H2 opener) gets a palette hue. Chapter 1 → `palette[0]`, Chapter 2 → `palette[1]`, wrapping at palette length. The hue appears as a thin left border on the section-opener heading, the color of the chapter number, or a 2px divider rule. Same chapter always uses the same color.
- **Callout boxes / pull quotes** — categorize them: "tip" callouts use one palette hue, "warning" callouts another, "example" a third. One hue per category, used consistently throughout the document.
- **Tag / category labels** on list items — one color per category from the palette.
- **Chart series** — in-document charts use palette colors in order, same convention as decks.
- **Tables with row categories** — category column cells tinted with a 10% opacity palette hue; saturated hue only in a narrow left rule.

What NOT to do with the palette in documents:
- Don't color running body text in palette hues — body stays `fg_primary` always.
- Don't color headings with palette hues beyond the chapter-coding use case above. Heading color is otherwise `fg_primary`.
- Don't use more than one palette hue in a single paragraph or list item — it reads as ransom-note.
- Don't auto-add a palette to a direction that has none. If the user wants more chromatic range on a technical-minimal report, ask whether they want to switch to vibrant-playful or add an explicit 3-hue palette — don't silently paint it.

When `color.palette` is empty (any of the six single-accent directions at their starter defaults), rely on `accent`, `fg_muted`, and type hierarchy to organize the document. Introducing fabricated colors breaks the direction.

## PDF path

When the output is a polished PDF, do not render from python-docx. Instead:

1. Generate an HTML file that reads the design tokens (same way `web-output.md` describes)
2. Use the `pdf` skill — see `/mnt/skills/public/pdf/SKILL.md` for the Playwright-based rendering pipeline
3. CSS `@page` rules control page size, margins, headers, footers
4. `@media print` strips interactive affordances (hover states, focus rings)

The HTML → PDF path gives you web-quality typography in a print deliverable. The python-docx → PDF path gives you Word output saved as PDF, which is not the same thing.

## Document-specific anti-patterns

| Avoid | Instead |
|---|---|
| Calibri 11pt body | Token-driven body font at `type.scale.base` |
| Times New Roman as the "classic" serif | Georgia, Source Serif Pro, or Fraunces |
| Rainbow headings (blue h1, green h2, orange h3) | One color for all headings; accent used sparingly for emphasis |
| Every paragraph as a bullet | Prose for prose, bullets only for genuinely parallel items |
| Default Word "Title" / "Subtitle" styles | Custom styles driven by tokens |
| Underline for emphasis | Weight or italic — underline reads as hyperlink |
| Justified text without hyphenation | Left-aligned, or justified WITH hyphenation enabled |
| Headers/footers containing the filename | Useful info only — page number, document title, or nothing |
| Bold AND italic AND colored for the same word | Pick one emphasis mechanism |

## The quality checkpoint

Before delivering: print the first two pages to PDF and look at them as a reader would. If the type feels like a default, it is. If the margins feel cramped, they are. Rework before shipping.
