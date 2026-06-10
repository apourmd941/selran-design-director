# Pack telemetry (v3.4+, opt-in)

## 1. Why telemetry exists

Pack authors have no way to learn what works. A commercial industry pack ships with seven components, three overlays, and a reference doc; six months later, the author cannot tell which components got pulled into real builds, which directions the overlay layered onto in practice, or which parts of the reference doc buyers opened. Without that signal, authors iterate blind.

Phase 10 closes that loop. Anonymous usage events are recorded locally when a user explicitly opts in, and uploaded only when the user runs an explicit command. The canonical aggregator strips individual identifiers and returns per-pack distributions to authors.

This is not analytics. This is not user tracking. The core skill makes zero network requests of its own. There is no Selran account system, no persistent identifier tied to a human, no behavioral profile. The data flow is one-way, from the ecosystem to authors, contingent on explicit per-install consent, and fully inspectable on disk before any of it leaves the machine.

## 2. The privacy posture

Default state is OFF. No events are recorded, no files are written, no state exists until the user opts in.

The opt-in prompt appears exactly once: the first time a **commercial** pack is installed. Installing a second commercial pack does not re-prompt. Answering "no" is remembered just like answering "yes" — the question does not come back.

MIT packs never prompt and never record events, even if the global telemetry state is opted-in. MIT packs are communal; author-facing telemetry is a commercial feedback mechanism. The three seed packs (`oss-project`, `personal-blog`, `resume-cv`) sit entirely outside the telemetry system; installing them is silent.

Events are written to `~/.selran/telemetry/events.jsonl` on the local machine. The file is plain JSONL, one event per line, append-only. Nothing leaves the machine without an explicit `selran telemetry upload` invocation. The CLI does not background-upload, does not batch-upload on install, does not heartbeat.

The `install-id` is a UUID4 generated on first opt-in and regenerated on `selran telemetry clear`. It is not derived from hostname, MAC address, username, or any other stable machine attribute — it is pure entropy from the system's CSPRNG. It is not linked to any Selran account system, because no Selran account system exists.

Never recorded under any circumstance:

- File paths, directory names, hostnames, usernames, IP addresses, home-directory references
- `design-system.md` contents — tokens, prose, project name, any of it
- User's email, git author name, any identifier outside the random `install_id`
- Any free-text the user types into a prompt or a brief
- Command arguments that could contain paths

If an event fails the redaction check at emission time, it is dropped. Section 5 covers the mechanics.

## 3. The opt-in prompt

The prompt text, shown once on first commercial pack install:

```
Selran can record anonymous usage data to help pack authors improve their packs.

What's recorded: pack installs, component activations, direction layering.
What's never recorded: file paths, hostnames, usernames, design-system contents, or anything you type.
Where it lives: ~/.selran/telemetry/events.jsonl on this machine.
When it's sent: only when you explicitly run `selran telemetry upload`.

Enable telemetry for commercial packs? [y/N]
```

Default answer is No. Pressing Enter, answering "n", answering "no", or anything other than an unambiguous "y" or "yes" records a No.

CI environments and non-interactive shells honor `SELRAN_TELEMETRY_ASSUME_NO=1`. Setting this environment variable suppresses the prompt and records a No without blocking the install. There is no equivalent `ASSUME_YES` — opting in has to be an intentional keystroke from a human.

The answer is written to `~/.selran/telemetry/consent.yaml` with a timestamp:

```yaml
schema_version: "1.0"
global:
  state: "opted_in"
  decided_at: "2026-04-23T14:12:03Z"
packs: {}
```

Per-pack overrides live under `packs:` and take precedence over the global state. Running `selran telemetry disable --pack fintech` records `packs.fintech.state: opted_out` and halts event emission for that pack while leaving the global state intact.

The prompt never re-appears for the same user unless they run `selran telemetry clear`, which removes `consent.yaml` along with everything else under `~/.selran/telemetry/`.

## 4. The event schema

Every recorded event conforms to this shape:

```json
{
  "event_id": "uuid4",
  "event_type": "pack.installed",
  "pack_name": "fintech",
  "pack_version": "0.2.0",
  "direction_base": "technical-minimal",
  "timestamp_utc": "2026-04-23T14:12:03.421Z",
  "install_id": "uuid4",
  "cli_version": "0.3.0",
  "metadata": {}
}
```

