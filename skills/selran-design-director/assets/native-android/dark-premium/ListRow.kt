package com.selran.direction.darkpremium

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Diamond
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Watch
import androidx.compose.material3.HorizontalDivider
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

// Tokens — dark-premium
private object DpRowTokens {
    val BgPrimary = Color(0xFF0A0A0B) // color.bg_primary
    val Border    = Color(0xFF27272A) // color.border
    val FgPrimary = Color(0xFFF4F4F5) // color.fg_primary
    val FgMuted   = Color(0xFF71717A) // color.fg_muted
    val Gold      = Color(0xFFD4AF37) // color.accent
}

@Composable
fun DarkPremiumListRow(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .defaultMinSize(minHeight = 56.dp)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = DpRowTokens.Gold,
            modifier = Modifier.size(20.dp),
        )
        Text(
            title,
            color = DpRowTokens.FgPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = DpRowTokens.FgMuted,
            modifier = Modifier.size(18.dp),
        )
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF0A0A0B,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDark() {
    Column(modifier = Modifier.background(DpRowTokens.BgPrimary)) {
        DarkPremiumListRow("Allocations", Icons.Outlined.Diamond, onClick = {})
        HorizontalDivider(color = DpRowTokens.Border, thickness = 1.dp)
        DarkPremiumListRow("Concierge", Icons.Outlined.Star, onClick = {})
        HorizontalDivider(color = DpRowTokens.Border, thickness = 1.dp)
        DarkPremiumListRow("Service history", Icons.Outlined.Watch, onClick = {})
    }
}
