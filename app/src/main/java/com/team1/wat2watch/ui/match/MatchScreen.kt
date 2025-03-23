package com.team1.wat2watch.ui.match

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.team1.wat2watch.BuildConfig
import com.team1.wat2watch.R
import com.team1.wat2watch.ui.SwipeExample.SwipeableCard


@Composable
fun MatchScreen(navController: NavController,
                viewModel: MatchViewModel = MatchViewModel()) {
    val movies = viewModel.movies.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.fetchMovies(
            apiKey = BuildConfig.API_KEY
        )
    }

    Scaffold (
        topBar = {
            TopNavigationBar(navController = navController)
        },
        bottomBar = {
            BottomNavigationBar(onUndoClick = {viewModel.triggerUndo()})
        }
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            SwipeableCard(
                dataSource = movies,
                matchViewModel = viewModel
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

@Composable
fun BottomNavigationBar(
    onUndoClick: () -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(53.dp)
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ){
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
                .width(104.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xffd9d9d9))
                .clickable(onClick = onUndoClick),
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
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 10.dp)

            )
        }

        Spacer(modifier = Modifier.width(40.dp))

        Box (
            modifier = Modifier
                .align(Alignment.CenterVertically)
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


@Preview(showBackground = true, widthDp = 412, heightDp = 840)
@Composable
fun MatchScreenPreview() {
    val context = LocalContext.current
    val fakeNavController = remember { NavController(context) }
    MatchScreen(fakeNavController)
}


