<!-- Microcopy worksheet — the checklist of every string a product needs.
     Fill in the artifact's direction voice (voice.md), respect char budgets
     (i18n.md copy-length). Doubles as a "did we write copy for every state?"
     audit — no slot should ship as lorem ipsum or a raw stack trace.
     See references/microcopy.md for formulas + the per-string output format. -->

# Microcopy — <product / feature>

- **Direction / voice:** <direction> · **Locales:** <en, …> · **Worst-case expansion:** ×<__>

## Buttons / CTAs
| Slot | Recommended | Chars | Alt | Note |
|---|---|---|---|---|
| primary CTA | | | | verb-first, ≤4 words |
| secondary | | | | |
| destructive | | | | names the action ("Delete 3 files") |

## Errors  (what happened + why + how to fix; blameless; no internals)
| Slot | Recommended | Note |
|---|---|---|
| network/offline | | |
| not found (404) | | |
| permission denied | | |
| server error (500) | | no stack/SQL; "Try again or contact support" |
| rate limited | | |

## Empty states  (what this is + why empty + the one action)
| Slot | Recommended | Note |
|---|---|---|
| first-run (no data yet) | | onboard here, don't dead-end |
| no search results | | suggest a next step / clear filters |
| filtered-to-nothing | | |

## Validation (inline)  (specific + actionable)
| Field | Message | Note |
|---|---|---|
| email | | "Enter a valid email" |
| password | | "Use 8+ characters" not "Invalid" |
| required | | |

## Confirmation / destructive  (name the consequence)
| Action | Title | Body | Confirm button |
|---|---|---|---|
| delete | | "This can't be undone." | <verb the action> |

## Loading  (honest; gravity-scaled)
| Slot | Copy | Note |
|---|---|---|
| general | | "Loading…" — never fake progress |
| long op | | set expectation if >a few s |

## Onboarding  (value-first, one idea/step, skippable)
| Step | Headline | Body | CTA |
|---|---|---|---|

## Toasts / notifications  (outcome + optional action)
| Event | Copy | Dismiss |
|---|---|---|
| saved | "Saved" | auto |
| failed | "Couldn't save — Retry" | persist |

## Success / confirmation  (confirm + next step)
| Slot | Copy |
|---|---|

## Accessibility copy
- Icon-only controls → `aria-label`: <list>
- Link text meaningful out of context (no "click here")
- Error text associated with its field
