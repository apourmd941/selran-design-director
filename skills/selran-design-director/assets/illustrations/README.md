# Illustrations — per-direction spot library

Each of the seven aesthetic directions ships an illustration voice that matches its typography, color, and motion. This folder is the canonical asset library for that system.

> Read `references/illustration.md` first. It defines the voice per direction, the "direction test" quality gate, and the anti-patterns to avoid (Corporate Memphis, rainbow palettes, stock clipart scaled up).

---

## Folder structure

```
assets/illustrations/
  README.md                       ← you are here
  technical-minimal/
    hero.svg                      1200×600 marketing hero
    empty-generic.svg             400×400 empty state
    error-404.svg                 400×400 page-not-found
    success.svg                   400×400 completion moment
    ai-prompts.md                 Midjourney / Flux / Ideogram templates
    README.md                     direction-specific usage notes
  editorial/                      (same 6 files)
  dark-premium/                   (same 6 files)
  warm-approachable/              (same 6 files)
  vibrant-playful/                (same 6 files)
  brutalist/                      (same 6 files)
  bold-distinctive/               (same 6 files)
```

Four canonical SVG spots per direction. These aren't meant to cover every situation — they're the baseline that proves the voice. When you need more, use the `ai-prompts.md` in that direction's folder to generate additional illustrations in the same voice.

---

## How the SVGs retint

Every SVG in this library consumes three CSS custom properties instead of hardcoded hex:

| Variable | Role | Falls back to |
|---|---|---|
| `--fg` | primary stroke / text color | `currentColor` |
| `--bg` | panel or negative space | `transparent` |
| `--accent` | direction's signature accent (gold, oxblood, terracotta, safety orange, etc.) | the direction's declared accent |

Wire them at the host element and the SVG will retint to match the page's design tokens. Example:

```html
<div class="illustration">
  <!-- inline SVG or <img> / <object> -->
  <svg class="illus" ...>...</svg>
</div>
```

```css
.illustration {
  --fg:     var(--color-fg-primary, #18181B);
  --bg:     var(--color-bg-primary, #FAFAF9);
  --accent: var(--color-accent,     #0A7A5C);

  width: 100%;
  max-width: 480px;
  height: auto;
}

/* hero usage */
.hero .illustration { max-width: 100%; }
```

Inline the SVG (don't use `<img>`) when you need the CSS variables to cascade in — external references via `<img>` create a separate style context and the variables won't apply.

---

## Picking the right spot

| If you need... | Use | viewBox |
|---|---|---|
| Above-the-fold marketing art | `hero.svg` | 1200×600 |
| "No data yet" placeholder | `empty-generic.svg` | 400×400 |
| Page-not-found (404) | `error-404.svg` | 400×400 |
| Completion / confirmation moment | `success.svg` | 400×400 |

If none of the above fit (search-empty, server-error, onboarding, settings, etc.), treat the closest spot as reference and generate a new one via that direction's `ai-prompts.md` or by drawing SVG directly in the same voice.

---

## Generating more illustrations

Two paths, same as `references/illustration.md` describes:

1. **AI image generation** — open `<direction>/ai-prompts.md`, pick the template, substitute your `[SUBJECT]`, pass it to Midjourney / Flux / Ideogram / Stable Diffusion. Each direction's prompt file is hand-tuned — the style modifiers, palette hints, negative prompts, and aesthetic references are specific to that voice, not generic.
2. **Direct SVG authoring** — open the four spot SVGs for the direction as reference. Match the stroke widths, the `<defs>` patterns, the composition rhythm. For geometric directions (technical-minimal, dark-premium, brutalist) this is often faster than image generation.

Either way: run the "direction test" before shipping. Can a stranger tell which of the seven directions an illustration belongs to, with no other context? If it's ambiguous, push it further into the signature.

---

## Quality standards

- **File size:** each SVG is ≤ 10 KB uncompressed, typically 3–6 KB. Optimize with `svgo` before shipping.
- **Self-contained:** no external fonts, no external images, no `<script>`. Valid SVG 1.1 / 2.0.
- **Single voice per direction:** no style-mixing. A `warm-approachable` SVG does not contain halftone. A `brutalist` SVG does not contain pastel gradients.
- **Palette fidelity:** every SVG uses only the direction's declared colors via CSS variables. Rainbow palettes are a tell.

---

## Extending the library

To add a new canonical spot (e.g. `empty-search.svg`, `error-500.svg`, `settings.svg`):

1. Draft it for ONE direction first to validate the composition works.
2. Port the composition across all seven directions — same concept, rendered in each voice.
3. Update this root README and each direction's README with the new filename.
4. Run the direction test on each rendering before committing.

To fork a direction (e.g. `warm-approachable-pastel`, `brutalist-xerox`):

1. Create a new sibling folder with the 6 required files.
2. Inherit the parent direction's voice but push one signature harder.
3. Document what's different in that folder's README.

---

## See also

- `references/illustration.md` — full voice spec, the direction test, anti-patterns
- `references/aesthetic-directions.md` — each direction's typography, color, motion, spatial logic
- `references/direction-starters.md` — the exact color palette each direction ships with
- `assets/imagery-queries.yaml` — photography prompts (complementary system for when illustration isn't the right lane)
