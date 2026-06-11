# Getting started

A 10-minute walkthrough from "I just installed Selran" to "I have a designed artifact in front of me." For first-time users.

If you just want the visual direction picker, skip to "Your first session." If you're trying to figure out which surface to start with (claude.ai chat vs. the local `selran` CLI), read "Pick a surface" first.

---

## Pick a surface

Selran has two entry points. They share the same design system, the same packs, the same registry, and the same opinions — but the day-to-day feel is different.

| Surface | When to use | What you get |
|---|---|---|
| **The skill, in claude.ai** | You want to *design* — produce a landing page, a deck, a doc, a brand system. The conversation is the work. | The four-phase arc (elicit → draft tokens → tweak → build) running inside Claude. Visual direction picker, decision-card menus, artifact previews in the right sidebar. |
| **The `selran` CLI, locally** | You're managing packs, signing artifacts, building handoff bundles, working in CI, or operating a self-hosted registry. The conversation isn't the work; the work is in the terminal. | 11 command groups: `pack` / `telemetry` / `enterprise` / `license` / `handoff` / `auth` / `workspace` / `comment` / `reference` / `plugin`, plus the cross-skill subcommands. |

Most users start with the skill in claude.ai. The CLI follows when you need to install a commercial pack, ship a handoff bundle to engineering, collaborate with a workspace, or write a plugin. You don't need both — but they don't fight each other when you do.

---

## Your first session (in claude.ai)

The skill's job in any session is to make **one strong aesthetic choice with you, capture it as a portable design-system file you can edit, and then build whatever artifact you asked for against that file.** The shape is always the same: elicit, draft, tweak, build.

### 1. Open a new chat and ask for something visual

Examples that route into the skill cleanly:

- *"Design a landing page for a fintech product launching next quarter"*
- *"I need a pitch deck for a Series B in cybersecurity. Calm and considered, not pitch-bro."*
- *"Make me a one-page resume — print-first, industry, not academic"*
- *"A poster for a poetry reading at an indie bookstore"*

The shorter your brief, the more the skill will ask. The more specific your brief, the less it asks. Either is fine — the skill caps itself at 2 questions before showing you tokens.

### 2. Pick a direction

The skill renders seven thumbnails as an HTML artifact, one per aesthetic direction:

- `technical-minimal` — Stripe / Linear / Anthropic register
- `editorial` — magazine, considered hierarchy, serif display
- `bold-distinctive` — oversized type, strong color, attention-grabbing
- `warm-approachable` — humanist sans, lifestyle and nonprofit register
- `vibrant-playful` — coordinated multi-hue palette, consumer brand register
- `dark-premium` — luxury, restraint, near-black on near-black
- `brutalist` — raw, anti-corporate, zine-grade

Click the one that feels right. If the brief was specific enough, the skill skips this and goes straight to tokens.

If none feel right, you have two escape hatches. **"Like a specific brand"** — name it ("like Linear's docs but warmer"), and the skill will route into reference-based generation: it ingests the URL, extracts style signals (colors, fonts, spacing — never copy or imagery), and blends them with your brief into a candidate system. **"None of these"** — describe the feeling in your own words; the skill will translate.

### 3. Inspect the design system

The skill drafts a `design-system.md` file with YAML frontmatter (color, type, spacing, motion, personality) and an optional prose section. It shows the file to you — full text — before building anything. Read it. The point is that you see the design as data, not as a black box.

If something's off, say so in plain language: *"the accent feels muddy"* or *"loosen the spacing"* or *"swap to a serif display."* The skill enters the **tweak loop** and surfaces a decision-card menu — three or four side-by-side preview cards, each showing a tiny rendered slice of *that option's outcome*. Pick one, or click "Something specific" to free-text.

### 4. Build the artifact

When you're happy, click **"Looks good — build it."** The skill produces the actual artifact (landing page HTML, slide deck, PDF, doc, poster) using the tokens exactly as they are. Build is a transform, not another design step. It also runs an anti-patterns audit silently — if something hits a default-AI red flag (Inter, purple-pink gradient, rainbow headings), the skill substitutes and tells you what it changed.

