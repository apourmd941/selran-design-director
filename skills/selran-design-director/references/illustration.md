# Illustration — per-direction spot illustration and hero-art system

Every direction ships an illustration voice that matches its typography, color, and motion. This reference specifies that voice — what illustrations look like in each direction, what they're used for, and how to generate more in-style when the 7 baseline spots in `assets/illustrations/{direction}/` aren't enough.

> **Illustration is not decoration.** In this system, spot illustrations carry empty states, 404s, success moments, and onboarding. They need the same discipline as type and color. Defaulting to "friendly rounded characters" for every direction is an AI-slop tell.

---

## When to use illustration vs. photography vs. icon

**Illustration** — abstract, characterful, brand-forward:
- Empty states, 404s, success screens, onboarding moments
- Marketing hero when photography would feel generic or stocky
- Editorial section dividers
- Deck cover art, chapter openers
- When the subject is abstract (a concept, a value, an outcome)

**Photography** — concrete, human, aspirational:
- Hero moments where the subject is a real person or real artifact (product shots)
- Testimonial modules
- Press/about pages

**Icon** — functional, utilitarian:
- Nav, toolbar, button adornments
- Status signifiers (check, warning, error — the system should provide these, not emoji)

Pick one lane per composition. Mixing illustration + photography in a single marketing module usually looks amateurish — consult `references/composition-rubric.md`.

---

## The seven illustration voices (by direction)

### technical-minimal
- **Style:** geometric, wireframe, isometric-adjacent. Hairline strokes (1px at display size). Mostly monochrome with single accent-color highlight.
- **Line weight:** 1–1.5px strokes. Never filled shapes for primary forms.
- **Color:** grayscale base (neutral zinc) + one accent fill at ~20% opacity for emphasis.
- **Composition:** precise, orthogonal, grid-aligned. Empty states use a single elegant line-drawing.
- **Signature move:** thin dotted or dashed accent lines — callouts, dimensions, annotations in the illustration itself.
- **Think:** Stripe's illustration system, Linear's empty states, Raycast spot art.
- **Don't:** rounded cartoon characters, gradients, drop shadows, 3D rendering.

### editorial
- **Style:** hand-drawn or engraving-inspired. Cross-hatching for shading. Serif-era feel.
- **Line weight:** variable — thicker for outlines (2–3px), thin for detail.
- **Color:** two-tone palette (ink + parchment), occasional accent for emphasis.
- **Composition:** classical — symmetric or rule-of-thirds, generous white space around the subject.
- **Signature move:** ornamental borders, rule lines, decorative flourishes (sparingly).
- **Think:** The Atlantic's feature illustrations, NYT Magazine spot art, old botanical prints.
- **Don't:** flat vector cartoons, bright saturated fills, modern isometric.

### dark-premium
- **Style:** minimalist, atmospheric. Sparse linework on dark. Gold/champagne accents.
- **Line weight:** 1px strokes in muted tones; accent elements get slightly thicker weight.
- **Color:** dark background, bone/ivory strokes, gold or champagne accent (< 10% coverage).
- **Composition:** negative-space-first. The illustration is often mostly empty with a single precise element.
- **Signature move:** subtle gold leaf effect — tiny gilded elements rather than large color fields.
- **Think:** luxury brand websites (Porsche, Roger Dubuis), fine-dining invitations, museum wayfinding.
- **Don't:** anything colorful, anything playful, anything loud.

### warm-approachable
- **Style:** soft, rounded, human. Organic shapes with no sharp corners. Gentle gradients acceptable (soft, not jarring).
- **Line weight:** rounded stroke caps, 2–3px strokes. Or filled shapes with soft pastel palette.
- **Color:** warm neutrals base + one or two pastel accents. Orange/peach/salmon/sage/blush.
- **Composition:** centered, friendly, often with a light cast shadow for grounding.
- **Signature move:** hand-drawn imperfections — a line that wobbles slightly, a shape that's gently asymmetric.
- **Think:** Calm app illustrations, Notion's marketing spots, Headspace's character work (restrained, not cartoony).
- **Don't:** Corporate Memphis (wavy limbs, purple-and-green, faceless people). That's AI-slop default.

### vibrant-playful
- **Style:** bold, saturated, geometric. Strong shapes, flat fills. Can include abstract characters.
- **Line weight:** optional — can be all-filled or thick-stroke (3–4px).
- **Color:** uses the 3–5 hue palette defined in the design system. Bright, confident.
- **Composition:** dynamic — elements rotated, overlapping, spilling outside frame. Never symmetric.
- **Signature move:** pattern fills (dots, stripes, halftone) as texture; bold solid-color backgrounds.
- **Think:** modern marketing sites (Figma, Loom, Framer), consumer app onboarding, festival posters.
- **Don't:** 3D render reflectivity, pastel softness, serif detailing.

### brutalist
- **Style:** raw, photocopy-aesthetic, high-contrast. Halftone or stippling for shading. No smooth curves.
- **Line weight:** 2–4px hard strokes. Can include hand-traced imperfection.
- **Color:** black + one accent color at most. Sometimes black + red, black + yellow.
- **Composition:** asymmetric, often cropped brutally. Text overlapping image is a signature.
- **Signature move:** photocopy grain, intentional registration offset, overprint texture.
- **Think:** zine aesthetic, Dazed & Confused, early Bloomberg Businessweek redesign.
- **Don't:** smooth gradients, rounded shapes, friendly characters.

### bold-distinctive
- **Style:** editorial-scale, display-typography-adjacent. Illustration is often integrated with oversized type.
- **Line weight:** variable — can be thick abstract shapes or fine detail
- **Color:** one strong accent (red, oxblood, cobalt) + black/white. High contrast.
- **Composition:** illustration supports oversized display type; often positioned as texture behind headline
- **Signature move:** display-scale numerals as graphic elements; illustration becomes typographic.
- **Think:** Pentagram posters, Pitchfork feature art, bold-direction fashion magazines.
- **Don't:** cute, friendly, rounded, decorative for its own sake.

