package com.team1.wat2watch.ui.watchlist

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.team1.wat2watch.R
import com.team1.wat2watch.ui.home.HomeViewModel
import com.team1.wat2watch.ui.login.LoginViewModel
import com.team1.wat2watch.ui.watchlist.WatchlistViewModel
import com.team1.wat2watch.ui.navbar.NavBar

@Composable
fun WatchlistScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel = WatchlistViewModel()
    var movies by remember { mutableStateOf(viewModel.movies.value) }
    var selectedSort by remember { mutableStateOf("A-Z") }
    var searchQuery by remember { mutableStateOf("") }

    fun updateMovies() {
        movies = viewModel.movies.value
            .filter { it.title.contains(searchQuery, ignoreCase = true) }
            .let {
                when (selectedSort) {
                    "A-Z" -> it.sortedBy { movie -> movie.title }
                    "Z-A" -> it.sortedByDescending { movie -> movie.title }
                    "Earliest - Latest" -> it.sortedBy { movie -> movie.year }
                    "Highest - Lowest Rating" -> it.sortedByDescending { movie -> movie.rating }
                    else -> it
                }
            }
    }

    LaunchedEffect(searchQuery, selectedSort) {
        updateMovies()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Watchlist",
            color = Color.Black,
            style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp, top = 8.dp)
        )

        Text(
            text = "All your favourite movies, all in one place.",
            color = Color.Black,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
            },
            placeholder = {
                Text(text = "Search for a movie...", color = Color.Gray)
            },
            trailingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon", tint = Color.Gray)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp)),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sort by:",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(end = 8.dp)
            )

            var expanded by remember { mutableStateOf(false) }

            Box {
                Button(onClick = { expanded = true }) {
                    Text(selectedSort)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    listOf("A-Z", "Z-A", "Earliest - Latest", "Highest - Lowest Rating").forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedSort = option
                                expanded = false
                                updateMovies()
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(movies) { movie ->
                MovieItem(movie = movie)
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    var isBookmarked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = movie.imageResId),
            contentDescription = movie.title,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = movie.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
            Text(
                text = "${movie.year} • ${movie.genre}",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = { isBookmarked = !isBookmarked }
        ) {
            Icon(
                imageVector = if (isBookmarked) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                contentDescription = "Bookmark Icon",
                tint = if (isBookmarked) Color.Red else Color.Gray
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun WatchlistPreview() {
    val context = LocalContext.current
    val fakeNavController = remember { NavController(context) }
    WatchlistScreen(fakeNavController)
}
