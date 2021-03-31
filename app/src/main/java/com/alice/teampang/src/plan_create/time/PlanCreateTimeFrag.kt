package com.alice.teampang.src.plan_create.time

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanCreateTimeBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.GlobalApplication.Companion.END_DATE
import com.alice.teampang.src.GlobalApplication.Companion.PLAN_ID
import com.alice.teampang.src.GlobalApplication.Companion.PLAN_NAME
import com.alice.teampang.src.GlobalApplication.Companion.START_DATE
import com.alice.teampang.src.GlobalApplication.Companion.prefs
import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.plan_create.PlanCreateService
import com.alice.teampang.src.plan_create.interfaces.PlanCreateFragView
import com.alice.teampang.src.plan_create.model.PlanBody
import com.alice.teampang.src.plan_create.model.PlanResponse

class PlanCreateTimeFrag : BaseFrag(), PlanCreateFragView, View.OnClickListener {

    private lateinit var time_array: ArrayList<ImageView>
    private lateinit var box_array: ArrayList<LinearLayout>

    private var _binding: FragPlanCreateTimeBinding? = null
    private val binding get() = _binding!!

    private var start_time: Int = -1
    private var end_time: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanCreateTimeBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        time_array = ArrayList()
        box_array = ArrayList()
        makeArray()

