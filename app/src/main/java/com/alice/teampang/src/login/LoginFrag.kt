package com.alice.teampang.src.login

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragLoginBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.GlobalApplication.Companion.ACCESS_TOKEN
import com.alice.teampang.src.GlobalApplication.Companion.REFRESH_TOKEN
import com.alice.teampang.src.GlobalApplication.Companion.prefs
import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.login.interfaces.LoginFragView
import com.alice.teampang.src.login.model.KakaoTokenBody
import com.alice.teampang.src.login.model.KakaoTokenResponse
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.AuthType
import com.kakao.sdk.auth.model.OAuthToken
import java.util.regex.Pattern

class LoginFrag: BaseFrag(), LoginFragView, View.OnClickListener {

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
        when(v.id) {
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
                navController.navigate(R.id.action_loginFrag_to_signupFrag) //신규회원
                //리프레쉬토큰 만료된 회원은 프로필 get 으로 확인
            }
            401 -> {
                //카카오 액세스 토큰이 없거나 유효하지 않음
                showCustomToast("카카오 로그인에 실패하였습니다.")
            }
            else -> {
                //예상치 못한 서버 응답
                showCustomToast(kakaoTokenResponse.message)
            }
        }
    }

    override fun kakaoTokenFailure(message: Throwable?) {
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

        Linkify.addLinks(binding.tvCondition1, pattern1, "https://gun0912.tistory.com/66",null,mTransform)
        Linkify.addLinks(binding.tvCondition2, pattern2, "https://gun0912.tistory.com/66",null,mTransform)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}