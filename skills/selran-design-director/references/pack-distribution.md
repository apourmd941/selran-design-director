# Pack distribution — CLI, registry, signatures, lockfiles

## What this is

Phase 8 ships two pieces of infrastructure on top of the pack system documented in [`packs.md`](./packs.md): a CLI called `selran` and a JSON-indexed registry. Users install packs with `selran pack install <name>`; pack authors publish with `selran pack publish ./my-pack/`; CI pipelines pin exact versions with `selran-packs.lock.yaml`. The canonical Selran registry is hosted at `https://registry.selran.design/index.json`; self-hosted registries work by serving the same JSON schema from any URL. The old copy-the-folder install pattern (unzip into `~/.claude/skills/selran-design-director/packs/`) still works unchanged — the CLI is additive, not a replacement.

---

## The two install flows, side by side

**Old flow (still supported, still documented in `packs.md`):**

```bash
unzip fintech-0.2.0.zip -d ~/.claude/skills/selran-design-director/packs/
# For commercial packs, add the acknowledgment:
$EDITOR ~/.claude/skills/selran-design-director/.pack-licenses.yaml
```

The loader auto-discovers the folder on next load. This path is intentionally preserved for air-gapped machines, one-off trials, forks that never touch the network, and anyone who prefers manual control.

**New flow (v3.4+):**

```bash
selran pack install fintech
```

The CLI resolves the latest stable version from the registry, downloads the archive and its detached ed25519 signature, verifies both against a trusted public key, extracts to the install dir, and prints the license-gate reminder for commercial packs. One command replaces four manual steps.

**When to use which.** The old path is correct for air-gapped environments, untrusted networks, or one-off trials where installing a CLI is overkill. The new path is correct for team-shared installs, CI pipelines, reproducible environments, and anything that ships to production. Both paths write to the same install directory, so mixing them is safe — the CLI detects folders installed by hand.

---

## Installing the CLI

```bash
# From PyPI (recommended; Phase 15):
pip install selran-cli

# From source (alternative; useful when tracking main):
pip install -e /path/to/selran-design-director-repo/cli/
```

The PyPI package name is `selran-cli`; the installed binary is `selran`. Releases are published from `v*` git tags via GitHub Actions OIDC trusted publishing — no long-lived tokens live in the repo. See `RELEASE.md` for the release procedure and `.github/workflows/release.yml` for the workflow.

Requires Python 3.10 or later. There is no Node, Go, or native binary — the CLI is pure Python, deliberately. The `cli/` directory at the repo root contains the package source; see `cli/README.md` for the full command reference.

Verify the install:

```bash
selran --version
selran pack list
```

---

## Day-to-day user workflows

Each command below lists the concrete invocation, the expected shape of the output, and the underlying mechanism.

### `selran pack install <name>[@<version>]`

```bash
selran pack install fintech              # latest stable
selran pack install fintech@0.2.0        # exact version
selran pack install fintech@^0.2.0       # semver range
```

