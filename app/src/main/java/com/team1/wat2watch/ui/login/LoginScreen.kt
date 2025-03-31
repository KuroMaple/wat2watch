package com.team1.wat2watch.ui.login

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    navController: NavController,
    onGoogleSignInClicked: () -> Unit
) {
    val loginSuccess by viewModel.loginSuccess.observeAsState(initial = false)
    val loginError by viewModel.loginError.observeAsState() // Observe the error state
    val forgotPasswordSuccess by viewModel.forgotPasswordSuccess.observeAsState()

    val showMessage = loginError != null || forgotPasswordSuccess == true
    val additionalOffset = if (showMessage) 40.dp else 0.dp

    LaunchedEffect(loginSuccess) {
        Log.d("loginSuccess:", loginSuccess.toString())
        if (loginSuccess) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_background_image),
            contentDescription = "login background image",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 0.dp, y = (0).dp)
                .fillMaxWidth()
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
                .offset(x = 3.dp, y = 856.dp)
                .clickable { navController.navigate("signup") },
        )
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
                    y = 425.dp
                )
                .clickable { viewModel.onForgotPasswordClick() }
        )
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
        EmailInput(viewModel)
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
        Text(
            text = "Sign In",
            color = Color.Black,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = nunitoSansFont
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .offset(
                    y = 236.dp
                ))
        Box(
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .offset(x = 9.dp, y = (-45).dp)
                .size(202.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.wat2watch_logo),
                contentDescription = "wat2watch Logo",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
        PasswordInput(viewModel)
        // Error and success messages
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 460.dp)
                .width(260.dp)
        ) {
            when {
                loginError != null -> {
                    Text(
                        text = loginError!!,
                        color = Color.Red,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = nunitoSansFont
                        ),
                        softWrap = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                forgotPasswordSuccess == true -> {
                    Text(
                        text = "Password reset email sent. Please check your inbox.",
                        color = Color.Green,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = nunitoSansFont
                        ),
                        softWrap = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        SignInButton(
            viewModel,
            navController,
            Modifier.offset(y = 465.dp + additionalOffset)
        )

        Text(
            text = "or",
            color = Color(0xffa5a5a5),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = nunitoSansFont
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .offset(y = 523.dp + additionalOffset)
                .requiredWidth(width = 45.dp)
                .requiredHeight(height = 22.dp)
        )

        GoogleSignInButton(
            modifier = Modifier.offset(y = 564.dp + additionalOffset),
            onClick = onGoogleSignInClicked
        )
    }
}

@Preview(widthDp = 412, heightDp = 917)
@Composable
private fun LoginScreenPreview() {
    val navController = rememberNavController()
    val viewModel = LoginViewModel()
    LoginScreen(
        modifier = Modifier,
        viewModel = viewModel,
        navController = navController,
        onGoogleSignInClicked = {}
    )
}

@Composable
fun EmailInput(viewModel: LoginViewModel){
    val email by viewModel.email.observeAsState("") // Default to empty string
    BasicTextField(
        value = email,
        onValueChange = { viewModel.setEmail(it) },
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = nunitoSansFont,
        ),
        modifier = Modifier
            .offset(x = 76.dp, y = 316.dp)
            .background(Color(0xfff5f5f5), RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .width(260.dp)
            .height(36.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp),
    )
}

@Composable
fun PasswordInput(viewModel: LoginViewModel){
    val password by viewModel.password.observeAsState("") // Default to empty string
    BasicTextField(
        value = password,
        onValueChange = { viewModel.setPassword(it) },
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
            .width(260.dp)
            .height(36.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp),
    )
}

@Composable
fun SignInButton(viewModel: LoginViewModel, navController: NavController, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize()
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
                viewModel.login()
            },
            modifier = modifier.then(Modifier.align(Alignment.TopCenter))
        )
    }
}

@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .then(
                Modifier
                    .offset(x = 65.dp)
                    .width(282.dp)
                    .height(35.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(8.dp))
                    .clickable(onClick = onClick)
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_sign_in_logo),
                contentDescription = "Google Logo",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
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
}
