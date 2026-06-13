# Authoring your first plugin

A 30-minute walkthrough from `selran plugin init` to a signed, registry-publishable archive. For developers who want to extend the skill with a non-built-in build executor, export format, audit module, or aesthetic direction.

If you've never read [`services/plugins/plugin-spec.md`](../../services/plugins/plugin-spec.md), skim its first two sections before starting. The plugin contract is small but precise — the spec is your contract with the skill, and matching it exactly is what makes a plugin work.

---

## Pick an interface

A plugin implements exactly one of four interfaces. Pick the one that matches what you're trying to add:

| Interface | What it does | Reference plugin to read |
|---|---|---|
| `BuildExecutor` | Produces a non-built-in artifact — a Webflow site, a Notion page, a Wordpress theme, a Slack-app shell, a Framer export. Takes a `design-system.md` + brief + optional assets; returns one or more files. | [`plugins-reference/pdf-build/`](../../plugins-reference/pdf-build/) |
| `ExportFormat` | Produces a non-built-in token export — Material 3 tokens, JetBrains Compose Multiplatform, Penpot, Sketch Symbols, Adobe AS-3. Takes the token graph; returns one or more files. | [`plugins-reference/material3-tokens/`](../../plugins-reference/material3-tokens/) |
| `AuditModule` | Audits a built artifact — brand-compliance checks, internal-style-guide enforcement, motion-budget audits beyond the built-in. Takes an artifact path + design system; returns a list of `AuditFinding`s. | [`plugins-reference/brand-compliance/`](../../plugins-reference/brand-compliance/) |
| `DirectionStarter` | Adds an 8th, 9th, Nth aesthetic direction without modifying the core. Ships token starters (light + dark) + a thumbnail render template + a voice-guide skeleton + signature gestures. | [`plugins-reference/mono-typographic/`](../../plugins-reference/mono-typographic/) |

`DirectionStarter` is the highest-risk interface and ships with extra gating: the canonical registry will not surface a direction-starter plugin to users until a Selran maintainer flips `maintainer_reviewed: true` on its registry entry. Self-hosted registries can bypass this. Build a different interface first if you can — `BuildExecutor` and `ExportFormat` have a faster path to ship.

---

## Step 1 — Scaffold

Pick a kebab-case name for your plugin (`webflow-build`, `material3-tokens`, `brand-compliance`). Then:

```bash
selran plugin init webflow-build --interface build_executor --output ./
```

This produces a directory tree with:

```
webflow-build/
├── plugin.yaml              # the manifest
├── webflow_build/
│   └── __main__.py          # the entrypoint skeleton
├── requirements.txt         # python deps (or package.json if --runtime node)
├── README.md                # author + install instructions
└── LICENSE                  # MIT by default
```

The init command is interactive — it asks for your `display_name`, `version`, `author email`, `license`, `homepage`, the interface-specific block, and the `capabilities` block. Capability declarations are taken seriously: the runtime enforces them, so be honest about what your plugin actually needs.

---

## Step 2 — Implement the entrypoint

The plugin runs as a subprocess. It reads JSON from stdin, writes JSON to stdout, plain text to stderr (errors only), and exits with `0` on success / `1` on plugin error / `2` on contract violation.

For a `BuildExecutor`, the stdin shape is:

```json
{
  "design_system": { "...": "full design-system.md as JSON" },
  "brief": "Build a marketing site for a fintech product launching next quarter.",
  "assets": [
    { "id": "logo", "url": "https://workspace-assets.selran.design/abc/logo.svg" }
  ],
  "secrets": { "WEBFLOW_API_TOKEN": "..." },
  "workspace": { "id": "ws_abc123", "name": "Acme Inc" },
  "invocation_id": "inv_xyz789"
}
```

Your entrypoint reads that JSON, does whatever work it needs, and writes:

