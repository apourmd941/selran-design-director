// Button.swift — brutalist
// Tokens:
//   color.bg_primary → #FFFFFF / dark #000000
//   color.fg_primary → #000000 / dark #FFFFFF
//   color.accent     → #B84400 / dark #FF6B00
//   spacing.radius.*  → 0 (no rounding, ever)
//   type.body        → Space Mono 14pt (mono everywhere, per personality)
//   motion           → duration_* = 0; linear only. No transition.
// Signature: press inversion — bg becomes fg and vice versa. No scale, no shadow.

import SwiftUI

struct DSBrutalistButton: View {
    enum Variant { case primary, secondary }
    let title: String
    var variant: Variant = .primary
    var isLoading: Bool = false
    var action: () -> Void = {}

    @Environment(\.colorScheme) private var scheme
    @State private var pressed = false

    private var ink: Color { scheme == .dark ? .white : .black }
    private var paper: Color { scheme == .dark ? .black : .white }
    private var accent: Color {
        scheme == .dark ? Color(red: 1.0, green: 0.420, blue: 0.0)
                        : Color(red: 0.722, green: 0.267, blue: 0.0)
    }

    // Inverted fills on press — signature brutalist hover-inversion gesture.
    private var fill: Color {
        if variant == .primary {
            return pressed ? paper : ink
        } else {
            return pressed ? ink : paper
        }
    }
    private var fgColor: Color {
        if variant == .primary {
            return pressed ? ink : paper
        } else {
            return pressed ? paper : ink
        }
    }

    var body: some View {
        Button(action: action) {
            HStack(spacing: 10) {
                if isLoading { Text("[...]") }
                Text(title.uppercased())
                    .font(.custom("SpaceMono-Bold", size: 14, relativeTo: .body))
                    .tracking(0.05 * 14)
            }
            .padding(.horizontal, 18)
            .frame(minHeight: 48)
            .frame(maxWidth: .infinity)
            .foregroundStyle(variant == .primary ? fgColor : (isLoading ? accent : fgColor))
            .background(Rectangle().fill(fill)) // 0 radius
            .overlay(Rectangle().strokeBorder(ink, lineWidth: 2))
        }
        .buttonStyle(.plain)
        .disabled(isLoading)
        ._onButtonGesture(pressing: { pressed = $0 }, perform: {})
        // No animation — brutalist demands instant state flips.
    }
}

#Preview("Brutalist Button") {
    VStack(spacing: 12) {
        DSBrutalistButton(title: "Submit", variant: .primary)
        DSBrutalistButton(title: "Cancel", variant: .secondary)
        DSBrutalistButton(title: "Loading", variant: .primary, isLoading: true)
    }
    .padding(24)
    .background(Color.white)
}

#Preview("Brutalist Button — Dark") {
    VStack(spacing: 12) {
        DSBrutalistButton(title: "Submit", variant: .primary)
        DSBrutalistButton(title: "Cancel", variant: .secondary)
    }
    .padding(24)
    .background(Color.black)
    .preferredColorScheme(.dark)
}
