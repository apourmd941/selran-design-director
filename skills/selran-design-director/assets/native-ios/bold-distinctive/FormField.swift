// FormField.swift — bold-distinctive
// Tokens:
//   color.fg_primary → #0A0A0B / dark #F5F0E8
//   color.accent     → #C72500
//   color.border     → ink (2px hard)
//   spacing.radius.* → 0
//   type.body        → General Sans 17pt; label in display serif tracked tight
// Signature: label set larger than usual, almost a heading; input sits under a 2px rule.

import SwiftUI

struct DSBoldFormField: View {
    let label: String
    let placeholder: String
    @Binding var text: String
    var helper: String? = nil
    var error: String? = nil

    @Environment(\.colorScheme) private var scheme
    @FocusState private var focused: Bool

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

    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text(label)
                .font(.custom("PPEditorialNew-Ultrabold", size: 20, relativeTo: .title3))
                .tracking(-0.02 * 20)
                .foregroundStyle(ink)

            TextField(placeholder, text: $text)
                .font(.custom("GeneralSans-Regular", size: 17, relativeTo: .body))
                .foregroundStyle(ink)
                .focused($focused)
                .padding(.vertical, 10)
                .overlay(alignment: .bottom) {
                    Rectangle()
                        .fill(error != nil ? red : ink)
                        .frame(height: focused || error != nil ? 3 : 2)
                }

            if let error {
                Text(error.uppercased())
                    .font(.custom("GeneralSans-Semibold", size: 12, relativeTo: .caption))
                    .tracking(0.10 * 12)
                    .foregroundStyle(red)
            } else if let helper {
                Text(helper)
                    .font(.custom("GeneralSans-Regular", size: 14, relativeTo: .footnote))
                    .foregroundStyle(muted)
            }
        }
        .animation(.timingCurve(0.2, 0, 0, 1, duration: 0.2), value: focused)
    }
}

#Preview("Bold-Distinctive FormField") {
    @Previewable @State var name = ""
    @Previewable @State var bad = "!"
    return VStack(alignment: .leading, spacing: 28) {
        DSBoldFormField(label: "Your name", placeholder: "First and last",
                        text: $name, helper: "Appears in the credits.")
        DSBoldFormField(label: "Manifesto line", placeholder: "One sentence.",
                        text: $bad, error: "At least ten characters.")
    }
    .padding(28)
    .background(Color(red: 0.961, green: 0.941, blue: 0.910))
}
