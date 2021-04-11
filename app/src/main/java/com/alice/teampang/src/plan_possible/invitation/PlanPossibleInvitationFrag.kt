package com.alice.teampang.src.plan_possible.invitation

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
import com.alice.teampang.databinding.FragPlanPossibleNameBinding
import com.alice.teampang.src.BaseFrag

class PlanPossibleInvitationFrag : BaseFrag(), View.OnClickListener {

    private var _binding: FragPlanPossibleInvitationBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanPossibleInvitationBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.invitationText1.setText("임건님으로부터\n팀프앙SPRINT3 초대가 왔습니다.")
        binding.invitationText3.setText("13 Feb 2021, 14:00 PM")
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.invitationBtn.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when(v.id) {
            R.id.invitation_btn -> {
              navController.navigate(R.id.action_planPossibleInvitationFrag_to_planPossibleNameFrag)
            }
        }
    }
}