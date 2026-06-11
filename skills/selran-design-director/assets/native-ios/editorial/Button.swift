// Button.swift — editorial
// Tokens:
//   color.bg_primary  → #F5F1EA (paper) / dark #14110D
//   color.fg_primary  → #1A1A1A          / dark #F5F1EA
//   color.accent      → #7A1F1F (oxblood)/ dark #D85E5E
//   spacing.radius.sm → 2pt (near-flat, print feel)
//   type.base (17pt)  → Söhne body; underline on pressed as a typographic gesture
// Generous horizontal padding per editorial's whitespace principle.

import SwiftUI

struct DSEditorialButton: View {
    enum Variant { case primary, secondary }
    let title: String
    var variant: Variant = .primary
    var isLoading: Bool = false
    var action: () -> Void = {}

    @Environment(\.colorScheme) private var scheme
    @State private var pressed = false

    private var ink: Color {
        scheme == .dark ? Color(red: 0.961, green: 0.945, blue: 0.918)
                        : Color(red: 0.102, green: 0.102, blue: 0.102)
    }
    private var paper: Color {
        scheme == .dark ? Color(red: 0.078, green: 0.067, blue: 0.051)
                        : Color(red: 0.961, green: 0.945, blue: 0.918)
    }
    private var accent: Color {
        scheme == .dark ? Color(red: 0.847, green: 0.369, blue: 0.369)
                        : Color(red: 0.478, green: 0.122, blue: 0.122)
    }

    var body: some View {
        Button {
            action()
        } label: {
            HStack(spacing: 8) {
                if isLoading { ProgressView().controlSize(.small).tint(variant == .primary ? paper : ink) }
                Text(title)
                    .font(.custom("Söhne-Buch", size: 16, relativeTo: .body))
                    .tracking(0.2)
                    .underline(pressed, color: variant == .secondary ? accent : paper)
            }
            .padding(.horizontal, 28) // generous
            .frame(minHeight: 48)
            .frame(maxWidth: .infinity)
            .foregroundStyle(variant == .primary ? paper : ink)
            .background(
                RoundedRectangle(cornerRadius: 2, style: .continuous)
                    .fill(variant == .primary ? accent : Color.clear)
            )
            .overlay(
                RoundedRectangle(cornerRadius: 2, style: .continuous)
                    .strokeBorder(variant == .secondary ? ink.opacity(0.5) : Color.clear, lineWidth: 1)
            )
        }
        .buttonStyle(.plain)
        .disabled(isLoading)
        ._onButtonGesture(pressing: { pressed = $0 }, perform: {})
        .animation(.easeOut(duration: 0.2), value: pressed)
    }
}

#Preview("Editorial Button") {
    VStack(spacing: 16) {
        DSEditorialButton(title: "Subscribe", variant: .primary)
        DSEditorialButton(title: "Read excerpt", variant: .secondary)
        DSEditorialButton(title: "Loading", variant: .primary, isLoading: true)
    }
    .padding(32)
    .background(Color(red: 0.961, green: 0.945, blue: 0.918))
}

#Preview("Editorial Button — Dark") {
    VStack(spacing: 16) {
        DSEditorialButton(title: "Subscribe", variant: .primary)
        DSEditorialButton(title: "Read excerpt", variant: .secondary)
    }
    .padding(32)
    .background(Color(red: 0.078, green: 0.067, blue: 0.051))
    .preferredColorScheme(.dark)
}
