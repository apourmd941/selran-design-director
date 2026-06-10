# provides/motion/

Motion and transition snippets — signature micro-interactions the pack wants to enforce as a standard. One CSS file per direction your pack targets.

Consume the same easing and duration variables as core `assets/motion/` — `--motion-duration-base`, `--motion-easing-standard`, `--motion-easing-emphasized`. Extend the core's language; don't invent parallel vocabulary.

## The reduced-motion contract

Every `@keyframes` block MUST ship a `@media (prefers-reduced-motion: reduce)` branch that either disables the animation outright or reduces it to an `opacity` transition. The core's a11y audit fails the pack otherwise. This is non-negotiable.

## Typical layout

```
provides/motion/
├── {{BASE_DIRECTION}}.css
└── <other-direction>.css         # optional
```

## Register in pack.yaml

```yaml
provides:
  motion:
    - "provides/motion/{{BASE_DIRECTION}}.css"
```

## Why pack motion exists

Pack-authored motion is where signature gestures live: a luxury pack's slow parallax reveal, a playful-consumer pack's rubber-band overshoot on CTAs, a quiet-professional pack's 80ms fade on every state change. Each gesture is a small promise the pack makes on every surface it renders.

This directory is optional — delete it if your pack doesn't ship motion extensions.

See `../../../references/motion-and-interaction.md` in the core for the motion philosophy, easing discipline, and the full reduced-motion contract.
