package com.alice.teampang.src.plan_confirm.date
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class DayEnableDecorator(val dates:Collection<CalendarDay>) : DayViewDecorator{

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setDaysDisabled(true)
    }
}