package com.alice.teampang.src.splash

import com.alice.teampang.src.GlobalApplication.Companion.getRetrofit
import com.alice.teampang.src.splash.interfaces.SplashFragView
import com.alice.teampang.src.splash.interfaces.SplashRetrofitInterface
import com.alice.teampang.src.splash.model.GetProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashService(splashFragView: SplashFragView) {
    val mSplashFragView: SplashFragView = splashFragView

    fun getProfile() {
        val splashRetrofitInterface: SplashRetrofitInterface = getRetrofit()!!.create(
            SplashRetrofitInterface::class.java
        )
        splashRetrofitInterface.getProfile().enqueue(object :
            Callback<GetProfileResponse?> {
            override fun onResponse(
                call: Call<GetProfileResponse?>,
                response: Response<GetProfileResponse?>
            ) {
                val getProfileResponse: GetProfileResponse? = response.body()
                if (getProfileResponse == null) {
                    mSplashFragView.getProfileSuccess(null)
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