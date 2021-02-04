package com.alice.teampang.src.plan_create.time

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanCreateTimeBinding
import com.alice.teampang.src.BaseFrag

class PlanCreateTimeFrag : BaseFrag(), View.OnClickListener {

    lateinit var navController: NavController

    lateinit var time_array: ArrayList<ImageView>
    lateinit var box_array: ArrayList<LinearLayout>

    private var _binding: FragPlanCreateTimeBinding? = null
    private val binding get() = _binding!!

    //나중에 넘기기
    var start_time: Int = -1
    var end_time: Int = -1

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

        for (i in 0..23) {
            box_array[i].setOnClickListener(this)
        }
        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_back -> navController.popBackStack()
            R.id.btn_next -> navController.navigate(R.id.action_planCreateTimeFrag_to_planCreateShareFrag)
            R.id.box_0 -> onTimeClick(0)
            R.id.box_1 -> onTimeClick(1)
            R.id.box_2 -> onTimeClick(2)
            R.id.box_3 -> onTimeClick(3)
            R.id.box_4 -> onTimeClick(4)
            R.id.box_5 -> onTimeClick(5)
            R.id.box_6 -> onTimeClick(6)
            R.id.box_7 -> onTimeClick(7)
            R.id.box_8 -> onTimeClick(8)
            R.id.box_9 -> onTimeClick(9)
            R.id.box_10 -> onTimeClick(10)
            R.id.box_11 -> onTimeClick(11)
            R.id.box_12 -> onTimeClick(12)
            R.id.box_13 -> onTimeClick(13)
            R.id.box_14 -> onTimeClick(14)
            R.id.box_15 -> onTimeClick(15)
            R.id.box_16 -> onTimeClick(16)
            R.id.box_17 -> onTimeClick(17)
            R.id.box_18 -> onTimeClick(18)
            R.id.box_19 -> onTimeClick(19)
            R.id.box_20 -> onTimeClick(20)
            R.id.box_21 -> onTimeClick(21)
            R.id.box_22 -> onTimeClick(22)
            R.id.box_23 -> onTimeClick(23)

        }

    }

    fun onTimeClick(i: Int) {
        if (start_time == -1) {
            //선택하기 전
            start_time = i
            time_array[i].visibility = View.VISIBLE
            binding.tv2.text = "종료시각을 선택해주세요"
        } else if (start_time != -1 && end_time == -1) {
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
                time_array[i].visibility = View.INVISIBLE
            } else {
                end_time = i + 1
                for (j in 0 until end_time) {
                    time_array[j].visibility = View.VISIBLE
                }
                for (j in start_time..23) {
                    time_array[j].visibility = View.VISIBLE
                }
                binding.tv2.text = "${start_time}시-${end_time}시, ${end_time - start_time + 24}시간"
            }
        } else if (start_time != -1 && end_time != -1) {
            //시작시각과 끝시각 설정되어있을 때
            for (j in 0..23) {
                time_array[j].visibility = View.INVISIBLE
            }
            time_array[i].visibility = View.VISIBLE
            start_time = i
            end_time = -1
            binding.tv2.text = "종료시각을 선택해주세요"
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


