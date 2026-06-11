# Aesthetic Directions

Seven curated directions. Each one is fully specified — color behavior, typography approach, spatial logic, motion feel — so "Editorial" means the same thing every time the skill triggers. If the user picks a direction, lean all the way in. Half-editorial and half-technical-minimal just produces mush.

---

## 1. Editorial

**Feels like:** a print magazine — The New Yorker, Wallpaper, Kinfolk. Authority through typography and grid discipline.

**Color behavior:**
- Warm neutrals dominate: cream, paper, ink. `#F5F1EA` / `#1A1A1A` or similar.
- One accent color used sparingly. Usually a deep color — oxblood, forest, navy — not a bright one.
- No gradients, ever. Flat fields only.

**Typography:**
- Serif display (Fraunces, Instrument Serif, EB Garamond at weight 500+) paired with a restrained sans body (Work Sans, Söhne, Figtree).
- Large type hierarchy: display runs 48–96px on web, body at 16–18px with generous line-height (1.6+).
- Drop caps acceptable on long-form content.
- Italic is a real typographic choice, not decoration.

**Spatial logic:**
- Strong grid. Usually 12-column, with intentional breakage for emphasis.
- Left-aligned body copy by default (center-aligned reads as amateur).
- Generous margins. Let content breathe.
- Rule lines (thin horizontal dividers) to separate sections — `1px solid rgba(0,0,0,0.12)`.

**Motion:**
- Minimal. Fades only, 200–300ms. No bouncing, no parallax, no scroll-triggered animation carnival.
- Think "quiet confidence," not "hey look at me."

**Signature gestures:**
- Display type sized as a hero element in its own right.
- Byline-style metadata (small caps, wide letter-spacing) beneath headlines.
- Pull quotes set much larger than body, with deliberate typographic styling.

---

## 2. Technical-Minimal

**Feels like:** Linear, Stripe, Vercel, Railway. Dense information, calm presentation, systematic.

**Color behavior:**
- Off-white or near-black background (`#FAFAF9` or `#0A0A0B`). Pick one — light or dark — and commit.
- Text in a high-contrast but softened pair (`#18181B` on `#FAFAF9`, or `#F4F4F5` on `#0A0A0B`).
- One accent (often a saturated single color — brand-blue, emerald, amber). Used for CTAs and key highlights only.
- Grays carry subtle temperature (slate for cool, stone for warm).

**Typography:**
- Modern geometric sans for everything (General Sans, Geist, DM Sans, Satoshi).
- Sometimes mono as an accent for numbers, code, or tiny labels (JetBrains Mono, Geist Mono, IBM Plex Mono).
- Tight type scale: 12/14/16/20/32/48. Not a lot of sizes.
- Font weight as the main hierarchy tool — 400 for body, 500 for emphasis, 600 for headings. Rarely go bolder.

**Spatial logic:**
- Dense but breathable. Components hug each other on a 4px rhythm (8px half-steps permitted when the layout needs extra air).
- Cards with hairline borders (`1px solid rgba(0,0,0,0.08)`), not shadows.
- Tabular data common. Tabular numerals required (`font-variant-numeric: tabular-nums`).

**Motion:**
- Fast (120–200ms) ease-out transitions. `cubic-bezier(0.2, 0, 0, 1)`.
- Subtle hover feedback only. No hero animations.
- Focus rings visible and intentional.

**Signature gestures:**
- Mono labels in small-caps above section headings.
- Inline small numeric badges or version tags.
- Keyboard shortcut hints in the UI (`⌘K` in styled chips).
- Gridlines visible on hover in tables/datasets.

---

## 3. Bold-Distinctive

**Feels like:** a cult fashion brand, a manifesto, a Kickstarter by someone who knows what they're doing. Oversized type, asymmetric, earns attention.

**Color behavior:**
- Either one extremely saturated color against a neutral (hot red on cream, acid yellow on charcoal), or a restrained duotone.
- Flat fields. No gradients unless they're the whole point.
- Occasionally inverted sections for rhythm (alternating light-on-dark / dark-on-light).

**Typography:**
- Display type HUGE — 80–200px on web, used as layout elements, sometimes bleeding off the edge.
- Display can be a characterful serif (PP Editorial New, Fraunces at weight 900) or a monumental sans (Druk, Migra, something weighty).
- Body in a neutral, readable sans — General Sans, Inter alternatives.
- Letter-spacing manipulated deliberately — tight for display, generous for small caps.

