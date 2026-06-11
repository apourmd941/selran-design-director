# Cross-skill integration — one pack, every Selran skill

Phase 13 extends the pack system one step further. A pack an org bought for the Selran Design Director skill becomes readable by sibling Selran skills — marketing-copy, email-campaign, landing-page-critic — without a second purchase, a second install, or a second contract. One pack. One license. Every Selran skill in the family honors it.

The core skill is unchanged at v3.4. The pack system is unchanged. What changes is that pack content is now exposed through a narrow, read-only API that any sibling skill can consume, and three demonstration companion skills ship in this repo to show the contract working end-to-end.

This doc is the reference. The spec for the wire format lives at [`../../registry/cross-skill-spec.md`](../../registry/cross-skill-spec.md). The CLI additions are in [`../../cli/README.md`](../../cli/README.md). The three companion skill stubs live at [`../../companion-skills/`](../../companion-skills/).

## 1. Why cross-skill integration exists

Packs are expensive to author and expensive to buy. A $29 archetype pack or a $49 industry pack represents a design-director-level opinion about voice, tokens, anti-patterns, and components, captured once and sold many times. When that pack is only readable by one skill, the investment is under-leveraged.

Most orgs that pay for a pack do not just design — they also write marketing copy, send email campaigns, and critique their own landing pages. Every one of those adjacent surfaces should sound and feel like the design system the pack codifies. The voice guide in the `mission-driven` pack is not Design Director content. It is brand content. It applies the moment anyone at the org writes a subject line, a hero headline, a CTA, or a landing page.

Phase 13 is the plumbing that makes that happen. An org pays once for a pack. Every Selran skill honors it. The voice guide that the design skill uses to critique copy blocks is the same voice guide marketing-copy uses to write them, is the same voice guide landing-page-critic uses to flag tone violations. The pack becomes a single design-system-as-a-service, not a skill-local data file.

The value thesis is narrow and defensible: packs stop being design-skill artifacts and start being org-wide style contracts. An agency that paid $49 for a fintech pack gets fintech-voice copy, fintech-voice email, and fintech-tone page critique from the same investment. The incremental cost is zero.

## 2. What it is not

Cross-skill integration is the simplest version of the idea that actually works. It is worth being explicit about the things it deliberately isn't.

- **Not skill-to-skill RPC.** Sibling skills do not call the Selran Design Director skill at runtime. The core skill is not invoked, not loaded, not consulted. A sibling skill reads a static JSON slice emitted by the CLI or the Python API, nothing more.
- **Not a pack-multiplexing registry.** There is no broker service, no mediator process, no daemon. The pack files on disk are the source of truth; the CLI reads them and hands a slice to the caller. Sibling skills talk to pack files through the `selran_cli.cross_skill` module directly, or shell out to the CLI.
- **Not automatic.** A sibling skill must declare that it consumes packs, and a pack must declare that it ships slices for that sibling skill. Opting in on both sides is the contract. A skill that never imports `PackConsumer` and never runs `selran pack export-for-skill` sees no pack content — nothing leaks across the boundary without an explicit call.
- **Not a runtime dependency.** Packs are read-only data to sibling skills. A sibling skill without a pack installed runs in a built-in defaults mode. Adding a pack enriches behavior; removing a pack drops behavior back to the skill's own baseline. No hard failure, no required integration.
- **Not a training pipeline.** Pack content is consumed in-session by the sibling skill's prompt. It is not aggregated, not uploaded, not fed to any model. The license-gate and the no-training clauses from the pack license carry forward unchanged.

If what you need is shared-state RPC or automatic propagation, Phase 13 is not the layer. The layer for those is something the ecosystem has not yet asked for.

## 3. The three sibling skills

Phase 13 ships three companion skills as MIT-licensed demonstration stubs. Each is narrowly scoped, each consumes a well-defined pack slice, each cites the pack on every use. They are `companion-skills/selran-marketing-copy/`, `companion-skills/selran-email-campaign/`, and `companion-skills/selran-landing-page-critic/`.

