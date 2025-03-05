package com.team1.wat2watch.ui.SwipeExample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.team1.wat2watch.ui.SwipeExample.Constants.cornerRadiusBig
import com.team1.wat2watch.ui.SwipeExample.Constants.normalElevation

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    cardIndex: Int
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadiusBig),
        elevation = CardDefaults.cardElevation(defaultElevation = normalElevation)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = cardIndex.toString(),
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    fontSize = 40.sp
                )
            )
        }
    }
}