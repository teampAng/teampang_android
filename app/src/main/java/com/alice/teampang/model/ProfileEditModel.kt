package com.alice.teampang.model

import com.google.gson.annotations.SerializedName

data class PatchProfileBody (
    @SerializedName("nickname") var nickname: String?,
    @SerializedName("gender") var gender: Int?,
    @SerializedName("university") var university: University?
        )
data class PatchProfileResponse (
    @SerializedName("status") var status: Int,
    @SerializedName("success") var success: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("data") var data: ProfileData
    )
data class ProfileData (
    @SerializedName("id") var id: Int,
    @SerializedName("nickname") var nickname: String,
    @SerializedName("gender") var gender: Int,
    @SerializedName("profile_image") var profileImage: String?,
    @SerializedName("university") var university: ProfileUniversity?
)
data class ProfileUniversity (
    @SerializedName("name") var univ: String?,
    @SerializedName("admission") var univNum: Int?,
    @SerializedName("department") var major: String?,
    @SerializedName("grade") var grade: Int?
)