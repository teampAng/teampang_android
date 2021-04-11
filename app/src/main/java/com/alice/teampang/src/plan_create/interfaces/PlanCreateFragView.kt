package com.alice.teampang.src.plan_create.interfaces

import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.plan_create.model.*

interface PlanCreateFragView {

    fun postPlanSuccess(planResponse: PlanResponse)
    fun postPlanError(errorResponse: ErrorResponse)
    fun postPlanFailure(message: Throwable?)

}