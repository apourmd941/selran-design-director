# Imagery

Rules for photography, illustration, and icons across all output formats. Most AI-generated designs ruin themselves at the imagery layer: generic stock, mixed illustration styles, emoji icons. This file enforces discipline.

## Query pack

Curated Unsplash/Pexels search strings per direction live at `assets/imagery-queries.yaml`. Use these instead of guessing — generic queries ("minimalist office", "diverse team") reliably return stock trash; the pack queries are tuned to surface on-brand results.

**How to consume at build time:**
- Load the YAML, read the branch for the active direction (e.g., `editorial.photography.hero`).
- Pick the **first** query for reproducibility, or sample one at random for variety across a multi-image artifact.
- Pass directly to Unsplash/Pexels API, or include in the prompt to whatever image-search/generation tool the workflow uses.
- Read the direction's `notes:` field and treat it as a negative filter — skip results that violate it.

**When to fall back to the pack vs. ask the user:**
- Default to the pack. It's tuned per direction and beats any one-off guess.
- Ask the user only when the artifact has a specific subject requirement the pack can't cover (e.g., "a photo of OUR product", "our actual team"). In that case, the user supplies the image.
- If the user provides custom queries or images, those override the pack. The pack is a *starting point*, not a constraint.

**Structure of the YAML:** each direction has `photography.<use_case>` (hero, product_context, team_portrait, texture_abstract, plus direction-specific cases like `hands_at_work` for warm-approachable or `street_raw` for brutalist), `illustration` keywords, an `ai_prompts.<use_case>` block (see below), and a `notes:` avoid-line.

## AI-generated imagery

Each direction in `assets/imagery-queries.yaml` also carries an `ai_prompts:` block with per-use-case prompts tuned for Midjourney / Flux / Ideogram-style image generators. Each prompt carries the full scaffolding — subject, composition, lighting, style/era modifier, palette, camera/medium emulation, aspect-ratio suffix, and negative keywords — so consumers can drop the string straight into the generator without losing direction.

**When to use AI vs. stock:**
- **AI for hero/brand imagery** where specificity matters — a hero that needs a particular subject, palette, and composition will almost always fail stock search but can be dialed in with AI.
- **AI when stock is failing** — if three Unsplash queries return generic laptop-on-desk shots, switch to the AI prompt rather than shipping the fourth-best stock result.
- **Stock for supporting/secondary imagery** — small product-context shots, background textures, team-adjacent context. Stock is cheaper, safer on licensing, and fine when specificity doesn't matter.
- **Stock (or real photography) when authenticity is the point** — testimonial portraits of real customers, actual product photos, anything load-bearing on "this really exists." AI portraits read as AI to most viewers.

**How to consume the `ai_prompts` YAML:**
- Load the YAML, read `<direction>.ai_prompts.<use_case>` (e.g., `dark-premium.ai_prompts.hero`).
- Pick the first prompt (reproducibility) or sample for variety across a multi-image set.
- Pass the full string to the image generator as-is. The aspect-ratio suffix and negative keywords are part of the prompt, not metadata — keep them attached.
- The direction's `notes:` field still applies as a negative filter — if the returned image violates it, reroll.
- For a multi-image project, fix one generator (same model, same version) across all images and generate in batches. Mixing Midjourney + Flux outputs in one deck looks as bad as mixing stock providers.

**Licensing and authenticity — caution:**
- AI imagery licensing varies by tool (Midjourney commercial rights require a paid plan; Flux/Stable Diffusion derivatives depend on model license). Verify before shipping client work.
- Some audiences (editorial publications, regulated industries, some enterprise clients) prohibit AI-generated imagery outright. Ask before using.
- AI imagery will continue to get better at passing as real, but viewers are getting better at spotting it — especially AI portraits with subtle hand/eye artifacts. For the `team_portrait` use case, prefer real photography when the image is load-bearing (e.g., a bio page) and reserve AI portraits for illustrative/editorial contexts where the abstraction is acceptable.

## First principle: no image is usually better than a bad image

A clean empty section reads as intentional. A generic stock photo reads as filler. When in doubt, cut it.

Three cases where imagery genuinely helps:
- **Product shots** — showing the actual artifact being discussed
- **Screenshots** — demonstrating functionality
- **Editorial photography or illustration** that's *purpose-made* for the direction

