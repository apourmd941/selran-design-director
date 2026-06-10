# Performance — per-direction guidance for shipping to real users

Polished design is half the story. The other half is **how fast it loads** on a mid-tier Android phone on 4G. This reference sets performance budgets per artifact type, explains the font-loading, image-optimization, and layout-stability patterns that meet them, and documents the bundle-size impact of each direction's font pairing so you can make informed trade-offs.

> Every design system shipped via this skill is held to these budgets unless the user explicitly opts out. Poor performance is an anti-pattern the same way Inter-everywhere is an anti-pattern — both signal the work wasn't finished.

---

## Budgets (what "good enough" means)

| Artifact | LCP | CLS | INP | Total JS (gz) | Total CSS (gz) | Total fonts | Notes |
|---|---|---|---|---|---|---|---|
| Marketing landing page | < 2.5s | < 0.1 | < 200ms | < 150 KB | < 40 KB | < 200 KB (2 weights) | Static, MPA; no client routing overhead |
| Product dashboard | < 3.0s | < 0.1 | < 200ms | < 500 KB | < 80 KB | < 250 KB | SPA acceptable |
| Long-form doc / article | < 2.0s | < 0.05 | < 100ms | < 80 KB | < 30 KB | < 160 KB | Reading; font matters more, JS matters less |
| Slide deck (HTML) | < 3.0s | < 0.1 | N/A | < 200 KB | < 60 KB | < 200 KB | Decks can be heavier; user expects load |
| Native iOS/Android | N/A | N/A | N/A | N/A | N/A | Embed fonts | Covered via platform tooling |

Measure in the field (real-user monitoring) or in lab (Lighthouse on mid-tier Android emulator, 4G throttle). Don't measure on a 16" MacBook on Wi-Fi — it lies.

---

## Font loading strategy

Fonts are the single largest source of "design-system overhead." Unoptimized, a 3-weight font family can add 400–600 KB — more than the rest of the page combined.

### The recipe (applies to every direction)

1. **Self-host, don't CDN.** Third-party Google Fonts / Adobe CDN adds a DNS lookup + TLS handshake on the critical path. Serve from your own origin.
2. **Subset aggressively.** If Latin-only, cut to `unicode-range: U+0000-00FF` + punctuation (saves 60–80% of each file).
3. **Two weights maximum** for marketing pages: one for body, one for display. Three weights acceptable for product UIs (regular, medium, semibold).
4. **Woff2 only.** Modern browsers (>99% of real traffic) support woff2. Don't ship woff or ttf fallbacks unless IE11 matters (it doesn't).
5. **`font-display: swap`** on every face. Never `block` (invisible text delay kills LCP); `optional` trades for FOUT we can't control.
6. **Preload the two critical faces** with `<link rel="preload" as="font" type="font/woff2" crossorigin>`. Not more than two — preload too much and the browser's prioritizer panics.

### Template for a two-font stack

```html
<link rel="preload" href="/fonts/display.woff2" as="font" type="font/woff2" crossorigin>
<link rel="preload" href="/fonts/body.woff2" as="font" type="font/woff2" crossorigin>

<style>
  @font-face {
    font-family: "Display";
    src: url("/fonts/display.woff2") format("woff2");
    font-weight: 700;
    font-display: swap;
    unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+2000-206F, U+2122;
  }
  @font-face {
    font-family: "Body";
    src: url("/fonts/body.woff2") format("woff2");
    font-weight: 400 600;   /* variable; one file covers the range */
    font-display: swap;
    unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+2000-206F, U+2122;
  }
</style>
```

### Variable fonts win when they're available

If the chosen family has a variable version (Inter Variable, Fraunces Variable, Recursive, Commissioner, General Sans Variable), ship it instead of multiple weight files. One variable file (~50–100 KB) covers every weight on the type scale and leaves room for fine-grained weight adjustments without new requests.

### Font loading and the i18n layer

If `i18n.scripts` declares non-Latin scripts (CJK, Arabic, Devanagari, etc.), **do not ship the full Noto family**. Subset per script:

