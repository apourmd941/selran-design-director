package com.selran.direction.brutalist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RectangleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — brutalist (see assets/direction-starters/brutalist.md)
// Inversion on press (no ease, instant), 2dp black border, 0 radius, monospace caps.
private object BrutalistTokens {
    val White   = Color(0xFFFFFFFF) // color.bg_primary
    val Black   = Color(0xFF000000) // color.fg_primary / border
    val Orange  = Color(0xFFB84400) // color.accent (safety)
    val Mono    = FontFamily.Monospace // → R.font.space_mono
}

@Composable
fun BrutalistPrimaryButton(
    label: String,
    onClick: () -> Unit,
    loading: Boolean = false,
    enabled: Boolean = true,
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()

    // Hover/press = inversion, no transition.
    val bg = if (pressed) BrutalistTokens.White else BrutalistTokens.Black
    val fg = if (pressed) BrutalistTokens.Black else BrutalistTokens.White

    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(containerColor = bg, contentColor = fg),
        border = BorderStroke(2.dp, BrutalistTokens.Black),
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        interactionSource = interaction,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        modifier = Modifier.defaultMinSize(minHeight = 48.dp),
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp,
                color = fg,
            )
        } else {
            Text(
                label.uppercase(),
                style = TextStyle(
                    fontFamily = BrutalistTokens.Mono,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                ),
            )
        }
    }
}

@Composable
fun BrutalistSecondaryButton(
    label: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RectangleShape,
        border = BorderStroke(2.dp, BrutalistTokens.Black),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = BrutalistTokens.Black),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        modifier = Modifier.defaultMinSize(minHeight = 48.dp),
    ) {
        Text(
            label.uppercase(),
            style = TextStyle(
                fontFamily = BrutalistTokens.Mono,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
            ),
        )
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewLight() {
    Column(
        modifier = Modifier.background(BrutalistTokens.White).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        BrutalistPrimaryButton("Submit", onClick = {})
        BrutalistPrimaryButton("Saving", onClick = {}, loading = true)
        BrutalistSecondaryButton("Cancel", onClick = {})
    }
}
