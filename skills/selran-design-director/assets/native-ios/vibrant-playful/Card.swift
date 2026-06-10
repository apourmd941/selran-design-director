// Card.swift — vibrant-playful
// Tokens:
//   color.bg_secondary → #F5EFE1 / dark #262019
//   color.fg_primary   → #1A1A1A / dark #FFFBF5
//   color.fg_muted     → #6A6A6A
//   color.palette[i]   → hue for color-coded section (pill + left-edge stripe)
//   spacing.radius.lg  → 20pt (large surfaces)
// Signature: a tag pill at top colored by palette hue; matching 3pt left stripe.

import SwiftUI

struct DSVibrantCard<Action: View>: View {
    let tag: String
    let title: String
    let body: String
    let hue: Color       // palette[i]
    @ViewBuilder var action: () -> Action

    @Environment(\.colorScheme) private var scheme

    private var bg: Color {
        scheme == .dark ? Color(red: 0.149, green: 0.125, blue: 0.098)
                        : Color(red: 0.961, green: 0.937, blue: 0.882)
    }
    private var fg: Color {
        scheme == .dark ? Color(red: 1.0, green: 0.984, blue: 0.961)
                        : Color(red: 0.102, green: 0.102, blue: 0.102)
    }
    private var muted: Color {
        scheme == .dark ? Color(red: 0.600, green: 0.557, blue: 0.490)
                        : Color(red: 0.416, green: 0.416, blue: 0.416)
    }

    var body: some View {
        HStack(alignment: .top, spacing: 0) {
            Rectangle().fill(hue).frame(width: 3) // stripe
            VStack(alignment: .leading, spacing: 12) {
                Text(tag.uppercased())
                    .font(.custom("Satoshi-Bold", size: 11, relativeTo: .caption2))
                    .tracking(0.06 * 11)
                    .foregroundStyle(hue)
                    .padding(.horizontal, 10).padding(.vertical, 4)
                    .background(Capsule().fill(hue.opacity(0.14)))
                Text(title)
                    .font(.custom("Satoshi-Bold", size: 22, relativeTo: .title2))
                    .foregroundStyle(fg)
                Text(body)
                    .font(.custom("Satoshi-Regular", size: 16, relativeTo: .body))
                    .foregroundStyle(muted)
                    .lineSpacing(4)
                action()
                    .padding(.top, 4)
            }
            .padding(18)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(
            RoundedRectangle(cornerRadius: 20, style: .continuous).fill(bg)
        )
        .clipShape(RoundedRectangle(cornerRadius: 20, style: .continuous))
    }
}

#Preview("Vibrant-Playful Card") {
    let p0 = Color(red: 0.769, green: 0.290, blue: 0.180)
    let p1 = Color(red: 0.122, green: 0.420, blue: 0.533)
    let p4 = Color(red: 0.165, green: 0.482, blue: 0.376)
    return VStack(spacing: 16) {
        DSVibrantCard(tag: "Chapter 01", title: "Getting set up", body: "Ten minutes. That's all it takes to start.", hue: p0) {
            Text("Begin →").font(.custom("Satoshi-Medium", size: 15, relativeTo: .callout)).foregroundStyle(p0)
        }
        DSVibrantCard(tag: "Chapter 02", title: "Invite your team", body: "Share a link. They're in.", hue: p1) { EmptyView() }
        DSVibrantCard(tag: "Chapter 05", title: "Ship it", body: "Go live whenever you're ready.", hue: p4) { EmptyView() }
    }
    .padding(20)
    .background(Color(red: 1.0, green: 0.984, blue: 0.961))
}
