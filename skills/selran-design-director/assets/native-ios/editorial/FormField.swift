// FormField.swift — editorial
// Tokens:
//   color.fg_primary / fg_muted / accent (oxblood) / border (1px #D6CFBE)
//   Label: small-caps Söhne tracked; input: Söhne body 17pt.
// Inline underline under the field (not a box). Focus thickens the rule to accent.

import SwiftUI

struct DSEditorialFormField: View {
    let label: String
    let placeholder: String
    @Binding var text: String
    var helper: String? = nil
    var error: String? = nil

    @Environment(\.colorScheme) private var scheme
    @FocusState private var focused: Bool

    private var ink: Color {
        scheme == .dark ? Color(red: 0.961, green: 0.945, blue: 0.918)
                        : Color(red: 0.102, green: 0.102, blue: 0.102)
    }
    private var muted: Color {
        scheme == .dark ? Color(red: 0.553, green: 0.529, blue: 0.459)
                        : Color(red: 0.420, green: 0.420, blue: 0.420)
    }
    private var rule: Color {
        scheme == .dark ? Color(red: 0.165, green: 0.149, blue: 0.125)
                        : Color(red: 0.839, green: 0.812, blue: 0.745)
    }
    private var accent: Color {
        scheme == .dark ? Color(red: 0.847, green: 0.369, blue: 0.369)
                        : Color(red: 0.478, green: 0.122, blue: 0.122)
    }

    private var strokeColor: Color { error != nil ? accent : (focused ? accent : rule) }
    private var strokeWidth: CGFloat { error != nil || focused ? 1.5 : 1 }

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(label.uppercased())
                .font(.custom("Söhne-Kräftig", size: 11, relativeTo: .caption2))
                .tracking(0.12 * 11)
                .foregroundStyle(muted)

            TextField(placeholder, text: $text)
                .font(.custom("Söhne-Buch", size: 17, relativeTo: .body))
                .foregroundStyle(ink)
                .focused($focused)
                .frame(minHeight: 44)
                .overlay(alignment: .bottom) {
                    Rectangle().fill(strokeColor).frame(height: strokeWidth)
                }

            if let error {
                Text(error)
                    .font(.custom("Fraunces-Regular", size: 14, relativeTo: .footnote).italic())
                    .foregroundStyle(accent)
            } else if let helper {
                Text(helper)
                    .font(.custom("Söhne-Buch", size: 14, relativeTo: .footnote))
                    .foregroundStyle(muted)
            }
        }
        .animation(.easeOut(duration: 0.25), value: focused)
    }
}

#Preview("Editorial FormField") {
    @Previewable @State var name = ""
    @Previewable @State var bad = "abc"
    return VStack(alignment: .leading, spacing: 28) {
        DSEditorialFormField(label: "Your name", placeholder: "Jane Doe",
                             text: $name, helper: "Appears on the masthead.")
        DSEditorialFormField(label: "Subscription code", placeholder: "XXXX-XXXX",
                             text: $bad, error: "Code must be eight characters.")
    }
    .padding(32)
    .background(Color(red: 0.961, green: 0.945, blue: 0.918))
}