Three cases where imagery almost always hurts:
- Generic stock (people in suits, team handshakes, abstract "tech" swooshes)
- Mismatched illustration styles (two illustrators, one deck)
- Emoji used as UI icons

---

## Photography by direction

How photographs should *feel* per aesthetic direction. Treatment covers: color grade, framing, subject, composition.

### editorial
Magazine-quality. Natural light, considered framing, subject has agency. Muted/desaturated grade OR rich b&w — not oversaturated. Often asymmetric composition (rule of thirds, negative space). Portraits look like they belong in The New Yorker or Kinfolk.

**AI prompt guidance:** "editorial portrait, natural light, shallow depth of field, muted color palette, 50mm film grain, New Yorker magazine style"
**Avoid:** saturated colors, centered subjects, stock-smile portraits, "tech workspace" generic shots.

### technical-minimal
Product-focused, clean, functional. Screenshots crisp with minimal chrome. Photos (when used) high-resolution, evenly lit, neutral background. Think Stripe, Linear — most "photos" are actually carefully composed product renders or screen recordings.

**AI prompt guidance:** "clean product photography on neutral background, even studio lighting, sharp focus, minimal styling"
**Avoid:** lifestyle photography, warm filters, shallow DOF, anything with a "vibe."

### bold-distinctive
Dramatic, high-impact. Full-bleed, cropped aggressively. Subject fills the frame. High contrast grade. Photography and type overlap intentionally.

**AI prompt guidance:** "bold editorial photography, high contrast, dramatic cropping, strong single subject, studio lighting with hard shadows"
**Avoid:** soft lighting, centered compositions, "tasteful" framing, anything restrained.

### dark-premium
Cinematic. Low-key lighting, rich shadows, warm highlights OR cool-blue grade. Negative space heavy. Often single-subject product photography with soft rim light. Feels like a luxury goods campaign.

**AI prompt guidance:** "cinematic product photography, low-key lighting, rich shadows, warm highlight accents, 35mm film quality, luxury campaign aesthetic"
**Avoid:** overhead flat-lay, bright even lighting, busy backgrounds.

### warm-approachable
Documentary-style, real people, warm grade, film-quality grain. People are actual people (not stock), hands are doing actual things. Medium DOF. Imperfect framing OK — that's what makes it human.

**AI prompt guidance:** "warm documentary photography, real candid moments, 35mm film grain, warm color grade, golden hour light, people doing real work with hands"
**Avoid:** posed "diverse team" stock, laughing-at-laptop shots, anything that looks staged.

### brutalist
Raw, un-retouched, sometimes intentionally low-resolution or posterized. Subjects often mid-action or awkwardly framed. Direct flash OK. The photo's imperfections are the point.

**AI prompt guidance:** "raw un-retouched photography, direct flash, imperfect framing, posterized color, zine aesthetic"
**Avoid:** polish of any kind. If it looks like it's from a magazine, it's wrong for this direction.

### vibrant-playful
Bright, warm, optimistic. Natural light with warmth, candid or semi-candid. Subjects active, often in motion. Palette hues may subtly inform the wardrobe or scene colors. Feels like Mailchimp's marketing site or a modern consumer brand.

**AI prompt guidance:** "warm vibrant lifestyle photography, natural light, people in motion, optimistic energy, film grain, modern consumer brand aesthetic"
**Avoid:** cool grades, serious portraits, clinical studio lighting.

---

## Illustration style by direction

When photography doesn't fit or isn't available, illustration serves. One style per project — never mix.

| Direction | Style | Color approach | Line weight |
|---|---|---|---|
| editorial | Minimal line art, architectural or portrait-inspired | Monochrome or 2-color (accent + ink) | Single-weight (1–2px) |
| technical-minimal | Geometric, isometric product diagrams | Monochrome with accent for emphasis | Single-weight hairline |
| bold-distinctive | Oversized expressive, print-poster inspired | 2–3 flat colors, high-contrast | Variable, often thick |
| dark-premium | Rare — prefer photography. When used, metallic/etched aesthetic | Muted with one accent | Fine detail |
| warm-approachable | Hand-drawn, imperfect, human | Warm palette, earthy | Hand-drawn variable |
| brutalist | ASCII art, pixelated, low-resolution, or raw geometric | Black + 1 accent max | Heavy or none |
| vibrant-playful | Flat-colored, slightly rounded, friendly shapes | **Uses `color.palette` for fills** — each illustration pulls 3–4 palette hues | Either no outline or single-weight |

