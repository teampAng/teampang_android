package com.alice.teampang.src.signup.interfaces

import com.alice.teampang.src.signup.model.*
import retrofit2.Call
import retrofit2.http.*

interface SignupRetrofitInterface {

    @POST("/user/profiles")
    fun postSignUp(@Body params: SignUpBody): Call<SignUpResponse>
}