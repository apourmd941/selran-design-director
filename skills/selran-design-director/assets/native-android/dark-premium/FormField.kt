package com.selran.direction.darkpremium

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

// Tokens — dark-premium
private object DpFieldTokens {
    val Gold        = Color(0xFFD4AF37) // color.accent
    val Border      = Color(0xFF3F3F46) // color.border_strong
    val BgLifted    = Color(0xFF151517) // color.bg_secondary
    val FgPrimary   = Color(0xFFF4F4F5) // color.fg_primary
    val FgMuted     = Color(0xFFA1A1AA) // color.fg_secondary
    val Danger      = Color(0xFFF87171) // color.danger
    val Radius      = 6.dp              // spacing.radius.md
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DarkPremiumFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    helper: String? = null,
    error: String? = null,
    modifier: Modifier = Modifier,
) {
    val isError = error != null
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            label.uppercase(),
            color = DpFieldTokens.FgMuted,
            style = TextStyle(fontSize = 11.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Medium),
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            singleLine = true,
            shape = RoundedCornerShape(DpFieldTokens.Radius),
            textStyle = TextStyle(fontSize = 16.sp, color = DpFieldTokens.FgPrimary),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor   = DpFieldTokens.BgLifted,
                unfocusedContainerColor = DpFieldTokens.BgLifted,
                focusedBorderColor      = DpFieldTokens.Gold,
                unfocusedBorderColor    = DpFieldTokens.Border,
                errorBorderColor        = DpFieldTokens.Danger,
                cursorColor             = DpFieldTokens.Gold,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        when {
            error != null  -> Text(error,  color = DpFieldTokens.Danger,  fontSize = 12.sp)
            helper != null -> Text(helper, color = DpFieldTokens.FgMuted, fontSize = 12.sp)
        }
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF0A0A0B,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDark() {
    var a by remember { mutableStateOf("") }
    var b by remember { mutableStateOf("member-0248") }
    Column(
        modifier = Modifier.background(Color(0xFF0A0A0B)).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        DarkPremiumFormField("Full name", a, { a = it }, helper = "As it appears on your card.")
        DarkPremiumFormField("Member ID", b, { b = it }, error = "Invalid format.")
    }
}
