package com.alice.teampang.src.plan_confirm.date

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanConfirmDateBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.plan_confirm.date.Dialog.YesNoDialogFragment
import com.alice.teampang.src.plan_confirm.planshare.Confirmfinal
import com.alice.teampang.src.plan_possible.selection.PlanPossibleSelectionFrag
import com.alice.teampang.src.plan_possible.selection.PlanPossibleSelectionFrag.Companion.SELECTION_HOURS
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import org.json.JSONArray
import org.json.JSONObject
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class PlanConfirmDateFrag : BaseFrag(), OnDateSelectedListener {

    private lateinit var mSelectedDay: CalendarDay
    private var _binding: FragPlanConfirmDateBinding? = null
    private val binding get() = _binding!!
    private lateinit var myContext: Context
    private var mDaySelectionMap: ArrayMap<CalendarDay, ArrayList<Confirm>> = ArrayMap()
    private var mPersonalSchedule: ArrayList<ConfirmTimeData> = ArrayList()
    private var mTeamScheduleList: ArrayList<CalendarDay> = ArrayList()
    private var mTeamScheduleList2: ArrayList<CalendarDay> = ArrayList()
    private var mTeamScheduleList3: ArrayList<CalendarDay> = ArrayList()
    private var mAdapter: ConfirmAdapter? = null
    private var startdate = "2021-04-05" //팀장이 정한 시작 기간을 다음과 같이 서버에서 가져와줌
    private var finishdate = "2021-04-10" //팀장이 정한 끝 기간
    private val mStartPosition = 15 //서버에서 주어지는 숫자 팀장이 15~20시까지 시간 선택을 하게끔 설정
    private var arr = startdate.split("-").toTypedArray()
    private var arr2 = finishdate.split("-").toTypedArray()
    private lateinit var mTeamFirstDate: LocalDate
    private lateinit var mTeamLastDate: LocalDate
    private lateinit var mSelectedDayDecorator: SelectedDayDecorator2
    private lateinit var mTeamScheduleDecorator: TeamScheduleDecorator2
    private lateinit var mEnableDecorator: DayEnableDecorator
    private var disabledList = ArrayList<CalendarDay>()
    private var ableList = ArrayList<CalendarDay>()
    private var availableTimes = JSONObject()
    private var strings3 = ArrayList<String>()
    private var strings4 = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanConfirmDateBinding.inflate(inflater, container, false)
        val view = binding.root

        if (container != null) {
            myContext = container.context
        }
        init()
        settings()

        val button1 = view.findViewById<CardView>(R.id.date_btn2)
        button1.setOnClickListener{
            gettime(mDaySelectionMap)

            val bundle2 = bundleOf("strings3" to strings3,"json" to availableTimes.toString())
            Log.i(TAG2, "mValue3 = $strings3")
            navController.navigate(R.id.action_planConfirmDateFrag_to_confirmfinal,bundle2)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

    }

    private fun init() {
        createCalendarData()
        setdisabledate()
        initMaterialCalendar()
        titleCustom()
    }

    private fun settings() {
        binding.confirmText1.setText("선택불가")
        binding.confirmText2.setText("선택가능")
        binding.confirmText3.setText("선택")
        binding.confirmText4.setText("한명불가")
    }

    private fun titleCustom() {
        binding.confirmDateCalendar.setTitleFormatter { "${it.month}월" }
        binding.confirmDateCalendar.setOnMonthChangedListener { widget, date ->
            binding.confirmDateCalendar.setTitleFormatter { "${it.month}월" }
        }
        val weekgroup = arrayOf("S", "M", "T", "W", "T", "F", "S") //월~일을 바꿔주는코드 적용 완료
        binding.confirmDateCalendar.setWeekDayLabels(weekgroup)
    }

    private fun createCalendarData() {
        setTeamSchedule()
        setPersonalSchedule()

        var nextDate = mTeamFirstDate

        //서버에서 들어온 날짜로 생성해줘야함
        while (!nextDate.isEqual(mTeamLastDate.plusDays(1))) {
            val selections = ArrayList<Confirm>()

            //마찬가지로 24개의 selection필요
            for (i in 0 until SELECTION_HOURS) {
                selections.add(Confirm(i))
            }
                for(i in 1 until mPersonalSchedule.size) {
                    if (nextDate == getdate()[i]) {
                        var day = CalendarDay.from(nextDate)
                        mTeamScheduleList.add(day)
                        // 날짜별로 selections를 매핑
                        mDaySelectionMap[day] = selections

                        //해당 날짜에 대한 schedule 값들을 변수에 넣음
                        val personalScheduleData = getenabledates(day)
                        if (personalScheduleData.isNotEmpty()) {
                            for (list in personalScheduleData) {
                                val time = list.getStartTime()
                                selections[time].titleOfSchedule = list.unavailable_member
                                selections[time].isAvailableTime = true

                                if (list.unavailable_member == "") {
                                    selections[time].membernone = true
                                } else {
                                    selections[time].membernotnone = true
                                }
                            }
                        }
                    }
                }
            mTeamScheduleList2.add(CalendarDay.from(nextDate))
            //입력한 스케쥴의 데이터를 Selection에 삽입
            nextDate = nextDate.plusDays(1)
        }
    }


    //우리가 입력한 날짜를 selections에 삽입한다.
    private fun getenabledates(day: CalendarDay)
            : ArrayList<ConfirmTimeData> {
        val list = ArrayList<ConfirmTimeData>()
        for (schedule in mPersonalSchedule) { //list에 추가한 만큼 for문이 돈다
            if (day.date == getDayOf(schedule)) { //오늘의 요일 == schedule data의 요일과 일치하면
                //  return schedule //스케쥴을 리턴한다
                list.add(schedule)
            }
        }
        return list
    }

    private fun getDayOf(schdule: ConfirmTimeData): LocalDate? {
        //String값을 Localdate로
        val date = LocalDate.parse(schdule.date, DateTimeFormatter.ISO_DATE)
        return date
    }

    private fun getdate() : ArrayList<LocalDate> {
        var list2 = ArrayList<LocalDate>()
        for (schedule in mPersonalSchedule) {
            list2.add(LocalDate.parse(schedule.date, DateTimeFormatter.ISO_DATE))
        }
        return list2
    }

    private fun setPersonalSchedule() {
        mPersonalSchedule.add(ConfirmTimeData("2021-04-05", "17:00", ""))
        mPersonalSchedule.add(ConfirmTimeData("2021-04-05", "18:00", ""))
        mPersonalSchedule.add(ConfirmTimeData("2021-04-05", "19:00", ""))
        mPersonalSchedule.add(ConfirmTimeData("2021-04-05", "20:00", "김형주"))
        mPersonalSchedule.add(ConfirmTimeData("2021-04-06", "17:00", "임건"))
        mPersonalSchedule.add(ConfirmTimeData("2021-04-06", "18:00", ""))
        mPersonalSchedule.add(ConfirmTimeData("2021-04-06", "19:00", ""))
        mPersonalSchedule.add(ConfirmTimeData("2021-04-06", "20:00", "선민승"))
        mPersonalSchedule.add(ConfirmTimeData("2021-04-07", "17:00", ""))
        mPersonalSchedule.add(ConfirmTimeData("2021-04-07", "18:00", ""))
        mPersonalSchedule.add(ConfirmTimeData("2021-04-07", "19:00", ""))
        mPersonalSchedule.add(ConfirmTimeData("2021-04-07", "20:00", "서채연"))
    }

    private fun setTeamSchedule() {
        mTeamFirstDate = LocalDate.of(arr[0].toInt(), arr[1].toInt(), arr[2].toInt())
        mTeamLastDate = LocalDate.of(arr2[0].toInt(), arr2[1].toInt(), arr2[2].toInt())
    }

    private fun initMaterialCalendar() {
        val firstDay = CalendarDay.from(arr[0].toInt(), arr[1].toInt(), arr[2].toInt())
        val lastDay = CalendarDay.from(arr2[0].toInt(), arr2[1].toInt(), arr2[2].toInt())
        binding.confirmDateCalendar.selectionMode =
            MaterialCalendarView.SELECTION_MODE_SINGLE //날짜를 복수클릭 가능하게 만들어줌
        binding.confirmDateCalendar.state().edit() //달력 기본 셋팅
            .isCacheCalendarPositionEnabled(false) //기본셋팅에 필요한 요소 설명 하기 어려워서 그냥 추가하라고 되어있음
            .setMinimumDate(firstDay) //팀장이 정한날 외에는 false
            .setMaximumDate(lastDay)
            .commit()

        binding.confirmDateCalendar.showOtherDates = (MaterialCalendarView.SHOW_OUT_OF_RANGE
                or MaterialCalendarView.SHOW_DECORATED_DISABLED)
        binding.confirmDateCalendar.isDynamicHeightEnabled //width height을 오류안나게 (어떤달은 4주 어떤달은 5주이니)

        mTeamScheduleDecorator = TeamScheduleDecorator2(
            mTeamScheduleList,
            mDaySelectionMap,
            requireContext()
        )
        mSelectedDayDecorator = SelectedDayDecorator2(requireContext(),ableList)
        binding.confirmDateCalendar.setDateTextAppearance(R.color.gray)
        mEnableDecorator = DayEnableDecorator(disabledList)


        binding.confirmDateCalendar.addDecorators(
            mTeamScheduleDecorator,
            mSelectedDayDecorator,
            mEnableDecorator
        )
        // OnDateSelected() callback을 받기 위한 Listener 설정
        binding.confirmDateCalendar.setOnDateChangedListener(this)

    }

    @SuppressLint("SetTextI18n")
    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {
        mSelectedDay = date
            if (selected && date in ableList) {
                configureRecyclerView(date)  //선택하면 해당 date에 해당하는 recyclerview 생성
                binding.confirmDateRecyclerview.visibility = View.VISIBLE
                binding.confirmText.text = "시간 선택 - ${date.month}월 ${date.day}일"
            } else {
                binding.confirmDateRecyclerview.visibility = View.INVISIBLE
                binding.confirmText.text = ""

        }
        // 맨 처음 항목으로 스크롤
        // Todo 맨 position을 계산하도록 수정 예정
        binding.confirmDateRecyclerview.scrollToPosition(mStartPosition - 1)

        mSelectedDayDecorator.setDate(date.date)

        // Decorator 내용이 변경되면 반드시 호출해야 함.
        widget.invalidateDecorators()
    }

    private fun configureRecyclerView(date: CalendarDay) {
        binding.confirmDateRecyclerview.setHasFixedSize(true)

        // 날짜에 해당하는 ArrayList<Selection>으로 어댑터 초기화
        mAdapter = mDaySelectionMap[date]?.let { ConfirmAdapter(context, it) }
        binding.confirmDateRecyclerview.adapter = mAdapter
        binding.confirmDateRecyclerview.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        mAdapter?.setOnItemClickListener(object : ConfirmAdapter.ItemClickListener {
            override fun onMemberNoneClick(v: View?, position: Int, check: Boolean) {
                //Toast.makeText(context, "$position item checked, $ check", Toast.LENGTH_LONG).show()
                val selection: Confirm = mDaySelectionMap[mSelectedDay]!![position]
                if (selection.membernone) {
                    selection.membernone = true
                } else if (!selection.membernone) {
                    selection.membernone = false
                }
                mAdapter!!.notifyItemChanged(position)
            }

            override fun onMemberNotNoneClick(v: View?, position: Int, check: Boolean) {
                val selection: Confirm = mDaySelectionMap[mSelectedDay]!![position]

                //!일때 ->dialog
                activity?.supportFragmentManager?.let { fragmentManager ->
                    val dialog2 = YesNoDialogFragment()
                    if (!selection.membernone) {
                        val args2 = Bundle()
                        args2.putString("key", selection.titleOfSchedule)
                        dialog2.arguments = args2
                        dialog2.show(fragmentManager, "hi")
                        dialog2.setOnItemClickListener(object :
                            YesNoDialogFragment.DialClickListener {
                            override fun DialMemberClick(check: Boolean) {
//                            selection.membernone = !selection.membernone   //콜백 리스너 활용해서 yes를 누를시에 해당변수가 true로
                                selection.membernone = true
                                mAdapter!!.notifyItemChanged(position)
                            }
                        })
                    } else {
                        selection.membernone = false    //membernone이 false일때 느낌표가 들어가  게 해야해서
                        //but false일때 이미지가 정의되어 있지 않아 느낌표가 들어가지 못함.
                        mAdapter!!.notifyItemChanged(position)
                    }
                }
            }
        })
    }

    private fun setdisabledate() {
        var firstDay = LocalDate.of(arr[0].toInt(), arr[1].toInt(), arr[2].toInt()-1)
        val lastDay = LocalDate.of(arr2[0].toInt(), arr2[1].toInt(), arr2[2].toInt()+1)
        var strings = ArrayList<String>()
        var strings2 = ArrayList<String>()
        while (true) {
            firstDay = firstDay.plusDays(1)
            if (firstDay == lastDay) break
            strings.add(firstDay .toString())
            strings2.add(firstDay .toString())
        }
        for(i in 0 until mPersonalSchedule.size){
            if(mPersonalSchedule[i].date in strings){
                strings.remove(mPersonalSchedule[i].date)
            }
            if(mPersonalSchedule[i].date !in strings2){
                strings2.remove(mPersonalSchedule[i].date)
            }
        }
        for(i in 0 until strings.size){
            val date = strings[i].split("-").toTypedArray()
            val date2 = strings2[i].split("-").toTypedArray()
            disabledList.add(CalendarDay.from(date[0].toInt(), date[1].toInt(), date[2].toInt()))
            ableList.add(CalendarDay.from(date2[0].toInt(), date2[1].toInt(), date2[2].toInt()))
        }
        Log.d("disable",disabledList.toString())
        Log.d("able",ableList.toString())
    }

    private fun gettime(map: ArrayMap<CalendarDay, ArrayList<Confirm>>) {
        val jsonDayAndTime = JSONArray()
        for (e in map) {
            for (i in 0 until SELECTION_HOURS) {
                if (e.value[i].membernone) {
                    val obj = JSONObject()
                    obj.put("date", e.key.date)
                    obj.put("time", e.value[i].toString())
                    jsonDayAndTime.put(obj)
                    strings3.add(e.key.date.toString())
                    strings3.add(e.value[i].toString())
                }
            }
        }
        availableTimes.put("available_times", jsonDayAndTime)
    }

    companion object {
        const val TAG2 = "ConfrimDateFrag"
        const val SELECTION_HOURS = 24
    }
}
/*
{
	"date": "2011-08-12"
	"detail": [
	{
		"time": "13:00"
		"unavailable_member": null
	},
	{
		"time": "13:00"
		"unavailable_member": null
	},
	]
},
{
	"date": "2011-08-12"
	"detail": [
	{
		"time": "13:00"
		"unavailable_member": null
	},
	{
		"time": "13:00"
		"unavailable_member": null
	},
	]
}
 */