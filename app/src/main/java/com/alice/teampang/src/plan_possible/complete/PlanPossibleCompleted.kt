package com.alice.teampang.src.plan_possible.complete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanPossibleCompleteBinding
import com.alice.teampang.databinding.FragPlanPossibleInvitationBinding
import com.alice.teampang.src.BaseFrag

class PlanPossibleCompleted : BaseFrag(), View.OnClickListener {



    private var _binding: FragPlanPossibleCompleteBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanPossibleCompleteBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.planCompleteBtn.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when(v.id) {
            R.id.plan_complete_btn -> {
//                navController.navigate(R.id.action_planPossibleInvitationFrag_to_planPossibleNameFrag)
            }
        }
    }
}