The CLI fetches `index.json` from the registry, resolves the version expression against the published pack entries, downloads `<pack>-<version>.tar.gz` and `<pack>-<version>.tar.gz.sig`, verifies the signature against the trusted public key referenced in the registry entry (`selran-2026-01` for Selran's canonical packs in 2026), checks the sha256 matches the registry record, extracts into `~/.claude/skills/selran-design-director/packs/<name>/`, and prints a post-install summary. For commercial packs, the output includes the license-gate reminder (env var or `.pack-licenses.yaml`).

### `selran pack list`

Two columns: what's installed locally, what's available on the registry. Installed packs that are also on the registry are marked with their version; installed packs that aren't registered (manually-installed or private) are marked `(local)`.

### `selran pack search <query>`

Ranked full-text search over pack `name`, `display_name`, `description`, and `tags`. Returns up to 20 results with version, license, and one-line description. Ranking weights exact name matches highest, then display name, then tag hits, then description body. Pair with `--license open` or `--license commercial` to filter.

### `selran pack verify [<name>] [--all]`

Re-checks signatures and checksums for one pack, or for every installed pack with `--all`. Verification runs against the current lockfile if present, otherwise against the live registry. Use this after pulling a repo that ships `selran-packs.lock.yaml`, or as a CI gate before shipping.

### `selran pack remove <name>`

Deletes the pack folder from the install directory. If a lockfile is present, the corresponding entry is removed and the lockfile is rewritten.

### `selran pack lock`

Generates `selran-packs.lock.yaml` from the current install state. Every installed pack appears with its exact version, archive sha256, signature sha256, and key ID.

### `selran pack install --from-lock`

Reads `selran-packs.lock.yaml` and installs exactly what it pins. Any drift — a newer version on the registry, a missing signature, a rotated key — fails the command. This is the CI-safe, reproducible-install entry point.

---

## The registry

The registry is a single JSON document: `index.json`. It can be served as a static file behind a CDN (what Selran does) or from a dynamic API that returns the same schema. The document lists every published pack, with per-version entries containing the download URL, archive sha256, signature URL, signature sha256, signing key ID, and metadata pulled from the pack's `pack.yaml`.

Top-level `schema_version: "1.0"`. Breaking changes to the schema bump the top-level version; the CLI refuses to read registries whose schema version it doesn't understand and prints the upgrade path.

**Canonical hosting.** Selran's registry is a static `index.json` behind a CDN at `https://registry.selran.design/index.json`. No server-side logic, no rate limiting beyond CDN defaults, no account system on the read path.

**Override the registry URL** per invocation with `--registry <url>` or globally with the `SELRAN_REGISTRY_URL` environment variable. The CLI accepts any URL that returns a conforming `index.json`.

**What an entry contains.** Each pack entry lists one or more versions. Each version carries: the version string, archive URL, archive sha256, detached signature URL, signature sha256, signing key ID, license string, price (informational), `requires_core` range, `requires_packs` array, publish date, yanked flag, deprecated flag, and the one-line description. The 25 packs on Selran's canonical registry today — five Wave-1 industries, seven Wave-1 archetypes, five Wave-2 industries, five Wave-2 archetypes, and three free/MIT seed packs — are all indexed in this shape.

**Schemas.** The canonical JSON Schema documents live in this repo at `registry/schema/registry.schema.json` (the top-level index) and `registry/schema/pack-entry.schema.json` (a single pack's entry). `selran pack validate` and `selran pack publish` both validate against these.

**Self-hosting.** Point `SELRAN_REGISTRY_URL` at your own `index.json`; the CLI resolves everything from there. Full walkthrough at `registry/self-hosting.md`.

---

## Signatures — what, why, how

Every published pack archive ships with a detached ed25519 signature. When the CLI installs, it downloads both files, verifies the signature against a trusted public key, and aborts on mismatch. The trusted key is referenced by ID from the registry entry; the actual public-key material lives in the user-local trusted-keys directory at `~/.claude/skills/selran-design-director/trusted-keys/`.

**Key rotation.** Signing keys are rotated annually. The canonical Selran key for 2026 is `selran-2026-01`; the next rotation will publish `selran-2027-01` alongside a grace period during which both keys are trusted. Old signatures remain verifiable because trusted keys are kept in the directory, not overwritten.

**Trust-on-first-use (TOFU).** When the CLI encounters a new registry key ID — either on first run against Selran's registry, or on first use of a self-hosted registry — it prompts the user with the key fingerprint and asks for confirmation. Accepted keys are pinned to the trusted-keys directory; subsequent installs under that key ID verify silently. Unexpected key changes fail loudly.

**Why ed25519 and not Sigstore / transparency log.** Ed25519 is offline-verifiable (the CLI never talks to a log service to confirm a signature), has zero dependencies beyond the `cryptography` Python library, and fits the Selran-single-publisher model for 2026. A transparency-log upgrade is on the roadmap if the ecosystem grows to multiple publishers who need cross-auditability.

**Why detached and not embedded.** Archives stay byte-identical regardless of who signs them. A mirror, a private fork, or a vendor redistributing a pack can add its own `.sig` file without re-archiving — the `.tar.gz` is unchanged, so checksums stay consistent across mirrors.

---

## Lockfiles — reproducible installs

`selran-packs.lock.yaml` is a project-local file, checked into version control. It pins every installed pack to an exact version, archive sha256, signature sha256, and signing key ID.

```yaml
# Excerpt — full schema at registry/lockfile-spec.md
schema_version: "1.0"
packs:
  fintech:
    version: "0.2.0"
    archive_sha256: "..."
    signature_sha256: "..."
    key_id: "selran-2026-01"
    registry: "https://registry.selran.design/index.json"
```

Run `selran pack install --from-lock` in CI. The command fails loudly on any drift — version mismatch, missing signature, rotated key not yet trusted. No best-effort fallbacks.

**Why YAML not JSON.** The rest of the Selran ecosystem is YAML-native: `design-system.md` is YAML inside markdown, `pack.yaml` is YAML, the schema files are the only JSON touchpoints. Consistency over microbenchmarks.

**Relation to `design-system.md`.** The lockfile is orthogonal to the design system. `design-system.md` pins tokens (colors, type, spacing); `selran-packs.lock.yaml` pins pack versions. Together they reproduce a complete design environment — same tokens, same component libraries, same references, bit-for-bit.

---

## Version ranges and resolution

The CLI accepts semver-style version expressions:

- Exact: `0.2.0`
- Caret: `^0.2.0` — pre-1.0 caret means same-minor (`>=0.2.0,<0.3.0`), respecting the convention that 0.x minor bumps are allowed to break. After 1.0.0, caret reverts to same-major (`>=1.0.0,<2.0.0`).
- Tilde: `~0.2.0` — same-patch (`>=0.2.0,<0.3.0`).
- Range: `>=0.1.0,<0.3.0` — arbitrary conjunction.
- `latest` — resolves to the highest non-yanked stable version.

Resolution picks the latest version that satisfies the expression. Ties break by release date, then by version string. Yanked versions are excluded from resolution unless explicitly pinned to an exact match (so lockfiles stay installable even after a yank).

Pre-release versions (`0.2.0-rc.1`) are excluded unless explicitly requested.

---

## Publishing — for pack authors

The happy path end-to-end:

1. **Scaffold.** `selran pack init my-awesome-pack --base-direction technical-minimal` creates a new pack folder from the template, preconfigured to layer on top of a chosen base direction.
2. **Fill it in.** Edit `pack.yaml`, drop content into `provides/`, add `pack-overrides/` for per-direction token overlays, write a real `LICENSE` and `CHANGELOG.md` and `README.md`.
3. **Validate.** `selran pack validate ./my-awesome-pack/` runs the full pack audit: manifest schema, path existence, anti-pattern checks (no Inter/Poppins/Roboto as default display, no emoji-as-UI-icons, Corporate Memphis detection on illustrations), discipline checks (class scoping, `focus-visible` states present, `prefers-reduced-motion` overrides on motion snippets). Fix every error and warning before continuing.
4. **Publish.**

```bash
selran pack publish ./my-awesome-pack/ --key ~/.selran/private.pem --dry-run
```

With `--dry-run`, the CLI tars the pack, signs the archive with the provided ed25519 private key, and leaves `my-awesome-pack-1.0.0.tar.gz` and `my-awesome-pack-1.0.0.tar.gz.sig` in `./dist/` for inspection. Drop `--dry-run` to upload to the configured registry.

5. **Register.** Open a pull request against the canonical `registry/index.json` adding your pack entry. Full PR template and review process at `registry/publishing.md`.

**Alternatively — self-host.** Host the archive + signature on your own CDN, publish your own `index.json`, and either ask users to set `SELRAN_REGISTRY_URL` to your URL, or request a listing on Selran's canonical registry with `signing_key_id` pointing at your key. Self-hosted publishers own their signing key end-to-end; the canonical registry just advertises their existence.

**Versioning rules.** Pre-1.0 is 0.x.y. Minor bumps for additions (new components, new references), patch bumps for fixes (typo, contrast nudge, missing focus-visible). Breaking changes in 0.x.y are allowed — document them in `CHANGELOG.md` and expect consumers to pin with tilde rather than caret if they can't tolerate churn.

---

## Webhooks

Self-hosted dynamic registries may send HTTP webhooks for pack events. The event catalog:

- `pack.version.published` — a new version entered the registry
- `pack.version.yanked` — a version was pulled from resolution
- `pack.version.deprecated` — a version is still installable but discouraged
- `pack.key.rotated` — a signing key was rotated; trusted-keys update needed

Each webhook is an HTTP POST to the consumer's configured URL with an HMAC-SHA256 signature in the `X-Selran-Signature` header, derived from a shared secret. The payload schema is documented at `registry/webhook-spec.md`.

**Use cases.** Auto-PR version bumps in downstream projects (receive `pack.version.published`, open a PR that runs `selran pack install <name>@<new-version>` + `selran pack lock`). Dashboard alerts. Slack or Discord integrations. Security monitoring on `pack.key.rotated`.

**Selran's canonical registry does NOT push webhooks.** It's a static file; there's no server-side event emission. Webhooks are a self-hosted registry feature. Consumers who need push notifications against Selran's packs should poll `index.json` on a schedule or rely on the `pack.version.published` event from a self-hosted mirror.

---

## Privacy and telemetry

The CLI sends zero telemetry by default. No usage analytics, no install pings, no version metrics, no error reports. `selran pack install <name>` makes exactly two outbound HTTPS requests:

1. `GET <registry>/index.json`
2. `GET <archive_url>` + `GET <signature_url>` (counted as one logical fetch, two TCP requests)

That is the entire network footprint. No background daemon, no heartbeat, no call-home. Phase 10 (pack telemetry for authors who want install counts) is explicitly opt-in, separately-governed, and documented on its own roadmap line when it ships.

---

## Security posture

**In-scope for Phase 8:**

- Ed25519 signature verification on every install
- TLS required for all registry and download URLs (HTTP redirects are refused)
- Trusted-keys pinning under `~/.claude/skills/selran-design-director/trusted-keys/`
- Lockfile integrity checks on `--from-lock` installs
- Manifest schema validation and discipline audit during `publish`
- Sha256 checksum verification on both the archive and the detached signature

**Out-of-scope for Phase 8 (tracked, not shipped):**

- Transparency-log-backed signing (Sigstore-style)
- Supply-chain attestation (SLSA levels)
- Reproducible builds of the archive itself
- Sandboxed pack execution — packs are static files, no code runs at install. If a future phase adds runnable hooks, sandboxing becomes in-scope.

**Reporting security issues.** Email `security@selran.design` and file a GitHub security advisory on the repo. Do not open public issues for suspected vulnerabilities.

---

## Migration — from copy-the-folder to CLI

Teams already running with manually-installed packs can migrate without reinstalling:

1. **Existing acknowledgments preserved.** `~/.claude/skills/selran-design-director/.pack-licenses.yaml` is read by the CLI; commercial-pack licensing carries over.
2. **Existing installs detected.** Packs already in `~/.claude/skills/selran-design-director/packs/` show up in `selran pack list --installed` without re-install. Folders that match a registry entry by name and version are annotated as registered; orphan folders (private packs, hand-rolled test packs) are annotated `(local)`.
3. **Generate a lockfile from the current state.** `selran pack lock` against a pre-CLI install writes a `selran-packs.lock.yaml` reflecting whatever is on disk. Any pack whose exact version isn't on the registry is flagged for manual review.
4. **Retroactive verification.** `selran pack verify --all` re-checks signatures against the registry for every installed pack that has a matching published version. Use this as a one-time audit before committing the generated lockfile.

---

## Roadmap — what's explicitly NOT in Phase 8

- **Phase 9 — pack-author tooling.** Richer `init`, `validate`, and `build` workflows; discipline linters with auto-fix; preview renders from inside `publish`.
- **Phase 10 — pack telemetry (opt-in).** Install counts and version adoption for pack authors who enable it; separately governed, off by default.
- **Phase 11 — pack ecosystem events.** Showcase site, awards, curated lists.
- **Transparency-log-backed signing** — no phase number yet; tracked if the ecosystem grows beyond single-publisher.
- **Pack composition and dependency resolution beyond basic `requires_packs`** — no phase number yet. Current treatment is "warn if a required pack is absent"; richer resolution (version constraints between packs, conflict detection) is future work.

---

## Reference links

- [`cli/README.md`](../../cli/README.md) — CLI command reference and flag documentation
- [`registry/README.md`](../../registry/README.md) — registry architecture and index schema
- [`registry/publishing.md`](../../registry/publishing.md) — author's guide to publishing
- [`registry/self-hosting.md`](../../registry/self-hosting.md) — running your own registry
- [`registry/lockfile-spec.md`](../../registry/lockfile-spec.md) — `selran-packs.lock.yaml` format
- [`registry/webhook-spec.md`](../../registry/webhook-spec.md) — webhook payload schema
- [`references/packs.md`](./packs.md) — pack manifest and layering spec (core reference)
