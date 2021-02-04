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
import com.alice.teampang.src.plan_create.name.PlanCreateNameFrag
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager

class PlanCreateCalendarFrag : BaseFrag(), View.OnClickListener {

    lateinit var navController : NavController

    private var _binding : FragPlanCreateCalendarBinding? = null
    private val binding  get() = _binding!!



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

        binding.calendarView.isShowDaysOfWeekTitle = false
        binding.calendarView.selectionManager = RangeSelectionManager(OnDaySelectedListener {
            if (binding.calendarView.selectedDates.size <= 0) return@OnDaySelectedListener
        })

        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_back -> navController.popBackStack()
            R.id.btn_next -> navController.navigate(R.id.action_planCreateCalendarFrag_to_planCreateTimeFrag)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}