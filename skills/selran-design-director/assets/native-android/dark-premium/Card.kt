package com.selran.direction.darkpremium

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

// Tokens — dark-premium
private object DpCardTokens {
    val BgLifted     = Color(0xFF151517) // color.bg_secondary (one step above primary)
    val BgPrimary    = Color(0xFF0A0A0B) // color.bg_primary
    val Border       = Color(0xFF27272A) // color.border
    val FgPrimary    = Color(0xFFF4F4F5) // color.fg_primary
    val FgSecondary  = Color(0xFFA1A1AA) // color.fg_secondary
    val Gold         = Color(0xFFD4AF37) // color.accent
    val Radius       = 10.dp             // spacing.radius.lg
}

@Composable
fun DarkPremiumCard(
    title: String,
    body: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(DpCardTokens.Radius),
        colors = CardDefaults.cardColors(containerColor = DpCardTokens.BgLifted),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, DpCardTokens.Border),
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // thin accent underline — "hero moment" signature
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .height(1.dp)
                    .background(DpCardTokens.Gold)
            )
            Text(
                title,
                color = DpCardTokens.FgPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.3).sp,
            )
            Text(
                body,
                color = DpCardTokens.FgSecondary,
                fontSize = 15.sp,
                lineHeight = 24.sp,
            )
            if (actionLabel != null && onAction != null) {
                TextButton(onClick = onAction, contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)) {
                    Text(
                        actionLabel.uppercase(),
                        color = DpCardTokens.Gold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 2.sp,
                    )
                }
            }
        }
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF0A0A0B,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDark() {
    Column(
        modifier = Modifier.background(DpCardTokens.BgPrimary).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        DarkPremiumCard(
            title = "Limited edition, N°24",
            body = "A thirty-six piece run. Hand-finished, individually numbered. Allocation opens to members first.",
            actionLabel = "Request access",
            onAction = {},
        )
    }
}
