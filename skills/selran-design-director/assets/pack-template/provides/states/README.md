# provides/states/

Screen-state variants — empty, loading, error, success. One of the four for every major surface the pack's components live on. Each state is a first-class design artifact, not a polish pass at the end.

One subfolder per surface, one HTML file per state per direction.

## Typical layout

```
provides/states/
├── dashboard/
│   ├── empty.{{BASE_DIRECTION}}.html
│   ├── loading.{{BASE_DIRECTION}}.html
│   ├── error.{{BASE_DIRECTION}}.html
│   └── success.{{BASE_DIRECTION}}.html
└── inbox/
    ├── empty.{{BASE_DIRECTION}}.html
    └── ...
```

## Register in pack.yaml

```yaml
provides:
  states:
    - "provides/states/dashboard/"
    - "provides/states/inbox/"
```

## Why pack states exist

Pack-specific states are the right place to express domain-authentic empty copy ("No transactions this month" vs. the generic "Nothing here yet"), domain-authentic error messages (compliance-aware language for regulated products), and domain-authentic success moments. Generic state primitives live in the core; domain voice lives here.

This directory is optional — delete it if your pack doesn't ship state variants.

See `../../../references/screen-states.md` in the core for the four-state doctrine and the empty-state copy patterns.
