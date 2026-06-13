package com.selran.direction.bolddistinctive

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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — bold-distinctive
private object BdRowTokens {
    val Cream = Color(0xFFF5F0E8)
    val Ink   = Color(0xFF0A0A0B) // color.fg_primary / border
    val Red   = Color(0xFFC72500) // color.accent
    val Muted = Color(0xFF5F5F5F) // color.fg_muted
    val Serif = FontFamily.Serif  // → R.font.pp_editorial_new
}

@Composable
fun BoldDistinctiveListRow(
    number: Int,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .defaultMinSize(minHeight = 72.dp)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Big numeric marker — section architecture at the row level
        Text(
            "%02d".format(number),
            color = BdRowTokens.Red,
            style = TextStyle(
                fontFamily = BdRowTokens.Serif,
                fontSize = 36.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-1.5).sp,
            ),
        )
        Icon(icon, contentDescription = null, tint = BdRowTokens.Ink, modifier = Modifier.size(22.dp))
        Text(
            title,
            color = BdRowTokens.Ink,
            style = TextStyle(
                fontFamily = BdRowTokens.Serif,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 26.sp,
            ),
            modifier = Modifier.weight(1f),
        )
        Icon(
            Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = BdRowTokens.Ink,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFF5F0E8)
@Composable
private fun PreviewLight() {
    Column(modifier = Modifier.background(BdRowTokens.Cream)) {
        BoldDistinctiveListRow(1, "The manifesto",   Icons.Outlined.Campaign, onClick = {})
        HorizontalDivider(color = BdRowTokens.Ink, thickness = 2.dp)
        BoldDistinctiveListRow(2, "What we believe", Icons.Outlined.LocalFireDepartment, onClick = {})
        HorizontalDivider(color = BdRowTokens.Ink, thickness = 2.dp)
        BoldDistinctiveListRow(3, "Our commitments", Icons.Outlined.Stars, onClick = {})
    }
}
