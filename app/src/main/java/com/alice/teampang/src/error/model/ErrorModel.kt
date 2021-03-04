package com.alice.teampang.src.error.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse (
    @SerializedName("status") var status: Int,
    @SerializedName("success") var success: Boolean,
    @SerializedName("message") var message: String
)