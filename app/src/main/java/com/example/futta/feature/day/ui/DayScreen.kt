package com.example.futta.feature.day.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.util.*

@Composable
fun DayScreenUi(day: LocalDate) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 2.dp, bottom = 2.dp)
            .background(Color(0xFF03B670), shape = RoundedCornerShape(4.dp))
            .padding(4.dp)
    ) {

        Text(
            text = "Veranstaltung",
            fontWeight = FontWeight.Bold,
        )

        //      if (event.description != null) {
        //          Text(
        //            text = event.description,
        //          style = MaterialTheme.typography.body2,
        //        maxLines = 1,
        //      overflow = TextOverflow.Ellipsis,
//}
    }
}