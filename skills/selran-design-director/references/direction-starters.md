# Direction Starters

A starter is a pre-baked `design-system.md` — one per aesthetic direction. Instead of asking the user to describe their taste in prose and translating, the skill renders six thumbnails and the user picks.

Starters live at `assets/direction-starters/<direction>.md` and are valid design-system.md files on their own: copy one into the project root and the skill can execute from it unchanged.

## When to use starters

The visual picker is the default first move on a fresh request **unless**:

- The user's brief already names a direction ("build a brutalist zine" → use brutalist.md directly, no picker)
- The user's brief pins enough specifics that direction is moot ("Stripe-like dashboard with emerald accent" → technical-minimal.md, possibly tweaked)
- An existing `design-system.md` is already in the project — inherit from it, don't re-elicit

Otherwise: render the picker.

## The seven starters

Each starter exists as one file. The filename is the direction key.

| File | Direction | Character in one line | Palette? |
|------|-----------|------------------------|----------|
| `editorial.md` | editorial | warm neutrals, serif display, rule-line dividers, quiet confidence | no (single accent) |
| `technical-minimal.md` | technical-minimal | near-white bg, geometric sans, hairline borders, tabular nums | no (single accent) |
| `bold-distinctive.md` | bold-distinctive | cream + hot red, huge display type, asymmetric, one big moment | no (single accent) |
| `dark-premium.md` | dark-premium | deep bg, muted gold, slow ease-out-expo, one hero glow | no (single accent) |
| `warm-approachable.md` | warm-approachable | peach bg, terracotta accent, 16px radius, italic serif accents | no (single accent) |
| `brutalist.md` | brutalist | white/black only + safety orange, mono everywhere, 0 radius, 0 motion | no (single accent) |
| `vibrant-playful.md` | vibrant-playful | cream bg, coordinated 5-hue palette, soft rounding, micro-motion | **yes** (5-hue) |

All seven pass WCAG AA out of the box across fg/bg, secondary/bg, muted/bg (large), accent/bg (large), bg/accent (button text — normal), and semantic colors. For vibrant-playful, every palette entry also passes AA-large as a decorative element on `bg_primary`.

## How the picker renders

1. For each of the six starters, read the YAML frontmatter.
2. Substitute tokens into `assets/thumbnail-template.html` — same `{{path.to.token}}` convention as the full preview.
3. Fill these placeholders per direction (these are content, not tokens):
   - `{{DIRECTION}}` — the direction key, uppercased for the label
   - `{{HEADLINE}}` — a short phrase that fits the voice; see the table below
   - `{{SUBLINE}}` — one line of body copy
   - `{{FONT_LINKS}}` — same Google Fonts substitution logic as preview
4. Render each to `preview/thumbs/<direction>.png` at 480×320 via Playwright (same pipeline as render-pipeline.md describes).
5. Present the six thumbnails to the user with a one-line description each. present as a visual choice (see SKILL.md § capability ladder) with the thumbnails as visual options if available, otherwise list them as text choices with image paths.
6. On pick: copy the chosen starter to `design-system.md` at the project root. Run the silent a11y audit (it will pass). Proceed to build.

### Headline / subline content per direction

Keep these in sync with the voice. Do not rewrite per project — these demo the aesthetic, not the user's product.

| Direction | HEADLINE | SUBLINE |
|-----------|----------|---------|
| editorial | "A considered voice." | "Typography-forward. Rule lines, not shadows. Italic as a real choice." |
| technical-minimal | "Dense. Quiet. Systematic." | "Hairline borders, tabular numerals, one saturated accent doing all the work." |
| bold-distinctive | "Earn attention." | "Oversized display type as architecture. Asymmetric. One hero moment." |
| dark-premium | "Cinematic restraint." | "Deep backgrounds, soft whites, one muted jewel accent. Slow ease-out." |
| warm-approachable | "Soft, human, warm." | "Peach and terracotta. Rounded corners. Italics feel natural here." |
| brutalist | "HTML aesthetic. On purpose." | "Monospace everywhere. Zero radius. Inversion on hover. No motion." |
| vibrant-playful | "Alive. Coordinated. Warm." | "Five harmonized hues. Section color-coding. Soft rounding. Micro-motion." |

## Customizing after the pick

The starter is a starting point, not a lock. After picking, the user can:

- Override any token (color, font, radius) — the skill honors manual edits
- Ask for a variation (see "three-variation explorer" in SKILL.md) — produces 3 small swaps off the picked starter
- Swap direction entirely — re-run the picker

The picked starter writes to `design-system.md` with the `meta.project` field filled from the user's brief (not left as "Editorial starter").

## Adding a new starter

To add a 7th direction (or fork an existing one):

1. Create `assets/direction-starters/<name>.md` — full YAML frontmatter matching the schema
2. Run the a11y audit against it (`assets/contrast-check.py` — see accessibility-check.md)
3. If any pair fails AA, tune the accent or fg/bg until it passes — ship nothing that fails
4. Add a row to the table above with a one-line character description
5. Add a row to the HEADLINE/SUBLINE table
6. Update the aesthetic-directions.md reference with the corresponding description if this is a new direction (not a fork)

The a11y gate is not optional. A starter that the skill's own audit flags is a bug.

## The optional `color.palette` field

Six of the seven starters leave `palette: []` empty — they commit to a single accent doing all the work, and multi-color would fight their aesthetic. Vibrant-playful is built around its palette.

Any direction CAN opt into a palette — the schema allows it. When a user on a bold-distinctive or warm-approachable base asks for "more color variety" or "different colors per section," add a coordinated 3-hue palette to their existing tokens rather than swapping direction. Rules:

- **Palette size:** 3 to 8 hues. More than 8 is a rainbow.
- **Harmony:** equal saturation range, compatible temperatures. Sampling from analogous or split-complementary positions on a color wheel works; random picks don't.
- **`palette[0]` matches `accent`** unless you have a reason to break that (keeps the primary accent visually continuous with the rest).
- **A11y gate:** every palette entry must pass AA-large (≥3.0) against `bg_primary`. If it's going to appear as button text or as a label background, it must pass AA-normal (≥4.5) too.
- **Never auto-extend a direction's palette.** If the user has technical-minimal and types "add more colors," ask whether they want to switch to vibrant-playful or add a minimal 3-hue palette. Don't silently adopt.

## Anti-patterns

- Don't render the picker when the user's brief already names a direction. It's friction, not choice.
- Don't let picker thumbnails drift from the actual starters. They render FROM the starter files — no hand-authored thumbnail content.
- Don't auto-pick. The starter system exists so the user chooses at the moment it matters.
- Don't blend two directions in the thumbnails. Each tile is one direction, committed.
- Don't add a palette to a direction that's meant to be single-accent just because an artifact "feels boring." Evaluate the actual need — sometimes it IS meant to feel restrained. If the user explicitly wants more color, re-open the direction choice.
