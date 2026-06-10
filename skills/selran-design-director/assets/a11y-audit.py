#!/usr/bin/env python3
"""
WCAG a11y audit for a Selran design-system.md — actionable mode.

Parses the YAML frontmatter, checks body-text foreground/background pairs for
AA compliance in both light and dark modes, and proposes minimal HSL
adjustments to fix the failures.

Usage:
    python a11y-audit.py path/to/design-system.md          # diagnose + fixes
    python a11y-audit.py path/to/design-system.md --fix    # apply fixes in-place
    python a11y-audit.py path/to/design-system.md --json   # JSON output

Imports contrast-check.py via importlib because the sibling file has a dash
in its name.
"""

from __future__ import annotations

import argparse
import colorsys
import importlib.util
import json
import re
import shutil
import sys
from pathlib import Path

try:
    import yaml
except ImportError:
    print(
        "PyYAML is required. Install with: pip install pyyaml\n"
        "(Manual fallback: copy the color: block into a dict literal by hand.)",
        file=sys.stderr,
    )
    sys.exit(2)

# Load sibling contrast-check.py
_HERE = Path(__file__).resolve().parent
_spec = importlib.util.spec_from_file_location("cc", str(_HERE / "contrast-check.py"))
cc = importlib.util.module_from_spec(_spec)
_spec.loader.exec_module(cc)  # type: ignore[union-attr]

# --- audit configuration --------------------------------------------------

# Foregrounds to check against each bg. `large=True` means the pair is
# allowed to satisfy the 3.0:1 large-text threshold instead of 4.5:1.
FG_TOKENS = [
    ("fg_primary", False),
    ("fg_secondary", False),
    ("fg_muted", False),
    ("accent", False),
    ("accent_hover", True),   # treated as large-text / non-text use (buttons, hover)
    ("success", False),
    ("warning", False),
    ("danger", False),
]
BG_TOKENS = ["bg_primary", "bg_secondary", "bg_tertiary"]

AA_NORMAL = 4.5
AA_LARGE = 3.0

# --- hex <-> hsl helpers --------------------------------------------------


def _hex_to_rgb01(h: str) -> tuple[float, float, float]:
    h = h.lstrip("#")
    if len(h) == 3:
        h = "".join(c * 2 for c in h)
    r, g, b = (int(h[i : i + 2], 16) for i in (0, 2, 4))
    return r / 255, g / 255, b / 255


def _rgb01_to_hex(r: float, g: float, b: float) -> str:
    def clamp(x: float) -> int:
        return max(0, min(255, round(x * 255)))

    return "#{:02X}{:02X}{:02X}".format(clamp(r), clamp(g), clamp(b))


def _hex_to_hls(h: str) -> tuple[float, float, float]:
    r, g, b = _hex_to_rgb01(h)
    return colorsys.rgb_to_hls(r, g, b)  # (h, l, s)


def _hls_to_hex(h: float, l: float, s: float) -> str:
    l = max(0.0, min(1.0, l))
    r, g, b = colorsys.hls_to_rgb(h, l, s)
    return _rgb01_to_hex(r, g, b)


# --- frontmatter parsing --------------------------------------------------


FRONTMATTER_RE = re.compile(r"^---\n(.*?)\n---\n", re.DOTALL)


def parse_frontmatter(md_text: str) -> dict:
    m = FRONTMATTER_RE.match(md_text)
    if not m:
        raise SystemExit("No YAML frontmatter found at top of file.")
    return yaml.safe_load(m.group(1)) or {}


def resolve_color_modes(color_block: dict) -> dict[str, dict[str, str]]:
    """Return {'light': {token: hex}, 'dark': {token: hex}} with dark inheriting."""
    light = {k: v for k, v in color_block.items() if k != "dark" and isinstance(v, str)}
    dark_override = color_block.get("dark") or {}
    dark = dict(light)  # inherit
    for k, v in dark_override.items():
        if isinstance(v, str):
            dark[k] = v
    return {"light": light, "dark": dark}


# --- audit ----------------------------------------------------------------


