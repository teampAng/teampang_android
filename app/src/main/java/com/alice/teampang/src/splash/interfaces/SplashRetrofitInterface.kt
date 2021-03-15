package com.alice.teampang.src.splash.interfaces

import com.alice.teampang.src.splash.model.*
import retrofit2.Call
import retrofit2.http.GET

interface SplashRetrofitInterface {

    @GET("user/profiles/me")
    fun getProfile(): Call<GetProfileResponse>
}