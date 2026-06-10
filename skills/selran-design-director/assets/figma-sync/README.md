# assets/figma-sync

This folder holds any supporting artifacts for the Figma bidirectional sync workflow — review diffs, cached variable-ID maps, and the `.env` pattern notes below. The actual sync logic runs at call time through Claude plus the Figma REST API; there's no static script to ship here. Keep this folder small — durable spec lives in the reference file, not here.

## Full spec

See `references/figma-sync.md` for the full read/write/conflict workflow, mapping table, and anti-patterns. Start there for any sync-related work.

## Payload templates

The sync reuses the existing export templates in `assets/exports/`:

- `assets/exports/figma-variables.template.json` — Enterprise Variables REST API payload shape (used for both read-mode parsing and write-mode emission).
- `assets/exports/figma-tokens.template.json` — Tokens Studio plugin format, used as the fallback when the file isn't on a Figma Enterprise plan.

Don't duplicate these here. Link to them from any new doc.

## Environment setup

The Figma Personal Access Token is read from the environment as `FIGMA_TOKEN`. Never commit it.

Pattern:

```
# .env.example  (commit this)
FIGMA_TOKEN=

# .env          (gitignore this)
FIGMA_TOKEN=figd_abc123...
```

Add `.env` to `.gitignore` before the first run. Token scopes needed: `file_variables:read`, `file_variables:write`, `files:read`. Generate at Figma → Settings → Account → Personal access tokens.

The file key (extracted from the Figma URL) is cached in `design-system.md`'s `meta.figma_file_key` after the first sync — no need to re-paste the URL every run.