Field reference:

| Field | Type | Purpose |
|---|---|---|
| `event_id` | UUID4 string | Deduplication key if the same event is uploaded twice |
| `event_type` | enum | One of the six values listed below |
| `pack_name` | kebab-case string | The pack the event is about |
| `pack_version` | semver string | The exact installed version |
| `direction_base` | string or null | The base direction; null when the event does not reference a direction |
| `timestamp_utc` | ISO-8601 with Z | Event fire time in UTC |
| `install_id` | UUID4 string | Per-installation identifier; regenerated on `clear` |
| `cli_version` | semver string | The CLI version that emitted the event |
| `metadata` | object | Reserved for pack-specific metadata; must pass redaction before inclusion |

The six event types:

- `pack.installed` — a pack archive was successfully extracted and registered.
- `pack.removed` — a pack was removed via `selran pack remove`.
- `pack.activated` — a pack participated in a design flow for the first time in a session.
- `component.used` — a pack component was pulled into an output artifact.
- `direction.layered` — a pack overlay was applied on top of a base direction in a render.
- `reference.consulted` — the core loaded a reference doc from a pack during a flow.

`metadata` is intentionally narrow. Pack hooks that want to emit a custom metadata payload must pass the redaction filter; anything that looks like a path, a hostname, or free text is stripped before the event is written.

## 5. What's never recorded

The redaction list is enforced at the emission boundary. Every candidate event passes through it before a line is appended to `events.jsonl`.

- **No file paths.** The redactor matches `/Users/`, `/home/`, `C:\`, `~/`, and any substring that looks like an absolute path. Match drops the event.
- **No hostnames.** Matches `.local` and compares substrings against `socket.gethostname()` output at emission time.
- **No usernames.** Compares against `os.getlogin()` and `getpass.getuser()` output.
- **No IP addresses.** v4 and v6 regex checks run over the full serialized event.
- **No `design-system.md` contents.** Tokens, prose, project name, palette values — none of these appear in any event payload. The emission code never reads `design-system.md`.
- **No git author name or email.** The redactor does not call `git config`; author identity is not part of the event surface in the first place.
- **No free-text the user typed.** Briefs, prompts, chat messages — the emission code has no access to these.
- **No command arguments.** Flags and positional arguments to `selran` commands are not recorded, because they can contain any of the above.

If an event fails redaction, it is dropped on the floor — silently, with a single debug log line. Never emitted partially, never buffered for retry, never surfaced to the pack author. Dropped events are not a bug; they are the contract.

## 6. Storage — local-first

All telemetry state lives under `~/.selran/telemetry/`:

```
~/.selran/telemetry/
├── events.jsonl        # append-only event log, one JSON object per line
├── consent.yaml        # global + per-pack opt-in state
├── install-id          # single-line UUID4
├── hmac-secret         # per-installation HMAC key
└── uploaded/           # optional; successful uploads archived here by date
```

The events file is human-readable and tool-friendly — `jq` works on it, `grep` works on it, `wc -l` counts your events. A typical line looks like this:

```json
{"event_id":"5a2c9d8f-1b74-4e0a-9c3b-b0f4e71d2a41","event_type":"component.used","pack_name":"fintech","pack_version":"0.2.0","direction_base":"technical-minimal","timestamp_utc":"2026-04-23T14:12:03.421Z","install_id":"f7a3e9b2-8c41-4d6e-b1a0-2e9c7d4f51a8","cli_version":"0.3.0","metadata":{}}
```

There is no rotation by default. The user controls retention. `selran telemetry clear` truncates everything and regenerates the install-id; between clears the file grows monotonically. On a typical workstation this adds up to kilobytes per month, not megabytes.

`consent.yaml` is plain YAML. Hand-edit it if you need to; the CLI reads it on every invocation. `install-id` is a single-line UUID4 text file — if you want a fresh identifier without clearing events, delete this file and the next emission writes a new one.

## 7. Commands — user-facing

Everything lives under `selran telemetry <subcommand>`.

```bash
selran telemetry status
selran telemetry enable [--pack <name>]
selran telemetry disable [--pack <name>]
selran telemetry export [--format jsonl|json|csv] [--output <path>]
selran telemetry clear
selran telemetry record <event_type> --pack <name> --version <semver>
selran telemetry upload [--endpoint <url>] [--dry-run]
```

| Command | What it does |
|---|---|
| `status` | Prints the global state, per-pack overrides, event count, and last-upload timestamp |
| `enable` | Flips the global state to opted-in; with `--pack <name>`, overrides only that pack |
| `disable` | Flips the global state to opted-out; with `--pack <name>`, overrides only that pack |
| `export` | Dumps events to stdout or a file in JSONL (default), JSON array, or CSV |
| `clear` | Deletes `events.jsonl`, regenerates `install-id` and `hmac-secret`, leaves `consent.yaml` intact unless you confirm |
| `record` | Appends a single event; invoked by the core skill's hooks, rarely by users directly |
| `upload` | Posts `events.jsonl` to the configured endpoint, signs the body, archives on success |

Example `status` output:

```
Telemetry: opted-in (decided 2026-02-14)
Per-pack overrides:
  fintech            opted-in
  luxury-retail      opted-out
