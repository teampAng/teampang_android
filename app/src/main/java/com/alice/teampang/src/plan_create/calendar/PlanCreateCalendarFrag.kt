package com.alice.teampang.src.plan_create.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanCreateCalendarBinding
import com.alice.teampang.src.BaseFrag
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager
import com.applikeysolutions.cosmocalendar.view.CalendarView
import java.util.*


class PlanCreateCalendarFrag : BaseFrag(), View.OnClickListener {

    lateinit var navController: NavController
    lateinit var calendarView: CalendarView

    private var _binding: FragPlanCreateCalendarBinding? = null
    private val binding get() = _binding!!

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
        calendarView = binding.calendarView

        calendarView.isShowDaysOfWeekTitle = false
        calendarView.selectionManager = RangeSelectionManager(OnDaySelectedListener {
            if (binding.calendarView.selectedDates.size <= 0) return@OnDaySelectedListener
            else {
                val days: List<Calendar> = calendarView.selectedDates
                //나중에 넘기기
                val start = getDate(0)
                val end = getDate(days.size - 1)
                binding.tv2.text = "${start} ~ ${end}"
            }
        })

        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_back -> navController.popBackStack()
            R.id.btn_next -> navController.navigate(R.id.action_planCreateCalendarFrag_to_planCreateTimeFrag)
        }
    }

    private fun getDate(i: Int): String {
        val days: List<Calendar> = calendarView.selectedDates
        val calendar: Calendar = days[i]
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        return "${year}년 ${month + 1}월 ${day}일"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
