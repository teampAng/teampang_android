package com.alice.teampang.token

import com.alice.teampang.src.error.ErrorUtils
import com.alice.teampang.retrofit.RetrofitHelper
import com.alice.teampang.model.RefreshTokenBody
import com.alice.teampang.model.TokenResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TokenService(tokenFragView: TokenFragView) {
    val mTokenFragView: TokenFragView = tokenFragView

    fun postRefreshToken(refreshTokenBody: RefreshTokenBody) {

        RetrofitHelper.RetrofitForInterface().postRefreshToken(refreshTokenBody).enqueue(object :
            Callback<TokenResponse?> {
            override fun onResponse(
                call: Call<TokenResponse?>,
                response: Response<TokenResponse?>
            ) {
                val tokenResponse: TokenResponse? = response.body()
                val error: ResponseBody? = response.errorBody()
                if (tokenResponse == null) {
                    if (error != null) mTokenFragView.tokenError(ErrorUtils.parseError(error))
                     else mTokenFragView.tokenFailure(null)
                    return
                }
                mTokenFragView.tokenSuccess(tokenResponse)
            }

            override fun onFailure(call: Call<TokenResponse?>, t: Throwable) {
                mTokenFragView.tokenFailure(t)
            }
        })
    }
}



