package com.alice.teampang.src.plan_possible.invited

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanPossibleInvitedBinding
import com.alice.teampang.base.BaseFrag

class PlanPossibleInvitedFrag : BaseFrag(), View.OnClickListener {


    private var _binding: FragPlanPossibleInvitedBinding? = null
    private val binding get() = _binding!!
    private var name1 : String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanPossibleInvitedBinding.inflate(inflater, container, false)
        val view = binding.root
        name1 = arguments?.getString("membername")
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.planInvitedBtn.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when(v.id) {
            R.id.plan_invited_btn -> {
                val name2 = bundleOf("membername2" to name1)
              navController.navigate(R.id.action_planPossibleInvitedFrag_to_planPossibleSelectionFrag, name2)
            }
        }
    }
}