package com.selran.direction.brutalist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RectangleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Folder
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

// Tokens — brutalist
private object BrRowTokens {
    val White = Color(0xFFFFFFFF) // color.bg_primary
    val Black = Color(0xFF000000) // color.fg_primary / border
    val Muted = Color(0xFF595959) // color.fg_muted
    val Mono  = FontFamily.Monospace
}

@Composable
fun BrutalistListRow(
    title: String,
    icon: ImageVector,
    sizeLabel: String? = null, // e.g. "12 KB"
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .defaultMinSize(minHeight = 48.dp)
            .border(1.dp, BrRowTokens.Black, RectangleShape)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(icon, contentDescription = null, tint = BrRowTokens.Black, modifier = Modifier.size(20.dp))
        Text(
            title,
            color = BrRowTokens.Black,
            style = TextStyle(
                fontFamily = BrRowTokens.Mono,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier.weight(1f),
        )
        if (sizeLabel != null) {
            Text(
                sizeLabel,
                color = BrRowTokens.Muted,
                style = TextStyle(fontFamily = BrRowTokens.Mono, fontSize = 11.sp),
            )
        }
        Icon(
            Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = BrRowTokens.Black,
            modifier = Modifier.size(18.dp),
        )
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewLight() {
    Column(
        modifier = Modifier.background(BrRowTokens.White),
        verticalArrangement = Arrangement.spacedBy((-1).dp), // collapse shared borders
    ) {
        BrutalistListRow("index.md",   Icons.Outlined.Article, sizeLabel = "2.4 KB", onClick = {})
        BrutalistListRow("src/",       Icons.Outlined.Folder,  sizeLabel = "14 files", onClick = {})
        BrutalistListRow("build.kt",   Icons.Outlined.Code,    sizeLabel = "842 B",  onClick = {})
    }
}
