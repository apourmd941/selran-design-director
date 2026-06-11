package com.selran.direction.technicalminimal

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
import androidx.compose.material.icons.filled.BarChart
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

// Tokens — technical-minimal
private object TmRowTokens {
    val FgPrimary   = Color(0xFF18181B) // color.fg_primary
    val FgMuted     = Color(0xFF71717A) // color.fg_muted
    val Border      = Color(0xFFE4E4E7) // color.border
    val Bg          = Color(0xFFFAFAF9) // color.bg_primary
}

@Composable
fun TechnicalMinimalListRow(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .defaultMinSize(minHeight = 48.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = TmRowTokens.FgMuted,
            modifier = Modifier.size(20.dp),
        )
        Text(
            title,
            color = TmRowTokens.FgPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = TmRowTokens.FgMuted,
            modifier = Modifier.size(18.dp),
        )
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFAFAF9)
@Composable
private fun PreviewLight() {
    Column(modifier = Modifier.background(TmRowTokens.Bg)) {
        TechnicalMinimalListRow("Usage & billing", Icons.Filled.BarChart, onClick = {})
        HorizontalDivider(color = TmRowTokens.Border, thickness = 1.dp)
        TechnicalMinimalListRow("API keys", Icons.Filled.BarChart, onClick = {})
        HorizontalDivider(color = TmRowTokens.Border, thickness = 1.dp)
        TechnicalMinimalListRow("Webhooks", Icons.Filled.BarChart, onClick = {})
    }
}
