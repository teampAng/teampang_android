package com.alice.teampang.src.my_schedule.edit

import com.alice.teampang.application.GlobalApplication.Companion.getRetrofit
import com.alice.teampang.model.MyScheduleBody
import com.alice.teampang.model.PostScheduleResponse
import com.alice.teampang.retrofit.RetrofitHelper
import com.alice.teampang.src.error.ErrorUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyScheduleEditService(myScheduleEditFragView: MyScheduleEditFragView) {
    val mMyScheduleEditFragView: MyScheduleEditFragView = myScheduleEditFragView

    fun postMySchedule(myScheduleBody: MyScheduleBody) {
        RetrofitHelper.RetrofitForInterface().postMySchedule(myScheduleBody).enqueue(object :
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
        RetrofitHelper.RetrofitForInterface().putMySchedule(id, myScheduleBody).enqueue(object :
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



