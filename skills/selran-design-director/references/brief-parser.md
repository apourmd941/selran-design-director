# Brief Parser

How to extract Phase 1 direction from a long brief instead of asking the visual picker question. When the user has already told you what they want — via audience, tone words, competitor references, domain, constraints — interrogating them with "How should it feel?" is friction. Parse the brief first, then decide whether to skip elicitation, ask one disambiguation question, or fall back to the standard picker.

---

## When to use this reference

Run the parser **before** Phase 1 elicitation when any of these triggers fire:

- The brief is **longer than ~100 words** (roughly: more than three sentences)
- The brief mentions an **audience** explicitly ("for finance ops teams", "targeting creators")
- The brief uses **tone adjectives** ("warm", "restrained", "clinical", "bold")
- The brief names a **competitor or reference** ("like Stripe", "feels like Arc")
- The brief lists **constraints** (accessibility floors, brand colors locked, fonts already licensed, dark mode required)
- The brief specifies **artifact scope** across multiple formats (UI + deck, landing + docs)

Skip the parser and go straight to the visual picker when:

- The brief is short and generic ("build me a landing page", "design a dashboard")
- The brief names an **artifact type only**, no audience or tone ("a pitch deck")
- The user is mid-edit of an existing artifact (Phase 1 is already skipped — see SKILL.md)

The parser is a filter, not a replacement. Its job is to decide between three outcomes: **skip elicitation** (direction is obvious), **ask one disambiguation question** (two directions are live), or **fall back to the picker** (the brief didn't actually narrow anything).

---

## The extraction schema

Pull these fields from the brief. Not every brief will have every field — leave missing ones null.

### `direction_signals`

Words and phrases that point to a specific direction. Scan the brief for these triggers:

| Direction | Trigger phrases |
|---|---|
| **editorial** | considered, slow, craft, literary, long-form, magazine, typographic, restrained, quiet, serif, print, byline, essay, publication, thoughtful |
| **technical-minimal** | enterprise, SOC2, infrastructure, dev tool, API, dashboard, systematic, dense, precise, data-driven, engineering, analytics, B2B, SaaS, developer, technical, clean, CLI |
| **bold-distinctive** | manifesto, launch, statement, editorial fashion, hero, asymmetric, oversized, attention, brand-forward, big, confident, striking, editorial poster |
| **dark-premium** | luxury, premium, high-end, cinematic, arthouse, jewel, metallic, sophisticated, exclusive, flagship, refined, product launch, hero gradient |
| **warm-approachable** | nonprofit, community, lifestyle, wellness, human, cozy, friendly, neighborhood, boutique, hospitality, therapy, soft, caring, warm, inclusive |
| **brutalist** | zine, punk, raw, unpolished, anti-design, manifesto, indie, DIY, archive, directory, scrappy, monospace, HTML-aesthetic, hacker |
| **vibrant-playful** | playful, energetic, bright, consumer, for creators, marketing, engaging, lively, delight, social, Duolingo-like, Mailchimp-like, alive, fun, approachable consumer |

When a phrase maps to two directions, note both — the resolution logic below handles ambiguity.

### `audience`

Who is this for? Normalize to one of: `developers`, `finance-ops`, `executives`, `creators`, `consumers`, `policymakers`, `students`, `clinicians`, `researchers`, `designers`, `nonprofit-donors`, `internal-employees`. If the brief says "marketing team at a mid-market SaaS," that's `internal-employees` + `consumers` (they're building for end users).

### `domain`

What industry or problem space? Fintech, devtools, healthcare, creator-economy, nonprofit, education, gaming, media, e-commerce, climate, legal, real-estate. Leave null if the brief is domain-agnostic.

### `tone_words`

Explicit adjectives from the brief. Quote them verbatim — don't paraphrase. If the brief says "warm but professional," capture both words. These feed into the tone-word dictionary below.

### `competitor_refs`

Named brands, products, or references. "Like Stripe," "feels like Arc browser," "similar to Rauno's portfolio." Capture the name and the preposition (like / similar to / feels like / not like / opposite of).

### `constraints`

Must-haves and must-avoids:
- Accessibility floor (WCAG AA? AAA?)
- Dark mode required?
- Brand colors already picked (hex values)
- Font already licensed (name)
- Required formats (web + pdf + pptx)
- Things to avoid ("no purple", "no gradients", "not Stripe-like")

