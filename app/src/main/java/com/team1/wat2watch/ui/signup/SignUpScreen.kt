package com.team1.wat2watch.ui.signup

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.team1.wat2watch.R
import com.team1.wat2watch.ui.login.nunitoSansFont
import com.team1.wat2watch.ui.login.rainyHeartsFont

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = viewModel()
) {
    val username by viewModel.username.observeAsState("")
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val confirmPassword by viewModel.confirmPassword.observeAsState("")
    val signUpError by viewModel.signUpError.observeAsState()
    val navigationEvent by viewModel.navigationEvent.observeAsState()

    LaunchedEffect(navigationEvent) {
        Log.d("SignUpScreen", "Navigation event observed: $navigationEvent")
        when (navigationEvent) {
            "home" -> {
                Log.d("SignUpScreen", "Navigating to home")
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
                viewModel.onNavigationHandled()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_background_image),
            contentDescription = "signup background image",
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Wat2Watch",
            color = Color.Black,
            style = TextStyle(
                fontSize = 64.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = rainyHeartsFont
            ),
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 29.dp, y = 54.dp)
        )
        Text(
            text = "reel fun movie nights with friends",
            color = Color.Black,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = nunitoSansFont
            ),
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 29.dp, y = 120.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 76.dp)
                .align(Alignment.Center)
                .offset(y = (-20).dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Create an Account",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = nunitoSansFont
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            InputField(label = "Username", value = username, isPassword = false) {
                viewModel.setUsername(it)
            }
            Spacer(modifier = Modifier.height(16.dp))
            InputField(label = "Email", value = email, isPassword = false) {
                viewModel.setEmail(it)
            }
            Spacer(modifier = Modifier.height(16.dp))
            InputField(label = "Password", value = password, isPassword = true) {
                viewModel.setPassword(it)
            }
            Spacer(modifier = Modifier.height(16.dp))
            InputField(label = "Confirm Password", value = confirmPassword, isPassword = true) {
                viewModel.setConfirmPassword(it)
            }
            Spacer(modifier = Modifier.height(24.dp))
            CreateAccountButton(viewModel)
            signUpError?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = nunitoSansFont
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 9.dp, y = (-45).dp)
                .size(202.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.wat2watch_logo),
                contentDescription = "wat2watch Logo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        Text(
            text = buildAnnotatedString {
                append("Already have an account? ")
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append("Sign in here")
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
                .clickable { navController.navigate("login") }
        )
    }
}

@Composable
fun InputField(label: String, value: String, isPassword: Boolean, onValueChange: (String) -> Unit) {
    Column {
        Text(
            text = label,
            color = Color.Black,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = nunitoSansFont
            ),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = nunitoSansFont
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier
                .background(Color(0xfff5f5f5), RoundedCornerShape(8.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .width(260.dp)
                .height(36.dp)
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun CreateAccountButton(viewModel: SignUpViewModel) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        InputChip(
            label = {
                Text(
                    text = "Create Account",
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
                Log.d("SignUpScreen", "Create Account button clicked")
                viewModel.signUp()
            }
        )
    }
}
