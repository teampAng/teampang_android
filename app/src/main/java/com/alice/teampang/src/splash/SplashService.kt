package com.alice.teampang.src.splash

import com.alice.teampang.retrofit.RetrofitHelper
import com.alice.teampang.src.error.ErrorUtils
import com.alice.teampang.model.GetProfileResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashService(splashFragView: SplashFragView) {
    val mSplashFragView: SplashFragView = splashFragView

    fun getProfile() {
        RetrofitHelper.RetrofitForInterface().getProfile().enqueue(object :
            Callback<GetProfileResponse?> {
            override fun onResponse(
                call: Call<GetProfileResponse?>,
                response: Response<GetProfileResponse?>
            ) {
                val getProfileResponse: GetProfileResponse? = response.body()
                val error: ResponseBody? = response.errorBody()
                if (getProfileResponse == null) {
                    if (error != null) mSplashFragView.getProfileError(ErrorUtils.parseError(error))
                    else mSplashFragView.getProfileFailure(null)
                    return
                }
                mSplashFragView.getProfileSuccess(getProfileResponse)
            }

            override fun onFailure(call: Call<GetProfileResponse?>, t: Throwable) {
                mSplashFragView.getProfileFailure(t)
            }
        })
    }


}