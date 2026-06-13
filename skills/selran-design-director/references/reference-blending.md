# Reference blending — the "make it feel like X" workflow

This file explains the user-facing flow for ingesting a reference (a URL or a screenshot) and turning it into a starting point for your `design-system.md`. It's the partner to `references/aesthetic-directions.md` (where the seven directions live) and `references/anti-patterns.md` (where the substitution rules come from). The operator/contributor-facing version of this same flow — with the conflict-resolution algorithm and the consensus rules — is at `services/references/blending-rules.md`.

## What this is

A way to say "make it feel like Linear's docs site, but warmer," or "blend Stripe's motion timing with Apple's restraint, in a finance product context," and have the skill produce a candidate `design-system.md` you can edit.

Reference blending is **optional**. The original four-phase arc — elicit, draft, tweak, build — works the same way it always did. Phase 1's visual picker and text-anchor questions are still the default path into a design system. Reference blending is an alternative entry that some people prefer when:

- They have a brand they want to feel adjacent to (without imitating)
- They have a screenshot of a layout they liked and want to extract its rhythm
- They're starting from a brief like "premium developer tooling, in the spirit of Linear and Stripe"
- They want a candidate design system to react to instead of a fresh draft

If none of those describe you, skip this file. The skill will still elicit a brief, surface the seven directions, and produce a starter — same as before.

## When to use it

Use reference blending when you have a concrete external reference in mind. If your brief is fully verbal ("warm, considered, slightly editorial"), the original flow is faster — the picker question is calibrated to map verbal briefs to directions and you'll spend less time describing references than just looking at the picker.

Use the original flow when:

- You don't have a specific brand or layout in mind
- You're working in a domain where the seven directions cover your needs without external prompts
- You're in a closed-network environment where outbound URL fetches aren't possible
- You have privacy constraints that make ingesting any external page inappropriate

Use reference blending when:

- You can name a brand or a URL whose feel you want to start from
- You have screenshots saved that capture the look you want
- Your brief is "X but warmer" or "X meets Y" or "the spacing of X with the type of Y"
- You want to see "what would it look like if we started from this reference?" as one of several options

Both paths can be mixed. Many users start with the original elicitation, look at the candidate, decide the candidate is missing something specific, and then ingest a reference to push the candidate in that direction. The two paths are complementary, not exclusive.

## How the flow works

The end-to-end flow is six steps. You see them in your terminal when you run the commands.

```bash
# 1. Ingest a URL or a screenshot.
selran reference ingest https://example.com/notebook-app

# Output:
# Queued ref_01HW0XVZ8NM3K9F7B6Q4D2RTYE
# Polling... ready in 12s.
# Top three directions: technical-minimal (0.71), warm-approachable (0.30), bold-distinctive (0.15)
# Caveats: INTER_DETECTED, RADIUS_DEFAULT_DETECTED
```

```bash
# 2. (Optional) Look at the summary.
selran reference show ref_01HW0XVZ8NM3K9F7B6Q4D2RTYE

# Output:
# Reference: ref_01HW0XVZ8NM3K9F7B6Q4D2RTYE
#   url:        https://example.com/notebook-app
#   fetched_at: 2026-04-26T12:00:00Z
#   trademark_safe: True
#
# Top three directions:
#   technical-minimal       0.71  geometric-sans display + tight tracking + 8px-grid + ...
#   warm-approachable       0.30  ease-out/spring motion + warm bright bg
#   bold-distinctive        0.15  high-contrast triad
#
# Caveats: 2
#   [warn] INTER_DETECTED
#     Body family matches Inter — substituting General Sans per anti-patterns.
#   [warn] RADIUS_DEFAULT_DETECTED
#     Default 8px-everywhere radius detected. Substituting a two-tier radius scheme...
```

```bash
# 3. Blend the reference with your brief.
selran reference blend ref_01HW0XVZ8NM3K9F7B6Q4D2RTYE \
  --brief "calm and considered, for a finance product"

# Output:
# Blended candidate produced. 7 tokens kept; 3 tokens dropped (anti-patterns substituted).
# This is a suggestion — review before adopting.
# Wrote: design-system.md
```