        for (i in 0..23) {
            box_array[i].setOnClickListener(this)
        }
        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
        binding.btnAllGrey.setOnClickListener(this)
        binding.btnAllBlue.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnBack -> navController.popBackStack()
            binding.btnNext -> {
                if (start_time != -1 && end_time != -1) {
                    tryPostPlan()
                } else showCustomToast("일정 시간 범위를 선택해주세요")
            }
            binding.btnAllGrey -> selectAll()
            binding.btnAllBlue -> deselectAll()
            binding.box0 -> onTimeClick(0)
            binding.box1 -> onTimeClick(1)
            binding.box2 -> onTimeClick(2)
            binding.box3 -> onTimeClick(3)
            binding.box4 -> onTimeClick(4)
            binding.box5 -> onTimeClick(5)
            binding.box6 -> onTimeClick(6)
            binding.box7 -> onTimeClick(7)
            binding.box8 -> onTimeClick(8)
            binding.box9 -> onTimeClick(9)
            binding.box10 -> onTimeClick(10)
            binding.box11 -> onTimeClick(11)
            binding.box12 -> onTimeClick(12)
            binding.box13 -> onTimeClick(13)
            binding.box14 -> onTimeClick(14)
            binding.box15 -> onTimeClick(15)
            binding.box16 -> onTimeClick(16)
            binding.box17 -> onTimeClick(17)
            binding.box18 -> onTimeClick(18)
            binding.box19 -> onTimeClick(19)
            binding.box20 -> onTimeClick(20)
            binding.box21 -> onTimeClick(21)
            binding.box22 -> onTimeClick(22)
            binding.box23 -> onTimeClick(23)
        }

    }

    private fun onTimeClick(i: Int) {
        if (start_time == -1) {
            //선택하기 전
            start_time = i
            end_time = start_time + 1
            time_array[i].visibility = View.VISIBLE
            binding.tv2.text = "${start_time}시-${end_time}시, 1시간"
        } else if (start_time != -1 && end_time == start_time + 1) {
            //시작시각은 선택, 끝시각은 선택하기 전
            if (i > start_time) {
                //끝시각으로 설정
                end_time = i + 1
                for (j in start_time until end_time) {
                    time_array[j].visibility = View.VISIBLE
                }
                binding.tv2.text = "${start_time}시-${end_time}시, ${end_time - start_time}시간"
            } else if (i == start_time) {
                //시작시각 다시 선택하면 선택 해제
                start_time = -1
                end_time = -1
                time_array[i].visibility = View.INVISIBLE
                binding.tv2.text = "시작 시각을 선택해주세요"
            } else {
                end_time = i + 1
                for (j in 0 until end_time) {
                    time_array[j].visibility = View.VISIBLE
                }
                for (j in start_time..23) {
                    time_array[j].visibility = View.VISIBLE
                }
                if (start_time == end_time) {
                    //24시간 모두 선택한 경우
                    start_time = 0
                    end_time = 24
                    binding.tv2.text = "0시-24시, 24시간"
                } else binding.tv2.text = "${start_time}시-${end_time}시, ${end_time - start_time + 24}시간"
            }
        } else if (start_time != -1 && end_time != -1) {
            //시작시각과 끝시각 설정되어있을 때
            for (j in 0..23) {
                time_array[j].visibility = View.INVISIBLE
            }
            binding.btnAllBlue.visibility = View.INVISIBLE
            binding.btnAllGrey.visibility = View.VISIBLE
            time_array[i].visibility = View.VISIBLE
            start_time = i
            end_time = start_time + 1
            binding.tv2.text = "${start_time}시-${end_time}시, 1시간"
        }
    }

    private fun selectAll() {
        start_time = 0
        end_time = 24
        binding.btnAllBlue.visibility = View.VISIBLE
        binding.btnAllGrey.visibility = View.INVISIBLE
        for (j in 0..23) {
            time_array[j].visibility = View.VISIBLE
        }
        binding.tv2.text = "${start_time}시-${end_time}시, ${end_time - start_time}시간"
    }

    private fun deselectAll() {
        start_time = -1
        end_time = -1
        binding.btnAllBlue.visibility = View.INVISIBLE
        binding.btnAllGrey.visibility = View.VISIBLE
        for (j in 0..23) {
            time_array[j].visibility = View.INVISIBLE
        }
        //나중에 스트링 리소스로 바꾸기
        binding.tv2.text = "시작 시각을 선택해주세요"
    }

    private fun tryPostPlan() {
        val planName = prefs.getString(PLAN_NAME, null)
        val startDate = prefs.getString(START_DATE, null)
        val endDate = prefs.getString(END_DATE, null)
        val startTime = if (start_time == 24) "23:59"
            else "${start_time}:00"
        val endTime = if (end_time == 24) "23:59"
        else "${start_time}:00"
        if (planName != null && startDate != null && endDate != null) {
            val postPlanBody = PlanBody(planName, startDate, endDate, startTime, endTime)
            val planCreateService = PlanCreateService(this)
            planCreateService.postPlan(postPlanBody)
        }
    }

    override fun postPlanSuccess(planResponse: PlanResponse) {
        when (planResponse.status) {
            201 -> {
                prefs.setInt(PLAN_ID, planResponse.data.id)
                navController.navigate(R.id.action_planCreateTimeFrag_to_planCreateShareFrag)
            }
            else -> showCustomToast(planResponse.message)
        }
    }

    override fun postPlanError(errorResponse: ErrorResponse) {
        when (errorResponse.status) {
            401 -> tryPostRefreshToken { tryPostPlan() }
            else -> showCustomToast(errorResponse.message)
        }
    }

    override fun postPlanFailure(message: Throwable?) {
        showCustomToast(message.toString())
    }

    private fun makeArray() {
        time_array.add(binding.time0)
        time_array.add(binding.time1)
        time_array.add(binding.time2)
        time_array.add(binding.time3)
        time_array.add(binding.time4)
        time_array.add(binding.time5)
        time_array.add(binding.time6)
        time_array.add(binding.time7)
        time_array.add(binding.time8)
        time_array.add(binding.time9)
        time_array.add(binding.time10)
        time_array.add(binding.time11)
        time_array.add(binding.time12)
        time_array.add(binding.time13)
        time_array.add(binding.time14)
        time_array.add(binding.time15)
        time_array.add(binding.time16)
        time_array.add(binding.time17)
        time_array.add(binding.time18)
        time_array.add(binding.time19)
        time_array.add(binding.time20)
        time_array.add(binding.time21)
        time_array.add(binding.time22)
        time_array.add(binding.time23)

        box_array.add(binding.box0)
        box_array.add(binding.box1)
        box_array.add(binding.box2)
        box_array.add(binding.box3)
        box_array.add(binding.box4)
        box_array.add(binding.box5)
        box_array.add(binding.box6)
        box_array.add(binding.box7)
        box_array.add(binding.box8)
        box_array.add(binding.box9)
        box_array.add(binding.box10)
        box_array.add(binding.box11)
        box_array.add(binding.box12)
        box_array.add(binding.box13)
        box_array.add(binding.box14)
        box_array.add(binding.box15)
        box_array.add(binding.box16)
        box_array.add(binding.box17)
        box_array.add(binding.box18)
        box_array.add(binding.box19)
        box_array.add(binding.box20)
        box_array.add(binding.box21)
        box_array.add(binding.box22)
        box_array.add(binding.box23)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


