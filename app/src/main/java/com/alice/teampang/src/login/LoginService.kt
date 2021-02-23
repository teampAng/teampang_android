package com.alice.teampang.src.login

import com.alice.teampang.src.GlobalApplication.Companion.getRetrofit
import com.alice.teampang.src.login.interfaces.LoginFragView
import com.alice.teampang.src.login.interfaces.LoginRetrofitInterface
import com.alice.teampang.src.login.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(loginFragView: LoginFragView) {
    val mLoginFragView: LoginFragView = loginFragView

    fun postKakaoToken(kakaoTokenBody: KakaoTokenBody) {
        val loginRetrofitInterface: LoginRetrofitInterface = getRetrofit()!!.create(
            LoginRetrofitInterface::class.java
        )
        loginRetrofitInterface.postKakoToken(kakaoTokenBody)?.enqueue(object :
            Callback<KakaoTokenResponse?> {
            override fun onResponse(
                call: Call<KakaoTokenResponse?>,
                response: Response<KakaoTokenResponse?>
            ) {
                val kakaoTokenResponse: KakaoTokenResponse? = response.body()
                if (kakaoTokenResponse == null) {
                    mLoginFragView.kakaoTokenFailure(null)
                    return
                }
                mLoginFragView.kakaoTokenSuccess(kakaoTokenResponse)
            }

            override fun onFailure(call: Call<KakaoTokenResponse?>, t: Throwable) {
                mLoginFragView.kakaoTokenFailure(t)
            }
        })
    }


}



