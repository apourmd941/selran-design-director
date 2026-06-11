package com.selran.direction.bolddistinctive

import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — bold-distinctive
private object BdNavTokens {
    val Cream = Color(0xFFF5F0E8) // color.bg_primary
    val Ink   = Color(0xFF0A0A0B) // color.fg_primary / border
    val Red   = Color(0xFFC72500) // color.accent
    val Muted = Color(0xFF5F5F5F)
    val Serif = FontFamily.Serif  // → R.font.pp_editorial_new
}

private data class BdNavDest(val label: String, val icon: ImageVector)

private val DESTS = listOf(
    BdNavDest("Home",     Icons.Filled.Home),
    BdNavDest("Drops",    Icons.Filled.LocalFireDepartment),
    BdNavDest("Manifesto",Icons.Filled.Campaign),
    BdNavDest("You",      Icons.Filled.Person),
)

@Composable
fun BoldDistinctiveBottomNav(selectedIndex: Int, onSelect: (Int) -> Unit) {
    NavigationBar(
        containerColor = BdNavTokens.Cream,
        tonalElevation = 0.dp,
        modifier = Modifier.border(2.dp, BdNavTokens.Ink),
    ) {
        DESTS.forEachIndexed { i, d ->
            NavigationBarItem(
                selected = selectedIndex == i,
                onClick = { onSelect(i) },
                icon = { Icon(d.icon, contentDescription = d.label) },
                label = {
                    Text(
                        d.label.uppercase(),
                        style = TextStyle(
                            fontFamily = BdNavTokens.Serif,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.5.sp,
                        ),
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BdNavTokens.Cream,
                    selectedTextColor = BdNavTokens.Cream,
                    unselectedIconColor = BdNavTokens.Ink,
                    unselectedTextColor = BdNavTokens.Muted,
                    indicatorColor = BdNavTokens.Red, // slab of red, no rounding
                ),
            )
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFF5F0E8)
@Composable
private fun PreviewLight() {
    var sel by remember { mutableIntStateOf(1) }
    BoldDistinctiveBottomNav(selectedIndex = sel, onSelect = { sel = it })
}
