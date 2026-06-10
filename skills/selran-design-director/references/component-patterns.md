# Component Patterns

Canonical UI components, tokenized so every direction renders them consistently with its aesthetic. Consult this when building any interface element — buttons, cards, tables, forms, nav, modals, badges.

The pattern is the structure; the **direction adjustments** below each pattern change the skin. Don't design a new button from scratch for every request — start with the pattern and apply the direction's adjustments.

---

## Starter components

Pre-built HTML snippets live in `assets/components/<direction>/` for the directions that have starters. They cover the canonical sections of a marketing page — nav, hero, stats, features, table, pricing, CTA, footer — so builds don't re-invent the same layout from zero each time.

**Contract (every snippet follows this):**

- Each file is a standalone fragment: one `<style>` block, then the markup.
- CSS is **scoped** with a per-component class prefix (`.c-nav-top`, `.c-hero-stat`, `.c-feat`, `.c-table`, etc.) so multiple snippets coexist on one page without collision.
- Styling uses CSS variables on the host: `--accent`, `--fg`, `--fg-muted`, `--bg`, `--bg-2`, `--bg-3`, `--border`. Editorial snippets additionally expect `--ff-display` (a serif family). Snippets do **not** redeclare these — the host page owns them.
- A `.mono` class on the host switches to JetBrains Mono (or similar) for small-caps labels. Snippets rely on it.
- `{Brand}` marks the one or two obvious rebranding spots (logo wordmark, eyebrow label).

**Coverage by direction:**

Marketing-page section snippets (happy-path):

| Direction | Components | Status |
|-----------|------------|--------|
| `technical-minimal` | nav-top, hero-stat-led, hero-split, stats-row, feature-grid-asymmetric, table-data, pricing-3tier, cta-closing, footer-minimal | **Full (9)** — lifted from the tested Meridian landing page |
| `warm-approachable` | nav-top, hero-stat-led, hero-split, stats-row, feature-grid-asymmetric, table-data, pricing-3tier, cta-closing, footer-minimal | **Full (9)** — lifestyle/wellness domain, pill-shaped chrome, soft warm shadows |
| `vibrant-playful` | nav-top, hero-stat-led, hero-split, stats-row, feature-grid-asymmetric, table-data, pricing-3tier, cta-closing, footer-minimal | **Full (9)** — creator-tool domain, palette-hued category coding via `data-cat` |
| `brutalist` | nav-top, hero-stat-led, hero-split, stats-row, feature-grid-asymmetric, table-data, pricing-3tier*, cta-closing, footer-minimal | **Full (9)** — indie label domain, mono type, hover-inversion. *`pricing-3tier` is substituted with a "manifesto block" (headline + numbered rules + CTAs) that preserves the structural footprint |
| `bold-distinctive` | nav-top, hero-stat-led, hero-split, stats-row, feature-grid-asymmetric, table-data, pricing-3tier, cta-closing, footer-minimal | **Full (9)** — editorial-magazine domain, oversized serif numerals as architecture, italic-for-accent |
| `editorial` | hero-split, feature-grid-asymmetric, table-data | **Partial (3)** — demonstrates pattern generality |
| `dark-premium` | hero-stat-led, feature-grid-asymmetric, cta-closing | **Partial (3)** — demonstrates pattern generality |

State components (the views AI-generated UI most often collapses on):

| Direction | empty-state | error-404 | loading-skeleton |
|-----------|:-----------:|:---------:|:----------------:|
| `technical-minimal` | ✓ | ✓ | ✓ |
| `editorial` | ✓ | ✓ | ✓ |
| `dark-premium` | ✓ | ✓ | ✓ |
| `warm-approachable` | ✓ | ✓ | ✓ |
| `vibrant-playful` | ✓ | ✓ | ✓ |
| `brutalist` | ✓ | ✓ | ✓ |
| `bold-distinctive` | ✓ | ✓ | ✓ |

State snippets share the same host contract as the section snippets. `loading-skeleton.html` in every direction includes a `prefers-reduced-motion: reduce` override that disables the shimmer animation. Each file has a unique `.c-<prefix>-<kind>` class scope (e.g. `.c-tm-empty`, `.c-br-404`, `.c-vp-skel`) so multiple state blocks can coexist with section snippets on the same page.

Form snippets (the three transactional views AI-generated UI routinely gets wrong — sign-in card, multi-step flow, validation-state demo):

