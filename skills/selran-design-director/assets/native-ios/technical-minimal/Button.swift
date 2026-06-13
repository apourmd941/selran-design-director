// Button.swift — technical-minimal
// Tokens consumed (inlined for snippet portability; wire to a real DS in-project):
//   color.accent        → #0A7A5C       (light)   / #10B981 (dark)
//   color.accent_hover  → #065F47                / #34D399
//   color.fg_primary    → #18181B                / #F4F4F5
//   color.border        → #E4E4E7                / #27272A
//   spacing.radius.md   → 8pt
//   type.body (500)     → 13pt medium, relativeTo: .footnote
//   motion.duration_fast → 150ms, ease-out

import SwiftUI

struct DSTechnicalButton: View {
    enum Variant { case primary, secondary }
    let title: String
    var variant: Variant = .primary
    var isLoading: Bool = false
    var action: () -> Void = {}

    @Environment(\.colorScheme) private var scheme

    private var accent: Color {
        scheme == .dark ? Color(red: 0.063, green: 0.725, blue: 0.506)   // #10B981
                        : Color(red: 0.039, green: 0.478, blue: 0.361)   // #0A7A5C
    }
    private var fg: Color {
        scheme == .dark ? Color(red: 0.957, green: 0.957, blue: 0.961)   // #F4F4F5
                        : Color(red: 0.094, green: 0.094, blue: 0.106)   // #18181B
    }
    private var border: Color {
        scheme == .dark ? Color(red: 0.153, green: 0.153, blue: 0.165)   // #27272A
                        : Color(red: 0.894, green: 0.894, blue: 0.906)   // #E4E4E7
    }

    var body: some View {
        Button(action: action) {
            HStack(spacing: 6) {
                if isLoading {
                    ProgressView().controlSize(.small)
                        .tint(variant == .primary ? .white : fg)
                }
                Text(title)
                    .font(.custom("GeneralSans-Medium", size: 13, relativeTo: .footnote))
                    .tracking(0.1)
            }
            .padding(.horizontal, 14)
            .frame(minHeight: 44) // hit target
            .frame(maxWidth: .infinity)
            .foregroundStyle(variant == .primary ? Color.white : fg)
            .background(
                RoundedRectangle(cornerRadius: 8, style: .continuous)
                    .fill(variant == .primary ? accent : Color.clear)
            )
            .overlay(
                RoundedRectangle(cornerRadius: 8, style: .continuous)
                    .strokeBorder(variant == .primary ? Color.clear : border, lineWidth: 1)
            )
        }
        .buttonStyle(.plain)
        .disabled(isLoading)
        .animation(.timingCurve(0.2, 0, 0, 1, duration: 0.15), value: isLoading)
    }
}

#Preview("Technical-Minimal Button") {
    VStack(spacing: 16) {
        DSTechnicalButton(title: "Continue", variant: .primary)
        DSTechnicalButton(title: "Cancel", variant: .secondary)
        DSTechnicalButton(title: "Saving", variant: .primary, isLoading: true)
    }
    .padding(24)
    .background(Color(red: 0.98, green: 0.98, blue: 0.976))
    .preferredColorScheme(.light)
}

#Preview("Technical-Minimal Button — Dark") {
    VStack(spacing: 16) {
        DSTechnicalButton(title: "Continue", variant: .primary)
        DSTechnicalButton(title: "Cancel", variant: .secondary)
    }
    .padding(24)
    .background(Color(red: 0.039, green: 0.039, blue: 0.043))
    .preferredColorScheme(.dark)
}
