package com.team1.wat2watch.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team1.wat2watch.ui.navbar.NavBar

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween // Ensures navbar stays at the bottom
    ) {
        // Main Content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Ensures content takes up remaining space
            contentAlignment = Alignment.Center
        ) {
            Text("Welcome to the Home Screen!")
        }

        // Bottom Navigation Bar
        NavBar()
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