| Direction | form-login | form-multi-step | form-validation |
|-----------|:----------:|:---------------:|:---------------:|
| `technical-minimal` | ✓ | ✓ | ✓ |
| `editorial` | ✓ | ✓ | ✓ |
| `dark-premium` | ✓ | ✓ | ✓ |
| `warm-approachable` | ✓ | ✓ | ✓ |
| `vibrant-playful` | ✓ | ✓ | ✓ |
| `brutalist` | ✓ | ✓ | ✓ |
| `bold-distinctive` | ✓ | ✓ | ✓ |

Form snippets follow the same host-var contract. Every `<input>` has a real `<label>` (never placeholder-only); invalid fields carry `aria-invalid="true"` plus `aria-describedby` pointing at the error text; inactive multi-step sections are `hidden` + `aria-hidden="true"`; OAuth icons are `aria-hidden="true"`. Focus uses `:focus-visible` in every direction, styled per the aesthetic (mono 2px ring, accent glow, block shadow, 4px solid outline, etc.) so keyboard and mouse users see the same affordances. Direction-specific substitutions: `brutalist/form-validation` refuses red/green (stripe + diagonal-pattern states only, `[✓]`/`[!]` ASCII markers); `editorial/form-multi-step` uses Roman numerals I/II/III as progress; `bold-distinctive/form-validation` uses 8px accent/fg left-rules instead of colored borders.

Other directions can adopt the same pattern when there's a need: create `assets/components/<direction>/` with the same file names and contract, write a short README, and snippets drop into any page that supplies the var contract.

**When to use a starter vs. build from scratch:** if the brief matches a direction that has a starter, reach for the snippet first and customize the copy + numbers. If the brief is off the main patterns (e.g. a dashboard, not a marketing page) or the direction has no starters, fall back to the base patterns below and apply the direction's adjustments.

---

## Buttons

### Base pattern

Three variants, always:

| Variant | Background | Text | Border | Use |
|---|---|---|---|---|
| **Primary** | `accent` | `bg_primary` | none | One per view — the main CTA |
| **Secondary** | transparent | `fg_primary` | `1px solid border_strong` | Support actions |
| **Ghost** | transparent | `fg_secondary` | none | Tertiary, destructive-confirm patterns |

States: default, hover, focus, active, disabled. All 5 must be explicit.

- Hover: primary → `accent_hover` bg; secondary → `bg_secondary` bg; ghost → `fg_primary` text
- Focus: 2px ring in `accent`, offset 2px
- Active: translate-y 1px OR scale 0.98 (pick one per design system, stay consistent)
- Disabled: 40% opacity, cursor not-allowed, no hover response

Padding: `var(--space-3) var(--space-4)` for default, `var(--space-2) var(--space-3)` for small, `var(--space-4) var(--space-6)` for large.

### Direction adjustments

- **editorial** — primary button uses `fg_primary` bg with `bg_primary` text (ink-on-paper), reserves `accent` for links. Radius: `radius.sm` (2–4px). No hover lift.
- **technical-minimal** — exactly the base pattern. Hover is subtle color shift. `radius.md` (8px). Focus ring 2px offset.
- **bold-distinctive** — primary button is oversized (18px body text, larger padding). `radius.sm` (0px). Hover does a color inversion, not a shade shift. Ghost variant usually skipped.
- **dark-premium** — primary has a 1px border in the accent hue for a defined edge. Subtle accent-glow on hover. `radius.md`. Micro-lift (translate-y -1px) on hover.
- **warm-approachable** — rounded (`radius.lg`, 16–20px). Hover lifts with a warm-tinted soft shadow, not a color shift. Friendly.
- **brutalist** — 2px solid black border on everything. `radius` = 0. Hover = color inversion (bg↔fg). No transitions (`duration: 0ms`).
- **vibrant-playful** — `radius.md` (12px). Medium weight (500). Hover does a small lift + slight color pulse. Palette colors can serve as secondary-button accents for categorical actions.

---

## Cards

### Base pattern

A card has a surface (`bg_secondary`), padding (`space-4` to `space-6`), and a boundary (border OR shadow, not both). Radius from `spacing.radius.md`.

```
┌─ card ──────────────────────────────┐
│ [optional header: label + metadata] │
│                                     │
│  content (title, body, action)      │
│                                     │
│ [optional footer: action buttons]   │
└─────────────────────────────────────┘
```

States: default, hover (when clickable), focus-within, selected.

- Hover on clickable card: raise the surface (`bg_tertiary` OR 1–2px translate-y, not both)
- Selected card: accent-colored 2px left border OR a ring in `accent` at 1px, 30% opacity

### Direction adjustments