```json
{
  "status": "ok",
  "artifacts": [
    {
      "filename": "site.zip",
      "content_base64": "...",
      "content_type": "application/zip",
      "metadata": { "webflow_site_id": "...", "page_count": 12 }
    }
  ],
  "logs": "Plain-text log output, max 1MB.",
  "telemetry": { "duration_ms": 28400, "asset_count": 3 }
}
```

The reference [`plugins-reference/pdf-build/pdf_build/__main__.py`](../../plugins-reference/pdf-build/pdf_build/__main__.py) is a working end-to-end implementation. Copy its shape — read stdin once, do the work, write stdout, exit cleanly.

For the other three interfaces, the stdin / stdout shapes are documented in the per-interface specs:

- [`services/plugins/build-executor-spec.md`](../../services/plugins/build-executor-spec.md)
- [`services/plugins/export-format-spec.md`](../../services/plugins/export-format-spec.md)
- [`services/plugins/audit-module-spec.md`](../../services/plugins/audit-module-spec.md)
- [`services/plugins/direction-starter-spec.md`](../../services/plugins/direction-starter-spec.md)

---

## Step 3 — Validate

Before building, run the validator:

```bash
selran plugin validate --plugin-dir ./webflow-build
```

The validator checks: manifest parses against `registry/schema/plugin-manifest.schema.json` and the matching interface schema; entrypoint files declared in `entrypoint.files` actually exist; capability declarations are sound (egress allowlist is a list of valid hostnames; `subprocess.spawn: false` and the entrypoint command has no shell metacharacters; etc.); the `implements:` value matches the interface block present.

It returns an empty list (no errors / no warnings) on success. Fix every error before moving on. Warnings are non-fatal but worth reading.

---

## Step 4 — Test locally

Run the plugin against a fixture input:

```bash
echo '{"design_system": {...}, "brief": "Test", "assets": [], "secrets": {}, "workspace": {"id":"test","name":"Test"}, "invocation_id":"inv_test"}' | \
  selran plugin invoke ./webflow-build --input-file -
```

Or write a fixture file and pipe it in. The output JSON should parse, the artifacts should be valid (decode the base64; open the file), and the exit code should be `0`.

If your plugin needs secrets (declared in `capabilities.secrets.required`), provide them via the `secrets:` block in the input JSON. The CLI strips them from logs at the source — they're never written to the audit log or to the plugin's stdout/stderr.

---

## Step 5 — Build a signed archive

Once the plugin runs cleanly:

```bash
selran plugin build --plugin-dir ./webflow-build --sign --signing-key ~/.keys/my-plugin-key.pem
```

This produces:

- `webflow-build-0.1.0.tar.gz` — the deterministic archive (sorted entries, `mtime=0`, gzip with `mtime=0`). Two consecutive builds produce byte-identical archives.
- `webflow-build-0.1.0.tar.gz.sig` — the ed25519 signature.

Generate a signing keypair if you don't have one:

```bash
openssl genpkey -algorithm ED25519 -out ~/.keys/my-plugin-key.pem
openssl pkey -in ~/.keys/my-plugin-key.pem -pubout -out ~/.keys/my-plugin-key.pub
```

Keep the private key out of the repo. The public key is what you register against the canonical registry.

---

## Step 6 — Register your author key

Before publishing, your public key must be registered against the canonical registry. Two paths:

- **Proof-of-domain.** Add a DNS TXT record at `_selran-plugin-key.<your-domain>.com` containing the SHA-256 fingerprint of your public key. Email `plugins@selran.design` with the domain.
- **Proof-of-organization.** Manual review. Email `plugins@selran.design` with your organization name, your public key, the plugins you intend to publish, and a brief description.

Either path resolves in 2–5 business days for the canonical registry. Self-hosted registries have their own policies.

---

## Step 7 — Publish

```bash
selran plugin publish ./webflow-build-0.1.0.tar.gz ./webflow-build-0.1.0.tar.gz.sig
```

The registry verifies the signature against your registered key, runs the manifest through the schema validator, and (for `direction_starter` plugins only) flags the entry for maintainer review. Successful publish appears at `https://registry.selran.design/plugins/<your-plugin>/<version>.tar.gz` within a few minutes.

