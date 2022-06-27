package com.example.futta.feature.month.ui

import com.example.futta.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.time.LocalDate

class DayDecoratorImpl() : DayViewDecorator {
    private var _dayList: List<LocalDate> = emptyList()


    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return if (day != null) {
            _dayList.find { date -> date.dayOfMonth == day.day && (date.monthValue + 1) == day.month && day.year ==date.year } == null
        } else false
    }

    override fun decorate( view: DayViewFacade?) {
        view?.addSpan(DotSpan(R.color.green_3000))
    }
    fun setDayList(dayList: List<LocalDate>) {
        _dayList = dayList
    }
}