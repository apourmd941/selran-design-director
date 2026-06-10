package com.selran.direction.editorial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — editorial
private object EdFieldTokens {
    val Accent       = Color(0xFF7A1F1F) // color.accent
    val Border       = Color(0xFFB8AE96) // color.border_strong
    val Ink          = Color(0xFF1A1A1A) // color.fg_primary
    val Muted        = Color(0xFF6B6B6B) // color.fg_muted
    val Danger       = Color(0xFF7A1F1F) // color.danger
    val Radius       = 2.dp              // spacing.radius.sm
    val Serif        = FontFamily.Serif
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorialFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    helper: String? = null,
    error: String? = null,
    modifier: Modifier = Modifier,
) {
    val isError = error != null
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            label.uppercase(),
            color = EdFieldTokens.Muted,
            style = TextStyle(fontSize = 11.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Medium),
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            singleLine = true,
            shape = RoundedCornerShape(EdFieldTokens.Radius),
            textStyle = TextStyle(fontFamily = EdFieldTokens.Serif, fontSize = 17.sp, color = EdFieldTokens.Ink),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = EdFieldTokens.Accent,
                unfocusedBorderColor = EdFieldTokens.Border,
                errorBorderColor     = EdFieldTokens.Danger,
                cursorColor          = EdFieldTokens.Accent,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        when {
            error != null -> Text(
                error, color = EdFieldTokens.Danger,
                style = TextStyle(fontFamily = EdFieldTokens.Serif, fontSize = 13.sp),
            )
            helper != null -> Text(
                helper, color = EdFieldTokens.Muted,
                style = TextStyle(fontFamily = EdFieldTokens.Serif, fontSize = 13.sp),
            )
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFF5F1EA)
@Composable
private fun PreviewLight() {
    var a by remember { mutableStateOf("") }
    var b by remember { mutableStateOf("The letter") }
    Column(
        modifier = Modifier.background(Color(0xFFF5F1EA)).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        EditorialFormField("Name", a, { a = it }, helper = "Your byline, as it should appear.")
        EditorialFormField("Title", b, { b = it }, error = "Titles use sentence case.")
    }
}