- Noto Sans JP full = ~16 MB per weight. Japanese-only subset (JP kanji + kana + punctuation) = ~4 MB.
- Arabic Noto = ~400 KB full; subset to Arabic + Arabic Presentation Forms = ~180 KB.
- CJK: ship the region you need (JP, KR, SC, or TC), not the combined family.

See `assets/i18n/font-fallbacks.yaml` "Loading strategy" section for the `@font-face` unicode-range patterns per script.

---

## Per-direction font budget

The primary face drives cost. These numbers assume self-hosted woff2, Latin-subset, two weights:

| Direction | Display | Body | Variable? | Two-weight cost (gz) | Notes |
|---|---|---|---|---|---|
| technical-minimal | General Sans / Inter Tight | Inter Variable | ✅ | ~80 KB | Cheapest. Inter var is one file. |
| editorial | Fraunces Variable | Söhne / Source Serif Pro | ✅ (Fraunces) | ~140 KB | Fraunces var is the main cost; Söhne static ~60 KB/weight |
| dark-premium | Saol Display / Editorial New | Söhne | ❌ | ~180 KB | Saol and Editorial New are licensed statics; no variable option |
| warm-approachable | Manrope | Manrope Variable | ✅ | ~70 KB | One family, one file. Lean. |
| vibrant-playful | Sora / Plus Jakarta Sans | Inter or Jakarta Variable | ✅ | ~90 KB | Both common choices are variable |
| brutalist | Space Grotesk | IBM Plex Mono | Partial | ~130 KB | Plex Mono is chunky (~55 KB/weight static) |
| bold-distinctive | Migra / Saol Display | General Sans | ❌ (Migra) | ~200 KB | Display-serif costs; Migra often 100+ KB at display weights |

**Rule of thumb:** if total font cost > 200 KB gz, either drop a weight, switch to a variable sibling (Söhne → Work Sans Variable, Migra → Fraunces Variable Italic at 900), or accept the cost and budget JS/CSS proportionally tighter.

---

## Image optimization

Images are the second biggest source of bloat. For marketing pages, hero + 2–3 feature images is common; each must ship responsive + modern format.

### The picture element — the baseline

```html
<picture>
  <source srcset="/img/hero-640.avif 640w, /img/hero-1280.avif 1280w, /img/hero-1920.avif 1920w"
          type="image/avif"
          sizes="(min-width: 768px) 60vw, 100vw">
  <source srcset="/img/hero-640.webp 640w, /img/hero-1280.webp 1280w, /img/hero-1920.webp 1920w"
          type="image/webp"
          sizes="(min-width: 768px) 60vw, 100vw">
  <img src="/img/hero-1280.jpg"
       srcset="/img/hero-640.jpg 640w, /img/hero-1280.jpg 1280w"
       sizes="(min-width: 768px) 60vw, 100vw"
       width="1280"
       height="720"
       alt="Real alt text describing the content"
       loading="lazy"
       decoding="async">
</picture>
```

**AVIF is 40–50% smaller than JPEG** at equivalent quality. WebP is 25–30% smaller. Ship AVIF first, WebP as fallback, JPG as baseline. Modern browsers (>95%) handle AVIF.

### Rules that matter

- **Always** set `width` and `height` attributes on `<img>`. This is what prevents CLS (layout shift as images load).
- **`loading="lazy"`** on below-the-fold images; never on the LCP hero.
- **`decoding="async"`** lets the browser defer decode off the main thread.
- **Three breakpoint sizes are enough** for most layouts (mobile / tablet / desktop). Don't ship 10 variants — the selection cost outweighs the bandwidth savings.
- **Generated content:** if images are AI-generated (per `references/imagery.md`), export at 2× the largest displayed size max, then downscale variants. Don't ship 4K images for 800px containers.

### For illustrations and logos: SVG

- **Always SVG**, never rasterized. SVG scales, compresses via gzip to ~30% of source, and looks crisp on retina.
- Optimize with `svgo` — strip metadata, merge paths, round precision to 2 decimals. Typical gain: 60–80% smaller.
- Inline SVGs (directly in HTML) over `<img src="file.svg">` for icons and logos ≤ 5 KB. Save the request.