### The one-illustrator rule

A deck, doc, or site should look like one illustrator made every illustration. If that's not true, don't use illustrations — choose a different visual element (charts, photography, or typographic treatments).

### AI-generated illustrations

Acceptable if they match the direction's style and are consistent across the project. To achieve consistency:
- Use the same prompt scaffold for every illustration in a project
- Fix the style descriptor ("flat vector illustration, muted palette, single-line character") across every prompt
- Generate in batches, pick the consistent ones, discard outliers

Don't mix AI illustrations with stock illustrations. Don't mix different AI models' styles. Don't use any "3D rendered" illustration unless the entire project is 3D-rendered.

---

## Icons

Icon systems are a separate design system inside the design system. Pick one library per project. **Never mix libraries.**

### Recommended libraries by direction

| Direction | Library | Notes |
|---|---|---|
| editorial | Phosphor (regular or light weight) OR Lucide | Hairline matches rule-line aesthetic |
| technical-minimal | Lucide OR Heroicons (outline 1.5px) | Geometric, clean, scales well |
| bold-distinctive | Custom or Iconoir (bold weight) | Larger, more expressive |
| dark-premium | Phosphor (light weight) OR custom | Refined, doesn't shout |
| warm-approachable | Phosphor (regular) OR Feather | Softer corners, friendlier |
| brutalist | System / Unicode characters OR raw HTML (`→` `•` `×`) | Icon libraries betray the aesthetic |
| vibrant-playful | Phosphor (fill or duotone) OR Iconoir | Duotone can use palette hues for fill |

### Icon discipline

- **One stroke weight** throughout the project — never 1.5px mixed with 2px
- **One style** (outline, filled, duotone) — don't mix
- **Size on a grid** — 16px, 20px, 24px, 32px. Not 17px, not 22px
- **Align to cap-height** next to text, not to baseline
- **Color** — inherit from `currentColor` by default. Accent color only when the icon carries meaning (status indicators)

### Emoji are not icons

Emoji in UI is always wrong. They render inconsistently across platforms, they break the direction's aesthetic, and they signal AI-generated work. The only acceptable use of emoji is:
- Content authored by users (chat, comments) — display them as-is
- Intentionally playful direction (vibrant-playful) on a single-element exception (e.g., a lone celebration emoji on a confirmation page)

Never: 🚀 in a heading, 💡 before a tip, ✨ anywhere.

---

## Screenshots

Screenshots appear in decks, docs, landing pages, and portfolios. Discipline:

- **Crop tight.** Remove browser chrome unless the chrome is the point.
- **Match the direction's treatment.** Add a 1px hairline border (`border` token) to separate from page background. No drop-shadow unless warm-approachable.
- **Retina quality.** Always @2x. Never upscale a low-res screenshot.
- **Annotate sparingly.** If you need to point at things, use `accent` colored rules or arrows — not red marker scribbles.
- **Device frames** (iPhone/MacBook mockups) are often AI-slop. Use them only if the device context is relevant; otherwise crop the content directly.

---

## Illustration / imagery anti-patterns

| Avoid | Why |
|---|---|
| Generic stock: handshakes, "diverse team," pointing-at-screen | Everyone has seen it; reads as AI-generated |
| Mixing two illustration styles in one project | Reads as inconsistent, amateur |
| Emoji as UI icons | Platform-inconsistent, breaks direction |
| "3D rendered" abstract shapes (floating orbs, blobs) | Unless the whole project is 3D, these betray AI-generation |
| Imagery that doesn't serve the point | Decoration without purpose = slop |
| Soft-glow drop-shadow on screenshots | Belongs to 2014; crisp hairline is cleaner |
| Upscaled low-res images | Reads as unprofessional; cut instead |
| Photos with people laughing at laptops | The universal marker of "we didn't think about this" |

---

## The final check

Before shipping any artifact with imagery, ask:
1. Would removing every image make the artifact *worse*, or just shorter? If shorter is fine, cut.
2. Does every image look like it came from the same source/style?
3. Does each image serve a specific point, or decorate?

If a single image fails any of these, replace or remove it. Imagery is the most common place AI-generated work falls apart — this is where direction enforcement pays off.
