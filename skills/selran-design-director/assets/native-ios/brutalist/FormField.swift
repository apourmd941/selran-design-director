// FormField.swift — brutalist
// Tokens:
//   color.bg_primary → #FFF / dark #000
//   color.fg_primary → #000 / dark #FFF
//   color.danger     → #D10000
//   spacing.radius.* → 0
//   type            → Space Mono throughout
// Label in mono-caps above; 2px solid border; underline on error helper text.

import SwiftUI

struct DSBrutalistFormField: View {
    let label: String
    let placeholder: String
    @Binding var text: String
    var helper: String? = nil
    var error: String? = nil

    @Environment(\.colorScheme) private var scheme
    @FocusState private var focused: Bool

    private var ink: Color { scheme == .dark ? .white : .black }
    private var paper: Color { scheme == .dark ? .black : .white }
    private var danger: Color { Color(red: 0.820, green: 0.0, blue: 0.0) } // #D10000

    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            Text(label.uppercased())
                .font(.custom("SpaceMono-Bold", size: 12, relativeTo: .caption))
                .tracking(0.05 * 12)
                .foregroundStyle(ink)

            TextField("", text: $text, prompt:
                Text(placeholder.uppercased())
                    .font(.custom("SpaceMono-Regular", size: 14, relativeTo: .body))
                    .foregroundColor(ink.opacity(0.45))
            )
                .font(.custom("SpaceMono-Regular", size: 14, relativeTo: .body))
                .foregroundStyle(ink)
                .focused($focused)
                .padding(.horizontal, 12)
                .frame(minHeight: 46)
                .background(Rectangle().fill(paper))
                .overlay(
                    Rectangle().strokeBorder(error != nil ? danger : ink, lineWidth: focused ? 3 : 2)
                )

            if let error {
                Text("ERROR: \(error)")
                    .font(.custom("SpaceMono-Bold", size: 12, relativeTo: .caption))
                    .foregroundStyle(danger)
                    .underline()
            } else if let helper {
                Text(helper)
                    .font(.custom("SpaceMono-Regular", size: 12, relativeTo: .caption))
                    .foregroundStyle(ink.opacity(0.7))
            }
        }
    }
}

#Preview("Brutalist FormField") {
    @Previewable @State var name = ""
    @Previewable @State var bad = "x"
    return VStack(alignment: .leading, spacing: 20) {
        DSBrutalistFormField(label: "Name", placeholder: "your name",
                             text: $name, helper: "Required. No emoji.")
        DSBrutalistFormField(label: "Token", placeholder: "paste token",
                             text: $bad, error: "Minimum 32 chars.")
    }
    .padding(20)
    .background(Color.white)
}
