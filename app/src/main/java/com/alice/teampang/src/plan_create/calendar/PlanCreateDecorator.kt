package com.alice.teampang.src.plan_create.calendar

import android.content.Context
import android.graphics.Color
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class PlanCreateDecorator (context: Context, drawable: Int, calendarDays1: List<CalendarDay>,
                           val color: Int
) :
    DayViewDecorator {
    var context: Context = context
    private val drawable: Int = drawable
    private val dates: HashSet<CalendarDay> = HashSet(calendarDays1)
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        // apply drawable to dayView
        view.setSelectionDrawable(context.getResources().getDrawable(drawable))

        if (color == 0) {
            // white text color
            view.addSpan(ForegroundColorSpan(Color.WHITE))
        } else if (color == 1) {
            view.addSpan(ForegroundColorSpan(Color.BLACK))
        } else {
            view.addSpan(ForegroundColorSpan(Color.GRAY))
            view.setDaysDisabled(true)
        }
    }

}