---

## CLS (Cumulative Layout Shift) budget

CLS measures how much the page jumps during load. Budget: **< 0.1** for marketing, **< 0.05** for reading surfaces.

### The five CLS sources and their fixes

| Source | Fix |
|---|---|
| Images without dimensions | Always set `width` + `height` on `<img>` |
| Webfonts swapping in | `size-adjust` or choose a system fallback with matching metrics; use `font-display: swap` accepting a brief FOUT is better than layout shift on load |
| Ad/embed iframes | Reserve space with explicit `aspect-ratio` CSS |
| Dynamically injected content (banners, cookie notices) | Inject above the fold **before** LCP paint, or use `position: fixed` (takes no layout space) |
| Animation that changes layout | Use `transform` / `opacity` (compositor-only), never `width` / `height` / `margin` in transitions |

### The font-metric trick

Match the system fallback's x-height and cap-height to the webfont before swap:

```css
@font-face {
  font-family: "Display";
  src: url("/fonts/display.woff2") format("woff2");
  font-display: swap;
  size-adjust: 98%;        /* webfont is 2% smaller than fallback at same size */
  ascent-override: 95%;
  descent-override: 23%;
  line-gap-override: 0%;
}
```

Tuning these for each chosen font eliminates the shift entirely when the webfont swaps in. Tools like `fontaine` (Nuxt) or the manual calculator at `https://screenspan.net/fallback` do this automation.

---

## JavaScript budget

For marketing pages, the target is **< 150 KB gzipped** total. That's maybe 450 KB parsed. On a mid-tier Android, every 10 KB of JS adds ~30ms of parse/compile — and JS runs on the main thread, blocking input.

### What blows the budget

- A framework you don't need (React for a landing page that could be static HTML)
- Animation libraries loaded eagerly (Framer Motion, GSAP at top of page)
- Analytics scripts (Segment + GTM + Mixpanel stacked = 80 KB before your code)
- Icon libraries as JS (Lucide React imported with all icons = 200 KB instead of the 4 you use)

### Fixes

- **Static HTML for marketing:** Astro, 11ty, Hugo, or plain HTML. No hydration.
- **Per-component dynamic imports:** `const Chart = lazy(() => import('./Chart'))` — the heavy chart loads only when the user scrolls to it.
- **Defer non-critical scripts:** `<script defer>` for analytics; below-the-fold JS via `<script src=... type=module defer>`.
- **Icon strategy:** SVG sprites or one-off inline SVGs. Never a full icon library unless you use 50+ of its icons.
- **CSS before JS:** inline the critical above-the-fold CSS (~10 KB) in `<head>`; load the rest via `<link>`. The page paints before any JS runs.

### Analytics-bloat mitigation

Batch analytics:
- One beacon, one vendor. Not four.
- Load analytics last (`<script defer>` after main content).
- Consider server-side analytics (Plausible, Fathom) — zero client JS.

---

## Per-direction performance notes

### technical-minimal
- Lowest font cost (Inter variable). Easiest direction to hit budgets.
- Animations are short (150ms) — favor `transform` / `opacity` only
- Gridlines and borders: use `box-shadow` or `border` — never multi-background SVGs

### editorial
- Fraunces Variable is large (~100 KB) — consider Fraunces Static if you only need 2 weights
- Drop caps and small caps features: prefer OpenType features (`font-feature-settings`) over image replacements
- Long-form articles benefit from `font-display: optional` (no swap flash during reading) if you can tolerate 100ms invisible-text window on first visit

### dark-premium
- Saol/Editorial New are the heaviest display fonts in the library (~110 KB gz per weight). Budget accordingly or substitute Fraunces Variable at 900.
- Dark backgrounds hide JPEG artifacts — can use higher compression (quality 75 vs. 85)
- Gold/champagne accents can be shipped as SVG gradients inline (< 200 bytes each) instead of hero background images