Events recorded: 142
Last upload: 2026-04-01 (37 events uploaded)
Install ID: f7a3e9b2… (use `clear` to regenerate)
```

`record` is documented here for completeness, but users rarely call it. It is the entry point the core skill's telemetry hooks use to append events during a design flow.

## 8. Uploading — explicit, manual, never automatic

`selran telemetry upload` posts the contents of `events.jsonl` to the configured endpoint. There is no scheduled upload, no install-time upload, no background daemon. If you never run the command, no event ever leaves your machine.

The default endpoint is `https://telemetry.selran.design/v1/events`. Override per invocation with `--endpoint <url>`, or globally with the `SELRAN_TELEMETRY_ENDPOINT` environment variable. Self-hosted aggregators accept the same payload format and must present a valid TLS certificate; plain HTTP endpoints are refused.

The request body is signed. The CLI computes an HMAC-SHA256 of the raw body using the per-installation secret at `~/.selran/telemetry/hmac-secret` and sends it in the `X-Selran-Signature` header:

```
X-Selran-Signature: sha256=<hex-digest>
```

The aggregator verifies the signature before accepting the payload; a failed signature is rejected with a 401. The secret never leaves your machine; the aggregator keys its lookup by `install_id` and receives the per-install secret during the first-upload handshake (see `registry/telemetry-spec.md`).

The server responds with `{"accepted": N, "rejected": 0}` or an equivalent JSON shape. Successfully uploaded events are moved to `~/.selran/telemetry/uploaded/<YYYY-MM-DD>.jsonl` so you can audit what was sent. Implementations may choose to delete rather than archive; both are conformant.

`--dry-run` reports the event count and payload size without sending anything. Use it before the first real upload to see what the payload looks like.

Upload failures are silent. Telemetry never blocks the user's workflow, never raises on a network error, never retries in a loop. If the endpoint is unreachable, the command prints a one-line warning and exits 0.

## 9. How pack authors consume the data

Pack authors do not read raw events. The aggregator returns pre-computed per-pack reports; the raw event stream stays on the aggregator side. The canonical format is specified in [`../../registry/telemetry-spec.md`](../../registry/telemetry-spec.md), which is the companion doc to this one.

The short version of what an author sees for their pack:

- Total installs and total removes in the reporting window
- Total activations — distinct installs that actually used the pack in a design flow
- Component-use distribution — which components got pulled into outputs and how often, relative to each other
- Direction-layering distribution — which base directions the pack overlay was applied on top of
- Version adoption curve — how installs migrate across pack versions over time
- Optional time-series histograms for week-over-week tracking

Individual `install_id` values are never exposed to authors. The aggregation floor is N=10: no bucket with fewer than ten distinct installs appears in the report. Pack authors of small packs see less detail by design — early adopters are not fingerprintable from an aggregate report.

Delivery is quarterly, covering a rolling 90-day window. Authors retrieve their report through the aggregator's API (the canonical aggregator's API surface is out of scope for the Phase 10 CLI and will ship with the server component). Self-hosted aggregators can export reports on whatever cadence they choose, subject to the same aggregation-floor requirement.

## 10. Self-hosted aggregators

