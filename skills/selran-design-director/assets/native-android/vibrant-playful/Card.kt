package com.selran.direction.vibrantplayful

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — vibrant-playful
// Palette hues used in order: each section gets its own palette[i] color-code.
private object VpCardTokens {
    val Cream     = Color(0xFFFFFBF5) // color.bg_primary
    val Surface   = Color(0xFFF5EFE1) // color.bg_secondary
    val Ink       = Color(0xFF1A1A1A) // color.fg_primary
    val InkSoft   = Color(0xFF3F3F3F) // color.fg_secondary

    // color.palette
    val Palette = listOf(
        Color(0xFFC44A2E), // palette[0] — primary accent
        Color(0xFF1F6B88), // palette[1] — blue
        Color(0xFF9C6014), // palette[2] — ochre
        Color(0xFF553E5A), // palette[3] — plum
        Color(0xFF2A7B60), // palette[4] — green
    )
    val Radius = 20.dp // spacing.radius.lg — big-card rounding
}

@Composable
fun VibrantPlayfulCard(
    title: String,
    body: String,
    paletteIndex: Int = 0,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val hue = VpCardTokens.Palette[paletteIndex.coerceIn(0, VpCardTokens.Palette.lastIndex)]
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(VpCardTokens.Radius),
        colors = CardDefaults.cardColors(containerColor = VpCardTokens.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            // Section color dot — the signature color-coding gesture.
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(modifier = Modifier.size(10.dp).background(hue, CircleShape))
                Text(
                    "CHAPTER ${paletteIndex + 1}".padStart(2, '0'),
                    color = hue,
                    fontSize = 11.sp,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
            Text(title, color = VpCardTokens.Ink, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            Text(body, color = VpCardTokens.InkSoft, fontSize = 15.sp, lineHeight = 22.sp)
            if (actionLabel != null && onAction != null) {
                TextButton(onClick = onAction, contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)) {
                    Text(actionLabel, color = hue, fontSize = 15.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFFFBF5)
@Composable
private fun PreviewLight() {
    Column(
        modifier = Modifier.background(VpCardTokens.Cream).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        VibrantPlayfulCard("Get set up", "Walk through the five-minute onboarding.", paletteIndex = 0, actionLabel = "Start", onAction = {})
        VibrantPlayfulCard("Invite your team", "Add up to five teammates for free.",   paletteIndex = 1, actionLabel = "Invite", onAction = {})
        VibrantPlayfulCard("Your first insight", "Connect a data source to see charts.", paletteIndex = 4, actionLabel = "Connect", onAction = {})
    }
}
