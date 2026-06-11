// Card.swift — warm-approachable
// Tokens:
//   color.bg_secondary → #F3E8D3 / dark #2B1F18
//   color.fg_primary   → #2D1B0F / dark #F5E8D3
//   color.fg_muted     → #7A6250
//   color.accent       → #B04A2C
//   spacing.radius.lg  → 24pt (cards feel like paper, not glass)
// Warm-tinted soft shadow instead of hairline border per personality rule.
// Italic Fraunces for optional pull-quote style.

import SwiftUI

struct DSWarmCard<Action: View>: View {
    let title: String
    let body: String
    var pullQuote: String? = nil
    @ViewBuilder var action: () -> Action

    @Environment(\.colorScheme) private var scheme

    private var bg: Color {
        scheme == .dark ? Color(red: 0.169, green: 0.122, blue: 0.094)
                        : Color(red: 0.953, green: 0.910, blue: 0.827)
    }
    private var fg: Color {
        scheme == .dark ? Color(red: 0.961, green: 0.910, blue: 0.827)
                        : Color(red: 0.176, green: 0.106, blue: 0.059)
    }
    private var muted: Color {
        scheme == .dark ? Color(red: 0.659, green: 0.580, blue: 0.471)
                        : Color(red: 0.478, green: 0.384, blue: 0.314)
    }
    private var accent: Color {
        scheme == .dark ? Color(red: 0.910, green: 0.439, blue: 0.251)
                        : Color(red: 0.690, green: 0.290, blue: 0.173)
    }

    var body: some View {
        VStack(alignment: .leading, spacing: 14) {
            Text(title)
                .font(.custom("Fraunces-Medium", size: 24, relativeTo: .title2))
                .foregroundStyle(fg)
            if let pullQuote {
                Text("\u{201C}\(pullQuote)\u{201D}")
                    .font(.custom("Fraunces-RegularItalic", size: 20, relativeTo: .title3))
                    .foregroundStyle(accent)
                    .lineSpacing(4)
            }
            Text(body)
                .font(.custom("Commissioner-Regular", size: 17, relativeTo: .body))
                .foregroundStyle(muted)
                .lineSpacing(6)   // leading 1.65
            action()
                .padding(.top, 4)
        }
        .padding(20)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(
            RoundedRectangle(cornerRadius: 24, style: .continuous).fill(bg)
        )
        .shadow(color: accent.opacity(scheme == .dark ? 0 : 0.10), radius: 18, x: 0, y: 8)
    }
}

#Preview("Warm-Approachable Card") {
    VStack(spacing: 20) {
        DSWarmCard(
            title: "From the neighborhood",
            body: "We opened in 2017 with one espresso machine and a lot of hope.",
            pullQuote: "Come as you are."
        ) {
            Text("Visit the shop →")
                .font(.custom("Commissioner-Medium", size: 15, relativeTo: .callout))
                .foregroundStyle(Color(red: 0.690, green: 0.290, blue: 0.173))
        }
    }
    .padding(24)
    .background(Color(red: 0.980, green: 0.953, blue: 0.906))
}
