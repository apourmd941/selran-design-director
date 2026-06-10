package com.selran.direction.darkpremium

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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

// Tokens — dark-premium (see assets/direction-starters/dark-premium.md)
// Dark-by-default: no light variant in this starter.
private object DarkPremiumTokens {
    val Gold        = Color(0xFFD4AF37) // color.accent
    val GoldHover   = Color(0xFFB89428) // color.accent_hover
    val BgPrimary   = Color(0xFF0A0A0B) // color.bg_primary
    val BgLifted    = Color(0xFF151517) // color.bg_secondary
    val FgPrimary   = Color(0xFFF4F4F5) // color.fg_primary
    val BorderStrong= Color(0xFF3F3F46) // color.border_strong
    val Radius      = 6.dp              // spacing.radius.md
}

@Composable
fun DarkPremiumPrimaryButton(
    label: String,
    onClick: () -> Unit,
    loading: Boolean = false,
    enabled: Boolean = true,
) {
    // Filled-dark with thin gold border — the "luxury restraint" move.
    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        shape = RoundedCornerShape(DarkPremiumTokens.Radius),
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkPremiumTokens.BgLifted,
            contentColor   = DarkPremiumTokens.Gold,
        ),
        border = BorderStroke(1.dp, DarkPremiumTokens.Gold),
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
        modifier = Modifier.defaultMinSize(minHeight = 48.dp),
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 1.5.dp,
                color = DarkPremiumTokens.Gold,
            )
        } else {
            Text(
                label.uppercase(),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp,
            )
        }
    }
}

@Composable
fun DarkPremiumSecondaryButton(
    label: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(DarkPremiumTokens.Radius),
        border = BorderStroke(1.dp, DarkPremiumTokens.BorderStrong),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = DarkPremiumTokens.FgPrimary),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
        modifier = Modifier.defaultMinSize(minHeight = 48.dp),
    ) {
        Text(
            label.uppercase(),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 2.sp,
        )
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF0A0A0B,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDark() {
    Column(
        modifier = Modifier.background(DarkPremiumTokens.BgPrimary).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        DarkPremiumPrimaryButton("Reserve now", onClick = {})
        DarkPremiumPrimaryButton("Processing", onClick = {}, loading = true)
        DarkPremiumSecondaryButton("Details", onClick = {})
    }
}
