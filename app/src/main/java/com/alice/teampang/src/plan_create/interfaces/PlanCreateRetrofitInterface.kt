package com.alice.teampang.src.plan_create.interfaces

import com.alice.teampang.src.plan_create.model.*
import retrofit2.Call
import retrofit2.http.*

interface PlanCreateRetrofitInterface {

    @POST("/meetings")
    fun postPlan(@Body params: PlanBody): Call<PlanResponse>

    @GET("meetings/{id}/invite-code")
    fun getInviteCode(@Path("id") id: Int): Call<PlanResponse>
}