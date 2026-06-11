// ListRow.swift — brutalist
// Tokens:
//   color.fg_primary → #000 / dark #FFF
//   color.border     → 2px solid (every container has one)
//   type.body        → Space Mono 14pt
// Signature: underline on title (HTML-default aesthetic); ">" glyph instead of chevron,
// but use SF Symbol chevron.forward to keep auto-mirroring for RTL.
// Hover/press inverts bg.

import SwiftUI

struct DSBrutalistListRow: View {
    let systemIcon: String
    let title: String
    let subtitle: String?  // often metadata: word count, timestamp, size
    var action: () -> Void = {}

    @Environment(\.colorScheme) private var scheme
    @State private var pressed = false

    private var ink: Color { scheme == .dark ? .white : .black }
    private var paper: Color { scheme == .dark ? .black : .white }

    var body: some View {
        Button(action: action) {
            HStack(spacing: 12) {
                Image(systemName: systemIcon)
                    .font(.system(size: 16, weight: .bold))
                    .foregroundStyle(pressed ? paper : ink)
                    .frame(width: 20)
                VStack(alignment: .leading, spacing: 2) {
                    Text(title.uppercased())
                        .font(.custom("SpaceMono-Bold", size: 14, relativeTo: .body))
                        .foregroundStyle(pressed ? paper : ink)
                        .underline()
                    if let subtitle {
                        Text(subtitle)
                            .font(.custom("SpaceMono-Regular", size: 11, relativeTo: .caption2))
                            .foregroundStyle(pressed ? paper : ink)
                    }
                }
                Spacer()
                Image(systemName: "chevron.forward")
                    .font(.system(size: 13, weight: .bold))
                    .foregroundStyle(pressed ? paper : ink)
            }
            .padding(.horizontal, 14)
            .frame(minHeight: 52)
            .contentShape(Rectangle())
            .background(Rectangle().fill(pressed ? ink : paper))
        }
        .buttonStyle(.plain)
        ._onButtonGesture(pressing: { pressed = $0 }, perform: {})
        .overlay(Rectangle().strokeBorder(ink, lineWidth: 2))
    }
}

#Preview("Brutalist ListRow") {
    VStack(spacing: -2) { // overlap borders so they share 2px edges
        DSBrutalistListRow(systemIcon: "doc.text", title: "index.html", subtitle: "3.4 KB · edited 2h ago")
        DSBrutalistListRow(systemIcon: "doc.text", title: "style.css", subtitle: "1.8 KB · edited 2h ago")
        DSBrutalistListRow(systemIcon: "doc.text", title: "README", subtitle: "527 B · edited 1d ago")
    }
    .padding(20)
    .background(Color.white)
}
