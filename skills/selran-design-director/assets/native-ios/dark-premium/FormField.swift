// FormField.swift — dark-premium
// Tokens:
//   color.bg_secondary → #151517 (field fill)
//   color.fg_primary   → #F4F4F5
//   color.fg_muted     → #71717A
//   color.accent       → #D4AF37 (focus ring — thin gold)
//   color.border       → #27272A
//   Label: tracked mono-case micro text; input: Söhne body 16pt.

import SwiftUI

struct DSDarkPremiumFormField: View {
    let label: String
    let placeholder: String
    @Binding var text: String
    var helper: String? = nil
    var error: String? = nil

    @FocusState private var focused: Bool

    private let bg = Color(red: 0.082, green: 0.082, blue: 0.090)
    private let fg = Color(red: 0.957, green: 0.957, blue: 0.961)
    private let faint = Color(red: 0.443, green: 0.443, blue: 0.478)
    private let muted = Color(red: 0.631, green: 0.631, blue: 0.667)
    private let gold = Color(red: 0.831, green: 0.686, blue: 0.216)
    private let border = Color(red: 0.153, green: 0.153, blue: 0.165)
    private let danger = Color(red: 0.973, green: 0.443, blue: 0.443)

    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text(label.uppercased())
                .font(.custom("Söhne-Buch", size: 11, relativeTo: .caption2))
                .tracking(0.10 * 11)
                .foregroundStyle(faint)

            TextField("", text: $text, prompt: Text(placeholder).foregroundColor(faint))
                .font(.custom("Söhne-Buch", size: 16, relativeTo: .body))
                .foregroundStyle(fg)
                .focused($focused)
                .padding(.horizontal, 14)
                .frame(minHeight: 48)
                .background(
                    RoundedRectangle(cornerRadius: 6, style: .continuous).fill(bg)
                )
                .overlay(
                    RoundedRectangle(cornerRadius: 6, style: .continuous)
                        .strokeBorder(
                            error != nil ? danger : (focused ? gold : border),
                            lineWidth: error != nil || focused ? 1 : 1
                        )
                )

            if let error {
                Text(error)
                    .font(.custom("Söhne-Buch", size: 13, relativeTo: .footnote))
                    .foregroundStyle(danger)
            } else if let helper {
                Text(helper)
                    .font(.custom("Söhne-Buch", size: 13, relativeTo: .footnote))
                    .foregroundStyle(muted)
            }
        }
        .animation(.timingCurve(0.19, 1, 0.22, 1, duration: 0.35), value: focused)
    }
}

#Preview("Dark-Premium FormField") {
    @Previewable @State var email = ""
    @Previewable @State var bad = "xx"
    return VStack(alignment: .leading, spacing: 24) {
        DSDarkPremiumFormField(label: "Email for invitation", placeholder: "you@domain",
                               text: $email, helper: "Invitation is non-transferable.")
        DSDarkPremiumFormField(label: "Membership code", placeholder: "AB-0000-AB",
                               text: $bad, error: "Code not recognised.")
    }
    .padding(28)
    .background(Color(red: 0.039, green: 0.039, blue: 0.043))
    .preferredColorScheme(.dark)
}
