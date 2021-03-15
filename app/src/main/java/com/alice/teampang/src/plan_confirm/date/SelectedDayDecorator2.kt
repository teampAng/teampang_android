package com.alice.teampang.src.plan_confirm.date

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.text.style.ForegroundColorSpan
import androidx.annotation.RequiresApi
import com.airbnb.lottie.model.content.CircleShape
import com.alice.teampang.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import org.threeten.bp.LocalDate

@SuppressLint("NewApi")
class SelectedDayDecorator2(context: Context, val calendarDays1: List<CalendarDay>) :
    DayViewDecorator {
    private var selection: ShapeDrawable
    private var date: CalendarDay? = null

    init {
        date = CalendarDay.from(2021,3,10)
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = context.getColor(R.color.primary)
        selection = drawable
    }
    //day를 제한을 줘야함 calendarDays에는 현재 4-5,4-6,4-7 이렇게 3개의 일이 들어가 있음
    override fun shouldDecorate(day: CalendarDay): Boolean {
    if (day in calendarDays1) {
        return date != null && day == date
        }
        else{
           return false
    }
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