**`marketing-copy`.** Writes headlines, body copy, and CTAs against a brand voice. Consumes `voice_guide`, `forbidden_phrases`, and `copy_library` from the pack slice; omits tokens, components, and anti-patterns. Active when the user asks for copy — *"write a hero headline for this launch,"* *"give me three CTA variants,"* *"body copy for the pricing page."* If a pack is installed, the copy is constrained by the pack's voice contract. If not, the skill uses its internal defaults.

**`email-campaign`.** Composes transactional and marketing emails, from subject line to footer. Consumes the full slice — tokens (for MSO-inline styling), voice guide, forbidden phrases, copy library (for subject-line patterns and CTA variants), components CSS (for the dark-mode-safe button), and citations. Active for user asks like *"welcome email for new members,"* *"abandoned-cart sequence,"* *"quarterly impact report email."* Email is the only sibling skill that consumes all optional slice fields.

**`landing-page-critic`.** Reviews an existing landing page — HTML file, URL, or pasted markup — against the pack's discipline rules. Consumes `anti_patterns`, `voice_guide` (for forbidden-tone detection inside headlines), and `forbidden_phrases`. Active when the user says *"critique this page,"* *"what's wrong with our pricing layout,"* *"does this headline violate our voice?"* Does not write — it reports.

Each skill is independent. Installing one does not require installing the others. All three are MIT so any org can fork, rename, adapt.

## 4. How packs declare support

A pack opts into cross-skill consumption through a single optional manifest field in `pack.yaml`:

```yaml
name: "mission-driven"
version: "0.2.0"
license: "Commercial"
skill_consumers:
  - "marketing-copy"
  - "email-campaign"
  - "landing-page-critic"
```

The enum is kebab-case, closed to the three slugs above in Phase 13, and expanded only through future phases with a schema bump. Omitting the field opts the pack out entirely — its content stays Design Director-local and the `selran pack export-for-skill` command refuses with an opt-out error.

Declaring a skill consumer that doesn't match a pack's content is a pack-quality flag, not a hard error. A pack that lists `email-campaign` but ships no tokens and no copy library will emit a slice with those fields empty. The CLI warns at `selran pack validate --full` time; the sibling skill logs the empty-slice warning at runtime and falls back to defaults. The ecosystem's signal-to-noise relies on pack authors being honest: declare what your content actually supports, not what you wish it supported.

Pack authors do not need to restructure their content to support sibling skills. The slice builder is an extractor, not a refactor. It reads the pack's reference docs and copy assets, pulls the H2 sections it understands (`## Voice principle`, `## Forbidden phrases`, `## Example headlines`, etc.), and maps them into the slice schema. Packs authored before Phase 13 are supported verbatim as long as their reference docs use the conventional H2 vocabulary documented at [`./pack-authoring.md`](./pack-authoring.md).

## 5. How sibling skills discover installed packs

Discovery is filesystem-first. Every sibling skill — and any third-party skill that wants to be a consumer — reads from the same directory the core skill writes to.

The canonical location is fixed by the `SELRAN_PACK_HOME` environment variable, default value `~/.claude/skills/selran-design-director/packs/`. Every directory in there that contains a `pack.yaml` is a pack. Every pack whose manifest declares `skill_consumers: [<slug>]` is visible to that sibling.

Two discovery paths ship with Phase 13:

```bash
# CLI path — the sibling skill shells out
selran pack list --skill-consumable --skill marketing-copy
```

Output is a table of pack name, version, license, and the slice's payload sizes. Machine-parseable with `--json`.

```python
# Python path — the sibling skill imports directly
from selran_cli.cross_skill import PackConsumer

consumer = PackConsumer()
packs = consumer.list_consumable("marketing-copy")
for pack in packs:
    print(pack.name, pack.version, pack.license)
```

Both paths respect `SELRAN_PACK_HOME`. Both honor the license-gate on commercial packs (section 10). Neither path touches the network.

