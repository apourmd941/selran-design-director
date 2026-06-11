# Enterprise pack usage (v3.4+)

The per-pack Selran Commercial License v1.0 covers a single legal entity. It is the right fit for a design team at one company buying a handful of industry packs. It is the wrong fit for a holding company with seven operating subsidiaries, an agency rotating through forty end-client engagements a year, or a regulated buyer who needs a contract with a support commitment attached.

Phase 12 adds an enterprise tier that sits on top of the Commercial License — not a replacement. Enterprise extends coverage across affiliates, adds an agency carve-out, opens private-pack hosting as a supported distribution path, and attaches a response-time SLA. The core skill is unchanged. The pack system is unchanged. What changes is the entitlement boundary and the class of customer it is sized for.

This doc is the reference. The license text lives at [`../../LICENSE-ENTERPRISE.md`](../../LICENSE-ENTERPRISE.md); the SLA spec lives at [`../../ENTERPRISE-SLA.md`](../../ENTERPRISE-SLA.md); pricing and contract terms are at `enterprise@selran.com`. Read this doc before you buy. Read the two companions before you sign.

## 1. Who this is for

Enterprise is for four classes of buyer. If you do not fit one of them, a per-pack Commercial License v1.0 is the honest recommendation.

- **Holding companies with multiple operating companies sharing a design system.** One parent, several brands, one design-ops team serving all of them. A single enterprise license covers the parent plus every majority-owned subsidiary without a per-org purchase.
- **Agencies doing branded work across many end-client orgs.** Agencies build for clients, deliver, and move on. A Premium Enterprise license carries an agency carve-out: unlimited concurrent end-client organizations under one contract, for the duration of paid engagements.
- **Large single-org buyers who want multi-entity coverage plus an SLA.** A global enterprise with a legal structure spanning fifteen subsidiaries and a procurement team that needs a named support contact. The per-pack license works for the packs; the SLA and the multi-entity coverage are what justify the upgrade.
- **Regulated industries requiring a named support commitment.** Banks, hospital systems, government contractors. Their procurement processes require contract terms that a clickthrough commercial license cannot supply.

Enterprise is NOT for:

- **Solo practitioners.** Buy the per-pack Commercial License v1.0. Enterprise is priced and structured for organizations, not individuals.
- **Open-source projects.** The MIT seed packs (`oss-project`, `personal-blog`, `resume-cv`) are free, unconditioned, and carry no enterprise path by design.
- **Small teams inside a single legal entity.** The Commercial License v1.0 already covers all employees and contractors within one organization. Upgrading is a cost with no coverage benefit.

## 2. The two tiers

Two tiers, negotiated annually. Pricing is via `enterprise@selran.com` — tier-appropriate and negotiated per-contract.

| Feature | Standard Enterprise | Premium Enterprise |
|---|---|---|
| Multi-org coverage (affiliates + subsidiaries) | Yes | Yes |
| All 22 commercial packs included | Yes | Yes |
| Support response (business hours) | 3 business days | 4 business hours |
| Uptime SLA (registry + license server) | 99.5% monthly | 99.9% monthly |
| Uptime reporting cadence | Annual | Quarterly |
| Quarterly account review | No | Yes |
| Agency carve-out (unlimited end-client orgs) | No | Yes |
| Private-pack hosting rights | No | Yes |
| Priority bug-fix escalation | No | Yes |
| Named account manager | No | Yes |
| Annual contract | Yes | Yes |

Standard is the default recommendation for enterprises that are not agencies and do not need to host internal packs through Selran infrastructure. Premium is the required tier for agency work, the required tier for private-pack hosting, and the tier that carries the 4-business-hour response.

See [`../../ENTERPRISE-SLA.md`](../../ENTERPRISE-SLA.md) for the full SLA spec, including severity definitions, escalation paths, and service credits.

## 3. Obtaining a license

The acquisition flow is five steps. There is no self-serve portal for enterprise — every contract is executed in writing.

1. **Email `enterprise@selran.com`** with the org's legal name, headcount, primary use case, and which tier you are considering. If you are an agency, say so — agency engagements default to Premium.
2. **Sales replies** with a tier recommendation and the draft contract. The contract incorporates the Selran Enterprise License v1.0 text and the SLA v1.0 spec by reference.
3. **On contract signing**, Selran issues two artifacts: a license key and a detached ed25519 signature file. The key format is `selran-ent-<org-slug>-<tier>-<32-base32>`, e.g. `selran-ent-acme-corp-premium-J7K9P2Q8R4X6T1Y3Z5M0A2B4C6D8E0F2`. The signature proves the key was issued by Selran and has not been altered.
4. **Run `selran enterprise register`** on every machine that needs enterprise entitlements:

    ```bash
    selran enterprise register \
      --license-key "selran-ent-acme-corp-premium-J7K9P2Q8R4X6T1Y3Z5M0A2B4C6D8E0F2" \
      --signature-file ./acme-corp.sig
    ```

