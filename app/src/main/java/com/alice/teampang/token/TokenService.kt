package com.alice.teampang.token

import com.alice.teampang.src.error.ErrorUtils
import com.alice.teampang.src.GlobalApplication.Companion.getRetrofit
import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.token.interfaces.TokenFragView
import com.alice.teampang.src.token.interfaces.TokenRetrofitInterface
import com.alice.teampang.src.token.model.RefreshTokenBody
import com.alice.teampang.src.token.model.TokenResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TokenService(tokenFragView: TokenFragView) {
    val mTokenFragView: TokenFragView = tokenFragView

    fun postRefreshToken(refreshTokenBody: RefreshTokenBody) {
        val tokenRetrofitInterface: TokenRetrofitInterface = getRetrofit()!!.create(
            TokenRetrofitInterface::class.java
        )
        tokenRetrofitInterface.postRefreshToken(refreshTokenBody).enqueue(object :
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



