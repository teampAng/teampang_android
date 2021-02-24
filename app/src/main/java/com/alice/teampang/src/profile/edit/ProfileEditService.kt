package com.alice.teampang.src.profile.edit

import com.alice.teampang.src.GlobalApplication.Companion.getRetrofit
import com.alice.teampang.src.profile.interfaces.ProfileEditFragView
import com.alice.teampang.src.profile.interfaces.ProfileEditRetrofitInterface
import com.alice.teampang.src.profile.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileEditService (profileEditFragView: ProfileEditFragView) {
    val mProfileEditFragView: ProfileEditFragView = profileEditFragView

    fun patchProfile(userId: Int, patchProfileBody: PatchProfileBody) {
        val profileEditRetrofitInterface: ProfileEditRetrofitInterface = getRetrofit()!!.create(
            ProfileEditRetrofitInterface::class.java
        )
        profileEditRetrofitInterface.patchProfile(userId, patchProfileBody).enqueue(object :
            Callback<PatchProfileResponse?> {
            override fun onResponse(
                call: Call<PatchProfileResponse?>,
                response: Response<PatchProfileResponse?>
            ) {
                val userInfoResponse: PatchProfileResponse? = response.body()
                if (userInfoResponse == null) {
                    mProfileEditFragView.patchProfileFailure(null)
                    return
                }
                mProfileEditFragView.patchProfileSuccess(userInfoResponse)
            }

            override fun onFailure(call: Call<PatchProfileResponse?>, t: Throwable) {
                mProfileEditFragView.patchProfileFailure(t)
            }
        })
    }
}