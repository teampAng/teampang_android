package com.alice.teampang.src.splash

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragSplashBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.GlobalApplication
import com.alice.teampang.src.GlobalApplication.Companion.INVITE_CODE
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_GRADE
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_MAJOR
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_NAME
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_NUM
import com.alice.teampang.src.GlobalApplication.Companion.USER_GENDER
import com.alice.teampang.src.GlobalApplication.Companion.USER_ID
import com.alice.teampang.src.GlobalApplication.Companion.USER_NICKNAME
import com.alice.teampang.src.GlobalApplication.Companion.prefs
import com.alice.teampang.src.MainActivity
import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.splash.interfaces.SplashFragView
import com.alice.teampang.src.splash.model.GetProfileResponse


class SplashFrag : BaseFrag(), SplashFragView {

    private var _binding: FragSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragSplashBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                val jwt: String? = prefs.getString(GlobalApplication.ACCESS_TOKEN, null)
                if (jwt != null) tryGetProfile()
                else navController.navigate(R.id.action_splashFrag_to_loginFrag) //신규회원
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

    }

    override fun onStart() {
        super.onStart()
        //inviteCode 받아서 저장 -> main -> plan_possible
        if (activity?.intent != null) {
            if (activity?.intent!!.action == Intent.ACTION_VIEW) {
                activity?.intent!!.data?.let {
                    if (it.scheme.equals(getString(R.string.kakao_scheme), false)
                        && it.host.equals("kakaolink", false)
                    ) {
                        val inviteCode = it.query.toString().substring(13)
                        prefs.setString(INVITE_CODE, inviteCode)
                        val bundle = bundleOf("isPlan" to true)
                        navController.navigate(R.id.action_splashFrag_to_mainFrag, bundle)
                    }
                }
            }
        }
    }


    private fun tryGetProfile() {
        val splashService = SplashService(this)
        splashService.getProfile()
    }


    override fun getProfileSuccess(getProfileResponse: GetProfileResponse) {
        when (getProfileResponse.status) {
            200 -> {
                //프로필 조회 성공, 기존 회원
                prefs.setInt(USER_ID, getProfileResponse.data.id)
                prefs.setString(USER_NICKNAME, getProfileResponse.data.nickname)
                prefs.setInt(USER_GENDER, getProfileResponse.data.gender)
                if (getProfileResponse.data.university != null) {
                    prefs.setString(UNIV_NAME, getProfileResponse.data.university!!.univ)
                    prefs.setString(UNIV_MAJOR, getProfileResponse.data.university!!.major)
                    prefs.setInt(UNIV_GRADE, getProfileResponse.data.university!!.grade)
                    prefs.setInt(UNIV_NUM, getProfileResponse.data.university!!.univNum-2000)
                }
                navController.navigate(R.id.action_splashFrag_to_mainFrag)
            }
            else -> showCustomToast(getProfileResponse.message)
        }
    }

    override fun getProfileError(errorResponse: ErrorResponse) {
        when (errorResponse.status) {
            401 -> tryPostRefreshToken { tryGetProfile() }
            404 -> {
                //프로필 존재x, 회원가입 화면으로 넘기기
                navController.navigate(R.id.action_splashFrag_to_loginFrag)
            }
            else -> showCustomToast(errorResponse.message)
        }
    }

    override fun getProfileFailure(message: Throwable?) {
        showCustomToast(message.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

