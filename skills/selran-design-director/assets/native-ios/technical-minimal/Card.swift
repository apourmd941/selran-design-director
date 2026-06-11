// Card.swift — technical-minimal
// Tokens:
//   color.bg_secondary  → #F4F4F5  / dark #151517
//   color.fg_primary    → #18181B  / dark #F4F4F5
//   color.fg_muted      → #71717A
//   color.border        → #E4E4E7  / dark #27272A
//   spacing.radius.md   → 8pt
//   type.lg (20pt/600)  → title; type.base (16pt/400) → body
// No shadows — hairline border instead, per personality rule.

import SwiftUI

struct DSTechnicalCard<Action: View>: View {
    let title: String
    let body: String
    @ViewBuilder var action: () -> Action

    @Environment(\.colorScheme) private var scheme

    private var bg: Color {
        scheme == .dark ? Color(red: 0.082, green: 0.082, blue: 0.090)
                        : Color(red: 0.957, green: 0.957, blue: 0.961)
    }
    private var fg: Color {
        scheme == .dark ? Color(red: 0.957, green: 0.957, blue: 0.961)
                        : Color(red: 0.094, green: 0.094, blue: 0.106)
    }
    private var muted: Color { Color(red: 0.443, green: 0.443, blue: 0.478) } // #71717A
    private var border: Color {
        scheme == .dark ? Color(red: 0.153, green: 0.153, blue: 0.165)
                        : Color(red: 0.894, green: 0.894, blue: 0.906)
    }

    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text(title)
                .font(.custom("GeneralSans-Semibold", size: 20, relativeTo: .title3))
                .foregroundStyle(fg)
            Text(body)
                .font(.custom("GeneralSans-Regular", size: 16, relativeTo: .body))
                .foregroundStyle(muted)
                .lineSpacing(4)
            action()
                .padding(.top, 4)
        }
        .padding(16)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(
            RoundedRectangle(cornerRadius: 8, style: .continuous).fill(bg)
        )
        .overlay(
            RoundedRectangle(cornerRadius: 8, style: .continuous)
                .strokeBorder(border, lineWidth: 1)
        )
    }
}

#Preview("Technical-Minimal Card") {
    VStack(spacing: 16) {
        DSTechnicalCard(
            title: "Usage this month",
            body: "14,820 requests across 3 environments. No anomalies detected."
        ) {
            Text("View report")
                .font(.custom("GeneralSans-Medium", size: 13, relativeTo: .footnote))
                .foregroundStyle(Color(red: 0.039, green: 0.478, blue: 0.361))
        }
        DSTechnicalCard(
            title: "API status",
            body: "Operational. Last incident 42 days ago."
        ) { EmptyView() }
    }
    .padding(24)
    .background(Color(red: 0.98, green: 0.98, blue: 0.976))
}
