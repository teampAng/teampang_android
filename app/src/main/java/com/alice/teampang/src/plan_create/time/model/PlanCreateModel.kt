package com.alice.teampang.src.plan_create.time.model

import com.alice.teampang.model.Data
import com.google.gson.annotations.SerializedName

data class PlanCreateResponse (
    @SerializedName("status") var status: Int,
    @SerializedName("success") var success: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("data") var data: Data
    )

data class PlanCreateData (
    @SerializedName("name") var name: String,
    @SerializedName("start_date") var start_date: String,
    @SerializedName("end_date") var end_date: String,
    @SerializedName("start_time") var start_time: String,
    @SerializedName("end_time") var end_time: String,
    )