- **editorial** — no card surface at all; sections divided by rule lines (`1px solid border`), not boxes. If a card is needed, cream-on-cream with 1px hairline.
- **technical-minimal** — hairline border (`1px solid border`), no shadow. `radius.md` (8px). Hover: `bg_tertiary` fill.
- **bold-distinctive** — no borders, huge padding, sections separated by whitespace and display-type weight. Cards rare.
- **dark-premium** — layered dark-on-dark (`bg_secondary` card on `bg_primary` page), 1px `border` in a slightly-lifted grey. Soft internal accent-glow on hover.
- **warm-approachable** — soft rounded (`radius.lg` 16–24px), warm-tinted soft shadow (`0 2px 8px rgba(warm-brown, 0.08)`). Paper-like.
- **brutalist** — 2px solid black border. `radius` = 0. No shadow ever. Hover: full inversion (bg→accent, fg→bg).
- **vibrant-playful** — soft `radius.md` (12px) or `radius.lg` (20px) for hero cards. Subtle warm shadow. A palette hue may serve as a 4px top border for category-coded cards.

---

## Tables

### Base pattern

Tabular data: header row (muted background or bottom border), body rows (alternating stripes or flat). Tabular numerals enabled (`font-variant-numeric: tabular-nums`). Cell padding: `space-2` vertical, `space-3` horizontal.

- Numeric columns right-aligned; text columns left-aligned
- First column may anchor-bold for row-identifier
- Row hover: `bg_secondary` fill
- Sortable columns indicate with a caret in `fg_muted`

### Direction adjustments

- **editorial** — no row stripes. Thin rule lines (`1px solid border`) between rows. Header row: small-caps label, `fg_secondary`, `border_strong` bottom rule.
- **technical-minimal** — no stripes; 1px hairline between rows. Header: uppercase mono label, 12px, `fg_muted`. Zebra optional but usually no.
- **bold-distinctive** — tables rare; when needed, treat numeric columns display-sized (display-font, huge).
- **dark-premium** — no stripes; subtle `border` between rows. Numeric highlights in `accent`.
- **warm-approachable** — soft alternating stripes (`bg_primary` / `bg_secondary`, very low contrast). `radius.sm` on the outer table container.
- **brutalist** — full HTML `<table>` aesthetic. 2px solid border on every cell. Alternate rows in `bg_secondary`. Headers bold uppercase.
- **vibrant-playful** — row categories tinted with 10% palette hue. Category column gets a 4px left rule in its palette color.

---

## Forms

### Base pattern

Input: `1px solid border_strong`, `radius.sm`, `bg_tertiary` fill OR transparent, `fg_primary` text, `fg_muted` placeholder. Label sits above, 14px, `fg_secondary`. Help/validation text below, 12px.

States:
- Default: standard border
- Hover: `border_strong` OR slight bg shift
- Focus: 2px ring in `accent`, `border` stays the same (the ring does the work)
- Error: `danger` border + `danger` help text
- Success: `success` border + `success` help text
- Disabled: `bg_secondary`, 60% opacity on text

Required fields: a single `*` in `accent` after the label. Skip "(required)" text — it clutters.

### Direction adjustments

- **editorial** — label above input, input with no fill and a bottom-only border (`1px solid border_strong`). Focus: bottom border becomes `accent` at 2px.
- **technical-minimal** — exactly the base pattern. Compact padding. Visible focus ring.
- **bold-distinctive** — larger inputs (18–20px body), more vertical space. Display-font labels above.
- **dark-premium** — subtle inset fill (`bg_secondary`), 1px top border lighter to simulate inset shadow. Focus: `accent` ring at 1px, accompanied by a soft glow.
- **warm-approachable** — rounded (`radius.md`, 12px). Warm-tinted focus ring. Labels in serif for friendliness.
- **brutalist** — 2px solid black border, `radius` = 0, `bg_primary` fill (white in light mode). Focus: 4px solid outline. No rings, no glows.
- **vibrant-playful** — `radius.md` (8–12px). Focus ring in `accent`. Category selectors can use palette hues for swatch-style picks.

---

## Navigation

### Base pattern

Top bar: logo on left, nav items center or right, action button far right. Height: 56–72px depending on density. Border-bottom `1px solid border` OR no border (use whitespace).

Side nav (for dashboards): 240–280px wide, `bg_secondary` background, nav items stacked with `space-2` vertical padding.

- Active item: `accent` left border (side nav) OR underline (top nav)
- Hover: `bg_secondary` fill
- Icon + label spacing: `space-2` gap

### Direction adjustments

