package com.team1.wat2watch

import com.team1.wat2watch.ui.login.LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.team1.wat2watch.ui.history.HistoryScreen
import com.team1.wat2watch.ui.home.HomeScreen
import com.team1.wat2watch.ui.signup.SignUpScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            NavHost(navController = navController, startDestination = "splash") {
                composable("splash") { SplashScreen(navController) }
                composable("login") { LoginScreen(navController = navController) }
                composable("home") { HomeScreen(navController = navController) }
                composable("history") { HistoryScreen(navController = navController) }
                composable("signup") { SignUpScreen(navController = navController) }
            }
        }
    }
}