5. **The CLI writes `~/.selran/enterprise/license.yaml`** with the parsed entitlements: tier, organization slug, expiry date, contract ID, and the raw signature for re-validation. `selran enterprise status` prints the resulting state.

License keys are non-transferable between orgs — the slug is baked into the key and validated at registration. Annual renewal is required; expired licenses drop the CLI into a read-only mode where `list`, `search`, and `status` continue to work (with a prominent expiry warning) but `install` refuses. The grace period is 30 days past expiry; after that, the license is treated as revoked for install purposes.

## 4. Multi-org use and affiliates

An enterprise license covers more than one legal entity, and this is the main reason holding companies buy it. The coverage boundary is deliberately wide.

- A Standard or Premium license covers the **Licensee Organization** plus its **Affiliates**, **Subsidiaries**, and **majority-owned ventures**, all under a single contract.
- "Affiliate" follows the standard corporate-law definition: any entity controlling, controlled by, or under common control with the Licensee, where "control" means greater than 50% ownership or the power to direct management. If your corporate counsel has a working definition of affiliate for your existing contracts, it almost certainly matches this one.
- **Contractors engaged by the Licensee** or an Affiliate are covered for the duration of the engagement. Independent contractors working on their own projects outside the Licensee's direction are NOT covered — they use a per-org Commercial License v1.0.
- **A holding company with 10 operating companies can use ONE enterprise license for all 10**, regardless of brand separation. The operating companies do not each need their own contract.
- Each org MAY install packs independently on its own machines. They MAY share private packs internally across the affiliate group (Premium only — Standard does not include private-pack rights).
- The `license.yaml` file can be distributed internally within the affiliate group; the license key itself is not a secret. The signature is what the CLI validates, and the signature is bound to the specific key. Distributing `license.yaml` to an unrelated third party does not grant them a valid entitlement — their `selran enterprise validate` call will succeed (the signature is fine) but their contractual right to use it does not exist, and support will not service tickets from orgs not named in the contract.

Entities acquired or divested mid-contract follow the contract terms. The default posture is: an acquired entity joins the covered affiliate group as of the acquisition date; a divested entity drops out, and the divested entity must purchase its own license to continue. Mid-contract changes are reported at the next annual renewal.

## 5. Agency carve-out (Premium only)

The per-pack Commercial License v1.0 explicitly requires one license per end-client organization for agencies. That works for a boutique agency running two or three long-term clients a year. It does not work for a 40-person agency rotating through dozens of 6-week engagements. Premium Enterprise replaces that per-client purchase with a carve-out.

- A Premium license covers **unlimited concurrent end-client orgs** the agency is performing contracted design work for. There is no per-engagement cap and no rolling 12-month cap.
- "End-client" means any organization for whom the agency is creating design artifacts under a **paid engagement**. Speculative pitches, pro-bono work for registered non-profits, and internal agency projects are all covered by the same license.
- End-clients do NOT inherit license rights from the agency. Only the agency may use the packs on end-client deliverables. The end-client receives the output (components, pages, a design system document); they do not receive the license.
- When an engagement ends, the agency retains the right to **update existing deliverables for 12 months** without re-engaging. Substantially new work for that same end-client after the 12-month window requires the end-client to hold its own license, or a new paid engagement to restart the clock.
- The `selran enterprise add-client <slug>` command maintains a local append-only log of end-client organizations the agency has worked with:

    ```bash
    selran enterprise add-client acme-corp
    selran enterprise add-client globex-industries
    selran enterprise list-clients
    ```

    This log is audit record-keeping, not enforcement. The carve-out is unlimited; the log exists so the agency can answer questions in a contract audit without reconstructing history from invoices.
- **Sub-licensing is prohibited.** The agency cannot install Selran packs on an end-client's machines using the agency's license. If the end-client wants packs installed on their own systems for in-house work, the end-client purchases their own license (Commercial or Enterprise).

The carve-out does not extend the SLA to end-clients. The SLA is between Selran and the agency. An end-client who wants Selran support buys their own contract.

