package com.alice.teampang.src.plan_possible.selection


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanPossibleSelection2Binding
import com.alice.teampang.src.BaseFrag
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import org.json.JSONArray
import org.json.JSONObject
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

class PlanPossibleSelectionFrag() : BaseFrag(), View.OnClickListener, OnDateSelectedListener {
    private lateinit var mSelectedDay: CalendarDay
    private lateinit var _binding: FragPlanPossibleSelection2Binding
    private val binding get() = _binding
    private var mDaySelectionMap: ArrayMap<CalendarDay, ArrayList<Selection>> = ArrayMap()
    private var mPersonalSchedule: ArrayList<PersonalScheduleData> = ArrayList()
    private var mTeamScheduleList: ArrayList<CalendarDay> = ArrayList()
    private var startdate = "2021-04-05" //팀장이 정한 시작 기간을 다음과 같이 서버에서 가져와줌
    private var finishdate = "2021-04-10" //팀장이 정한 끝 기간
    private val mStartPosition = 15 //서버에서 주어지는 숫자 팀장이 15~20시까지 시간 선택을 하게끔 설정
    private var mEndPosition = 20
    private var arr = startdate.split("-").toTypedArray()
    private var arr2 = finishdate.split("-").toTypedArray()

    private var mAdapter: SelectionAdapter? = null
    private lateinit var mTeamFirstDate: LocalDate
    private lateinit var mTeamLastDate: LocalDate

    // 현재 선택한 날짜를 위한 Decorator
    private lateinit var mSelectedDayDecorator: SelectedDayDecorator
    private lateinit var mTeamScheduleDecorator: TeamScheduleDecorator

    private var btnSelectAll = false

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanPossibleSelection2Binding.inflate(inflater, container, false)

        titleCustom()
        textSettings()
        init()
        checkAllAvailableSelections(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.dateBtn.setOnClickListener(this)
        binding.btnSelectAll.setOnClickListener(this)
    }

