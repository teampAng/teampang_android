package com.alice.teampang.src.token.model

import com.google.gson.annotations.SerializedName

data class RefreshTokenBody (@SerializedName("refresh") var refresh: String)
data class TokenResponse (
    @SerializedName("status") var status: Int,
    @SerializedName("success") var success: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("data") var data: Data
    )
data class Data (
    @SerializedName("refresh") var refresh: String,
    @SerializedName("access") var access: String,
    @SerializedName("code") var code: String
    )