package com.alice.teampang.src.setting

import com.alice.teampang.model.LogoutBody
import com.alice.teampang.model.LogoutResponse
import com.alice.teampang.retrofit.RetrofitHelper
import com.alice.teampang.src.error.ErrorUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingService(settingFragView: SettingFragView) {
    val mSettingFragView: SettingFragView = settingFragView

    fun postLogout(logoutBody: LogoutBody) {
        RetrofitHelper.RetrofitForInterface().postLogout(logoutBody).enqueue(object :
            Callback<LogoutResponse?> {
            override fun onResponse(
                call: Call<LogoutResponse?>,
                response: Response<LogoutResponse?>
            ) {
                val logoutResponse: LogoutResponse? = response.body()
                val error: ResponseBody? = response.errorBody()
                if (logoutResponse == null) {
                    if (error != null) mSettingFragView.logoutError(ErrorUtils.parseError(error))
                    else mSettingFragView.logoutFailure(null)
                    return
                }
                mSettingFragView.logoutSuccess(logoutResponse)
            }

            override fun onFailure(call: Call<LogoutResponse?>, t: Throwable) {
                mSettingFragView.logoutFailure(t)
            }
        })
    }
}



