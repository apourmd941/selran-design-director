// TabBar.swift — technical-minimal
// Tokens:
//   color.bg_primary  → #FAFAF9 / dark #0A0A0B
//   color.accent      → #0A7A5C / dark #10B981
//   color.fg_muted    → #71717A
//   color.border      → #E4E4E7 / dark #27272A
//   type.xs (12pt)    → label
// Uses iOS TabView shell; customizes the appearance. Propagate accent via .tint.

import SwiftUI

struct TabItem: Identifiable {
    let id = UUID()
    let title: String
    let icon: String // SF Symbol
}

struct DSTechnicalTabBar: View {
    let items: [TabItem]
    @Binding var selection: Int

    @Environment(\.colorScheme) private var scheme

    private var accent: Color {
        scheme == .dark ? Color(red: 0.063, green: 0.725, blue: 0.506)
                        : Color(red: 0.039, green: 0.478, blue: 0.361)
    }
    private var muted: Color { Color(red: 0.443, green: 0.443, blue: 0.478) }
    private var border: Color {
        scheme == .dark ? Color(red: 0.153, green: 0.153, blue: 0.165)
                        : Color(red: 0.894, green: 0.894, blue: 0.906)
    }
    private var bg: Color {
        scheme == .dark ? Color(red: 0.039, green: 0.039, blue: 0.043)
                        : Color(red: 0.98, green: 0.98, blue: 0.976)
    }

    var body: some View {
        HStack(spacing: 0) {
            ForEach(Array(items.enumerated()), id: \.element.id) { idx, item in
                Button {
                    selection = idx
                } label: {
                    VStack(spacing: 4) {
                        Image(systemName: item.icon)
                            .font(.system(size: 20, weight: selection == idx ? .semibold : .regular))
                        Text(item.title)
                            .font(.custom("GeneralSans-Medium", size: 11, relativeTo: .caption2))
                    }
                    .foregroundStyle(selection == idx ? accent : muted)
                    .frame(maxWidth: .infinity, minHeight: 44)
                }
                .buttonStyle(.plain)
            }
        }
        .padding(.top, 8)
        .padding(.bottom, 4)
        .background(bg)
        .overlay(alignment: .top) {
            Rectangle().fill(border).frame(height: 1)
        }
        .tint(accent)
    }
}

#Preview("Technical-Minimal TabBar") {
    @Previewable @State var sel = 0
    return VStack {
        Spacer()
        Text("Dashboard")
            .font(.custom("GeneralSans-Semibold", size: 20, relativeTo: .title3))
        Spacer()
        DSTechnicalTabBar(items: [
            TabItem(title: "Home", icon: "house"),
            TabItem(title: "Usage", icon: "chart.bar"),
            TabItem(title: "Keys", icon: "key"),
            TabItem(title: "Settings", icon: "gearshape")
        ], selection: $sel)
    }
    .background(Color(red: 0.98, green: 0.98, blue: 0.976))
    .ignoresSafeArea(edges: .bottom)
}
