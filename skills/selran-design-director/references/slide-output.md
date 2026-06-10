# Slide output

Rules for presentations — PowerPoint (.pptx), Keynote exports, HTML-based decks. Inherits from design tokens.

## The core problem with AI-generated decks

Every slide looks the same. Title at top, four bullets below, maybe a stock image. The deck is a document pretending to be a presentation.

Fix: slides are visual, not textual. If you can't see the point from 10 feet away, the slide is wrong.

## Format selection

| Use case | Format | Why |
|---|---|---|
| Will be edited / passed around | .pptx | Universally editable |
| One-off pitch, high visual polish | HTML deck | More design control, better animations |
| Formal corporate presentation | .pptx with template | Matches org's style |
| Portfolio / creative pitch | HTML or PDF export | Control over every pixel |

For HTML decks, consider reveal.js or a custom single-file HTML approach.

## The one-idea-per-slide rule

If a slide has more than one idea, split it. The audience can read or listen, not both.

- Each slide: ONE claim, insight, or data point
- Title summarizes the takeaway, not the topic ("Revenue doubled last quarter" not "Q3 Revenue")
- Body is the evidence: a chart, a quote, an image, or a minimal bullet set

## Slide anatomy

### Title slide

- Deck title, presenter name, date, organization
- ONE typographic moment — large title, small supporting info
- Cover image if appropriate, with gradient overlay for text legibility
- Restrain yourself — no swooshes, no 3D, no stock imagery of people in suits shaking hands

### Content slides — the patterns

1. **Hero statement slide** — one sentence, huge type, nothing else. Use for transitions and key claims.
2. **Stat slide** — one enormous number + small context label. Use tabular numerals.
3. **Image-led slide** — full-bleed image with a small caption in corner
4. **Quote slide** — pull quote in accent color, small attribution below
5. **Comparison slide** — two columns or a simple table
6. **Chart slide** — one chart, title above, takeaway caption below
7. **Process/timeline slide** — horizontal or vertical flow with 3–7 steps
8. **Team/logos slide** — grid of headshots or customer logos

Vary between these patterns. If five slides in a row all have the same layout, the deck feels monotonous.

### Section divider slides

Between major sections: one word or short phrase, big type, colored background. Lets the audience breathe and know something new is starting.

### Closing slide

- Thank you, call to action, or next steps
- Contact info if relevant
- NOT "Any questions?" as the only content — it's empty air

## Typography on slides

- **Minimum body size: 24pt** — anything smaller can't be read from the back
- **Titles: 36–60pt** depending on length
- **Stats: 72–120pt** — make the number huge
- **Line length:** short. If a line wraps, rewrite it.

Font pairings:
- Technical/corporate: Söhne / Geist / Inter + a mono for data
- Editorial/refined: Fraunces display + sans body
- Bold/creative: oversized display face
- Consulting/classic: serif (Tiempos, Source Serif) + sans body

Avoid Calibri and Arial defaults. Every PowerPoint template ever has them.

## Color on slides

Slides are projected. High contrast is non-negotiable.

- **Dark mode decks** work well in dim rooms and look premium
- **Light mode decks** work better in bright rooms
- **Accent color:** use for emphasis, key stats, callouts — NOT for every title
- **Backgrounds:** solid or gradient, not photos (images belong IN slides, not under type)

### Using `color.palette` in decks (when the direction has one)

If the design-system's `color.palette` array is populated (required for vibrant-playful, optional on any other direction), use it to bring visual variety across a long deck without fighting the core accent:

- **Section color-coding** — each section divider + its slides pull a palette hue for accent elements (underlines, small dividers, quote marks). Section 1 = `palette[0]`, Section 2 = `palette[1]`, wrapping at palette length. Same section always uses the same color.
- **Chart series** — use palette in order. Series 1 = `palette[0]`, series 2 = `palette[1]`, etc. Never reach for arbitrary new colors for charts when the palette exists.
- **Category tags or status pills** — one palette hue per category, used consistently throughout the deck.
- **Section divider slides** — use the section's palette hue as the background OR as a wide color band, with large type on top.

