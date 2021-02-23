package com.alice.teampang.src.login.interfaces

import com.alice.teampang.src.login.model.*
import retrofit2.Call
import retrofit2.http.*

interface LoginRetrofitInterface {

    @POST("/user/login/kakao")
    fun postKakoToken(@Body params: KakaoTokenBody): Call<KakaoTokenResponse>
}