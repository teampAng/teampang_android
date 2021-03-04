package com.alice.teampang.src.plan_possible.selection

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanPossibleSelectionBinding
import com.alice.teampang.src.BaseFrag
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlin.collections.ArrayList

class PlanPossibleSelectionFrag : BaseFrag(), View.OnClickListener {

    private lateinit var myContext: Context
    private var _binding: FragPlanPossibleSelectionBinding? = null
    private val binding get() = _binding!!
    private val calList = ArrayList<CalendarDay>()
    private var adapter = SelectionAdapter()
    private var data2 : MutableList<Selection> = setData()
    private lateinit var mLayoutManager : LinearLayoutManager
    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (container != null) {
            myContext = container.context
        }

        _binding = FragPlanPossibleSelectionBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.cvCalendar.selectionMode = MaterialCalendarView.SELECTION_MODE_MULTIPLE
        binding.cvCalendar.state().edit()
            .isCacheCalendarPositionEnabled(false)
            .setMinimumDate(CalendarDay.from(2021, 2, 4))
            .setMaximumDate(CalendarDay.from(2021, 2, 9))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

          binding.cvCalendar.showOtherDates = MaterialCalendarView.SHOW_OUT_OF_RANGE
          binding.cvCalendar.isDynamicHeightEnabled = true

        for (calDay in calList) {
            binding.cvCalendar.addDecorators(CurrentDayDecorator(myContext, calDay))
        }

        view.findViewById<RecyclerView>(R.id.selection_recyclerview).adapter = adapter
        view.findViewById<RecyclerView>(R.id.selection_recyclerview).layoutManager =
            LinearLayoutManager(
                myContext
            )

        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        view.findViewById<RecyclerView>(R.id.selection_recyclerview).layoutManager
        adapter.List = data2

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.cvBtn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.cv_btn -> navController.popBackStack()
        }
    }

    fun setData() : MutableList<Selection>{
        var data1:MutableList<Selection> = mutableListOf<Selection>()
        for(i in 0..23){
            var setnumber = i
            var listdata = Selection(setnumber)
            data1.add(listdata)
        }
        return data1
    }
}
