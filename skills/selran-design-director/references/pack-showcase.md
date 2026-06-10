# Pack showcase (v3.4+)

## 1. Why the showcase exists

The ecosystem has quiet, high-quality packs that potential authors never see. A thoughtful MIT seed pack with a 1,600-word reference doc and a meticulous overlay can sit next to a commercial pack with ten times the marketing surface and show up nowhere on the list a user scrolls. Install-count is a lagging indicator. It rewards momentum and distribution, not craft.

Phase 11 closes that gap with a once-a-year curated event. A public rubric, an accountable three-person panel, a separate track for free work, and six categories that isolate the things the ecosystem actually cares about: reference-doc substance, overlay coherence, component discipline, fit to category. The winners' page becomes a durable reference for new authors — a concrete model of what "good" looks like in this ecosystem, not an abstraction.

This is editorial curation, not analytics. It is judgment, not optimization. The showcase is deliberately small, deliberately slow (one cycle a year), and deliberately opinionated. It does not try to be a leaderboard.

## 2. What the showcase is not

- Not a popularity contest. Not install-count based. The aggregator data from Phase 10 is not consulted during judging.
- Not a marketing campaign for commercial packs. MIT packs compete on equal footing in five of six categories, and the sixth category is MIT-only.
- Not lifetime achievement. Each year's judgment is based on each year's rubric applied to that year's eligible field. A pack that won in 2026 must earn the 2027 citation on 2027's terms.
- Not a replacement for telemetry. Phase 10 telemetry is for pack authors learning from real usage. The showcase is for the ecosystem rewarding craft. The two systems do not share data.
- Not a permanent ranking. The rotation rule — no pack wins the same category two years running — keeps the field fresh and prevents calcification.

## 3. The calendar

Each cycle runs the same twelve-month cadence. Dates are fixed; the rubric is adjusted at most once a year during the December judging window.

- **June 1 — nominations open.** The window opens on the first day of June. Nominations received before June 1 are held for the current cycle; they are not dropped.
- **October 31 — nominations close.** The five-month window gives authors time to ship a v0.2.0 depth pass if they want one, and gives third-party nominators time to write a specific, cited submission rather than a rushed one.
- **November 1 – December 14 — judging.** Six weeks of internal panel review. This is the longest phase on purpose — the panel reads every reference doc end to end and inspects the component CSS directly.
- **December 15 — winners announced.** Publication of the winners' page and the panel citations. Badges become available the same day.

Today's date is 2026-04-23. The 2026 cycle is pre-nomination. No submissions are being accepted yet; the nomination window opens June 1. Drafting a submission now is fine. Opening a PR now is premature.

## 4. The six categories

The categories are stable across years. Full per-category rubrics live in [`../../showcase/categories.md`](../../showcase/categories.md); per-sub-criterion scoring weights live in [`../../showcase/judging-rubric.md`](../../showcase/judging-rubric.md). Short prose for each:

**Pack of the Year.** Overall excellence across every criterion. The pack most worth installing this year, full stop. What wins: a balanced showing — no category-best scores, but no weak spots either, and a pack the panel would actually reach for.

**Best Reference Doc.** Depth, opinionatedness, forbidden-phrases lists, reference-brand analysis. What wins: writing that teaches — 2,000 words that say something specific, not 2,000 words of generic pattern-chat.

**Best Component Discipline.** CSS scoping, token fallbacks, accessibility, anti-patterns avoided. What wins: a pack whose components read well under direct inspection — clean scoping, sensible fallbacks, real `:focus-visible` and `prefers-reduced-motion` behavior, no AI-slop defaults.

**Best Free / MIT Pack.** MIT-only track. What wins: a pack that earns its place in the ecosystem as a gift, not a loss-leader — an MIT pack the panel would recommend on its own merits, not because it is free.

**Most Impactful New Pack.** First-year packs only. What wins: a pack that changes the conversation in its vertical or archetype — someone looks at it and says "oh, that is how this is supposed to be done."

**Best Overlay.** Tokens-only track. Judged on `pack-overrides/<direction>.yaml` quality alone. What wins: an overlay that demonstrates the "only override what differs" principle — surgical, coherent, and worth more than the sum of its lines.

## 5. Nomination mechanics

Two paths. Both are supported equally.

- **Markdown file PR.** Fork the repo, fill in `showcase/submission-template.md` under `showcase/<year>/submissions/<pack-name>.md`, open a pull request. This path is preferred for detailed nominations that cite passages and sections.
- **GitHub issue.** Use the `showcase-nomination` issue template at `.github/ISSUE_TEMPLATE/showcase-nomination.md`. The issue is triaged into the submissions folder by a maintainer. This path is preferred for short, focused nominations.

