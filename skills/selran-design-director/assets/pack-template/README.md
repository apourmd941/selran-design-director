# {{PACK_DISPLAY_NAME}}

*This README was scaffolded by `selran pack init`. Replace the placeholder text before publishing.*

A pack for the [Selran Design Director](https://selran.design) skill. Ships opinionated components, patterns, and tokens layered on top of the core `{{BASE_DIRECTION}}` direction.

**Version:** 0.1.0
**License:** {{LICENSE}}
**Price:** ${{PRICE_USD}} (set to `0` for free packs; delete this line if not commercial)
**Requires core:** `>=3.4.0`

---

## Who it's for

**TODO:** A one-paragraph description of who benefits from this pack. Be specific about the team size, the product stage, the regulatory context, or the aesthetic the buyer already has in mind.

Example structure:

- **Team shape one** — two-sentence description. Why the pack fits. What problem it solves on day one.
- **Team shape two** — two-sentence description. What kind of surfaces this team ships. Why a generic starter doesn't cover them.
- **Team shape three** — two-sentence description. The buyer profile. What triggers the purchase decision.

End with a one-line "if your situation is X, this pack is not for you" disqualifier. Honesty shortens the refund cycle.

---

## What's inside

### Components

- **`{{PACK_NAME}}-hero.html`** — a token-driven hero component scoped with `.pack-{{PACK_NAME}}-hero-*`. Consumes `--pack-{{PACK_NAME}}-accent`, `--pack-{{PACK_NAME}}-bg`, `--pack-{{PACK_NAME}}-fg`, `--pack-{{PACK_NAME}}-border` with hex fallbacks. `:focus-visible` outlines on every CTA. `prefers-reduced-motion` respected. 44px minimum tap targets.

**TODO:** List every component you ship, one bullet each. Describe the *intent* (what it exists to solve), not the markup (which authors can read). One sentence per component is the target.

Every component in this pack:

- Consumes CSS variables with hex fallbacks — no bare hex outside `:root`
- Scopes all classes with `.pack-{{PACK_NAME}}-*` so pack components never collide with core components on the same page
- Ships `:focus-visible` outlines on every interactive element
- Honors `prefers-reduced-motion` on every animation
- Maintains 44px minimum tap-target height on every button

### Reference doc

`provides/references/{{PACK_NAME}}-patterns.md` — a 500+ word craft doc covering the voice, component architecture, forbidden phrases, and annotated reference brands that make this pack opinionated. **TODO:** Rewrite to match your pack's actual domain.

### Overlay

`pack-overrides/{{BASE_DIRECTION}}.yaml` — layers pack-specific tokens on top of the core `{{BASE_DIRECTION}}` starter. Only the keys that genuinely differ from the core are listed; everything else inherits.

---

## Install

The Selran core auto-discovers packs in `~/.claude/skills/selran-design-director/packs/`.

**Install from zip:**

```bash
unzip {{PACK_NAME}}-0.1.0.zip -d ~/.claude/skills/selran-design-director/packs/
```

**Install project-local (pin to a repo):**

```bash
unzip {{PACK_NAME}}-0.1.0.zip -d <your-project>/.selran-packs/
```

**Opt in to the overlay:**

```yaml
# design-system.md
direction: {{BASE_DIRECTION}}+{{PACK_NAME}}
```

**Commercial packs only:** the core's license gate requires one of:

- An `SELRAN_PACK_LICENSE_{{PACK_NAME}}` environment variable set to your license key, *or*
- An entry in `~/.claude/skills/selran-design-director/.pack-licenses.yaml` acknowledging the purchase.

Ask the skill *"what packs do I have?"* after install to confirm the gate is satisfied.

---

## Support

- **Docs:** {{HOMEPAGE}}
- **Changelog:** see `CHANGELOG.md`
- **License:** see `LICENSE`
