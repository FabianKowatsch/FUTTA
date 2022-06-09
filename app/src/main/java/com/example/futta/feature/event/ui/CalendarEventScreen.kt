package com.example.futta.feature.event.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.futta.R
import com.example.futta.domain.model.EventId
import java.time.temporal.ChronoUnit

@Composable
fun CalendarEventScreen(viewModel: CalendarEventViewModel = viewModel(), eventId: EventId) {
    val event by viewModel.bindUi(LocalContext.current, eventId = eventId).observeAsState()
    CalendarEventScreenUi(event = event)

}

@Composable
fun CalendarEventScreenUi(event: CalendarEventUI?) {
    if (event != null) {
        val dateString = event.date.toString()
        Scaffold(
                topBar = {
                    Column(modifier = Modifier
                            .background(colorResource(id = R.color.green_3100))
                            .fillMaxWidth()
                            .padding(10.dp, 2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                                text = dateString,
                                color = colorResource(id = R.color.white)
                        )
                    }
                },
        ) {
            Column(

            ) {
                Text(
                        modifier = Modifier
                                .padding(2.5.dp),
                        text = event.timeSpan.timeStart.truncatedTo(ChronoUnit.MINUTES).toString(),
                        fontSize = 12.sp,
                )
            }
            Column(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                        text = event.title,
                        //  color = Color(0xFFFFFFFF),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                )
            }
            Column(

            ) {
                Text(
                        modifier = Modifier
                                .padding(2.5.dp, 15.dp, 2.5.dp, 2.5.dp),
                        text = event.timeSpan.timeEnd.truncatedTo(ChronoUnit.MINUTES).toString(),
                        fontSize = 12.sp,
                )
            }
            Column(
            ) {
                Text(
                        modifier = Modifier
                                .padding(50.dp, 35.dp, 5.dp, 5.dp),
                        text = event.description,
                        fontSize = 12.sp,
                )
            }
        }
    }
}
