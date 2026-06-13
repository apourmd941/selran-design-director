// Button.swift — dark-premium
// Tokens:
//   color.bg_primary   → #0A0A0B   (deep, not pure black)
//   color.fg_primary   → #F4F4F5   (soft white, not pure)
//   color.accent       → #D4AF37   (muted gold)
//   color.border       → #27272A
//   spacing.radius.sm  → 4pt
//   type.base (16pt)   → Söhne; letter-spaced +0.1em
// No dark-mode switch — this direction is dark by default per aesthetic brief.

import SwiftUI

struct DSDarkPremiumButton: View {
    enum Variant { case primary, secondary }
    let title: String
    var variant: Variant = .primary
    var isLoading: Bool = false
    var action: () -> Void = {}

    private let bg = Color(red: 0.039, green: 0.039, blue: 0.043)       // #0A0A0B
    private let fg = Color(red: 0.957, green: 0.957, blue: 0.961)       // #F4F4F5
    private let gold = Color(red: 0.831, green: 0.686, blue: 0.216)     // #D4AF37
    private let border = Color(red: 0.153, green: 0.153, blue: 0.165)   // #27272A

    var body: some View {
        Button(action: action) {
            HStack(spacing: 8) {
                if isLoading {
                    ProgressView().controlSize(.small)
                        .tint(variant == .primary ? gold : fg)
                }
                Text(title.uppercased())
                    .font(.custom("Söhne-Buch", size: 13, relativeTo: .footnote))
                    .tracking(0.10 * 13) // letter-spaced — luxury brief
            }
            .padding(.horizontal, 22)
            .frame(minHeight: 48)
            .frame(maxWidth: .infinity)
            .foregroundStyle(variant == .primary ? gold : fg)
            .background(
                RoundedRectangle(cornerRadius: 4, style: .continuous)
                    .fill(variant == .primary ? bg : Color.clear)
            )
            .overlay(
                RoundedRectangle(cornerRadius: 4, style: .continuous)
                    .strokeBorder(variant == .primary ? gold.opacity(0.6) : border, lineWidth: 1)
            )
        }
        .buttonStyle(.plain)
        .disabled(isLoading)
        .animation(.timingCurve(0.19, 1, 0.22, 1, duration: 0.35), value: isLoading)
    }
}

#Preview("Dark-Premium Button") {
    VStack(spacing: 16) {
        DSDarkPremiumButton(title: "Reserve", variant: .primary)
        DSDarkPremiumButton(title: "Learn more", variant: .secondary)
        DSDarkPremiumButton(title: "Processing", variant: .primary, isLoading: true)
    }
    .padding(32)
    .background(Color(red: 0.039, green: 0.039, blue: 0.043))
    .preferredColorScheme(.dark)
}
