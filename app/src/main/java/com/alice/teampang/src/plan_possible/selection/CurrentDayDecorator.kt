package com.alice.teampang.src.plan_possible.selection

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import com.alice.teampang.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class CurrentDayDecorator(context: Context?, currentDay: CalendarDay) : DayViewDecorator {
    @SuppressLint("NewApi", "UseCompatLoadingForDrawables")
    private val drawable: Drawable = context?.getDrawable(R.drawable.ic_menu_circle)!!
    private var myDay = currentDay

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == myDay
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(ForegroundColorSpan(Color.GRAY));
    }

    init {
        // You can set background for Decorator via drawable here
    }
}