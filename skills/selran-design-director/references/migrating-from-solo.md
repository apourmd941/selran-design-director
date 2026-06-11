# Migrating from solo to a workspace

A short guide for existing solo CLI users who are about to start collaborating with a team. For most users this takes 5 minutes. Nothing about your existing setup breaks; the new surfaces are opt-in.

---

## What changes, what stays the same

| Surface | Solo (Phase 1–20) | Workspace (Phase 21+) |
|---|---|---|
| `selran pack install <name>` (MIT pack) | Works as-is | Works as-is |
| `selran pack install <name>` (commercial pack) | Works via `SELRAN_PACK_LICENSE_<NAME>` env var, `~/.selran/.pack-licenses.yaml`, or per-pack `license_key` file | All three solo paths still work; **plus** a workspace-mediated path: a member with the `editor` role on a project that has the pack license |
| `selran handoff bundle ...` | Works as-is | Works as-is, with the addition of workspace-scoped artifacts (a bundle can reference workspace assets and be commented on by workspace members) |
| `selran preview ...` (local render) | Works as-is | Works as-is |
| `selran auth login` | Not used | The new entry point — opens an OIDC device-code flow against `identity.selran.design` |
| `selran workspace ...` | Not available | Available after `selran auth login`. Workspaces wrap projects + design-systems + pack licenses + members. |
| `selran comment ...` | Not available | Available after joining a workspace. Threaded comments on artifacts. |
| `selran reference ...` | Not available | Available after joining a workspace. URL-or-screenshot reference ingestion + blending. |
| `selran plugin install ...` | Available, but installs into your own user-level state | Available; installs into the active workspace's plugin set, gated by workspace-admin approval |

The general rule: **everything you could do solo, you can still do solo.** Workspaces add new surfaces; they don't replace the existing ones.

---

## When to switch

Stay solo if any of these apply:

- You're the only person working on the design
- You're testing the skill or evaluating it before adopting at a team level
- Your workflow is "design in claude.ai, hand off the markdown to engineering"

Move to a workspace when any of these apply:

- Two or more people are designing the same system
- You need threaded feedback on a draft (instead of doing it in Slack)
- Your team needs SAML SSO for compliance (Phase 21 supports OIDC + SAML 2.0 via WorkOS)
- You need an audit log of who changed what (SOC 2, ISO 27001)
- You need GDPR-style per-user export and erasure
- Your organization wants to install the same commercial pack across multiple users without distributing license-key env vars

---

## The 5-minute migration

### 1. Authenticate

```bash
selran auth login
```

This opens the RFC 8628 device-code flow. The CLI prints a verification URL and a short user code; you open the URL in your browser, enter the code, sign in with your IdP (Google Workspace / Okta / Azure AD / generic OIDC / email-password via WorkOS), and the CLI poll-completes.

