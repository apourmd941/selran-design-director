package com.selran.direction.bolddistinctive

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RectangleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — bold-distinctive (see assets/direction-starters/bold-distinctive.md)
// Hot red on cream. Sharp corners (radius 0). Loud, confident, unapologetic.
private object BdTokens {
    val Cream      = Color(0xFFF5F0E8) // color.bg_primary
    val Ink        = Color(0xFF0A0A0B) // color.fg_primary / border
    val Red        = Color(0xFFC72500) // color.accent (hot)
    val RedHover   = Color(0xFF991C00) // color.accent_hover
    val OnRed      = Color(0xFFF5F0E8)
}

@Composable
fun BoldDistinctivePrimaryButton(
    label: String,
    onClick: () -> Unit,
    loading: Boolean = false,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        shape = RectangleShape, // spacing.radius.md = 0
        colors = ButtonDefaults.buttonColors(
            containerColor = BdTokens.Red,
            contentColor   = BdTokens.OnRed,
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        contentPadding = PaddingValues(horizontal = 28.dp, vertical = 16.dp),
        modifier = Modifier.defaultMinSize(minHeight = 56.dp), // bold = bigger
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.dp,
                color = BdTokens.OnRed,
            )
        } else {
            Text(
                label.uppercase(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.5.sp,
            )
        }
    }
}

@Composable
fun BoldDistinctiveSecondaryButton(
    label: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RectangleShape,
        border = BorderStroke(2.dp, BdTokens.Ink),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = BdTokens.Ink),
        contentPadding = PaddingValues(horizontal = 28.dp, vertical = 16.dp),
        modifier = Modifier.defaultMinSize(minHeight = 56.dp),
    ) {
        Text(
            label.uppercase(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.5.sp,
        )
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFF5F0E8)
@Composable
private fun PreviewLight() {
    Column(
        modifier = Modifier.background(BdTokens.Cream).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        BoldDistinctivePrimaryButton("Pre-order now", onClick = {})
        BoldDistinctivePrimaryButton("Loading", onClick = {}, loading = true)
        BoldDistinctiveSecondaryButton("Manifesto", onClick = {})
    }
}
