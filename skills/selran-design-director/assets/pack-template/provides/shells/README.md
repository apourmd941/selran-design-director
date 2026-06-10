# provides/shells/

App-shell primitives — sidebar, topbar, tab-bar, bottom-nav, command-palette, breadcrumbs, split-view. Anything that frames a product surface.

One subfolder per shell, one HTML file per direction your pack targets.

## Typical layout

```
provides/shells/
├── command-palette/
│   ├── {{BASE_DIRECTION}}.html
│   └── <other-direction>.html    # optional
└── breadcrumb-bar/
    └── {{BASE_DIRECTION}}.html
```

## Register in pack.yaml

```yaml
provides:
  shells:
    - "provides/shells/command-palette/"
    - "provides/shells/breadcrumb-bar/"
```

## Discipline

- Consume core shell tokens (`--shell-bg`, `--shell-border`, `--shell-fg`) where possible.
- Add pack-scoped tokens (`--pack-{{PACK_NAME}}-shell-*`) only where you genuinely need deviation.
- Scope every class with `.pack-{{PACK_NAME}}-<shell>-*` to prevent collision.
- Every interactive element ships `:focus-visible` and 44px min tap targets.

This directory is optional — delete it if your pack doesn't ship shells.

See `../../../references/app-shell.md` in the core for the shell primitive spec, responsive-breakpoint expectations, and the keyboard-navigation contract.
