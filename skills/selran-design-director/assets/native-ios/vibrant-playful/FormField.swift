// FormField.swift — vibrant-playful
// Tokens:
//   color.bg_secondary → #F5EFE1 / dark #262019  (field fill, soft)
//   color.accent       → #C44A2E / dark #FF6B50
//   color.fg_primary / fg_muted / border / danger
//   spacing.radius.md  → 12pt
// Focus pulses the border in accent; 2px ring, friendly.

import SwiftUI

struct DSVibrantFormField: View {
    let label: String
    let placeholder: String
    @Binding var text: String
    var helper: String? = nil
    var error: String? = nil
    var hue: Color? = nil   // optional palette hue override for focus

    @Environment(\.colorScheme) private var scheme
    @FocusState private var focused: Bool

    private var defaultAccent: Color {
        scheme == .dark ? Color(red: 1.0, green: 0.420, blue: 0.314)
                        : Color(red: 0.769, green: 0.290, blue: 0.180)
    }
    private var accent: Color { hue ?? defaultAccent }
    private var fg: Color {
        scheme == .dark ? Color(red: 1.0, green: 0.984, blue: 0.961)
                        : Color(red: 0.102, green: 0.102, blue: 0.102)
    }
    private var muted: Color {
        scheme == .dark ? Color(red: 0.600, green: 0.557, blue: 0.490)
                        : Color(red: 0.416, green: 0.416, blue: 0.416)
    }
    private var fill: Color {
        scheme == .dark ? Color(red: 0.149, green: 0.125, blue: 0.098)
                        : Color(red: 0.961, green: 0.937, blue: 0.882)
    }
    private var border: Color {
        scheme == .dark ? Color(red: 0.200, green: 0.173, blue: 0.133)
                        : Color(red: 0.910, green: 0.875, blue: 0.788)
    }
    private var danger: Color { Color(red: 0.690, green: 0.196, blue: 0.271) } // #B03245

    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            Text(label)
                .font(.custom("Satoshi-Medium", size: 14, relativeTo: .footnote))
                .foregroundStyle(fg)

            TextField(placeholder, text: $text)
                .font(.custom("Satoshi-Regular", size: 16, relativeTo: .body))
                .foregroundStyle(fg)
                .focused($focused)
                .padding(.horizontal, 14)
                .frame(minHeight: 46)
                .background(
                    RoundedRectangle(cornerRadius: 12, style: .continuous).fill(fill)
                )
                .overlay(
                    RoundedRectangle(cornerRadius: 12, style: .continuous)
                        .strokeBorder(
                            error != nil ? danger : (focused ? accent : border),
                            lineWidth: focused || error != nil ? 2 : 1
                        )
                )

            if let error {
                Text(error)
                    .font(.custom("Satoshi-Regular", size: 13, relativeTo: .footnote))
                    .foregroundStyle(danger)
            } else if let helper {
                Text(helper)
                    .font(.custom("Satoshi-Regular", size: 13, relativeTo: .footnote))
                    .foregroundStyle(muted)
            }
        }
        .animation(.timingCurve(0.34, 1.1, 0.64, 1, duration: 0.28), value: focused)
    }
}

#Preview("Vibrant-Playful FormField") {
    @Previewable @State var handle = ""
    @Previewable @State var bad = "123"
    return VStack(alignment: .leading, spacing: 18) {
        DSVibrantFormField(label: "Handle", placeholder: "@you",
                           text: $handle, helper: "Letters, numbers, underscores.")
        DSVibrantFormField(label: "Promo code", placeholder: "SUNSHINE",
                           text: $bad, error: "Hmm, that code isn't valid.")
    }
    .padding(24)
    .background(Color(red: 1.0, green: 0.984, blue: 0.961))
}
