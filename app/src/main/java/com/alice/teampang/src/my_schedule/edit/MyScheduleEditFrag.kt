package com.alice.teampang.src.my_schedule.edit

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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alice.teampang.databinding.*
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.my_schedule.interfaces.MyScheduleEditFragView
import com.alice.teampang.src.my_schedule.model.*


class MyScheduleEditFrag : BaseFrag(), MyScheduleEditFragView, View.OnClickListener {

    var scheduleName: String? = null
    var day = "월"
    var startMinute = 0
    var startHour = 9
    var endMinute = 0
    var endHour = 10
    var startTimeS = "09:00"
    var endTimeS = "10:00"

    private var mId = 0
    private var timesArrayList = ArrayList<Times>()
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

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        myScheduleEditAdapter = MyScheduleEditAdapter(requireContext())

        if (arguments?.getBoolean("isAdd") == false) {
            binding.title.text = "내 개인 일정 수정"
        }

        setFragmentResultListener("requestKey") { resultKey, bundle ->
            mId = bundle.getInt("id", -1)
            timesArrayList = bundle.getSerializable("times") as ArrayList<Times>
            scheduleName = bundle.getString("name")
            binding.scheduleName.hint = scheduleName
            setRcvMyScheduleEdit()
        }
        setRcvMyScheduleEdit()

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
        binding.btnAdd.setOnClickListener(this)
        binding.btnFinish.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnBack -> navController.popBackStack()
            binding.btnDay -> dayOfWeekDialog()
            binding.btnTime -> timeDialog()
            binding.btnAdd -> {
                if (scheduleName == null) {
                    showCustomToast("일정 이름을 입력해주세요.")
                } else {
                    //test
                    showCustomToast("${scheduleName}${binding.btnDay.text}${binding.btnTime.text}")
                    //리사이클러뷰 아이템 추가
                    timesArrayList.add(Times(day, startTimeS, endTimeS))
                    myScheduleEditAdapter.notifyDataSetChanged()
                }
            }
            binding.btnFinish -> {
                if (arguments?.getBoolean("isAdd") == true) {
                    //일정 추가로 넘어온 경우 -> 일정 생성 api
                    tryPostMySchedule()
                }
                else if (arguments?.getBoolean("isAdd") == false) {
                    //일정 수정으로 넘어온 경우 -> 일정 수정 api
                    tryPutMySchedule()
                }
            }
            binding.layout -> hideKeyBoard()
        }
    }

    private fun Fragment.setFragmentResultListener(
        requestKey: String,
        listener: ((resultKey: String, bundle: Bundle) -> Unit)
    ) {
        parentFragmentManager.setFragmentResultListener(requestKey, this, listener)
    }

    //--------------------개인 일정 생성-----------------------//

    private fun tryPostMySchedule() {
        val myScheduleEditService = MyScheduleEditService(this)
        if (scheduleName != null && timesArrayList.size > 0) {
            val myScheduleBody = MyScheduleBody(scheduleName!!, timesArrayList)
            myScheduleEditService.postMySchedule(myScheduleBody)
        } else showCustomToast("일정을 입력해주세요")
    }

    override fun postMyScheduleSuccess(postScheduleResponse: PostScheduleResponse) {
        when (postScheduleResponse.status) {
            201 -> {
                showCustomToast("새로운 일정이 생성되었습니다")
                navController.popBackStack()
            }
            else -> {
                showCustomToast(postScheduleResponse.message)
            }
        }
    }

    override fun postMyScheduleError(errorResponse: ErrorResponse) {
        when (errorResponse.status) {
            400 -> showCustomToast(errorResponse.message)
            401 -> tryPostRefreshToken { tryPostMySchedule() }
            409 -> showCustomToast(errorResponse.message)
            else -> showCustomToast(errorResponse.message)
        }
    }

    override fun postMyScheduleFailure(message: Throwable?) {
        showCustomToast(message.toString())
    }

    //--------------------개인 일정 수정-----------------------//

    private fun tryPutMySchedule() {
        val myScheduleEditService = MyScheduleEditService(this)
        if (scheduleName != null && timesArrayList.size > 0) {
            val myScheduleBody = MyScheduleBody(scheduleName!!, timesArrayList)
            myScheduleEditService.putMySchedule(mId, myScheduleBody)
        } else showCustomToast("일정을 입력해주세요")
    }

    override fun putMyScheduleSuccess(putScheduleResponse: PostScheduleResponse) {
        when (putScheduleResponse.status) {
            200 -> {
                showCustomToast("일정이 수정되었습니다")
                navController.popBackStack()
            }
            else -> {
                showCustomToast(putScheduleResponse.message)
            }
        }
    }

    override fun putMyScheduleError(errorResponse: ErrorResponse) {
        when (errorResponse.status) {
            400 -> showCustomToast(errorResponse.message)
            401 -> tryPostRefreshToken { tryPutMySchedule() }
            else -> showCustomToast(errorResponse.message)
        }
    }

    override fun putMyScheduleFailure(message: Throwable?) {
        showCustomToast(message.toString())
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
                    dbinding.endTime.minute = 55 / 5
                } else {
                    dbinding.endTime.setCurrentHour(startHour)
                    dbinding.endTime.setCurrentMinute(55 / 5)
                }
                //start time 을 23:55 로 설정하면, start time 이 23:50 으로 바뀌게 설정
                if (startMinute == 55) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        dbinding.startTime.minute = 50 / 5
                    } else {
                        dbinding.startTime.setCurrentMinute(50 / 5)
                    }
                }
            } else {
                //끝시각 시작시각보다 1시간 늦게 설정해주기
                if (startHour > endHour) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        dbinding.endTime.hour = startHour + 1
                        dbinding.endTime.minute = startMinute / 5
                    } else {
                        dbinding.endTime.setCurrentHour(startHour + 1)
                        dbinding.endTime.setCurrentMinute(startMinute / 5)
                    }
                } else if (startHour == endHour && startMinute >= endMinute) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        dbinding.endTime.hour = startHour + 1
                        dbinding.endTime.minute = startMinute / 5
                    } else {
                        dbinding.endTime.setCurrentHour(startHour + 1)
                        dbinding.endTime.setCurrentMinute(startMinute / 5)
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
                return if (time < 10) {
                    "0$time"
                } else {
                    "$time"
                }
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
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.scheduleName.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}