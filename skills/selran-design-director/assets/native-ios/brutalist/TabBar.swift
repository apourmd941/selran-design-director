// TabBar.swift — brutalist
// Tokens:
//   color.bg_primary → #FFF / dark #000
//   color.fg_primary → #000 / dark #FFF
//   color.accent     → #B84400
//   spacing.radius.*  → 0
//   type.body        → Space Mono
// Signature: selected tab is fully inverted (bg swap). 2px border across top. No motion.

import SwiftUI

struct BrutalistTabItem: Identifiable {
    let id = UUID()
    let title: String
    let icon: String
}

struct DSBrutalistTabBar: View {
    let items: [BrutalistTabItem]
    @Binding var selection: Int

    @Environment(\.colorScheme) private var scheme

    private var ink: Color { scheme == .dark ? .white : .black }
    private var paper: Color { scheme == .dark ? .black : .white }

    var body: some View {
        HStack(spacing: 0) {
            ForEach(Array(items.enumerated()), id: \.element.id) { idx, item in
                let active = selection == idx
                Button {
                    selection = idx
                } label: {
                    VStack(spacing: 2) {
                        Image(systemName: item.icon)
                            .font(.system(size: 18, weight: .bold))
                        Text(item.title.uppercased())
                            .font(.custom("SpaceMono-Bold", size: 10, relativeTo: .caption2))
                            .tracking(0.05 * 10)
                    }
                    .foregroundStyle(active ? paper : ink)
                    .frame(maxWidth: .infinity, minHeight: 52)
                    .background(Rectangle().fill(active ? ink : paper))
                    .overlay(
                        // 2px right border between tabs, except last
                        Rectangle()
                            .frame(width: idx == items.count - 1 ? 0 : 2)
                            .foregroundStyle(ink),
                        alignment: .trailing
                    )
                }
                .buttonStyle(.plain)
            }
        }
        .overlay(alignment: .top) { Rectangle().fill(ink).frame(height: 2) }
        .background(paper)
        // No animation — instant inversion.
    }
}

#Preview("Brutalist TabBar") {
    @Previewable @State var sel = 0
    return VStack(spacing: 0) {
        Spacer()
        Text("FILES")
            .font(.custom("SpaceMono-Bold", size: 32, relativeTo: .largeTitle))
        Spacer()
        DSBrutalistTabBar(items: [
            BrutalistTabItem(title: "Files", icon: "doc"),
            BrutalistTabItem(title: "Log", icon: "terminal"),
            BrutalistTabItem(title: "Net", icon: "network"),
            BrutalistTabItem(title: "Quit", icon: "power")
        ], selection: $sel)
    }
    .background(Color.white)
    .ignoresSafeArea(edges: .bottom)
}