### warm-approachable
- Rounded radii are free — don't cost bytes. Image corners use `border-radius` in CSS.
- Soft shadows: layer two `box-shadow` values, don't use image-based shadows
- Manrope is the leanest humanist sans — keep it

### vibrant-playful
- Animation-heavy direction. Use `will-change: transform` only on elements mid-animation; remove after.
- Bright colors compress well in AVIF (flat-color areas)
- If using gradients extensively, prefer CSS gradients (0 bytes) over SVG backgrounds

### brutalist
- Plex Mono is chunky. If on a budget, swap to JetBrains Mono (leaner).
- High-contrast hard edges compress exceptionally well — JPEG at 70 quality still looks intentional
- Motion budget: often zero. Direction favors no motion → automatic CLS/INP wins.

### bold-distinctive
- Display-serif costs are real. If LCP is suffering, serve a preview hero with system font, swap in Migra after paint.
- Oversized type: consider serving the display headline as pre-rendered SVG for predictable rendering, bypassing font load entirely
- Text-heavy marketing benefits most from `font-display: optional` + careful fallback metric matching

---

## Performance checklist (before shipping)

Run through this on every artifact before delivery. Hard fails block shipping.

### Fonts
- [ ] Self-hosted (not Google Fonts CDN)
- [ ] Two weights max for marketing, three for product UI
- [ ] `font-display: swap` on every face
- [ ] Preload the two critical faces
- [ ] Subset to declared `i18n.scripts` — no full Noto for Latin-only projects
- [ ] Variable font if the family offers one

### Images
- [ ] `<picture>` with AVIF → WebP → JPG fallback chain
- [ ] `width` and `height` on every `<img>`
- [ ] `loading="lazy"` on below-the-fold images
- [ ] `decoding="async"` on all images
- [ ] Responsive `srcset` with 3 sizes
- [ ] SVGs for illustrations/logos, optimized with svgo

### JavaScript
- [ ] Marketing: static HTML (Astro/11ty/plain). No framework unless justified.
- [ ] Below-the-fold components via dynamic import
- [ ] Analytics: one vendor, deferred
- [ ] Icon strategy: inline SVGs or sprite, not a full library

### CSS
- [ ] Critical above-the-fold CSS inline in `<head>`
- [ ] Non-critical CSS via `<link rel="stylesheet">` (renders without blocking)
- [ ] Unused CSS purged (Tailwind's `content: []` properly configured)

### CLS prevention
- [ ] `size-adjust` on webfonts to match fallback metrics
- [ ] `aspect-ratio` on embed/iframe containers
- [ ] Banners/cookie notices injected above LCP or positioned fixed

### Measurement
- [ ] Lighthouse run on mid-tier Android emulator, 4G throttle — not desktop Wi-Fi
- [ ] LCP/CLS/INP within budget for the artifact type
- [ ] Real-user monitoring (RUM) in production (Vercel Analytics, Cloudflare, custom)

---

## Anti-patterns

- **Shipping Google Fonts CDN.** Adds DNS/TLS; self-host is faster every time.
- **Three+ display weights.** One display weight is usually enough; two is the ceiling for marketing.
- **`<img>` without dimensions.** Guaranteed CLS.
- **Hydrating a static marketing page.** If it doesn't interact beyond forms, it doesn't need React.
- **Inline base64 images.** Inflates HTML + blocks parsing + caches poorly. Always use separate files.
- **JS-driven animations that change layout.** Use CSS transforms exclusively for animation.
- **Loading analytics eagerly.** Defer everything that isn't critical path.
- **Claiming "fast" without measuring.** If you haven't run Lighthouse on a throttled profile, the claim is empty.

---

## See also

- `references/anti-patterns.md` — the generic AI-slop enforcement list (overlaps with some perf anti-patterns above)
- `references/web-output.md` — how tokens map to CSS; affects how efficiently styles compile
- `references/mobile-web.md` — "Performance checklist" section at the bottom
- `references/accessibility-check.md` — a11y v2 rules (focus indicators, motion preference) overlap with perf concerns
- `assets/i18n/font-fallbacks.yaml` — `@font-face` loading strategy for non-Latin scripts
