// TabBar.swift — editorial
// Tokens:
//   color.bg_primary → #F5F1EA / dark #14110D
//   color.accent     → #7A1F1F / dark #D85E5E
//   color.fg_muted   → #6B6B6B
//   color.border     → #D6CFBE (thin rule line on top)
// Selected tab gets a serif italic label + a short rule beneath — print navigation feel.

import SwiftUI

struct EditorialTabItem: Identifiable {
    let id = UUID()
    let title: String
    let icon: String
}

struct DSEditorialTabBar: View {
    let items: [EditorialTabItem]
    @Binding var selection: Int

    @Environment(\.colorScheme) private var scheme

    private var ink: Color {
        scheme == .dark ? Color(red: 0.961, green: 0.945, blue: 0.918)
                        : Color(red: 0.102, green: 0.102, blue: 0.102)
    }
    private var muted: Color {
        scheme == .dark ? Color(red: 0.553, green: 0.529, blue: 0.459)
                        : Color(red: 0.420, green: 0.420, blue: 0.420)
    }
    private var accent: Color {
        scheme == .dark ? Color(red: 0.847, green: 0.369, blue: 0.369)
                        : Color(red: 0.478, green: 0.122, blue: 0.122)
    }
    private var rule: Color {
        scheme == .dark ? Color(red: 0.165, green: 0.149, blue: 0.125)
                        : Color(red: 0.839, green: 0.812, blue: 0.745)
    }
    private var bg: Color {
        scheme == .dark ? Color(red: 0.078, green: 0.067, blue: 0.051)
                        : Color(red: 0.961, green: 0.945, blue: 0.918)
    }

    var body: some View {
        HStack(spacing: 0) {
            ForEach(Array(items.enumerated()), id: \.element.id) { idx, item in
                Button {
                    selection = idx
                } label: {
                    VStack(spacing: 6) {
                        Image(systemName: item.icon)
                            .font(.system(size: 18, weight: .light))
                        Text(item.title)
                            .font(.custom(selection == idx ? "Fraunces-RegularItalic" : "Söhne-Buch",
                                          size: 12, relativeTo: .caption))
                            .tracking(selection == idx ? 0 : 0.08 * 12)
                        Rectangle()
                            .fill(selection == idx ? accent : Color.clear)
                            .frame(width: 16, height: 1)
                    }
                    .foregroundStyle(selection == idx ? ink : muted)
                    .frame(maxWidth: .infinity, minHeight: 48)
                }
                .buttonStyle(.plain)
            }
        }
        .padding(.top, 10)
        .padding(.bottom, 6)
        .background(bg)
        .overlay(alignment: .top) {
            Rectangle().fill(rule).frame(height: 1)
        }
    }
}

#Preview("Editorial TabBar") {
    @Previewable @State var sel = 1
    return VStack {
        Spacer()
        Text("Volume III")
            .font(.custom("Fraunces-Medium", size: 28, relativeTo: .title))
        Spacer()
        DSEditorialTabBar(items: [
            EditorialTabItem(title: "Home", icon: "house"),
            EditorialTabItem(title: "Issues", icon: "book"),
            EditorialTabItem(title: "Authors", icon: "person.2"),
            EditorialTabItem(title: "Archive", icon: "tray.full")
        ], selection: $sel)
    }
    .background(Color(red: 0.961, green: 0.945, blue: 0.918))
    .ignoresSafeArea(edges: .bottom)
}