The token cache lands at `~/.selran/auth.json` with mode `0600` enforced (the CLI rejects the file if it's found with wider permissions). Your existing solo state — pack licenses, telemetry consent, preview cache — is untouched.

### 2. Create or join a workspace

If you're the team's first user:

```bash
selran workspace create acme-design --display-name "Acme Design"
```

You become the workspace's first admin. Add team members:

```bash
selran workspace invite designer-2@acme.com --role editor
selran workspace invite designer-3@acme.com --role viewer
```

Each invitee gets an email (via Postmark), clicks the single-use link in the email (valid 7 days), authenticates via `selran auth login`, and lands in the workspace.

If someone else has already created the workspace, you'll get an invite email. Click the link, authenticate, accept.

### 3. Switch to the workspace

```bash
selran workspace switch acme-design
```

Now every workspace-scoped command (`comment`, `reference`, `plugin install`, etc.) acts on `acme-design`. The active-workspace state lives on the same row as your access token in `~/.selran/auth.json`, so it travels with the session — switching workspaces does not invalidate the JWT.

### 4. Verify

```bash
selran auth whoami
selran workspace current
selran workspace members
```

`whoami` prints your user ID, email, and workspace memberships. `current` prints the active workspace. `members` prints the team list with roles.

That's the migration. Five minutes.

---

## What about my existing pack licenses?

Two ways to bring them across:

**Path A — keep them as solo licenses.** The three solo gates (`SELRAN_PACK_LICENSE_<NAME>` env var, `~/.selran/.pack-licenses.yaml`, per-pack `license_key` file) still work in a workspace context. If you have a license already, do nothing.

**Path B — promote to workspace-mediated.** A workspace admin can register the license at the workspace level via `selran license register --workspace acme-design --pack <pack-name> --key <license-key>` (Phase 16 + Phase 21 integration). Now any workspace member with the `editor` role on a project that has the pack license can install the pack — no per-user env var.

The CLI checks the gates in this order at install time:
1. Explicit `--workspace <slug>` flag → workspace-mediated license
2. Active session with active workspace → workspace-mediated license
3. Existing solo gates (env var / yaml / key file) → solo license

First match wins. You can run mixed — some packs solo, some workspace-mediated — without breakage.

---

## What about my existing telemetry consent?

Telemetry consent stays per-machine, not per-workspace. The skill respects your existing `~/.selran/telemetry/consent.json` regardless of which workspace is active. Joining a workspace does not auto-enable telemetry; opting in remains explicit.

If you want to share telemetry only from work projects and not personal ones, the cleanest pattern is two terminals with different `SELRAN_TELEMETRY_HOME` env vars — one for work, one for personal.

---

## What about audits?

Every workspace-scoped action (login, workspace creation, invite sent, role changed, comment created, pack installed, plugin installed, etc.) is logged to the workspace audit log automatically (Phase 21c + Phase 22 + Phase 23 extensions to `services/audit/event-taxonomy.md`). The log is append-only with 7-year retention by default (SOC 2 / ISO 27001 alignment). Operators can query it via `services/audit/audit-log-query.py`.

Solo CLI usage is not logged anywhere centrally. Telemetry events still happen if you've opted in, but they're machine-local until you explicitly `selran telemetry upload` to an aggregator.

---

## What if I want to leave a workspace?

```bash
selran auth logout
```

This revokes your refresh token and clears `~/.selran/auth.json`. You're back to solo. Your workspace memberships remain server-side until an admin removes you, but no commands on your machine are workspace-scoped anymore.

If you want to leave the workspace permanently:

```bash
selran auth login                           # log back in
selran workspace switch acme-design         # confirm active
selran workspace leave                      # surrender membership
```

The `leave` command requires confirmation (admin-imposed if you're the last admin in the workspace — the workspace must always have at least one). After leaving, your audit log entries are retained per Phase 21's redaction-with-retention policy: PII is replaced with a redaction sentinel (`redacted-<sha256>@selran.invalid`), but the action history survives.

---

## What about GDPR data export and erasure?

Phase 21c ships `POST /v1/compliance/data-export` and `POST /v1/compliance/erasure`. Both are accessible via the CLI:

```bash
selran auth compliance export        # queues a worker; emails a 7-day signed URL when ready
selran auth compliance erasure       # 7-day cool-off period; type CONFIRM ERASURE to proceed
```

Export gathers everything tied to your user ID across all your workspaces — profile, audit events, workspace memberships, tokens issued, and any user-uploaded assets — into a signed zip. Erasure scrubs PII while retaining the audit trail for legal hold compliance. SLAs: 72 hours for export (well inside the GDPR 30-day window); 7-day cool-off then 30 days for erasure completion.

---

## Common questions

**Can I be in multiple workspaces?**
Yes. Use `selran workspace list` to see all your memberships and `selran workspace switch <slug>` to switch the active one. The role per workspace is independent — you can be `admin` in `acme-design` and `viewer` in `client-x`.

**Does `selran handoff bundle` change inside a workspace?**
The bundle format is unchanged (`bundle_format: "1.0"`, deterministic, ed25519-signed). What changes is *who can verify it*: workspace members can verify against the workspace's registered public key without needing it on their disk. Solo users still verify with `--public-key <path>`.

**Can I install a workspace plugin into my solo CLI?**
No. Plugins install per-workspace, not per-user. Workspace admins approve installs based on capability declarations; that approval doesn't transfer to a personal install. If you want a plugin solo, install it from the canonical registry directly: `selran plugin install <name>` without an active workspace.

**What if my organization runs a self-hosted Selran (on-prem or on a private cloud)?**
Override the service URLs via env vars: `SELRAN_REGISTRY_URL`, `SELRAN_IDENTITY_URL`, `SELRAN_WORKSPACE_URL`, `SELRAN_COMMENTS_URL`, `SELRAN_REFERENCES_URL`. The CLI is deployment-agnostic; only the URLs change. Your auth state lands at the same `~/.selran/auth.json` regardless.

---

## Cross-references

- [`SKILL.md`](../SKILL.md) — the skill's four-phase contract (unchanged across solo/workspace)
- [`references/getting-started.md`](./getting-started.md) — first-session walkthrough
- [`references/packs.md`](./packs.md) — full pack-install + license-gate spec
- [`services/identity/README.md`](../../services/identity/README.md) — Phase 21 identity service overview
- [`services/workspaces/README.md`](../../services/workspaces/README.md) — Phase 21 workspace data model + role matrix
- [`services/comments/README.md`](../../services/comments/README.md) — Phase 22 comments service
- [`services/references/README.md`](../../services/references/README.md) — Phase 22 reference ingestion
- [`services/audit/README.md`](../../services/audit/README.md) — Phase 21 audit log
- [`services/gdpr/data-rights-spec.md`](../../services/gdpr/data-rights-spec.md) — Phase 21 GDPR export/erasure
- [`RELEASE.md`](../../RELEASE.md) — operator-side gates (the workspace surfaces require the operator to have stood up `identity.selran.design` and `workspaces.selran.design`)
