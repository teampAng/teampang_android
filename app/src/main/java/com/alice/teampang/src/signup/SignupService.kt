package com.alice.teampang.src.signup

import com.alice.teampang.src.GlobalApplication.Companion.getRetrofit
import com.alice.teampang.src.error.ErrorUtils
import com.alice.teampang.src.signup.interfaces.SignupFragView
import com.alice.teampang.src.signup.model.*
import com.alice.teampang.src.signup.interfaces.SignupRetrofitInterface
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupService(signupFragView: SignupFragView) {
    val mSignupFragView: SignupFragView = signupFragView

    fun postSignUp(signUpBody: SignUpBody) {
        val loginRetrofitInterface: SignupRetrofitInterface = getRetrofit()!!.create(
            SignupRetrofitInterface::class.java
        )
        loginRetrofitInterface.postSignUp(signUpBody)?.enqueue(object :
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