**Spatial logic:**
- Asymmetric. Off-grid intentional breaks. Diagonal flow.
- Typography overlaps images, images overlap type.
- Negative space used as a design element.
- Sometimes intentionally "poster-like" — one hero idea per scroll.

**Motion:**
- Hero moments get one big orchestrated entry (staggered reveal on page load).
- Scroll-triggered reveals acceptable if used once or twice, not constantly.
- Hover states on big type can be dramatic (color shift, underline sweep).

**Signature gestures:**
- Display type as layout architecture — big numbers, massive section markers.
- Intentional text-crop at section edges, creating tension.
- Large numeric section markers (01, 02, 03) styled as display elements.
- One unexpected wildcard per page — a rotated element, a handwritten note, a texture overlay.

---

## 4. Dark-Premium

**Feels like:** a high-end tech product page, an arthouse film poster, a luxury watch brand. Cinematic, considered, no noise.

**Color behavior:**
- Deep backgrounds (`#0A0A0B` to `#18181B`), never pure black.
- Text in soft white (`#F4F4F5`, not `#FFFFFF`).
- One accent that's either a muted jewel tone (emerald `#10B981` muted, sapphire `#3B82F6` muted) or a warm metallic (gold `#D4AF37`, copper `#B87333`).
- Sometimes a single dramatic hero gradient or glow, used once.

**Typography:**
- Display: high-contrast serif (Canela, Tiempos, PP Editorial New) OR a characterful sans (Söhne, General Sans Semi-Bold).
- Body: clean humanist sans at weight 400 with line-height 1.6.
- Numbers tabular and often emphasized — this aesthetic loves stats and figures.

**Spatial logic:**
- Full-bleed hero sections. Plenty of vertical space.
- Strong vertical rhythm.
- Cards with subtle 1px borders in a mid-gray (`#27272A`), not shadows.
- Occasional dividing lines or geometric accents.

**Motion:**
- Slower than Technical-Minimal — 250–400ms — and ease-out-expo (`cubic-bezier(0.19, 1, 0.22, 1)`).
- Subtle glow effects on interactive elements — restrained.
- Scroll-triggered reveals feel cinematic here.

**Signature gestures:**
- One "hero moment" element with soft glow (a product, a number, a chart).
- Thin accent underlines beneath headings.
- Small-caps metadata in muted gray.
- Dark-on-dark layered cards (slightly lifted backgrounds inside a darker page).

---

## 5. Warm-Approachable

**Feels like:** a boutique hotel site, a neighborhood coffee brand, a thoughtful nonprofit. Human, soft, not-corporate.

**Color behavior:**
- Warm backgrounds: cream, peach, sand, soft pink. `#FAF3E7` / `#FFF4ED` / `#F5E6D3`.
- Warm text: deep brown or warm charcoal, not black (`#3D2817`, `#2D1B0F`).
- Accents in warm tones — terracotta, ochre, olive, dusty rose. Saturated but not neon.
- Gradients if any are warm-on-warm, very subtle.

**Typography:**
- Serif or humanist sans. Nothing geometric or cold.
- Fraunces, Caveat for hand-drawn accents, Crimson Pro for body.
- Or warm sans: Commissioner, Instrument Sans.
- Italics feel natural here.

**Spatial logic:**
- Softer corners. Border-radius 12–24px, not sharp.
- Curved or organic dividers acceptable.
- Cards feel like paper — subtle shadow, warm tint.
- Imagery: photographic, warm-toned, real (not stock-handshakes).

**Motion:**
- Gentle. 300–400ms ease-in-out.
- Slight hover lifts. Nothing jolty.

**Signature gestures:**
- Hand-drawn underlines or arrows (SVG, single-weight).
- Photography with warm film tones.
- Circular or organic-shape elements among the orthogonal layout.
- Occasional script accent (italic or handwritten) on a single phrase.

---

## 6. Brutalist / Raw

**Feels like:** an art zine, a punk poster, a developer's manifesto site. Unapologetic, unpolished-on-purpose, high-contrast.

**Color behavior:**
- High contrast: pure black on bright colors, or stark neutrals.
- Signal colors: safety-vest orange, warning yellow, neon green, hazmat red — used confrontationally.
- White space is functional, not elegant.
- No gradients. No shadows. No rounded corners.

