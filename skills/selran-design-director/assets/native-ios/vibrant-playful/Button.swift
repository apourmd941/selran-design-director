// Button.swift — vibrant-playful
// Tokens:
//   color.bg_primary → #FFFBF5 / dark #1A1613
//   color.fg_primary → #1A1A1A / dark #FFFBF5
//   color.accent     → #C44A2E (palette[0]) / dark #FF6B50
//   color.palette    → 5 coordinated hues; this component uses palette[0] by default,
//                      but accepts `hue` to color-code by section.
//   spacing.radius.md → 12pt
//   type.base (16pt) → Satoshi Medium (500)
// Bouncy spring on press + subtle scale. Ease-out-back cubic for easing.

import SwiftUI

struct DSVibrantButton: View {
    enum Variant { case primary, secondary }
    let title: String
    var variant: Variant = .primary
    var isLoading: Bool = false
    var hue: Color? = nil   // override accent (supports palette rotation)
    var action: () -> Void = {}

    @Environment(\.colorScheme) private var scheme
    @Environment(\.accessibilityReduceMotion) private var reduceMotion
    @State private var pressed = false

    // palette[0] default
    private var defaultAccent: Color {
        scheme == .dark ? Color(red: 1.0, green: 0.420, blue: 0.314)
                        : Color(red: 0.769, green: 0.290, blue: 0.180)
    }
    private var accent: Color { hue ?? defaultAccent }
    private var fg: Color {
        scheme == .dark ? Color(red: 1.0, green: 0.984, blue: 0.961)
                        : Color(red: 0.102, green: 0.102, blue: 0.102)
    }

    var body: some View {
        Button(action: action) {
            HStack(spacing: 8) {
                if isLoading { ProgressView().controlSize(.small).tint(.white) }
                Text(title)
                    .font(.custom("Satoshi-Medium", size: 16, relativeTo: .body))
            }
            .padding(.horizontal, 20)
            .frame(minHeight: 48)
            .frame(maxWidth: .infinity)
            .foregroundStyle(variant == .primary ? Color.white : accent)
            .background(
                RoundedRectangle(cornerRadius: 12, style: .continuous)
                    .fill(variant == .primary ? accent : accent.opacity(0.12))
            )
        }
        .buttonStyle(.plain)
        .disabled(isLoading)
        .scaleEffect(pressed ? 0.96 : 1.0)
        .animation(reduceMotion ? .linear(duration: 0.01)
                                 : .spring(response: 0.32, dampingFraction: 0.6), value: pressed)
        ._onButtonGesture(pressing: { pressed = $0 }, perform: {})
    }
}

#Preview("Vibrant-Playful Button") {
    let palette = [
        Color(red: 0.769, green: 0.290, blue: 0.180), // #C44A2E
        Color(red: 0.122, green: 0.420, blue: 0.533), // #1F6B88
        Color(red: 0.612, green: 0.376, blue: 0.078), // #9C6014
        Color(red: 0.333, green: 0.243, blue: 0.353), // #553E5A
        Color(red: 0.165, green: 0.482, blue: 0.376)  // #2A7B60
    ]
    return VStack(spacing: 14) {
        DSVibrantButton(title: "Get started", variant: .primary)
        DSVibrantButton(title: "Learn more", variant: .secondary)
        DSVibrantButton(title: "Category", variant: .primary, hue: palette[1])
        DSVibrantButton(title: "Feature", variant: .primary, hue: palette[4])
    }
    .padding(24)
    .background(Color(red: 1.0, green: 0.984, blue: 0.961))
}