A sibling skill that finds zero consumable packs is not in an error state. It is in its default mode, and should run with internal behavior only, noting in its output that no pack was consulted.

## 6. The pack slice contract

The slice is JSON. The shape is frozen at `schema_version: "1.0"` and only additive changes are permitted within that version. Breaking changes bump the top-level version and ship a migration note, not a silent replacement.

```json
{
  "schema_version": "1.0",
  "pack_name": "mission-driven",
  "pack_version": "0.2.0",
  "pack_license": "Commercial",
  "skill_consumer": "marketing-copy",
  "tokens": null,
  "voice_guide": {
    "principle": "Never say what you are; show what you did.",
    "sentence_mechanics": "...",
    "verb_energy": "...",
    "vocabulary": "..."
  },
  "forbidden_phrases": ["empowering", "unlocking", "driving impact", "changemakers"],
  "copy_library": {
    "headline_patterns": ["stat-first with a year attached"],
    "example_headlines": ["We planted 47,000 trees in 2025.", "..."],
    "cta_variants": ["Donate $47", "Sponsor a tree", "Read the annual report"]
  },
  "components_css": null,
  "anti_patterns": null,
  "cite": {
    "pack_readme": "packs-official/mission-driven/README.md",
    "reference_doc": "packs-official/mission-driven/provides/references/mission-driven-voice.md",
    "pack_version": "0.2.0"
  }
}
```

Top-level keys are fixed. There are exactly twelve.

Which fields are populated depends on which sibling skill asked for the slice:

- **`marketing-copy`** populates `voice_guide`, `forbidden_phrases`, `copy_library`, `cite`. Sets `tokens`, `components_css`, and `anti_patterns` to `null`.
- **`email-campaign`** populates every optional field. It's the heaviest consumer.
- **`landing-page-critic`** populates `anti_patterns`, `forbidden_phrases`, `voice_guide` (for forbidden-tone detection), `cite`. Omits `tokens`, `copy_library`, `components_css`.

A field that is semantically absent is `null`. A field that the skill consumer shape says should be populated but the pack has no content for is `[]` or `{}` (empty but present) — the sibling skill uses that signal to log a warning and proceed with defaults.

`schema_version: "1.0"` is frozen. Additions go at the tail of existing arrays or as new keys inside existing objects. Renames, type changes, or deletions require a major bump. The contract is stable enough to build production skills against. The freeze is now mechanically enforced on every PR by [`.github/workflows/schema-freeze.yml`](../../.github/workflows/schema-freeze.yml) — a backward-incompatible change to any of the four guarded schemas (`cross-skill-export`, `telemetry-event`, `enterprise-license`, `pack-entry`) fails CI unless the PR's head commit starts with `BREAKING:` and bumps the `schema_version` const's major.

## 7. Skill author workflow

Concretely, the happy path for a sibling skill consuming a pack looks like this. The user has `mission-driven` installed. The user asks the marketing-copy skill: *"write a hero headline for our end-of-year campaign."*

```bash
# The skill discovers what's available.
selran pack list --skill-consumable --skill marketing-copy --json

# It sees mission-driven, and asks for the slice.
selran pack export-for-skill mission-driven --skill marketing-copy > /tmp/slice.json
```

Or the Python path:

```python
from selran_cli.cross_skill import PackConsumer

consumer = PackConsumer()
packs = consumer.list_consumable("marketing-copy")
# -> [PackInfo(name="mission-driven", version="0.2.0", license="Commercial")]

slice = consumer.load_pack_slice("mission-driven", "marketing-copy")
# -> dict with voice_guide, forbidden_phrases, copy_library, cite
```

The skill then uses the slice inside its generation prompt:

1. Seeds the system prompt with `slice["voice_guide"]["principle"]`.
2. Constrains the output to avoid any string in `slice["forbidden_phrases"]`.
3. Biases toward patterns in `slice["copy_library"]["headline_patterns"]`.
4. Emits the headline.
5. Appends a citation footer from `slice["cite"]`.

