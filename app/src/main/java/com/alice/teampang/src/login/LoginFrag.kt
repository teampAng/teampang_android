package com.alice.teampang.src.login

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragLoginBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.GlobalApplication
import com.alice.teampang.src.GlobalApplication.Companion.ACCESS_TOKEN
import com.alice.teampang.src.GlobalApplication.Companion.REFRESH_TOKEN
import com.alice.teampang.src.GlobalApplication.Companion.prefs
import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.login.interfaces.LoginFragView
import com.alice.teampang.src.login.model.KakaoTokenBody
import com.alice.teampang.src.login.model.KakaoTokenResponse
import com.alice.teampang.src.splash.model.GetProfileResponse
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.AuthType
import com.kakao.sdk.auth.model.OAuthToken
import java.util.regex.Pattern

class LoginFrag : BaseFrag(), LoginFragView, View.OnClickListener {

    private lateinit var kakaoTokenBody: KakaoTokenBody

    private var _binding: FragLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        makeHyperlink()

        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> kakaoLogin()
        }
    }

    private fun kakaoLogin() {
        // 로그인 공통 callback 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                showCustomToast("카카오 로그인에 실패하였습니다.")
            } else if (token != null) {
                kakaoTokenBody = KakaoTokenBody(token.accessToken)
                tryPostKakaoToken()
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (LoginClient.instance.isKakaoTalkLoginAvailable(requireView().context)) {
            LoginClient.instance.loginWithKakaoTalk(requireView().context, callback = callback)
        } else {
            LoginClient.instance.loginWithKakaoAccount(
                requireView().context,
                authType = AuthType.REAUTHENTICATE, //기존 로그인 여부와 상관없이 로그인 요청
                callback = callback
            )
        }
    }


    private fun tryPostKakaoToken() {
        prefs.setString(ACCESS_TOKEN, null)
        val loginService = LoginService(this)
        loginService.postKakaoToken(kakaoTokenBody)
    }

    override fun kakaoTokenSuccess(kakaoTokenResponse: KakaoTokenResponse) {
        when (kakaoTokenResponse.status) {
            200 -> {
                //로그인 성공, 신규 회원 or 리프레쉬토큰 만료된 회원
                prefs.setString(ACCESS_TOKEN, kakaoTokenResponse.data.access)
                prefs.setString(REFRESH_TOKEN, kakaoTokenResponse.data.refresh)
                //프로필 get 으로 회원 구분
                tryGetProfile()
            }
            else -> showCustomToast(kakaoTokenResponse.message)
        }
    }

    override fun kakaoTokenError(errorResponse: ErrorResponse) {
        when (errorResponse.status) {
            401 -> showCustomToast("카카오 로그인에 실패하였습니다.") //카카오 액세스 토큰이 유효하지 x
            else -> showCustomToast(errorResponse.message)
        }
    }

    override fun kakaoTokenFailure(message: Throwable?) {
        showCustomToast(message.toString())
    }

    private fun tryGetProfile() {
        val loginService = LoginService(this)
        loginService.getProfile()
    }


    override fun getProfileSuccess(getProfileResponse: GetProfileResponse) {
        when (getProfileResponse.status) {
            200 -> {
                //프로필 조회 성공, 기존 회원
                prefs.setInt(GlobalApplication.USER_ID, getProfileResponse.data.id)
                prefs.setString(GlobalApplication.USER_NICKNAME, getProfileResponse.data.nickname)
                prefs.setInt(GlobalApplication.USER_GENDER, getProfileResponse.data.gender)
                if (getProfileResponse.data.university != null) {
                    prefs.setString(
                        GlobalApplication.UNIV_NAME,
                        getProfileResponse.data.university!!.univ
                    )
                    prefs.setString(
                        GlobalApplication.UNIV_MAJOR,
                        getProfileResponse.data.university!!.major
                    )
                    prefs.setInt(
                        GlobalApplication.UNIV_GRADE,
                        getProfileResponse.data.university!!.grade
                    )
                    prefs.setInt(
                        GlobalApplication.UNIV_NUM,
                        getProfileResponse.data.university!!.univNum-2000
                    )
                }
                navController.navigate(R.id.action_loginFrag_to_mainFrag)
            }
            else -> showCustomToast(getProfileResponse.message)
        }
    }

    override fun getProfileError(errorResponse: ErrorResponse) {
        when (errorResponse.status) {
            401 -> tryPostRefreshToken { tryGetProfile() }
            404 -> {
                //프로필 존재x, 회원가입 화면으로 넘기기
                navController.navigate(R.id.action_loginFrag_to_signupFrag) //신규회원
            }
            else -> showCustomToast(errorResponse.message)
        }
    }

    override fun getProfileFailure(message: Throwable?) {
        showCustomToast(message.toString())
    }

    private fun makeHyperlink() {
        //TextView 밑줄 긋기
        val spannableString1 =
            SpannableString("서비스 이용약관") //나중에 string resource 파일로 바꾸기 getString(resId)
        spannableString1.setSpan(UnderlineSpan(), 0, spannableString1.length, 0)
        binding.tvCondition1.text = spannableString1

        val spannableString2 =
            SpannableString("개인정보 처리방침")
        spannableString2.setSpan(UnderlineSpan(), 0, spannableString2.length, 0)
        binding.tvCondition2.text = spannableString2

        val mTransform = Linkify.TransformFilter { match, url -> "" }

        val pattern1 = Pattern.compile("서비스 이용약관")
        val pattern2 = Pattern.compile("개인정보 처리방침")

        Linkify.addLinks(
            binding.tvCondition1,
            pattern1,
            "https://www.teampang.app/term-of-service",
            null,
            mTransform
        )
        Linkify.addLinks(
            binding.tvCondition2,
            pattern2,
            "https://www.teampang.app/privacy-policy",
            null,
            mTransform
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}