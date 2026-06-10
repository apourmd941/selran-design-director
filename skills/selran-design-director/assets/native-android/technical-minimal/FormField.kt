package com.selran.direction.technicalminimal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — technical-minimal
private object TmFieldTokens {
    val Accent        = Color(0xFF0A7A5C) // color.accent (focused border)
    val BorderStrong  = Color(0xFFD4D4D8) // color.border_strong
    val FgPrimary     = Color(0xFF18181B) // color.fg_primary
    val FgMuted       = Color(0xFF71717A) // color.fg_muted
    val Danger        = Color(0xFFB91C1C) // color.danger
    val Radius        = 8.dp              // spacing.radius.md
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TechnicalMinimalFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    helper: String? = null,
    error: String? = null,
    modifier: Modifier = Modifier,
) {
    val isError = error != null
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = TmFieldTokens.FgPrimary,
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            singleLine = true,
            shape = RoundedCornerShape(TmFieldTokens.Radius),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = TmFieldTokens.Accent,
                unfocusedBorderColor = TmFieldTokens.BorderStrong,
                errorBorderColor     = TmFieldTokens.Danger,
                cursorColor          = TmFieldTokens.Accent,
                focusedTextColor     = TmFieldTokens.FgPrimary,
                unfocusedTextColor   = TmFieldTokens.FgPrimary,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        when {
            error != null -> Text(error, color = TmFieldTokens.Danger, fontSize = 12.sp)
            helper != null -> Text(helper, color = TmFieldTokens.FgMuted, fontSize = 12.sp)
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFAFAF9)
@Composable
private fun PreviewLight() {
    var a by remember { mutableStateOf("acme@example.com") }
    var b by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.background(Color(0xFFFAFAF9)).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TechnicalMinimalFormField("Email", a, { a = it }, helper = "We'll never share it.")
        TechnicalMinimalFormField("API key", b, { b = it }, error = "Required")
    }
}
