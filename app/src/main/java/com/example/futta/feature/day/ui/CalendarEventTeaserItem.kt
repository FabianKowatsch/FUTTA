package com.example.futta.feature.day.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.futta.domain.model.EventId
import com.example.futta.domain.model.TimeSpan
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Composable
fun CalendarEventTeaserItem(event: CalendarEventTeaserUI) {
    Card(
    ) {
        Row(        modifier = Modifier.padding(2.dp),) {
            Text(
                    text = event.timeSpan.timeStart.truncatedTo(ChronoUnit.MINUTES).toString(),
                    fontSize = 9.sp,
            )
            Row () {
                Text(

                        text = event.title,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview
@Composable
fun CalendarEventTeaserItem_Preview() {
    CalendarEventTeaserItem(event = CalendarEventTeaserUI(EventId("sdgfsfgd"), "Beispielevent", TimeSpan(LocalTime.now(), LocalTime.now())))
}