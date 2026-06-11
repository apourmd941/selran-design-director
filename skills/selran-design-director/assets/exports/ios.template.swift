//
//  DesignTokens.swift
//  {{meta.project}}
//
//  Generated from design-system.md. Do not edit by hand — edit the source and re-emit.
//  SwiftUI-native design tokens: Color, Typography, Spacing, Radius, Motion.
//

import SwiftUI

// MARK: - Hex initializer
// Supports "#RRGGBB" and "#RRGGBBAA". Falls back to .clear on bad input.
extension Color {
    init(hex: String) {
        let trimmed = hex.trimmingCharacters(in: .whitespacesAndNewlines)
            .replacingOccurrences(of: "#", with: "")
        var rgba: UInt64 = 0
        Scanner(string: trimmed).scanHexInt64(&rgba)

        let r, g, b, a: Double
        switch trimmed.count {
        case 6:
            r = Double((rgba & 0xFF0000) >> 16) / 255.0
            g = Double((rgba & 0x00FF00) >>  8) / 255.0
            b = Double( rgba & 0x0000FF)        / 255.0
            a = 1.0
        case 8:
            r = Double((rgba & 0xFF000000) >> 24) / 255.0
            g = Double((rgba & 0x00FF0000) >> 16) / 255.0
            b = Double((rgba & 0x0000FF00) >>  8) / 255.0
            a = Double( rgba & 0x000000FF)        / 255.0
        default:
            r = 0; g = 0; b = 0; a = 0
        }
        self.init(.sRGB, red: r, green: g, blue: b, opacity: a)
    }
}

// MARK: - Design tokens (namespace, no instances)
public enum DesignTokens {

    // MARK: Color
    public enum Color {
        // Light-mode values
        public enum Light {
            public static let bgPrimary    = SwiftUI.Color(hex: "{{color.bg_primary}}")
            public static let bgSecondary  = SwiftUI.Color(hex: "{{color.bg_secondary}}")
            public static let bgTertiary   = SwiftUI.Color(hex: "{{color.bg_tertiary}}")

            public static let fgPrimary    = SwiftUI.Color(hex: "{{color.fg_primary}}")
            public static let fgSecondary  = SwiftUI.Color(hex: "{{color.fg_secondary}}")
            public static let fgMuted      = SwiftUI.Color(hex: "{{color.fg_muted}}")

            public static let accent       = SwiftUI.Color(hex: "{{color.accent}}")
            public static let accentHover  = SwiftUI.Color(hex: "{{color.accent_hover}}")

            public static let border       = SwiftUI.Color(hex: "{{color.border}}")
            public static let borderStrong = SwiftUI.Color(hex: "{{color.border_strong}}")

            public static let success      = SwiftUI.Color(hex: "{{color.success}}")
            public static let warning      = SwiftUI.Color(hex: "{{color.warning}}")
            public static let danger       = SwiftUI.Color(hex: "{{color.danger}}")
        }

        // Dark-mode values
        public enum Dark {
            public static let bgPrimary    = SwiftUI.Color(hex: "{{color.dark.bg_primary}}")
            public static let bgSecondary  = SwiftUI.Color(hex: "{{color.dark.bg_secondary}}")
            public static let bgTertiary   = SwiftUI.Color(hex: "{{color.dark.bg_tertiary}}")

            public static let fgPrimary    = SwiftUI.Color(hex: "{{color.dark.fg_primary}}")
            public static let fgSecondary  = SwiftUI.Color(hex: "{{color.dark.fg_secondary}}")
            public static let fgMuted      = SwiftUI.Color(hex: "{{color.dark.fg_muted}}")

            public static let accent       = SwiftUI.Color(hex: "{{color.dark.accent}}")
            public static let accentHover  = SwiftUI.Color(hex: "{{color.dark.accent_hover}}")

            public static let border       = SwiftUI.Color(hex: "{{color.dark.border}}")
            public static let borderStrong = SwiftUI.Color(hex: "{{color.dark.border_strong}}")
        }

        /// Build a Color that adapts to the current color scheme.
        /// Use as: `DesignTokens.Color.dynamicColor(light: "#FAFAF9", dark: "#0A0A0B")`
        public static func dynamicColor(light: String, dark: String) -> SwiftUI.Color {
            #if canImport(UIKit)
            return SwiftUI.Color(UIColor { trait in
                trait.userInterfaceStyle == .dark
                    ? UIColor(SwiftUI.Color(hex: dark))
                    : UIColor(SwiftUI.Color(hex: light))
            })
            #else
            // Fallback for non-UIKit targets (macOS pre-12). Prefers light.
            return SwiftUI.Color(hex: light)
            #endif
        }

