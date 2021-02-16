package com.alice.teampang.src.my_schedule

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.NumberPicker
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alice.teampang.databinding.*
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.my_schedule.model.Times


class MyScheduleEditFrag : BaseFrag(), View.OnClickListener {

    lateinit var navController: NavController

    var scheduleName: String? = null
    var day = "월"
    var startMinute = 0
    var startHour = 9
    var endMinute = 0
    var endHour = 10
    var startTimeS = "09:00"
    var endTimeS = "10:00"

    private var timesArrayList = ArrayList<Times>()
    lateinit var times: Times
    private lateinit var myScheduleEditAdapter: MyScheduleEditAdapter

    private var _binding: FragMyScheduleEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragMyScheduleEditBinding.inflate(inflater, container, false)
        val view = binding.root


//        if (arguments?.getSerializable("times") != null) {
//            times = (arguments?.getSerializable("times") as Times?)!!
//            timesArrayList.add(times)
//        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        navController = Navigation.findNavController(view)
        myScheduleEditAdapter = MyScheduleEditAdapter(requireContext())

        //새로운 개인일정 추가할 때 필요(나중에 어느 버튼으로 온 건지 구분해야겠다)
        setRcvMyScheduleEdit()

        setFragmentResultListener("requestKey") { resultKey, bundle ->
            timesArrayList = bundle.getSerializable("times") as ArrayList<Times>
            scheduleName = bundle.getString("name")
            binding.scheduleName.hint = scheduleName
            //개인일정 수정할 때 일정이름은 수정 안 되게
            binding.scheduleName.isEnabled = false
            setRcvMyScheduleEdit()
        }



