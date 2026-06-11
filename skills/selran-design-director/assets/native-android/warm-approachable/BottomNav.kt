package com.selran.direction.warmapproachable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — warm-approachable
private object WaNavTokens {
    val Cream     = Color(0xFFFAF3E7) // color.bg_primary
    val Accent    = Color(0xFFB04A2C) // color.accent
    val CocoaSoft = Color(0xFF7A6250) // color.fg_muted
    val Indicator = Color(0xFFF3E8D3) // color.bg_secondary — gentle pill behind icon
}

private data class NavDest(
    val label: String,
    val iconSelected: ImageVector,
    val iconUnselected: ImageVector,
)

private val DESTS = listOf(
    NavDest("Home",      Icons.Filled.Home,     Icons.Outlined.Home),
    NavDest("Favorites", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
    NavDest("Shop",      Icons.Filled.Coffee,   Icons.Outlined.Coffee),
    NavDest("Me",        Icons.Filled.Person,   Icons.Outlined.Person),
)

@Composable
fun WarmApproachableBottomNav(selectedIndex: Int, onSelect: (Int) -> Unit) {
    NavigationBar(
        containerColor = WaNavTokens.Cream,
        tonalElevation = 0.dp,
    ) {
        DESTS.forEachIndexed { i, d ->
            val selected = selectedIndex == i
            NavigationBarItem(
                selected = selected,
                onClick = { onSelect(i) },
                icon = {
                    Icon(
                        if (selected) d.iconSelected else d.iconUnselected,
                        contentDescription = d.label,
                    )
                },
                label = { Text(d.label, fontSize = 12.sp, fontWeight = FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = WaNavTokens.Accent,
                    selectedTextColor = WaNavTokens.Accent,
                    unselectedIconColor = WaNavTokens.CocoaSoft,
                    unselectedTextColor = WaNavTokens.CocoaSoft,
                    indicatorColor = WaNavTokens.Indicator,
                ),
            )
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFAF3E7)
@Composable
private fun PreviewLight() {
    var sel by remember { mutableIntStateOf(0) }
    WarmApproachableBottomNav(selectedIndex = sel, onSelect = { sel = it })
}
