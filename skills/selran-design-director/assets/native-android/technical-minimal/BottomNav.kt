package com.selran.direction.technicalminimal

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BarChart
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

// Tokens — technical-minimal
private object TmNavTokens {
    val Bg          = Color(0xFFFAFAF9) // color.bg_primary
    val Accent      = Color(0xFF0A7A5C) // color.accent
    val OnAccent    = Color(0xFFFAFAF9)
    val FgMuted     = Color(0xFF71717A) // color.fg_muted
    val FgPrimary   = Color(0xFF18181B) // color.fg_primary
    val Indicator   = Color(0xFFDCEEE8) // 12% tint of accent
}

private data class NavDest(val label: String, val icon: ImageVector)

private val DESTS = listOf(
    NavDest("Home",     Icons.Filled.Home),
    NavDest("Explore",  Icons.Filled.Search),
    NavDest("Reports",  Icons.Outlined.BarChart),
    NavDest("Settings", Icons.Filled.Settings),
)

@Composable
fun TechnicalMinimalBottomNav(
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
) {
    NavigationBar(
        containerColor = TmNavTokens.Bg,
        tonalElevation = 0.dp,
    ) {
        DESTS.forEachIndexed { i, d ->
            NavigationBarItem(
                selected = selectedIndex == i,
                onClick = { onSelect(i) },
                icon = { Icon(d.icon, contentDescription = d.label) },
                label = { Text(d.label, fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = TmNavTokens.Accent,
                    selectedTextColor = TmNavTokens.Accent,
                    unselectedIconColor = TmNavTokens.FgMuted,
                    unselectedTextColor = TmNavTokens.FgMuted,
                    indicatorColor = TmNavTokens.Indicator,
                ),
            )
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFAFAF9)
@Composable
private fun PreviewLight() {
    var sel by remember { mutableIntStateOf(0) }
    TechnicalMinimalBottomNav(selectedIndex = sel, onSelect = { sel = it })
}