---

## The seven canonical spots (per direction)

Each direction's `assets/illustrations/{direction}/` folder ships SVG templates for these seven states. They consume `--accent`, `--fg`, `--bg` CSS variables so they retint when the host page's tokens change.

| File | Purpose | Typical placement |
|---|---|---|
| `hero.svg` | Marketing hero or onboarding cover | Above the fold, 1200×600 typical |
| `empty-generic.svg` | Empty state (no data yet) | Inside empty-state card |
| `empty-search.svg` | No results found | Search results page |
| `error-404.svg` | Page-not-found | 404 page center |
| `error-500.svg` | Server/crash error | Error page center |
| `success.svg` | Completion / confirmation moment | Success modal, onboarding end |
| `settings.svg` | Settings or preferences empty/intro | Settings page header |

Copy an SVG into your project and wire:

```css
.illustration {
  --accent: var(--accent);
  --fg: var(--fg);
  --bg: var(--bg);
  width: 100%;
  max-width: 480px;
  height: auto;
}
```

The SVGs use `currentColor` and CSS variables for all color values so they retint with the host's tokens.

---

## Generating more illustrations in-style

When you need an illustration beyond the canonical seven, generate one matching the direction's voice. Two paths:

### Path A — AI image generation (Midjourney / Flux / Ideogram / Stable Diffusion)

Use a direction-specific prompt template. The templates live in `assets/illustrations/{direction}/ai-prompts.md`. Each direction's prompts are scaffolded with the right style modifiers, color hints, and negative prompts.

**Example (technical-minimal):**

```
isometric line illustration of [SUBJECT],
thin 1px strokes, monochrome zinc grayscale, single accent at #0A7A5C,
wireframe aesthetic, precise geometric forms,
orthogonal composition, grid-aligned,
white background, no shadows, no gradients, no 3D rendering,
style of Stripe illustration, Linear empty state, Raycast spot art
--ar 3:2 --v 6
```

**Example (editorial):**

```
engraving-style illustration of [SUBJECT],
cross-hatching shading, variable line weight, ink on parchment palette,
classical symmetric composition, generous white space,
botanical print aesthetic, Atlantic feature illustration feel,
no flat vector, no bright colors, no modern isometric
--ar 3:2 --v 6
```

Full prompt templates per direction in `assets/illustrations/{direction}/ai-prompts.md`.

### Path B — Generate SVG directly (for programmatic icons and simple spots)

For geometric, rule-based illustrations (technical-minimal line drawings, brutalist shapes, dark-premium minimal marks), write SVG directly. Use the canonical spot SVGs as reference for:
- Stroke widths matching the direction
- Color variable patterns
- Typical viewBox proportions (1200×800 for hero, 400×400 for spots)

---

## Quality gate: the "direction test"

Before shipping any illustration, run it past this test. Can a stranger — given nothing but the illustration and the seven direction names — correctly guess which direction it belongs to?

- **technical-minimal:** isometric wireframe → yes, clearly technical
- **editorial:** engraved bird → yes, clearly editorial
- **dark-premium:** single gold arc on black → yes, clearly dark-premium
- **vibrant-playful:** bright geometric shapes with halftone → yes, clearly vibrant
- **brutalist:** photocopy-texture asymmetric shape → yes, clearly brutalist

If the answer is ambiguous — "it could be warm or vibrant" — the illustration is drifting toward generic AI-slop. Push it further into the direction's signature.

---

## Common anti-patterns

1. **Corporate Memphis everywhere.** Wavy purple-and-green people with no faces and impossibly long limbs. This is the #1 AI-slop illustration default across every tool. **Never ship this unless the user explicitly asks**, and even then, warn them.
2. **Mixing styles in one composition.** A hand-drawn engraving next to a flat-vector character — reads as "pulled from a stock library."
3. **Gradient overload on warm/vibrant directions.** Subtle gradient = good; three overlapping gradients = 2014 iOS style.
4. **3D-render illustrations on technical-minimal or brutalist.** The materials physics fights the direction's discipline.
5. **Generic "people at desks" illustrations.** For empty states especially — abstract the concept, don't literalize it.
6. **Stock icon clipart as illustration.** Icons are icons; illustration is illustration. Don't promote a 24×24 flat icon to a 400×400 hero by scaling it up.
7. **Faceless figures on warm-approachable.** This is the faceless-person Corporate Memphis tell. Give figures faces, or skip the figure entirely.
8. **Rainbow palettes.** Use the direction's declared palette. If the direction has 3 colors, the illustration uses those 3. No exceptions.

---

## Implementation at build time

1. When an artifact calls for illustration, check `assets/illustrations/{direction}/` for a matching canonical spot
2. If present, inline the SVG (or serve as file) with CSS-variable bindings — retints automatically
3. If not present, consult the direction's `ai-prompts.md` for a prompt template, substitute the subject, generate via the user's preferred tool
4. Run the result through the "direction test" — does it pass?
5. Optimize with `svgo` before shipping (see `references/performance.md` for the SVG optimization pattern)

---

## See also

- `references/imagery.md` — photography style, icon library picks, anti-emoji; includes the photography-vs-illustration decision
- `references/aesthetic-directions.md` — each direction's signature gestures (many illustration cues derive from these)
- `references/performance.md` — SVG optimization + lazy-loading for below-the-fold illustrations
- `references/direction-starters.md` — each starter's color palette feeds directly into illustration palette
- `assets/imagery-queries.yaml` — Midjourney/Flux prompt templates (complements the illustration prompts here)
