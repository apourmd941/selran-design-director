package com.selran.direction.brutalist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RectangleShape
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

// Tokens — brutalist
private object BrFieldTokens {
    val White  = Color(0xFFFFFFFF)
    val Black  = Color(0xFF000000)
    val Danger = Color(0xFFD10000)
    val Muted  = Color(0xFF595959)
    val Mono   = FontFamily.Monospace
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrutalistFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    helper: String? = null,
    error: String? = null,
    modifier: Modifier = Modifier,
) {
    val isError = error != null
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            "> " + label.uppercase(),
            color = BrFieldTokens.Black,
            style = TextStyle(fontFamily = BrFieldTokens.Mono, fontSize = 12.sp, fontWeight = FontWeight.Bold),
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            singleLine = true,
            shape = RectangleShape,
            textStyle = TextStyle(fontFamily = BrFieldTokens.Mono, fontSize = 15.sp, color = BrFieldTokens.Black),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor    = BrFieldTokens.Black,
                unfocusedBorderColor  = BrFieldTokens.Black,
                errorBorderColor      = BrFieldTokens.Danger,
                cursorColor           = BrFieldTokens.Black,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        when {
            error != null -> Text(
                "ERROR: $error",
                color = BrFieldTokens.Danger,
                style = TextStyle(fontFamily = BrFieldTokens.Mono, fontSize = 12.sp),
            )
            helper != null -> Text(
                "// $helper",
                color = BrFieldTokens.Muted,
                style = TextStyle(fontFamily = BrFieldTokens.Mono, fontSize = 12.sp),
            )
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewLight() {
    var a by remember { mutableStateOf("user_01") }
    var b by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.background(Color(0xFFFFFFFF)).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        BrutalistFormField("Username", a, { a = it }, helper = "lowercase, 3–20 chars")
        BrutalistFormField("Password", b, { b = it }, error = "required")
    }
}
