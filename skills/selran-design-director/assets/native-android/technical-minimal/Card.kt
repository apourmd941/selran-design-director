package com.selran.direction.technicalminimal

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — technical-minimal
private object TmCardTokens {
    val BgSecondary = Color(0xFFF4F4F5) // color.bg_secondary
    val Border      = Color(0xFFE4E4E7) // color.border
    val FgPrimary   = Color(0xFF18181B) // color.fg_primary
    val FgSecondary = Color(0xFF52525B) // color.fg_secondary
    val Accent      = Color(0xFF0A7A5C) // color.accent
    val Radius      = 8.dp              // spacing.radius.md
}

@Composable
fun TechnicalMinimalCard(
    title: String,
    body: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(TmCardTokens.Radius),
        colors = CardDefaults.cardColors(containerColor = TmCardTokens.BgSecondary),
        elevation = CardDefaults.cardElevation(0.dp), // hairline, not shadow
        border = BorderStroke(1.dp, TmCardTokens.Border),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                title,
                color = TmCardTokens.FgPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                body,
                color = TmCardTokens.FgSecondary,
                fontSize = 14.sp,
                lineHeight = 22.sp,
            )
            if (actionLabel != null && onAction != null) {
                TextButton(onClick = onAction, contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)) {
                    Text(
                        actionLabel,
                        color = TmCardTokens.Accent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFAFAF9)
@Composable
private fun PreviewLight() {
    Column(
        modifier = Modifier.background(Color(0xFFFAFAF9)).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TechnicalMinimalCard(
            title = "Monthly recurring revenue",
            body = "Up 12.4% from last month. Invoice sync completes at 04:00 UTC.",
            actionLabel = "View report",
            onAction = {},
        )
    }
}
