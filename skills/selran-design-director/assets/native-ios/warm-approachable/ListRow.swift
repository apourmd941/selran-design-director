// ListRow.swift — warm-approachable
// Tokens:
//   color.fg_primary → #2D1B0F / dark #F5E8D3
//   color.fg_muted   → #7A6250
//   color.accent     → #B04A2C
//   color.border     → #E0CEB0 (soft) / dark #3A2B21
//   type.base (17pt) Commissioner → title; type.sm (15pt) Fraunces-italic → subtitle
// Icon in a rounded-square tinted well — friendlier than a bare symbol.

import SwiftUI

struct DSWarmListRow: View {
    let systemIcon: String
    let title: String
    let subtitle: String?
    var action: () -> Void = {}

    @Environment(\.colorScheme) private var scheme

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
    private var border: Color {
        scheme == .dark ? Color(red: 0.227, green: 0.169, blue: 0.129)
                        : Color(red: 0.878, green: 0.808, blue: 0.690)
    }

    var body: some View {
        Button(action: action) {
            HStack(spacing: 14) {
                Image(systemName: systemIcon)
                    .font(.system(size: 17, weight: .medium))
                    .foregroundStyle(accent)
                    .frame(width: 40, height: 40)
                    .background(
                        RoundedRectangle(cornerRadius: 12).fill(accent.opacity(0.12))
                    )
                VStack(alignment: .leading, spacing: 3) {
                    Text(title)
                        .font(.custom("Commissioner-Medium", size: 17, relativeTo: .body))
                        .foregroundStyle(fg)
                    if let subtitle {
                        Text(subtitle)
                            .font(.custom("Fraunces-RegularItalic", size: 15, relativeTo: .callout))
                            .foregroundStyle(muted)
                    }
                }
                Spacer()
                Image(systemName: "chevron.forward")
                    .font(.system(size: 13, weight: .medium))
                    .foregroundStyle(muted)
            }
            .padding(.horizontal, 16)
            .frame(minHeight: 64)
            .contentShape(Rectangle())
        }
        .buttonStyle(.plain)
        .overlay(alignment: .bottom) {
            Rectangle().fill(border).frame(height: 1).padding(.leading, 70)
        }
    }
}

#Preview("Warm-Approachable ListRow") {
    VStack(spacing: 0) {
        DSWarmListRow(systemIcon: "cup.and.saucer", title: "Morning menu", subtitle: "fresh, daily")
        DSWarmListRow(systemIcon: "leaf", title: "Our suppliers", subtitle: "within 50 miles")
        DSWarmListRow(systemIcon: "heart", title: "Gift a friend", subtitle: "small gestures")
    }
    .padding(.vertical, 8)
    .background(Color(red: 0.980, green: 0.953, blue: 0.906))
}
