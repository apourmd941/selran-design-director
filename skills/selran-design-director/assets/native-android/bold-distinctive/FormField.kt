package com.selran.direction.bolddistinctive

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

// Tokens — bold-distinctive
private object BdFieldTokens {
    val Cream  = Color(0xFFF5F0E8)
    val Ink    = Color(0xFF0A0A0B) // color.fg_primary / border
    val Red    = Color(0xFFC72500) // color.accent
    val Muted  = Color(0xFF5F5F5F)
    val Danger = Color(0xFFC81D0B)
    val Serif  = FontFamily.Serif  // → R.font.pp_editorial_new (labels)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoldDistinctiveFormField(
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
            color = BdFieldTokens.Ink,
            style = TextStyle(
                fontFamily = BdFieldTokens.Serif,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp,
            ),
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            singleLine = true,
            shape = RectangleShape,
            textStyle = TextStyle(fontSize = 18.sp, color = BdFieldTokens.Ink, fontWeight = FontWeight.Medium),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor    = BdFieldTokens.Red,
                unfocusedBorderColor  = BdFieldTokens.Ink,
                errorBorderColor      = BdFieldTokens.Danger,
                cursorColor           = BdFieldTokens.Red,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        when {
            error != null  -> Text(error,  color = BdFieldTokens.Danger, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            helper != null -> Text(helper, color = BdFieldTokens.Muted,  fontSize = 13.sp)
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFF5F0E8)
@Composable
private fun PreviewLight() {
    var a by remember { mutableStateOf("") }
    var b by remember { mutableStateOf("maybe") }
    Column(
        modifier = Modifier.background(Color(0xFFF5F0E8)).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        BoldDistinctiveFormField("Your name", a, { a = it }, helper = "First and last, please.")
        BoldDistinctiveFormField("Conviction", b, { b = it }, error = "We don't do 'maybe'.")
    }
}
