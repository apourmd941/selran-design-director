# Selran Design Director

A Claude skill that acts as a design director — asks the right questions, locks a design system, then executes across web UIs, native apps, documents, presentations, posters, and email.

Released by [Selran](https://selran.com). MIT licensed. **Current version: 3.16.1** — by **Aidin Eslampour**.

> Looking for install instructions and the plugin overview? See the [project README](https://github.com/apourmd941/selran-design-director#readme).

## Why this exists

Most AI design skills either (a) auto-pick styles and lock you out of the decision, or (b) give Claude vague "good taste" instructions and hope for the best. Neither puts the user in control at the moment it matters.

This skill is different:

- **Asks 1–2 tappable questions** before writing code — or shows a visual picker of seven pre-audited starter designs, adapting to what the host can render (inline options, option previews, or a browser-tab picker page; see SKILL.md § capability ladder)
- **Skips questions the brief already answered** — no repetition
- **Generates a `design-system.md` file** you can inspect and edit before anything is built
- **Builds against that file** so UI, docs, decks, and posters share one voice
- **Enforces an anti-slop checklist** — no Inter-as-default, no purple-to-pink gradients, no rainbow headings, no emoji icons, no Corporate Memphis, no generic shadcn defaults
- **Portable across formats** — same tokens drive your web UI, your PDF whitepaper, and your pitch deck so they feel like siblings
- **Extensible via an optional pack system** — the core stays MIT; commercial packs (industry-specific or brand-archetype-specific) plug in without modifying core files

## What it covers

- **Web UIs** — React, HTML, Tailwind, landing pages, dashboards, components
- **Native apps** — iOS (SwiftUI) and Android (Jetpack Compose / Material 3)
- **Mobile web** — responsive treatments of the 20 most-common web components
- **Documents** — Word (.docx), PDFs, reports, whitepapers
- **Presentations** — PowerPoint (.pptx), HTML decks, pitch decks
- **Posters & static visuals** — flyers, social cards, single-image deliverables
- **Email** — MSO/Outlook-compatible HTML with VML buttons
- **Favicons + OG images** — per-direction templates

## Seven aesthetic directions

Each ships as a pre-audited starter (WCAG AA) with light + dark tokens, components, motion, charts, imagery prompts, favicon, OG image template, and exports.

| Direction | Feel | Best for |
|---|---|---|
| **technical-minimal** | Sharp, systematic, Stripe/Linear-adjacent | Developer tools, B2B SaaS, dashboards |
| **editorial** | Calm, considered, magazine-style serifs | Whitepapers, long-form, finance, law |
| **dark-premium** | Luxury, restrained, high-contrast dark | Fintech, AI tools, premium brands |
| **warm-approachable** | Human, soft, approachable sans | Nonprofits, wellness, lifestyle |
| **vibrant-playful** | Alive, coordinated 5-hue palette | Consumer brands, creative, marketing |
| **brutalist** | Raw, zine-like, high-tension typography | Portfolios, culture, counter-design |
| **bold-distinctive** | Confident, graphic, memorable | Launches, campaigns, brand reveals |

## Pack system (v3.4)

The core is MIT. Packs are optional add-ons that plug in without modifying core files — new components, shells, references, illustrations, or per-direction token overlays. Packs can be MIT, Apache, CC-BY, or Commercial, each with its own LICENSE that travels with the pack.

Opt into a pack's overlay in `design-system.md`:

```yaml
direction: "technical-minimal+fintech"
```

The core resolves `technical-minimal` as the base and shallow-merges the fintech pack's overlay on top. Users can also reference pack components by name during a build.

**25 packs ship today** in the repo's [`packs-official/`](../packs-official/) directory:

- **Wave-1 Industry** (5 × \$49): fintech, healthcare, saas-b2b, developer-tools, e-commerce
- **Wave-1 Archetype** (7 × \$29): challenger, luxury, mission-driven, tech-forward, heritage, playful-consumer, quiet-professional
- **Wave-2 Industry** (5 × \$49): legal-tech, edtech, real-estate, nonprofit, gov-civic
- **Wave-2 Archetype** (5 × \$29): academic, activist, sophisticated-casual, hospitable, technical-educator
- **Free/MIT Seed** (3 × \$0): oss-project, personal-blog, resume-cv

To author your own pack, copy the scaffold at [`assets/pack-template/`](./assets/pack-template/) and fill it in. See [`references/packs.md`](./references/packs.md) for the canonical spec and [`references/pack-authoring.md`](./references/pack-authoring.md) for the full authoring guide.

## Pack distribution, ecosystem, and licensing (v3.4)

As of v3.4, the pack system is a full ecosystem:

- **`selran` CLI + registry** — `pip install selran-cli` (Phase 15), then `selran pack install <name>` installs with ed25519 signature verification against a JSON-indexed registry at `https://registry.selran.design/`; `selran-packs.lock.yaml` pins versions for reproducible CI; self-hosted registries are first-class. See [`cli/README.md`](../cli/README.md), [`references/pack-distribution.md`](./references/pack-distribution.md), and [`registry/deployment.md`](../registry/deployment.md) for the hosting contract.
- **Pack-author tooling** — `selran pack init` scaffolds interactively, `selran pack validate --full` runs four audit modules, `selran pack build --sign` produces signed archives. See [`references/pack-authoring.md`](./references/pack-authoring.md).
- **Opt-in telemetry** — local-first, default-OFF; MIT packs never record; no data leaves the machine without explicit `selran telemetry upload`; k-anonymity floor of N=10 at the aggregator. See [`references/pack-telemetry.md`](./references/pack-telemetry.md).
- **Annual Pack Showcase** — once-a-year editorial curation across six categories; winners get citation + `showcase_wins` registry entry + badge SVG; MIT + commercial compete on equal footing (with one MIT-only track). See [`references/pack-showcase.md`](./references/pack-showcase.md).
- **Enterprise tier** — Standard + Premium licenses for multi-org use, agency carve-out, private-pack hosting, SLA commitments; offline ed25519 license verification; license keys format `selran-ent-<org>-<tier>-<32-base32>`. See [`references/pack-enterprise.md`](./references/pack-enterprise.md).
- **Cross-skill integration** — packs become consumable by three sibling Selran skills (`selran-marketing-copy`, `selran-email-campaign`, `selran-landing-page-critic`) via a structured pack slice exported by `selran pack export-for-skill`. One pack investment pays dividends across the whole skill family. As of Phase 17, each companion skill ships its own publication scaffolding (release workflow, VERSION, smoke tests) and the `schema_version: "1.0"` pack-slice freeze is mechanically enforced by CI. See [`references/cross-skill.md`](./references/cross-skill.md).
- **Commerce + licensing ops** — Stripe Checkout for 22 commercial packs + 2 enterprise tiers, HMAC-verified webhook dispatch, maintainer-only `selran license {issue-pack, issue-enterprise, revoke, rotate}` subcommands, and an offline-checked revocation list. User-facing purchase flow, delivery SLO, refunds, and renewals at [`references/pack-commerce.md`](./references/pack-commerce.md); operator-side infrastructure under [`commerce/`](../commerce/).

## Capabilities at a glance

- **Visual direction picker** — seven thumbnails on a fresh request; pick one to skip the text-question flow
- **Brief parser** — long briefs (200+ words) extract direction signals automatically, skipping elicitation when confidence is high
- **Component library** — canonical components (button, card, table, form, nav, modal, tag) with direction-specific adjustments for all seven directions
- **App foundations** — shells, screen states (loading/empty/error/success), wizard flows, composition rubric
- **Multi-brand inheritance** — a parent design system with per-product forks that honor shared tokens
- **A/B variants** — ask for 3 hero options, pick one; variants are tracked
- **Voice guide** — copy tone per direction so the words match the visuals
- **Palette picker** — coordinated 3-to-8 hue palettes for artifacts that need chromatic variety
- **Direction fusion** — axis-by-axis controlled borrowing across directions (`technical-minimal + editorial-display + warm-color`) with coherence guards and pre-blessed pairings
- **Live iteration** — point at the rendered artifact in plain language ("the hero feels flat"); the skill translates to concrete token moves and re-renders
- **Figma bidirectional sync** — read Figma Variables/Styles into `design-system.md` or push markdown tokens to Figma via the REST API (with Tokens Studio fallback for non-Enterprise)
- **Visual preview** — render the design before committing to build
- **Three-variation explorer** — three forks of the current direction, rendered as thumbnails alongside the original
- **Token export** — CSS custom properties, Tailwind config, Figma Tokens JSON, Figma Variables, Style Dictionary, iOS (Swift), Android (XML)
- **Accessibility audit** — silent WCAG AA audit on every fresh design system; explicit audits on demand; auto-fix via HSL lightness stepping
- **Performance budgets** — LCP/CLS/INP targets per artifact, font-cost tables, image optimization, CLS prevention
- **Theme versioning** — snapshot, fork, diff, revert
- **Illustration system** — per-direction SVG spot libraries + AI prompts for generating more
- **Internationalization** — RTL mirroring, font fallback chains for CJK/Arabic/Devanagari/Hebrew/Thai/Cyrillic, copy-length expansion factors per language

## Installation

### Claude Code (CLI)

Install from the `selran` marketplace:

```
/plugin marketplace add apourmd941/selran-devloop
/plugin install design-director@selran
```

Or install the skill manually from the public repo:

```bash
git clone https://github.com/apourmd941/selran-design-director
cp -r selran-design-director/skills/selran-design-director ~/.claude/skills/
```

Restart Claude Code. The skill auto-triggers when you ask for design work.

### Claude.ai (web)

Zip `selran-design-director/`, then go to `claude.ai` → Settings → Capabilities → enable "Code execution and file creation" → Customize → Skills → "+" → "Create skill" → upload the zip.

## How to use it

Just ask Claude to build something visual. The skill triggers automatically.

**Example prompts:**

- `build me a landing page for a developer tool`
- `design a pitch deck for our Series A raise`
- `create a quarterly investor report as a PDF`
- `make a dashboard for healthcare analytics`
- `design a poster for our launch event`
- `build an onboarding email`
- `make an iOS app for this product`

Claude will tap 1–2 questions (or zero if your brief was clear enough), generate `design-system.md`, show it to you, open a tweak loop, then build the artifact using those tokens. Follow-up requests in the same project reuse the same tokens.

After the design system exists, you can also ask for any on-demand workflow:

- *"show me a preview"* — visual render before building
- *"show me some variations"* — three forks of the current direction
- *"export the tokens"* — emits all formats (CSS, Tailwind, Figma, Style Dictionary, iOS, Android)
- *"check accessibility"* — WCAG audit with auto-fix offers
- *"save this as v2"* / *"revert to original"* — theme versioning
- *"make it mobile"* — responsive treatment
- *"build it for iOS"* / *"make it Android"* — native app rendering
- *"sync with Figma"* — bidirectional Figma Variables sync
- *"what packs do I have?"* — list installed packs
- *"install the fintech pack"* / *"publish my pack"* — CLI-driven pack distribution (v3.4+)
- *"enable pack telemetry"* / *"export my events"* — opt-in local-first telemetry (v3.4+)
- *"which packs are worth using?"* / *"nominate my pack"* — annual showcase (v3.4+)
- *"do I have an enterprise license?"* / *"set up a private registry"* — enterprise tier (v3.4+)
- *"export this pack for marketing-copy"* / *"which packs work cross-skill?"* — cross-skill consumption (v3.4+)

## Philosophy

Three things this skill believes that most design tools don't:

1. **The user should choose direction, not the tool.** Auto-picking "good taste" is a dodge.
2. **Consistency beats novelty.** A deck, a doc, and a web page sharing tokens *is* the brand.
3. **Anti-slop is a checklist, not vibes.** The "AI-generated design" problem gets solved with enforced rules at every layer — fonts, gradients, icons, imagery, layout, copy — not with a vague preamble.

## License

MIT — use it, modify it, share it, ship it with your own products.

## Contributing

Issues and PRs welcome on the repo. The reference files are the main place to contribute — new directions, font suggestions, additional anti-patterns as they emerge.

## Credits

Created by [Aidin / Selran](https://selran.com).
