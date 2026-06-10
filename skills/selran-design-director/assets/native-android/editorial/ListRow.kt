package com.selran.direction.editorial

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
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Star
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

// Tokens — editorial
private object EdRowTokens {
    val Ink     = Color(0xFF1A1A1A) // color.fg_primary
    val Muted   = Color(0xFF6B6B6B) // color.fg_muted
    val Rule    = Color(0xFFD6CFBE) // color.border
    val Paper   = Color(0xFFF5F1EA) // color.bg_primary
    val Serif   = FontFamily.Serif  // → R.font.fraunces
}

@Composable
fun EditorialListRow(
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
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = EdRowTokens.Muted,
            modifier = Modifier.size(22.dp),
        )
        Text(
            title,
            color = EdRowTokens.Ink,
            style = TextStyle(
                fontFamily = EdRowTokens.Serif,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
            ),
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = EdRowTokens.Muted,
            modifier = Modifier.size(16.dp),
        )
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFF5F1EA)
@Composable
private fun PreviewLight() {
    Column(modifier = Modifier.background(EdRowTokens.Paper)) {
        EditorialListRow("The long essay", Icons.Outlined.Article, onClick = {})
        HorizontalDivider(color = EdRowTokens.Rule, thickness = 1.dp, modifier = Modifier.padding(horizontal = 24.dp))
        EditorialListRow("Saved for later", Icons.Outlined.Bookmark, onClick = {})
        HorizontalDivider(color = EdRowTokens.Rule, thickness = 1.dp, modifier = Modifier.padding(horizontal = 24.dp))
        EditorialListRow("Editor's picks", Icons.Outlined.Star, onClick = {})
    }
}
