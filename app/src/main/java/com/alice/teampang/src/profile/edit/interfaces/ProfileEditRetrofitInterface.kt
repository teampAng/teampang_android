package com.alice.teampang.src.profile.interfaces

import com.alice.teampang.src.profile.model.*
import retrofit2.Call
import retrofit2.http.*

interface ProfileEditRetrofitInterface {

    @PATCH("/user/profiles/{id}")
    fun patchProfile(
        @Path("id") userId: Int,
        @Body params: PatchProfileBody
    )
    : Call<PatchProfileResponse>
}