## 6. Private-pack hosting (Premium only)

Organizations frequently author internal packs — proprietary component libraries, internal brand guidelines, domain-specific references that quote confidential information. They do not want these in the canonical Selran registry, and they do not want to rebuild the install pipeline from scratch. Premium Enterprise opens a supported path.

- Private packs live in a **private registry** the org self-hosts. The registry serves the same `index.json` schema as the canonical one; the org is responsible for the HTTP(S) endpoint and the storage backend behind it.
- Private-registry URL is registered via the CLI or an environment variable:

    ```bash
    selran enterprise private-registry set https://packs.acme.corp/index.json
    # or
    export SELRAN_PRIVATE_REGISTRY_URL="https://packs.acme.corp/index.json"
    ```

- The CLI resolves pack names against the **canonical registry first**, then the private registry. Private-registry authors cannot unintentionally override canonical packs with the same name — canonical wins. Name a private pack `acme-fintech`, not `fintech`.
- `pack.yaml` for private packs MUST include `private: true` and `organization: "<org-slug>"` matching the license's org slug. The `selran pack install <name>` command enforces the org-slug match at install time: attempting to install a private pack marked `organization: acme-corp` from a machine whose license has `organization.slug: globex-industries` fails with an entitlement error. No bypass flag.
- Private packs can still be signed with ed25519. The org either uses Selran's canonical signing infrastructure (trust-on-first-use against Selran's published key) or publishes its own signing key under a `signing_key_id` that matches the org slug. Self-signing is the usual choice; it keeps the signing key inside the org's own key-management story.
- Private packs retain the full pack-system contract: `requires_core`, `requires_packs`, `pack_overrides`, the anti-pattern baseline. `selran pack validate` runs identically against private and public packs; the validator does not care which registry a pack will ship from.
- Private-registry setup, CDN configuration, and access-control patterns are documented in [`../../registry/private-registry.md`](../../registry/private-registry.md).

An annotated `pack.yaml` showing every enterprise field, including `private`, `organization`, and `enterprise_tier`, ships at [`../assets/enterprise-pack-example.yaml`](../assets/enterprise-pack-example.yaml).

## 7. Support and SLA

The enterprise SLA is documented in full at [`../../ENTERPRISE-SLA.md`](../../ENTERPRISE-SLA.md). The summary:

- **Standard:** 3-business-day response during business hours. Business hours are Mon–Fri, 09:00–18:00 UTC, excluding Selran-published holidays. 99.5% monthly uptime target for the canonical registry and the license-validation endpoint, measured from the status page at `https://status.selran.com`. Annual uptime reports.
- **Premium:** 4-business-hour response in the same business window. 99.9% monthly uptime target. Quarterly uptime reports and a quarterly account review call.
- **Channels:** `support@selran.com` for Standard; `priority@selran.com` for Premium. Both tiers may file bugs via GitHub issues on the public repo, but public issues do not carry SLA response times — the SLA clock starts when a ticket is escalated via email.
- **Escalation:** Premium has a named account manager and a direct escalation path documented in the SLA. Standard routes through the shared support queue with standard triage.
- **Measurement:** Response times are measured from ticket creation, not acknowledgement. A 4-hour response clock starts when the email hits the queue.

In-scope for support: the core skill (`SKILL.md`, `references/`, `assets/`), the CLI (bugs, install errors, signature-verification failures), the canonical registry (outages, schema issues), enterprise license lifecycle (registration, validation, renewal, revocation), and the 22 Selran-authored commercial packs across Wave-1 and Wave-2.

Out-of-scope: third-party packs authored by parties other than Selran (they have their own support), MIT seed packs (community-maintained; bug reports welcome via GitHub, no SLA), custom pack authoring (available as a separate professional-services SKU), Claude Code itself (an Anthropic product; file issues with Anthropic), and the user's design choices (Selran builds a skill to build designs, not a design-review service).

## 8. Renewal, termination, revocation

