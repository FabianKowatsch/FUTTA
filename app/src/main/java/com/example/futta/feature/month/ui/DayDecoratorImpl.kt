package com.example.futta.feature.month.ui

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.futta.R
import com.example.futta.domain.model.CycleType
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.time.LocalDate


class DayDecoratorImpl(private val dates: List<MonthCalendarEventUI>, private val icon: Drawable?) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return if (day != null) {
            dates.any { date ->
                eventIsOnDate(
                    date.date,
                    LocalDate.of(day.year, day.month, day.day),
                    date.cycleType
                )
            }
        } else false
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(5f, Color.parseColor("#96B669")))
        icon?.let { view?.setBackgroundDrawable(it) }
    }

    private fun eventIsOnDate(
        eventDate: LocalDate,
        selectedDate: LocalDate,
        cycleType: CycleType
    ): Boolean {
        return when (cycleType) {
            CycleType.YEAR -> eventDate.month == selectedDate.month && eventDate.dayOfMonth == selectedDate.dayOfMonth
            CycleType.MONTH -> eventDate.dayOfMonth == selectedDate.dayOfMonth
            CycleType.WEEK -> eventDate.dayOfWeek == selectedDate.dayOfWeek
            CycleType.NONE -> eventDate == selectedDate
        }
    }
}