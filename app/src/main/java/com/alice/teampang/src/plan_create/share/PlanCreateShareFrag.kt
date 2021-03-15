package com.alice.teampang.src.plan_create.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanCreateShareBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.GlobalApplication.Companion.USER_NICKNAME
import com.alice.teampang.src.GlobalApplication.Companion.kakaoLink
import com.alice.teampang.src.GlobalApplication.Companion.prefs

class PlanCreateShareFrag : BaseFrag(), View.OnClickListener {

    private var _binding: FragPlanCreateShareBinding? = null
    private val binding get() = _binding!!

    lateinit var userName: String
    lateinit var inviteCode: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanCreateShareBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        userName = prefs.getString(USER_NICKNAME, "팀프앙")!!
        inviteCode = "ef7d7d53-e743-452a-8b43-6cb0e4d2cf93"

        binding.btnKakao.setOnClickListener(this)
        binding.btnInvite.setOnClickListener(this)
        binding.btnFinish.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v) {
            binding.btnKakao -> kakaoLink(46465, userName, inviteCode, "dummyPlan", "", view)
            binding.btnInvite -> {}
            binding.btnFinish -> {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

