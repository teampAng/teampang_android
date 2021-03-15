package com.alice.teampang.src.plan_confirm.date

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import android.util.ArrayMap
import androidx.core.content.ContextCompat
import com.alice.teampang.R
import com.alice.teampang.src.plan_possible.selection.PlanPossibleSelectionFrag
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class TeamScheduleDecorator2(private val dayList: ArrayList<CalendarDay>,
private var map: ArrayMap<CalendarDay, ArrayList<Confirm>>, context:Context)
:DayViewDecorator{
    private var highlightDrawable: Drawable?

    init {
//        val drawable = ShapeDrawable(OvalShape())
//        drawable.paint.color=(ContextCompat.getColor(context,R.color.primary))
        val drawable = ContextCompat.getDrawable(context,R.drawable.l_independent)
        highlightDrawable = drawable
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        if (dayList.contains(day) && isThereAnyAvailableSchedule(day)) {
            return true
        }
        return false
    }

    override fun decorate(view: DayViewFacade?) {
        highlightDrawable?.let { view?.setBackgroundDrawable(it) }
        view?.addSpan(ForegroundColorSpan(Color.BLACK))
    }

    private fun isThereAnyAvailableSchedule(day: CalendarDay?): Boolean {
        for (e in map) {
            if (e.key.equals(day)) {
                for (i in 0 until PlanPossibleSelectionFrag.SELECTION_HOURS) {
                    if(e.value[i].membernone&&e.value[i].isAvailableTime)
                    {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun setMap(map: ArrayMap<CalendarDay, ArrayList<Confirm>>) {
        this.map = map
    }
}