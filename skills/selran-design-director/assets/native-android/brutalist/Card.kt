package com.selran.direction.brutalist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RectangleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — brutalist
private object BrCardTokens {
    val White  = Color(0xFFFFFFFF) // color.bg_primary
    val Black  = Color(0xFF000000) // color.fg_primary / border
    val Orange = Color(0xFFB84400) // color.accent
    val Muted  = Color(0xFF595959) // color.fg_muted
    val Mono   = FontFamily.Monospace
}

@Composable
fun BrutalistCard(
    title: String,
    body: String,
    meta: String? = null,       // e.g. "2.4 KB · 312 words · 2026-04-21"
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = BrCardTokens.White),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(2.dp, BrCardTokens.Black),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (meta != null) {
                Text(
                    meta,
                    color = BrCardTokens.Muted,
                    style = TextStyle(fontFamily = BrCardTokens.Mono, fontSize = 11.sp),
                )
            }
            Text(
                title.uppercase(),
                color = BrCardTokens.Black,
                style = TextStyle(
                    fontFamily = BrCardTokens.Mono,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                ),
            )
            Row(modifier = Modifier.background(BrCardTokens.Black).fillMaxWidth().padding(1.dp)) {}
            Text(
                body,
                color = BrCardTokens.Black,
                style = TextStyle(
                    fontFamily = BrCardTokens.Mono,
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                ),
            )
            if (actionLabel != null && onAction != null) {
                TextButton(onClick = onAction, contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)) {
                    Text(
                        actionLabel.uppercase() + " →",
                        color = BrCardTokens.Orange,
                        style = TextStyle(
                            fontFamily = BrCardTokens.Mono,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                        ),
                    )
                }
            }
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewLight() {
    Column(
        modifier = Modifier.background(BrCardTokens.White).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        BrutalistCard(
            title = "readme.md",
            meta  = "2.4 KB · 312 words · 2026-04-21",
            body  = "This site is a manifesto. View source. Nothing is hidden. Nothing is polished.",
            actionLabel = "read full",
            onAction = {},
        )
    }
}