def audit_mode(tokens: dict[str, str], mode: str) -> list[dict]:
    """Return list of failure dicts for one mode."""
    fails = []
    for fg_tok, is_large in FG_TOKENS:
        if fg_tok not in tokens:
            continue
        fg_hex = tokens[fg_tok]
        threshold = AA_LARGE if is_large else AA_NORMAL
        for bg_tok in BG_TOKENS:
            if bg_tok not in tokens:
                continue
            bg_hex = tokens[bg_tok]
            ratio = cc.contrast_ratio(fg_hex, bg_hex)
            if ratio < threshold:
                fails.append(
                    {
                        "mode": mode,
                        "fg_token": fg_tok,
                        "bg_token": bg_tok,
                        "fg_hex": fg_hex,
                        "bg_hex": bg_hex,
                        "ratio": round(ratio, 2),
                        "threshold": threshold,
                        "size": "large" if is_large else "normal",
                    }
                )
    return fails


def find_fix(fg_hex: str, bg_hex: str, threshold: float, darken: bool) -> str | None:
    """Step L in 1/256 increments until contrast >= threshold. Return hex or None."""
    h, l, s = _hex_to_hls(fg_hex)
    step = 1 / 256
    for _ in range(256):
        l = l - step if darken else l + step
        if l < 0 or l > 1:
            return None
        candidate = _hls_to_hex(h, l, s)
        if cc.contrast_ratio(candidate, bg_hex) >= threshold:
            return candidate
    return None


def propose_fixes(fails: list[dict]) -> list[dict]:
    """Group fails by (mode, fg_token), pick the largest L adjustment needed."""
    grouped: dict[tuple[str, str], list[dict]] = {}
    for f in fails:
        grouped.setdefault((f["mode"], f["fg_token"]), []).append(f)

    proposals = []
    for (mode, fg_tok), group in grouped.items():
        darken = mode == "light"
        fg_hex = group[0]["fg_hex"]
        _, orig_l, _ = _hex_to_hls(fg_hex)

        best_hex = None
        best_l = orig_l
        covered_idxs = []
        conflict_idxs = []

        # For each fail in the group, compute what it would take; pick the
        # most conservative (largest) adjustment that satisfies its threshold.
        per_fail = []
        for f in group:
            fix = find_fix(fg_hex, f["bg_hex"], f["threshold"], darken)
            per_fail.append((f, fix))

        # Pick the fix hex whose L is furthest from original (the hardest one).
        candidates = [(f, fix) for f, fix in per_fail if fix is not None]
        if not candidates:
            # No fix possible for any pair
            proposals.append(
                {
                    "mode": mode,
                    "token": fg_tok,
                    "from_hex": fg_hex,
                    "to_hex": None,
                    "fails_fixed": [],
                    "fails_unresolved": [_fail_label(f) for f in group],
                    "from_l": round(orig_l * 100),
                    "to_l": None,
                }
            )
            continue

        def _l_of(hx: str) -> float:
            return _hex_to_hls(hx)[1]

        chosen_f, chosen_hex = max(
            candidates,
            key=lambda t: abs(_l_of(t[1]) - orig_l),
        )
        best_hex = chosen_hex
        best_l = _l_of(chosen_hex)

        # Verify which fails this single change actually resolves.
        for f in group:
            if cc.contrast_ratio(best_hex, f["bg_hex"]) >= f["threshold"]:
                covered_idxs.append(f)
            else:
                conflict_idxs.append(f)

        proposals.append(
            {
                "mode": mode,
                "token": fg_tok,
                "from_hex": fg_hex,
                "to_hex": best_hex,
                "fails_fixed": [_fail_label(f) for f in covered_idxs],
                "fails_unresolved": [_fail_label(f) for f in conflict_idxs],
                "from_l": round(orig_l * 100),
                "to_l": round(best_l * 100),
            }
        )
    return proposals


def _fail_label(f: dict) -> str:
    return (
        f"{f['mode'].upper()} / {f['fg_token']} on {f['bg_token']} "
        f"({f['ratio']}:1, needs {f['threshold']}:1)"
    )