    private fun textSettings(){
        binding.selectText1.setText("선택불가")
        binding.selectText2.setText("선택가능")
        binding.selectText3.setText("선택")
        binding.selectText4.setText("개인일정")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.date_btn -> {
                val data = getJsonFromAvailableSchedule(mDaySelectionMap)
                Log.i(TAG, "data = $data")
                navController.navigate(R.id.action_planPossibleSelectionFrag_to_planPossibleComplete)
            }
            R.id.btn_select_all -> toggleSelectAll()
        }
    }
    //캘린더 디자인 커스텀
    private fun titleCustom() {
        binding.cvCalendar.setTitleFormatter { "${it.month}월" }
        binding.cvCalendar.setOnMonthChangedListener { widget, date ->
            binding.cvCalendar.setTitleFormatter { "${it.month}월" }
        }
        val weekgroup  = arrayOf("S", "M", "T", "W" , "T", "F", "S") //월~일을 바꿔주는코드 적용 완료
        binding.cvCalendar.setWeekDayLabels(weekgroup)
    }
    //모두 제거, 모두 선택 실행
    private fun toggleSelectAll() {
        if (btnSelectAll) {
            btnSelectAll = false
            checkAllAvailableSelections(true)
            binding.btnSelectAll.setImageResource(R.drawable.ic_all_release)
        } else {
            btnSelectAll = true
            checkAllAvailableSelections(false)
            binding.btnSelectAll.setImageResource(R.drawable.ic_all_check)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun init() {
        createCalendarData()
        // Material Calendar 관련 설정.
        initMaterialCalendar()
    }
    //각 날짜, 시간
    @RequiresApi(Build.VERSION_CODES.N)
    private fun createCalendarData() {
        // 팀 스케줄 설정
        setTeamSchedule()
        // 개인 스케줄 설정
        setPersonalSchedule()

        val personalScheduleList = getPersonalSchedulesFromJson(getJson())

        // 가져온 팀 스케줄과 개인 스케줄로 available 한 시간 설정
        var nextDate = mTeamFirstDate
        Log.d(TAG, "nextDate = $nextDate")

        while (!nextDate.isEqual(mTeamLastDate.plusDays(1))) {
            //while (!4/5 == 4/9) 즉 현재 1씩 증가하는 nextdate와 lastdate가 같게 되면 false로 루프 빠져나옴 (for문으로 추후 변경 부등호 써서)

            val selections = ArrayList<Selection>()

            // 24시간의 Selection을 생성
            for (i in 0 until SELECTION_HOURS) {
                selections.add(Selection(i))
            }

            val day = CalendarDay.from(nextDate)
            //팀 데코레이션에서 칠해줄 리스트를 만들어줌
            mTeamScheduleList.add(day)

            // 날짜별로 selections를 매핑
            mDaySelectionMap[day] = selections
            //map[2021-02-19] = 24개의 selections를 대입
            //map의 [day] 번째 key에 selections(value)가 쭉 들어감

            // 1. 먼저 개인 스케줄을 Selection에 대입

            // 개인 스케쥴에서 오늘의 요일을 확인하고 스케줄을 가져온다.
            val personalScheduleData = getPersonScheduleOfTheDay(personalScheduleList, day) //오늘의 날짜를 넣음 -> schedule data의 요일과 일치하면 스케쥴을 가져옴

            // 개인 스케쥴의 데이터를 Selection에 삽입
            if (personalScheduleData != null) {
                if (personalScheduleData.isNotEmpty()) {
                    for (list in personalScheduleData) {
                        val startTime = list.getStartTime()
                        val endTime = list.getEndTime()
                        for (i in startTime..endTime) { //예 15~20시
                            //자바에서 selections.get(i) = selections[i]
                            selections[i].isPersonalSchedule = true //starttime ~ endtime 번째의 selections의 isPersonalSchedule을 true로
                            selections[i].titleOfSchedule = list.scheduleName //starttime ~ endtime 번째의 selections의 개인일정 이름값을 넣어줌
                            Log.i(TAG, "selection[$i] : ${selections[i]}")
                        }
                    }
                }
            }

            // 2. 팀장이 정한 시간에 개인 스케줄을 제외하고 isAvailableTime으로 할당

            // selections에 팀장이 정한 시간 셋팅
            for (i in mStartPosition .. mEndPosition) {
                Log.d(TAG, "team leader time set $i")
                selections[i].isTeamSchedule = true

                // 개인 스케줄이 있으면 체크하지 못하도록 isAvailableTime false 처리
                if (!selections[i].isPersonalSchedule) {
                    selections[i].isAvailableTime = true
                    Log.d(TAG, "available time set $i")
                }
            }

            nextDate = nextDate.plusDays(1) //맨 마지막 nextDate를 1씩 증가시킨후 다시 loop반복
            Log.d(TAG, "nextDate = $nextDate")
        }
    }
    //개인 스케쥴에서 오늘의 요일을 확인하고 스케줄을 가져온다.

    private fun getPersonScheduleOfTheDay(
        personalScheduleList: ArrayList<PersonalScheduleData>,
        day: CalendarDay
    ): ArrayList<PersonalScheduleData>? {
        val list = ArrayList<PersonalScheduleData>()
        for (schedule in mPersonalSchedule) { //list에 추가한 만큼 for문이 돈다 지금은 3
            if (day.date.dayOfWeek == getDayOfWeek(schedule)) { //오늘의 요일 == schedule data의 요일과 일치하면
//                return schedule //스케쥴을 리턴한다
                list.add(schedule)
            }
        }
        return list
    }
    //"월"이면 Monday로 "화"면 TUESDAY로 return
    private fun getDayOfWeek(schedule: PersonalScheduleData): DayOfWeek? {
        var dow: DayOfWeek? = null
        when (schedule.day) {
            "월" -> dow = DayOfWeek.MONDAY
            "MON" -> dow = DayOfWeek.MONDAY
            "화" -> dow = DayOfWeek.TUESDAY
            "TUE" -> dow = DayOfWeek.TUESDAY
            "수" -> dow = DayOfWeek.WEDNESDAY
            "WED" -> dow = DayOfWeek.WEDNESDAY
            "목" -> dow = DayOfWeek.THURSDAY
            "THU" -> dow = DayOfWeek.THURSDAY
            "금" -> dow = DayOfWeek.FRIDAY
            "FRI" -> dow = DayOfWeek.FRIDAY
            "토" -> dow = DayOfWeek.SATURDAY
            "SAT" -> dow = DayOfWeek.SATURDAY
            "일" -> dow = DayOfWeek.SUNDAY
            "SUN" -> dow = DayOfWeek.SUNDAY
        }
        return dow
    }


    private fun initMaterialCalendar() {
        val firstDay = CalendarDay.from(arr[0].toInt(), arr[1].toInt(), arr[2].toInt())
        val lastDay = CalendarDay.from(arr2[0].toInt(), arr2[1].toInt(), arr2[2].toInt())
        binding.cvCalendar.selectionMode = MaterialCalendarView.SELECTION_MODE_SINGLE //날짜를 복수클릭 가능하게 만들어줌
        binding.cvCalendar.state().edit() //달력 기본 셋팅
            .isCacheCalendarPositionEnabled(false) //기본셋팅에 필요한 요소 설명 하기 어려워서 그냥 추가하라고 되어있음
            .setMinimumDate(firstDay) //팀장이 정한날 외에는 false
            .setMaximumDate(lastDay)
            .commit()
        binding.cvCalendar.showOtherDates = MaterialCalendarView.SHOW_OUT_OF_RANGE //팀장이 정한 기간 외에는 다 false처리 2
        binding.cvCalendar.isDynamicHeightEnabled //width height을 오류안나게 (어떤달은 4주 어떤달은 5주이니)
        binding.cvCalendar.setDateTextAppearance(R.color.gray)
        mTeamScheduleDecorator = TeamScheduleDecorator(mTeamScheduleList, mDaySelectionMap, requireContext())
        mSelectedDayDecorator = SelectedDayDecorator(requireContext())
        binding.cvCalendar.addDecorators(mTeamScheduleDecorator, mSelectedDayDecorator)
        // OnDateSelected() callback을 받기 위한 Listener 설정
        binding.cvCalendar.setOnDateChangedListener(this)
    }

    // 현재 날짜가 선택되면 호출됨
    @SuppressLint("SetTextI18n")
    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {
        mSelectedDay = date

        if (selected) {
            configureRecyclerView(date)  //선택하면 해당 date에 해당하는 recyclerview 생성
            binding.selectionRecyclerview.visibility = View.VISIBLE
            binding.selectionText.text = "시간 선택 - ${date.month}월 ${date.day}일"
        } else {
            binding.selectionRecyclerview.visibility = View.INVISIBLE
            binding.selectionText.text = ""
        }

        // 맨 처음 항목으로 스크롤
        // Todo 맨 position을 계산하도록 수정 예정
        binding.selectionRecyclerview.scrollToPosition(getScrollStartPosition(mDaySelectionMap[date]!!)-1)
        mSelectedDayDecorator.setDate(date.date)
        // Decorator 내용이 변경되면 반드시 호출해야 함.
        widget.invalidateDecorators()
    }

    private fun configureRecyclerView(date: CalendarDay) {
        // RecyclerView 자체의 크기가 변하지 않는 것을 알고 있을 때
        // 이 옵션을 설정하면 성능이 개선됩니다
        binding.selectionRecyclerview.setHasFixedSize(true)

        // To do
        // map으로 CalendarDay와 Selection을 맵핑하고 해당되는 CalendarDay의 Selection으로 어댑터를 초기화 한다.

        // 날짜에 해당하는 ArrayList<Selection>으로 어댑터 초기화
        mAdapter = SelectionAdapter(context, mDaySelectionMap[date]!!)
        binding.selectionRecyclerview.adapter = mAdapter
        binding.selectionRecyclerview.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        mAdapter!!.setOnItemClickListener(object : SelectionAdapter.ItemClickListener {
            override fun onItemClick(v: View?, position: Int, check: Boolean) {
                //Toast.makeText(context, "$position item checked, $check", Toast.LENGTH_LONG).show()
                val selection: Selection = mDaySelectionMap[mSelectedDay]!![position]
                selection.availableChecked = check
                mAdapter!!.notifyItemChanged(position)
            }
        })
    }

    // String 타입 json으로 부터 개인 스케줄을 뽑아내어 list 형태로 반환
    private fun getPersonalSchedulesFromJson(json: String): ArrayList<PersonalScheduleData> {
        val list: ArrayList<PersonalScheduleData> = ArrayList()
        val jsonArray = JSONArray(json)
        for (i in 0 until jsonArray.length()) {
            val schedule = jsonArray.getJSONObject(i)
            val name: String = schedule.getString("name")
            Log.d (TAG, "$i, name = $name")
            val times = schedule.getString("times")
            val timesArray = JSONArray(times)
            for (j in 0 until timesArray.length()) {
                val timeObj = timesArray.getJSONObject(j)
                val day = timeObj.getString("day")
                val startTime = timeObj.getString("start_time")
                val endTime = timeObj.getString("end_time")
                Log.d(TAG, "day = $day")
                Log.d(TAG, "start_time = $startTime")
                Log.d(TAG, "end_time = $endTime")
                list.add(PersonalScheduleData(day, name, startTime, endTime))
            }
        }
        return list
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getJson(): String {
        val json = StringBuilder()
        val inputStream: InputStream? = context?.assets?.open("private_schedule.json")
        val reader = BufferedReader(InputStreamReader(inputStream))
        for (line in reader.lines()) {
            json.append(line)
        }
        return json.toString()
    }


    private fun setPersonalSchedule() { //개인일정을 서버에서 가져와서 리스트에 담아주었음
        mPersonalSchedule.add(PersonalScheduleData("월", "알바", "15:00:00", "15:50:00"))
        mPersonalSchedule.add(PersonalScheduleData("월", "공부", "16:00:00", "16:50:00"))
        mPersonalSchedule.add(PersonalScheduleData("월", "면접", "17:00:00", "17:50:00"))
        mPersonalSchedule.add(PersonalScheduleData("월", "식사", "18:00:00", "18:50:00"))
        mPersonalSchedule.add(PersonalScheduleData("화", "스터디", "15:00:00", "16:50:00"))
        mPersonalSchedule.add(PersonalScheduleData("화", "청소", "17:00:00", "17:50:00"))
        mPersonalSchedule.add(PersonalScheduleData("수", "모임", "17:00:00", "19:01:00"))
        mPersonalSchedule.add(PersonalScheduleData("수", "식사", "19:01:00", "20:00:00"))
    }

    private fun setTeamSchedule() {
        mTeamFirstDate = LocalDate.of(arr[0].toInt(), arr[1].toInt(), arr[2].toInt())
        mTeamLastDate = LocalDate.of(arr2[0].toInt(), arr2[1].toInt(), arr2[2].toInt())
    }

    //화면 on시 가능한 날짜 모두 체크 되게끔 하는 기능
    private fun checkAllAvailableSelections(check: Boolean) {
        for (day in mDaySelectionMap) {
            Log.d(TAG, "day = $day")
            for (i in 0 until SELECTION_HOURS) {
                // 20210310_carsung 개인 일정도 availableChecked true 값을 가질 수 있어서 초기화 수행
                day.value[i].availableChecked = false
                if (day.value[i].isAvailableTime) {
                    day.value[i].availableChecked = check
                }
            }
        }
        mAdapter?.notifyDataSetChanged()
        mTeamScheduleDecorator.setMap(mDaySelectionMap)
        binding.cvCalendar.invalidateDecorators()
    }

    private fun getJsonFromAvailableSchedule(map: ArrayMap<CalendarDay, ArrayList<Selection>>): JSONObject {
        val jsonDayAndTime = JSONArray()
        for (e in map) {
            for (i in 0 until SELECTION_HOURS) {
                if (e.value[i].availableChecked) {
                    val obj = JSONObject()
                    obj.put("date", e.key.date)
                    obj.put("time", e.value[i].toString())
                    jsonDayAndTime.put(obj)
                }
            }
        }
        val availableTimes = JSONObject()
        availableTimes.put("available_times", jsonDayAndTime)
        return availableTimes
    }


    // 맨 처음 스크롤 리스트를 get
    private fun getScrollStartPosition(selections: ArrayList<Selection>): Int {
        for (selection in selections) {
            selection.apply {
                if (isTeamSchedule || isPersonalSchedule || isAvailableTime) return selection.id
            }
        }
        return 0
    }

    companion object {
        const val TAG = "SelectionFragment"
        const val SELECTION_HOURS = 24
    }
}