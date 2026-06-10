package com.selran.direction.editorial

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — editorial
private object EdNavTokens {
    val Paper     = Color(0xFFF5F1EA) // color.bg_primary
    val Accent    = Color(0xFF7A1F1F) // color.accent
    val Muted     = Color(0xFF6B6B6B) // color.fg_muted
    val Indicator = Color(0xFFEEE8DC) // color.bg_secondary (subtle)
    val Serif     = FontFamily.Serif
}

private data class NavDest(val label: String, val icon: ImageVector)

private val DESTS = listOf(
    NavDest("Today",   Icons.Outlined.Home),
    NavDest("Read",    Icons.Outlined.Article),
    NavDest("Saved",   Icons.Outlined.Bookmark),
    NavDest("Account", Icons.Outlined.Person),
)

@Composable
fun EditorialBottomNav(selectedIndex: Int, onSelect: (Int) -> Unit) {
    NavigationBar(
        containerColor = EdNavTokens.Paper,
        tonalElevation = 0.dp,
    ) {
        DESTS.forEachIndexed { i, d ->
            NavigationBarItem(
                selected = selectedIndex == i,
                onClick = { onSelect(i) },
                icon = { Icon(d.icon, contentDescription = d.label) },
                label = {
                    Text(
                        d.label,
                        style = TextStyle(fontFamily = EdNavTokens.Serif, fontSize = 12.sp, letterSpacing = 1.sp),
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = EdNavTokens.Accent,
                    selectedTextColor = EdNavTokens.Accent,
                    unselectedIconColor = EdNavTokens.Muted,
                    unselectedTextColor = EdNavTokens.Muted,
                    indicatorColor = EdNavTokens.Indicator,
                ),
            )
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFF5F1EA)
@Composable
private fun PreviewLight() {
    var sel by remember { mutableIntStateOf(1) }
    EditorialBottomNav(selectedIndex = sel, onSelect = { sel = it })
}
