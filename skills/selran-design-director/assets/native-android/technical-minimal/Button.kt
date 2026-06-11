package com.selran.direction.technicalminimal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background

// Tokens — technical-minimal (see assets/direction-starters/technical-minimal.md)
private object TechnicalMinimalTokens {
    val Accent      = Color(0xFF0A7A5C) // color.accent
    val AccentHover = Color(0xFF065F47) // color.accent_hover
    val OnAccent    = Color(0xFFFAFAF9) // color.bg_primary
    val FgPrimary   = Color(0xFF18181B) // color.fg_primary
    val BorderStrong= Color(0xFFD4D4D8) // color.border_strong
    val Radius      = 8.dp              // spacing.radius.md
}

@Composable
fun TechnicalMinimalPrimaryButton(
    label: String,
    onClick: () -> Unit,
    loading: Boolean = false,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        shape = RoundedCornerShape(TechnicalMinimalTokens.Radius),
        colors = ButtonDefaults.buttonColors(
            containerColor = TechnicalMinimalTokens.Accent,
            contentColor   = TechnicalMinimalTokens.OnAccent,
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        contentPadding = ButtonDefaults.ContentPadding,
        modifier = Modifier.defaultMinSize(minHeight = 48.dp),
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp,
                color = TechnicalMinimalTokens.OnAccent,
            )
        } else {
            Text(label, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun TechnicalMinimalSecondaryButton(
    label: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(TechnicalMinimalTokens.Radius),
        border = BorderStroke(1.dp, TechnicalMinimalTokens.BorderStrong),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = TechnicalMinimalTokens.FgPrimary),
        modifier = Modifier.defaultMinSize(minHeight = 48.dp),
    ) {
        Text(label, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFAFAF9)
@Composable
private fun PreviewLight() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TechnicalMinimalPrimaryButton("Continue", onClick = {})
        TechnicalMinimalPrimaryButton("Saving", onClick = {}, loading = true)
        TechnicalMinimalSecondaryButton("Cancel", onClick = {})
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF0A0A0B,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDark() {
    Column(
        modifier = Modifier.background(Color(0xFF0A0A0B)).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TechnicalMinimalPrimaryButton("Continue", onClick = {})
        TechnicalMinimalSecondaryButton("Cancel", onClick = {})
    }
}
