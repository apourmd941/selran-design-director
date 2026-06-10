package com.selran.direction.vibrantplayful

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — vibrant-playful
private object VpFieldTokens {
    val Accent    = Color(0xFFC44A2E) // palette[0] / color.accent
    val Border    = Color(0xFFD1BF9E) // color.border_strong
    val Ink       = Color(0xFF1A1A1A) // color.fg_primary
    val Muted     = Color(0xFF6A6A6A) // color.fg_muted
    val Danger    = Color(0xFFB03245) // color.danger
    val Radius    = 12.dp             // spacing.radius.md
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VibrantPlayfulFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    helper: String? = null,
    error: String? = null,
    modifier: Modifier = Modifier,
) {
    val isError = error != null
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(label, color = VpFieldTokens.Ink, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            singleLine = true,
            shape = RoundedCornerShape(VpFieldTokens.Radius),
            textStyle = TextStyle(fontSize = 16.sp, color = VpFieldTokens.Ink),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor    = VpFieldTokens.Accent,
                unfocusedBorderColor  = VpFieldTokens.Border,
                errorBorderColor      = VpFieldTokens.Danger,
                cursorColor           = VpFieldTokens.Accent,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        when {
            error != null  -> Text(error,  color = VpFieldTokens.Danger, fontSize = 13.sp)
            helper != null -> Text(helper, color = VpFieldTokens.Muted,  fontSize = 13.sp)
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFFFBF5)
@Composable
private fun PreviewLight() {
    var a by remember { mutableStateOf("") }
    var b by remember { mutableStateOf("bad") }
    Column(
        modifier = Modifier.background(Color(0xFFFFFBF5)).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        VibrantPlayfulFormField("Workspace name", a, { a = it }, helper = "You can change this later.")
        VibrantPlayfulFormField("Subdomain", b, { b = it }, error = "Must be at least 4 characters.")
    }
}
