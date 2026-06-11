package com.selran.direction.warmapproachable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — warm-approachable
private object WaCardTokens {
    val Surface      = Color(0xFFF3E8D3) // color.bg_secondary
    val Cream        = Color(0xFFFAF3E7) // color.bg_primary
    val Cocoa        = Color(0xFF2D1B0F) // color.fg_primary
    val CocoaSoft    = Color(0xFF5A4332) // color.fg_secondary
    val Accent       = Color(0xFFB04A2C) // color.accent
    val Radius       = 16.dp             // spacing.radius.md
    val Serif        = FontFamily.Serif  // → R.font.fraunces (italic accent)
}

@Composable
fun WarmApproachableCard(
    title: String,
    body: String,
    italicAccent: String? = null,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(WaCardTokens.Radius),
        colors = CardDefaults.cardColors(containerColor = WaCardTokens.Surface),
        // Warm tinted soft shadow — Material's tonal elevation handles this naturally.
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                title,
                color = WaCardTokens.Cocoa,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )
            if (italicAccent != null) {
                Text(
                    italicAccent,
                    color = WaCardTokens.Accent,
                    style = TextStyle(
                        fontFamily = WaCardTokens.Serif,
                        fontStyle = FontStyle.Italic,
                        fontSize = 17.sp,
                        lineHeight = 24.sp,
                    ),
                )
            }
            Text(
                body,
                color = WaCardTokens.CocoaSoft,
                fontSize = 15.sp,
                lineHeight = 24.sp,
            )
            if (actionLabel != null && onAction != null) {
                TextButton(onClick = onAction, contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)) {
                    Text(actionLabel, color = WaCardTokens.Accent, fontSize = 15.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFAF3E7)
@Composable
private fun PreviewLight() {
    Column(
        modifier = Modifier.background(WaCardTokens.Cream).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        WarmApproachableCard(
            title = "Sunday bread club",
            italicAccent = "slow mornings, warm dough.",
            body = "Join us each weekend. Grab a loaf on your way out, or stay a while.",
            actionLabel = "Reserve a seat",
            onAction = {},
        )
    }
}
