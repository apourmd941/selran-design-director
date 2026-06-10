// TabBar.swift — vibrant-playful
// Tokens:
//   color.bg_primary → #FFFBF5 / dark #1A1613
//   color.palette    → each tab rotates through a palette hue on select (color-coded)
//   color.fg_muted   → #6A6A6A
//   color.border     → #E8DFC9
// Signature: selected tab icon scales up and tints to its palette hue with a spring.

import SwiftUI

struct VibrantTabItem: Identifiable {
    let id = UUID()
    let title: String
    let icon: String
    let hue: Color   // palette[i] for this tab
}

struct DSVibrantTabBar: View {
    let items: [VibrantTabItem]
    @Binding var selection: Int

    @Environment(\.colorScheme) private var scheme
    @Environment(\.accessibilityReduceMotion) private var reduceMotion

    private var muted: Color {
        scheme == .dark ? Color(red: 0.600, green: 0.557, blue: 0.490)
                        : Color(red: 0.416, green: 0.416, blue: 0.416)
    }
    private var border: Color {
        scheme == .dark ? Color(red: 0.200, green: 0.173, blue: 0.133)
                        : Color(red: 0.910, green: 0.875, blue: 0.788)
    }
    private var bg: Color {
        scheme == .dark ? Color(red: 0.102, green: 0.086, blue: 0.075)
                        : Color(red: 1.0, green: 0.984, blue: 0.961)
    }

    var body: some View {
        HStack(spacing: 0) {
            ForEach(Array(items.enumerated()), id: \.element.id) { idx, item in
                let active = selection == idx
                Button {
                    selection = idx
                } label: {
                    VStack(spacing: 4) {
                        Image(systemName: item.icon)
                            .font(.system(size: 20, weight: active ? .semibold : .regular))
                            .foregroundStyle(active ? item.hue : muted)
                            .scaleEffect(active ? 1.12 : 1.0)
                        Text(item.title)
                            .font(.custom("Satoshi-Medium", size: 11, relativeTo: .caption2))
                            .foregroundStyle(active ? item.hue : muted)
                    }
                    .frame(maxWidth: .infinity, minHeight: 48)
                }
                .buttonStyle(.plain)
                .animation(reduceMotion ? .linear(duration: 0.01)
                                         : .spring(response: 0.4, dampingFraction: 0.65),
                           value: selection)
            }
        }
        .padding(.top, 8)
        .padding(.bottom, 4)
        .background(bg)
        .overlay(alignment: .top) {
            Rectangle().fill(border).frame(height: 1)
        }
    }
}

#Preview("Vibrant-Playful TabBar") {
    @Previewable @State var sel = 0
    let p = [
        Color(red: 0.769, green: 0.290, blue: 0.180),
        Color(red: 0.122, green: 0.420, blue: 0.533),
        Color(red: 0.612, green: 0.376, blue: 0.078),
        Color(red: 0.333, green: 0.243, blue: 0.353),
        Color(red: 0.165, green: 0.482, blue: 0.376)
    ]
    return VStack {
        Spacer()
        Text("Hello, Jamie")
            .font(.custom("Satoshi-Bold", size: 28, relativeTo: .title))
        Spacer()
        DSVibrantTabBar(items: [
            VibrantTabItem(title: "Home", icon: "house.fill", hue: p[0]),
            VibrantTabItem(title: "Learn", icon: "book.fill", hue: p[1]),
            VibrantTabItem(title: "Tasks", icon: "checkmark.circle.fill", hue: p[2]),
            VibrantTabItem(title: "Team", icon: "person.3.fill", hue: p[3]),
            VibrantTabItem(title: "You", icon: "person.crop.circle.fill", hue: p[4])
        ], selection: $sel)
    }
    .background(Color(red: 1.0, green: 0.984, blue: 0.961))
    .ignoresSafeArea(edges: .bottom)
}
