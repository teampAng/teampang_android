package com.alice.teampang.model

import com.google.gson.annotations.SerializedName

data class KakaoTokenBody (@SerializedName("access_token") var accessToken: String)
data class KakaoTokenResponse (
    @SerializedName("status") var status: Int,
    @SerializedName("success") var success: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("data") var data: LoginData
    )
data class LoginData (
    @SerializedName("refresh") var refresh: String,
    @SerializedName("access") var access: String
    )
