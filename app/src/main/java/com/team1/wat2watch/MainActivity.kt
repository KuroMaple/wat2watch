package com.team1.wat2watch

import com.team1.wat2watch.ui.login.LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.team1.wat2watch.ui.history.HistoryScreen
import com.team1.wat2watch.ui.home.HomeScreen
import com.team1.wat2watch.ui.login.LoginViewModel
import com.team1.wat2watch.ui.match.MatchScreen
import com.team1.wat2watch.ui.navbar.NavBar
import com.team1.wat2watch.ui.navbar.NavBarViewModel
import com.team1.wat2watch.ui.signup.SignUpScreen
import com.team1.wat2watch.ui.profile.ProfileScreen



class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("323875359522-gq59u1q98bhom7p4nqc4843h9aoarm7a.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        viewModel.initGoogleSignInClient(googleSignInClient)

        val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            viewModel.handleGoogleSignInResult(task)
        }

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "splash") {
                composable("splash") { SplashScreen(navController) }
                composable("login") {
                    LoginScreen(
                        navController = navController,
                        viewModel = viewModel,
                        onGoogleSignInClicked = {
                            val signInIntent = googleSignInClient.signInIntent
                            googleSignInLauncher.launch(signInIntent)
                        }
                    )
                }
                composable("home") { HomeScreen(navController = navController) }
                composable("history") { HistoryScreen(navController = navController) }
                composable("signup") { SignUpScreen(navController = navController) }
            }
        }
    }
}

@Composable
fun MyApp(viewModel: LoginViewModel, kFunction0: () -> Unit) {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel() // ViewModel for login

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            val isLoggedIn = loginViewModel.loginSuccess.observeAsState(false).value
            Scaffold(
                bottomBar = {
                    // Track navigation changes properly
                    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
                    val currentRoute = currentBackStackEntry?.destination?.route
                    if (isLoggedIn && currentRoute != "match") {
                        NavBar(viewModel = NavBarViewModel(navController)) // Show NavBar
                    }
                }
            ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "splash",
                modifier = Modifier.padding(innerPadding)) {
                composable("splash") { SplashScreen(navController) }
                composable("login") {
                    LoginScreen(
                    modifier = Modifier,
                    viewModel = viewModel,
                    navController = navController,
                    onGoogleSignInClicked = {})
                }
                composable("home") { HomeScreen(navController = navController) }
                composable("history") { HistoryScreen() }
                composable("signup") { SignUpScreen(navController = navController) }
                composable("profile") { ProfileScreen(navController = navController) }
                composable("match") { MatchScreen(navController) }
            }
            }
        }
    }
}
