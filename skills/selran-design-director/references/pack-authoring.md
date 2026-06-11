# Pack Authoring — the canonical guide

This is the craft guide for authoring a Selran pack. It sits alongside three sibling docs: [`packs.md`](./packs.md) covers the manifest schema and three-tier layering, [`pack-distribution.md`](./pack-distribution.md) covers the CLI, registry, signing, and publish flow, and [`../assets/pack-template/`](../assets/pack-template/) is the scaffold that `selran pack init` expands. This doc is the opinionated middle — how to think about a pack before you write one, and how to write it so the ecosystem trusts it.

Read it end to end before you commit a weekend to authoring. The time cost is twenty minutes. The time cost of authoring a pack that fails validation after a full weekend of work is much higher.

## 1. Who should author a pack

Packs are not a universal answer. They exist for authors who have a repeatable, opinionated take on a vertical, archetype, or surface — codified once and reused across many projects.

You should author a pack if you are one of these:

- An agency building for repeat-industry clients. You have opinions about fintech or legal-tech that you re-apply on every engagement. A pack codifies those opinions once and earns on every reuse.
- A solo designer or developer with a strong take on a vertical or archetype. You have written the same voice guide three times by hand. Write it once, ship it as a pack.
- An open-source project maintainer who wants a first-class design surface. Your project has a landing page, a docs site, a dashboard — a pack gives contributors a shared reference.
- An organization with an internal-only design system. You want Selran-native distribution inside the company without publishing anything externally. Proprietary packs are first-class.

You should NOT author a pack if:

- You have one project, not a pattern. Use the core directions directly. A pack is for things you will use more than once.
- Your "pack" is a single client's brand book. That belongs in a per-project `design-system.md`, not a redistributable pack.
- You are chasing a visual trend. Trends pass. Packs persist. The packs that age well encode an industry or a voice, not a color palette of the month.

Authorial honesty is a feature of the ecosystem. Shipping a weak pack hurts the buyer, hurts your reputation, and hurts the registry's signal-to-noise. If the pack doesn't earn its existence, don't ship it.

## 2. The three pack shapes

The ecosystem has converged on three pack shapes. Pick one. Do not mix.

**Industry pack.** Heavy on components — seven to ten or more at v0.2.0 — and anchored by a domain-specific reference doc in the 1,500 to 2,100 word range. Industry packs are compliance-aware when the vertical demands it: HIPAA language for healthcare, Bluebook citations for legal-tech, Section 508 accommodations for gov-civic. Base price is $49. Examples already in the ecosystem: `fintech`, `healthcare`, `legal-tech`, `gov-civic`. Buyers pay for the components and the reference doc in roughly equal measure.

**Archetype pack.** Lighter on components — two or three voice-forward pieces — and anchored by a heavy voice guide in the 1,900 to 2,100 word range. The voice guide is the main deliverable. Think of it as the style guide the brand's principal would hand a new writer on day one. Base price is $29. Examples: `challenger`, `luxury`, `academic`, `activist`, `hospitable`. Buyers pay almost entirely for the voice.

**Seed or utility pack.** MIT-licensed, free, narrow in scope. Demonstrates one surface well. Same structural shape as an industry pack at v0.1.0 — three components, one reference doc, one overlay — but written to seed the ecosystem, not to generate revenue. Examples: `oss-project`, `personal-blog`, `resume-cv`. These are gifts to the ecosystem and loss-leaders for authors building reputation.

If you catch yourself wanting to ship "a mostly-archetype pack with a few industry components," stop. You are about to ship something that is hard to price, hard to market, and hard to maintain. Split it into two packs or cut it down to one shape.

## 3. Naming and licensing

Names travel. They appear in class prefixes, in the registry index, in purchase receipts, in every reference doc. Choose deliberately.

Naming rules:

- Kebab-case, lowercase, ASCII-only. `fintech`, `saas-b2b`, `quiet-professional` are correct.
- Scoped packs for third-parties take the form `@org/pack-name`. The Phase 8 registry supports scoping; the Phase 9 CLI scaffolds it with `selran pack init --scope @org`.
- Do not overlap with existing pack names on the canonical registry. Check `registry/index.json` before you commit to a name.
- Do not use trademarked words without written permission. `apple-style`, `airbnb-cards`, `stripe-look` — none of these are allowed on the canonical registry.
- The name becomes a CSS class prefix: `pack-<name>-*`. Short is kind. A 20-character pack name leaks into every class in every component.

