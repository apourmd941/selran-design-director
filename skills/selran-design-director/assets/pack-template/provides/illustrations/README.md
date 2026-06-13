# provides/illustrations/

SVG spot illustrations plus an `ai-prompts.md` file that describes the in-style prompts a user can send to a generator to extend the set.

## Typical layout

```
provides/illustrations/
└── {{BASE_DIRECTION}}/
    ├── hero.svg
    ├── empty-generic.svg
    ├── error-404.svg
    ├── success.svg
    └── ai-prompts.md
```

## Register in pack.yaml

```yaml
provides:
  illustrations:
    - "provides/illustrations/{{BASE_DIRECTION}}/"
```

## Discipline

- Every SVG consumes `--accent`, `--fg`, `--bg` CSS variables so it retints with the host page's tokens. Hardcoded fills are rejected by the validator.
- Scope any SVG-local classes with `.pack-{{PACK_NAME}}-illustration-*`.
- Strip generator metadata (Illustrator, Figma export cruft). Ship clean SVG.
- Corporate Memphis is banned across every direction, even warm-approachable — floating limbs, noodle-arms, disproportionate figures. The core rejects it on sight.

This directory is optional — delete it if your pack doesn't ship illustrations.

See `../../../references/illustration.md` in the core for the illustration philosophy, the SVG-token contract, and the "Common anti-patterns" section.