```bash
# 4. Open the candidate in your editor.
$EDITOR design-system.md

# 5. Run the rest of the skill against the candidate.
# (e.g. iterate via Phase 2's tweak loop, render previews, ship.)
```

```bash
# 6. (Optional) Compare against another reference later.
selran reference ingest https://example.com/payments-marketing
selran reference blend \
  ref_01HW0XVZ8NM3K9F7B6Q4D2RTYE \
  ref_01HW0XW2P0KJTC4M8N7Y5ZWQAB \
  --brief "calm and considered, for a finance product"
```

The output of step 3 is a `design-system.md` blob with a populated `derived_from` block in its YAML frontmatter. That block records, for every token in the file, which reference contributed it (or which were dropped, and why). You can read it; you can edit it; you can throw it away and start over. The skill does not enforce that you keep using the blended candidate.

## What the `derived_from` block means

Every blended `design-system.md` has a `derived_from` block in its YAML frontmatter. It looks like this:

```yaml
derived_from:
  brief: "calm and considered, for a finance product"
  target_direction: "technical-minimal"
  references:
    - id: "ref_01HW0XVZ8NM3K9F7B6Q4D2RTYE"
      url: "https://example.com/notebook-app"
      direction_score: 0.71
      tokens_kept:
        - "color.bg"
        - "color.fg"
        - "spacing.base_unit"
        - "spacing.rhythm"
        - "type.scale_ratio"
        - "motion.duration"
        - "motion.easing"
      tokens_dropped:
        - "type.display_class (INTER_DETECTED)"
        - "type.body_class (INTER_DETECTED)"
        - "radius.primary (RADIUS_DEFAULT_DETECTED)"
  decisions:
    - "routed brief 'calm and considered, for a finance product' → technical-minimal direction"
    - "rejected Inter; using General Sans per anti-patterns"
    - "split radius into a two-tier scheme (10 primary / 4 secondary) per RADIUS_DEFAULT_DETECTED substitution"
```

Read it field-by-field:

- **`brief`** is the brief you typed, verbatim. If you didn't supply one, this is `null`.
- **`target_direction`** is the direction the blender picked. If you supplied `--target-direction`, that's what's recorded; otherwise it's the direction with the highest score across the input references, adjusted for your brief.
- **`references[]`** is one entry per reference. Each entry shows the URL, the direction score, the tokens kept (where the reference's signal made it through), and the tokens dropped (where the blender substituted because of an anti-pattern caveat or because another reference outvoted this one).
- **`decisions[]`** is plain-language explanations of the choices the blender made. These are the lines you'd repeat to a colleague to explain *why* the candidate looks the way it does.

The block is **not** decorative. The skill uses it during Phase 2's tweak loop: when you say "make this warmer," the skill knows which tokens came from where, and which substitutions were already made. It can offer a more precise tweak ("warmer means we should revisit `color.accent` — it was substituted from the reference's purple to the direction's default; here are three direction-appropriate warmer options") instead of a generic adjustment.

You can edit the `derived_from` block manually, but you usually shouldn't. If you want to change a kept token, change the token directly and let the block reflect what you actually did. The block is a *log*, not a *plan*.

## Trademark and copyright

This is the most important section in the file. Read it.

The reference ingestion service does not copy brand assets. Specifically, it never stores or persists the source page's HTML, CSS, JS, copy, imagery, logos, or font binaries. What it stores is style signals: hex codes, integer spacing, font family classifications, integer radius values, integer motion durations, plus a 480x320 thumbnail with the source URL plainly visible.

Translation: when you ingest `https://example.com/notebook-app`, the skill is *taking notes about the page's design rhythm*. It is not *copying the page*. The blended candidate that comes out the other end is inspired by the reference's character without imitating its brand.

The blender enforces this through anti-pattern substitution. If the reference's accent is a Stripe purple-pink gradient, the blender drops the accent and substitutes a single saturated accent from your chosen direction. If the reference's body font is Inter, the blender drops the font signal and uses General Sans (or another humanist substitute appropriate for your direction). The substitutions are all logged in `tokens_dropped`, so you can see what was rejected and why.

A few things this means for you in practice:

- **You can ingest a reference even if you don't own the brand.** The skill is taking notes; you're the one who decides what to do with them. Just like reading a magazine for design inspiration: the magazine isn't yours, but the act of being inspired is.
- **You should not pass references through the skill expecting it to clone the source.** It will refuse. If you actually want to clone something, the right tool is a screenshot and a manual transcription — and even that's usually a copyright issue you should think about.
- **The `trademark_safe: true` field on every reference summary is a deliberate audit signal.** It's `true` when the full caveat pipeline ran. If your operator has bypassed any caveats (rare; typically only for ingesting your own brand into a private workspace), the field flips to `false` and the CLI surfaces that.
- **The blended `design-system.md`'s `derived_from` block makes the lineage explicit.** You can show it to a designer or a lawyer and they can see exactly which signals influenced your design system.

The framing the skill uses, internally and externally, is "extracting style signals you can use as a starting point." Not "scraping competitors' designs." The difference matters legally and ethically; it also changes how the output behaves.

## Anti-pattern traps

The reference pipeline mirrors the anti-pattern list in `references/anti-patterns.md` at runtime. Eight caveats can fire on any ingested reference:

- **`INTER_DETECTED`** — body or display family is Inter. Substitution: General Sans for body.
- **`POPPINS_DETECTED`** — body or display family is Poppins. Substitution: DM Sans or Manrope.
- **`ROBOTO_DETECTED`** — body or display family is Roboto. Substitution: Hanken Grotesk or Outfit.
- **`PURPLE_DOMINANT`** — accent is purple in the 250–290° hue range with high saturation. Substitution: a single saturated accent from the chosen direction's palette; never gradient.
- **`APPLE_GLASS_DETECTED`** — Apple SF / system-ui font signature, often with frosted surfaces. Substitution: opaque surfaces from the direction's `color.surface`; humanist sans (General Sans / Manrope) at appropriate weight.
- **`NOTION_GREY_DETECTED`** — `#37352F`-on-`#F7F6F3` palette with near-zero accent saturation. Substitution: `#191919` on `#FAFAF9` plus a single saturated accent.
- **`VERCEL_PURE_BLACK_DETECTED`** — `#000000` background with monospace headings and neon-bright accents. Substitution: `dark-premium`'s `#0A0A0B` (warmer-than-pure-black) with a single accent from the direction.
- **`RADIUS_DEFAULT_DETECTED`** — primary radius is exactly 8 px and applied to every surface uniformly. Substitution: a two-tier radius scheme (primary surfaces get the direction's radius, secondary surfaces get something smaller or square corners).

Each caveat carries a `severity` (`warn` in v1; `block` is reserved for future codes that may need harder enforcement). All v1 caveats are warnings — the blender always substitutes, and you always see what was substituted in `tokens_dropped`.

If you want the original signal preserved despite the caveat (you really want Inter for some reason), you can override after the blend by editing `design-system.md` directly. The skill won't fight you on the second pass; the caveat fired once at ingest time, and you've now seen it. Your call.

## How to override the suggestions

The blender produces a *candidate*. Everything in it is editable. Three common overrides:

### Override the chosen direction

```bash
selran reference blend ref_01HW0XVZ... \
  --brief "premium developer tooling" \
  --target-direction dark-premium
```

This forces the blender to use `dark-premium` regardless of what the reference's scores say. The reference's signals are still pulled in where they're compatible with `dark-premium`; conflicts are dropped.

### Override a specific token after the blend

Edit `design-system.md` directly. The tokens in the YAML frontmatter are the truth; the `derived_from` block is just a log. Nothing about editing the tokens invalidates the block — it's still accurate as a record of what the blender produced.

If you want to keep the block accurate after manual edits, you can re-run `selran reference blend` and the new block reflects your edits. But you don't have to.

### Skip the reference entirely

If the blended candidate isn't what you wanted, you can ignore it and run the original elicitation flow:

```bash
# Discard the candidate, run the standard elicit flow.
selran elicit
```

The reference summary stays in your workspace's reference list (you can use it later, or delete it with `selran reference delete`). The discarded candidate is just a file; deleting it has no side effect.

## FAQ

**Q: Does the reference get re-fetched every time I blend?**
A: No. The reference is ingested once (`selran reference ingest`) and stored as a structured summary. Every subsequent `blend` reads from the stored summary. If the source page changes, you have to re-ingest to pick up the changes.

**Q: Can I blend more than two references?**
A: Up to five per blend. Beyond five, the consensus algorithm starts to wash out and the result trends toward the chosen direction's defaults rather than reflecting any one reference. If you want more than five inputs, ingest them all and run blends in pairs to compare.

**Q: What happens if `robots.txt` disallows my reference URL?**
A: The ingestion fails with `robots_disallowed`. Your operator can bypass this for a specific URL by flipping the `trademark_safe` flag — but that's a deliberate audit signal, not a default. If `robots.txt` says no, the right answer is usually to pick a different reference.

**Q: Can I ingest a screenshot of my own product?**
A: Yes — that's one of the use cases. Pass `--image <path>` instead of a URL. The pipeline runs the same way; the only difference is step 1 (fetch) is replaced with reading the local file.

**Q: Are reference summaries shared across workspaces?**
A: No. Reference summaries are workspace-scoped. A reference ingested in workspace A is not visible to workspace B. This is enforced at the auth layer; cross-workspace lookups return 404 (never 403, to avoid leaking the existence of references).

**Q: Can I delete a reference?**
A: Yes — `selran reference delete <id>`. Admin role only. The summary JSON and the thumbnail are removed; the audit log retains the fact that the deletion happened. There is no undelete.

**Q: How long are references retained?**
A: Failed ingestions are retained for 30 days (so you can see why an old reference id failed). Successful ingestions are retained until you delete them. There is no automatic GC of successful summaries.

**Q: Does this work offline?**
A: Partially. URL ingestion needs a network. Image-upload ingestion does not. The blending step is purely local once the references are stored. If you're in an air-gapped environment, the reference path is not for you; the original elicitation flow is.

**Q: What if my reference is a single-page app that needs JavaScript to render?**
A: The pipeline runs Chromium with full JS execution, waits for `document.fonts.ready`, and then captures the rendered state. SPAs render fine. Pages that gate on user interaction (clicks, form fills) before rendering useful content will produce summaries based on the pre-interaction state, which usually isn't what you want — pick a different page or a screenshot.

**Q: Can I see what the blender saw?**
A: `selran reference show <id>` shows the structured summary. `selran reference inspect <id> --json` dumps the raw JSON. The 480x320 thumbnail is downloadable from the `thumbnail_url` in the summary. The full-resolution screenshot is not stored; only the thumbnail and the extracted signals survive.

**Q: Why doesn't the blender just copy the reference's tokens directly?**
A: Because then the output would be an imitation, not a design system. The whole point is to use the reference as a *starting point* — to extract the rhythm, character, and intent without reproducing the brand. The substitutions for anti-patterns are what make the difference between "inspired by Linear" and "looks like Linear."

## Cross-references

- `references/anti-patterns.md` — the canonical anti-pattern list (the "Reference-imitation traps" section is the user-facing companion to this file's caveat list)
- `references/aesthetic-directions.md` — the seven directions the blender picks from
- `references/voice.md` — copy direction that the blended `design-system.md` will need
- `assets/blended-design-system.md` — a worked example of a blended `design-system.md` with the `derived_from` block populated
- `assets/reference-template.json` — a worked example of a reference summary
- `services/references/blending-rules.md` — operator/contributor-facing version of this same flow, with the conflict-resolution algorithm written out
- `services/references/reference-spec.md` — the HTTP contract for `selran reference ingest` / `blend` / etc.