Licensing is four choices plus an internal-only option. Each has trade-offs.

| License | Who uses it | Trade-off |
|---|---|---|
| MIT | Seed packs, OSS project packs, ecosystem demos | Zero revenue; maximum reach; cannot be retracted |
| Apache-2.0 | Corporate MIT-equivalent with patent grant | Same reach as MIT plus a patent license; slightly more legal comfort for corporate consumers |
| CC-BY-4.0 | Content-heavy packs where the voice guide is the primary deliverable | Good for writing-forward packs; awkward when components mix CC-BY content with CSS that is more naturally MIT |
| Commercial | Paid packs, single-org perpetual | Revenue, a license gate, an actual LICENSE file and EULA you maintain |
| Proprietary | Internal-only packs inside an organization | No public registry entry; strict distribution control |

Three questions will usually pick the license for you:

1. Do you want to charge? Yes → Commercial. Internal-only → Proprietary.
2. Is the content the product, or are the components the product? Content → CC-BY-4.0. Components → MIT or Apache-2.0.
3. Does your buyer base require patent grants (large enterprises, government)? Yes → Apache-2.0 over MIT.

Ship an actual LICENSE file with the full text at the pack root. A `license: MIT` field in `pack.yaml` without a LICENSE file fails validation.

## 4. Component discipline — the five non-negotiables

Pack components are judged against the core's anti-patterns the same way any Selran output is. There are five rules every pack MUST follow. The validator enforces all five automatically in Phase 9.

1. **Class scoping.** Every custom class begins with `.pack-<name>-<component>-*`. No bare `.hero`, no bare `.card`, no generic `.wrapper`. Packs coexist in the same DOM; collisions are real.
2. **Token fallbacks.** Every `var(--token)` call carries a fallback. Write `var(--accent, #0A7A5C)`, not `var(--accent)`. Packs should render even when a consumer has not wired the core tokens.
3. **No bare hex outside `:root`.** Hex values live in `:root { --accent: #...; }` token definitions or inside `var(--accent, #...)` fallback slots. Anywhere else is an override fight waiting to happen.
4. **`:focus-visible` and `prefers-reduced-motion` on every interactive surface.** The accessibility baseline is not a pack-author judgment call. Every button, link, card with hover, and toggle needs both.
5. **No AI-slop defaults.** No Inter, Poppins, Roboto, Helvetica, or Arial as the first entry in the body stack. No purple-to-pink gradients. No emoji-as-UI-icons — use inline SVG or text glyphs. These are the tells of AI-generated design. If your pack has them, your pack is AI-generated design.

`selran pack validate --full` checks all five mechanically. If validation is clean, you have met the baseline. You have not yet shipped a good pack — the baseline is necessary, not sufficient — but you have cleared the floor.

## 5. Overlay authoring — the "only override what differs" principle

`pack-overrides/<direction>.yaml` is a shallow-merge overlay on top of a core starter. Authors new to Selran frequently over-override, redefining tokens that already match the core. Overlays are diffs, not complete token sets.

Good overlay:

```yaml
pack_overrides:
  technical-minimal:
    color:
      accent: "#0B4A8C"
      accent_hover: "#083869"
      gray_family: "cool"
    personality:
      - "tabular numerals on every numeric surface"
```

Bad overlay — over-specifying:

```yaml
pack_overrides:
  technical-minimal:
    color:
      bg: "#FFFFFF"          # same as core — remove
      fg: "#18181B"          # same as core — remove
      accent: "#0B4A8C"
      accent_hover: "#083869"
      border: "rgba(0,0,0,0.08)"  # same as core — remove
      semantic:              # same as core — remove
        success: "#16A34A"
        warning: "#EAB308"
        error: "#DC2626"
```

The rule: if the value equals the core starter's value, delete the line. An overlay that redefines forty tokens is either doing more work than a new direction would — in which case author a new direction, rare and advanced — or it is noise that will drift out of sync the next time the core updates.

