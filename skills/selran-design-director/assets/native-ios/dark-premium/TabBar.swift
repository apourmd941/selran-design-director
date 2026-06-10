// TabBar.swift — dark-premium
// Tokens:
//   color.bg_primary → #0A0A0B
//   color.fg_primary → #F4F4F5
//   color.fg_muted   → #71717A
//   color.accent     → #D4AF37 (selected icon + 1px underline)
//   color.border     → #27272A
// Slow ease-out-expo on selection; no bounce. Labels in tracked caps.

import SwiftUI

struct DarkPremiumTabItem: Identifiable {
    let id = UUID()
    let title: String
    let icon: String
}

struct DSDarkPremiumTabBar: View {
    let items: [DarkPremiumTabItem]
    @Binding var selection: Int

    private let bg = Color(red: 0.039, green: 0.039, blue: 0.043)
    private let fg = Color(red: 0.957, green: 0.957, blue: 0.961)
    private let faint = Color(red: 0.443, green: 0.443, blue: 0.478)
    private let gold = Color(red: 0.831, green: 0.686, blue: 0.216)
    private let border = Color(red: 0.153, green: 0.153, blue: 0.165)

    var body: some View {
        HStack(spacing: 0) {
            ForEach(Array(items.enumerated()), id: \.element.id) { idx, item in
                Button {
                    selection = idx
                } label: {
                    VStack(spacing: 6) {
                        Image(systemName: item.icon)
                            .font(.system(size: 19, weight: .light))
                            .foregroundStyle(selection == idx ? gold : faint)
                        Text(item.title.uppercased())
                            .font(.custom("Söhne-Buch", size: 10, relativeTo: .caption2))
                            .tracking(0.10 * 10)
                            .foregroundStyle(selection == idx ? fg : faint)
                        Rectangle()
                            .fill(selection == idx ? gold : Color.clear)
                            .frame(width: 12, height: 1)
                    }
                    .frame(maxWidth: .infinity, minHeight: 48)
                }
                .buttonStyle(.plain)
                .animation(.timingCurve(0.19, 1, 0.22, 1, duration: 0.35), value: selection)
            }
        }
        .padding(.top, 10)
        .padding(.bottom, 6)
        .background(bg)
        .overlay(alignment: .top) {
            Rectangle().fill(border).frame(height: 1)
        }
        .tint(gold)
    }
}

#Preview("Dark-Premium TabBar") {
    @Previewable @State var sel = 0
    return VStack {
        Spacer()
        Text("Maison")
            .font(.custom("Tiempos Headline", size: 36, relativeTo: .largeTitle))
            .foregroundStyle(Color(red: 0.957, green: 0.957, blue: 0.961))
        Spacer()
        DSDarkPremiumTabBar(items: [
            DarkPremiumTabItem(title: "Home", icon: "house"),
            DarkPremiumTabItem(title: "Collection", icon: "sparkles"),
            DarkPremiumTabItem(title: "Atelier", icon: "hammer"),
            DarkPremiumTabItem(title: "Account", icon: "person")
        ], selection: $sel)
    }
    .background(Color(red: 0.039, green: 0.039, blue: 0.043))
    .preferredColorScheme(.dark)
    .ignoresSafeArea(edges: .bottom)
}
