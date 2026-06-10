package com.selran.direction.warmapproachable

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
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

// Tokens — warm-approachable (see assets/direction-starters/warm-approachable.md)
private object WarmTokens {
    val Accent      = Color(0xFFB04A2C) // color.accent (terracotta)
    val AccentHover = Color(0xFF8E381A) // color.accent_hover
    val Cream       = Color(0xFFFAF3E7) // color.bg_primary
    val Cocoa       = Color(0xFF2D1B0F) // color.fg_primary
    val BorderStrong= Color(0xFFC9B18C) // color.border_strong
    val Radius      = 16.dp             // spacing.radius.md — rounder
}

@Composable
fun WarmApproachablePrimaryButton(
    label: String,
    onClick: () -> Unit,
    loading: Boolean = false,
    enabled: Boolean = true,
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.98f else 1.0f,
        animationSpec = spring(dampingRatio = 0.7f, stiffness = Spring.StiffnessMedium),
        label = "warmPressScale",
    )

    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        shape = RoundedCornerShape(WarmTokens.Radius),
        colors = ButtonDefaults.buttonColors(
            containerColor = WarmTokens.Accent,
            contentColor   = WarmTokens.Cream,
        ),
        interactionSource = interaction,
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
        modifier = Modifier.defaultMinSize(minHeight = 48.dp).scale(scale),
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.dp,
                color = WarmTokens.Cream,
            )
        } else {
            Text(label, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun WarmApproachableSecondaryButton(
    label: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(WarmTokens.Radius),
        border = BorderStroke(1.5.dp, WarmTokens.BorderStrong),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = WarmTokens.Cocoa),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
        modifier = Modifier.defaultMinSize(minHeight = 48.dp),
    ) {
        Text(label, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFAF3E7)
@Composable
private fun PreviewLight() {
    Column(
        modifier = Modifier.background(WarmTokens.Cream).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        WarmApproachablePrimaryButton("Book a stay", onClick = {})
        WarmApproachablePrimaryButton("Loading", onClick = {}, loading = true)
        WarmApproachableSecondaryButton("Learn more", onClick = {})
    }
}
