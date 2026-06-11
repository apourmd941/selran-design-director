# provides/wizards/

Wizard and multi-step flow primitives — step-indicator, review-confirm, tooltip-tour, progressive forms, KYC flows, onboarding funnels. Anything that structures a user moving through an ordered sequence of decisions.

One subfolder per wizard, one HTML file per step per direction.

## Typical layout

```
provides/wizards/
├── kyc-flow/
│   ├── step-1.{{BASE_DIRECTION}}.html
│   ├── step-2.{{BASE_DIRECTION}}.html
│   ├── step-3.{{BASE_DIRECTION}}.html
│   └── review.{{BASE_DIRECTION}}.html
└── onboarding/
    └── ...
```

## Register in pack.yaml

```yaml
provides:
  wizards:
    - "provides/wizards/kyc-flow/"
    - "provides/wizards/onboarding/"
```

## Why pack wizards exist

Pack wizards are where domain constraints live: a fintech KYC wizard encodes regulatory sequencing that a generic progressive-form primitive can't assume. Ship the sequencing as part of the pack, not as a client's problem. Steps must be standalone — each step is its own copy-pasteable artifact, not a fragment that depends on siblings at render time.

This directory is optional — delete it if your pack doesn't ship wizards.

See `../../../references/wizard-flows.md` in the core for the wizard-flow spec, step-indicator expectations, and the review-confirm step pattern.
