package com.alice.teampang.src.plan_create.model

import com.google.gson.annotations.SerializedName

data class PlanBody(
    @SerializedName("name") var name: String,
    @SerializedName("start_date") var startDate: String,
    @SerializedName("end_date") var endDate: String,
    @SerializedName("start_time") var startTime: String,
    @SerializedName("end_time") var endTime: String
)
data class PlanResponse (
    @SerializedName("status") var status: Int,
    @SerializedName("success") var success: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("data") var data: Data
    )
data class Data (
    @SerializedName("id") var id: Int,
    @SerializedName("code") var inviteCode: String
    )
