# Anti-Patterns: Defaults to Refuse

This is the enforcement layer. Before delivering any visual artifact, verify nothing on this list has slipped in. These patterns are what make AI-generated design instantly recognizable as AI-generated. Refuse them unless the user explicitly requested them.

## Typography

**Banned by default:**
- **Inter** — overused to the point of invisibility. Used by everyone, signals "I didn't think about this."
- **Roboto** — Google's default. Same problem.
- **Arial / Helvetica Neue** (as the only choice) — not a creative decision.
- **Space Grotesk** — massively overused in AI-generated portfolios and landing pages.
- **Poppins** — the "startup" font. Legible but characterless.
- **Montserrat** as display type — tired. Fine for body in constrained cases.
- **Comic Sans, Papyrus**, etc. — obviously, except as jokes.

**Preferred alternatives** (mix of free Google Fonts and paid foundries):
- *Modern geometric sans:* General Sans, Satoshi, Geist, DM Sans, Manrope, Hanken Grotesk, Outfit
- *Humanist sans:* Instrument Sans, Söhne (paid), Work Sans, Commissioner, Figtree
- *Serif (editorial):* Fraunces, Instrument Serif, Source Serif, Crimson Pro, EB Garamond
- *Display serif:* PP Editorial New (paid), Canela (paid), Tiempos (paid), or Fraunces at display weight
- *Mono:* JetBrains Mono, Geist Mono, IBM Plex Mono, Berkeley Mono (paid)

**Rules:**
- Use at most two font families per artifact. Three only if one is mono used sparingly.
- Display + body pairing should have visible contrast (serif display + sans body, or geometric display + humanist body).
- Never set body copy in a display font.

## Color

