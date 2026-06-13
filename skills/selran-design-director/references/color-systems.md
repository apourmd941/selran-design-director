# Color Systems

How to construct a palette, not pick one from a menu of 161. This is about the principles; the aesthetic-directions file gives you specific starting points.

## The budget rule

Every project gets a small color budget:
- **1 background color** (with up to 2 lifted variants for layered surfaces)
- **1 primary text color** (with up to 2 muted variants for secondary and tertiary text)
- **1 accent** (primary action, focus, key highlight)
- **Optionally: 1 secondary accent** (used sparingly — warnings, success states, or rhythm)
- **Semantic colors** (error/warning/success) — count these as utility, not palette

That's it. More colors than this is almost always noise. If you feel you need more, you probably need more *shades* of your existing palette, not more *colors*.

## Construction

### 1. Pick the background first
This sets the project's temperature more than anything else.
- Light warm: cream, paper, off-white with a warm tint (`#FAF7F2`, `#F5F1EA`)
- Light cool: off-white with a cool tint (`#FAFAFC`, `#F1F4F7`)
- Light neutral: paper with no detectable temperature (`#FAFAF9`)
- Dark warm: deep brown-black, charcoal (`#1A1612`, `#1F1B16`)
- Dark cool: near-black with a blue shift (`#0A0A0B`, `#0B0F19`)
- Dark neutral: pure charcoal (`#18181B`)

Avoid `#FFFFFF` and `#000000` — both read as unconsidered. Give them a subtle temperature.

### 2. Derive text from background
Text color should have *just enough* contrast. Not maximum contrast.
- On a warm background, use a warm dark (`#2D1B0F` not `#000`)
- On a cool background, use a cool dark (`#0C111C` not `#000`)
- Aim for 12:1 to 15:1 contrast ratio for body text. 21:1 (pure black on white) is actually too harsh for prose.

Secondary text: 40–60% opacity of primary, or a specific second value like `#71717A`.
Tertiary (captions, metadata): 40% opacity or lower — `#A1A1AA`.

### 3. Choose an accent with intent
Accent colors do jobs:
- **Draw attention to CTAs** — should stand out against the background
- **Indicate interactivity** — link color, focus outline
- **Carry brand personality** — if there's a brand, this is where it usually lives

Avoid safe accents (generic blue `#3B82F6`, generic green `#10B981`) unless the brief calls for them. Consider:
- *Muted jewel tones:* deep emerald `#047857`, muted sapphire `#1E40AF`, claret `#9F1239`, warm amber `#B45309`
- *Signal colors, if the aesthetic allows:* safety orange `#EA580C`, acid lime `#84CC16`, electric cyan `#06B6D4`
- *Earthy:* terracotta `#C2410C`, sage `#687F5B`, rust `#B04A1E`, ochre `#D4923B`
- *Luxe:* oxblood `#6B0F1A`, warm gold `#B8860B`, soft copper `#B4532A`

### 4. If you add a secondary accent
It should either:
- Be analogous (next to the primary on the color wheel) for harmony
- Be complementary (across the wheel) for rhythm and contrast — but use it very sparingly

Never pick a secondary accent randomly. It should have a job (warnings, data categorization, alternating section backgrounds).

## Gray families

Grays are not neutral — they have temperature. Pick one family per project:

**Cool grays (slate):**
```
50:  #F8FAFC   600: #475569
100: #F1F5F9   700: #334155
200: #E2E8F0   800: #1E293B
300: #CBD5E1   900: #0F172A
400: #94A3B8   950: #020617
500: #64748B
```

**Warm grays (stone):**
```
50:  #FAFAF9   600: #57534E
100: #F5F5F4   700: #44403C
200: #E7E5E4   800: #292524
300: #D6D3D1   900: #1C1917
400: #A8A29E   950: #0C0A09
500: #78716C
```

**Neutral (zinc):**
```
50:  #FAFAFA   600: #52525B
100: #F4F4F5   700: #3F3F46
200: #E4E4E7   800: #27272A
300: #D4D4D8   900: #18181B
400: #A1A1AA   950: #09090B
500: #71717A
```

Do not mix families. Pick one based on the project's temperature and use it for all neutrals.

## Dark mode is not "inverted"

Common mistake: taking a light palette and swapping bg/text. It almost never works. Dark mode needs:
- **Lifted blacks** — your darkest surface is `#0A0A0B`, not `#000`
- **Softer whites** — text is `#F4F4F5`, not `#FFFFFF`, to reduce eye strain
- **Muted accents** — the accent color that looks right on white is usually too saturated on black. Desaturate it 10–20%.
- **Layered surfaces** — dark mode uses elevation via slightly lighter surfaces (`#0A0A0B` → `#18181B` → `#27272A`), since shadows don't work as well on dark backgrounds.

## Gradients — when allowed

Gradients are fine when:
- Used once on a hero element (the eye knows what to look at)
- Between two colors from the same family (subtle warm-to-warm)
- As a texture, not a statement (very subtle background wash, 5% opacity)

Banned:
- Purple-to-pink on white, as noted.
- Rainbow gradients.
- Gradients on text that also has a gradient background (too much).
- Gradients on every card.

## Contrast & accessibility

Minimums:
- Body text: 4.5:1 (WCAG AA)
- Large text (≥18pt or ≥14pt bold): 3:1
- UI elements (focus indicators, borders conveying meaning): 3:1

For brand accents that don't pass: use them on larger type or as backgrounds behind white text, not as small text colors themselves.

## Color token naming

When you write the design-tokens JSON, use semantic names, not color names:
- `bg-primary`, `bg-secondary`, `bg-tertiary` (for layered surfaces)
- `fg-primary`, `fg-secondary`, `fg-muted`
- `accent`, `accent-hover`, `accent-muted`
- `border`, `border-subtle`, `border-strong`
- `danger`, `warning`, `success` (semantic)

This way, swapping palettes later changes values, not references.
