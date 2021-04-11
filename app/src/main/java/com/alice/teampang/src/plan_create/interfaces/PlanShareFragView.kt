package com.alice.teampang.src.plan_create.interfaces

import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.plan_create.model.*

interface PlanShareFragView {

    fun getInviteCodeSuccess(getInviteCodeResponse: PlanResponse)
    fun getInviteCodeError(errorResponse: ErrorResponse)
    fun getInviteCodeFailure(message: Throwable?)

}