// Button.swift — bold-distinctive
// Tokens:
//   color.bg_primary → #F5F0E8  (cream)   / dark #0A0A0B
//   color.fg_primary → #0A0A0B             / dark #F5F0E8
//   color.accent     → #C72500  (hot red) / dark #FF5530
//   spacing.radius.sm → 0 (sharp; the button is a block)
//   type.body (17pt) → General Sans 500; labels feel confident, not subtle.

import SwiftUI

struct DSBoldButton: View {
    enum Variant { case primary, secondary }
    let title: String
    var variant: Variant = .primary
    var isLoading: Bool = false
    var action: () -> Void = {}

    @Environment(\.colorScheme) private var scheme

    private var ink: Color {
        scheme == .dark ? Color(red: 0.961, green: 0.941, blue: 0.910)
                        : Color(red: 0.039, green: 0.039, blue: 0.043)
    }
    private var cream: Color {
        scheme == .dark ? Color(red: 0.039, green: 0.039, blue: 0.043)
                        : Color(red: 0.961, green: 0.941, blue: 0.910)
    }
    private var red: Color {
        scheme == .dark ? Color(red: 1.0, green: 0.333, blue: 0.188)
                        : Color(red: 0.780, green: 0.145, blue: 0.0)
    }

    var body: some View {
        Button(action: action) {
            HStack(spacing: 10) {
                if isLoading { ProgressView().controlSize(.small).tint(cream) }
                Text(title)
                    .font(.custom("GeneralSans-Semibold", size: 17, relativeTo: .body))
                    .tracking(0.02 * 17)
                if !isLoading {
                    Image(systemName: "arrow.forward")
                        .font(.system(size: 16, weight: .bold))
                }
            }
            .padding(.horizontal, 24)
            .frame(minHeight: 54)  // a little taller — wants presence
            .frame(maxWidth: .infinity)
            .foregroundStyle(variant == .primary ? cream : ink)
            .background(
                Rectangle().fill(variant == .primary ? red : Color.clear)
            )
            .overlay(
                Rectangle()
                    .strokeBorder(variant == .secondary ? ink : Color.clear, lineWidth: 2)
            )
        }
        .buttonStyle(.plain)
        .disabled(isLoading)
        .animation(.timingCurve(0.2, 0, 0, 1, duration: 0.25), value: isLoading)
    }
}

#Preview("Bold-Distinctive Button") {
    VStack(spacing: 16) {
        DSBoldButton(title: "Start the work", variant: .primary)
        DSBoldButton(title: "Read manifesto", variant: .secondary)
        DSBoldButton(title: "Submitting", variant: .primary, isLoading: true)
    }
    .padding(28)
    .background(Color(red: 0.961, green: 0.941, blue: 0.910))
}

#Preview("Bold-Distinctive Button — Dark") {
    VStack(spacing: 16) {
        DSBoldButton(title: "Start the work", variant: .primary)
        DSBoldButton(title: "Read manifesto", variant: .secondary)
    }
    .padding(28)
    .background(Color(red: 0.039, green: 0.039, blue: 0.043))
    .preferredColorScheme(.dark)
}
