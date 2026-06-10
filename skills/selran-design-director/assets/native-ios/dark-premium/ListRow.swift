// ListRow.swift — dark-premium
// Tokens:
//   color.fg_primary → #F4F4F5
//   color.fg_muted   → #71717A
//   color.accent     → #D4AF37 (used for selected icon)
//   color.border     → #27272A (1px divider)
//   type.base (16pt) Söhne → title; type.sm (14pt) tracked → subtitle

import SwiftUI

struct DSDarkPremiumListRow: View {
    let systemIcon: String
    let title: String
    let subtitle: String?
    var selected: Bool = false
    var action: () -> Void = {}

    private let fg = Color(red: 0.957, green: 0.957, blue: 0.961)
    private let muted = Color(red: 0.631, green: 0.631, blue: 0.667)
    private let faint = Color(red: 0.443, green: 0.443, blue: 0.478)
    private let gold = Color(red: 0.831, green: 0.686, blue: 0.216)
    private let border = Color(red: 0.153, green: 0.153, blue: 0.165)

    var body: some View {
        Button(action: action) {
            HStack(spacing: 14) {
                Image(systemName: systemIcon)
                    .font(.system(size: 16, weight: .light))
                    .foregroundStyle(selected ? gold : muted)
                    .frame(width: 22)
                VStack(alignment: .leading, spacing: 3) {
                    Text(title)
                        .font(.custom("Söhne-Buch", size: 16, relativeTo: .body))
                        .foregroundStyle(fg)
                    if let subtitle {
                        Text(subtitle.uppercased())
                            .font(.custom("Söhne-Buch", size: 11, relativeTo: .caption2))
                            .tracking(0.10 * 11)
                            .foregroundStyle(faint)
                    }
                }
                Spacer()
                Image(systemName: "chevron.forward")
                    .font(.system(size: 12, weight: .light))
                    .foregroundStyle(faint)
            }
            .padding(.horizontal, 20)
            .frame(minHeight: 56)
            .contentShape(Rectangle())
        }
        .buttonStyle(.plain)
        .overlay(alignment: .bottom) {
            Rectangle().fill(border).frame(height: 1).padding(.leading, 56)
        }
    }
}

#Preview("Dark-Premium ListRow") {
    VStack(spacing: 0) {
        DSDarkPremiumListRow(systemIcon: "crown", title: "Private Collection",
                             subtitle: "By appointment", selected: true)
        DSDarkPremiumListRow(systemIcon: "sparkles", title: "Limited Editions",
                             subtitle: "Edition N°7 · 842 units")
        DSDarkPremiumListRow(systemIcon: "person.crop.circle", title: "Concierge",
                             subtitle: "Available 24 / 7")
    }
    .background(Color(red: 0.039, green: 0.039, blue: 0.043))
    .preferredColorScheme(.dark)
}