Phase 9's `selran pack validate --full` emits a warning when an overlay redefines a token that matches the core. The warning is a signal, not a blocker. Treat it as a prompt to trim.

## 6. Reference doc discipline

The reference doc is what buyers actually pay for in industry and archetype packs, and what ecosystem contributors care about in seed packs. Write it like it is the product, because it is.

Structural rules:

- Word-count targets by shape: industry 1,500–2,100; archetype 1,900–2,100; seed 1,500–1,700. Under 500 words fails `selran pack validate --full`.
- Every reference doc must include a "Forbidden phrases" section (spelled exactly, or "forbidden" as a prefix). Not a flourish — this is the section buyers reference most after purchase.
- Industry packs must include three to six annotated reference sites with one-line notes on why each example works. Archetype packs must include three to four annotated reference brands with the same.
- Headings use ATX style — `##`, never underlines.
- No emojis. No purple prose. No "let's dive in!" blog voice.
- Voice is practical, opinionated, confident, welcoming. Not preachy.

The reference doc is a craft document, not a marketing document. If a sentence would be at home in a sales landing page, cut it.

## 7. Versioning and CHANGELOG discipline

Packs use semver: `<major>.<minor>.<patch>`. The conventions differ pre- and post-1.0.

Pre-1.0 (`0.x.y`):

- Bump minor for new components, new sample screens, new overlay tokens.
- Bump patch for fixes, typos, rendering bugs.
- Breaking changes are allowed but must be documented in CHANGELOG under a `### Breaking` heading.
- `0.1.0` is the initial release. `0.2.0` is the first major expansion.

Post-1.0 (`1.x.y`):

- Bump major for breaking changes — component removal, scoping rename, license change.
- Bump minor for additions.
- Bump patch for fixes.

CHANGELOG format follows Keep a Changelog:

```markdown
## [0.2.0] — 2026-04-22

### Added
- ...

### Changed
- ...

### Fixed
- ...
```

`selran pack validate --full` checks that `## [<manifest.version>]` exists in `CHANGELOG.md`. A version bump without a CHANGELOG entry fails.

## 8. Testing a pack locally before shipping

The local development loop matters more than the publish step. Ship a pack that has never been opened in a browser and the registry will catch it, but only after a reviewer has wasted their time.

The loop, in order:

1. `selran pack validate --full ./my-pack/` — fix every error, address every warning.
2. `selran pack build ./my-pack/ --dry-run` — produces `./dist/my-pack-0.1.0.tar.gz` without publishing anywhere.
3. Extract the archive into `~/.claude/skills/selran-design-director/packs/` on a test install.
4. Open Claude Code, run a fresh design flow against `direction: <base>+my-pack`.
5. Inspect the result: do the components render? Does the overlay apply? Does the reference doc surface when the model asks for it?
6. Iterate in-place, rebuild, reinstall, repeat until the pack feels right.

For components specifically, apply the standalone test: every HTML component must render correctly when you double-click the `.html` file and open it in a browser with no build step. Embedded CSS, inline SVG, static content. If the component depends on a bundler, a framework, or an external asset, the dependency has leaked. Fix it before you ship.

## 9. The build + publish flow end-to-end

Once the pack is ready, the publish flow is six steps. The full workflow lives in [`pack-distribution.md`](./pack-distribution.md) and [`../../registry/publishing.md`](../../registry/publishing.md); this is the shape of it.

```bash
# 1. Final validation — strict mode turns warnings into errors
selran pack validate --full --strict ./my-pack/

# 2. Build the distribution archive and the SHA-256 checksum
selran pack build ./my-pack/

# 3. Sign the archive with your ed25519 private key
selran pack build ./my-pack/ --sign ~/.selran/private.pem

# 4. Inspect the artifacts the build step produced
ls -la ./dist/
# my-pack-0.1.0.tar.gz
# my-pack-0.1.0.tar.gz.sha256
# my-pack-0.1.0.tar.gz.sig

# 5. Dry-run the publish before the real one
selran pack publish ./dist/my-pack-0.1.0.tar.gz --dry-run

# 6. For Selran's canonical registry: open a PR adding your entry to registry/index.json.
#    For self-hosted registries: upload the archive plus the .sig to your CDN and update
#    your own index.json.
```

