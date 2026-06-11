// TabBar.swift — bold-distinctive
// Tokens:
//   color.bg_primary → #F5F0E8 / dark #0A0A0B
//   color.fg_primary → #0A0A0B / dark #F5F0E8
//   color.accent     → #C72500 / dark #FF5530
//   spacing.radius.* → 0
//   type.body (17pt) → General Sans for labels, confident, no hedging
// Selected tab: bold label + accent underline (thick, 3px). No scale bounce.

import SwiftUI

struct BoldTabItem: Identifiable {
    let id = UUID()
    let title: String
    let icon: String
}

struct DSBoldTabBar: View {
    let items: [BoldTabItem]
    @Binding var selection: Int

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
    private var bg: Color {
        scheme == .dark ? Color(red: 0.039, green: 0.039, blue: 0.043)
                        : Color(red: 0.961, green: 0.941, blue: 0.910)
    }

    var body: some View {
        HStack(spacing: 0) {
            ForEach(Array(items.enumerated()), id: \.element.id) { idx, item in
                let active = selection == idx
                Button {
                    selection = idx
                } label: {
                    VStack(spacing: 6) {
                        Image(systemName: item.icon)
                            .font(.system(size: 20, weight: active ? .bold : .regular))
                        Text(item.title)
                            .font(.custom(active ? "GeneralSans-Semibold" : "GeneralSans-Regular",
                                          size: 12, relativeTo: .caption))
                        Rectangle()
                            .fill(active ? red : Color.clear)
                            .frame(width: 24, height: 3) // thick accent underline
                    }
                    .foregroundStyle(active ? ink : muted)
                    .frame(maxWidth: .infinity, minHeight: 52)
                }
                .buttonStyle(.plain)
                .animation(.timingCurve(0.2, 0, 0, 1, duration: 0.2), value: selection)
            }
        }
        .padding(.top, 10)
        .padding(.bottom, 4)
        .background(bg)
        .overlay(alignment: .top) {
            Rectangle().fill(ink).frame(height: 2)
        }
    }
}

#Preview("Bold-Distinctive TabBar") {
    @Previewable @State var sel = 0
    return VStack(spacing: 0) {
        Spacer()
        Text("HELLO.")
            .font(.custom("PPEditorialNew-Ultrabold", size: 72, relativeTo: .largeTitle))
            .tracking(-0.03 * 72)
        Spacer()
        DSBoldTabBar(items: [
            BoldTabItem(title: "Work", icon: "square.grid.2x2"),
            BoldTabItem(title: "About", icon: "info.circle"),
            BoldTabItem(title: "Press", icon: "newspaper"),
            BoldTabItem(title: "Contact", icon: "envelope")
        ], selection: $sel)
    }
    .background(Color(red: 0.961, green: 0.941, blue: 0.910))
    .ignoresSafeArea(edges: .bottom)
}
