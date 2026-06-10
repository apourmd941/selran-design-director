#!/usr/bin/env python3
"""
WCAG 2.x contrast ratio check.

Usage:
    python contrast-check.py "#18181B" "#FAFAF9"
    python contrast-check.py "#18181B" "#FAFAF9" --large

Exit code: 0 if AA passes, 1 if AA fails. Prints ratio + AA/AAA status.

Exposes a check_pair(fg_hex, bg_hex, large=False) function when imported.
"""

import sys
import argparse


def _hex_to_rgb(h: str) -> tuple[float, float, float]:
    h = h.lstrip("#")
    if len(h) == 3:
        h = "".join(c * 2 for c in h)
    if len(h) != 6:
        raise ValueError(f"invalid hex color: {h!r}")
    r, g, b = (int(h[i : i + 2], 16) for i in (0, 2, 4))
    return r / 255, g / 255, b / 255


def _relative_luminance(rgb: tuple[float, float, float]) -> float:
    def channel(c: float) -> float:
        return c / 12.92 if c <= 0.03928 else ((c + 0.055) / 1.055) ** 2.4

    r, g, b = (channel(c) for c in rgb)
    return 0.2126 * r + 0.7152 * g + 0.0722 * b


def contrast_ratio(fg_hex: str, bg_hex: str) -> float:
    l1 = _relative_luminance(_hex_to_rgb(fg_hex))
    l2 = _relative_luminance(_hex_to_rgb(bg_hex))
    lighter, darker = (l1, l2) if l1 > l2 else (l2, l1)
    return (lighter + 0.05) / (darker + 0.05)


def check_pair(fg_hex: str, bg_hex: str, large: bool = False) -> dict:
    """
    Returns: {
      "ratio": float,
      "aa": bool,          # passes AA for the given text size
      "aaa": bool,         # passes AAA for the given text size
      "aa_threshold": float,
      "aaa_threshold": float,
    }
    """
    ratio = contrast_ratio(fg_hex, bg_hex)
    aa_threshold = 3.0 if large else 4.5
    aaa_threshold = 4.5 if large else 7.0
    return {
        "ratio": round(ratio, 2),
        "aa": ratio >= aa_threshold,
        "aaa": ratio >= aaa_threshold,
        "aa_threshold": aa_threshold,
        "aaa_threshold": aaa_threshold,
    }


def _main() -> int:
    p = argparse.ArgumentParser(description="WCAG 2.x contrast check")
    p.add_argument("fg", help="foreground hex (e.g. #18181B)")
    p.add_argument("bg", help="background hex (e.g. #FAFAF9)")
    p.add_argument(
        "--large",
        action="store_true",
        help="use large-text thresholds (3.0 AA / 4.5 AAA)",
    )
    args = p.parse_args()

    result = check_pair(args.fg, args.bg, large=args.large)
    size_label = "large" if args.large else "normal"
    print(
        f"{args.fg} on {args.bg} ({size_label} text): "
        f"ratio {result['ratio']}:1 "
        f"— AA {'PASS' if result['aa'] else 'FAIL'} "
        f"(≥{result['aa_threshold']}:1), "
        f"AAA {'PASS' if result['aaa'] else 'FAIL'} "
        f"(≥{result['aaa_threshold']}:1)"
    )
    return 0 if result["aa"] else 1


if __name__ == "__main__":
    sys.exit(_main())