# --- output ---------------------------------------------------------------


def format_report(fails: list[dict], proposals: list[dict], path: Path) -> str:
    lines = []
    lines.append(f"=== WCAG a11y audit — {path.name} ===")
    lines.append(
        f"Thresholds: AA normal {AA_NORMAL}:1, AA large {AA_LARGE}:1 "
        "(accent_hover treated as large/non-text)"
    )
    lines.append("")
    if not fails:
        lines.append("No failures. All checked pairs pass AA at the appropriate threshold.")
        return "\n".join(lines) + "\n"

    lines.append(f"FAILS ({len(fails)}):")
    for f in fails:
        lines.append(
            f"  {f['mode'].upper():5s} / {f['fg_token']:<13s} on {f['bg_token']:<13s} "
            f"{f['ratio']}:1  ({f['size']} text, needs {f['threshold']}:1)"
        )
    lines.append("")

    resolved_total = sum(len(p["fails_fixed"]) for p in proposals)
    token_count = sum(1 for p in proposals if p["to_hex"])
    lines.append(
        f"PROPOSED FIXES ({token_count} token changes resolve {resolved_total} of {len(fails)} fails):"
    )
    for p in proposals:
        if p["to_hex"]:
            lines.append(
                f"  {p['mode'].upper():5s} / {p['token']:<13s} "
                f"{p['from_hex']} -> {p['to_hex']}  "
                f"(L {p['from_l']}->{p['to_l']})  "
                f"— fixes {len(p['fails_fixed'])} fail"
                f"{'s' if len(p['fails_fixed']) != 1 else ''}"
            )
            if p["fails_unresolved"]:
                lines.append(
                    f"    ! conflict: {len(p['fails_unresolved'])} pair(s) still failing with this change"
                )
                for label in p["fails_unresolved"]:
                    lines.append(f"      - {label}")
        else:
            lines.append(
                f"  {p['mode'].upper()} / {p['token']}: no in-gamut HSL step resolves any fail"
            )
    lines.append("")
    lines.append("Run again with --fix to apply these changes to design-system.md.")
    lines.append("(A snapshot of the current file will be saved as design-system.md.bak)")
    return "\n".join(lines) + "\n"


# --- in-place --fix -------------------------------------------------------


