// Card.swift — editorial
// Tokens:
//   color.bg_secondary → #EEE8DC / dark #1D1A15
//   color.fg_primary   → #1A1A1A / dark #F5F1EA
//   color.border       → #D6CFBE (thin rule) / dark #2A2620
//   type.display → Fraunces serif; type.caps → tracked sans
// Signature: small-caps eyebrow above title, rule line between sections.

import SwiftUI

struct DSEditorialCard<Action: View>: View {
    let eyebrow: String  // small-caps label
    let title: String
    let body: String
    @ViewBuilder var action: () -> Action

    @Environment(\.colorScheme) private var scheme

    private var bg: Color {
        scheme == .dark ? Color(red: 0.114, green: 0.102, blue: 0.082)
                        : Color(red: 0.933, green: 0.910, blue: 0.863)
    }
    private var ink: Color {
        scheme == .dark ? Color(red: 0.961, green: 0.945, blue: 0.918)
                        : Color(red: 0.102, green: 0.102, blue: 0.102)
    }
    private var muted: Color {
        scheme == .dark ? Color(red: 0.553, green: 0.529, blue: 0.459)
                        : Color(red: 0.420, green: 0.420, blue: 0.420)
    }
    private var rule: Color {
        scheme == .dark ? Color(red: 0.165, green: 0.149, blue: 0.125)
                        : Color(red: 0.839, green: 0.812, blue: 0.745)
    }

    var body: some View {
        VStack(alignment: .leading, spacing: 14) {
            Text(eyebrow.uppercased())
                .font(.custom("Söhne-Buch", size: 11, relativeTo: .caption2))
                .tracking(0.12 * 11) // caps tracking
                .foregroundStyle(muted)
            Text(title)
                .font(.custom("Fraunces-Medium", size: 28, relativeTo: .title))
                .tracking(-0.01 * 28)
                .foregroundStyle(ink)
                .lineSpacing(2)
            Rectangle().fill(rule).frame(height: 1)
            Text(body)
                .font(.custom("Söhne-Buch", size: 17, relativeTo: .body))
                .foregroundStyle(ink.opacity(0.85))
                .lineSpacing(6)   // leading 1.65
            action()
                .padding(.top, 4)
        }
        .padding(24)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(bg)
    }
}

#Preview("Editorial Card") {
    DSEditorialCard(
        eyebrow: "Chapter 02",
        title: "A quiet revolution in publishing",
        body: "Typography, long considered decoration, has returned to its proper place — as the argument itself."
    ) {
        Text("Continue reading →")
            .font(.custom("Fraunces-Regular", size: 15, relativeTo: .callout).italic())
            .foregroundStyle(Color(red: 0.478, green: 0.122, blue: 0.122))
    }
    .padding(24)
    .background(Color(red: 0.961, green: 0.945, blue: 0.918))
}
