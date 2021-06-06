package com.alice.teampang.src.plan_create.time

import com.alice.teampang.application.GlobalApplication
import com.alice.teampang.retrofit.RetrofitHelper
import com.alice.teampang.src.error.ErrorUtils
import com.alice.teampang.src.plan_create.time.interfaces.PlanCreateFragView
import com.alice.teampang.src.plan_create.time.model.PlanCreateData
import com.alice.teampang.src.plan_create.time.model.PlanCreateResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlanCreateService(planCreateTimeFrag : PlanCreateFragView ) {

    private val mPlanCreateView : PlanCreateFragView = planCreateTimeFrag

    fun PostPlanCreate(PlanCreateData: PlanCreateData) {

        RetrofitHelper.RetrofitForInterface().PostCreatePlan(PlanCreateData).enqueue(object : Callback<PlanCreateResponse>{
            override fun onResponse(call: Call<PlanCreateResponse>, response: Response<PlanCreateResponse>) {
                val PlanCreateResponse: PlanCreateResponse? = response.body()
                val error: ResponseBody? = response.errorBody()
                if (PlanCreateResponse == null) {
                    if (error != null) mPlanCreateView.PlanCreateError(ErrorUtils.parseError(error))
                    else mPlanCreateView.PlanCreateFailure(null)
                    return
                }
                mPlanCreateView.PlanCreateSuccess(PlanCreateResponse)
            }

            override fun onFailure(call: Call<PlanCreateResponse>, t: Throwable) {
                mPlanCreateView.PlanCreateFailure(t)
            }
        }
        )}
    }




