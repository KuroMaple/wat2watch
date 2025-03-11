package com.team1.wat2watch.ui.match.card


import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.team1.wat2watch.data.model.Movie
import com.team1.wat2watch.ui.SwipeExample.Constants.cornerRadiusBig
import com.team1.wat2watch.ui.SwipeExample.Constants.normalElevation

@Composable
fun MovieCard(
    movie: Movie,
    cardIndex: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadiusBig),
        elevation = CardDefaults.cardElevation(defaultElevation = normalElevation),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xffc9dbef))
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            println(movie.getFullImageUrl())
            AsyncImage(
                model = movie.getFullImageUrl(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.75f)  // Adjust width to 75% of the parent
                    .height(300.dp)  // Set a fixed height
                    .padding(16.dp)  // Add padding around the image
                    .clip(RoundedCornerShape(12.dp))
            )

            Column(
                horizontalAlignment = Alignment.Start // Align inner column's children to the left
            ) {
                Text(
                    text = movie.title,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 25.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(start = 25.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically // Align text vertically in the center
                ) {
                    Text(
                        text = "Fantasy/Adventure", // TODO: Replace with dynamic genre
                        color = Color.Black,
                        style = TextStyle(fontSize = 16.sp)
                    )
                    Box(
                        modifier = Modifier
                            .requiredSize(6.dp)
                            .background(Color.Black, shape = CircleShape)
                            .padding(horizontal = 4.dp)
                    )
                    Text(
                        text = "2h 5m",
                        color = Color.Black,
                        style = TextStyle(fontSize = 16.sp)
                    )
                    Box(
                        modifier = Modifier
                            .requiredSize(6.dp)
                            .background(Color.Black, shape = CircleShape)
                            .padding(horizontal = 4.dp) // Add horizontal spacing between the dot and text
                    )
                    Text(
                        text = if (movie.adult) "R" else "PG",
                        color = Color.Black,
                        style = TextStyle(fontSize = 16.sp)
                    )
                }

                Text(
                    text = movie.overview,
                    color = Color.Black,
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier
                        .padding(start = 25.dp, top = 16.dp)
                        .height(88.dp)
                        .fillMaxWidth(0.9f)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MovieCardPreview() {
    // Sample movie data
    val sampleMovie = Movie(
        title = "Spirited Away",
        overview = "Chihiro wanders into a magical world where a witch rules -- and those who disobey her are turned into animals.",
        poster_path = "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg?20200913095930",
        adult = false,
    )

    // Preview of the DraggableMovieCard with sample movie data
    MovieCard(
        movie = sampleMovie,
        cardIndex = 0,
    )
}

