package com.team1.wat2watch.ui.history

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.team1.wat2watch.R
import com.team1.wat2watch.ui.login.LoginViewModel
import com.team1.wat2watch.ui.navbar.NavBar

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = LoginViewModel(),
    navController: NavController
) {
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
            item { HistoryItem("Caitlin & Amena", "The Wizard of Oz", "Today\n11:38 am", R.drawable.image17) }
            item { HistoryItem("Caitlin, Asma, & Jagan", "Interstellar", "Yesterday\n10:56 pm", R.drawable.image18) }
            item { HistoryItem("Raphael, Asma, Caitlin, Hassan, Amena, Bob, Joe, & Jagan", "Toy Story 4", "01/02/25\n2:36 pm", R.drawable.image19) }
        }

        Spacer(modifier = Modifier.height(8.dp))
        NavBar(navController = navController)
    }
}

@Composable
fun HistoryItem(names: String, movie: String, timestamp: String, imageRes: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = movie,
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
    val navController = rememberNavController()
    HistoryScreen(navController = navController)
}
