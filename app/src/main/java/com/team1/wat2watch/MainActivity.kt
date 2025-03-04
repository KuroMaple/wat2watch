package com.team1.wat2watch

import com.team1.wat2watch.ui.login.LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.team1.wat2watch.ui.history.HistoryScreen
import com.team1.wat2watch.ui.home.HomeScreen
import com.team1.wat2watch.ui.navbar.NavBar
import com.team1.wat2watch.ui.navbar.NavBarViewModel
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.team1.wat2watch.ui.login.LoginViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                false
            }
        }
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel() // ViewModel for login

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            // Check the login success state and show bottom bar if true
            val isLoggedIn = loginViewModel.loginSuccess.observeAsState(false).value

            Scaffold(
                bottomBar = {
                    if (isLoggedIn) {
                        NavBar(viewModel = NavBarViewModel(navController)) // Show NavBar when logged in
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "splash",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("splash") {
                        SplashScreen(navController) // Show splash screen first
                    }
                    composable("login") {
                        LoginScreen(navController, loginViewModel) // Login screen
                    }
                    composable("home") {
                        HomeScreen() // Show home screen when logged in
                    }
                    composable("history") {
                        HistoryScreen() // Show history screen when logged in
                    }
                }
            }
        }
    }
}