**Banned by default:**
- **Purple-to-pink gradient on white** (`#667eea` → `#764ba2`, or Stripe's generic pastels copied badly). The single most AI-generated-looking choice possible.
- **Untinted neutral grays** (pure `#888`, `#CCC`). Grays should have a subtle temperature — cooler slate, warmer stone, greenish sage.
- **Random rainbow accents**. Dashboards with one color per card = signals unconsidered.
- **Gradient everything**. A gradient hero is fine. A gradient hero plus gradient buttons plus gradient cards is noise.
- **Pure `#000` on pure `#FFF`** for body text — too high-contrast, reads as harsh. Use `#0A0A0B` on `#FAFAF9` or similar.

**Rules:**
- One dominant background, one primary text color, one accent, maybe one secondary accent. That's the budget.
- Grays carry temperature. Pick cool grays *or* warm grays for the project and don't mix.
- If you use a gradient, use it *once* on a single hero element, not throughout.
- Dark mode is not "same design, inverted." It needs its own color decisions — slightly muted accents, lifted blacks (`#0A0A0B` not `#000`), softer text (`#F4F4F5` not `#FFFFFF`).

## Layout & composition

**Banned by default:**
- **The three-feature-card row** on every landing page. "Here are three things we do," each in a card with an icon. Kill it.
- **Perfectly centered everything**. Center-aligned hero, center-aligned section, center-aligned CTA. Asymmetry and left-alignment often read as more considered.
- **Generic hero pattern**: giant centered headline, one sentence of subtext, two buttons side by side. Needs at least one unexpected element to earn attention.
- **Equal-weight grids**. Six items in a 3×2 grid all the same size, with no hierarchy. A big one and five smaller ones usually reads better.
- **Full-bleed hero images of abstract gradients** that don't communicate anything.

**Rules:**
- Asymmetry and overlap signal design intent. Use them at least once per non-trivial artifact.
- Typography should carry weight. Big type is a layout element, not just text.
- Negative space is a feature. Don't fill every region.

## Components & interactions

**Banned by default:**
- **Glassmorphism applied indiscriminately** (blurred translucent cards everywhere). Fine as one specific treatment; terrible as a whole aesthetic.
- **Neumorphism** unless explicitly asked — hard to do well and usually fails accessibility.
- **Bouncy / elastic animations** on everything. Professional UIs use `cubic-bezier(0.2, 0, 0, 1)` and similar ease-out curves, not springs.
- **Rotating 3D cubes**, cursor-following 3D globes, or Three.js-for-the-sake-of-it.
- **Emoji as icons in a serious UI**. Use Lucide, Heroicons, Tabler, or custom SVGs.
- **"AI-feel" glows and particle fields** around every element.

**Rules:**
- Motion should have a reason. Page load reveal, state change, hover feedback. Not decoration.
- 150–250ms is the sweet spot for most UI transitions. Anything >400ms feels sluggish.
- One signature interaction per page. If everything moves, nothing is noticed.

## Document & deck anti-patterns

**In .docx / PDF:**
- Default Calibri or Times New Roman body. Pick an intentional font (EB Garamond, Source Serif, or a considered sans) and set it explicitly.
- Bullet-list-everything layouts. Prose carries authority; bullets fragment it.
- Left-aligned 12pt body with no line-height adjustment — set 1.4–1.6 line-height minimum.
- Rainbow-colored headings. Headings should be the text color at different weights/sizes, or one restrained accent used consistently.

**In .pptx / HTML decks:**
- White background + black text + four bullets per slide = the aesthetic everyone is trying to escape.
- Stock photography of handshakes, lightbulbs, or "diverse team in open office."
- Clipart icons (the bundled PowerPoint ones).
- One slide doing four jobs at once. If it has a chart, a paragraph, a bullet list, and a callout, it has too much.
- Transition wipes between slides. Cross-fade or no transition.

**Rules:**
- A deck slide should carry *one* idea. Everything else serves that idea.
- Use a custom slide master with explicit typography, even for quick decks — it takes 60 seconds and elevates the whole deck.
- Dark decks are fine and often more memorable than light ones. Consider this default.

## Poster / static art

**Banned:**
- Centered headline + centered subhead + centered small-print attribution.
- Generic geometric-shapes-in-the-corners backgrounds.
- Over-polished vector illustrations of people with purple/orange accents.
- Date/time/location info squeezed into a bottom bar as an afterthought.

**Rules:**
- Typography should dominate. A great poster is usually 80% type and 20% everything else.
- If there's imagery, it should be the main event, not decoration.
- Commit to one structural idea (full-bleed photo, typographic grid, asymmetric stack) — don't mix three.

---

## Copy defaults

Copy that fights the visual direction is an anti-pattern of its own — an Editorial layout with "Unlock your potential," a Brutalist zine with "Empower your listening experience," a Technical-Minimal docs page with "Seamlessly supercharge your workflow." The visual discipline is wasted the moment the words belong to a different aesthetic. See `references/voice.md` for per-direction voice profiles, length targets, CTA patterns, and worked examples.

**Universal forbidden phrases — refuse regardless of direction:**

- *Verbs:* unlock, empower, leverage, harness, supercharge, unleash, revolutionize, disrupt, transform (as hype), streamline, elevate.
- *Adjectives:* seamless/seamlessly, powerful, robust, cutting-edge, state-of-the-art, next-generation, best-in-class, world-class, industry-leading, game-changing, revolutionary, magical, effortless.
- *Phrases:* "in today's fast-paced world," "harness the power of," "unlock your potential," "take your ___ to the next level," "the future of ___," "join thousands of happy customers," "trusted by leading brands," "one-stop shop," "end-to-end solution," "we're on a mission to..."
- *Openers:* "Introducing...", "Imagine a world where...", "What if...", "In a time when..."

Rule: if a banned phrase appears in a draft, rewrite the sentence around it — do not merely swap the word. "Enable" is still "empower" in a different hat.

**Copy/visual alignment check (last pass, every artifact):** read the finished copy aloud. Does it sound like the visual it's sitting on? A quiet Editorial page should not crackle like a product launch. A Brutalist page should not coo. A Vibrant-Playful product should not read like a law firm. If the copy could drop unchanged into any other direction, it's too generic — rewrite to the profile in `voice.md`.

---

## Accessibility anti-patterns (hard fails)

These block shipping. A "polished" artifact that fails these is unshipable — it excludes users. See `references/accessibility-check.md` "Part 2 — A11y v2" for the full rule set.

- **`outline: none` without replacement.** Every focusable element needs a visible focus ring. Removing the browser default without providing a custom one is a keyboard-user hostility.
- **Tap targets below 44×44px.** Icon buttons at 32×32, tag close X at 16×16, toggle thumbs at 18px — all fail motor-impaired users.
- **Modals without focus trap.** Tab escaping to underlying page breaks keyboard navigation. Use `<dialog>` or focus-lock.
- **`<div onClick>` as a button.** Invisible to screen readers, not keyboard-accessible, not announced as interactive.
- **Icon-only buttons without `aria-label`.** A `<button><svg/></button>` with no label is silent to screen readers.
- **Animations without `prefers-reduced-motion` override.** Auto-playing carousels, scroll-hijacking animations, parallax — all can trigger vestibular issues. Must be disableable.
- **Forms without `<label>`.** Every `<input>` needs an associated label, programmatically linked via `for`/`id`.
- **Multiple `<h1>` tags per page.** Breaks the heading landmark structure screen readers navigate by.
- **Skipping heading levels** (`<h1>` → `<h3>`). Skipped levels confuse assistive tech.
- **Images-as-buttons without `role` + keyboard handler.** Clickable images must be explicitly interactive.

## Performance anti-patterns (hard fails for marketing pages)

Ship slow and you excluded the real-world mobile user, the same way you'd exclude the screen-reader user. See `references/performance.md` for the budgets and fixes.

- **Google Fonts CDN.** Adds DNS/TLS on the critical path. Self-host.
- **Three+ display font weights on marketing.** Two is the ceiling.
- **`<img>` without `width`/`height`.** Guaranteed CLS.
- **Hydrating a static marketing page.** Astro/11ty/plain HTML, not React.
- **Analytics loaded eagerly.** Defer everything non-critical.
- **No AVIF/WebP fallback chain.** Shipping 2× larger JPEGs is lazy.
- **Animating `width`/`margin`/`height`.** Use `transform` (compositor-only). Layout-property animations tank INP.
- **Full icon library imported for 4 icons.** Use sprite or inline SVG.

## Illustration anti-patterns

See `references/illustration.md` for the full list. Hard fails:

- **Corporate Memphis as default.** Wavy purple-and-green faceless people. #1 AI-slop illustration signature. Never ship unless user explicitly requests.
- **Mixing illustration styles in one composition.** Engraving next to flat vector reads "pulled from a stock library."
- **3D render on technical-minimal or brutalist.** Material physics fights the direction's discipline.
- **Stock clipart scaled up.** A 24×24 icon at 400×400 is pixelation hidden behind SVG.
- **Rainbow palettes in illustration.** Match the direction's declared palette; no "fun" additions.

## Live-iteration anti-patterns

See `references/live-iteration.md` for the full loop. Hard fails when pushing a design past "fine" on user feedback:

- **Applying token changes without showing the diff first.** The user should see "accent `#0A7A5C` → `#6B0F1A`" before you re-render. Silent mutation breaks trust.
- **Accepting "better" without confirming what changed.** If the user says "better" and you can't point to 2–3 specific token moves that made it better, you learned nothing — and the next tweak is blind.
- **Exiting the direction to fix a complaint inside it.** If the user says "feels cold" on a technical-minimal page, don't swap to warm-approachable. Tweak the accent temperature inside technical-minimal. Direction hops defeat the point of the starter.
- **Spiraling into micro-tweaks past cycle 5 without stepping back.** If five cycles haven't landed the design, the problem is architectural, not token-level. Offer "want to step back?" Don't keep nudging.
- **Changing tokens that affect more than the user named without flagging.** User said "make this section louder" — you can't silently bump `type.scale.xxl` for the whole site. Scope the change or name the scope.
- **Adding elements to fix "busy" or "cluttered."** Those words mean subtract, not add. Never add a background gradient to cover visual confusion.

## Direction-fusion anti-patterns

See `references/direction-fusion.md` for the full rules. Hard fails:

- **Four+ axes borrowed from four+ directions.** That's not fusion, that's slop. Cap at 2 borrows per fusion; 3 triggers a flag and a user confirm.
- **"A little bit of everything."** No committed base. Refuse to build — ask the user to name the base direction first.
- **Fusing brutalist + warm-approachable directly.** Aesthetic opposites. One signature from one into the other is the absolute maximum; two borrows is a mess.
- **Borrowing motion from a faster direction into a slower one.** Speeding up editorial or dark-premium with vibrant-playful springs breaks both characters. Slower→faster is fine; reverse is not.
- **Mixing illustration libraries across directions.** Corporate-memphis characters on a technical-minimal base, or engravings on vibrant-playful, read as stock-library pulls. Illustration matches the base direction, always.
- **Using fusion as a substitute for the variations workflow.** If the user wants to explore options, fork the tokens via `references/variants.md` — don't fuse to add interest.

## Figma-sync anti-patterns

See `references/figma-sync.md` for the full spec. Hard fails:

- **Writing to Figma without a dry-run.** The proposed creates / modifies / deletes must be shown and confirmed before any API write hits the file.
- **Silently merging conflicts.** If both sides edited since last sync, present the four-option menu (keep markdown / keep Figma / review each / cancel). Never auto-resolve.
- **Overwriting Figma styles by name-collision.** Before overwriting any style or variable that didn't originate from our sync, check and confirm.
- **Pretending motion / fonts / breakpoints synced when they didn't.** Variables don't represent motion curves or font binaries today. Report what synced and what didn't — do not fake success.
- **Committing `FIGMA_TOKEN` to the repo.** Always `.env`, always gitignored. Never hardcoded.

## Pack-development anti-patterns

See `references/packs.md` for the full spec. Hard fails when authoring or loading a pack:

- **Packs without `pack.yaml` or with an invalid manifest.** Missing `name`, missing `license`, missing `version`, paths in `provides:` that don't exist — all fail validation. The loader refuses the pack.
- **Packs without a `LICENSE` file.** Every pack must carry its own license text. Shipping the placeholder from `pack-template/` as the real `LICENSE` is a defect.
- **Packs that overwrite core files.** If a pack's `provides/` or `pack_overrides/` path collides with a core path (e.g., tries to replace `assets/components/technical-minimal/nav.html`), the loader refuses to install. Packs add and extend; they never overwrite.
- **Commercial packs without license gating.** A pack marked `license: Commercial` that still serves content without a key check is a loader bug — every commercial pack gets gated (env var, `license_key` file, or `.pack-licenses.yaml` acknowledgment) before its content is served.
- **Packs claiming direction names they don't match.** A pack component filed under `technical-minimal/` that uses rounded shapes, saturated fills, and playful motion is lying about the direction. Match the direction's spirit or file under a different direction.
- **Hardcoded hex in pack components.** Pack components must consume `var(--accent)`, `var(--fg)`, `var(--bg)` — never `#0A7A5C` inline. Otherwise the pack breaks surface-split, multi-brand, dark-mode, and pack_overrides.
- **Packs smuggling in core anti-patterns.** Inter, Corporate Memphis, purple-on-white gradients, rainbow palettes, emoji-as-icons — all still banned inside a pack. The anti-patterns list applies to every file the skill serves.
- **Packs claiming the "Selran" name in their branding.** The core skill is Selran Design Director. Pack authors use their own names — "Selran Dashboards Pro" implies official endorsement and should be avoided unless actually endorsed.
- **Packs that skip the seven-direction contract.** A pack cannot invent an 8th direction with unrelated semantics ("gothic-industrial-midjourney") and file it under `provides.directions:`. New directions go through the core's review process; packs that need a novel aesthetic should extend an existing direction via `pack_overrides:`, not invent a parallel taxonomy.
- **Committing license keys to the pack repo.** License keys belong in the user's environment (env var, `.pack-licenses.yaml`, or the purchased `license_key` file) — never bundled with the pack itself.

## Reference-imitation traps

These fire when you ingest a reference (a URL or a screenshot) via `selran reference ingest`. The pipeline mirrors this list at runtime — every trap below has a corresponding caveat code that the reference summary records, and the blender substitutes per the rules in `services/references/blending-rules.md`. The user-facing version of these substitutions is at `references/reference-blending.md`.

The five canonical traps. Each one is a distinct visual signature that signals "this design was started from a famous reference and the imitation wasn't dialed back" — exactly the failure mode reference blending exists to prevent.

### 1. Stripe purple-pink

**Signature signals.** A `#635BFF`-to-`#C84B31`-style purple-to-pink gradient on white, often paired with Inter at multiple weights and an animated gradient hero. Saturated purple accent (hue 250–290°, saturation > 0.45). The single most "I started from Stripe" tell on the public web.

**Caveat code.** `PURPLE_DOMINANT` (fires on the dominant accent's hue + saturation). `INTER_DETECTED` typically fires alongside.

**Substitute.** Pick a single saturated accent from the chosen direction's palette — green for technical-minimal, the direction's warm accent for warm-approachable, the direction's vivid-on-dark accent for dark-premium. Do **not** gradient. Stripe's purple-pink is a brand fingerprint; reproducing it on someone else's site reads as imitation regardless of the rest of the design. A single saturated accent on a calmer surface is what the brand appears to be doing — copy *that* discipline, not the literal hues.

**Why the substitution still honors intent.** The user said "make it feel like Stripe" because Stripe feels considered, restrained-except-where-it-isn't, and visually confident. None of those are the gradient — they're the typographic discipline, the spacing, and the willingness to use one saturated color decisively. The substitution preserves all three.

### 2. Apple SF / glass

**Signature signals.** Frosted-translucent backgrounds with backdrop blur, SF Pro / SF Pro Display / `system-ui` as the primary font, soft shadows under floating cards, hairline borders at near-zero opacity. The "made on a Mac, not made by Apple" look.

**Caveat code.** `APPLE_GLASS_DETECTED` (fires on SF / system-ui font signature; the glass surface signature reinforces it).

**Substitute.** Opaque surfaces from the direction's `color.surface`; humanist sans (General Sans for technical-minimal, Manrope for warm-approachable, Söhne for editorial) at appropriate weight. Hard borders or no borders — never the near-zero hairlines. Drop the backdrop blur entirely; if you want hierarchy on stacked surfaces, use `color.bg_secondary` against `color.bg_primary`, not transparency.

**Why the substitution still honors intent.** Apple's glass aesthetic reads as premium *because* it's tied to Apple's hardware identity and software animation budget. Reproducing it elsewhere reads as cosplay; readers register "Apple-ish" before they register your actual content. Opaque surfaces with deliberate elevation tokens carry the premium feel without the imitation read.

### 3. Linear's Inter

**Signature signals.** Inter at multiple weights (Regular for body, SemiBold for h2, Bold for h1, occasionally Medium for h3), a near-monochrome palette with a single muted-purple accent (`#5E6AD2`-ish), 8px radius applied uniformly, motion under 200 ms. The "we read Linear's blog and want to feel that exact level of considered" look — usually achieved by inheriting Linear's most legible tokens (Inter, the radius, the spacing) and almost nothing else.

**Caveat code.** `INTER_DETECTED` (fires on Inter as either body or display family).

**Substitute.** General Sans for body and display in technical-minimal blends; Manrope for warm-approachable blends. Preserve Linear's spacing rhythm (4 base, 8px grid) and tracking (tight) if the rest of the brief warrants technical-minimal — those are *direction* signals, not Linear-specific signals, and they survive substitution. The display family substitute matters most: General Sans has more character per glyph than Inter, which is what makes a non-Linear product not look like a Linear product even when the rest of the system is similar.

**Why the substitution still honors intent.** "Like Linear" usually means "calm, technical, considered, not over-styled." Inter is just one expression of that; General Sans expresses the same intent without the immediate "yes, this is Linear" pattern-match. The user gets what they wanted (the calm) without the imitation read.

### 4. Notion grey

**Signature signals.** `#37352F`-on-`#F7F6F3` (warm near-black on warm off-white), near-zero accent saturation (any accent is desaturated to almost grey), uniform serif-via-system fall-throughs, generous reading column. The "we want it to feel like Notion's writing surface" look.

**Caveat code.** `NOTION_GREY_DETECTED` (fires on the bg/fg pair within ΔE5 of `#F7F6F3` / `#37352F` and accent saturation < 0.20).

**Substitute.** A less-grey neutral pair: `#191919` on `#FAFAF9`. Then add a single saturated accent from the chosen direction (a warm muted accent for warm-approachable, a saturated green for technical-minimal, etc.). The two-tone Notion palette is the imitation read; introducing one decisive accent breaks the pattern-match without losing the calm reading surface.

**Why the substitution still honors intent.** Users invoking "Notion" usually mean "long-form-friendly, calm, not-distracting." That's the warm bg + dark fg + generous column — not the literal `#37352F`. The substitute hexes are within 1–2 perceptual steps of Notion's pair, but the introduction of a saturated accent (which Notion doesn't use) signals "this is its own product" instead of "this is a Notion clone." Reading comfort is preserved; brand mimicry is not.

### 5. Vercel pure black

**Signature signals.** `#000000` background (literally `rgb(0,0,0)`), neon-bright accents (saturation > 0.7, lightness > 0.5 — typically a vivid green or magenta), a monospace headline font, edge-to-edge layouts with hairline borders. The "we want the dark-mode developer-aesthetic from a famous deployment platform" look.

**Caveat code.** `VERCEL_PURE_BLACK_DETECTED` (fires on `#000000` bg + monospace family + neon accent triple).

**Substitute.** `dark-premium`'s `#0A0A0B` (a warmer-than-pure-black that sits ~3% lighter and slightly cooler) with a single accent from the direction's `color.accent`. The pure-black is the imitation tell; the warmer dark surface preserves the "premium dark UI" read without the brand mimicry. Replace the neon with the direction's saturated accent (still vivid, but tuned to the direction's character rather than to Vercel's brand). Keep the monospace headline if the rest of the brief wants brutalist; substitute General Sans Bold for technical-minimal or dark-premium briefs.

**Why the substitution still honors intent.** Pure black on screens reads as harsh in most contexts — Vercel can pull it off because their content is dense, their motion is restrained, and their typography is unusually careful. Most teams reproducing it inherit only the pure-black and end up with a surface that's harder to read than they realized. The dark-premium substitute carries the "premium dark UI" feeling without the eye strain or the imitation; the substituted accent carries character without invoking Vercel specifically.

---

## The self-audit question

After building anything, ask: **"If I showed this to a designer, would they know it was AI-generated?"**

If the answer is "probably yes" — it's usually because you hit one of the items above. Revise.
