package com.alice.teampang.src.profile.edit

import com.alice.teampang.model.PatchProfileBody
import com.alice.teampang.model.PatchProfileResponse
import com.alice.teampang.retrofit.RetrofitHelper
import com.alice.teampang.src.error.ErrorUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileEditService (profileEditFragView: ProfileEditFragView) {
    val mProfileEditFragView: ProfileEditFragView = profileEditFragView

    fun patchProfile(userId: Int, patchProfileBody: PatchProfileBody) {
        RetrofitHelper.RetrofitForInterface().patchProfile(userId, patchProfileBody).enqueue(object :
            Callback<PatchProfileResponse?> {
            override fun onResponse(
                call: Call<PatchProfileResponse?>,
                response: Response<PatchProfileResponse?>
            ) {
                val userInfoResponse: PatchProfileResponse? = response.body()
                val error: ResponseBody? = response.errorBody()
                if (userInfoResponse == null) {
                    if (error != null) mProfileEditFragView.patchProfileError(ErrorUtils.parseError(error))
                    else mProfileEditFragView.patchProfileFailure(null)
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