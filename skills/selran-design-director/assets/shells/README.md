# App Shells

Seven app-shell primitives per direction. These are the structural elements that decide how a user moves through an app — distinct from the landing-page atoms in `assets/components/`.

## Coverage

All seven directions ship all seven shells:

| Shell | All directions |
|---|---|
| `sidebar.html` | ✓ |
| `topbar.html` | ✓ |
| `tab-bar.html` | ✓ |
| `bottom-nav.html` | ✓ |
| `command-palette.html` | ✓ |
| `breadcrumbs.html` | ✓ |
| `split-view.html` | ✓ |

Total: 7 × 7 = 49 shell snippets.

## Host contract

Same as `assets/components/` — snippets assume these CSS vars on `:root`:

- `--accent`, `--fg`, `--fg-muted`, `--bg`, `--bg-2`, `--bg-3`, `--border`
- `.mono` class for small-caps labels
- Editorial + bold-distinctive additionally use `--ff-display` (serif)
- Vibrant-playful additionally uses `--p1`..`--p5` (palette hues)

## Class prefix convention

Per direction, with per-component sub-prefixes so all seven shells + all six states + all four wizards can coexist on one page:

| Direction | Prefix |
|---|---|
| technical-minimal | `.c-tm-*` |
| editorial | `.c-ed-*` |
| dark-premium | `.c-dp-*` |
| warm-approachable | `.c-wa-*` |
| vibrant-playful | `.c-vp-*` |
| brutalist | `.c-br-*` |
| bold-distinctive | `.c-bd-*` |

## See also

- `references/app-shell.md` — full spec, when to use which primitive, composition rules
- `references/composition-rubric.md` — decision tree for card vs. list row vs. table row
