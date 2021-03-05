package com.alice.teampang.src.setting.model

import com.google.gson.annotations.SerializedName

data class LogoutBody (@SerializedName("refresh") var refreshToken: String)
data class LogoutResponse (
    @SerializedName("status") var status: Int,
    @SerializedName("success") var success: Boolean,
    @SerializedName("message") var message: String
    )
