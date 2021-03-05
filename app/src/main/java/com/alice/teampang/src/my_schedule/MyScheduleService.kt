package com.alice.teampang.src.my_schedule

import com.alice.teampang.src.GlobalApplication.Companion.getRetrofit
import com.alice.teampang.src.error.ErrorUtils
import com.alice.teampang.src.login.interfaces.LoginFragView
import com.alice.teampang.src.login.interfaces.LoginRetrofitInterface
import com.alice.teampang.src.login.model.*
import com.alice.teampang.src.my_schedule.interfaces.MyScheduleFragView
import com.alice.teampang.src.my_schedule.interfaces.MyScheduleRetrofitInterface
import com.alice.teampang.src.my_schedule.model.MyScheduleResponse
import com.alice.teampang.src.splash.interfaces.SplashRetrofitInterface
import com.alice.teampang.src.splash.model.GetProfileResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyScheduleService(myScheduleFragView: MyScheduleFragView) {
    val mMyScheduleFragView: MyScheduleFragView = myScheduleFragView

    fun getMySchedule() {
        val myScheduleRetrofitInterface: MyScheduleRetrofitInterface = getRetrofit()!!.create(
            MyScheduleRetrofitInterface::class.java
        )
        myScheduleRetrofitInterface.getMySchedule().enqueue(object :
            Callback<MyScheduleResponse?> {
            override fun onResponse(
                call: Call<MyScheduleResponse?>,
                response: Response<MyScheduleResponse?>
            ) {
                val myScheduleResponse: MyScheduleResponse? = response.body()
                val error: ResponseBody? = response.errorBody()
                if (myScheduleResponse == null) {
                    if (error != null) mMyScheduleFragView.myScheduleError(ErrorUtils.parseError(error))
                    else mMyScheduleFragView.myScheduleFailure(null)
                    return
                }
                mMyScheduleFragView.myScheduleSuccess(myScheduleResponse)
            }

            override fun onFailure(call: Call<MyScheduleResponse?>, t: Throwable) {
                mMyScheduleFragView.myScheduleFailure(t)
            }
        })
    }

//    fun postKakaoToken(kakaoTokenBody: KakaoTokenBody) {
//        val myScheduleRetrofitInterface: MyScheduleRetrofitInterface = getRetrofit()!!.create(
//            MyScheduleRetrofitInterface::class.java
//        )
//        myScheduleRetrofitInterface.postKakoToken(kakaoTokenBody).enqueue(object :
//            Callback<KakaoTokenResponse?> {
//            override fun onResponse(
//                call: Call<KakaoTokenResponse?>,
//                response: Response<KakaoTokenResponse?>
//            ) {
//                val kakaoTokenResponse: KakaoTokenResponse? = response.body()
//                val error: ResponseBody? = response.errorBody()
//                if (kakaoTokenResponse == null) {
//                    if (error != null) mMyScheduleFragView.kakaoTokenError(ErrorUtils.parseError(error))
//                    else mMyScheduleFragView.kakaoTokenFailure(null)
//                    return
//                }
//                mMyScheduleFragView.kakaoTokenSuccess(kakaoTokenResponse)
//            }
//
//            override fun onFailure(call: Call<KakaoTokenResponse?>, t: Throwable) {
//                mMyScheduleFragView.kakaoTokenFailure(t)
//            }
//        })
//    }
}



