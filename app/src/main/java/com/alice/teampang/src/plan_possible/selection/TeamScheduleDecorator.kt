package com.alice.teampang.src.plan_possible.selection

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.text.style.ForegroundColorSpan
import android.util.ArrayMap
import android.util.Log
import androidx.core.content.ContextCompat
import com.alice.teampang.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class TeamScheduleDecorator(private val dayList: ArrayList<CalendarDay>,
                            private var map: ArrayMap<CalendarDay, ArrayList<Selection>>,context:Context)
    :DayViewDecorator{
    private var highlightDrawable: Drawable

    init {
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color=(ContextCompat.getColor(context, R.color.primary))
        highlightDrawable = drawable
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        if (dayList.contains(day) && isThereAnyAvailableSchedule(day)) {
            return true
        }
        return false
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(highlightDrawable)
        view?.addSpan(ForegroundColorSpan(Color.WHITE))
    }

    private fun isThereAnyAvailableSchedule(day: CalendarDay?): Boolean {
        for (e in map) {
            if (e.key.equals(day)) {
                for (i in 0 until PlanPossibleSelectionFrag.SELECTION_HOURS) {
                    if (e.value[i].availableChecked)
                    {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun setMap(map: ArrayMap<CalendarDay, ArrayList<Selection>>) {
        this.map = map
    }
}