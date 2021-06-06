package com.alice.teampang.src.login

import com.alice.teampang.retrofit.RetrofitHelper
import com.alice.teampang.src.error.ErrorUtils
import com.alice.teampang.model.KakaoTokenBody
import com.alice.teampang.model.KakaoTokenResponse
import com.alice.teampang.model.GetProfileResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(loginFragView: LoginFragView) {
    val mLoginFragView: LoginFragView = loginFragView

    fun postKakaoToken(kakaoTokenBody: KakaoTokenBody) {
        RetrofitHelper.RetrofitForInterface().postKakoToken(kakaoTokenBody).enqueue(object :
            Callback<KakaoTokenResponse?> {
            override fun onResponse(
                call: Call<KakaoTokenResponse?>,
                response: Response<KakaoTokenResponse?>
            ) {
                val kakaoTokenResponse: KakaoTokenResponse? = response.body()
                val error: ResponseBody? = response.errorBody()
                if (kakaoTokenResponse == null) {
                    if (error != null) mLoginFragView.kakaoTokenError(ErrorUtils.parseError(error))
                    else mLoginFragView.kakaoTokenFailure(null)
                    return
                }
                mLoginFragView.kakaoTokenSuccess(kakaoTokenResponse)
            }

            override fun onFailure(call: Call<KakaoTokenResponse?>, t: Throwable) {
                mLoginFragView.kakaoTokenFailure(t)
            }
        })
    }

    fun getProfile() {
        RetrofitHelper.RetrofitForInterface().getProfile().enqueue(object :
            Callback<GetProfileResponse?> {
            override fun onResponse(
                call: Call<GetProfileResponse?>,
                response: Response<GetProfileResponse?>
            ) {
                val getProfileResponse: GetProfileResponse? = response.body()
                val error: ResponseBody? = response.errorBody()
                if (getProfileResponse == null) {
                    if (error != null) mLoginFragView.getProfileError(ErrorUtils.parseError(error))
                    else mLoginFragView.getProfileFailure(null)
                    return
                }
                mLoginFragView.getProfileSuccess(getProfileResponse)
            }

            override fun onFailure(call: Call<GetProfileResponse?>, t: Throwable) {
                mLoginFragView.getProfileFailure(t)
            }
        })
    }


}