        binding.scheduleName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                scheduleName = p0.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        binding.btnBack.setOnClickListener(this)
        binding.btnDay.setOnClickListener(this)
        binding.btnTime.setOnClickListener(this)
        binding.btnEdit.setOnClickListener(this)
        binding.btnFinish.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnBack -> navController.popBackStack()
            binding.btnDay -> dayOfWeekDialog()
            binding.btnTime -> timeDialog()
            binding.btnEdit -> {
                if (scheduleName == null) {
                    showCustomToast("일정 이름을 입력해주세요.")
                } else {
                    //test
                    showCustomToast("${scheduleName}${binding.btnDay.text}${binding.btnTime.text}")
                    //리사이클러뷰 아이템 추가
                    //개인일정 추가 api 쏘기

                    timesArrayList.add(Times(day, startTimeS, endTimeS))
                    myScheduleEditAdapter.notifyDataSetChanged()
                }
            }
            binding.btnFinish -> {
                navController.popBackStack()
            }
            binding.layout -> hideKeyBoard()
        }
    }

    fun Fragment.setFragmentResultListener(
        requestKey: String,
        listener: ((resultKey: String, bundle: Bundle) -> Unit)
    ) {
        parentFragmentManager.setFragmentResultListener(requestKey, this, listener)
    }

    private fun setRcvMyScheduleEdit() {
        binding.rvSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSchedule.adapter = myScheduleEditAdapter
        myScheduleEditAdapter.data = timesArrayList
        myScheduleEditAdapter.notifyDataSetChanged()
    }

    private fun timeDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dbinding = DialogTimepickerBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog: AlertDialog = builder.setView(dbinding.root).create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.show()

        dbinding.startTime.setIs24HourView(true)
        dbinding.endTime.setIs24HourView(true)

        dbinding.startTime.setTimeInterval(5)
        dbinding.endTime.setTimeInterval(5)

        //init time
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dbinding.startTime.hour = startHour
            dbinding.endTime.hour = endHour
            dbinding.startTime.minute = startMinute
            dbinding.endTime.minute = endMinute
        } else {
            dbinding.startTime.setCurrentHour(startHour)
            dbinding.endTime.setCurrentHour(endHour)
            dbinding.startTime.setCurrentMinute(startMinute)
            dbinding.endTime.setCurrentMinute(endMinute)
        }

        fun setTime() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                startHour = dbinding.startTime.hour
                endHour = dbinding.endTime.hour
                startMinute = dbinding.startTime.minute * 5
                endMinute = dbinding.endTime.minute * 5
            } else {
                startHour = dbinding.startTime.getCurrentHour()
                endHour = dbinding.endTime.getCurrentHour()
                startMinute = dbinding.startTime.getCurrentMinute()
                endMinute = dbinding.endTime.getCurrentMinute()
            }
        }


        //에타 시간표 로직 참고
        fun makePossible() {
            if (startHour == 23) {
                //start hour 가 23일때는 end time 을 23:55 로 설정
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dbinding.endTime.hour = startHour
                    dbinding.endTime.minute = 55/5
                } else {
                    dbinding.endTime.setCurrentHour(startHour)
                    dbinding.endTime.setCurrentMinute(55/5)
                }
                //start time 을 23:55 로 설정하면, start time 이 23:50 으로 바뀌게 설정
                if (startMinute == 55) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        dbinding.startTime.minute = 50/5
                    } else {
                        dbinding.startTime.setCurrentMinute(50/5)
                    }
                }
            } else {
                //끝시각 시작시각보다 1시간 늦게 설정해주기
                if (startHour > endHour) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        dbinding.endTime.hour = startHour + 1
                        dbinding.endTime.minute = startMinute/5
                    } else {
                        dbinding.endTime.setCurrentHour(startHour + 1)
                        dbinding.endTime.setCurrentMinute(startMinute/5)
                    }
                } else if (startHour == endHour && startMinute >= endMinute) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        dbinding.endTime.hour = startHour + 1
                        dbinding.endTime.minute = startMinute/5
                    } else {
                        dbinding.endTime.setCurrentHour(startHour + 1)
                        dbinding.endTime.setCurrentMinute(startMinute/5)
                    }
                }
            }
        }


        dbinding.startTime.setOnTimeChangedListener { view, hourOfDay, minute ->
            setTime()
            makePossible()
        }
        dbinding.endTime.setOnTimeChangedListener { view, hourOfDay, minute ->
            setTime()
            makePossible()
        }

        dbinding.btnOk.setOnClickListener {
            setTime()
            var startHourS = ""
            var endHourS = ""
            var startMinS = ""
            var endMinS = ""

            fun timeToString(time: Int): String {
                return if(time < 10) { "0$time" }
                else { "$time" }
            }

            startHourS = timeToString(startHour)
            endHourS = timeToString(endHour)
            startMinS = timeToString(startMinute)
            endMinS = timeToString(endMinute)

            startTimeS = "${startHourS}:${startMinS}"
            endTimeS = "${endHourS}:${endMinS}"

            binding.btnTime.text = "${startTimeS} - ${endTimeS}"
            dialog.dismiss()
        }

        dbinding.btnPop.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun dayOfWeekDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dbinding = DialogDayOfWeekPickerBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog: AlertDialog = builder.setView(dbinding.root).create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.show()

        fun dayOfWeekClick(v: View, dayOfWeek: String) {
            v.setOnClickListener {
                day = dayOfWeek
                dialog.dismiss()
                binding.btnDay.text = dayOfWeek
            }
        }
        dayOfWeekClick(dbinding.btnMon, "월")
        dayOfWeekClick(dbinding.btnTue, "화")
        dayOfWeekClick(dbinding.btnWed, "수")
        dayOfWeekClick(dbinding.btnThu, "목")
        dayOfWeekClick(dbinding.btnFri, "금")
        dayOfWeekClick(dbinding.btnSat, "토")
        dayOfWeekClick(dbinding.btnSun, "일")
    }

    val DEFAULT_INTERVAL = 5
    val MINUTES_MIN = 0
    val MINUTES_MAX = 60

    @SuppressLint("PrivateApi")
    fun TimePicker.setTimeInterval(
        timeInterval: Int = DEFAULT_INTERVAL
    ) {
        try {
            val classForId = Class.forName("com.android.internal.R\$id")
            val fieldId = classForId.getField("minute").getInt(null)

            (this.findViewById(fieldId) as NumberPicker).apply {
                minValue = MINUTES_MIN
                maxValue = MINUTES_MAX / timeInterval - 1
                displayedValues = getDisplayedValue(timeInterval)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getDisplayedValue(
        timeInterval: Int = DEFAULT_INTERVAL
    ): Array<String> {
        val minutesArray = ArrayList<String>()
        for (i in 0 until MINUTES_MAX step timeInterval) {
            minutesArray.add(i.toString())
        }

        return minutesArray.toArray(arrayOf(""))
    }

    private fun hideKeyBoard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.scheduleName.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}