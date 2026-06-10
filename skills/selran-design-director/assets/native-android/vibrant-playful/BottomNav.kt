package com.selran.direction.vibrantplayful

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Explore
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tokens — vibrant-playful
// Each tab takes a palette hue — the color-coded navigation signature.
private object VpNavTokens {
    val Cream = Color(0xFFFFFBF5) // color.bg_primary
    val Muted = Color(0xFF6A6A6A) // color.fg_muted
    val Palette = listOf(
        Color(0xFFC44A2E), // palette[0]
        Color(0xFF1F6B88), // palette[1]
        Color(0xFF9C6014), // palette[2]
        Color(0xFF553E5A), // palette[3]
    )
}

private data class VpNavDest(
    val label: String,
    val iconSelected: ImageVector,
    val iconUnselected: ImageVector,
)

private val DESTS = listOf(
    VpNavDest("Home",      Icons.Filled.Home,     Icons.Outlined.Home),
    VpNavDest("Explore",   Icons.Filled.Explore,  Icons.Outlined.Explore),
    VpNavDest("Insights",  Icons.Filled.BarChart, Icons.Outlined.BarChart),
    VpNavDest("Me",        Icons.Filled.Person,   Icons.Outlined.Person),
)

@Composable
fun VibrantPlayfulBottomNav(selectedIndex: Int, onSelect: (Int) -> Unit) {
    NavigationBar(containerColor = VpNavTokens.Cream, tonalElevation = 0.dp) {
        DESTS.forEachIndexed { i, d ->
            val selected = selectedIndex == i
            val hue = VpNavTokens.Palette[i % VpNavTokens.Palette.size]
            val scale by animateFloatAsState(
                targetValue = if (selected) 1.08f else 1.0f,
                animationSpec = spring(dampingRatio = 0.55f, stiffness = Spring.StiffnessMediumLow),
                label = "vpTabScale",
            )
            NavigationBarItem(
                selected = selected,
                onClick = { onSelect(i) },
                icon = {
                    Icon(
                        if (selected) d.iconSelected else d.iconUnselected,
                        contentDescription = d.label,
                        modifier = Modifier.scale(scale),
                    )
                },
                label = { Text(d.label, fontSize = 12.sp, fontWeight = FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = hue,
                    selectedTextColor = hue,
                    unselectedIconColor = VpNavTokens.Muted,
                    unselectedTextColor = VpNavTokens.Muted,
                    indicatorColor = hue.copy(alpha = 0.14f),
                ),
            )
        }
    }
}

@Preview(name = "Light", showBackground = true, backgroundColor = 0xFFFFFBF5)
@Composable
private fun PreviewLight() {
    var sel by remember { mutableIntStateOf(1) }
    VibrantPlayfulBottomNav(selectedIndex = sel, onSelect = { sel = it })
}