Self-hosted aggregators are a first-class path, the same way self-hosted registries are in Phase 8. Any organization that wants telemetry to stay inside its network perimeter can run its own.

Configuration is one environment variable:

```bash
export SELRAN_TELEMETRY_ENDPOINT="https://telemetry.example.com/v1/events"
selran telemetry upload
```

Or per invocation:

```bash
selran telemetry upload --endpoint https://telemetry.example.com/v1/events
```

The aggregator must accept the payload format specified in [`../../registry/telemetry-spec.md`](../../registry/telemetry-spec.md). Minimum server-side responsibilities:

- Verify the HMAC-SHA256 signature against the per-install secret registered during handshake
- Store incoming events idempotently, keyed by `event_id`
- Enforce the aggregation-floor of N=10 on every report it emits
- Strip `install_id` from every author-facing output — only aggregate counts and distributions are exposed
- Retain raw events only as long as necessary to produce the reports, and document the retention window

Users who do not trust the canonical aggregator can point at a self-hosted one, or never upload at all. "Never upload" is a fully supported mode — events accumulate on disk and the author simply does not receive that install's data.

The canonical aggregator's source will be MIT-licensed and published alongside the Phase 10 server component (not shipped with the Phase 10 CLI). The reference implementation is the specification: if the canonical server accepts a payload, any conformant self-hosted server must too.

## 11. User rights — export, delete, disable

- **Export.** `selran telemetry export --format json > my-telemetry.json` dumps every recorded event in a plain JSON array. `--format jsonl` produces the on-disk format directly; `--format csv` produces a spreadsheet-friendly file. No encryption, no signing, no format obfuscation. It is your data on your disk.
- **Delete.** `selran telemetry clear` removes `events.jsonl` and regenerates `install-id` and `hmac-secret`. Any future events, if you remain opted in, are uncorrelatable with past events — the aggregator has no way to link the old and new identifiers. The `uploaded/` archive is also cleared.
- **Disable.** `selran telemetry disable` flips the global state to opted-out. Event emission stops immediately. Re-enable later with `selran telemetry enable`; nothing else changes.
- **Per-pack opt-out.** `selran telemetry disable --pack <name>` silences a single pack while the rest keep their state. Useful when one commercial pack author's aggregator feels like more data than you want to share.

There is no account to close, no form to submit, no waiting period. Your data lives on your disk under your control. If you want it gone, delete the directory.

## 12. FAQ

**Does the core skill send any data anywhere?** No. The core skill is telemetry-free. Telemetry is a pack-ecosystem feature scoped to commercial pack usage, opted into through the CLI. The skill itself never calls home, never pings an endpoint, never records an event.

**Can I install commercial packs without ever enabling telemetry?** Yes. Answer No to the prompt. The pack works identically — same components, same overlays, same reference docs. The author simply does not see your usage.

**Can a pack author force-enable telemetry for their pack?** No. The CLI controls consent state, not the pack. Pack code can read `consent.yaml` to check whether the user opted in, but cannot write to it, prompt for it, or bypass a No. Packs that attempt to write telemetry state are rejected during `selran pack validate`.

**What if I install the CLI offline?** Telemetry is local-only by default, so it works identically offline. Events accumulate in `events.jsonl`; the file waits until you run `selran telemetry upload` on a machine with network. Nothing about the core install or pack flow requires network beyond the initial registry fetch.

**Does this apply to MIT seed packs?** No. MIT packs never prompt, never record. The three seed packs (`oss-project`, `personal-blog`, `resume-cv`) sit outside the telemetry system entirely. Installing or using them produces no events, regardless of the global opt-in state.

**Can I contribute to the aggregator server?** When its source lands after Phase 10, yes — it will be MIT-licensed and accept external pull requests. The repository will be published at `github.com/selran/telemetry-aggregator` or a similar path, documented on the roadmap when the server ships.

## Related
- [./packs.md](./packs.md) — Pack-system spec
- [./pack-distribution.md](./pack-distribution.md) — CLI + registry reference
- [./pack-authoring.md](./pack-authoring.md) — Authoring guide
- [../../registry/telemetry-spec.md](../../registry/telemetry-spec.md) — Aggregator payload spec (companion doc)
- [../../cli/README.md](../../cli/README.md) — CLI command reference
