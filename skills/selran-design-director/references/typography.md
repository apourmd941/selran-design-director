# Typography

Pair by contrast. Enforce hierarchy. Don't default to Inter.

## The pairing rule

Most good designs use exactly two font families. Three only if one of them is mono used sparingly (for code, labels, or numbers). More than three creates chaos.

Pair for **contrast**, not similarity:
- Serif display + sans body (classic editorial pairing)
- Geometric sans display + humanist sans body (technical-minimal)
- Characterful sans + the same sans at smaller sizes (works if the font has enough personality)
- Mono display + sans body (developer tool aesthetic)
- Script/display + serif body (luxury or editorial niche)

## Curated pairings that work

### For editorial / refined
- **Fraunces** (display, variable) + a modern sans like **General Sans** or **DM Sans**
- **Instrument Serif** (display, italic-heavy) + **Instrument Sans** (body) — built as a pair
- **EB Garamond** (display) + **Work Sans** (body)
- **Playfair Display** + **Source Sans Pro** — classic, slightly tired but still solid
- **Fraunces alone** — a single variable font can do it all if you use weight and optical sizes deliberately

### For technical / clean
- **Geist Sans** + **Geist Mono** — Vercel's system, cohesive, free
- **General Sans** (body) + **JetBrains Mono** (accents)
- **Satoshi** — one font, multiple weights, works alone
- **DM Sans** + **DM Mono** — designed together
- **IBM Plex Sans** + **IBM Plex Mono** — another designed-together pair, slightly more corporate

### For bold / distinctive
- **PP Editorial New** (paid, display) + **General Sans** (body) — stunning when licensed
- **Fraunces** at weight 900 (display) + **Hanken Grotesk** (body) — free alternative
- **Migra** or **Druk** (paid) + any Inter-alternative for body
- **Domaine Display** (paid) + **Söhne** (paid)

### For warm / approachable
- **Fraunces** (warm serif) + **Commissioner** (humanist sans)
- **Crimson Pro** + **Work Sans**
- **Lora** + **Nunito Sans**
- **Caveat** (handwritten, use *very* sparingly for single phrases) + any of the above serifs

### For dark-premium
- **Canela** or **Tiempos Headline** (paid) + **Söhne** (paid)
- Free alternative: **Fraunces** (high contrast, weight 500) + **General Sans Medium**
- **PP Editorial New** (paid) + any Inter-alternative
- **Playfair Display** + **Manrope** — usable even if slightly safe

### For brutalist / raw
- **Times New Roman** (as a statement) + **Courier New**
- **Space Mono** for everything
- **JetBrains Mono** + **IBM Plex Sans**
- System fonts (`font-family: -apple-system, BlinkMacSystemFont, system-ui`) — the anti-aesthetic

## Fonts to avoid (and why)

| Font | Why |
|---|---|
| **Inter** | Signals "I used the default." Too ubiquitous to feel intentional. |
| **Roboto** | Android default. Characterless. |
| **Arial / Helvetica** | As the *only* font — reads as unconsidered. Fine as a specific statement. |
| **Space Grotesk** | Over-rotated in AI landing pages. Was fresh in 2021, now tired. |
| **Poppins** | The "startup" font. Rounded and friendly but characterless. |
| **Montserrat** as display | Exhausted. Fine at small sizes if really needed. |
| **Lato** | Capable but bland. Usually a sign of not thinking about it. |
| **Comic Sans, Papyrus** | Yes, obviously. |

## Practical type scales

Pick one based on density and aesthetic:

### Compact (dense UIs, dashboards, developer tools)
```
xs:   12px / 16px line-height
sm:   14px / 20px
base: 16px / 24px
lg:   18px / 28px
xl:   24px / 32px
2xl:  32px / 40px
3xl:  48px / 56px
```

### Standard (most applications and sites)
```
xs:   13px / 20px
sm:   15px / 24px
base: 17px / 28px
lg:   20px / 32px
xl:   28px / 36px
2xl:  40px / 48px
3xl:  56px / 64px
```

### Editorial (long-form content)
```
xs:   14px / 22px
sm:   16px / 26px
base: 18px / 30px   ← generous body
lg:   22px / 34px
xl:   32px / 42px
2xl:  48px / 56px
3xl:  72px / 80px   ← display-sized
```

### Bold / display-driven
```
Body/UI: same as Standard
Display: 80px / 128px / 200px — used as layout elements
```

## Weight discipline

Pick 2-3 weights per font and stick to them:
- **Standard:** 400 body, 500 emphasis, 600 headings
- **Editorial:** 400 body, 500 emphasis, 700 headings or display at the display weight the font is designed for
- **Technical-minimal:** 400 body, 500 emphasis, 500–600 headings (keep it restrained)
- **Bold-distinctive:** 400 body, 800–900 display

Avoid using every weight in the family. It visually fragments the page.

## Line-height

- Body prose: 1.5–1.7 (longer copy = more line-height)
- UI / dense text: 1.3–1.5
- Display type: 1.0–1.15 (tighter)

## Letter-spacing

- Display type (large headings): slightly negative (-0.01em to -0.03em) for tightness
- Body copy: 0 (default)
- Small caps / uppercase labels: wider (+0.05em to +0.15em)

## Special cases

### Numbers
If your design has data (dashboards, financial figures, tables), use **tabular numerals**:
```css
font-variant-numeric: tabular-nums;
```
Do this for the whole number-heavy section, not per-element. Numbers that don't line up look unprofessional.

### Uppercase labels (small caps)
When using all-caps for labels or small markers:
```css
text-transform: uppercase;
letter-spacing: 0.08em;
font-size: 0.75em;
font-weight: 500;
```
Uppercase without letter-spacing looks cramped.

### Italics
Real italics (the font family's designed italic cut) > `font-style: italic` on fonts without one (which fakes it).

## Google Fonts shortcuts

For each direction, here's a one-liner that loads two fonts:

**Editorial:**
```html
<link href="https://fonts.googleapis.com/css2?family=Fraunces:wght@400;500;700;900&family=Work+Sans:wght@400;500;600&display=swap" rel="stylesheet">
```

**Technical-minimal:**
```html
<link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;600&family=JetBrains+Mono:wght@400;500&display=swap" rel="stylesheet">
```

**Warm-approachable:**
```html
<link href="https://fonts.googleapis.com/css2?family=Fraunces:wght@400;500;700&family=Commissioner:wght@400;500;600&display=swap" rel="stylesheet">
```

**Bold-distinctive (free alternative):**
```html
<link href="https://fonts.googleapis.com/css2?family=Fraunces:wght@900&family=Outfit:wght@400;500;700&display=swap" rel="stylesheet">
```

**Dark-premium (free alternative):**
```html
<link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@500;700&family=Manrope:wght@400;500;600&display=swap" rel="stylesheet">
```

**Brutalist / raw:**
```html
<link href="https://fonts.googleapis.com/css2?family=Space+Mono:wght@400;700&family=IBM+Plex+Sans:wght@400;500&display=swap" rel="stylesheet">
```
