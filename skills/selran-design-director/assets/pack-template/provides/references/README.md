# provides/references/

Markdown reference docs the core loads when the pack's topic is active. At runtime they live at `references/packs/<pack-name>/<filename>.md`, exposed alongside core references.

A reference doc is a *craft doc*, not marketing. It exists to make the pack's opinions legible — to help a designer using the pack understand *why* the components are shaped the way they are, and to give them copy-level ammunition for the next decision the component doesn't make for them.

## Word-count targets

The validator enforces a minimum; the target depends on pack shape:

- **Industry packs** (fintech, healthcare, saas-b2b, etc.): 800-1,200 words. Dense, specific, jargon-literate.
- **Archetype packs** (challenger, luxury, heritage, mission-driven): 600-900 words. Tone and gesture over process.
- **Micro / component packs** (chart-pack, motion-pack): 400-600 words. One domain, one voice.
- **Floor:** `selran pack validate --full` fails below 500 words.

## Required sections

Every pack reference doc includes:

- `## Forbidden phrases` — a real list, 8-15 entries minimum. Specific enough to actually refuse. "revolutionary" is ban-worthy; "bad copy" isn't.
- `## Reference brands` — 3-6 annotated brands with one-line "why this works" notes. Not a logo wall. A reading list.

Other high-value sections (encouraged, not mandatory):

- `## What's unique about designing for <target>` — the load-bearing opinion
- `## Component architecture` — which of your components composes with which, and why
- `## Copy voice` — the voice signature by surface (headline vs. CTA vs. disclaimer vs. error)
- `## Anti-patterns specific to <target>` — mistakes this pack is built to prevent

## Tone

- Craft-doc register. An essay a senior IC would write for their team, not a brochure.
- Specific over general. "Use tabular numerals on rates and balances" beats "care about numbers."
- Opinionated. A pack earns trust by refusing things.
- No hype. The forbidden-phrases list is also a tone-of-voice contract — practice what you ban.

See `../../../references/pack-authoring.md#6-reference-doc-discipline` for the full doctrine and the validator's failure modes.
