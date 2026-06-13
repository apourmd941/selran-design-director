# Selran Design Director

**Stop shipping AI-slop UI.** By [Selran](https://github.com/apourmd941) — written and
maintained by **Aidin Eslampour**.

A Claude plugin that directs high-quality visual work: websites, web apps,
dashboards, landing pages, native iOS (SwiftUI) and Android (Jetpack Compose)
apps, React components, Word/PDF/PowerPoint documents, HTML slide decks,
posters, and reports.

## How it works

1. **Pick how it should feel.** Seven pre-baked design directions, rendered as
   real thumbnails — tap the one you like. With no Hub, a clickable picker
   opens in your browser and your choice comes straight back — no copy-paste.
   No design vocabulary needed.
2. **Get a design system you own.** A portable `design-system.md` — colors,
   type, spacing, motion — that you can read and edit before anything is built.
3. **Everything obeys it.** Every artifact in the project follows the system:
   dark-mode variants, a component pattern library, native mobile snippets,
   **custom icon sets** and **token-driven generative backgrounds**, i18n/RTL
   support, accessibility audits (WCAG AA), performance budgets — and the
   **words** too: error messages, empty states, and button labels written in
   the brand voice, with character budgets so they survive translation.
4. **Talk to it like a design partner.** "The hero feels flat" or "make it
   louder" gets translated into concrete token changes and re-rendered.
5. **Or point it at a design you already have.** Hand it a screenshot, a URL,
   pasted HTML, or a PowerPoint and ask "does this look AI-made?" — it scores
   the tell-tale signatures (Inter, purple-on-white gradients, centered-
   everything, the three-card row…), gives a *reads-as-AI-made: high/medium/low*
   verdict, and **de-slops** it: either fixes the file in place or rebuilds it
   in a direction you choose.
6. **Hand it to engineering.** When it's ready, get a developer spec —
   tokens, component specs, states, motion, accessibility requirements, and
   edge cases — plus the real token files (CSS / Tailwind / Figma / Style
   Dictionary) to install, not retype.
7. **Or get a brand book.** A full brand guidelines document — logo usage,
   color, typography, spacing, voice & tone, application examples, do/don't —
   as HTML, PDF, and Word. Most tools skip the logo, voice, and spacing pages;
   this one doesn't, and the book is the *same* system everything else obeys.

And before it says "done," it can **verify its own work** — render the result
and check the *actual pixels* (contrast, overflow, dark mode) at real
breakpoints, not just read the code.

And throughout: generic AI defaults — Inter/Roboto everywhere, purple-on-white
gradients, rainbow headings, stock photos, emoji as UI icons — are blocked
unless you explicitly ask for them. Restraint isn't timidity, though: a
**boldness dial** takes any direction from quiet to maximal — even experimental
— and the accessibility floors and anti-slop rules hold the whole way up.

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
