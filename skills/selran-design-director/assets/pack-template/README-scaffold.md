# You just ran `selran pack init` — here's what to do next

*Delete this file before publishing your pack. It's a one-time read for the author, not a shipped artifact. The `.packignore` file already excludes it from `selran pack build` archives.*

Welcome. You have a fresh, discipline-passing pack scaffold. Every file has a placeholder where your specifics belong. Here is the shortest path from scaffold to publishable pack.

## 1. Re-verify placeholder substitution

The CLI substitutes `{{PACK_NAME}}`, `{{PACK_DISPLAY_NAME}}`, `{{AUTHOR}}`, `{{HOMEPAGE}}`, `{{DESCRIPTION}}`, `{{LICENSE}}`, `{{PRICE_USD}}`, `{{BASE_DIRECTION}}`, `{{DATE}}`, and `{{YEAR}}` at scaffold time using the prompt answers you gave. Open `pack.yaml`, `README.md`, `CHANGELOG.md`, and `LICENSE` and confirm every placeholder was replaced. If you see any double-brace token still present, run `selran pack init --repair` to re-prompt and re-substitute.

## 2. Customize the hero component

Open `provides/components/<pack-name>-hero.html` (the filename has your pack name substituted). The structure is disciplined — scoped classes, token fallbacks, `:focus-visible`, `prefers-reduced-motion`, 44px tap targets. Customize:

- The eyebrow, title, dek, and proof-line copy (marked `<!-- TODO -->`)
- The accent/bg/fg/border hex fallbacks in `:root` — match your pack's overlay
- The SVG icon if the chevron doesn't fit your pack's voice

Do NOT change the class-prefix pattern or remove the `prefers-reduced-motion` block.

## 3. Rewrite the reference doc

Open `provides/references/<pack-name>-patterns.md`. Replace every italicized placeholder paragraph with real opinions. The validator fails the doc below 500 words and warns below 8 forbidden phrases — aim for 800-1,000 words for an industry pack, 600-900 for an archetype pack.

## 4. Tune the overlay

Open `pack-overrides/<base-direction>.yaml`. Set your accent + accent-hover. Uncomment type/spacing/motion keys only if you actually deviate from the core — inherited values are a feature, not a gap. See `../references/pack-authoring.md#6-overlay-authoring-the-only-override-what-differs-principle`.

## 5. Add more components

Drop new HTML files into `provides/components/`, then register each path in `pack.yaml` under `provides.components`. Every new component must pass the same five non-negotiables as the starter hero.

## 6. Validate until clean

```bash
selran pack validate --full
```

Run until it exits with zero errors AND zero warnings. The validator checks manifest integrity, component discipline (scoped classes, token fallbacks, `:focus-visible`, reduced-motion, tap targets), reference-doc length and required sections, overlay shape, changelog currency, and license presence.

## 7. Build the signed artifact

```bash
selran pack build --sign <your-private-key>
```

Produces `<pack-name>-<version>.zip` + `<pack-name>-<version>.zip.sig` in `dist/`. The signature is what lets the core's license gate and canonical-registry listing trust the archive.

## 8. Publish

- **Canonical registry:** open a PR to the `selran/registry` repo with your pack's metadata. Maintainers review for discipline; accepted packs show up in `selran pack search`.
- **Your own CDN:** upload the zip + sig to your site, link it from `homepage` in `pack.yaml`, distribute the URL however you want.
- **Private / team:** ship the archive directly — email, Slack, private git repo. The core's loader treats any correctly-shaped directory under `~/.claude/skills/selran-design-director/packs/` as a valid install.

## Canonical how-to-author guide

This file is a quick-start. The complete authoring doctrine — every discipline, every failure mode, every authoring pattern — lives in `../references/pack-authoring.md`. Read it once before you ship your first pack.
