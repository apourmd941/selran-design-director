// Button.swift — warm-approachable
// Tokens:
//   color.bg_primary → #FAF3E7  (peach/cream) / dark #1F1612
//   color.fg_primary → #2D1B0F  (warm charcoal) / dark #F5E8D3
//   color.accent     → #B04A2C  (terracotta) / dark #E87040
//   spacing.radius.md → 16pt (soft rounding)
//   type.base (17pt) → Commissioner (humanist sans) 500 weight
// Micro-bounce on press: scale 0.97 with spring. Honors reduce-motion.

import SwiftUI

struct DSWarmButton: View {
    enum Variant { case primary, secondary }
    let title: String
    var variant: Variant = .primary
    var isLoading: Bool = false
    var action: () -> Void = {}

    @Environment(\.colorScheme) private var scheme
    @Environment(\.accessibilityReduceMotion) private var reduceMotion
    @State private var pressed = false

    private var fg: Color {
        scheme == .dark ? Color(red: 0.961, green: 0.910, blue: 0.827)
                        : Color(red: 0.176, green: 0.106, blue: 0.059)
    }
    private var paper: Color {
        scheme == .dark ? Color(red: 0.122, green: 0.086, blue: 0.071)
                        : Color(red: 0.980, green: 0.953, blue: 0.906)
    }
    private var accent: Color {
        scheme == .dark ? Color(red: 0.910, green: 0.439, blue: 0.251)
                        : Color(red: 0.690, green: 0.290, blue: 0.173)
    }

    var body: some View {
        Button(action: action) {
            HStack(spacing: 8) {
                if isLoading {
                    ProgressView().controlSize(.small)
                        .tint(variant == .primary ? paper : accent)
                }
                Text(title)
                    .font(.custom("Commissioner-Medium", size: 17, relativeTo: .body))
            }
            .padding(.horizontal, 22)
            .frame(minHeight: 50)
            .frame(maxWidth: .infinity)
            .foregroundStyle(variant == .primary ? paper : accent)
            .background(
                RoundedRectangle(cornerRadius: 16, style: .continuous)
                    .fill(variant == .primary ? accent : Color.clear)
            )
            .overlay(
                RoundedRectangle(cornerRadius: 16, style: .continuous)
                    .strokeBorder(variant == .secondary ? accent.opacity(0.4) : Color.clear, lineWidth: 1.5)
            )
        }
        .buttonStyle(.plain)
        .disabled(isLoading)
        .scaleEffect(pressed ? 0.97 : 1.0)
        .animation(reduceMotion ? .linear(duration: 0.01)
                                 : .spring(response: 0.35, dampingFraction: 0.72), value: pressed)
        ._onButtonGesture(pressing: { pressed = $0 }, perform: {})
    }
}

#Preview("Warm-Approachable Button") {
    VStack(spacing: 16) {
        DSWarmButton(title: "Say hello", variant: .primary)
        DSWarmButton(title: "Maybe later", variant: .secondary)
        DSWarmButton(title: "Sending", variant: .primary, isLoading: true)
    }
    .padding(28)
    .background(Color(red: 0.980, green: 0.953, blue: 0.906))
}

#Preview("Warm-Approachable Button — Dark") {
    VStack(spacing: 16) {
        DSWarmButton(title: "Say hello", variant: .primary)
        DSWarmButton(title: "Maybe later", variant: .secondary)
    }
    .padding(28)
    .background(Color(red: 0.122, green: 0.086, blue: 0.071))
    .preferredColorScheme(.dark)
}
