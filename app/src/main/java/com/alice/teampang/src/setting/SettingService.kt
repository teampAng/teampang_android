package com.alice.teampang.src.setting

import com.alice.teampang.src.GlobalApplication.Companion.getRetrofit
import com.alice.teampang.src.error.ErrorUtils
import com.alice.teampang.src.login.interfaces.SettingRetrofitInterface
import com.alice.teampang.src.setting.interfaces.SettingFragView
import com.alice.teampang.src.setting.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingService(settingFragView: SettingFragView) {
    val mSettingFragView: SettingFragView = settingFragView

    fun postLogout(logoutBody: LogoutBody) {
        val settingRetrofitInterface: SettingRetrofitInterface = getRetrofit()!!.create(
            SettingRetrofitInterface::class.java
        )
        settingRetrofitInterface.postLogout(logoutBody).enqueue(object :
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