### `artifact_type`

What's being built: `landing-page`, `dashboard`, `doc`, `deck`, `portfolio`, `report`, `email`, `component`, `poster`. Multi-format briefs get a list.

---

## Direction resolution logic

Walk the decision tree in order. Stop at the first rule that produces a confident answer.

**1. Competitor-named → start from that brand's direction.**
If `competitor_refs` is non-empty, look up the named brand in the competitor map below. Use that direction as the starting point. Confidence: **high** if the brand is a clear fit for one direction, **medium** if it spans two (e.g., Vercel = technical-minimal OR dark-premium — disambiguate on domain).

**2. Explicit unambiguous tone words → map directly.**
If `tone_words` contains words that cluster unambiguously to one direction (see the tone-word dictionary), pick that direction. Confidence: **high**.

**3. Audience + domain → infer.**
If there's no competitor and no explicit tone, use the audience × domain intersection:
- executives + finance → technical-minimal or dark-premium
- developers + devtools → technical-minimal
- creators + creator-economy → vibrant-playful or bold-distinctive
- policymakers + nonprofit → editorial or warm-approachable
- consumers + wellness → warm-approachable
- consumers + gaming → vibrant-playful or bold-distinctive

Confidence: **medium** when the intersection points to two directions, **high** when it's unambiguous.

**4. Still two directions live → ask ONE disambiguation question.**
Do not ask the generic "How should it feel?" Ask the specific question from the ambiguity table below. This is the whole point of parsing — we've already earned the right to ask sharper.

**5. Nothing resolved → fall back to the visual picker.**
If the brief was long but genuinely didn't narrow anything (rare, but happens), run Path A from SKILL.md.

### Ambiguity-resolution table

When two directions are both live, ask the concrete question instead of the generic feeling question.

| Pair | Disambiguation question |
|---|---|
| editorial vs technical-minimal | "More magazine-style (serif display, big type hierarchy) or more software-product-style (geometric sans, dense, systematic)?" |
| warm-approachable vs vibrant-playful | "One warm accent doing everything, or a coordinated multi-hue palette for categories and charts?" |
| dark-premium vs technical-minimal | "Dark mode as the primary experience (cinematic, hero gradient) or a light product UI with a dark variant?" |
| bold-distinctive vs brutalist | "Polished-bold (oversized type, asymmetric, composed) or raw-brutalist (monospace, zero radius, HTML-aesthetic)?" |
| editorial vs warm-approachable | "Authoritative editorial (quiet, considered, oxblood accent) or soft lifestyle (peach, rounded, italic serif accents)?" |
| technical-minimal vs dark-premium | "Light near-white with a single saturated accent, or deep background with a muted jewel accent?" |
| bold-distinctive vs vibrant-playful | "One dramatic hero moment per screen, or consistent multi-color coordination across the whole product?" |

---

## Competitor-to-direction map

When a brief names a brand, start from that brand's direction. Some brands legitimately span two — call that out and ask.

| Brand / Reference | Direction(s) | Note |
|---|---|---|
| Stripe | technical-minimal | Canonical. Purple accent is theirs — only use if asked. |
| Linear | technical-minimal | Dense, systematic, cool gray. |
| Vercel | technical-minimal / dark-premium | Marketing site = dark-premium; dashboard = technical-minimal. |
| Arc (browser) | bold-distinctive / vibrant-playful | Colorful, playful, brand-forward. |
| Notion | warm-approachable / technical-minimal | Marketing = warm; product UI = technical-minimal. |
| Figma | vibrant-playful | Coordinated multi-hue, consumer-friendly. |
| Are.na | editorial / brutalist | Archive-aesthetic, restrained, grid-heavy. |
| Cal.com | technical-minimal | Clean, systematic, developer-friendly consumer tool. |
| Apple | dark-premium / technical-minimal | Product pages = dark-premium; support docs = technical-minimal. |
| Rauno (portfolio) | dark-premium | Deep bg, cinematic motion, single hero. |
| Bloomberg Terminal | technical-minimal / brutalist | Dense data-first; brutalist if the mood is information-maximal. |
| Nothing (phone) | brutalist / bold-distinctive | Monochrome, monospace, stark. |
| Glossier | warm-approachable | Soft pink, serif, photographic. |
| Jacquemus | editorial | Fashion, minimal type, restrained. |
| Supreme | brutalist / bold-distinctive | Box logo aesthetic, confrontational. |
| Mailchimp | vibrant-playful | Canonical example. Playful illustrations, warm palette. |
| Duolingo | vibrant-playful | Green + multi-color, character-driven. |
| Liquid Death | bold-distinctive / vibrant-playful | Editorial metal aesthetic with consumer-playful moments. |
| Headspace | warm-approachable / vibrant-playful | Calming warm palette, multi-hue illustration. |
| The New Yorker | editorial | Canonical. Serif, grid, rule lines. |
| Kinfolk | editorial / warm-approachable | Print-aesthetic, warm neutrals. |
| Craigslist | brutalist | Default browser aesthetic, embraced. |
| Berghain / Boiler Room | brutalist / bold-distinctive | Underground music aesthetic. |
| Aesop | editorial / warm-approachable | Apothecary, serif, cream. |
| Hermès | editorial / dark-premium | Luxury print-aesthetic. |

