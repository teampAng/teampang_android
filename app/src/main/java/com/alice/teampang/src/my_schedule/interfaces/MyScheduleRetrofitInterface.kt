package com.alice.teampang.src.my_schedule.interfaces

import com.alice.teampang.src.my_schedule.model.*
import retrofit2.Call
import retrofit2.http.*

interface MyScheduleRetrofitInterface {

    @GET("user/schedules")
    fun getMySchedule(): Call<MyScheduleResponse>

    @DELETE("user/schedules/{id}")
    fun deleteMySchedule(@Path("id") id: Int): Call<MyScheduleResponse>

    @POST("/user/schedules")
    fun postMySchedule(@Body params: MyScheduleBody): Call<PostScheduleResponse>

    @PUT("/user/schedules/{id}")
    fun putMySchedule(@Path("id") id: Int, @Body params: MyScheduleBody): Call<PostScheduleResponse>
}