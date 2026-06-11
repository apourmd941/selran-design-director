package com.selran.direction.vibrantplayful

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Rocket
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — vibrant-playful
private object VpRowTokens {
    val Cream     = Color(0xFFFFFBF5) // color.bg_primary
    val Ink       = Color(0xFF1A1A1A) // color.fg_primary
    val Muted     = Color(0xFF6A6A6A) // color.fg_muted

    val Palette = listOf(
        Color(0xFFC44A2E), // palette[0]
        Color(0xFF1F6B88), // palette[1]
        Color(0xFF9C6014), // palette[2]
        Color(0xFF553E5A), // palette[3]
        Color(0xFF2A7B60), // palette[4]
    )
}

@Composable
fun VibrantPlayfulListRow(
    title: String,
    icon: ImageVector,
    paletteIndex: Int = 0,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val hue = VpRowTokens.Palette[paletteIndex.coerceIn(0, VpRowTokens.Palette.lastIndex)]
    val hueBg = hue.copy(alpha = 0.14f) // tint chip
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .defaultMinSize(minHeight = 60.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(hueBg, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, contentDescription = null, tint = hue, modifier = Modifier.size(20.dp))
        }
        Text(
            title,
            color = VpRowTokens.Ink,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f),
        )
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = VpRowTokens.Muted,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFFFBF5)
@Composable
private fun PreviewLight() {
    Column(modifier = Modifier.background(VpRowTokens.Cream)) {
        VibrantPlayfulListRow("Getting started", Icons.Outlined.Rocket,    paletteIndex = 0, onClick = {})
        VibrantPlayfulListRow("Team",            Icons.Outlined.Groups,    paletteIndex = 1, onClick = {})
        VibrantPlayfulListRow("Tips & tricks",   Icons.Outlined.Lightbulb, paletteIndex = 2, onClick = {})
        VibrantPlayfulListRow("Insights",        Icons.Outlined.BarChart,  paletteIndex = 4, onClick = {})
    }
}
