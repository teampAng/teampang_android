package com.alice.teampang.src.plan_possible.selection

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.text.style.ForegroundColorSpan
import androidx.annotation.RequiresApi
import com.alice.teampang.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import org.threeten.bp.LocalDate

@SuppressLint("NewApi")
class SelectedDayDecorator(context: Context) : DayViewDecorator {
    private var selection: ShapeDrawable
    private var date: CalendarDay? = null

    init {
        date = CalendarDay.today()
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = context.getColor(R.color.skyblue)
        selection = drawable
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return date != null && day == date
    }

    override fun decorate(view: DayViewFacade) {
        view.setBackgroundDrawable(selection)
        view.addSpan(ForegroundColorSpan(Color.WHITE))
    }

    /**
     * We're changing the internals, so make sure to call [MaterialCalendarView.invalidateDecorators]
     */
    fun setDate(date: LocalDate?) {
        this.date = CalendarDay.from(date)
    }
}