package com.team1.wat2watch.ui.match

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.team1.wat2watch.R

@Composable
fun MatchScreen(navController: NavController) {
    val modifier = Modifier
    Box(
        modifier = modifier
            .fillMaxSize() // Use fillMaxSize instead of fixed width/height
            .background(Color.White)
    ) {
        TopNavigationBar(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 1.dp),
            navController
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f) // Adjust width as a percentage of the parent
                .fillMaxHeight(0.7f) // Adjust height as needed
                .background(color = Color(0xffc9dbef))
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.image16),
                contentDescription = "image 16",
                modifier = Modifier
                    .fillMaxWidth(0.75f) // Maintain aspect ratio and adjust width
                    .height(445.dp)
            )

            Column(
                horizontalAlignment = Alignment.Start // Align inner column's children to the left
            ) {
                Text(
                    text = "Spirited Away",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 25.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically // Align text vertically in the center
                ) {
                    Text(
                        text = "Fantasy/Adventure",
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
                        text = "PG",
                        color = Color.Black,
                        style = TextStyle(fontSize = 16.sp)
                    )
                }

                Text(
                    text = "Chihiro wanders into a magical world where a witch rules -- and those who disobey her are turned into animals.",
                    color = Color.Black,
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier
                        .padding(start = 25.dp, top = 16.dp)
                        .height(88.dp)
                        .fillMaxWidth(0.9f)
                )
            }
        }



        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 32.dp, top = 750.dp)
                .width(104.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xffd9d9d9)),
        ) {
            Image(
                painter = painterResource(id = R.drawable.image24),
                contentDescription = "image 24",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 10.dp)
                    .requiredSize(20.dp)
            )

            Text(
                text = "Undo",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier.align(Alignment.Center).padding(start = 10.dp)
            )
        }


        Box (
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 32.dp, top = 750.dp)
                .width(204.dp)
                .height(40.dp)
        )
        {
            Image(
                painter = painterResource(id = R.drawable.info_icon),
                contentDescription = "info icon",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(end = 10.dp)
                    .requiredSize(20.dp)
            )
            Text(
                text = "How does this work?",
                color = Color.Black,
                textAlign = TextAlign.End,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(22.dp)
            )
        }

    }
}

@Composable
fun TopNavigationBar(modifier: Modifier = Modifier, navController: NavController) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(53.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.topnav_arrow),
            contentDescription = "Back Arrow",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp, end = 16.dp)
                .clickable{
                    navController.navigate("home")
                },
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
        )

        Text(
            text = "Caitlin’s Match Party",
            color = Color.Black,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 16.dp)
        )
    }
}


@Preview(showBackground = true, widthDp = 412, heightDp = 840)
@Composable
fun MatchScreenPreview() {
    val context = LocalContext.current
    val fakeNavController = remember { NavController(context) }
    MatchScreen(fakeNavController)
}