### 5. Defend on challenge (only if you push back)

After delivery, the skill asks one short thing: *"Anything off?"* If you say no, that's it. If you say yes — *"the hero feels flat"* — the skill explains the choice in one sentence, offers 2–3 concrete paths forward, and waits. It doesn't preemptively defend choices; it explains only when challenged.

---

## What to do next

A few common follow-ups on the same project:

- **"Now make me a pitch deck for the same product."** The skill inherits the same design-system.md across artifacts — UI, doc, deck, poster — without re-eliciting. Briefly confirms ("Using the same design system — ok?"), then builds.
- **"Show me a preview before we commit to building."** Pre-build sanity check via the decision-card surface — one card showing the about-to-ship slice.
- **"Export the tokens for engineering."** Default emits CSS variables, Tailwind config, Style Dictionary, Figma Tokens JSON, iOS, Android, and plain JSON — all in one go. Drop the export into `exports/` next to your `design-system.md`.
- **"Hand this off to my engineering team."** Use `selran handoff bundle` (Phase 20) to produce a deterministic signed zip with the design system, all 7 token-export formats, the preview HTML + PNG, and the relevant assets. Engineering verifies with `selran handoff verify <bundle.zip>`.
- **"My team needs comments on this."** Move from solo to workspace via `selran auth login` + `selran workspace create` (Phase 21). See [`migrating-from-solo.md`](./migrating-from-solo.md).

---

## Common first-session questions

**Why does the skill keep asking me to pick? Can it just choose?**
Picking is the user's job. Even when the skill could infer a direction from your brief, it asks — because design choices that don't have your fingerprint on them get reverted later. The cap is 2 questions before tokens; not 0.

**Why does the design system live in markdown? I expected JSON.**
Markdown with YAML frontmatter is editable, diffable, reviewable, and human-readable. JSON is a downstream export (run `selran-design-director`'s token export workflow to get any of seven JSON-shaped formats). The source-of-truth being markdown means you can hand-edit it, send it to a colleague, or commit it to your repo without a tool.

**Can I bring my own brand colors and fonts?**
Yes. Either type them in the brief (*"primary color is #8B1A28"*) or hand-edit the `design-system.md` file the skill produces. The skill respects "direct edits win; regeneration only fills in unedited fields."

**Can I use this with my company's existing design system?**
Two ways. (1) If your system is documented as tokens, write a `design-system.md` that mirrors it and the skill will build against it. (2) If your system has Figma Variables, use the Figma bidirectional sync workflow — it reads your Variables back into `design-system.md`, or pushes markdown tokens to Figma. See `references/figma-sync.md`.

**Do I need an account?**
No. The skill and the CLI work for solo users without any auth or workspace. Auth + workspaces (Phase 21) are opt-in, for teams. License-gated commercial packs require either an env var with the license key or a `~/.selran/.pack-licenses.yaml` acknowledgment — both work without an account.

---

## Cross-references

- [`SKILL.md`](../SKILL.md) — the formal four-phase contract the skill follows
- [`references/aesthetic-directions.md`](./aesthetic-directions.md) — full spec of the seven directions
- [`references/direction-starters.md`](./direction-starters.md) — the per-direction starter token sets the visual picker uses
- [`references/decision-cards.md`](./decision-cards.md) — the multi-choice surface used for tweaks, reference confirmation, and pre-build checks
- [`references/anti-patterns.md`](./anti-patterns.md) — what the skill refuses to ship by default
- [`references/packs.md`](./packs.md) — installing and using packs
- [`references/reference-blending.md`](./reference-blending.md) — the "make it feel like X" workflow
- [`references/migrating-from-solo.md`](./migrating-from-solo.md) — moving from solo CLI to workspace
- [`references/authoring-your-first-plugin.md`](./authoring-your-first-plugin.md) — building a plugin
