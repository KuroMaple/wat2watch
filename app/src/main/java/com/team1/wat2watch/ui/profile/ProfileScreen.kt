package com.team1.wat2watch.ui.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.team1.wat2watch.R
import com.team1.wat2watch.ui.login.LoginViewModel
import utils.Movie

@JvmOverloads
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val signOutEvent by viewModel.signOutEvent.observeAsState(false)
    val username = viewModel.username.observeAsState("User").value
    val creationDate = viewModel.creationDate.observeAsState("12/24").value
    val scrollState = rememberScrollState()

    val watchedMovies by viewModel.watchedMovies.collectAsState(emptyList())

    // Handle sign out navigation
    LaunchedEffect(signOutEvent) {
        if (signOutEvent) {
            Log.d("ProfileScreen", "Sign out event detected, navigating to login")
            // Navigate to login with clear backstack
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
            // Reset the sign out event
            viewModel._signOutEvent.postValue(false)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 40.dp)
    ) {
        Column(
            modifier = modifier
                .weight(1f)
                .padding(horizontal = 28.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "Profile",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_black)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                )
            )

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F0FF))
            ) {
                Column(
                    modifier = modifier
                        .padding(vertical = 36.dp, horizontal = 64.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_pic_placeholder),
                        contentDescription = "Profile Picture",
                        modifier = modifier.size(109.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = modifier.height(16.dp))

                    Text(
                        text = username, // Using dynamic username from FireStore
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_condensed_light)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFF000000),
                        )
                    )

                    Text(
                        text = "Wat2Watcher since $creationDate",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_condensed_light)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(top = 10.dp)
            ) {
                // TODO: ADD SETTINGS ICON HERE (wrong icon used currently)
                Image(
                    painter = painterResource(id = R.drawable.wat2watch_navbar_search_icon),
                    contentDescription = "Settings",
                    modifier = modifier.size(24.dp)
                )
                Spacer(modifier = modifier.width(10.dp))
                Text(
                    text = "Settings",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_condensed_light)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                    )
                )
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
                thickness = 1.dp,
                color = Color(0xFFE0E0E0) // Light grey color
            )

            Text(
                text = "Account Information",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_condensed_light)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000)
                ),
                modifier = modifier.padding(top = 24.dp, bottom = 8.dp)
            )

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Account type: Standard",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_condensed_light)),
                            fontWeight = FontWeight(400)
                        )
                    )
                }
            }

            Text(
                text = "My Watchlist Preview",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_condensed_light)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000)
                ),
                modifier = modifier.padding(top = 24.dp, bottom = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Show up to 3 movies from watchlist
                watchedMovies.take(3).forEach { movie ->
                    AsyncImage(
                        model = movie.getFullImageUrl(),
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(80.dp)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(8.dp))
                    )
                }

                // "See all" button that navigates to watchlist
                Card(
                    modifier = Modifier
                        .width(80.dp)
                        .fillMaxHeight()
                        .clickable { navController.navigate("search") },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "See all →",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_condensed_light))
                            )
                        )
                    }
                }
            }

            Button(
                onClick = {
                    Log.d("ProfileScreen", "Sign out button clicked")
                    loginViewModel.resetLoginState()
                    viewModel.signOut() // This will trigger the signOutEvent
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC9DBEF))
            ) {
                Text(
                    text = "Sign Out",
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_condensed_light)),
                        fontWeight = FontWeight(600)
                    )
                )
            }
        }
    }
}
