// Card.swift — bold-distinctive
// Tokens:
//   color.bg_primary → #F5F0E8 / dark #0A0A0B
//   color.fg_primary → #0A0A0B / dark #F5F0E8
//   color.accent     → #C72500 / dark #FF5530
//   spacing.radius.* → 0
//   type.display → PP Editorial New; type.xxl (72) reserved for hero
// Signature: large numeric section marker (01, 02...) as display element;
// asymmetric offset; one hero card per screen.

import SwiftUI

struct DSBoldCard<Action: View>: View {
    let number: String       // "01", "02", ...
    let title: String
    let body: String
    @ViewBuilder var action: () -> Action

    @Environment(\.colorScheme) private var scheme

    private var ink: Color {
        scheme == .dark ? Color(red: 0.961, green: 0.941, blue: 0.910)
                        : Color(red: 0.039, green: 0.039, blue: 0.043)
    }
    private var cream: Color {
        scheme == .dark ? Color(red: 0.039, green: 0.039, blue: 0.043)
                        : Color(red: 0.961, green: 0.941, blue: 0.910)
    }
    private var red: Color {
        scheme == .dark ? Color(red: 1.0, green: 0.333, blue: 0.188)
                        : Color(red: 0.780, green: 0.145, blue: 0.0)
    }
    private var creamAlt: Color {
        scheme == .dark ? Color(red: 0.082, green: 0.082, blue: 0.090)
                        : Color(red: 0.925, green: 0.894, blue: 0.831)
    }

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            // Giant section marker — display architecture
            Text(number)
                .font(.custom("PPEditorialNew-Ultrabold", size: 96, relativeTo: .largeTitle))
                .tracking(-0.03 * 96)
                .foregroundStyle(red)
                .lineLimit(1)
                .padding(.bottom, 4)
                .offset(x: -4) // intentional optical offset

            Text(title)
                .font(.custom("PPEditorialNew-Ultrabold", size: 40, relativeTo: .title))
                .tracking(-0.02 * 40)
                .foregroundStyle(ink)
                .lineSpacing(-4)
                .padding(.top, 12)

            Rectangle().fill(ink).frame(width: 48, height: 2).padding(.top, 16)

            Text(body)
                .font(.custom("GeneralSans-Regular", size: 17, relativeTo: .body))
                .foregroundStyle(ink.opacity(0.75))
                .lineSpacing(4)
                .padding(.top, 16)

            action()
                .padding(.top, 20)
        }
        .padding(28)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(Rectangle().fill(creamAlt))
    }
}

#Preview("Bold-Distinctive Card") {
    DSBoldCard(
        number: "01",
        title: "Earn attention on purpose.",
        body: "Design with presence. Don't whisper when the moment calls for a shout."
    ) {
        Text("Read the manifesto →")
            .font(.custom("GeneralSans-Semibold", size: 15, relativeTo: .callout))
            .foregroundStyle(Color(red: 0.780, green: 0.145, blue: 0.0))
    }
    .padding(20)
    .background(Color(red: 0.961, green: 0.941, blue: 0.910))
}

#Preview("Bold-Distinctive Card — Dark") {
    DSBoldCard(
        number: "02",
        title: "Refuse the ordinary.",
        body: "Most products look the same because most teams are afraid."
    ) { EmptyView() }
    .padding(20)
    .background(Color(red: 0.039, green: 0.039, blue: 0.043))
    .preferredColorScheme(.dark)
}
