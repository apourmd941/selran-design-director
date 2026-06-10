# OG image templates

Per-direction 1200×630 Open Graph / Twitter-card scaffolds. Each template is a standalone HTML file wired to design tokens; render to PNG via Playwright or Puppeteer.

## Files

| Template | Direction | Layout |
|---|---|---|
| `technical-minimal.html.template` | technical-minimal | Logo + accent dot, large clean headline, hairline divider, small mono metadata |
| `editorial.html.template` | editorial | Dateline, italic serif headline with accent italic span, subtitle, byline rule |
| `dark-premium.html.template` | dark-premium | Dark bg, soft accent radial glow, luminous headline, 2px accent underline |
| `warm-approachable.html.template` | warm-approachable | Soft-rounded secondary-bg card, dot logo, headline with italic accent |
| `vibrant-playful.html.template` | vibrant-playful | Color block with palette blend, bold headline, colored pill metadata |
| `brutalist.html.template` | brutalist | 8px frame border, uppercase mono, hard 2px rules, inverted bytes tag |
| `bold-distinctive.html.template` | bold-distinctive | Huge italic display headline, oversized numeric section marker |

## Placeholders

All templates use these placeholders:

**Token paths** (same as other exports):
- `{{color.*}}` — bg, fg, accent, palette, border
- `{{type.display}}`, `{{type.body}}`, `{{type.mono}}`
- `{{type.tracking.*}}`, `{{type.leading.display}}`
- `{{meta.version}}`

**OG-specific content**:
- `{{og.title}}` — the headline (required)
- `{{og.subtitle}}` — 1–2 sentence supporting line (optional)
- `{{og.logo}}` — small wordmark/label in the corner (required)

## Render to PNG via Playwright

```python
# pip install playwright && playwright install chromium
from playwright.sync_api import sync_playwright

with sync_playwright() as p:
    browser = p.chromium.launch()
    page = browser.new_page(viewport={"width": 1200, "height": 630})
    page.goto(f"file://{absolute_path_to_rendered_html}")
    page.wait_for_load_state("networkidle")
    page.screenshot(path="og.png", clip={"x": 0, "y": 0, "width": 1200, "height": 630})
    browser.close()
```

Or in one shell line:

```bash
npx playwright-chromium screenshot \
  --viewport-size 1200,630 \
  --full-page=false \
  file://$(pwd)/og.html og.png
```

Or with Puppeteer:

```js
const puppeteer = require('puppeteer');
(async () => {
  const browser = await puppeteer.launch();
  const page = await browser.newPage();
  await page.setViewport({ width: 1200, height: 630, deviceScaleFactor: 2 });
  await page.goto(`file://${__dirname}/og.html`, { waitUntil: 'networkidle0' });
  await page.screenshot({ path: 'og.png', clip: { x: 0, y: 0, width: 1200, height: 630 } });
  await browser.close();
})();
```

`deviceScaleFactor: 2` gives you a retina 2400×1260 source — downscale for shipping if file-size matters. Social platforms display at ~1200×630 regardless.

## HTML meta tags

```html
<!-- Open Graph -->
<meta property="og:image" content="https://yoursite.com/og.png" />
<meta property="og:image:width" content="1200" />
<meta property="og:image:height" content="630" />
<meta property="og:image:type" content="image/png" />
<meta property="og:title" content="..." />
<meta property="og:description" content="..." />

<!-- Twitter -->
<meta name="twitter:card" content="summary_large_image" />
<meta name="twitter:image" content="https://yoursite.com/og.png" />
<meta name="twitter:title" content="..." />
<meta name="twitter:description" content="..." />
```

## Notes

- Fonts: the rendering headless browser must have access to the fonts declared in `{{type.*}}`. Either install them system-wide before rendering, or inline a `@font-face` block pointing to a hosted WOFF2 at the top of the template.
- `vibrant-playful.html.template` references `color.palette.1` and `color.palette.2`. If `palette` is `[]`, substitute `accent` / `accent_hover` during emit.
- `brutalist` template uppercases everything via CSS — if that hurts the specific title, remove `text-transform: uppercase` for the `.subtitle` selector.
- Generate a per-post OG image by re-running the render with different `{{og.title}}` / `{{og.subtitle}}`.
