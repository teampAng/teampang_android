package com.alice.teampang.src.login.interfaces

import com.alice.teampang.src.setting.model.*
import retrofit2.Call
import retrofit2.http.*

interface SettingRetrofitInterface {

    @POST("/user/logout")
    fun postLogout(@Body params: LogoutBody): Call<LogoutResponse>
}