Publishing is idempotent on the SHA-256 of the tarball — re-publishing the same archive returns the existing entry rather than creating a duplicate.

---

## Step 8 — Install your plugin into a workspace

From a workspace admin's CLI:

```bash
selran auth login
selran workspace switch <workspace-slug>
selran plugin install webflow-build --accept-capabilities
```

The `--accept-capabilities` flag is required for any plugin with non-trivial capability declarations (network egress, secret access, etc.). Without it, the install fails with the capability declaration printed for the admin's review. This is policy: workspace admins consciously accept what each plugin can do.

---

## Common first-plugin pitfalls

**The plugin's stdin/stdout JSON shape doesn't match the contract.**
Run `selran plugin invoke` against your fixture and verify the output JSON conforms to the schema. The contract violation exit code (`2`) fires when the runtime detects shape mismatch, but it's better to catch it locally than at install time.

**Capability declarations are too loose.**
The temptation is to declare `egress_allowlist: ["*"]` and `secrets: required: ["EVERYTHING"]` to avoid runtime errors. Don't. Workspace admins read the capability block at install time and refuse to install plugins that overreach. Declare only what you actually need.

**Secrets leak into stdout or logs.**
Anything in stdin under `secrets:` is private. Don't echo them, don't write them to the artifacts' metadata, don't include them in error messages. The runtime scrubs known secret patterns from the audit log, but the easiest defense is "don't put them in the output in the first place."

**Resource limits are exceeded.**
The runtime caps CPU time, wall-clock time, memory, and output size at `4×` your declared estimates. If your plugin runs for 28 seconds and you declared `estimated_runtime_seconds: 5`, the runtime hard-kills it at 20 seconds. Declare estimates honestly — overestimating wastes a workspace's job-queue slot; underestimating gets your plugin killed.

**The plugin builds non-deterministically.**
Two consecutive `selran plugin build` invocations should produce byte-identical archives (same sha256). If they don't, you've leaked an mtime or a path-order somewhere — usually in your own packaging step, not the CLI's. Check your `requirements.txt` content order, your `__pycache__` files (the build excludes these), and any timestamped log files.

**The signing key is committed to the repo.**
The CLI prints a warning if it sees a private key path inside the plugin directory at build time, but the warning is best-effort. Keep your `~/.keys/` outside the plugin tree, and don't paste private keys into READMEs.

---

## What's next

- Read the per-interface spec for your interface and confirm your output matches every required field.
- Read [`services/plugins/sandbox-spec.md`](../../services/plugins/sandbox-spec.md) to understand what your plugin can and can't do at runtime.
- If you're shipping a `direction_starter`, read [`references/aesthetic-directions.md`](./aesthetic-directions.md) and [`references/direction-starters.md`](./direction-starters.md) carefully — the contract for direction shapes is the strictest in the system.
- Open a GitHub issue against the [Selran repo](https://github.com/apourmd941/selran-design-director) tagged `plugin-author-question` if anything is unclear. Plugin contract feedback in the first 90 days post-launch will shape `plugin_contract_version: "1.1"` (additive changes only — see the schema-freeze policy).

---

## Cross-references

- [`services/plugins/README.md`](../../services/plugins/README.md) — plugin contract overview + the four interfaces at a glance
- [`services/plugins/plugin-spec.md`](../../services/plugins/plugin-spec.md) — full manifest format + lifecycle + runtime contract
- [`services/plugins/sandbox-spec.md`](../../services/plugins/sandbox-spec.md) — sandboxing posture per platform
- [`services/plugins/signing-spec.md`](../../services/plugins/signing-spec.md) — ed25519 chain + author key registration + revocation
- [`plugins-reference/`](../../plugins-reference/) — four reference plugins, one per interface, with working entrypoints
- [`registry/schema/plugin-manifest.schema.json`](../../registry/schema/plugin-manifest.schema.json) — the manifest's JSON Schema
