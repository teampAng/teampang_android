package com.alice.teampang.src.my_schedule.model

import com.google.gson.annotations.SerializedName

data class MyScheduleResponse(
    @SerializedName("status") var status: Int,
    @SerializedName("success") var success: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("data") var data: ArrayList<Data>
)

data class Data(
    @SerializedName("id") var id: Int,
    @SerializedName("schedule_times") var times: ArrayList<Times>,
    @SerializedName("name") var name: String
)

data class Times(
    @SerializedName("day") var day: String,
    @SerializedName("start_time") var startTime: String,
    @SerializedName("end_time") var endTime: String
)