Eligibility criteria:

- The pack lives in the canonical Selran registry, or in a self-hosted registry that publicly advertises showcase-eligibility.
- The pack is at version 0.1.0 or higher. Pre-release packs (0.0.x) are not eligible — the version floor exists because packs below 0.1.0 have not committed to their shape yet.
- The pack has been publicly available for at least 60 days before nominations close. A pack published on September 1 is eligible; a pack published on September 15 is not.
- Self-nominations are allowed and not disadvantaged. Authors often know their pack's best angle better than a third-party nominator does.
- Third-party nominations are allowed and welcome.

A strong nomination is specific, component-level, and cites passages by section number or line. "The reference doc's Section 4 — the forbidden-phrases list — is the tightest I have seen in a healthcare pack; see lines 112–141 for the pattern." A weak nomination is "this pack is great, everyone I know loves it." The former gets the panel's attention; the latter gets filed and does not meaningfully move the needle.

## 6. Judging

The panel is three people: two Selran maintainers and one rotating invited community judge. Panel composition is announced roughly two weeks before nominations open each year. The community judge changes every cycle; the maintainer seats rotate less often but are published in advance.

Judging proceeds in the priority order published on the shared contract:

1. **Discipline adherence.** This is the mechanical baseline. Every nominated pack runs through `selran pack validate --full` and any pack with hard failures is disqualified at the audit stage before human judgment begins. The audit is automated, repeatable, and independent of the panel.
2. **Reference-doc substance.** Read end to end, scored against the rubric in `showcase/judging-rubric.md`. Depth, specificity, forbidden-phrases quality, reference-brand analysis.
3. **Token overlay coherence.** Every overlay file is inspected. Over-override is penalized. Surgical overlays with clear intent score higher than sprawling ones.
4. **Component architecture.** CSS is inspected directly. Scoping, fallbacks, accessibility states, and anti-patterns all contribute. Rendering the components in a browser is part of the pass.
5. **Documentation completeness.** CHANGELOG, LICENSE, README, nomination template. Present and accurate beats present and neglected.
6. **Fit to category.** Does the pack belong in this category at all? An excellent industry pack nominated for Best Overlay may not place.

Scoring is 0–5 on each sub-criterion, weighted to a total out of 100. Weights are published in [`../../showcase/judging-rubric.md`](../../showcase/judging-rubric.md) and do not change mid-cycle.

Recusal is required for any pack a panelist authored, contributed to, or has financial interest in. Recused votes are dropped from the scoring pool, not zeroed. A two-person decision on a recused pack is documented as such in the citation.

Tiebreaker rules: within 2 points of each other, the panel picks by (a) fit to category, (b) teaching value, (c) MIT-or-commercial preference as defined per category (MIT is preferred in Best Free/MIT Pack; neither is preferred in the general categories).

## 7. The MIT/commercial balance

This section is explicit. The ecosystem has both MIT seed packs and commercial packs, and the showcase treats them with intent.

In five of the six categories — Pack of the Year, Best Reference Doc, Best Component Discipline, Most Impactful New Pack, Best Overlay — MIT packs and commercial packs compete on equal footing. There is no "commercial pack tier" and no "MIT pack tier" for general categories. The rubric measures craft. The rubric does not measure price.

The sixth category — **Best Free / MIT Pack** — is MIT-only. This asymmetry exists on purpose. MIT authors do not compete on component-count because they do not sell; a category that insists on "at least seven components at v0.2.0" rewards the commercial economics of an industry pack, not craft. The MIT-only track guarantees that an excellent free pack has a clear path to recognition regardless of what commercial packs ship in the same year.

Commercial packs cannot win Best Free / MIT Pack. That is the only asymmetry in the rules.

In all five general categories, an MIT pack winning is a feature, not a bug — it signals that the rubric measured what it claimed to measure (craft) and not what it did not claim to measure (commercial polish). The panel is instructed to resist the reflex to "balance" winners across license tiers. A year in which MIT packs win four of five general categories is a year the rubric worked.

The rule exists because install-count-based showcases inevitably favor commercial packs with marketing budgets. Selran's showcase explicitly protects the free tier from that dynamic. An MIT pack that would lose an install-count contest 50-to-1 can still win Best Reference Doc outright if the writing is better.

## 8. What winners get

