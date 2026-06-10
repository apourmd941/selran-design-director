// ListRow.swift — editorial
// Tokens:
//   color.fg_primary / fg_muted / border (rule line)
//   type.lg (22pt) Fraunces → title; type.xs (13pt) Söhne tracked → byline
// Signature: byline-style metadata in tracked sans beneath serif title; rule line divider.

import SwiftUI

struct DSEditorialListRow: View {
    let systemIcon: String
    let title: String
    let byline: String?  // rendered as small-caps tracked metadata
    var action: () -> Void = {}

    @Environment(\.colorScheme) private var scheme

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
        Button(action: action) {
            HStack(alignment: .top, spacing: 16) {
                Image(systemName: systemIcon)
                    .font(.system(size: 18, weight: .light))
                    .foregroundStyle(muted)
                    .frame(width: 24)
                    .padding(.top, 4)
                VStack(alignment: .leading, spacing: 6) {
                    Text(title)
                        .font(.custom("Fraunces-Medium", size: 22, relativeTo: .title3))
                        .tracking(-0.01 * 22)
                        .foregroundStyle(ink)
                    if let byline {
                        Text(byline.uppercased())
                            .font(.custom("Söhne-Buch", size: 11, relativeTo: .caption2))
                            .tracking(0.12 * 11)
                            .foregroundStyle(muted)
                    }
                }
                Spacer()
                Image(systemName: "chevron.forward")
                    .font(.system(size: 13, weight: .light))
                    .foregroundStyle(muted)
                    .padding(.top, 10)
            }
            .padding(.horizontal, 24)
            .padding(.vertical, 18)
            .frame(minHeight: 64)
            .contentShape(Rectangle())
        }
        .buttonStyle(.plain)
        .overlay(alignment: .bottom) {
            Rectangle().fill(rule).frame(height: 1).padding(.horizontal, 24)
        }
    }
}

#Preview("Editorial ListRow") {
    VStack(spacing: 0) {
        DSEditorialListRow(systemIcon: "book", title: "On restraint", byline: "Essay · 14 min read")
        DSEditorialListRow(systemIcon: "pencil.and.scribble", title: "The italic as argument", byline: "Column · Volume III")
        DSEditorialListRow(systemIcon: "camera", title: "Portraiture in the margin", byline: "Feature")
    }
    .padding(.vertical, 16)
    .background(Color(red: 0.961, green: 0.945, blue: 0.918))
}
