package com.team1.wat2watch

import com.team1.wat2watch.ui.login.LoginScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.team1.wat2watch.ui.match.MatchViewModel
import com.team1.wat2watch.ui.navbar.NavBar
import com.team1.wat2watch.ui.navbar.NavBarViewModel
import com.team1.wat2watch.ui.signup.SignUpScreen
import com.team1.wat2watch.ui.profile.ProfileScreen
import com.team1.wat2watch.ui.watchlist.MovieDetailsScreen
import com.team1.wat2watch.ui.watchlist.WatchlistScreen
import com.team1.wat2watch.ui.watchlist.WatchlistViewModel


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
            MyApp(viewModel = viewModel, signInWithGoogle = {
                val signInIntent = googleSignInClient.signInIntent
                googleSignInLauncher.launch(signInIntent)
            },
                startDestination = "login")
        }
    }
}

@Composable
fun MyApp(viewModel: LoginViewModel, signInWithGoogle: () -> Unit, startDestination: String) {
    val navController = rememberNavController()
    val matchViewModel = MatchViewModel()

    // Track auth state changes
    var isLoggedIn by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser != null) }

    // This effect sets up a listener for auth state changes
    DisposableEffect(Unit) {
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val currentUser = firebaseAuth.currentUser
            val newLoggedInState = currentUser != null

            // Only update if there's an actual change to prevent unnecessary recomposition
            if (isLoggedIn != newLoggedInState) {
                Log.d("AuthState", "Auth state changed - User logged in: $newLoggedInState")
                isLoggedIn = newLoggedInState
            }
        }

        FirebaseAuth.getInstance().addAuthStateListener(authStateListener)

        onDispose {
            FirebaseAuth.getInstance().removeAuthStateListener(authStateListener)
        }
    }

    // This effect handles navigation based on auth state
    LaunchedEffect(isLoggedIn) {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        Log.d("AuthNavigation", "Current route: $currentRoute, isLoggedIn: $isLoggedIn")

        if (!isLoggedIn && currentRoute != "login" && currentRoute != "signup" && currentRoute != null) {
            // If logged out and not on login/signup screens, navigate to login
            Log.d("AuthNavigation", "Navigating to login because user signed out")
            navController.navigate("login") {
                // Clear the entire back stack
                popUpTo(0) { inclusive = true }
            }
        } else if (isLoggedIn && currentRoute == "login") {
            // If logged in and on login screen, navigate to home
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            Scaffold(
                bottomBar = {
                    // Track navigation changes properly
                    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
                    val currentRoute = currentBackStackEntry?.destination?.route
                    Log.d("RouteCheck", "Current Route: $currentRoute")
                    if (currentRoute != "match" && currentRoute != "login" && currentRoute != "splash") {
                        NavBar(viewModel = NavBarViewModel(navController)) // Show NavBar
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("splash") { SplashScreen(navController) }
                    composable("login") {
                        LoginScreen(
                            modifier = Modifier,
                            viewModel = viewModel,
                            navController = navController,
                            onGoogleSignInClicked = signInWithGoogle) // Pass the function here
                    }
                    composable("home") { HomeScreen(navController = navController, matchViewModel = matchViewModel) }
                    composable("history") { HistoryScreen(navController = navController) }
                    composable("signup") { SignUpScreen(navController = navController) }
                    composable("profile") { ProfileScreen(navController = navController) }
                    composable("match") { MatchScreen(navController, matchViewModel) }
                    composable("search") { WatchlistScreen(navController = navController) }
                    composable("movieDetails/{movieId}") { backStackEntry ->
                        val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
                        val viewModel = WatchlistViewModel()
                        val movie = movieId?.let { viewModel.getMovieById(it) }

                        if (movie != null) {
                            MovieDetailsScreen(movie = movie, navController = navController)
                        } else {
                            Text(text = "Movie not found")
                        }
                    }
                }
            }
        }
    }
}
