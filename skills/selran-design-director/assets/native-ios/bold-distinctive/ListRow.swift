// ListRow.swift — bold-distinctive
// Tokens:
//   color.fg_primary / fg_muted
//   color.accent     → #C72500 (on the index number)
//   color.border     → ink (high contrast)
//   type.display     → PP Editorial New for index; type.lg (24pt) for title
// Signature: left-aligned oversized index number, serif, next to a confident sans title.

import SwiftUI

struct DSBoldListRow: View {
    let index: String         // "01", "02"...
    let title: String
    let subtitle: String?
    var action: () -> Void = {}

    @Environment(\.colorScheme) private var scheme

    private var ink: Color {
        scheme == .dark ? Color(red: 0.961, green: 0.941, blue: 0.910)
                        : Color(red: 0.039, green: 0.039, blue: 0.043)
    }
    private var muted: Color {
        scheme == .dark ? Color(red: 0.624, green: 0.596, blue: 0.502)
                        : Color(red: 0.373, green: 0.373, blue: 0.373)
    }
    private var red: Color {
        scheme == .dark ? Color(red: 1.0, green: 0.333, blue: 0.188)
                        : Color(red: 0.780, green: 0.145, blue: 0.0)
    }

    var body: some View {
        Button(action: action) {
            HStack(alignment: .firstTextBaseline, spacing: 18) {
                Text(index)
                    .font(.custom("PPEditorialNew-Ultrabold", size: 44, relativeTo: .title))
                    .tracking(-0.02 * 44)
                    .foregroundStyle(red)
                    .frame(width: 60, alignment: .leading)
                VStack(alignment: .leading, spacing: 4) {
                    Text(title)
                        .font(.custom("GeneralSans-Semibold", size: 22, relativeTo: .title3))
                        .foregroundStyle(ink)
                        .multilineTextAlignment(.leading)
                    if let subtitle {
                        Text(subtitle.uppercased())
                            .font(.custom("GeneralSans-Medium", size: 12, relativeTo: .caption))
                            .tracking(0.10 * 12)
                            .foregroundStyle(muted)
                    }
                }
                Spacer()
                Image(systemName: "arrow.forward")
                    .font(.system(size: 16, weight: .bold))
                    .foregroundStyle(ink)
            }
            .padding(.horizontal, 20)
            .padding(.vertical, 18)
            .frame(minHeight: 76)
            .contentShape(Rectangle())
        }
        .buttonStyle(.plain)
        .overlay(alignment: .bottom) {
            Rectangle().fill(ink).frame(height: 1)
        }
    }
}

#Preview("Bold-Distinctive ListRow") {
    VStack(spacing: 0) {
        DSBoldListRow(index: "01", title: "Refuse the ordinary", subtitle: "Chapter one")
        DSBoldListRow(index: "02", title: "Speak first. Explain later.", subtitle: "Chapter two")
        DSBoldListRow(index: "03", title: "Make something to remember.", subtitle: "Chapter three")
    }
    .padding(.vertical, 12)
    .background(Color(red: 0.961, green: 0.941, blue: 0.910))
}
