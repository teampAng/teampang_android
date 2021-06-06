package com.alice.teampang.src.signup

import com.alice.teampang.model.SignUpBody
import com.alice.teampang.model.SignUpResponse
import com.alice.teampang.retrofit.RetrofitHelper
import com.alice.teampang.src.error.ErrorUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupService(signupFragView: SignupFragView) {
    val mSignupFragView: SignupFragView = signupFragView

    fun postSignUp(signUpBody: SignUpBody) {
        RetrofitHelper.RetrofitForInterface().postSignUp(signUpBody)?.enqueue(object :
            Callback<SignUpResponse?> {
            override fun onResponse(
                call: Call<SignUpResponse?>,
                response: Response<SignUpResponse?>
            ) {
                val signUpResponse: SignUpResponse? = response.body()
                val error: ResponseBody? = response.errorBody()
                if (signUpResponse == null) {
                    if (error != null) mSignupFragView.signUpError(ErrorUtils.parseError(error))
                    else mSignupFragView.signUpFailure(null)
                    return
                }
                mSignupFragView.signUpSuccess(signUpResponse)
            }

            override fun onFailure(call: Call<SignUpResponse?>, t: Throwable) {
                mSignupFragView.signUpFailure(t)
            }
        })
    }


}



