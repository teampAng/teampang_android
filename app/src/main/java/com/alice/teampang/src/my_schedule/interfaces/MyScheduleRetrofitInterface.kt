package com.alice.teampang.src.my_schedule.interfaces

import com.alice.teampang.src.my_schedule.model.*
import retrofit2.Call
import retrofit2.http.*

interface MyScheduleRetrofitInterface {

    @GET("user/schedules")
    fun getMySchedule(): Call<MyScheduleResponse>

    @POST("/user/schedules")
    fun postMySchedule(@Body params: MyScheduleBody): Call<PostScheduleResponse>

}