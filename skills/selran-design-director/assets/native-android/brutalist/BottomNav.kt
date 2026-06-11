package com.selran.direction.brutalist

import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
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

// Tokens — brutalist
private object BrNavTokens {
    val White  = Color(0xFFFFFFFF)
    val Black  = Color(0xFF000000)
    val Orange = Color(0xFFB84400) // color.accent
    val Muted  = Color(0xFF595959)
    val Mono   = FontFamily.Monospace
}

private data class BrNavDest(val label: String, val icon: ImageVector)

private val DESTS = listOf(
    BrNavDest("INDEX",  Icons.Filled.Home),
    BrNavDest("SEARCH", Icons.Filled.Search),
    BrNavDest("MENU",   Icons.Filled.Menu),
)

@Composable
fun BrutalistBottomNav(selectedIndex: Int, onSelect: (Int) -> Unit) {
    NavigationBar(
        containerColor = BrNavTokens.White,
        tonalElevation = 0.dp,
        modifier = Modifier.border(2.dp, BrNavTokens.Black),
    ) {
        DESTS.forEachIndexed { i, d ->
            NavigationBarItem(
                selected = selectedIndex == i,
                onClick = { onSelect(i) },
                icon = { Icon(d.icon, contentDescription = d.label) },
                label = {
                    Text(
                        d.label,
                        style = TextStyle(
                            fontFamily = BrNavTokens.Mono,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                        ),
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BrNavTokens.White,
                    selectedTextColor = BrNavTokens.White,
                    unselectedIconColor = BrNavTokens.Black,
                    unselectedTextColor = BrNavTokens.Black,
                    // selected pill = orange slab, no ease — brutalist inversion on the indicator
                    indicatorColor = BrNavTokens.Orange,
                ),
            )
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewLight() {
    var sel by remember { mutableIntStateOf(0) }
    BrutalistBottomNav(selectedIndex = sel, onSelect = { sel = it })
}
