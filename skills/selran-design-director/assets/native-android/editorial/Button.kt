package com.selran.direction.editorial

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — editorial (see assets/direction-starters/editorial.md)
// Swap FontFamily.Serif for R.font.fraunces_regular in a real project.
private object EditorialTokens {
    val Accent      = Color(0xFF7A1F1F) // color.accent (oxblood)
    val AccentHover = Color(0xFF5C1717) // color.accent_hover
    val Paper       = Color(0xFFF5F1EA) // color.bg_primary
    val Ink         = Color(0xFF1A1A1A) // color.fg_primary
    val Border      = Color(0xFFB8AE96) // color.border_strong
    val Radius      = 2.dp              // spacing.radius.sm — nearly square
    val Serif       = FontFamily.Serif  // → Font(R.font.fraunces_regular)
}

@Composable
fun EditorialPrimaryButton(
    label: String,
    onClick: () -> Unit,
    loading: Boolean = false,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        shape = RoundedCornerShape(EditorialTokens.Radius),
        colors = ButtonDefaults.buttonColors(
            containerColor = EditorialTokens.Accent,
            contentColor   = EditorialTokens.Paper,
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        contentPadding = PaddingValues(horizontal = 28.dp, vertical = 14.dp),
        modifier = Modifier.defaultMinSize(minHeight = 48.dp),
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 1.5.dp,
                color = EditorialTokens.Paper,
            )
        } else {
            Text(
                label,
                style = TextStyle(
                    fontFamily = EditorialTokens.Serif,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 0.2.sp,
                ),
            )
        }
    }
}

@Composable
fun EditorialSecondaryButton(
    label: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(EditorialTokens.Radius),
        border = BorderStroke(1.dp, EditorialTokens.Border),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = EditorialTokens.Ink),
        contentPadding = PaddingValues(horizontal = 28.dp, vertical = 14.dp),
        modifier = Modifier.defaultMinSize(minHeight = 48.dp),
    ) {
        Text(
            label,
            style = TextStyle(fontFamily = EditorialTokens.Serif, fontSize = 15.sp),
        )
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFF5F1EA)
@Composable
private fun PreviewLight() {
    Column(
        modifier = Modifier.background(EditorialTokens.Paper).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        EditorialPrimaryButton("Read the essay", onClick = {})
        EditorialPrimaryButton("Submitting", onClick = {}, loading = true)
        EditorialSecondaryButton("Archive", onClick = {})
    }
}
