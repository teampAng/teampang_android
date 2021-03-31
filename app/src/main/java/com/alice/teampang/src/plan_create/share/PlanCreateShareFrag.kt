package com.alice.teampang.src.plan_create.share

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanCreateShareBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.GlobalApplication
import com.alice.teampang.src.GlobalApplication.Companion.PLAN_ID
import com.alice.teampang.src.GlobalApplication.Companion.PLAN_NAME
import com.alice.teampang.src.GlobalApplication.Companion.USER_NICKNAME
import com.alice.teampang.src.GlobalApplication.Companion.kakaoLink
import com.alice.teampang.src.GlobalApplication.Companion.prefs
import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.plan_create.PlanCreateService
import com.alice.teampang.src.plan_create.interfaces.PlanShareFragView
import com.alice.teampang.src.plan_create.model.PlanBody
import com.alice.teampang.src.plan_create.model.PlanResponse

class PlanCreateShareFrag : BaseFrag(), PlanShareFragView, View.OnClickListener {

    private var _binding: FragPlanCreateShareBinding? = null
    private val binding get() = _binding!!

    lateinit var userName: String
    lateinit var planName: String
    lateinit var inviteCode: String
    var kakaoInvite : Boolean = true

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
        planName = prefs.getString(PLAN_NAME, "팀프앙")!!

        binding.btnKakao.setOnClickListener(this)
        binding.btnInvite.setOnClickListener(this)
        binding.btnFinish.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnKakao -> {
                kakaoInvite = true
                tryGetInviteCode()
            }
            binding.btnInvite -> {
                kakaoInvite = false
                tryGetInviteCode()
            }
            binding.btnFinish -> navController.popBackStack()
        }
    }

    private fun tryGetInviteCode() {
        val id = prefs.getInt(PLAN_ID, -1)
        if (id != -1) {
            val planShareService = PlanShareService(this)
            planShareService.getInviteCode(id)
        } else showCustomToast("일정 아이디가 존재하지 않습니다")
    }

    override fun getInviteCodeSuccess(getInviteCodeResponse: PlanResponse) {
        when (getInviteCodeResponse.status) {
            200 -> {
                inviteCode = getInviteCodeResponse.data.inviteCode
                if (kakaoInvite) kakaoLink(46465, userName, inviteCode, "join", "", view)
                else shareIntent()
            }
            else -> showCustomToast(getInviteCodeResponse.message)
        }
    }

    override fun getInviteCodeError(errorResponse: ErrorResponse) {
        when (errorResponse.status) {
            401 -> tryPostRefreshToken { tryGetInviteCode() }
            else -> showCustomToast(errorResponse.message)
        }
    }

    override fun getInviteCodeFailure(message: Throwable?) {
        showCustomToast(message.toString())
    }

    private fun shareIntent() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/html"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "${userName}님에게 ${planName} 초대가 왔어요!")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://www.teampang.app/join/${inviteCode}")
        startActivity(Intent.createChooser(sharingIntent, "일정 초대하기"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

