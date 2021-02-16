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
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener

/*
8월 1일 누름 -> 0~24까지에 대해서
8월 2일 24~
8월 3일
3x24
collectionview
3일이오면 collection 3개
만들어둔 컬랙션에 포지션을 저장
 */

class PlanPossibleSelectionFrag() : BaseFrag(), View.OnClickListener {
    var selectedDay: CalendarDay? = null
    lateinit var navController: NavController
    private lateinit var myContext: Context
    private var _binding: FragPlanPossibleSelectionBinding? = null
    private val binding get() = _binding!!
    
    private val adapter = SelectionAdapter()
    private val calList = ArrayList<CalendarDay>()
    var data2: MutableList<Selection> = mutableListOf()
    var a = false
    val startdate = "2021-04-05"
    val arr = startdate.split("-")
    val finishdate = "2021-04-10"
    val arr2 = finishdate.split("-")
    var mNumberList = mutableListOf<CalendarDay>()



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
            .setMinimumDate(CalendarDay.from(arr[0].toInt(), arr[1].toInt(), arr[2].toInt()))
            .setMaximumDate(CalendarDay.from(arr2[0].toInt(), arr2[1].toInt(), arr2[2].toInt()))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

        binding.cvCalendar.showOtherDates = MaterialCalendarView.SHOW_OUT_OF_RANGE

        binding.cvCalendar.isDynamicHeightEnabled = true

        binding.cvCalendar.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
                mNumberList.clear()
                mNumberList.add(date)



            view.findViewById<RecyclerView>(R.id.selection_recyclerview).adapter = adapter
            view.findViewById<RecyclerView>(R.id.selection_recyclerview).layoutManager =
                LinearLayoutManager(
                    myContext, LinearLayoutManager.HORIZONTAL, false
                )

            data2.clear()
            for (item in 1..24) {
                data2.add(Selection("$item"))
            }
            adapter.List = data2

        })


        for (calDay in calList) {
            binding.cvCalendar.addDecorators(CurrentDayDecorator(myContext, calDay))
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.dateBtn.setOnClickListener(this)
        binding.alltext.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.date_btn -> navController.popBackStack()

            R.id.alltext -> {
                if (a.equals(true)) {
                    val startdate = "2021-04-05"
                    val arr = startdate.split("-")

                    binding.cvCalendar.selectedDate = CalendarDay.from(2020, 4, 5)
                    binding.cvCalendar.selectedDate = CalendarDay.from(arr[0].toInt(), 4, 6)
                    binding.cvCalendar.selectedDate = CalendarDay.from(arr[0].toInt(), 4, 7)
                    a = false
                } else {
                    a = true
                }
            }
        }
        }
    }

