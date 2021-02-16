package com.alice.teampang.src.plan_confirm.date

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanConfirmDateBinding
import com.alice.teampang.databinding.FragPlanConfirmMinhourBinding
import com.alice.teampang.src.BaseFrag

class PlanConfirmDateFrag:BaseFrag(), View.OnClickListener {

    lateinit var navController : NavController

    val args : PlanConfirmDateFragArgs by navArgs()

    private var _binding: FragPlanConfirmDateBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanConfirmDateBinding.inflate(inflater, container, false)
        val view = binding.root
        val pickednumber = args.mintime
        binding.confirmDateText2.setText("다음은 모두가 $pickednumber+시간의 팀플이 가능한 일정입니다.")

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.planConfirmBtn.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when(v.id) {
            R.id.plan_confirm_btn -> {
//                navController.navigate(R.id.action_planPossibleInvitationFrag_to_planPossibleNameFrag)
            }

            R.id.btn_back -> {
                navController.popBackStack()
            }
        }
    }
}

