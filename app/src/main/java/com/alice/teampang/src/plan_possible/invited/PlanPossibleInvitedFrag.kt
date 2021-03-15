package com.alice.teampang.src.plan_possible.invited

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanPossibleInvitationBinding
import com.alice.teampang.databinding.FragPlanPossibleInvitedBinding
import com.alice.teampang.databinding.FragPlanPossibleNameBinding
import com.alice.teampang.src.BaseFrag

class PlanPossibleInvitedFrag : BaseFrag(), View.OnClickListener {


    private var _binding: FragPlanPossibleInvitedBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanPossibleInvitedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.possibleInvitedBtn.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when(v.id) {
            R.id.possible_invited_btn -> {
              navController.navigate(R.id.action_planPossibleInvitedFrag_to_planPossibleSelectionFrag)
            }
        }
    }
}