What NOT to do with the palette:
- Don't use more than one palette hue per individual slide (except in charts and consistent categorical tags) — a slide with 4 different palette colors feels circus-tent.
- Don't color slide titles in palette hues — titles stay in `fg_primary`. Palette is for accents, data, and navigation, not for running headers.
- Don't invent new colors mid-deck. The palette is the palette.

When the direction has `palette: []` (technical-minimal, editorial, dark-premium, etc.), **don't fabricate one to spice up the deck**. Either the direction's restraint is the right call for this content, or the direction itself is wrong — flag to the user rather than silently adding color.

## Data and charts

- Remove chart junk: no 3D, no shadow, no legend if you can label directly
- **Single-series charts:** one color for data (`fg_primary` or `accent`), `accent` for the one data point you're highlighting
- **Multi-series charts (with palette):** use `color.palette` in order — series 1 is `palette[0]`, series 2 is `palette[1]`, etc. Same category = same hue everywhere in the deck.
- **Multi-series charts (no palette):** use a single accent plus greyscale steps (100%, 60%, 30%) rather than inventing colors
- Big labels, big axis text — remember the 24pt rule
- Tables sparingly; transform tables into visuals when possible
- For financial charts: gridlines light gray, data line 2–3px thick

Libraries that output well: Plotly (for interactive), Chart.js (simple), D3 (custom). For static slides, direct SVG often wins.

## Images

- Full-bleed or consciously framed with padding — no random placement
- Stock imagery: avoid unless carefully selected. Better no image than generic corporate stock.
- Screenshots: crop tight, add a subtle 1px border or shadow to separate from background
- Illustrations: one style throughout the deck, not mixed

## Transitions and animations

Less is more.

- **Between slides:** simple fade or cut. Never "Cube" or "Page Curl."
- **Within slide:** only animate to reveal sequentially if it aids understanding
- **Chart builds:** acceptable — draw lines or bars progressively
- **Everything bouncing in from different directions:** no

## Speaker notes

If generating notes:
- 2–3 sentences per slide maximum
- What you'd say out loud, not what's already on the slide
- Include transitions: "This sets up the next section about..."

## PowerPoint-specific notes

When using the `pptx` skill:

1. Generate design tokens as usual
2. Pass tokens (colors, fonts, sizes) as explicit parameters
3. Create a custom slide master if the deck is long (15+ slides)
4. For heavy design polish, consider generating HTML and exporting to PDF instead

### PowerPoint font caveat

PowerPoint uses system fonts, so Google Fonts won't embed. Options:

- **Use fonts that are on every system:** Georgia (serif), Verdana, Trebuchet MS, or the newer Aptos (Microsoft default)
- **Use Microsoft Cloud Fonts:** Bahnschrift, Seaford, Grandview — look modern, ship with Office
- **Embed fonts in the file:** works for PPTX but file balloons
- **Convert to PDF at the end:** fonts render as designed everywhere

## HTML deck specifics

If building a web-based deck:

- **Fixed aspect ratio:** 16:9 (1920×1080 is standard) — use `transform: scale()` to fit viewport
- **Keyboard nav:** arrow keys, spacebar for next
- **Progress indicator:** subtle bar at bottom or slide number in corner
- **Export to PDF:** CSS `@page` rules + `@media print` so the deck saves well

## Deck-specific anti-patterns

| Avoid | Instead |
|---|---|
| "Agenda" slide with 8 bullet points | Section dividers throughout the deck instead |
| Logo on every slide | Only on title and end |
| Every slide has the title in the same place | OK to be consistent, but break the pattern for hero/stat slides |
| Bullet points everywhere | Use images, charts, quotes, stats instead |
| "Thank you" slide as the final slide | Call to action, contact info, next steps |
| Swoops, curls, 3D transitions | Fade or cut |
| Light gray text on white (for a "subtle" look) | Accessibility fail — bump contrast |
| Horizontal scrolling text / marquee | Never |

## The quality checkpoint

Before delivering a deck: flip through it in presenter mode. If any slide makes you squint, rethink it. If two consecutive slides feel visually identical, change one.