        // Adaptive semantic tokens — use these in views; they flip with the system.
        public static let bgPrimary    = dynamicColor(light: "{{color.bg_primary}}",    dark: "{{color.dark.bg_primary}}")
        public static let bgSecondary  = dynamicColor(light: "{{color.bg_secondary}}",  dark: "{{color.dark.bg_secondary}}")
        public static let bgTertiary   = dynamicColor(light: "{{color.bg_tertiary}}",   dark: "{{color.dark.bg_tertiary}}")

        public static let fgPrimary    = dynamicColor(light: "{{color.fg_primary}}",    dark: "{{color.dark.fg_primary}}")
        public static let fgSecondary  = dynamicColor(light: "{{color.fg_secondary}}",  dark: "{{color.dark.fg_secondary}}")
        public static let fgMuted      = dynamicColor(light: "{{color.fg_muted}}",      dark: "{{color.dark.fg_muted}}")

        public static let accent       = dynamicColor(light: "{{color.accent}}",        dark: "{{color.dark.accent}}")
        public static let accentHover  = dynamicColor(light: "{{color.accent_hover}}",  dark: "{{color.dark.accent_hover}}")

        public static let border       = dynamicColor(light: "{{color.border}}",        dark: "{{color.dark.border}}")
        public static let borderStrong = dynamicColor(light: "{{color.border_strong}}", dark: "{{color.dark.border_strong}}")
    }

    // MARK: Typography
    public enum Typography {
        // Font families. If the custom face is unavailable, SwiftUI falls back
        // to the system font at the same size via `.custom(_:size:)`.
        public static func display(_ size: CGFloat) -> Font {
            .custom("{{type.display}}", size: size)
        }
        public static func body(_ size: CGFloat) -> Font {
            .custom("{{type.body}}", size: size)
        }
        public static func mono(_ size: CGFloat) -> Font {
            .custom("{{type.mono}}", size: size)
        }

        // Scale (px → pt; iOS pt ≈ CSS px for design handoff)
        public enum Size {
            public static let xs:      CGFloat = {{type.scale.xs}}
            public static let sm:      CGFloat = {{type.scale.sm}}
            public static let base:    CGFloat = {{type.scale.base}}
            public static let lg:      CGFloat = {{type.scale.lg}}
            public static let xl:      CGFloat = {{type.scale.xl}}
            public static let xxl:     CGFloat = {{type.scale.xxl}}
            public static let display: CGFloat = {{type.scale.display}}
        }

        // Leading (line-height ratio)
        public enum Leading {
            public static let body:    CGFloat = {{type.leading.body}}
            public static let ui:      CGFloat = {{type.leading.ui}}
            public static let display: CGFloat = {{type.leading.display}}
        }

        // Tracking (em — SwiftUI uses points; multiply by font size when applying)
        public enum Tracking {
            public static let display: CGFloat = {{type.tracking.display}}
            public static let body:    CGFloat = {{type.tracking.body}}
            public static let caps:    CGFloat = {{type.tracking.caps}}
        }
    }

    // MARK: Spacing
    public enum Spacing {
        public static let base: CGFloat = {{spacing.base_unit}}
        public static let xs:   CGFloat = {{spacing.base_unit}}       //  1x
        public static let sm:   CGFloat = {{spacing.base_unit}} * 2   //  2x
        public static let md:   CGFloat = {{spacing.base_unit}} * 3   //  3x
        public static let lg:   CGFloat = {{spacing.base_unit}} * 4   //  4x
        public static let xl:   CGFloat = {{spacing.base_unit}} * 6   //  6x
        public static let xxl:  CGFloat = {{spacing.base_unit}} * 8   //  8x
        public static let x3l:  CGFloat = {{spacing.base_unit}} * 12  // 12x
        public static let x4l:  CGFloat = {{spacing.base_unit}} * 16  // 16x
    }

    // MARK: Radius
    public enum Radius {
        public static let sm:   CGFloat = {{spacing.radius.sm}}
        public static let md:   CGFloat = {{spacing.radius.md}}
        public static let lg:   CGFloat = {{spacing.radius.lg}}
        public static let full: CGFloat = {{spacing.radius.full}}
    }

    // MARK: Motion
    public enum Motion {
        // Durations in seconds (SwiftUI animation API takes seconds; YAML is ms).
        public static let durationFast: Double = {{motion.duration_fast}} / 1000.0
        public static let durationBase: Double = {{motion.duration_base}} / 1000.0
        public static let durationSlow: Double = {{motion.duration_slow}} / 1000.0

        // Built-in SwiftUI curves closest to the YAML cubic-beziers.
        // For exact parity, supply a custom timing curve via `Animation.timingCurve(_:_:_:_:)`.
        public static let easeOut:      Animation = .easeOut(duration: durationBase)
        public static let easeDramatic: Animation = .timingCurve(0.19, 1.0, 0.22, 1.0, duration: durationBase)
    }
}
