package com.team1.wat2watch.ui.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team1.wat2watch.R

@Composable
fun NavBar(modifier: Modifier = Modifier) {
    val selectedIcon = remember { mutableStateOf("Home Icon") } // Default selected icon

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .requiredWidth(412.dp)
                .requiredHeight(74.dp)
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceEvenly, // Spaces out items
            verticalAlignment = Alignment.CenterVertically // Centers children vertically
        ) {
            // Icon Boxes (Wrapped inside Box for background and padding)
            listOf(
                R.drawable.wat2watch_navbar_home_svg to "Home Icon",
                R.drawable.wat2watch_navbar_search_icon to "Search Icon",
                R.drawable.wat2watch_navbar_add_icon to "Add Icon",
                R.drawable.wat2watch_navbar_history_icon to "History Icon",
                R.drawable.wat2watch_navbar_user_icon to "User Icon"
            ).forEach { (icon, description) ->
                val isSelected = selectedIcon.value == description
                val backgroundColor = if (isSelected) Color(0xffc9dbef) else Color.White
                val iconSize = if (description == "Add Icon") 36.dp else 24.dp
                Box(
                    modifier = Modifier
                        .requiredWidth(48.dp)
                        .requiredHeight(42.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(backgroundColor)
                        .clickable { selectedIcon.value = description }, // Update selection on click
                    contentAlignment = Alignment.Center // Centers icon inside Box
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = description,
                        modifier = Modifier
                            .requiredWidth(iconSize)
                            .requiredHeight(iconSize)
                    )
                }
            }
        }
}

@Preview(widthDp = 412, heightDp = 74)
@Composable
private fun NavBarPreview() {
    NavBar(Modifier)
}