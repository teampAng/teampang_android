package com.alice.teampang.src.plan_confirm.minhour

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanConfirmMinhourBinding
import com.alice.teampang.databinding.FragPlanPossibleInvitationBinding
import com.alice.teampang.src.BaseFrag
import kotlin.properties.Delegates

class PlanConfirmMinHour :BaseFrag(),View.OnClickListener{
    lateinit var navController : NavController


    private var _binding: FragPlanConfirmMinhourBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanConfirmMinhourBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.planConfirmPicker.minValue = 1

        binding.planConfirmPicker.maxValue = 24 //max를 팀장이 정한 숫자를 서버에서 불러와서 세팅

        //edittext로 값 변경 막아줌
        binding.planConfirmPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        //선택한 시간 담아줌

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.planConfirmBtn.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when(v.id) {
            R.id.plan_confirm_btn -> {
                val picked = binding.planConfirmPicker.value
                val action = PlanConfirmMinHourDirections.actionPlanConfirmMinHourToPlanConfirmDateFrag(picked)
                Log.d("pickednumber1",picked.   toString())
                navController.navigate(action)
            }
        }
    }
}
