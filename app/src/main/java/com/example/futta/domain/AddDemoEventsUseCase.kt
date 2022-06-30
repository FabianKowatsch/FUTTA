package com.example.futta.domain

import com.example.futta.data.calendarEventRepo
import com.example.futta.domain.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class AddDemoEventsUseCase {
    suspend operator fun invoke() = withContext(Dispatchers.Default) {
        if (calendarEventRepo.getAllEvents().isNotEmpty()) return@withContext
        listOfNotNull(
            CalendarEvent.Default.create(
                id = EventId("6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b"),
                title = "birthday max",
                description = "this is an event and this is some additional text to make the textblock larger. The User should now be able to see some more lines of text description.",
                timeSlot = TimeSlot.FULL_DAY,
                date = LocalDate.now(),
                cycleType = CycleType.NONE
            ),
            CalendarEvent.Lecture.create(
                id = EventId("d4735e3a265e16eee03f59718b9b5d03019c07d8b6c51f90da3a666eec13ab35"),
                title = "management",
                description = "this event shouldn't be attended",
                timeSlot = TimeSlot.TWO,
                date = LocalDate.now(),
                lectureInfo = LectureInfo.Hybrid(
                    location = "HFU I 0.012",
                    onlineUrl = "url.com",
                    felixUrl = "url2.com",
                )
            ),
            CalendarEvent.Lecture.create(
                id = EventId("f4735e3a265e16eee03f59718b9b5d03019c07d8b6c51f90da3a666eec13ab35"),
                title = "economy",
                description = "this event shouldn't be attended as well",
                timeSlot = TimeSlot.THREE,
                date = LocalDate.of(2022, 6, 3),

                lectureInfo = LectureInfo.Online(
                    onlineUrl = "url.com",
                    felixUrl = "url2.com",
                )
            )
        ).forEach {
            calendarEventRepo.addEvent(it)
        }
    }
}