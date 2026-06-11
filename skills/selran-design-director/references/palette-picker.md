# Palette picker — runtime palette exploration

A floating panel that lets a reviewer swap the page's 5-slot secondary
palette live and step through three escalating pattern intensities. Two
flows: **Inside the direction** (chip-picked curated palettes + intensity)
and **Explore outside** (mood + intensity, surfaced as "exits direction").
Selection persists via `localStorage` under the key `palette-picker`.

## When to use

Drop this into any web artifact when the user wants to *see* palette
options at full fidelity rather than approve them from swatches. Trigger
keywords:

- "let me explore palettes"
- "palette picker"
- "try palettes live"
- "interactive palette"
- "swap the colors on the page"
- "preview palette options"

Useful for high-stakes landing pages, case-study layouts, or any moment
you've already locked type and spacing and only the color story is open.

## What the host page needs

The picker sets CSS custom properties on `:root` and a
`data-palette-pattern` attribute on `<body>`. Everything else is opt-in.

**1. Declare the slot variables on `:root`.** Any initial values are fine;
the panel overrides them on apply.

```css
:root {
  --sec-1: var(--accent);
  --sec-2: var(--accent);
  --sec-3: var(--accent);
  --sec-4: var(--accent);
  --sec-5: var(--accent);
}
```

**2. Mark elements with slot + opt-in classes.** Slot classes
(`.pp-slot-1` .. `.pp-slot-5`) determine which color an element picks up.
Opt-in classes determine *when* it picks it up:

- `.pp-label` — colored at pattern 1+, becomes a filled pill at pattern 3.
- `.pp-num` — colored at pattern 2+.
- `.pp-pill` — opt-in pill treatment at pattern 3 (without the label rules).
- `.sec-1` .. `.sec-5` — always colored, ignores pattern intensity.

**Before** (Meridian-shaped markup, statically emerald):

```html
<div class="stats">
  <div class="stat">
    <div class="mono">Avg. close time</div>
    <div class="num">2.1 days</div>
  </div>
  <!-- ... -->
</div>
```

**After** (same markup with picker hooks):

```html
<div class="stats">
  <div class="stat pp-slot-1">
    <div class="mono pp-label">Avg. close time</div>
    <div class="num pp-num">2.1 days</div>
  </div>
  <!-- ... -->
</div>
```

No CSS changes in the host — the picker snippet brings its own cascade.

**3. Paste the rendered snippet immediately before `</body>`.**

## How it's emitted

From a project root that contains `design-system.md` (with the standard
YAML frontmatter used elsewhere in this skill):

```bash
python ~/.claude/skills/selran-design-director/assets/palette-picker.emit.py .
```

The helper:

1. Parses the YAML frontmatter.
2. Runs HSL math on `color.accent` to compute five tonal variations
   (powers palette "A · Accent only") — drops them in as
   `accent.tonal.1` .. `accent.tonal.5`.
3. Substitutes `{{color.accent}}`, `{{color.accent_hover}}`,
   `{{color.bg_primary}}`, and the tonal slots into
   `palette-picker.template.html`.
4. Writes `palette-picker.html` to the project root (or a path passed
   as the second argument).

Palettes B (warm earth), C (cool steel), and all three outside moods
(Jewel / Vibrant / Bold) are hard-coded in the template; they travel
well against most accents. Edit the `INSIDE` and `MOODS` objects in the
emitted file to tune per-project.
