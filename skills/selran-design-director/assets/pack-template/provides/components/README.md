# provides/components/

Every component in this pack lives here. One HTML file per component. Each file is a standalone, copy-pasteable snippet that consumes tokens and renders correctly on its own.

Declare each path explicitly in `pack.yaml` under `provides.components`.

## The five non-negotiables

Every component ships with all five. `selran pack validate --full` fails the pack if any one is missing.

1. **Scoped class prefix.** Every custom class starts with `.pack-{{PACK_NAME}}-<component>-*`. Pack components must never collide with core components on the same page. Good: `.pack-{{PACK_NAME}}-hero-title`. Bad: `.hero-title`, `.title`, `.pack-{{PACK_NAME}}-title` (missing component segment).
2. **Token-driven with fallbacks.** Every color goes through `var(--pack-{{PACK_NAME}}-<name>, #hex-fallback)`. Declare the tokens once in `:root { }` at the top of the `<style>` block. No bare hex outside `:root`. The fallback lets the component render even when the pack hasn't been loaded.
3. **`:focus-visible` on every interactive element.** Buttons, links, inputs, summary-disclosures — every focusable element ships a visible outline. The core's a11y audit flags components that rely on `:focus` alone or drop focus outlines entirely.
4. **`prefers-reduced-motion` on every `@keyframes`.** Every animation has a matching reduced-motion branch that either disables the animation or reduces it to an `opacity` transition. Users who set the OS preference should never see transforms or parallax.
5. **Humane font stack.** `'General Sans', 'IBM Plex Sans', system-ui, sans-serif` is the starting point for body copy. `Inter`, `Poppins`, and `Roboto` are banned as the first choice in the stack — the core enforces this via `references/anti-patterns.md`.

## Additional discipline

- **Tap-target minimum 44px.** Every button, link-as-button, and tappable surface ships `min-height: 44px`. WCAG 2.5.5 and touch ergonomics.
- **Tabular numerals on numeric surfaces.** `font-variant-numeric: tabular-nums` on anything showing a rate, balance, count, or timestamp.
- **SVG icons only, no emoji.** Inline SVG with `aria-hidden="true"`. Emoji render inconsistently across platforms and can't be tinted by tokens.
- **No JavaScript.** Components are pure HTML + CSS. State lives in `:hover`, `:focus`, `:checked`, `[open]`, and `details`/`summary`.

See `../../../references/pack-authoring.md#4-component-discipline-the-five-non-negotiables` for the full checklist and the validator's failure modes.
