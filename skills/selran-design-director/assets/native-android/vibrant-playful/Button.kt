package com.selran.direction.vibrantplayful

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — vibrant-playful (see assets/direction-starters/vibrant-playful.md)
// palette[0..4] = coordinated hues for chart/section/tag color-coding.
private object VpTokens {
    val Cream      = Color(0xFFFFFBF5) // color.bg_primary
    val Ink        = Color(0xFF1A1A1A) // color.fg_primary
    val Accent     = Color(0xFFC44A2E) // palette[0] / color.accent
    val OnAccent   = Color(0xFFFFFBF5) // on-accent
    val Palette1Bg = Color(0xFFFBDFD4) // tint of palette[0]
    val Radius     = 12.dp             // spacing.radius.md
}

@Composable
fun VibrantPlayfulPrimaryButton(
    label: String,
    onClick: () -> Unit,
    loading: Boolean = false,
    enabled: Boolean = true,
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1.0f,
        animationSpec = spring(dampingRatio = 0.55f, stiffness = Spring.StiffnessMediumLow),
        label = "vpBounce",
    )

    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        shape = RoundedCornerShape(VpTokens.Radius),
        colors = ButtonDefaults.buttonColors(
            containerColor = VpTokens.Accent,
            contentColor   = VpTokens.OnAccent,
        ),
        interactionSource = interaction,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        modifier = Modifier.defaultMinSize(minHeight = 48.dp).scale(scale),
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.dp,
                color = VpTokens.OnAccent,
            )
        } else {
            Text(label, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun VibrantPlayfulSecondaryButton(
    label: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    // Tonal variant — tinted with palette[0] at ~15% alpha for "same-color-family" feel.
    FilledTonalButton(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(VpTokens.Radius),
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = VpTokens.Palette1Bg,
            contentColor   = VpTokens.Accent,
        ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        modifier = Modifier.defaultMinSize(minHeight = 48.dp),
    ) {
        Text(label, fontSize = 15.sp, fontWeight = FontWeight.Medium)
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFFFBF5)
@Composable
private fun PreviewLight() {
    Column(
        modifier = Modifier.background(VpTokens.Cream).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        VibrantPlayfulPrimaryButton("Get started", onClick = {})
        VibrantPlayfulPrimaryButton("Loading", onClick = {}, loading = true)
        VibrantPlayfulSecondaryButton("Maybe later", onClick = {})
    }
}
