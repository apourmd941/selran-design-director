package com.selran.direction.bolddistinctive

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — bold-distinctive
private object BdCardTokens {
    val Cream    = Color(0xFFF5F0E8) // color.bg_primary
    val Surface  = Color(0xFFECE4D4) // color.bg_secondary
    val Ink      = Color(0xFF0A0A0B) // color.fg_primary / border
    val InkSoft  = Color(0xFF2B2B2B) // color.fg_secondary
    val Red      = Color(0xFFC72500) // color.accent
    val Serif    = FontFamily.Serif  // → R.font.pp_editorial_new weight 900
}

@Composable
fun BoldDistinctiveCard(
    sectionNumber: Int,
    title: String,
    body: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = BdCardTokens.Cream),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(2.dp, BdCardTokens.Ink),
    ) {
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Huge section marker — display type as layout architecture.
                Text(
                    "%02d".format(sectionNumber),
                    color = BdCardTokens.Red,
                    style = TextStyle(
                        fontFamily = BdCardTokens.Serif,
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = (-3).sp,
                        lineHeight = 72.sp,
                    ),
                )
            }
            Text(
                title,
                color = BdCardTokens.Ink,
                style = TextStyle(
                    fontFamily = BdCardTokens.Serif,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-1).sp,
                    lineHeight = 34.sp,
                ),
            )
            Text(
                body,
                color = BdCardTokens.InkSoft,
                fontSize = 15.sp,
                lineHeight = 24.sp,
            )
            if (actionLabel != null && onAction != null) {
                TextButton(onClick = onAction, contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)) {
                    Text(
                        actionLabel.uppercase() + " →",
                        color = BdCardTokens.Red,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                    )
                }
            }
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFF5F0E8)
@Composable
private fun PreviewLight() {
    Column(
        modifier = Modifier.background(BdCardTokens.Cream).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        BoldDistinctiveCard(
            sectionNumber = 1,
            title = "Earn the damn attention.",
            body  = "Most products whisper. Ours sings. Read why we built it this way.",
            actionLabel = "Read more",
            onAction = {},
        )
    }
}
