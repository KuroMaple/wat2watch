package com.team1.wat2watch.ui.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.google.firebase.auth.FirebaseAuth
import com.team1.wat2watch.R
import com.team1.wat2watch.ui.login.LoginViewModel

@JvmOverloads
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController,
    loginViewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val signOutEvent by viewModel.signOutEvent.observeAsState(false)
    val username = viewModel.username.observeAsState("User").value
    val creationDate = viewModel.creationDate.observeAsState("12/24").value
    val scrollState = rememberScrollState()

    LaunchedEffect(signOutEvent) {
        if (signOutEvent == true) {
            Log.d("ProfileScreen", "Sign out event detected, navigating to login")

            // Force logout again to be sure
            FirebaseAuth.getInstance().signOut()

            // Reset login state
            loginViewModel.resetLoginState()

            // Navigate to login with a very clear navigation instruction
            navController.navigate("login") {
                // Clear all back stack entries
                popUpTo(0) { inclusive = true }
            }

            // Reset the sign out event after navigation
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
                // Placeholder movie posters - replace with actual data
                repeat(3) {
                    Card(
                        modifier = Modifier
                            .width(80.dp)
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Movie ${it+1}")
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .width(80.dp)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("See all →")
                    }
                }
            }

            fun signOut() {
                // Your existing sign out code (like FirebaseAuth.getInstance().signOut())
                loginViewModel.resetLoginState() // Reset the login state to ensure loginSuccess is false
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            }

            Button(
                onClick = {
                    Log.d("ProfileScreen", "Sign out button clicked")

                    // Direct approach to sign out and navigate
                    FirebaseAuth.getInstance().signOut()
                    loginViewModel.resetLoginState()

                    // Navigate immediately
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }

                    // Also trigger the view model's sign out for completeness
                    viewModel.signOut()
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
