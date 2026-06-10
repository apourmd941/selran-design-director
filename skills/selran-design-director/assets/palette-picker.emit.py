#!/usr/bin/env python3
"""Emit palette-picker.html from design-system.md.

Reads the YAML frontmatter of a project's design-system.md, computes five
HSL-shifted tonal variations of the accent hex, substitutes all
{{token.path}} placeholders in palette-picker.template.html, and writes
the rendered file to the project root (or a user-supplied path).

Usage:
    python palette-picker.emit.py                 # cwd as project root
    python palette-picker.emit.py /path/to/proj   # explicit project root
    python palette-picker.emit.py /path/to/proj /out/palette-picker.html
"""
import colorsys
import re
import sys
from pathlib import Path

import yaml

SKILL = Path.home() / ".claude" / "skills" / "selran-design-director" / "assets"
TEMPLATE = SKILL / "palette-picker.template.html"


def flatten(obj, prefix=""):
    out = {}
    if isinstance(obj, dict):
        for k, v in obj.items():
            key = f"{prefix}.{k}" if prefix else k
            if isinstance(v, (dict, list)):
                out.update(flatten(v, key))
            else:
                out[key] = v
    elif isinstance(obj, list):
        for i, v in enumerate(obj):
            out.update(flatten(v, f"{prefix}.{i}"))
    return out


def hex_to_rgb(hx):
    hx = hx.lstrip("#")
    return tuple(int(hx[i : i + 2], 16) / 255.0 for i in (0, 2, 4))


def rgb_to_hex(r, g, b):
    clamp = lambda x: max(0, min(255, round(x * 255)))
    return f"#{clamp(r):02X}{clamp(g):02X}{clamp(b):02X}"


def tonal_five(accent_hex):
    """Five tonal variations of accent_hex via HSL lightness/saturation shifts.

    Slot 1 is the accent itself; slots 2–5 walk darker↔lighter with small
    saturation tweaks so the family reads as one hue but shows depth.
    """
    r, g, b = hex_to_rgb(accent_hex)
    h, l, s = colorsys.rgb_to_hls(r, g, b)
    # (lightness_delta, saturation_mult)
    shifts = [
        (0.00, 1.00),  # 1 — the accent itself
        (-0.08, 1.05),  # 2 — deeper, slightly more saturated
        (-0.04, 0.95),  # 3 — mid-shadow
        (+0.12, 0.85),  # 4 — lifted, slightly muted
        (-0.18, 1.10),  # 5 — darkest, most saturated
    ]
    out = []
    for dl, ms in shifts:
        nl = max(0.05, min(0.95, l + dl))
        ns = max(0.0, min(1.0, s * ms))
        nr, ng, nb = colorsys.hls_to_rgb(h, nl, ns)
        out.append(rgb_to_hex(nr, ng, nb))
    return out


def main():
    argv = sys.argv[1:]
    root = Path(argv[0]).resolve() if argv else Path.cwd()
    out_path = Path(argv[1]).resolve() if len(argv) > 1 else root / "palette-picker.html"

    ds = root / "design-system.md"
    if not ds.exists():
        sys.exit(f"error: {ds} not found")

    text = ds.read_text()
    m = re.match(r"^---\n(.*?)\n---\n", text, re.DOTALL)
    if not m:
        sys.exit(f"error: no YAML frontmatter in {ds}")
    raw = yaml.safe_load(m.group(1))

    tokens = flatten(raw)

    accent = tokens.get("color.accent")
    if not accent:
        sys.exit("error: color.accent not found in design-system.md")
    for i, hx in enumerate(tonal_five(accent), start=1):
        tokens[f"accent.tonal.{i}"] = hx

    tmpl = TEMPLATE.read_text()

    def sub(s):
        return re.sub(
            r"\{\{([^}]+)\}\}",
            lambda mm: str(tokens.get(mm.group(1), mm.group(0))),
            s,
        )

    out_path.parent.mkdir(parents=True, exist_ok=True)
    out_path.write_text(sub(tmpl))
    print(f"  wrote {out_path}")


if __name__ == "__main__":
    main()
