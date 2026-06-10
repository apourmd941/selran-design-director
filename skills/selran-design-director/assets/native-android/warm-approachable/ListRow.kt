package com.selran.direction.warmapproachable

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Place
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

// Tokens — warm-approachable
private object WaRowTokens {
    val Cream     = Color(0xFFFAF3E7) // color.bg_primary
    val Surface   = Color(0xFFF3E8D3) // color.bg_secondary
    val Cocoa     = Color(0xFF2D1B0F) // color.fg_primary
    val CocoaSoft = Color(0xFF7A6250) // color.fg_muted
    val Accent    = Color(0xFFB04A2C) // color.accent
}

@Composable
fun WarmApproachableListRow(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .defaultMinSize(minHeight = 64.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        // Circular icon chip — organic shape among orthogonal layout
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(WaRowTokens.Surface, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = WaRowTokens.Accent,
                modifier = Modifier.size(20.dp),
            )
        }
        Text(
            title,
            color = WaRowTokens.Cocoa,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = WaRowTokens.CocoaSoft,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFAF3E7)
@Composable
private fun PreviewLight() {
    Column(
        modifier = Modifier.background(WaRowTokens.Cream).padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        WarmApproachableListRow("Favorite spots", Icons.Outlined.Favorite, onClick = {})
        WarmApproachableListRow("Nearby", Icons.Outlined.Place, onClick = {})
        WarmApproachableListRow("Coffee log", Icons.Outlined.Coffee, onClick = {})
    }
}
