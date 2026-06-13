package com.selran.direction.darkpremium

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Diamond
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
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

// Tokens — dark-premium
private object DpNavTokens {
    val BgPrimary = Color(0xFF0A0A0B) // color.bg_primary
    val Gold      = Color(0xFFD4AF37) // color.accent
    val FgMuted   = Color(0xFF71717A) // color.fg_muted
    // very subtle gold tint for the selected indicator
    val Indicator = Color(0x33D4AF37)
}

private data class NavDest(val label: String, val icon: ImageVector)

private val DESTS = listOf(
    NavDest("Home",    Icons.Outlined.Home),
    NavDest("Pieces",  Icons.Outlined.Diamond),
    NavDest("Members", Icons.Outlined.Star),
    NavDest("Account", Icons.Outlined.Person),
)

@Composable
fun DarkPremiumBottomNav(selectedIndex: Int, onSelect: (Int) -> Unit) {
    NavigationBar(
        containerColor = DpNavTokens.BgPrimary,
        tonalElevation = 0.dp,
    ) {
        DESTS.forEachIndexed { i, d ->
            NavigationBarItem(
                selected = selectedIndex == i,
                onClick = { onSelect(i) },
                icon = { Icon(d.icon, contentDescription = d.label) },
                label = {
                    Text(
                        d.label.uppercase(),
                        fontSize = 10.sp,
                        letterSpacing = 1.5.sp,
                        fontWeight = FontWeight.Medium,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DpNavTokens.Gold,
                    selectedTextColor = DpNavTokens.Gold,
                    unselectedIconColor = DpNavTokens.FgMuted,
                    unselectedTextColor = DpNavTokens.FgMuted,
                    indicatorColor = DpNavTokens.Indicator,
                ),
            )
        }
    }
}

@Preview(name = "Dark", showBackground = true, backgroundColor = 0xFF0A0A0B,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDark() {
    var sel by remember { mutableIntStateOf(1) }
    DarkPremiumBottomNav(selectedIndex = sel, onSelect = { sel = it })
}
