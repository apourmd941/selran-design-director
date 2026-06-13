// ListRow.swift — technical-minimal
// Tokens:
//   color.fg_primary → #18181B / dark #F4F4F5
//   color.fg_muted   → #71717A
//   color.border     → #E4E4E7 / dark #27272A
//   type.sm (14pt)   → subtitle; type.base (16pt/500) → title
// Hairline 1px divider under row (not default iOS separator) per direction personality.

import SwiftUI

struct DSTechnicalListRow: View {
    let systemIcon: String
    let title: String
    let subtitle: String?
    var action: () -> Void = {}

    @Environment(\.colorScheme) private var scheme

    private var fg: Color {
        scheme == .dark ? Color(red: 0.957, green: 0.957, blue: 0.961)
                        : Color(red: 0.094, green: 0.094, blue: 0.106)
    }
    private var muted: Color { Color(red: 0.443, green: 0.443, blue: 0.478) }
    private var border: Color {
        scheme == .dark ? Color(red: 0.153, green: 0.153, blue: 0.165)
                        : Color(red: 0.894, green: 0.894, blue: 0.906)
    }

    var body: some View {
        Button(action: action) {
            HStack(spacing: 12) {
                Image(systemName: systemIcon)
                    .font(.system(size: 16, weight: .regular))
                    .foregroundStyle(muted)
                    .frame(width: 20)
                VStack(alignment: .leading, spacing: 2) {
                    Text(title)
                        .font(.custom("GeneralSans-Medium", size: 16, relativeTo: .body))
                        .foregroundStyle(fg)
                    if let subtitle {
                        Text(subtitle)
                            .font(.custom("GeneralSans-Regular", size: 14, relativeTo: .footnote))
                            .foregroundStyle(muted)
                    }
                }
                Spacer()
                Image(systemName: "chevron.forward") // auto-mirrors for RTL
                    .font(.system(size: 13, weight: .medium))
                    .foregroundStyle(muted)
            }
            .padding(.horizontal, 16)
            .frame(minHeight: 52) // exceeds 44pt hit target
            .contentShape(Rectangle())
        }
        .buttonStyle(.plain)
        .overlay(alignment: .bottom) {
            Rectangle().fill(border).frame(height: 1)
        }
    }
}

#Preview("Technical-Minimal ListRow") {
    VStack(spacing: 0) {
        DSTechnicalListRow(systemIcon: "key", title: "API Keys", subtitle: "4 active")
        DSTechnicalListRow(systemIcon: "chart.bar", title: "Usage", subtitle: "14.8K this month")
        DSTechnicalListRow(systemIcon: "person.2", title: "Team", subtitle: nil)
    }
    .background(Color(red: 0.98, green: 0.98, blue: 0.976))
    .padding(.vertical, 16)
}
