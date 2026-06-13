// Card.swift — dark-premium
// Tokens:
//   color.bg_secondary → #151517 (lifted one step above bg_primary — layered dark-on-dark)
//   color.fg_primary   → #F4F4F5
//   color.fg_muted     → #71717A
//   color.accent       → #D4AF37 (gold)
//   color.border       → #27272A (1px)
//   type.lg (20pt) Söhne Semibold → title
// Signature: thin accent underline beneath title; optional hero glow (subtle).

import SwiftUI

struct DSDarkPremiumCard<Action: View>: View {
    let eyebrow: String
    let title: String
    let body: String
    var glow: Bool = false
    @ViewBuilder var action: () -> Action

    private let bg = Color(red: 0.082, green: 0.082, blue: 0.090)    // #151517
    private let fg = Color(red: 0.957, green: 0.957, blue: 0.961)    // #F4F4F5
    private let muted = Color(red: 0.631, green: 0.631, blue: 0.667) // #A1A1AA
    private let faint = Color(red: 0.443, green: 0.443, blue: 0.478) // #71717A
    private let gold = Color(red: 0.831, green: 0.686, blue: 0.216)  // #D4AF37
    private let border = Color(red: 0.153, green: 0.153, blue: 0.165)// #27272A

    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text(eyebrow.uppercased())
                .font(.custom("Söhne-Buch", size: 11, relativeTo: .caption2))
                .tracking(0.10 * 11)
                .foregroundStyle(faint)
            Text(title)
                .font(.custom("Söhne-Halbfett", size: 20, relativeTo: .title3))
                .foregroundStyle(fg)
            Rectangle().fill(gold).frame(width: 32, height: 1) // thin accent underline
            Text(body)
                .font(.custom("Söhne-Buch", size: 15, relativeTo: .body))
                .foregroundStyle(muted)
                .lineSpacing(5)
            action()
                .padding(.top, 6)
        }
        .padding(20)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(
            RoundedRectangle(cornerRadius: 10, style: .continuous)
                .fill(bg)
                .background {
                    if glow {
                        // One hero glow per screen — subtle
                        RadialGradient(colors: [gold.opacity(0.18), Color.clear],
                                       center: .topTrailing, startRadius: 4, endRadius: 240)
                            .blur(radius: 20)
                    }
                }
        )
        .overlay(
            RoundedRectangle(cornerRadius: 10, style: .continuous)
                .strokeBorder(border, lineWidth: 1)
        )
    }
}

#Preview("Dark-Premium Card") {
    VStack(spacing: 20) {
        DSDarkPremiumCard(
            eyebrow: "Edition N°7",
            title: "Hand-finished in Geneva",
            body: "Each movement assembled over forty hours by a single watchmaker.",
            glow: true
        ) {
            Text("VIEW COLLECTION")
                .font(.custom("Söhne-Buch", size: 11, relativeTo: .caption2))
                .tracking(0.10 * 11)
                .foregroundStyle(Color(red: 0.831, green: 0.686, blue: 0.216))
        }
        DSDarkPremiumCard(eyebrow: "Figure",
                          title: "842 units",
                          body: "Annual production, by design.") { EmptyView() }
    }
    .padding(24)
    .background(Color(red: 0.039, green: 0.039, blue: 0.043))
    .preferredColorScheme(.dark)
}