The signing step is optional for self-hosted packs and required for packs submitted to the canonical registry. Keep your private key out of your repo and out of your shell history.

## 10. Common pack-authoring anti-patterns

Twelve mistakes new pack authors make. Each is common enough that the validator, the reviewers, or the ecosystem will catch it — fixing it yourself first is cheaper.

1. **Bundling core-skill files in the pack.** Packs extend the core; they do not ship copies of it. If your pack archive contains `skill.md` or anything under `references/` that is not yours, strip it.
2. **Over-overriding the base direction.** If `pack-overrides/technical-minimal.yaml` redefines forty tokens, the overlay is doing more work than a new direction would. Either trim it or author a new direction (advanced, rare).
3. **Hardcoded hex values in component CSS.** Every color travels through a token. If a color cannot justify a token, it does not belong in the component.
4. **Components that require JavaScript to render.** Selran pack components render standalone in a browser without a build step. No React, no Vue, no bundler. Static HTML with embedded CSS and inline SVG.
5. **Reference doc that reads like marketing copy.** The reference doc is a craft document. "Our pack empowers teams to unlock synergies across verticals" is a failure mode, not a selling point.
6. **Voice guide with no forbidden-phrases section.** The forbidden-phrases section is often the first thing buyers read. It IS the voice guide in concentrated form.
7. **Pack name that is too long or too clever.** `enterprise-b2b-saas-platform-dashboard-pack` is a bad name. `saas-b2b` is a good name. Your pack name will appear in every CSS class you ship.
8. **Missing LICENSE file.** Every pack ships an explicit LICENSE file with full text at the root. The `license:` field in `pack.yaml` alone is not sufficient.
9. **CHANGELOG without the current version.** If `pack.yaml` says `version: 0.1.0` and `CHANGELOG.md` has no `## [0.1.0]` entry, validation fails. Keep them in sync on every release.
10. **Components without `:focus-visible` or `prefers-reduced-motion`.** Baseline accessibility is not a pack-author judgment call.
11. **A pack that tries to serve every industry or every archetype.** The pack system works because packs are opinionated. A pack that tries to serve everyone serves no one — and a buyer who sees "universal" in a pack description assumes the author has no taste.
12. **Shipping pre-validate failures.** Never publish a pack that `selran pack validate --full` flags with errors. Warnings can be judgment calls; errors cannot.

## 11. When the canonical registry rejects a submission

Selran's canonical registry at `registry.selran.design` is curated. Submissions go through review. Rejection is common for first-time authors and is not a judgment on the author — it is a judgment on the submission against a public standard.

Common rejection reasons:

- The pack violates one or more of the five component-discipline non-negotiables.
- The pack's scope is unclear or overlaps substantially with an existing pack on the registry.
- The reference doc is under 500 words or reads as marketing copy rather than craft guidance.
- The `license:` field in `pack.yaml` does not match the actual LICENSE file text.
- The pack bundles trademarked names (`apple-*`, `stripe-*`) or proprietary assets without documented permission.
- The package includes binary assets — fonts, images — over 500 KB without explicit justification. Font licensing is legally messy; ship font stacks, not font files.

If your pack is rejected, fix the stated issue and resubmit. If you disagree with a reviewer, respond on the PR with your reasoning. Substantive disagreements move to a second maintainer. The canonical registry is curated on purpose; the alternative is self-hosting, which is a first-class path documented in [`../../registry/self-hosting.md`](../../registry/self-hosting.md). Self-hosted packs are not second-class citizens — the lockfile, signature verification, and install flow all work identically.

## 12. Reference links

- [`packs.md`](./packs.md) — pack manifest schema and three-tier layering
- [`pack-distribution.md`](./pack-distribution.md) — CLI, registry, signing, publish
- [`../assets/pack-template/`](../assets/pack-template/) — scaffold that `selran pack init` expands
- [`../../cli/README.md`](../../cli/README.md) — CLI command reference
- [`../../registry/publishing.md`](../../registry/publishing.md) — registry submission workflow
- [`../../packs-official/`](../../packs-official/) — 25 reference packs to study