The final output the user sees includes both the generated headline and an attribution block:

```
"We planted 47,000 trees in 2025. Help us plant 100,000 in 2026."

Voice guidance from the mission-driven pack v0.2.0
(packs-official/mission-driven/provides/references/mission-driven-voice.md)
```

The citation is required — section 9. The slice is discarded at end of session — section 10.

## 8. Pack author workflow

For a pack author, cross-skill support is three edits and a validation run.

1. **Declare the consumers.** Add `skill_consumers: [marketing-copy, email-campaign]` to `pack.yaml`. Only list the slugs your content actually supports. A voice-heavy archetype pack with no components almost certainly belongs in `marketing-copy` and `landing-page-critic`; it has no useful content for `email-campaign` unless you also ship tokens.
2. **Use extractor-friendly H2 headings in the reference doc.** The slice builder parses reference docs for sections it recognizes. The conventional H2 vocabulary — `## Voice principle`, `## Forbidden phrases`, `## Example headlines`, `## CTA variants`, `## Anti-patterns`, `## Sentence mechanics`, `## Verb energy`, `## Vocabulary` — is documented at [`./pack-authoring.md`](./pack-authoring.md). Hitting the vocabulary is worth the small amount of discipline.
3. **Validate.** Run `selran pack validate --full ./my-pack/`. The `--full` flag adds a slice-generation pass that exports a slice for every declared consumer and inspects it for empty optional fields. Warnings are surfaced; the author fixes coverage gaps before publishing.
4. **Publish.** No change to the publish flow. Sibling-skill support rides along with the pack archive; no separate artifact is built, no separate registry listing.

A pack that passes `validate --full` with no empty-slice warnings is a well-shaped pack. Sibling skills will get useful content out of it on every call.

## 9. Citation and attribution

Every sibling skill that uses pack content MUST cite the pack. This is not a soft convention. It is the attribution contract, and it is why the `cite` field is the one field every slice always populates.

The `cite` field carries three machine-readable paths: the pack's README.md, the reference doc that sourced the content, and the pack version. The sibling skill embeds them verbatim, unformatted, into its output so the user can trace every voice choice, every forbidden phrase, every anti-pattern back to the pack that supplied it.

The canonical citation format:

```
Voice guidance from the <pack_name> pack v<pack_version>
(<reference_doc_path>)
```

For a landing-page critique that consumed `mission-driven`:

```
Anti-patterns from the mission-driven pack v0.2.0
(packs-official/mission-driven/provides/references/mission-driven-voice.md)
See also packs-official/mission-driven/README.md.
```

The citation is at the end of the output, not buried in metadata. The user reads it. The sibling skill does not get to suppress it, soften it, or move it behind a click. Attribution is part of the deliverable.

Pack authors benefit from the citation carrying the pack name and version in plain text: it is a free surface for their work, correct by construction, and resistant to being stripped by the skill author. Orgs benefit from traceability — every copy decision points back to the reference that justified it.

## 10. License-gate carry-forward

Commercial packs stay commercial when consumed by sibling skills. The license does not dilute because the content crossed a skill boundary.

`PackConsumer` calls the same license-gate code that `selran pack install` calls. On a commercial pack with no license acknowledgment, the `load_pack_slice` method raises `LicenseGateError` and the sibling skill refuses to proceed. MIT packs pass through freely. Enterprise-licensed packs (Phase 12) are checked against `~/.selran/enterprise/license.yaml` and gated by org slug and tier.

Three operational rules fall out of the carry-forward:

- **The sibling skill MUST NOT cache slice content past a session.** Caching converts a per-invocation license check into a one-time check, and a pack the user has since uninstalled or whose license has since expired must not keep leaking content. `PackConsumer` loads from disk on every call; in-memory caches inside the sibling skill's process end at session teardown.
- **The sibling skill MUST NOT write pack content to telemetry, logs, or any persistent store** that survives the session. Transient stderr is fine. Piping the slice into a file the skill writes is not.
- **The sibling skill MUST re-check on every invocation.** A user who uninstalls a pack mid-session and then asks the skill for copy should, on the next invocation, get the default-mode output. No stale references to a pack that is no longer present.

