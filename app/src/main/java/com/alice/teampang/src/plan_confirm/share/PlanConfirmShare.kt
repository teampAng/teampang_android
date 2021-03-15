package com.alice.teampang.src.plan_confirm.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanConfirmShareBinding
import com.alice.teampang.src.BaseFrag

class PlanConfirmShare:BaseFrag(), View.OnClickListener {

    private var _binding: FragPlanConfirmShareBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanConfirmShareBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.btnFinish.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_finish -> {
               // navController.navigate(R.id.action_planPossibleInvitationFrag_to_planPossibleNameFrag)
            }
        }
    }
}