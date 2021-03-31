package com.alice.teampang.src.plan_create.share

import com.alice.teampang.src.GlobalApplication.Companion.getRetrofit
import com.alice.teampang.src.error.ErrorUtils
import com.alice.teampang.src.plan_create.interfaces.PlanCreateFragView
import com.alice.teampang.src.plan_create.interfaces.PlanCreateRetrofitInterface
import com.alice.teampang.src.plan_create.interfaces.PlanShareFragView
import com.alice.teampang.src.plan_create.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlanShareService(planShareFragView: PlanShareFragView) {
    val mPlanShareFragView: PlanShareFragView = planShareFragView

    fun getInviteCode(id: Int) {
        val planCreateRetrofitInterface: PlanCreateRetrofitInterface = getRetrofit()!!.create(
            PlanCreateRetrofitInterface::class.java
        )
        planCreateRetrofitInterface.getInviteCode(id).enqueue(object :
            Callback<PlanResponse?> {
            override fun onResponse(
                call: Call<PlanResponse?>,
                response: Response<PlanResponse?>
            ) {
                val planResponse: PlanResponse? = response.body()
                val error: ResponseBody? = response.errorBody()
                if (planResponse == null) {
                    if (error != null) mPlanShareFragView.getInviteCodeError(ErrorUtils.parseError(error))
                    else mPlanShareFragView.getInviteCodeFailure(null)
                    return
                }
                mPlanShareFragView.getInviteCodeSuccess(planResponse)
            }

            override fun onFailure(call: Call<PlanResponse?>, t: Throwable) {
                mPlanShareFragView.getInviteCodeFailure(t)
            }
        })
    }
}



