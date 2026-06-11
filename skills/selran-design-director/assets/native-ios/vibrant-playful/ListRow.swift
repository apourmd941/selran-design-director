// ListRow.swift — vibrant-playful
// Tokens:
//   color.palette[i] → icon tint (per-row, color-coded by category)
//   color.fg_primary / fg_muted / border
//   spacing.radius.md → 12pt for icon tile
//   type.base (16pt/500) Satoshi → title

import SwiftUI

struct DSVibrantListRow: View {
    let systemIcon: String
    let title: String
    let subtitle: String?
    let hue: Color            // category palette hue
    var action: () -> Void = {}

    @Environment(\.colorScheme) private var scheme

    private var fg: Color {
        scheme == .dark ? Color(red: 1.0, green: 0.984, blue: 0.961)
                        : Color(red: 0.102, green: 0.102, blue: 0.102)
    }
    private var muted: Color {
        scheme == .dark ? Color(red: 0.600, green: 0.557, blue: 0.490)
                        : Color(red: 0.416, green: 0.416, blue: 0.416)
    }
    private var border: Color {
        scheme == .dark ? Color(red: 0.200, green: 0.173, blue: 0.133)
                        : Color(red: 0.910, green: 0.875, blue: 0.788)
    }

    var body: some View {
        Button(action: action) {
            HStack(spacing: 14) {
                Image(systemName: systemIcon)
                    .font(.system(size: 18, weight: .semibold))
                    .foregroundStyle(hue)
                    .frame(width: 40, height: 40)
                    .background(
                        RoundedRectangle(cornerRadius: 12).fill(hue.opacity(0.15))
                    )
                VStack(alignment: .leading, spacing: 3) {
                    Text(title)
                        .font(.custom("Satoshi-Medium", size: 16, relativeTo: .body))
                        .foregroundStyle(fg)
                    if let subtitle {
                        Text(subtitle)
                            .font(.custom("Satoshi-Regular", size: 14, relativeTo: .footnote))
                            .foregroundStyle(muted)
                    }
                }
                Spacer()
                Image(systemName: "chevron.forward")
                    .font(.system(size: 13, weight: .semibold))
                    .foregroundStyle(muted)
            }
            .padding(.horizontal, 16)
            .frame(minHeight: 60)
            .contentShape(Rectangle())
        }
        .buttonStyle(.plain)
        .overlay(alignment: .bottom) {
            Rectangle().fill(border).frame(height: 1).padding(.leading, 70)
        }
    }
}

#Preview("Vibrant-Playful ListRow") {
    let p = [
        Color(red: 0.769, green: 0.290, blue: 0.180),
        Color(red: 0.122, green: 0.420, blue: 0.533),
        Color(red: 0.612, green: 0.376, blue: 0.078),
        Color(red: 0.333, green: 0.243, blue: 0.353),
        Color(red: 0.165, green: 0.482, blue: 0.376)
    ]
    return VStack(spacing: 0) {
        DSVibrantListRow(systemIcon: "sparkles", title: "What's new", subtitle: "3 releases this week", hue: p[0])
        DSVibrantListRow(systemIcon: "chart.pie", title: "Usage", subtitle: "On track", hue: p[1])
        DSVibrantListRow(systemIcon: "person.3", title: "Team", subtitle: "12 members", hue: p[4])
        DSVibrantListRow(systemIcon: "book", title: "Learn", subtitle: "Guides and tutorials", hue: p[3])
    }
    .padding(.vertical, 8)
    .background(Color(red: 1.0, green: 0.984, blue: 0.961))
}
