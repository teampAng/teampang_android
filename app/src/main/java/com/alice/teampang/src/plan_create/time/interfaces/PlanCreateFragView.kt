package com.alice.teampang.src.plan_create.time.interfaces

import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.plan_create.time.model.PlanCreateResponse

interface PlanCreateFragView {

    fun PlanCreateSuccess(PlanCreateResponse: PlanCreateResponse)
    fun PlanCreateError(errorResponse: ErrorResponse)
    fun PlanCreateFailure(message: Throwable?)

}