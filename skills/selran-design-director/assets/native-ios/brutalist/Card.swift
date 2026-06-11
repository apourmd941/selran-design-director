// Card.swift — brutalist
// Tokens:
//   color.bg_primary   → #FFFFFF / dark #000000
//   color.fg_primary   → #000000 / dark #FFFFFF
//   color.border       → #000000 (2px solid)
//   color.accent       → #B84400
//   spacing.radius.*   → 0
//   type.body          → Space Mono
// Signature: file-size / timestamp metadata displayed prominently; 2px borders, no shadow.

import SwiftUI

struct DSBrutalistCard<Action: View>: View {
    let label: String        // e.g. "NOTE // 4.2 KB // 2026-04-21"
    let title: String
    let body: String
    @ViewBuilder var action: () -> Action

    @Environment(\.colorScheme) private var scheme

    private var ink: Color { scheme == .dark ? .white : .black }
    private var paper: Color { scheme == .dark ? .black : .white }

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            // Metadata header bar — mono, inverted
            Text(label.uppercased())
                .font(.custom("SpaceMono-Regular", size: 11, relativeTo: .caption2))
                .tracking(0.05 * 11)
                .foregroundStyle(paper)
                .padding(.horizontal, 12).padding(.vertical, 6)
                .frame(maxWidth: .infinity, alignment: .leading)
                .background(Rectangle().fill(ink))

            VStack(alignment: .leading, spacing: 12) {
                Text(title.uppercased())
                    .font(.custom("SpaceMono-Bold", size: 20, relativeTo: .title3))
                    .foregroundStyle(ink)
                Text(body)
                    .font(.custom("SpaceMono-Regular", size: 14, relativeTo: .body))
                    .foregroundStyle(ink)
                    .lineSpacing(3)
                Rectangle().fill(ink).frame(height: 1)
                action()
            }
            .padding(16)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(Rectangle().fill(paper))
        .overlay(Rectangle().strokeBorder(ink, lineWidth: 2))
    }
}

#Preview("Brutalist Card") {
    VStack(spacing: 20) {
        DSBrutalistCard(
            label: "NOTE // 4.2 KB // 2026-04-21",
            title: "Read the source",
            body: "No dependencies. No tracking. No newsletter popup. Just HTML and words."
        ) {
            Text("[OPEN]")
                .font(.custom("SpaceMono-Bold", size: 13, relativeTo: .footnote))
                .underline()
                .foregroundStyle(Color(red: 0.722, green: 0.267, blue: 0.0))
        }
        DSBrutalistCard(
            label: "LOG // 932 B",
            title: "System up",
            body: "Uptime 14d 03h 12m. No incidents."
        ) { EmptyView() }
    }
    .padding(20)
    .background(Color.white)
}

#Preview("Brutalist Card — Dark") {
    DSBrutalistCard(
        label: "NOTE // 4.2 KB // 2026-04-21",
        title: "Read the source",
        body: "No dependencies. No tracking. No newsletter popup."
    ) { EmptyView() }
    .padding(20)
    .background(Color.black)
    .preferredColorScheme(.dark)
}
