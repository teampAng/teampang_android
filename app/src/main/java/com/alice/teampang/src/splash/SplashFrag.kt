package com.alice.teampang.src.splash

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragSplashBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.GlobalApplication
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_GRADE
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_MAJOR
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_NAME
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_NUM
import com.alice.teampang.src.GlobalApplication.Companion.USER_GENDER
import com.alice.teampang.src.GlobalApplication.Companion.USER_ID
import com.alice.teampang.src.GlobalApplication.Companion.USER_NICKNAME
import com.alice.teampang.src.GlobalApplication.Companion.prefs
import com.alice.teampang.src.splash.interfaces.SplashFragView
import com.alice.teampang.src.splash.model.GetProfileResponse


class SplashFrag: BaseFrag(), SplashFragView {

    lateinit var navController : NavController

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

        navController = Navigation.findNavController(view)

        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                val jwt: String? = prefs.getString(GlobalApplication.ACCESS_TOKEN, null)
                if (jwt != null) tryGetProfile()
                else navController.navigate(R.id.action_splashFrag_to_loginFrag)
            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

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
                    prefs.setInt(UNIV_NUM, getProfileResponse.data.university!!.univNum)
                }
                navController.navigate(R.id.action_splashFrag_to_mainFrag)
            }
            404 -> {
                //프로필 존재x, 로그인 화면으로 넘기기
                navController.navigate(R.id.action_splashFrag_to_loginFrag)
            }
            else -> {
                //예상치 못한 서버 응답
                showCustomToast(getProfileResponse.message)
            }
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

