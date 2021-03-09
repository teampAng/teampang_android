package com.alice.teampang.src.my_schedule.model

import com.google.gson.annotations.SerializedName

data class MyScheduleBody(
    @SerializedName("name") var name: String,
    @SerializedName("schedule_times") var times: ArrayList<Times>
)

data class PostScheduleResponse(
    @SerializedName("status") var status: Int,
    @SerializedName("success") var success: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("data") var data: Data
)