def apply_fixes(md_path: Path, proposals: list[dict], color_block: dict) -> list[str]:
    """
    Apply proposed hex changes to the design-system.md in place via targeted
    regex replacement. Writes <file>.bak first. Returns a list of edits made.
    """
    text = md_path.read_text()
    backup = md_path.with_suffix(md_path.suffix + ".bak")
    shutil.copy2(md_path, backup)

    # Split frontmatter from body so we only edit the frontmatter.
    m = FRONTMATTER_RE.match(text)
    if not m:
        raise SystemExit("Frontmatter disappeared between read and fix.")
    fm = m.group(1)
    body = text[m.end():]

    # For dark-mode edits we need to scope to inside the `dark:` sub-block.
    # Strategy: find the dark: block span, edit light tokens only outside it,
    # dark tokens only inside it.
    dark_match = re.search(r"(^\s*dark:\s*\n(?:(?:\s{2,}.*\n)+))", fm, re.MULTILINE)
    dark_span = dark_match.span(1) if dark_match else None

    edits: list[str] = []

    def _replace_token(text_slice: str, token: str, new_hex: str) -> tuple[str, bool]:
        # Match `  token: "#HEX"` or `  token:   #HEX` etc.
        pat = re.compile(
            rf'(^\s*{re.escape(token)}\s*:\s*)(["\']?)(#[0-9A-Fa-f]{{3,8}})\2',
            re.MULTILINE,
        )
        found = {"hit": False}

        def _sub(match):
            found["hit"] = True
            return f'{match.group(1)}{match.group(2)}{new_hex}{match.group(2)}'

        new_text = pat.sub(_sub, text_slice, count=1)
        return new_text, found["hit"]

    for p in proposals:
        if not p["to_hex"]:
            continue
        if p["mode"] == "light":
            # Edit only the portion of fm OUTSIDE the dark block.
            if dark_span:
                before = fm[: dark_span[0]]
                inside = fm[dark_span[0] : dark_span[1]]
                after = fm[dark_span[1] :]
                new_before, hit = _replace_token(before, p["token"], p["to_hex"])
                if hit:
                    fm = new_before + inside + after
                    # dark_span may still be valid since we didn't change lengths
                    # for same-width hex. Recompute to be safe.
                    dm = re.search(r"(^\s*dark:\s*\n(?:(?:\s{2,}.*\n)+))", fm, re.MULTILINE)
                    dark_span = dm.span(1) if dm else None
                    edits.append(
                        f"light/{p['token']}: {p['from_hex']} -> {p['to_hex']}"
                    )
            else:
                fm, hit = _replace_token(fm, p["token"], p["to_hex"])
                if hit:
                    edits.append(
                        f"light/{p['token']}: {p['from_hex']} -> {p['to_hex']}"
                    )
        else:  # dark
            if not dark_span:
                continue
            before = fm[: dark_span[0]]
            inside = fm[dark_span[0] : dark_span[1]]
            after = fm[dark_span[1] :]
            new_inside, hit = _replace_token(inside, p["token"], p["to_hex"])
            if hit:
                fm = before + new_inside + after
                edits.append(
                    f"dark/{p['token']}: {p['from_hex']} -> {p['to_hex']}"
                )
            else:
                # Token inherits from light; insert a new override inside dark block.
                # Match indentation of an item line (not the `dark:` header line).
                item_indents = re.findall(r"^(\s+)[A-Za-z_]\w*:\s", inside, re.MULTILINE)
                # First match is `dark:` itself (shallow); use a deeper one if present.
                deeper = [i for i in item_indents if len(i) > len(item_indents[0])]
                indent = deeper[0] if deeper else "    "
                new_line = f'{indent}{p["token"]}: "{p["to_hex"]}"\n'
                # Insert at the end of the dark block. `inside` ends with \n.
                new_inside = inside + new_line
                fm = before + new_inside + after
                edits.append(
                    f"dark/{p['token']}: (new override) -> {p['to_hex']}"
                )
            # Recompute dark_span since lengths may have changed.
            dm = re.search(r"(^\s*dark:\s*\n(?:(?:\s{2,}.*\n)+))", fm, re.MULTILINE)
            dark_span = dm.span(1) if dm else None

    new_text = f"---\n{fm}\n---\n{body}"
    md_path.write_text(new_text)
    return edits


# --- main -----------------------------------------------------------------


def main() -> int:
    ap = argparse.ArgumentParser(description="WCAG a11y audit with actionable fixes.")
    ap.add_argument("path", help="path to design-system.md")
    ap.add_argument("--fix", action="store_true", help="apply proposed fixes in place")
    ap.add_argument("--json", action="store_true", help="emit findings + fixes as JSON")
    args = ap.parse_args()

    md_path = Path(args.path).expanduser().resolve()
    if not md_path.exists():
        print(f"not found: {md_path}", file=sys.stderr)
        return 2

    fm = parse_frontmatter(md_path.read_text())
    color_block = fm.get("color", {}) or {}
    modes = resolve_color_modes(color_block)

    fails = audit_mode(modes["light"], "light") + audit_mode(modes["dark"], "dark")
    proposals = propose_fixes(fails)

    if args.json:
        print(json.dumps({"fails": fails, "proposals": proposals}, indent=2))
    else:
        print(format_report(fails, proposals, md_path), end="")

    if args.fix and proposals:
        edits = apply_fixes(md_path, proposals, color_block)
        if args.json:
            print(json.dumps({"applied": edits}, indent=2))
        else:
            print("\nApplied edits:")
            for e in edits:
                print(f"  {e}")
            print(f"Backup: {md_path}.bak")

    return 1 if fails else 0


if __name__ == "__main__":
    sys.exit(main())
