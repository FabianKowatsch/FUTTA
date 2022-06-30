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
                title = "Mobile App Development",
                description = "In dieser Veranstaltung lernen Sie wie mobile Apps entwickelt werden und sehen dabei die volle Bandbreite von hands-on Coding bis zu den App Stores. Der Fokus liegt bei der Praxis auf Android wir werden uns allerdings in den Theorie Sessions auch mit iOS befassen.",
                timeSlot = TimeSlot.TWO,
                date = LocalDate.of(2022, 6, 28),
                lectureInfo = LectureInfo.Hybrid(
                    location = "DM-09",
                    onlineUrl = "https://rooms.hs-furtwangen.de/rooms/dm09",
                    felixUrl = "https://felix.hs-furtwangen.de/url/RepositoryEntry/4072408000/CourseNode/92654919339985",
                )
            ),
            CalendarEvent.Lecture.create(
                id = EventId("d4735e3a265e16eee03f59718b9b5d03019c07d8b6c51f90ssdd666eec13ab35"),
                title = "Mobile App Dev Praktikum",
                description = "In dieser Veranstaltung lernen Sie wie mobile Apps entwickelt werden und sehen dabei die volle Bandbreite von hands-on Coding bis zu den App Stores. Der Fokus liegt bei der Praxis auf Android wir werden uns allerdings in den Theorie Sessions auch mit iOS befassen.",
                timeSlot = TimeSlot.TWO,
                date = LocalDate.of(2022, 6, 28),
                lectureInfo = LectureInfo.Online(
                    onlineUrl = "https://rooms.hs-furtwangen.de/rooms/dm09",
                    felixUrl = "https://felix.hs-furtwangen.de/url/RepositoryEntry/4072408000/CourseNode/92654919339985",
                )
            ),
            CalendarEvent.Lecture.create(
                id = EventId("f4735e3a265e16eee03f59718b9b5d03019c07d8b6c51f4hda5a6g6eec13ab35"),
                title = "IT- und Online-Produktmanagement",
                description = "Dr. rer.pol Gotthard Pietsch",
                timeSlot = TimeSlot.TWO,
                date = LocalDate.of(2022, 6, 30),

                lectureInfo = LectureInfo.Hybrid(
                    location=
                    "FU L1.05a",
                    onlineUrl = "https://rooms.hs-furtwangen.de/rooms/dm11",
                    felixUrl = "https://www.dm.hs-furtwangen.de/dm.php?template=projects_welcome&projectid=2033",
                )
            ),  CalendarEvent.Lecture.create(
                id = EventId("f4735e3a265e16eee03f59718b9tzdd03019c07d8b6c51f90da3a666eec13ab35"),
                title = "Management von Medienprodukten",
                description = "Prof. Dr. Christoph Zydorek",
                timeSlot = TimeSlot.TWO,
                date = LocalDate.of(2022, 7, 1),

                lectureInfo = LectureInfo.Local(
                    location=
                    "FU I0.14",
                    felixUrl = "https://felix.hs-furtwangen.de/url/RepositoryEntry/4072407124/CourseNode/102268025979099",
                )
            ),
            CalendarEvent.Lecture.create(
                id = EventId("f4735e3a265e16eee03f59718b9b5d03019c07d8b6c51f90da3a666eec13ab35"),
                title = "Aktuelle Entwicklungen im Bereich Online Medien",
                description = "Seminar über moderne Technologien im Online-Bereich mit Prof. Dr. Eisenbiegler",
                timeSlot = TimeSlot.FOUR,
                date = LocalDate.of(2022, 6, 28),
                lectureInfo = LectureInfo.Local(
                    location=
                    "FU L1.05a",
                    felixUrl = "https://felix.hs-furtwangen.de/url/RepositoryEntry/4067262949",
                )
            ),
            CalendarEvent.Lecture.create(
                id = EventId("f4735e3a265e16eee03f59718b9b5d03019c07d8b6c51f90da3a666eec13ab35"),
                title = "English - Business (B2.1)",
                description = "Business English with Kelsey Phillips",
                timeSlot = TimeSlot.FIVE,
                date = LocalDate.of(2022, 6, 28),
                lectureInfo = LectureInfo.Online(
                    onlineUrl = "https://rooms.hs-furtwangen.de/rooms/lc23",
                    felixUrl = "https://lconline.hs-furtwangen.de/course/view.php?id=365",
                )
            )

        ).forEach {
            calendarEventRepo.addEvent(it)
        }
    }
}