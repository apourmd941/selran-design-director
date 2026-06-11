package com.selran.direction.warmapproachable

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — warm-approachable
private object WaFieldTokens {
    val Accent     = Color(0xFFB04A2C) // color.accent
    val Border     = Color(0xFFC9B18C) // color.border_strong
    val Cocoa      = Color(0xFF2D1B0F) // color.fg_primary
    val CocoaSoft  = Color(0xFF7A6250) // color.fg_muted
    val Danger     = Color(0xFFB94A2A) // color.danger
    val Radius     = 16.dp             // spacing.radius.md
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarmApproachableFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    helper: String? = null,
    error: String? = null,
    modifier: Modifier = Modifier,
) {
    val isError = error != null
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(label, color = WaFieldTokens.Cocoa, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            singleLine = true,
            shape = RoundedCornerShape(WaFieldTokens.Radius),
            textStyle = TextStyle(fontSize = 16.sp, color = WaFieldTokens.Cocoa),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor    = WaFieldTokens.Accent,
                unfocusedBorderColor  = WaFieldTokens.Border,
                errorBorderColor      = WaFieldTokens.Danger,
                cursorColor           = WaFieldTokens.Accent,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        when {
            error != null  -> Text(error,  color = WaFieldTokens.Danger,    fontSize = 13.sp)
            helper != null -> Text(helper, color = WaFieldTokens.CocoaSoft, fontSize = 13.sp)
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFAF3E7)
@Composable
private fun PreviewLight() {
    var a by remember { mutableStateOf("") }
    var b by remember { mutableStateOf("hello") }
    Column(
        modifier = Modifier.background(Color(0xFFFAF3E7)).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        WarmApproachableFormField("Your name", a, { a = it }, helper = "We'll greet you this way.")
        WarmApproachableFormField("Email", b, { b = it }, error = "That doesn't look like an email.")
    }
}
