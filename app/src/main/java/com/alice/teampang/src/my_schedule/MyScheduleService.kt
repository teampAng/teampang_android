package com.alice.teampang.src.my_schedule

import android.widget.Toast
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
                    if (error != null) mMyScheduleFragView.myScheduleError(
                        ErrorUtils.parseError(
                            error
                        )
                    )
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

    fun deleteMySchedule(id: Int) {
        val myScheduleRetrofitInterface: MyScheduleRetrofitInterface = getRetrofit()!!.create(
            MyScheduleRetrofitInterface::class.java
        )
        myScheduleRetrofitInterface.deleteMySchedule(id).enqueue(object :
            Callback<MyScheduleResponse?> {
            override fun onResponse(
                call: Call<MyScheduleResponse?>,
                response: Response<MyScheduleResponse?>
            ) {
                val error: ResponseBody? = response.errorBody()
                val code: Int = response.code()
                if (code == 204) {
                    mMyScheduleFragView.deleteScheduleSuccess()
                } else {
                    if (error != null) mMyScheduleFragView.deleteScheduleError(
                        ErrorUtils.parseError(error)
                    )
                    else mMyScheduleFragView.deleteScheduleFailure(null)
                    return
                }
            }

            override fun onFailure(call: Call<MyScheduleResponse?>, t: Throwable) {
                mMyScheduleFragView.deleteScheduleFailure(t)
            }
        })
    }
}



