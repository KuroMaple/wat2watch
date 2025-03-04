package com.team1.wat2watch.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team1.wat2watch.R



val nunitoSansFont = FontFamily(
    Font(R.font.nunito_sans_7pt_condensed_medium, FontWeight.Normal), // Regular weight
    Font(R.font.nunito_sans_7pt_black, FontWeight.Bold) // Bold weight
)

val rainyHeartsFont = FontFamily(
    Font(R.font.rainyhearts, FontWeight.Normal)
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = HomeViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween // Ensures navbar stays at the bottom
    ) {
        // Main Content
        Box(
            modifier = modifier
                .weight(1f)
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
            Column(
                modifier = Modifier
                    .offset(x = 20.dp, y = (40).dp),
            ) {
                Text(
                    text = "Good evening,",
                    color = Color.Black,
                    style = TextStyle(
                        fontFamily = nunitoSansFont,
                        fontSize = 18.sp,
                    ),
                    modifier = modifier)
                Text(
                    text = "Caitlin!",
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Medium),
                    fontFamily = rainyHeartsFont,
                    modifier = modifier)
                Text(
                    text = "There’s no place like home <3",
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = nunitoSansFont),
                    modifier = modifier)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = 0.dp, y = (50).dp),
                horizontalAlignment = Alignment.CenterHorizontally, // Center content horizontally
                verticalArrangement = Arrangement.Center // Center content vertically
            )
            {
                Text(
                    text = "Have a code from a friend?",
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 18.sp),)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            color = Color.Black,
                            fontSize = 16.sp)
                        ) {append("Enter it here! It will look something like ")}
                        withStyle(style = SpanStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold)) {append("walrus-eats-pizza")}},
                    modifier = modifier
                        .requiredWidth(width = 246.dp))
                Spacer(modifier = Modifier.height(15.dp))
                CodeInput(viewModel)
                JoinPartyButton()
                Spacer(modifier = Modifier.height(45.dp))
                Text(
                    text = "OR",
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = nunitoSansFont
                    ))
                Spacer(modifier = Modifier.height(40.dp))
                Icon(
                    painter = painterResource(id = R.drawable.wat2watch_home_add_party_icon),
                    contentDescription = "Join Party Icon",
                    modifier = Modifier
                        .requiredWidth(103.dp)
                        .requiredHeight(103.dp),
                    tint = Color(0xFFC9DBEF)
                )
                Spacer(modifier = Modifier.height(30.dp))
                StartPartyButton()
            }


        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {

    HomeScreen(Modifier)
}

@Composable
fun CodeInput(viewModel: HomeViewModel){
    val code by viewModel.code.observeAsState("") // Default to empty string
    BasicTextField(
        value = code,
        onValueChange = { viewModel.setCode(it) },
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = com.team1.wat2watch.ui.login.nunitoSansFont,
        ),
        modifier = Modifier
            .background(Color(0xfff5f5f5), RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .requiredWidth(260.dp)
            .requiredHeight(36.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

@Composable
fun JoinPartyButton(/*Add Params here when behaviour defined*/){
    InputChip(
        label = {
            Text(
                text = "Join a party",
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
        onClick = {},
    )
}

@Composable
fun StartPartyButton(/*Add Params here when behaviour defined*/){
    InputChip(
        label = {
            Text(
                text = "Start a party",
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
        onClick = {},
    )
}
