package com.alice.teampang.src.my_schedule.edit

import com.alice.teampang.src.GlobalApplication.Companion.getRetrofit
import com.alice.teampang.src.error.ErrorUtils
import com.alice.teampang.src.my_schedule.interfaces.MyScheduleEditFragView
import com.alice.teampang.src.my_schedule.model.*
import com.alice.teampang.src.my_schedule.interfaces.MyScheduleFragView
import com.alice.teampang.src.my_schedule.interfaces.MyScheduleRetrofitInterface
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyScheduleEditService(myScheduleEditFragView: MyScheduleEditFragView) {
    val mMyScheduleEditFragView: MyScheduleEditFragView = myScheduleEditFragView

    fun postMySchedule(myScheduleBody: MyScheduleBody) {
        val myScheduleRetrofitInterface: MyScheduleRetrofitInterface = getRetrofit()!!.create(
            MyScheduleRetrofitInterface::class.java
        )
        myScheduleRetrofitInterface.postMySchedule(myScheduleBody).enqueue(object :
            Callback<PostScheduleResponse?> {
            override fun onResponse(
                call: Call<PostScheduleResponse?>,
                response: Response<PostScheduleResponse?>
            ) {
                val postScheduleResponse: PostScheduleResponse? = response.body()
                val error: ResponseBody? = response.errorBody()
                if (postScheduleResponse == null) {
                    if (error != null) mMyScheduleEditFragView.postMyScheduleError(ErrorUtils.parseError(error))
                    else mMyScheduleEditFragView.postMyScheduleFailure(null)
                    return
                }
                mMyScheduleEditFragView.postMyScheduleSuccess(postScheduleResponse)
            }

            override fun onFailure(call: Call<PostScheduleResponse?>, t: Throwable) {
                mMyScheduleEditFragView.postMyScheduleFailure(t)
            }
        })
    }

    fun putMySchedule(id: Int, myScheduleBody: MyScheduleBody) {
        val myScheduleRetrofitInterface: MyScheduleRetrofitInterface = getRetrofit()!!.create(
            MyScheduleRetrofitInterface::class.java
        )
        myScheduleRetrofitInterface.putMySchedule(id, myScheduleBody).enqueue(object :
            Callback<PostScheduleResponse?> {
            override fun onResponse(
                call: Call<PostScheduleResponse?>,
                response: Response<PostScheduleResponse?>
            ) {
                val putScheduleResponse: PostScheduleResponse? = response.body()
                val error: ResponseBody? = response.errorBody()
                if (putScheduleResponse == null) {
                    if (error != null) mMyScheduleEditFragView.putMyScheduleError(ErrorUtils.parseError(error))
                    else mMyScheduleEditFragView.putMyScheduleFailure(null)
                    return
                }
                mMyScheduleEditFragView.putMyScheduleSuccess(putScheduleResponse)
            }

            override fun onFailure(call: Call<PostScheduleResponse?>, t: Throwable) {
                mMyScheduleEditFragView.putMyScheduleFailure(t)
            }
        })
    }
}



