package com.alice.teampang.src.my_schedule

import com.alice.teampang.src.GlobalApplication.Companion.getRetrofit
import com.alice.teampang.src.error.ErrorUtils
import com.alice.teampang.src.my_schedule.model.*
import com.alice.teampang.src.my_schedule.interfaces.MyScheduleFragView
import com.alice.teampang.src.my_schedule.interfaces.MyScheduleRetrofitInterface
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
}



