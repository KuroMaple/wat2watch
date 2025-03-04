package com.team1.wat2watch.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team1.wat2watch.R
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

val nunitoSansFont = FontFamily(
    Font(R.font.nunito_sans_7pt_condensed_medium, FontWeight.Normal), // Regular weight
    Font(R.font.nunito_sans_7pt_black, FontWeight.Bold) // Bold weight
)

val rainyHeartsFont = FontFamily(
    Font(R.font.rainyhearts, FontWeight.Normal)
)

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel 
) {
    val loginError by  loginViewModel.loginError.observeAsState() // Observe the error state
    Box(
        modifier = Modifier
            .requiredWidth(width = 412.dp)
            .requiredHeight(height = 917.dp)
            .background(color = Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_background_image),
            contentDescription = "login background image",

            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 0.dp, y = (0).dp)
                .requiredWidth(width = 412.dp)
                .height(250.dp)

        )
        Text(
            text = "reel fun movie nights with friends",
            color = Color.Black,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = nunitoSansFont
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 29.dp,
                    y = 120.dp
                ))
        Text(
            text = buildAnnotatedString {
                append("New to Wat2Watch? ")
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append("Create an account")
                }
            },
            color = Color.Black,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = nunitoSansFont
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .offset(
                    x = 3.dp,
                    y = 856.dp
                ))
        Text(
            text = "Forgot password?",
            color = Color(0xff757575),
            textDecoration = TextDecoration.Underline,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = nunitoSansFont
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 76.dp,
                    y = 421.dp
                ))
        Text(
            text = "Email",
            color = Color.Black,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = nunitoSansFont
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 76.dp,
                    y = 293.dp
                ))
        EmailInput( loginViewModel)
        Text(
            text = "Password",
            color = Color.Black,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = nunitoSansFont
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 76.dp,
                    y = 360.dp
                ))
        Text(
            text = "Wat2Watch",
            color = Color.Black,
            style = TextStyle(
                fontSize = 64.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = rainyHeartsFont
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 29.dp,
                    y = 54.dp
                )
        )
        SignInButton( loginViewModel, navController)
        Text(
            text = "Sign In",
            color = Color.Black,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = nunitoSansFont
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 167.dp,
                    y = 236.dp
                ))
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 210.5.dp,
                    y = 642.dp
                )
                .requiredWidth(width = 202.dp)
                .requiredHeight(height = 206.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.wat2watch_logo),
                contentDescription = "wat2watch Logo",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Fit
            )

        }



        PasswordInput( loginViewModel)
        Box(
            modifier = Modifier
                .offset(x = 65.dp, y = 564.dp) // Positioning
                .width(282.dp)
                .height(35.dp)
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 6.dp), // Add padding for better layout
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google_sign_in_logo),
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(20.dp) // Keep the logo small
                )

                Spacer(modifier = Modifier.width(8.dp)) // Space between logo & text

                Text(
                    text = "Sign in with Google",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = nunitoSansFont
                    )
                )
            }
        }
        Text(
            text = "or",
            color = Color(0xffa5a5a5),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = nunitoSansFont
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 183.dp,
                    y = 526.dp
                )
                .requiredWidth(width = 45.dp)
                .requiredHeight(height = 22.dp))

        loginError?.let {
            Text(
                text = it,
                color = Color.Red,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = nunitoSansFont
                ),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 76.dp, y = 460.dp) // Position it below the password field
            )
        }
    }
}

@Preview(widthDp = 412, heightDp = 917)
@Composable
private fun LoginScreenPreview() {
    // Create a temporary NavController for preview
    val navController = rememberNavController()

    LoginScreen(navController, LoginViewModel())
}

@Composable
fun EmailInput( loginViewModel: LoginViewModel){
    val email by  loginViewModel.email.observeAsState("") // Default to empty string
    BasicTextField(
        value = email,
        onValueChange = {  loginViewModel.setEmail(it) },
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = nunitoSansFont,
        ),
        modifier = Modifier
            .offset(x = 76.dp, y = 316.dp)
            .background(Color(0xfff5f5f5), RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .requiredWidth(260.dp)
            .requiredHeight(36.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp),
    )
}

@Composable
fun PasswordInput( loginViewModel: LoginViewModel){
    val password by  loginViewModel.password.observeAsState("") // Default to empty string
    BasicTextField(
        value = password,
        onValueChange = {  loginViewModel.setPassword(it) },
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = nunitoSansFont,
        ),
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier
            .offset(x = 76.dp, y = 384.dp)
            .background(Color(0xfff5f5f5), RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .requiredWidth(260.dp)
            .requiredHeight(36.dp)
            .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
        ,

    )
}

@Composable
fun SignInButton(loginViewModel: LoginViewModel, navController: NavController){
    val loginSuccess by loginViewModel.loginSuccess.observeAsState(false)

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.navigate("home")
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()  // Ensure the Box takes up the full screen or desired space
    ) {
        InputChip(
            label = {
                Text(
                    text = "Sign in",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = nunitoSansFont
                    )
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = InputChipDefaults.inputChipColors(
                containerColor = Color(0xFFC9DBEF),
                selectedContainerColor = Color(0xFFC9DBEF),
                disabledContainerColor = Color(0xFFC9DBEF)
            ),
            selected = true,
            onClick = {
                loginViewModel.login()
            },
            modifier = Modifier
                .align(Alignment.TopStart)  // Now works because the parent is a Box
                .offset(x = 170.dp, y = 478.dp)
        )
    }
}