// FormField.swift — technical-minimal
// Tokens:
//   color.fg_primary / fg_secondary / fg_muted / border / accent / danger
//   type.xs (12pt)   → label (small-caps mono tracking 0.08em)
//   type.base (16pt) → input
// Uses iOS TextField; styles only the chrome. Dynamic Type via relativeTo:.

import SwiftUI

struct DSTechnicalFormField: View {
    let label: String
    let placeholder: String
    @Binding var text: String
    var helper: String? = nil
    var error: String? = nil

    @Environment(\.colorScheme) private var scheme
    @FocusState private var focused: Bool

    private var fg: Color {
        scheme == .dark ? Color(red: 0.957, green: 0.957, blue: 0.961)
                        : Color(red: 0.094, green: 0.094, blue: 0.106)
    }
    private var muted: Color { Color(red: 0.443, green: 0.443, blue: 0.478) }
    private var border: Color {
        scheme == .dark ? Color(red: 0.153, green: 0.153, blue: 0.165)
                        : Color(red: 0.894, green: 0.894, blue: 0.906)
    }
    private var accent: Color {
        scheme == .dark ? Color(red: 0.063, green: 0.725, blue: 0.506)
                        : Color(red: 0.039, green: 0.478, blue: 0.361)
    }
    private var danger: Color { Color(red: 0.725, green: 0.110, blue: 0.110) } // #B91C1C

    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            Text(label.uppercased())
                .font(.custom("JetBrainsMono-Regular", size: 11, relativeTo: .caption2))
                .tracking(0.08 * 11)
                .foregroundStyle(muted)

            TextField(placeholder, text: $text)
                .font(.custom("GeneralSans-Regular", size: 16, relativeTo: .body))
                .foregroundStyle(fg)
                .padding(.horizontal, 12)
                .frame(minHeight: 44)
                .focused($focused)
                .background(
                    RoundedRectangle(cornerRadius: 8, style: .continuous)
                        .strokeBorder(
                            error != nil ? danger : (focused ? accent : border),
                            lineWidth: focused || error != nil ? 1.5 : 1
                        )
                )

            if let error {
                Text(error)
                    .font(.custom("GeneralSans-Regular", size: 13, relativeTo: .footnote))
                    .foregroundStyle(danger)
            } else if let helper {
                Text(helper)
                    .font(.custom("GeneralSans-Regular", size: 13, relativeTo: .footnote))
                    .foregroundStyle(muted)
            }
        }
        .animation(.timingCurve(0.2, 0, 0, 1, duration: 0.15), value: focused)
    }
}

#Preview("Technical-Minimal FormField") {
    @Previewable @State var email = ""
    @Previewable @State var bad = "not-an-email"
    return VStack(alignment: .leading, spacing: 20) {
        DSTechnicalFormField(label: "Email", placeholder: "you@company.com",
                             text: $email, helper: "We'll never share it.")
        DSTechnicalFormField(label: "Webhook URL", placeholder: "https://…",
                             text: $bad, error: "Must start with https://")
    }
    .padding(24)
    .background(Color(red: 0.98, green: 0.98, blue: 0.976))
}