- **editorial** — top nav only. Thin horizontal rule line underneath (`1px solid border`). Items in small-caps, tracked. Active: `accent` color text, no underline.
- **technical-minimal** — top nav or side nav. Minimal chrome, hairline border. Active item: 2px `accent` underline (top nav) or 2px left border (side nav). Keyboard shortcut hints (`⌘K`) visible.
- **bold-distinctive** — nav often oversized OR hidden behind a hamburger for single-page flows. When visible, large type.
- **dark-premium** — layered nav bg (`bg_secondary` on `bg_primary`). Active item: subtle accent glow, no hard underline.
- **warm-approachable** — rounded pill active states on nav items. Friendly, generous spacing.
- **brutalist** — 2px solid black border-bottom on top bar. Items all-caps, mono. Active: full inversion (bg becomes accent, text becomes bg).
- **vibrant-playful** — active item can use a palette hue as the accent underline for section-coded navigation.

---

## Modals / Sheets / Dialogs

### Base pattern

Modal: centered, max-width ~480–640px, `bg_primary` (or `bg_secondary` for layered dark), `radius.lg`. Backdrop: 60% opacity over page.

Structure: header (title + close), body, footer (action buttons). Primary action on the right; cancel on the left OR just close button at top-right.

Entry: fade-in backdrop (200ms) + scale-from-0.95 modal (250ms). Exit reverses.

- Focus traps inside modal; first focusable element gets focus on open
- Escape key closes; click backdrop closes (unless destructive unsaved state)

### Direction adjustments

- **editorial** — no modal chrome — dialog is a full-bleed panel with rule lines, typography-forward.
- **technical-minimal** — exactly the base pattern. 1px hairline border, no shadow.
- **bold-distinctive** — full-bleed or near-full-bleed dialog, display-font title.
- **dark-premium** — soft accent-glow on the modal surface, slow ease-out-expo entry (400ms).
- **warm-approachable** — rounded (`radius.lg` 16–24px), warm soft shadow, gentle scale-in with ease-in-out.
- **brutalist** — no fade, no scale. Modal just appears (`duration: 0`). 2px solid border. Backdrop 100% opacity black.
- **vibrant-playful** — `radius.lg` (20px), soft warm shadow, subtle bounce-easing on entry (ease-out-back).

---

## Tags / Pills / Badges

### Base pattern

Small rectangular or pill-shaped element, `radius.full` for pills, `radius.sm` for tags. Padding: `space-1` vertical, `space-2` horizontal. Text: 12px, `fg_secondary` or `accent`.

Types:
- **Neutral tag** — `bg_secondary` bg, `fg_secondary` text
- **Accent pill** — `accent` bg, `bg_primary` text (for highlighted status)
- **Categorical pill** — palette hue bg (when palette exists), `bg_primary` text

### Direction adjustments

- **editorial** — avoid pills; use italic or small-caps inline typography instead.
- **technical-minimal** — tight rectangular tags with `radius.sm`. Mono font for labels.
- **bold-distinctive** — big, confident pills used sparingly. Oversized text within.
- **dark-premium** — muted tags (`bg_secondary`, `fg_muted`). Accent pills with subtle glow.
- **warm-approachable** — rounded pills (`radius.full`). Warm-tinted. Friendly.
- **brutalist** — rectangular, 2px solid border, `radius` = 0. Uppercase mono text.
- **vibrant-playful** — palette-coded pills for categories. Each category always uses the same palette hue.

---

## Anti-patterns across all components

| Avoid | Why |
|---|---|
| Both a border AND a shadow on the same element | Belongs to one visual system or the other |
| Multiple radius values on one element (card with 4 different corners) | Unintentional and AI-slop |
| Button text smaller than body text | Reads as secondary; buttons are primary actions |
| Icons inside buttons with unbalanced spacing | Icon + label gap should equal internal padding / 2 |
| Placeholder text as the only label | Accessibility fail; also disappears on focus |
| Hover states that aren't also focus states | Keyboard users must see the same feedback |
| Skeuomorphic gradients on buttons/cards | Belongs to 2010, not 2026 |
| "Submit" as a button label | Use the action verb — "Save changes", "Sign in", "Publish" |

---

## When a pattern doesn't exist here

If the artifact needs a component not listed — a complex data visualization, a video player, a kanban board — start from the nearest pattern (card for tiles, modal for overlays, tag for chips) and apply the direction's adjustments. Don't invent new rules; inherit from the closest kin.

If the user explicitly asks for something outside the patterns (a marquee banner, a drawer, a hero carousel), use judgment but keep the token-driven discipline: no hardcoded colors, no ad-hoc radii, no fonts outside the type families declared in `design-system.md`.
