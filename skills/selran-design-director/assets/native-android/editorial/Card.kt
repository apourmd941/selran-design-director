package com.selran.direction.editorial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RectangleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — editorial
private object EdCardTokens {
    val Paper    = Color(0xFFF5F1EA)    // color.bg_primary
    val Ink      = Color(0xFF1A1A1A)    // color.fg_primary
    val InkSoft  = Color(0xFF3D3D3D)    // color.fg_secondary
    val Muted    = Color(0xFF6B6B6B)    // color.fg_muted
    val Rule     = Color(0xFFD6CFBE)    // color.border
    val Accent   = Color(0xFF7A1F1F)    // color.accent
    val Serif    = FontFamily.Serif     // → R.font.fraunces
}

@Composable
fun EditorialCard(
    eyebrow: String,
    title: String,
    body: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    // Editorial uses rule-lines and generous whitespace, not enclosed "card" shapes.
    Surface(
        color = EdCardTokens.Paper,
        modifier = modifier.fillMaxWidth(),
        shape = RectangleShape,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                eyebrow.uppercase(),
                color = EdCardTokens.Muted,
                style = TextStyle(fontSize = 11.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Medium),
            )
            Text(
                title,
                color = EdCardTokens.Ink,
                style = TextStyle(
                    fontFamily = EdCardTokens.Serif,
                    fontSize = 26.sp,
                    lineHeight = 30.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = (-0.4).sp,
                ),
            )
            HorizontalDivider(color = EdCardTokens.Rule, thickness = 1.dp)
            Text(
                body,
                color = EdCardTokens.InkSoft,
                style = TextStyle(
                    fontFamily = EdCardTokens.Serif,
                    fontSize = 17.sp,
                    lineHeight = 28.sp,
                    fontStyle = FontStyle.Italic,
                ),
            )
            if (actionLabel != null && onAction != null) {
                TextButton(onClick = onAction, contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)) {
                    Text(
                        actionLabel,
                        color = EdCardTokens.Accent,
                        style = TextStyle(
                            fontFamily = EdCardTokens.Serif,
                            fontSize = 15.sp,
                            textDecoration = TextDecoration.Underline,
                        ),
                    )
                }
            }
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFF5F1EA)
@Composable
private fun PreviewLight() {
    Column(modifier = Modifier.background(EdCardTokens.Paper).padding(8.dp)) {
        EditorialCard(
            eyebrow = "Chapter 04",
            title = "On the quiet work of maintenance.",
            body = "A meditation on what remains when novelty moves on — the slow, unseen labor that sustains the institutions we inherit.",
            actionLabel = "Continue reading",
            onAction = {},
        )
    }
}