These rules are load-bearing. An uncited, cached, persistent copy of pack content breaks the license gate even if the initial slice-load was legal. The companion skills in `companion-skills/` implement the contract correctly and are the reference model.

Any sibling skill author who cannot or will not follow these rules should not consume packs. The entire design of Phase 13 is that the license gate survives the skill boundary intact.

## 11. FAQ

**Can sibling skills write to packs?** No. Packs are read-only to sibling skills. The CLI exposes no `update` or `write` API on the cross-skill module. Pack files are modified only by the pack author's own tooling and by `selran pack install`.

**What happens if a pack declares `skill_consumers: [email-campaign]` but ships no component CSS?** The slice emits `components_css: {}` (empty object, not null), the CLI warns at `validate --full` time, and the sibling skill logs a warning and falls back to its internal defaults for component styling. Partial coverage is legal; it is surfaced, not hidden.

**How do I build my own sibling skill?** The three stubs at [`../../companion-skills/`](../../companion-skills/) are the reference implementations. Each is MIT and under 2,500 lines of content; copy the directory structure, rename the skill, adapt the reference doc to your surface, keep the Phase 1 through Phase 5 workflow intact (discover, ask, load, execute, cite), and ship. The [`../../companion-skills/README.md`](../../companion-skills/README.md) spells out the full contract.

**Can a non-Selran skill consume Selran packs?** Yes. `selran_cli.cross_skill.PackConsumer` is a public API with a stable signature. Any Claude Code skill — or any Python program — can `pip install selran-cli` and import it. The citation and license-gate contracts apply identically; the `PackConsumer` class enforces both regardless of who is calling it.

**Does this feature leak to telemetry?** No. Pack telemetry (Phase 10) records events on pack installs and on core-skill pack consumption, both opt-in and both local-first. Cross-skill consumption is **not** recorded. It is session-local, invisible to the aggregator, and does not produce a JSONL event. A sibling skill reading a slice is indistinguishable from the user reading the reference doc directly.

**Can I consume a private enterprise pack from a sibling skill?** Yes, if the enterprise license gate passes. `PackConsumer` calls `selran enterprise validate` before handing back any slice for a private-org pack. If the calling machine holds the enterprise license that matches the pack's `organization` slug, the slice is returned; if not, `LicenseGateError` is raised. Private packs never leak across org boundaries through the cross-skill API.

**What version of `selran-cli` do I need?** 0.5.0 or later. The `cross_skill` module shipped in that release. Pin with `selran-cli>=0.5.0` in any downstream skill that depends on the API.

**Is there a rate limit or a network call?** No. The cross-skill API is entirely local — it reads from `SELRAN_PACK_HOME` on disk. No HTTP, no socket, no daemon. A slice-load is an open, a YAML parse, a handful of file reads from the pack's `provides/references/` tree, and a JSON emit.

## Related

- [./pack-distribution.md](./pack-distribution.md) — CLI, registry, signatures, lockfiles
- [./pack-authoring.md](./pack-authoring.md) — authoring a pack, including the H2 vocabulary the slice builder reads
- [./pack-telemetry.md](./pack-telemetry.md) — opt-in telemetry (cross-skill consumption is out of scope)
- [./pack-enterprise.md](./pack-enterprise.md) — enterprise tier and private packs (enforced in cross-skill)
- [../../registry/cross-skill-spec.md](../../registry/cross-skill-spec.md) — wire format, error codes, implementation spec
- [../../companion-skills/README.md](../../companion-skills/README.md) — companion-skills directory overview
- [../../cli/README.md](../../cli/README.md) — full CLI reference, including `pack export-for-skill` and `pack list --skill-consumable`
