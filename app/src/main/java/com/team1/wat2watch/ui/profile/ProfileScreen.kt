package com.team1.wat2watch.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.team1.wat2watch.R
import com.team1.wat2watch.ui.login.LoginViewModel
import com.team1.wat2watch.ui.navbar.NavBar

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = LoginViewModel(),
    navController: NavController
) {
    var reWatchToggle by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {
        Column(
            modifier = modifier
                .weight(1f)
                .padding(horizontal = 28.dp)
        ) {
            Text(
                text = "Profile",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_black)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                )
            )

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F0FF))
            ) {
                Column(
                    modifier = modifier
                        .padding(vertical = 36.dp, horizontal = 64.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_pic_placeholder),
                        contentDescription = "Profile Picture",
                        modifier = modifier.size(109.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = modifier.height(16.dp))

                    Text(
                        text = "Caitlin",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_condensed_light)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFF000000),
                        )
                    )

                    Text(
                        text = "Wat2Watcher since 12/24",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_condensed_light)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000)
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = modifier.height(36.dp))

                    Text(
                        text = "45",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_condensed_light)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFF000000)
                        )
                    )

                    Text(
                        text = "Wat2Watch matches",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_condensed_light)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000)
                        ),
                        modifier = modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(top = 10.dp)
            ) {
                // TODO: ADD SETTINGS ICON HERE (wrong icon used currently)
                Image(
                    painter = painterResource(id = R.drawable.wat2watch_navbar_search_icon),
                    contentDescription = "Settings",
                    modifier = modifier.size(24.dp)
                )
                Spacer(modifier = modifier.width(10.dp))
                Text(
                    text = "Settings",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_condensed_light)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                    )
                )
            }

            Text(
                text = "Re-watch Warrior",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_condensed_extra_light)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000)
                ),
                modifier = modifier.padding(start = 10.dp, top = 16.dp)
            )

            Row(
                modifier = modifier
                    .padding(start = 10.dp)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Include movies in match parties even\nif I've previously matched on them",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_sans_7pt_condensed_extra_light)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000)
                    ),
                    modifier = modifier.weight(1f)
                )
                ToggleSwitch(
                    checked = reWatchToggle,
                    onCheckedChange = { reWatchToggle = it }
                )
            }
        }

        NavBar(navController = navController)
    }
}

@Composable
fun ToggleSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier
    )
}