If the brief names a brand not in this table, reason from first principles against `aesthetic-directions.md`.

---

## Tone-word dictionary

Cluster of adjectives → direction. Use this to resolve step 2 of the decision tree.

**→ technical-minimal**
fast, precise, technical, systematic, data-driven, dense, clean, efficient, scalable, engineered, reliable, rigorous, structured, deliberate

**→ editorial**
timeless, considered, slow, craft, literary, thoughtful, quiet, restrained, authoritative, typographic, curated, long-form, essayistic, resonant, measured

**→ bold-distinctive**
bold, loud, confident, striking, statement, attention-grabbing, hero, unmissable, oversized, dramatic, punchy, assertive

**→ dark-premium**
premium, luxury, cinematic, sophisticated, refined, exclusive, flagship, high-end, moody, atmospheric, jewel-like, elevated, aspirational, hushed

**→ warm-approachable**
warm, human, friendly, soft, cozy, caring, approachable, gentle, inviting, neighborly, handmade, genuine, organic, comforting

**→ brutalist**
raw, unpolished, honest, blunt, functional, utilitarian, anti-design, scrappy, DIY, punk, archival, documentary, unfiltered

**→ vibrant-playful**
playful, energetic, lively, bright, fun, joyful, alive, delightful, exuberant, spirited, buoyant, colorful, expressive, engaging

If the brief mixes clusters ("bold and restrained"), treat as **contradiction** — see anti-patterns.

---

## Output format

After parsing, produce this internal summary **before** moving to Phase 2. Don't show it to the user — it's your reasoning scratchpad.

```yaml
extracted:
  direction: editorial
  confidence: high          # high | medium | low
  ambiguity_with: null      # or another direction key if medium
  audience: policy-researchers
  domain: nonprofit
  tone_words: [considered, restrained, long-form]
  competitor_refs: []
  constraints:
    dark_mode: false
    a11y: AA
    brand_colors: null
  artifact_type: [report, landing-page]
  skip_elicitation: true    # high → true; medium → ask one Q; low → fall back to picker
  skip_reason: "brief explicitly says 'considered, long-form report for policy researchers'"
```

**Branching rules:**

- `confidence: high` → `skip_elicitation: true`. Go straight to Phase 2 with the resolved direction. Do NOT ask "How should it feel?"
- `confidence: medium` → `skip_elicitation: false`. Ask the single disambiguation question from the ambiguity table. Do NOT run the full picker.
- `confidence: low` → `skip_elicitation: false`. Fall back to Path A (visual picker) or Path B (text anchor) from SKILL.md.

Keep the reasoning terse. One line per field. If a field is null, omit it.

---

## Worked examples

### Example 1 — "Policy research org report"

> We're a nonprofit policy research org publishing our annual report on housing affordability. Audience is policymakers, academics, journalists. We want something considered and long-form — no flashy startup aesthetic. Serif type feels right. Must work in print PDF and web. AA accessibility required.

```yaml
extracted:
  direction: editorial
  confidence: high
  audience: policymakers
  domain: nonprofit
  tone_words: [considered, long-form]
  constraints: {a11y: AA, formats: [pdf, web]}
  artifact_type: [report, landing-page]
  skip_elicitation: true
  skip_reason: "explicit tone words + audience + anti-signal ('no flashy startup aesthetic')"
```

