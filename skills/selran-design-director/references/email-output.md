# Email Output

Rules for producing transactional and marketing emails that carry the design system into the inbox. Email is the output format most likely to destroy a design system, because the rendering surface is worse than every browser from 2008. This file captures the discipline needed to land a design intact.

Companion scaffold: `assets/email-template.html` — a bulletproof 600px template with `{{token.path}}` placeholders matching `assets/preview-template.html`.

---

## Why email is different

Every assumption from web output breaks in email:

- **MSO / Outlook desktop** renders HTML via Word's engine, not a browser. No flexbox, no grid, no CSS variables, no modern selectors, no `position`, no `float` reliably, no animations, no SVG.
- **No JavaScript.** Ever. Not a debate.
- **Table-based layout.** Nested `<table>` is the only reliable way to produce columns, padding, or alignment that renders in Outlook.
- **Inline styles required.** Most clients strip or ignore `<style>` blocks (Gmail web keeps them, Gmail mobile sometimes doesn't, Outlook 365 partially, Yahoo unpredictable). Every load-bearing style goes inline; the `<style>` block is for `@media` queries and hover states only.
- **Dark mode is unpredictable.** iOS Mail and Apple Mail honor `prefers-color-scheme`. Outlook dark-mode forcibly inverts colors in ways you can't fully override. Gmail dark-mode sometimes recolors text.
- **Image blocking is default** in Outlook and many corporate clients. Every image needs alt text that reads as sensible copy on its own; the layout must hold without images.
- **600px max content width.** This is the lowest common denominator. Wider content gets zoomed out or truncated in Outlook.
- **Web fonts degrade silently.** Outlook ignores `@font-face`. Use a `<link>` in the head for clients that support it, and a system-font fallback stack for everyone else.

---

## Email architecture

### Nested tables

A canonical email has at least three table layers:

1. **Outer 100%-width table** with the body background color — full-width so dark-mode-aware clients paint it correctly.
2. **Inner 600px centered table** holding the actual content.
3. **Section tables** inside the inner table, one per content block (header, hero, body, CTA, footer).

All tables carry `role="presentation"`, `border="0"`, `cellpadding="0"`, `cellspacing="0"` so screen readers skip them and Outlook doesn't inject default spacing.

### Bulletproof buttons

A CSS button with `padding` and `background` is insufficient — Outlook collapses the padding and the button becomes unclickable outside the text. The bulletproof pattern:

- A `<table>` with a single `<td>` styled as the button background, with padding on the `<td>` itself (not the `<a>`).
- An `<a>` with `display: inline-block` and a `mso-padding-alt` CSS property (Outlook-specific, gives it a click target).
- An MSO conditional comment that injects a VML `<v:roundrect>` rendering of the same button — this is what Outlook desktop actually displays.

### Web fonts

- Link the font in `<head>` via `<link rel="stylesheet" href="https://fonts.googleapis.com/css2?...">`.
- In every element's inline `style`, declare a font-family stack: `"{{type.body}}", -apple-system, BlinkMacSystemFont, "Segoe UI", Arial, sans-serif`.
- Outlook ignores the web font and falls back to Arial or Segoe UI. Pick a fallback weight that reads similar to your display face.
- Wrap web-font `<link>` in an MSO conditional to hide it from Outlook (avoids a broken font request).

### MSO conditional comments

```html
<!--[if mso]>
  ...Outlook-only markup (VML buttons, table width fixes)...
<![endif]-->
<!--[if !mso]><!-->
  ...markup for everything else...
<!--<![endif]-->
```

Use these sparingly — for VML buttons, fallback dimensions, and Outlook-specific padding fixes.

---

## Tokens to email translation

Each design-system token maps to a specific place in the email template:

| Token | Email usage |
|---|---|
| `color.bg_primary` | Outer table background, `<body>` bgcolor attribute |
| `color.bg_secondary` | Inner content table background (if the page has a contrasting card feel) |
| `color.fg_primary` | Body paragraph color, heading color |
| `color.fg_secondary` | Preheader, footer metadata |
| `color.fg_muted` | Unsubscribe link, fine-print |
| `color.accent` | CTA button `bgcolor` and VML `fillcolor`, link color |
| `color.accent_hover` | Unused in email — no hover in most clients |
| `color.border` | Hairline dividers (`<td>` with `border-top`) |
| `type.display` | Hero headline inline `font-family` with fallback stack |
| `type.body` | Paragraph inline `font-family` |
| `type.mono` | Preheader eyebrow label, if used |
| `type.scale.display` | Hero headline size (cap at 36–42px for email — larger gets weird in Outlook) |
| `type.scale.xxl` | Section heading size (cap at 24–28px) |
| `type.scale.base` | Body text size, minimum 14px for legibility on mobile |
| `type.leading.body` | Paragraph `line-height` |
| `spacing.base_unit` | `<td>` padding values. Section padding typically `base_unit * 5–8` (40–64px) on desktop, `base_unit * 3` (24px) on mobile via media query |
| `spacing.radius.sm` | Button border-radius, applied to `<td>` and VML `arcsize` |
| `motion.*` | Unused in email — clients strip transitions |

Tokens consumed by the template use the same `{{token.path}}` convention as `assets/preview-template.html`.

---

## Direction-specific adjustments

| Direction | Email adjustments |
|---|---|
| **editorial** | Long-form body copy stays long-form. Use serif display face in the hero, `<hr>` replaced with `<td>` rule lines using `border-top: 1px solid {{color.border}}`. Drop caps fake-able with a large inline-styled first letter — test in Outlook, often fails. |
| **technical-minimal** | Dense but breathable. Tabular numerals won't render in Outlook (`font-variant-numeric` stripped) — if numbers need alignment, use fixed-width `<td>` widths. Hairline borders via `<td>` `border` attribute. Keep CTA subtle — no big color blocks. |
| **bold-distinctive** | Oversized display type caps at ~42px in email (larger breaks in Outlook). Color-block sections with solid `bgcolor` on `<td>`s — alternate light/dark rhythm. Asymmetric composition impossible in Outlook tables; approximate with offset padding. |
| **dark-premium** | Dark backgrounds with `bgcolor="#0A0A0B"` on every `<td>` and the outer table. Text explicit-colored (`color: #F4F4F5`) on every element — Outlook dark-mode will try to invert and fail weirdly otherwise. Avoid the gradient hero — use solid fill. No glow effects. |
| **warm-approachable** | Warm cream `bgcolor` on outer table. Border-radius via `<td>` style (Outlook strips, falls back to square — acceptable). Photography in hero uses alt text like "Freshly baked loaf on the counter" — these render as the preheader if images blocked. |
| **vibrant-playful** | Keep color blocks. Use **solid fills** instead of gradients — Outlook renders gradients as a fallback solid color, often the wrong one. Palette-coded section dividers work via `<td>` `bgcolor` per section. Cap palette at 3 hues in one email — more reads as busy on small screens. |
| **brutalist** | Preserve raw borders — every `<td>` carries a 1px or 2px solid black border. System fonts (`Times New Roman`, `Courier New`) work perfectly here because every client has them. Monospace body text acceptable. No rounded corners, no shadows — this direction is easiest to ship intact. |

---

## Required building blocks

Every email assembled by this skill includes, in order:

1. **Preheader** — hidden-ish single line above the header. Shows in inbox preview. Use `display: none; max-height: 0; overflow: hidden;` trick. Always write this — it determines open rate.
2. **Header** — logo (left or center), optional nav (rare in transactional). Logo as `<img>` with alt text, max height 40–48px, linked to homepage.
3. **Hero block** — either a headline + subhead, or an image with headline overlay (overlay via separate rows, not CSS — overlays fail in Outlook).
4. **Body text block** — single column, 60–65ch line length, base body style.
5. **One-column section** — standard content block with heading + paragraph.
6. **Two-column section** — two `<td>`s side-by-side with a spacer `<td>` between. Must collapse to stacked rows on mobile via media query (`<table>` with class `stack` that sets width to 100% under 600px).
7. **CTA button** — bulletproof pattern, centered or left-aligned per direction.
8. **Divider** — `<td>` row with `border-top` or a thin `bgcolor` filled cell.
9. **Footer** — company name, physical mailing address (CAN-SPAM requires US postal address for commercial mail; many jurisdictions have parallel rules), unsubscribe link (one-click for Gmail promotional), preferences link, copyright line. Footer typography one step smaller and `fg_muted`.

---

## Dark-mode handling

- **What works broadly:** explicit color declarations on every element (`color:`, `bgcolor=""`). Avoid relying on defaults — dark-mode clients will invert defaults.
- **iOS Mail / Apple Mail:** `@media (prefers-color-scheme: dark) { ... }` in a `<style>` block works. Swap backgrounds and text in that block.
- **Outlook.com / Outlook mobile:** uses `[data-ogsc]` attribute selector. Target with `[data-ogsc] .text { color: #fff !important; }`.
- **Outlook desktop:** forces its own dark-mode color inversion on text blocks — can partially override with `mso-color-alt` (Outlook 2019+ only) but not reliably.
- **Pragmatic advice:** design the light-mode email to be legible if a dark-mode client inverts it poorly. Avoid pure white (`#FFFFFF`) backgrounds — they invert to pure black and crush everything. Use an off-white like `#FAFAF9`. For logos and icons in images, provide a dark-mode variant swapped in via the iOS/Apple media query, but accept Outlook will show the light version.

---

## Accessibility

- **`lang="en"`** (or appropriate) on `<html>` — screen readers use this for pronunciation.
- **Meaningful `<title>`** — some screen readers read it, and it's what Gmail web shows in the tab.
- **Alt text on every `<img>`** — required. Load-bearing images ("Your order is confirmed") get descriptive alt; decorative images get `alt=""`. Never omit the attribute entirely.
- **Semantic headers** — `<h1>` for the primary subject, `<h2>` for section headings. Email renderers respect heading hierarchy for screen readers even though Outlook styles `<h1>` weirdly (override inline).
- **Color contrast** — same WCAG AA rules as web. Body text against `bg_primary` must hit 4.5:1; large display can drop to 3:1.
- **Plain-text alternative** — every HTML email ships with a `text/plain` MIME part. Most email service providers (Mailchimp, Postmark, SendGrid) auto-generate this; verify it's not garbage. Required for deliverability and for users on text-only clients.
- **Link text descriptive** — "View order" not "click here." Screen readers read link text out of context.

---

## Testing

- **Litmus or Email on Acid** — essential for cross-client preview. No substitute.
- **Manual smoke test** — Gmail web (desktop), Gmail mobile app, Outlook 365 web, Outlook desktop (Windows), Apple Mail (macOS), iOS Mail, Yahoo Mail web. Anything that fails in Outlook desktop usually fails the worst.
- **The Litmus / Outlook desktop gap** — Litmus renders Outlook correctly most of the time, but there's a known gap where real Outlook on a specific Windows build renders differently from Litmus's screenshot. For high-stakes campaigns, test on real Outlook via a VM or a colleague's machine.
- **Dark-mode test** — toggle dark mode in Apple Mail, Outlook mobile, and Gmail mobile. The three behave differently; catch invert-brokenness here.
- **Image-blocked test** — open the email with images disabled. The layout should still make sense and the CTA still be visible (alt text on the CTA image or a text-based bulletproof button).
- **Spam filter check** — run through Litmus spam-test or Mail-Tester. Image-to-text ratio, link domain, authentication (SPF/DKIM/DMARC) all matter for inbox placement.

---

## Anti-patterns

| Avoid | Why |
|---|---|
| Background images on `<body>` or section tables | Outlook drops them. Use `bgcolor` solid fills instead. |
| `position: absolute/relative/fixed` | Stripped by most clients. |
| Flexbox or CSS grid | Outlook doesn't support. Use nested tables. |
| External stylesheets (`<link rel="stylesheet">` for your own CSS) | Stripped in most webmail. Inline every style. |
| Forms inside email | Most clients strip `<form>`. Link to a web page for any input. |
| `<iframe>` | Blocked everywhere. Blocked for a reason. |
| JavaScript | Strip-on-sight. Ignore anyone who says "but kinetic email uses JS" — those use CSS hacks, not JS. |
| Web fonts without fallback stack | Outlook renders in whatever its default is — usually Times New Roman. Always declare a fallback. |
| Gradient backgrounds | Outlook renders as a solid color (often the wrong one). Use solid fills. |
| CSS `box-shadow`, `transform`, `animation` | Stripped by Outlook and many mobile clients. |
| Full-width images wider than 600px | Outlook doesn't scale them — they overflow and break the layout. Cap at 600px native, provide a 2x for retina via the `srcset`-less workaround (separate `<img>` with media query hiding the non-retina). |
| Emoji in subject line relied on as load-bearing | Render inconsistently across clients — fine as decoration, never as the only signal. |

---

## The final check before send

1. Does the email read sensibly with images disabled?
2. Does the CTA still work on mobile (tap target ≥44×44px)?
3. Does the footer include mailing address + unsubscribe?
4. Does the subject line under 50 chars and preheader under 90 chars?
5. Does dark-mode preview still look like the brand, or has it become grayscale?
6. Does Outlook desktop render the bulletproof button as a colored rectangle, not naked text?

If any fails, fix it before approving. Email is expensive to re-send.
