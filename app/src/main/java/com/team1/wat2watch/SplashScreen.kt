package com.team1.wat2watch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

val nunitoSansFont = FontFamily(
    Font(R.font.nunito_sans_7pt_condensed_medium, FontWeight.Normal), // Regular weight
    Font(R.font.nunito_sans_7pt_black, FontWeight.Bold) // Bold weight
)

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        delay(2000)  // Show splash screen for 2 seconds
        // Navigate to the Login screen (you can use an Intent or a state)
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }  // Remove splash screen from backstack
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.wat2watch_splash),
                contentDescription = "App Logo",
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp)) // Add spacing if needed
            Text(
                text = "Wat2Watch",
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontFamily = nunitoSansFont,
                fontSize = 32.sp,
            )
        }
    }
}

@Preview(widthDp = 412, heightDp = 917)
@Composable
private fun SplashScreenPreview() {
    // Create a temporary NavController for preview
    val navController = rememberNavController()

    // Call SplashScreen with the mocked NavController
    SplashScreen(navController = navController)
}