**Typography:**
- System fonts embraced deliberately (`Times New Roman`, `Courier New` as statements).
- Or monospace everywhere (Space Mono, JetBrains Mono).
- Display weights often heavy-all-caps or monospace-huge.
- Underlines on links (plain HTML default aesthetic).

**Spatial logic:**
- Rectangular, gridded, boxy.
- Hairline borders around everything. 1px or 2px, solid.
- Tables look like actual HTML tables.
- Images often un-cropped, un-edited, slightly pixelated.

**Motion:**
- Almost none. Instant state changes.
- Hover states: inversion (bg becomes fg, fg becomes bg).
- No ease curves — linear or no animation at all.

**Signature gestures:**
- Monospace labels everywhere.
- Rectangular hover-inversion on buttons.
- Visible grid lines as structural elements.
- File-size / word-count / timestamp metadata displayed prominently.
- HTML "view source" aesthetic — like the designer wants you to read the markup.

---

## 7. Vibrant-Playful

**Feels like:** Mailchimp, Duolingo, Liquid Death, Headspace, Notion marketing. Warm, consumer-facing, coordinated multi-color. Confident and friendly, never childish. The kind of design that earns trust by feeling *alive*.

**Color behavior:**
- Warm off-white background (`#FFFBF5`, `#FFF9F0`, or cream). Softened near-black text, not pure.
- A **coordinated palette of 3–5 hues** (stored in `color.palette`), each used deliberately: one for CTAs, one for data/charts, one for highlights, etc.
- Palette hues harmonize — equal saturation range, compatible temperatures. Not a rainbow. Think "brand system," not "crayon box."
- `palette[0]` typically matches the primary `accent` for continuity.
- Gradients only when it's ONE hero gradient, carefully composed.

**Typography:**
- Confident warm-humanist sans (Satoshi, General Sans, Söhne, DM Sans at 500).
- Medium weight (500) dominates for body and buttons — friendly, not heavy.
- Display leans into slight character — a rounded or slightly condensed variant at 700 for punctuation moments.
- No decorative script fonts. No Comic Sans adjacents.

**Spatial logic:**
- Soft rounding throughout: 12px cards, 20px on large surfaces, pill-shape reserved for tags.
- Generous padding — elements breathe. 48–64px section spacing common.
- Section color-coding: different panels, chart series, tag categories each pull a palette hue in order.

**Motion:**
- Medium tempo (250–350ms), slightly playful easing — a soft ease-out-back (`cubic-bezier(0.34, 1.1, 0.64, 1)`), never a sproingy spring.
- Micro-interactions: small lifts on hover, subtle color pulses on select, icon animations on state change.
- One orchestrated moment per screen; restraint still wins.

**Signature gestures:**
- Color-coded tag/pill system — each category gets its palette hue, consistent across the product.
- Chart series colored in palette order (index 0, 1, 2...) — never grayscale for data.
- Section dividers or section labels colored per section (Chapter 1 = palette[0], Chapter 2 = palette[1], etc.).
- Illustrations or icon tints pull from palette — never from an arbitrary third color.
- Avoid: every element in a different color, palette-for-the-sake-of-palette, gradient abuse.

---

## How to pick quickly

Given the user's brief, match on:
- **Audience seriousness** — executives/finance/legal → Editorial or Technical-Minimal. Consumer brand/marketing → Vibrant-Playful or Warm. Creative → Bold or Vibrant. Dev/technical → Technical-Minimal or Brutalist. Luxury/lifestyle → Dark-Premium or Editorial.
- **Information density** — high density → Technical-Minimal. Medium with variety → Vibrant-Playful. Low density → Bold-Distinctive or Editorial.
- **Emotion to evoke** — trust → Editorial or Technical-Minimal. Desire → Dark-Premium or Warm. Energy → Bold-Distinctive or Vibrant-Playful. Solidarity → Brutalist or Warm. Delight → Vibrant-Playful.
- **Color appetite** — wants ONE accent doing all the work → any of the first six. Wants coordinated multi-color (charts, sections, categories) → Vibrant-Playful, or a palette-equipped variant of Warm or Bold.

When in doubt between two close directions, pick the more distinctive one. Mediocrity lives in the average.
