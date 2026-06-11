# i18n assets — font fallbacks, RTL rules, copy-length tolerance

Three files feed the internationalization layer declared in `design-system.md` under `i18n:`.

| File | Consumed by | Purpose |
|---|---|---|
| `font-fallbacks.yaml` | web/ios/android build | Font-family chains per script × direction so CJK/Arabic/Devanagari etc. render correctly alongside the primary Latin face |
| `rtl-mirror.yaml` | web/ios/android build | Which components flip under RTL, which don't, and how — organized by component category |
| `copy-length.yaml` | all surfaces | Expansion factor per language, overflow strategy per direction, component-specific sizing rules |

## Usage at build time

1. Read the project's `design-system.md` `i18n` block
2. For each declared script in `i18n.scripts`, look up the fallback chain in `font-fallbacks.yaml` keyed by `<script>.<direction>`
3. If `i18n.rtl: true`, apply the mirror rules from `rtl-mirror.yaml` — logical CSS properties handle most; this file lists what needs explicit handling (directional icons, carousel direction, etc.)
4. Given `i18n.max_expansion`, size components per the rules in `copy-length.yaml` under `sizing:` and apply the direction's overflow strategy from `overflow_strategy:`

## When to use these

- **Latin-only, LTR-only projects:** ignore these files entirely. The `i18n` block in `design-system.md` is optional.
- **One non-Latin script:** use the relevant script entry from `font-fallbacks.yaml`. Skip RTL rules if it's LTR (CJK/Devanagari/Cyrillic).
- **RTL locale:** use `rtl-mirror.yaml` + the RTL-capable script entries from `font-fallbacks.yaml` (arabic, hebrew).
- **Multi-locale launch:** check `copy-length.yaml` for the worst-case expansion across all your locales; set `max_expansion` in `design-system.md`; apply the sizing rules.

## Extending

- New script: add a new top-level key to `font-fallbacks.yaml` with entries for all 7 directions.
- New language: add an entry to `copy-length.yaml#expansion`.
- New component mirror: add it to the appropriate category in `rtl-mirror.yaml` (flip / no_flip / depends).

## See also

- `references/i18n.md` — the full spec (schema block, three dimensions, testing, anti-patterns)
- `references/breakpoints.md` — how overflow interacts with responsive layout
- `references/native-ios.md` / `references/native-android.md` — platform-specific i18n APIs
