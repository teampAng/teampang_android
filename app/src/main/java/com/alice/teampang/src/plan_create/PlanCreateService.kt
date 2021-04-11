package com.alice.teampang.src.plan_create

import com.alice.teampang.src.GlobalApplication.Companion.getRetrofit
import com.alice.teampang.src.error.ErrorUtils
import com.alice.teampang.src.plan_create.interfaces.PlanCreateFragView
import com.alice.teampang.src.plan_create.interfaces.PlanCreateRetrofitInterface
import com.alice.teampang.src.plan_create.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlanCreateService(planCreateFragView: PlanCreateFragView) {
    val mPlanCreateFragView: PlanCreateFragView = planCreateFragView

    fun postPlan(planBody: PlanBody) {
        val planCreateRetrofitInterface: PlanCreateRetrofitInterface = getRetrofit()!!.create(
            PlanCreateRetrofitInterface::class.java
        )
        planCreateRetrofitInterface.postPlan(planBody).enqueue(object :
            Callback<PlanResponse?> {
            override fun onResponse(
                call: Call<PlanResponse?>,
                response: Response<PlanResponse?>
            ) {
                val planResponse: PlanResponse? = response.body()
                val error: ResponseBody? = response.errorBody()
                if (planResponse == null) {
                    if (error != null) mPlanCreateFragView.postPlanError(ErrorUtils.parseError(error))
                    else mPlanCreateFragView.postPlanFailure(null)
                    return
                }
                mPlanCreateFragView.postPlanSuccess(planResponse)
            }

            override fun onFailure(call: Call<PlanResponse?>, t: Throwable) {
                mPlanCreateFragView.postPlanFailure(t)
            }
        })
    }
}



