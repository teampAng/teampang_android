package com.alice.teampang.src.plan_create.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanCreateCalendarBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.GlobalApplication.Companion.END_DATE
import com.alice.teampang.src.GlobalApplication.Companion.START_DATE
import com.alice.teampang.src.GlobalApplication.Companion.prefs
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate
import java.util.*


class PlanCreateCalendarFrag : BaseFrag(), View.OnClickListener {

    private var _binding: FragPlanCreateCalendarBinding? = null
    private val binding get() = _binding!!

    private var dateList = ArrayList<LocalDate>()
    private var startDate = ""
    private var endDate = ""

    val color = 0
    private val transparent = 1
    private val disabled = 2

    private val year = CalendarDay.today().year
    private val month = CalendarDay.today().month
    private val day = CalendarDay.today().day

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanCreateCalendarBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        //타이틀 커스텀
        binding.calendarView.setTitleFormatter { "${it.month}월" }
        binding.calendarView.setOnMonthChangedListener { widget, date ->
            binding.calendarView.setTitleFormatter { "${it.month}월" }
        }

        //최소 최대 날짜 지정
        val cal = Calendar.getInstance()
        cal.set(year, month + 1, 1)
        val maxDay: Int = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        binding.calendarView.state().edit()
            .setMinimumDate(CalendarDay.from(year, month, 1))
            .setMaximumDate(CalendarDay.from(year, month + 2, maxDay))
            .commit()

        setDisabledDates()
        rangeCalendar()

        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_back -> navController.popBackStack()
            R.id.btn_next -> {
                if (startDate != "" && endDate != "") {
                    prefs.setString(START_DATE, startDate)
                    prefs.setString(END_DATE, endDate)
                    navController.navigate(R.id.action_planCreateCalendarFrag_to_planCreateTimeFrag)
                } else showCustomToast("일정을 선택해주세요")
            }
        }
    }

    private fun getDate(i: Int): String {
        val calendar = dateList[i]
        val day = calendar.dayOfMonth
        val month = calendar.monthValue
        val year = calendar.year

        return "${year}년 ${month}월 ${day}일"
    }


    private fun rangeCalendar() {
        binding.calendarView.setOnRangeSelectedListener { widget, dates ->
            for (i in dates.indices) {
                dateList.add(dates[i].date)
            }
            if (dateList.size > 28) {
                showCustomToast("기간은 최대 4주까지 선택할 수 있습니다.")
                val removeList = ArrayList<LocalDate>()
                Log.d("why", dateList.toString())
                for (i in 28 until dateList.size) {
                    Log.d("why", dateList[28].toString())
                    removeList.add(dateList[28])
                    dateList.removeAt(28)
                }
                setEvent(removeList, transparent)
                removeList.clear()
            }
            //나중에 넘기기
            startDate = dateList[0].toString()
            endDate = dateList[dateList.size-1].toString()
            binding.tv2.text = "${getDate(0)} ~ ${getDate(dateList.size-1)}"
            setEvent(dateList, color)
        }

        binding.calendarView.setOnDateChangedListener { _, date, selected ->
            if (dateList.size > 0) {
                setEvent(dateList, transparent)
                dateList.clear()
                startDate = ""
                endDate = ""
            }
            dateList.add(date.date)
            if (selected) {
                binding.tv2.text = getDate(0)
                setEvent(dateList, color)
                startDate = date.date.toString()
                endDate = startDate
            } else {
                setEvent(dateList, transparent)
                //나중에 스트링리소스 정리할 때 바꾸기
                binding.tv2.text = "일정을 선택해주세요"
                startDate = ""
                endDate = ""
            }
            dateList.clear()
        }
        binding.calendarView.invalidateDecorators()
    }

    private fun setDisabledDates() {
        val disabledList = ArrayList<LocalDate>()
        for (i in 1 until day) {
            disabledList.add(LocalDate.of(year, month, i))
        }
        setEvent(disabledList, disabled)
    }

    private fun setEvent(dateList: List<LocalDate>, color: Int) {
        val datesLeft: MutableList<CalendarDay> = ArrayList()
        val datesCenter: MutableList<CalendarDay> = ArrayList()
        val datesRight: MutableList<CalendarDay> = ArrayList()
        val datesIndependent: MutableList<CalendarDay> = ArrayList()
        datesLeft.clear()
        datesCenter.clear()
        datesRight.clear()
        datesIndependent.clear()
        for (localDate in dateList) {
            var right = false
            var left = false
            for (day1 in dateList) {
                if (localDate.isEqual(day1.plusDays(1))) {
                    left = true
                }
                if (day1.isEqual(localDate.plusDays(1))) {
                    right = true
                }
            }
            if (left && right) {
                datesCenter.add(CalendarDay.from(localDate))
            } else if (left) {
                datesLeft.add(CalendarDay.from(localDate))
            } else if (right) {
                datesRight.add(CalendarDay.from(localDate))
            } else {
                datesIndependent.add(CalendarDay.from(localDate))
            }
        }
        when (color) {
            0 -> {
                setDecor(datesCenter, R.drawable.r_center, 0)
                setDecor(datesLeft, R.drawable.r_left, 0)
                setDecor(datesRight, R.drawable.r_right, 0)
                setDecor(datesIndependent, R.drawable.r_independent, 0)
            }
            1 -> {
                setDecor(datesCenter, R.drawable.t_center, 1)
                setDecor(datesLeft, R.drawable.t_left, 1)
                setDecor(datesRight, R.drawable.t_right, 1)
                setDecor(datesIndependent, R.drawable.t_independent, 1)
            }
            2 -> {
                setDecor(datesCenter, R.drawable.t_center, 2)
                setDecor(datesLeft, R.drawable.t_left, 2)
                setDecor(datesRight, R.drawable.t_right, 2)
                setDecor(datesIndependent, R.drawable.t_independent, 2)
            }
        }
    }

    private fun setDecor(calendarDayList: List<CalendarDay>, drawable: Int, color: Int) {
        binding.calendarView.addDecorators(
            PlanCreateDecorator(
                requireContext(), drawable, calendarDayList, color
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
