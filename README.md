# Selran Design Director

**Stop shipping AI-slop UI.** By [Selran](https://github.com/apourmd941) — written and
maintained by **Aidin Eslampour**.

A Claude plugin that directs high-quality visual work: websites, web apps,
dashboards, landing pages, native iOS (SwiftUI) and Android (Jetpack Compose)
apps, React components, Word/PDF/PowerPoint documents, HTML slide decks,
posters, and reports.

## How it works

1. **Pick how it should feel.** Seven pre-baked design directions, rendered as
   real thumbnails — tap the one you like. On hosts that can't render inline,
   a picker page opens in your browser; just tell Claude the number. No design
   vocabulary needed.
2. **Get a design system you own.** A portable `design-system.md` — colors,
   type, spacing, motion — that you can read and edit before anything is built.
3. **Everything obeys it.** Every artifact in the project follows the system:
   dark-mode variants, a component pattern library, native mobile snippets,
   i18n/RTL support, accessibility audits (WCAG AA), performance budgets.
4. **Talk to it like a design partner.** "The hero feels flat" or "make it
   louder" gets translated into concrete token changes and re-rendered.

And throughout: generic AI defaults — Inter/Roboto everywhere, purple-on-white
gradients, rainbow headings, stock photos, emoji as UI icons — are blocked
unless you explicitly ask for them.

## Install

In Claude Code or Claude Desktop:

```
/plugin marketplace add apourmd941/selran-devloop
/plugin install design-director@selran
```

(The `selran` marketplace also carries [Greenloop](https://github.com/apourmd941/selran-devloop),
the convergent audit/fix loop.)

## Add-on packs

The core (this repository) is MIT and fully functional standalone — seven
directions, all output formats. Domain packs (fintech, developer tools,
luxury editorial, …) extend it with additional component libraries, shells,
illustrations, and per-direction token overlays, and are licensed
independently. Pack installation requires the Selran CLI; the skill tells you
when (and only when) that matters.

## Feedback & contributions

Bug reports and feature requests are very welcome — open an issue. Pull
requests are not accepted: to keep authorship and licensing unambiguous, all
code in this repository is written by the author. If you've found a fix,
describe it in an issue and it will be credited in the changelog.

## License & attribution

Core: [MIT](LICENSE) — © 2026 Selran, Aidin Eslampour. Add-on packs are
licensed separately. Privacy: this plugin runs entirely locally and collects
nothing — see [PRIVACY.md](PRIVACY.md). Contact: aidin.eslampour@selran.ai
