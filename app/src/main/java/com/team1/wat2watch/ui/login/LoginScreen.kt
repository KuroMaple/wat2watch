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

val nunitoSansFont = FontFamily(
    Font(R.font.nunito_sans_7pt_condensed_medium, FontWeight.Normal), // Regular weight
    Font(R.font.nunito_sans_7pt_black, FontWeight.Bold) // Bold weight
)

val rainyHeartsFont = FontFamily(
    Font(R.font.rainyhearts, FontWeight.Normal)
)

@Preview(showBackground = true)
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
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
                fontFamily = nunitoSansFont),
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
                fontFamily = nunitoSansFont),
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
                fontFamily = nunitoSansFont),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 76.dp,
                    y = 293.dp
                ))
        Text(
            text = "Password",
            color = Color.Black,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = nunitoSansFont),
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
        InputChip(
            label = {
                Text(
                    text = "Sign in",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = nunitoSansFont))
            },
            shape = RoundedCornerShape(8.dp),
            colors = InputChipDefaults.inputChipColors(
                containerColor = Color(0xFFC9DBEF),
                selectedContainerColor = Color(0xFFC9DBEF),
                disabledContainerColor = Color(0xFFC9DBEF)
            ),
            selected = true,
            onClick = { },
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 170.dp,
                    y = 478.dp
                ))
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

//            Box(
//                modifier = Modifier
//                    .align(alignment = Alignment.TopStart)
//                    .offset(x = 26.5.dp,
//                        y = 41.052734375.dp)
//                    .requiredWidth(width = 99.dp)
//                    .requiredHeight(height = 79.dp)
//                    .clip(shape = RoundedCornerShape(24.dp))
//                    .background(color = Color(0xffbfbfbf)))
//            Image(
//                painter = painterResource(id = R.drawable.vector1),
//                contentDescription = "Vector 1",
//                modifier = Modifier
//                    .align(alignment = Alignment.TopStart)
//                    .offset(x = 40.394775390625.dp,
//                        y = 0.dp)
//                    .requiredWidth(width = 62.dp)
//                    .requiredHeight(height = 50.dp)
//                    .border(border = BorderStroke(1.dp, Color.Black)))
//            Box(
//                modifier = Modifier
//                    .align(alignment = Alignment.TopStart)
//                    .offset(x = 38.5.dp,
//                        y = 53.052734375.dp)
//                    .requiredWidth(width = 76.dp)
//                    .requiredHeight(height = 56.dp)
//                    .clip(shape = RoundedCornerShape(10.dp))
//                    .background(color = Color(0xffc9dbef)))
//            Box(
//                modifier = Modifier
//                    .align(alignment = Alignment.TopStart)
//                    .offset(x = 55.5526123046875.dp,
//                        y = 83.36865234375.dp)
//                    .requiredSize(size = 5.dp)
//                    .clip(shape = CircleShape)
//                    .background(color = Color.Black))
//            Box(
//                modifier = Modifier
//                    .align(alignment = Alignment.TopStart)
//                    .offset(x = 97.23681640625.dp,
//                        y = 83.36865234375.dp)
//                    .requiredSize(size = 5.dp)
//                    .clip(shape = CircleShape)
//                    .background(color = Color.Black))
//            Box(
//                modifier = Modifier
//                    .align(alignment = Alignment.TopStart)
//                    .offset(x = 76.5.dp,
//                        y = 81.dp)
//                    .requiredWidth(width = 8.dp)
//                    .requiredHeight(height = 16.dp)
//                    .background(color = Color.Black))
//            Image(
//                painter = painterResource(id = R.drawable.vector5),
//                contentDescription = "Vector 5",
//                modifier = Modifier
//                    .align(alignment = Alignment.TopStart)
//                    .offset(x = 0.dp,
//                        y = 136.5.dp)
//                    .requiredWidth(width = 98.dp)
//                    .requiredHeight(height = 30.dp)
//                    .border(border = BorderStroke(4.dp, Color.Black)))
        }
        OutlinedTextField(
            value = "",
            onValueChange = {},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xfff5f5f5),
                unfocusedContainerColor = Color(0xfff5f5f5)
            ),
            shape = RoundedCornerShape(8.dp), // Proper rounded corners
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 76.dp, y = 316.dp)
                .requiredWidth(width = 260.dp)
                .requiredHeight(height = 36.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xfff5f5f5),
                unfocusedContainerColor = Color(0xfff5f5f5)
            ),
            shape = RoundedCornerShape(8.dp), // Proper rounded corners
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 76.dp, y = 384.dp)
                .requiredWidth(width = 260.dp)
                .requiredHeight(height = 36.dp)
                )
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
    }
}

@Preview(widthDp = 412, heightDp = 917)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(Modifier)
}