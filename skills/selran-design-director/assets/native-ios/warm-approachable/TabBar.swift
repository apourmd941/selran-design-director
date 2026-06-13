// TabBar.swift — warm-approachable
// Tokens:
//   color.bg_primary → #FAF3E7 / dark #1F1612
//   color.accent     → #B04A2C / dark #E87040
//   color.fg_muted   → #7A6250
//   color.border     → #E0CEB0
// Selected tab gets a soft rounded pill behind the icon — feels hospitable, not official.

import SwiftUI

struct WarmTabItem: Identifiable {
    let id = UUID()
    let title: String
    let icon: String
}

struct DSWarmTabBar: View {
    let items: [WarmTabItem]
    @Binding var selection: Int

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
    private var bg: Color {
        scheme == .dark ? Color(red: 0.122, green: 0.086, blue: 0.071)
                        : Color(red: 0.980, green: 0.953, blue: 0.906)
    }

    var body: some View {
        HStack(spacing: 0) {
            ForEach(Array(items.enumerated()), id: \.element.id) { idx, item in
                Button {
                    selection = idx
                } label: {
                    VStack(spacing: 4) {
                        Image(systemName: item.icon)
                            .font(.system(size: 18, weight: selection == idx ? .semibold : .regular))
                            .foregroundStyle(selection == idx ? accent : muted)
                            .frame(width: 40, height: 30)
                            .background(
                                RoundedRectangle(cornerRadius: 14)
                                    .fill(selection == idx ? accent.opacity(0.14) : Color.clear)
                            )
                        Text(item.title)
                            .font(.custom("Commissioner-Medium", size: 11, relativeTo: .caption2))
                            .foregroundStyle(selection == idx ? fg : muted)
                    }
                    .frame(maxWidth: .infinity, minHeight: 48)
                }
                .buttonStyle(.plain)
                .animation(.spring(response: 0.4, dampingFraction: 0.8), value: selection)
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

#Preview("Warm-Approachable TabBar") {
    @Previewable @State var sel = 2
    return VStack {
        Spacer()
        Text("Welcome back")
            .font(.custom("Fraunces-Medium", size: 28, relativeTo: .title))
            .foregroundStyle(Color(red: 0.176, green: 0.106, blue: 0.059))
        Spacer()
        DSWarmTabBar(items: [
            WarmTabItem(title: "Home", icon: "house"),
            WarmTabItem(title: "Menu", icon: "cup.and.saucer"),
            WarmTabItem(title: "Orders", icon: "bag"),
            WarmTabItem(title: "You", icon: "person")
        ], selection: $sel)
    }
    .background(Color(red: 0.980, green: 0.953, blue: 0.906))
    .ignoresSafeArea(edges: .bottom)
}
