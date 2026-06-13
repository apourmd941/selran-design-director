// FormField.swift — warm-approachable
// Tokens:
//   color.bg_secondary → #F3E8D3 (gentle fill) / dark #2B1F18
//   color.fg_primary   → #2D1B0F / dark #F5E8D3
//   color.fg_muted     → #7A6250
//   color.accent       → #B04A2C
//   color.border       → #E0CEB0
//   spacing.radius.md  → 16pt (soft rounding)
//   Label stays visible above; focus softens to accent.

import SwiftUI

struct DSWarmFormField: View {
    let label: String
    let placeholder: String
    @Binding var text: String
    var helper: String? = nil
    var error: String? = nil

    @Environment(\.colorScheme) private var scheme
    @FocusState private var focused: Bool

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
    private var fill: Color {
        scheme == .dark ? Color(red: 0.169, green: 0.122, blue: 0.094)
                        : Color(red: 0.953, green: 0.910, blue: 0.827)
    }
    private var border: Color {
        scheme == .dark ? Color(red: 0.227, green: 0.169, blue: 0.129)
                        : Color(red: 0.878, green: 0.808, blue: 0.690)
    }
    private var danger: Color { Color(red: 0.725, green: 0.290, blue: 0.165) } // #B94A2A

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(label)
                .font(.custom("Commissioner-Medium", size: 15, relativeTo: .callout))
                .foregroundStyle(fg)

            TextField(placeholder, text: $text)
                .font(.custom("Commissioner-Regular", size: 17, relativeTo: .body))
                .foregroundStyle(fg)
                .focused($focused)
                .padding(.horizontal, 16)
                .frame(minHeight: 48)
                .background(
                    RoundedRectangle(cornerRadius: 16, style: .continuous).fill(fill)
                )
                .overlay(
                    RoundedRectangle(cornerRadius: 16, style: .continuous)
                        .strokeBorder(
                            error != nil ? danger : (focused ? accent : border),
                            lineWidth: focused || error != nil ? 2 : 1
                        )
                )

            if let error {
                Text(error)
                    .font(.custom("Fraunces-RegularItalic", size: 14, relativeTo: .footnote))
                    .foregroundStyle(danger)
            } else if let helper {
                Text(helper)
                    .font(.custom("Commissioner-Regular", size: 14, relativeTo: .footnote))
                    .foregroundStyle(muted)
            }
        }
        .animation(.easeInOut(duration: 0.25), value: focused)
    }
}

#Preview("Warm-Approachable FormField") {
    @Previewable @State var name = ""
    @Previewable @State var bad = "notanemail"
    return VStack(alignment: .leading, spacing: 22) {
        DSWarmFormField(label: "Your name", placeholder: "what should we call you?",
                        text: $name, helper: "We use this on the signup card.")
        DSWarmFormField(label: "Email", placeholder: "you@elsewhere.com",
                        text: $bad, error: "That doesn't look right — mind another try?")
    }
    .padding(28)
    .background(Color(red: 0.980, green: 0.953, blue: 0.906))
}