- **Renewal.** Enterprise contracts run for 12 months. Selran provides renewal terms 60 days before expiry. A 30-day grace period after expiry allows read-only CLI operations (`list`, `search`, `status`) to continue with a prominent warning; `install`, `publish`, and private-registry access are blocked during the grace period.
- **Termination for material breach.** Either party may terminate for material breach with a 30-day cure period. Examples of material breach on the customer side: sub-licensing to an uncovered third party, publishing a private Selran pack to the public internet, using Selran-authored pack content as training data for an ML model.
- **Revocation.** Selran maintains a revocation list that `selran enterprise validate` checks on every invocation. A revoked license key is recorded against the contract ID; revoked licenses cannot install new packs or resolve against private registries, but do not forcibly remove packs already installed. Revocation events are rare and require either a contract termination or a confirmed compromise.
- **Post-termination.** Installed packs continue to function as static files on disk. No updates, no new installs, no private-registry access. The output the Licensee produced while the license was active remains theirs — the license covers the tooling, not the artifacts.

## 9. Skill-side behavior

When a user interacts with the skill inside Claude Code, the skill handles enterprise-adjacent questions by shelling out to the CLI and interpreting the result. The skill does not re-implement license parsing.

- *"do I have an enterprise license?"* / *"check my license"* → skill runs `selran enterprise status`, parses output, surfaces tier + expiry + entitlements in plain language.
- *"install this private pack"* → skill checks the license tier and org slug, surfaces the entitlement check before attempting install, and explains the error clearly when the org slug does not match.
- *"how do I host a private pack?"* → skill loads this doc's section 6 plus `registry/private-registry.md` and walks the user through private-registry setup, signing-key decisions, and the `private` + `organization` fields in `pack.yaml`.
- *"add an end-client"* → skill confirms Premium tier (aborts with a helpful message on Standard), runs `selran enterprise add-client <slug>`, and shows the updated client list.
- *"what does my enterprise tier cover?"* → skill runs `selran enterprise status --json`, formats entitlements as a bulleted list, and points at this doc for the full spec.
- *"license expires soon"* → skill surfaces the expiry warning from `selran enterprise validate`, notes the 30-day grace window, and links to `enterprise@selran.com` for renewal.

The skill never fabricates license state. Every enterprise-aware answer is grounded in a CLI call the user can re-run.

## 10. FAQ

**Can a Standard license cover agency work?** No. Agency work across multiple end-client orgs requires Premium. Standard is an affiliate-coverage upgrade, not an agency-carve-out upgrade.

**If our holding company has 10 subsidiaries, do we need 10 licenses?** No. One enterprise license covers all affiliates, subsidiaries, and majority-owned ventures under a single contract. This is the core reason to choose Enterprise over per-org Commercial.

**What happens to our installed packs if we don't renew?** They keep working. Pack files are static; they do not phone home. No updates, no new installs, no private-registry access until a new license is acquired. Output shipped while the license was active stays shipped.

**Can an end-client continue using the deliverables after the engagement ends?** Yes. The deliverables are theirs. The enterprise license covers the agency creating the deliverables, not the end-product artifact. What the end-client cannot do is install the Selran packs on their own machines using the agency's license — that requires the end-client's own license.

**Can we audit the license terms before signing?** Yes. The full license text is at `LICENSE-ENTERPRISE.md` in the repo root; the SLA text is at `ENTERPRISE-SLA.md`. Both are public and auditable by procurement, legal, or security teams before contract execution.

**Does enterprise include third-party packs from the canonical registry?** No. Third-party packs have their own licenses and are not part of the enterprise bundle. Enterprise covers the 22 Selran-authored commercial packs (five Wave-1 industries, seven Wave-1 archetypes, five Wave-2 industries, five Wave-2 archetypes), the core skill, the CLI, canonical-registry access, and — on Premium — private-pack hosting rights.

**Is there a non-profit or educational discount?** Contact `enterprise@selran.com`. Educational institutions and registered non-profits are commonly granted discounted terms; both tiers are available at reduced pricing for qualifying organizations.

## Related
- [../../LICENSE-ENTERPRISE.md](../../LICENSE-ENTERPRISE.md) — Selran Enterprise License v1.0 text
- [../../ENTERPRISE-SLA.md](../../ENTERPRISE-SLA.md) — Full SLA spec
- [./packs.md](./packs.md) — Pack-system spec
- [./pack-distribution.md](./pack-distribution.md) — CLI + registry reference
- [./pack-authoring.md](./pack-authoring.md) — Authoring guide
- [./pack-telemetry.md](./pack-telemetry.md) — Opt-in telemetry (separate from enterprise)
- [../../registry/private-registry.md](../../registry/private-registry.md) — Private-registry setup
- [../../cli/README.md](../../cli/README.md) — CLI command reference
- [../assets/enterprise-pack-example.yaml](../assets/enterprise-pack-example.yaml) — Annotated enterprise pack manifest
