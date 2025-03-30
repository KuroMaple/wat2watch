package com.team1.wat2watch.ui.history

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.team1.wat2watch.R
import com.team1.wat2watch.ui.login.LoginViewModel
import com.team1.wat2watch.ui.navbar.NavBar

@Composable
fun HistoryScreen(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel = remember { HistoryScreenViewModel() }
    val historyItems by viewModel.historyItems.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUserMatchHistory("userId")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopNavigationNoBackArrow()

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(historyItems) { history ->
                HistoryItem(
                    names = history.names,
                    movie = history.movie,
                    timestamp = history.timestamp,
                    imageUrl = history.imageUrl
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun HistoryItem(names: String, movie: String, timestamp: String, imageUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = movie,
            placeholder = painterResource(R.drawable.image18),
            error = painterResource(R.drawable.image17),
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = names,
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = movie,
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge
            )
        }

        Text(
            text = timestamp,
            color = Color(0xffa5a5a5),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun TopNavigationNoBackArrow(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(73.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Past Matches",
            color = Color.Black,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryPreview() {
    HistoryScreen(navController = NavController(LocalContext.current))
}