- **A citation on the year's winners page.** 150 to 200 words of panel commentary per category, written after the decision is final. Citations are specific — they quote the reference doc, name the overlay tokens, cite the components. Published at `showcase/<year>/winners.md` in the repo and at `https://selran.design/showcase/<year>/` on the canonical site.
- **A `showcase_winners` entry in the registry record.** The registry schema supports this field; see [`../../registry/schema/pack-entry.schema.json`](../../registry/schema/pack-entry.schema.json). A pack's entry carries `showcase_winner: true` plus a `showcase_wins: [{year, category}]` array that accumulates across cycles.
- **Skill-side surfacing.** When a user asks the core skill "which packs are worth using?" or "show me the best packs," the skill consults the `showcase_wins` field and surfaces up to five winners with a one-line citation each.
- **A showcase badge SVG.** Served from `https://selran.design/showcase/badges/<year>-<category>.svg` (forthcoming). Authors embed the badge in their pack's README. The badge art is plain, small, and dateable — this year's badge does not pretend to be forever.

Nothing else. No cash prize. No contracts. No exclusivity, no paid placement, no sponsored slots. The prize is the citation and the signal. The showcase is not a revenue stream for Selran; it is a governance mechanism for the ecosystem.

## 9. Skill-side behavior

The core skill is aware of the showcase and changes its responses in a few specific situations. The reference doc that drives the behavior is this file.

- **"Which packs are worth using?" / "Show me the best packs" / "Recommended packs."** The skill loads current and past winners' lists from `showcase/<year>/winners.md`, filters to the user's domain if it can be inferred from context (fintech project in flight → prefer fintech-tagged winners), and surfaces up to five with a one-line citation each. Winners from older years are marked with the year.
- **"Nominate my pack for showcase."** The skill loads this file's Sections 5 and 6, walks the user through the submission template from `showcase/submission-template.md`, and produces one of two outputs: the pre-filled GitHub issue URL ready for click, or a draft of `showcase/<year>/submissions/<pack-name>.md` ready for a PR branch. If the current date is outside the nomination window, the skill says so and asks whether the user wants to save the draft for the next cycle.
- **"Is showcase season?"** The skill compares today's date to the timing contract and reports the current window. Today (2026-04-23) the answer is "pre-nomination; nominations open June 1."
- **"Why did pack X win?"** The skill loads the relevant winners' page and quotes the panel citation verbatim. It does not paraphrase. The citation is the answer.

The skill does not run audits, does not simulate judging, does not predict winners. It reads the record and relays it.

## 10. Historical archive

Winners are archived permanently in `showcase/<year>/winners.md` for every cycle. Past-year pages are never edited after publication, with one narrow exception: errata. A factual error — a misspelled pack name, an incorrect author attribution, a broken link — is fixed with an Errata block at the top of the page, not by rewriting the citation body.

Later-year reinterpretation is deliberately out of scope. The panel judged the 2026 field against the 2026 rubric with the 2026 eligibility cutoff. A 2029 reader who disagrees with the 2026 decision is welcome to say so publicly; the archive does not retroactively relitigate. The rubric is refined year over year, and the record shows which rubric applied to which decision.

## 11. FAQ

**Can a pack win the same category two years in a row?** No. The rotation rule excludes last year's winner from this year's same category. Different categories are fine.

**Can a pack win multiple categories in the same year?** Yes. There is no cap. A pack can win Pack of the Year and Best Reference Doc and Best Overlay in the same cycle if it genuinely earns them.

**Are commercial packs favored?** No. Five of six categories are open to both MIT and commercial packs with no asymmetry in the rubric. The sixth category is MIT-only, which is an asymmetry in favor of MIT.

**Can self-hosted registries participate?** Yes, if the registry publicly advertises showcase-eligibility and the pack otherwise qualifies. The panel verifies that the registry's index is reachable and that the pack entry meets the eligibility criteria.

**What if my pack is v0.0.9 at nomination-close?** Not eligible. The 0.1.0 floor exists because pre-release packs have not committed to their shape yet. Ship 0.1.0 before October 31 and the pack is in next year's field regardless.

**Can the panel be challenged?** There is no formal appeals process. The governance mechanism is rotation: next year's panel is different people. Substantive disagreement lands in the rubric revision for the following cycle, not as an overturn of the current cycle.

## Related
- [./packs.md](./packs.md) — Pack-system spec
- [./pack-distribution.md](./pack-distribution.md) — CLI + registry
- [./pack-authoring.md](./pack-authoring.md) — Authoring guide
- [./pack-telemetry.md](./pack-telemetry.md) — Opt-in usage telemetry
- [../../showcase/README.md](../../showcase/README.md) — Showcase infrastructure
- [../../showcase/categories.md](../../showcase/categories.md) — Category definitions
- [../../showcase/judging-rubric.md](../../showcase/judging-rubric.md) — Scoring rubric
- [../../registry/schema/pack-entry.schema.json](../../registry/schema/pack-entry.schema.json) — Registry fields (showcase_wins)
