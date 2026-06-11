# Composition Rubric — Card vs. List Row vs. Table Row

The skill's component library gives you buttons, cards, tables, lists. What it hasn't had: a decision tree for **when to use which container**. This rubric answers: "I have N items of type T to display. Which container?"

Consult this before building any data-display screen.

---

## The three containers

| Container | Feel | Density | Best for |
|---|---|---|---|
| **Card** | Object-first, visual | Low — one card per ~200×200px minimum | Heterogeneous items, marketing tiles, dashboards, "browse and pick" |
| **List row** | Item-first, vertical | Medium — one row per ~56–72px | Homogeneous items, feed-style content, mail/messages, activity |
| **Table row** | Data-first, scannable | High — one row per ~40–48px | Structured records with multiple fields that need column alignment |

## Decision tree

Ask in order:

### 1. Are all items the same *type*?

- **No, heterogeneous** (mixing projects, people, files) → **Card**. Different cards can have different structures.
- **Yes, homogeneous** → continue to Q2.

### 2. Does the user need to compare values *across* items?

- **Yes** (sort by price, compare timestamps, scan statuses) → **Table**. Columns enable comparison.
- **No** (just browse one at a time) → continue to Q3.

### 3. Is each item a narrative (has prose, preview, context)?

- **Yes** (mail preview, post excerpt, activity description) → **List row**.
- **No, just identifiers + metadata** → continue to Q4.

### 4. Do you need density?

- **High** (>20 items visible without scroll) → **Table**.
- **Low-medium** → **List row**.

### 5. Default fallback

If you got this far without a clear answer: **list row**. It's the safest choice for homogeneous items that don't need comparison.

## Edge cases

**Kanban / board views** — the columns are containers, the items within are cards. Always cards.

**Settings / preferences** — always list rows with a label on left, control on right. Not cards.

**Search results (mixed content)** — cards if results come from different sources (files + people + projects); list rows if they're all the same type.

**Comments / threads** — list rows, not cards. Cards fragment a conversation.

**Product grid (ecommerce)** — cards. Each product is an object.

**Admin tables (users, orgs, records)** — tables. Comparison and sort are the whole point.

**Dashboard widgets** — cards, but each card is a *container* for something else (chart, stat, list). Cards are the frame, not the content.

**Mobile (<768px)** — tables collapse to stacked list rows. Cards often shrink to full-width stacked. Don't try to fit a 6-column table on a phone.

## Nesting rules

- **Cards can contain lists** (dashboard widget showing recent items)
- **Cards can contain tables** (small summary tables inside a card)
- **List rows should not contain cards** — that's a sign you picked the wrong outer container
- **Table cells should not contain cards** — cells should be atomic values
- **Avoid cards-inside-cards** unless you have a clear visual hierarchy reason (e.g., a nested "featured item" within a larger card). Two depth levels maximum.

## Mixing containers on one screen

A complex screen often uses multiple containers at different levels:

```
[Top bar]
[Stats row — 3 cards with numbers]        ← cards for heterogeneous KPIs
[Filter bar]
[Results table — homogeneous records]     ← table for scanning/sorting
```

Or:

```
[Top bar]
[Sidebar]
[Inbox list — list rows of messages]      ← list rows for homogeneous mail
[Split view right pane: message detail]   ← single object, card-like container
```

The rule: **each container answers one question.** Stats answer "how are we doing?" → cards. Records answer "what's in the system?" → table. Messages answer "what came in?" → list rows.

## Card subtypes

Not all cards are equal. The direction adjustments in `component-patterns.md` cover the base card. Common subtypes:

- **Object card** — represents a thing (project, file, user). Thumbnail / avatar + name + meta.
- **Stat card** — big number + label + trend. One KPI per card.
- **Media card** — image-forward, for galleries and content grids.
- **Feature card** — marketing, equal-weight tiles on a landing page.
- **Action card** — prompts the user to do something ("Get started", "Invite teammates"). Has a CTA.

Pick the subtype based on what the card represents. Don't use a stat card to show an object, or vice versa.

## List row subtypes

- **Minimal row** — single line, label + meta. Used in settings, menu lists.
- **Media row** — avatar/thumbnail + title + subtitle. Mail, DMs, contacts.
- **Multi-line row** — title + description + meta. Search results, activity feed.
- **Action row** — row ends with a control (toggle, button, chevron). Settings, in-app menus.

## Table variants

- **Data table** — standard rows and columns, sortable headers, optional selection checkbox. The dominant form.
- **Comparison table** — column-per-item (not row-per-item). Pricing pages, feature matrices. Different structural direction.
- **Pivot table** — columns and rows both have headers. Rarely needed — use only for genuine 2-D data (schedule grids, category-by-period reports).

## Responsive behavior

- **Desktop** (≥1024px): any container works as designed.
- **Tablet** (768–1024px): tables often collapse to 2–3 key columns; cards go from 3-col grid to 2-col; list rows unchanged.
- **Mobile** (<768px):
  - Tables collapse to stacked label-value pairs per row (essentially becoming list rows)
  - Cards go single-column, full-width
  - List rows unchanged
  - Kanban board becomes vertical-scroll with column pickers

## Anti-patterns

- **Don't use cards for everything.** Card overuse → "Trello-looking" UI that's actually hard to scan.
- **Don't use a table to show one record.** Use a detail view (headings + fields), not a 1-row table.
- **Don't mix cards and list rows for the same item type on one screen.** Pick one container per item type.
- **Don't put action buttons on every row of a 50-row table.** Use selection checkboxes + bulk action bar instead.
- **Don't add decorative icons to table columns.** Tables are for scanning; icons slow comparison. Exception: status indicators with meaning (online/offline dot).
- **Don't use drag-reorder on list rows with >20 items.** Beyond 20, users can't keep track. Use a separate "reorder" mode or a "move to top/bottom" action.
