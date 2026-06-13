# Boldness dial

Design Director's instinct is restraint — block the slop, commit to a system,
stay coherent. That's a strength, and it's also the risk: restraint without a
counterweight reads as *competent but tame*, which is the one thing the scrappy
"just be BOLD" prompts (Anthropic's `frontend-design`) beat it on. The boldness
dial is the counterweight: a single control that lets **any** direction play
louder — up to genuinely maximal — without abandoning the token system or the
accessibility floors.

## The three settings

A `boldness:` field in `design-system.md` (or a live "make it bolder / quieter"
request):

- **restrained** — quiet, generous, low-contrast. For executive, finance, legal,
  clinical, or dense-tool contexts where confidence reads as calm.
- **confident** (default) — the directions as specified in
  `aesthetic-directions.md`. A clear point of view, not shouting.
- **maximal** — the direction turned up: dramatic type contrast, the accent
  used assertively, atmospheric backgrounds, orchestrated motion, grid-breaking
  composition. This is where the "wow" lives.

## What the dial scales (and by how much)

One dial moves several axes together, relative to the direction's `confident`
baseline:

| Axis | restrained | confident | maximal |
|---|---|---|---|
| Type scale contrast (display ÷ body) | ~3–4× | direction default | 6–10×+; oversized display |
| Accent saturation & coverage | muted, sparing | default | saturated; used as a structural element, not a dab |
| Spatial drama | calm grid, generous space | direction default | asymmetry, overlap, diagonal flow, grid-breaking |
| Background treatment | flat / one tint | direction default | gradient mesh, noise, grain, layered transparency, pattern |
| Motion | minimal, fast | direction default | orchestrated page-load reveal, scroll-triggering, signature gesture foregrounded |
| Density / ornament | airy | direction default | controlled maximalism — dense, decorative, intentional |

## What the dial NEVER scales (the floors)

These are invariant at every setting — maximal is not permission to break them:

- **WCAG AA contrast** — a maximal palette still passes; saturation goes up,
  legibility does not go down (re-audit via `accessibility-check.md`).
- **Hit targets** ≥ 44px / 48dp, visible focus rings, keyboard paths.
- **`prefers-reduced-motion`** — orchestrated motion always has the reduced
  fallback; maximal motion is opt-out-able.
- **Anti-patterns** — no Inter/Roboto, no purple-on-white gradient, no
  emoji-as-icons *even at maximal*. **Maximalism ≠ slop.** Maximalism is
  *intentional* density and ornament; slop is *unconsidered defaults*. The dial
  pushes intentionality, never the defaults `anti-patterns.md` forbids.

## Per-direction ceilings (maximal means different things)

Each direction maxes out in its own register — don't push one toward another's:

- **technical-minimal** maxes via *type scale + a single bold accent + sharp
  motion*, not via color riot. Its maximal is "Linear on a confident day," not Vegas.
- **editorial** maxes via *typographic drama* (huge display serif, ragged
  asymmetry, drop caps), not saturated color.
- **bold-distinctive / vibrant-playful** have the highest ceilings — full
  maximalist color + motion is on-brand.
- **dark-premium** maxes via *contrast + glow + restraint-then-impact*, never clutter.
- **brutalist** maxes via *rawness* (system type pushed, hard edges, exposed grid).
- **warm-approachable** maxes via *warmth + generous scale*, staying human.

## Experimental reach (maximal + avant-garde gestures)

When the user wants to go past "bold" into the genuinely unusual — the territory
`frontend-design` reaches for (maximalist chaos, retro-futurist, art-deco /
geometric, organic/natural, industrial) — set `boldness: experimental`: it's
**maximal + permission to adopt an avant-garde signature gesture**, layered on a
base direction via `direction-fusion.md` mechanics. Still token-driven, still
a11y-audited, still anti-slop. This gives the "unforgettable" range without
forking the system — the experiment is a *setting on the brand*, not a different
tool. Use it for launch/campaign/portfolio surfaces; keep product surfaces
nearer `confident` (`surface-split.md` can carry both on one brand).

## How it's set

- **Up front:** the `boldness:` field, or inferred from the brief
  ("make it daring/unforgettable" → maximal; "restrained/executive" → restrained).
- **Live:** "make it louder/bolder/quieter" is a dial move — `live-iteration.md`
  translates it to the specific token changes in the table above, shows the
  diff, re-renders.
- **Coherence guard:** a maximal artifact still runs `self-critique.md`. The
  density-coherence category is exactly the check that maximalism stayed
  *controlled* (intentional) rather than tipping into noise. If it can't pass,
  it's not bold — it's broken.
