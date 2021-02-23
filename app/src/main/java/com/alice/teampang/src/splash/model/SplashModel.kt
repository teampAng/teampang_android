package com.alice.teampang.src.splash.model

import com.google.gson.annotations.SerializedName

data class GetProfileResponse(
    @SerializedName("status") var status: Int,
    @SerializedName("success") var success: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("data") var data: Data
)
data class Data (
    @SerializedName("id") var id: Int,
    @SerializedName("nickname") var nickname: String,
    @SerializedName("gender") var gender: Int,
    @SerializedName("profile_image") var profileImage: String?,
    @SerializedName("university") var university: University?
)
data class University (
    @SerializedName("name") var univ: String,
    @SerializedName("admission") var univNum: Int,
    @SerializedName("department") var major: String,
    @SerializedName("grade") var grade: Int
)