### Example 2 — "Devtool dashboard like Linear"

> Building an internal analytics dashboard for our infra team. Think Linear or Vercel's dashboard — dense, clean, dark mode required. Our brand blue is #2F6FEB. Engineers will stare at this eight hours a day.

```yaml
extracted:
  direction: technical-minimal
  confidence: high
  audience: developers
  domain: devtools
  tone_words: [dense, clean]
  competitor_refs: [Linear, Vercel]
  constraints: {dark_mode: true, brand_color: "#2F6FEB"}
  artifact_type: [dashboard]
  skip_elicitation: true
  skip_reason: "two devtool competitors named, both map to technical-minimal"
```

### Example 3 — "Consumer creator-economy landing"

> We're a new platform for indie musicians to sell merch directly to fans. Warm, energetic, feels like you're at a local record shop. Our audience is 18–35, creative, on TikTok. Needs to feel alive — lots of color, not corporate.

```yaml
extracted:
  direction: vibrant-playful
  confidence: medium
  ambiguity_with: warm-approachable
  audience: creators
  domain: creator-economy
  tone_words: [warm, energetic, alive]
  competitor_refs: []
  artifact_type: [landing-page]
  skip_elicitation: false
  skip_reason: "warm + energetic + 'lots of color' points vibrant-playful, but 'local record shop' pulls warm-approachable — ask"
```

→ Ask: *"One warm accent doing everything, or a coordinated multi-hue palette for categories and charts?"*

### Example 4 — "Luxury watch brand product page"

> Flagship product page for our new mechanical watch. Premium, cinematic, no noise. Competitors: A. Lange & Söhne, Rauno's portfolio aesthetic. Gold accent over deep background. Hero video of the movement.

```yaml
extracted:
  direction: dark-premium
  confidence: high
  audience: consumers
  domain: luxury
  tone_words: [premium, cinematic]
  competitor_refs: [Rauno-portfolio]
  constraints: {accent: gold, hero: video}
  artifact_type: [landing-page]
  skip_elicitation: true
  skip_reason: "explicit 'premium, cinematic' + Rauno ref + gold-on-deep — dark-premium locked"
```

### Example 5 — "Vague long brief"

> We're a mid-market SaaS company. We make software. Our customers are businesses. We want the landing page to look good and convert well. The team can't agree on direction. Please make it nice.

```yaml
extracted:
  direction: null
  confidence: low
  audience: internal-employees
  domain: null
  tone_words: []
  skip_elicitation: false
  skip_reason: "brief is long but didn't narrow — fall back to visual picker"
```

→ Run Path A from SKILL.md.

---

## Anti-patterns

**Don't over-extract.** If the brief says "warm," don't infer "therefore nonprofit, therefore warm-approachable, therefore peach + terracotta." Infer less than the brief says. `tone_words: [warm]` is the extraction; the direction resolution is a separate step.

**Don't force a direction when confidence is low.** A long brief doesn't guarantee a parseable brief. Example 5 above — lots of words, zero signal. Fall back to the picker instead of hallucinating an answer.

**Don't skip the disambiguation question when it's medium confidence.** Two live directions is the exact situation the parser exists to narrow. Asking one sharp question ("one accent or a palette?") is leagues better than the generic "How should it feel?" OR than silently picking.

**Acknowledge contradictions.** If the brief says "bold and restrained," that's a real tension, not noise. Flag it back to the user: *"You mentioned 'bold' and 'restrained' — those pull different ways. Closer to quietly-confident (restrained wins) or oversized-but-considered (bold wins)?"* Don't just pick one and hope.

**Don't use parser output as user-facing copy.** The YAML summary is internal reasoning. Never paste it back to the user — they'll find it alienating. Translate to plain language when speaking to them.

**Don't re-run the parser on subsequent artifacts.** If a `design-system.md` already exists, inherit it (see SKILL.md "Subsequent artifacts in the same project"). The parser is a Phase 1 tool, not a rerun on every turn.

**Don't extend the competitor map silently.** If a brand isn't in the table, reason from first principles against `aesthetic-directions.md` and state your reasoning. Don't invent a mapping and treat it as canon.
