package com.alice.teampang.src.my_schedule.interfaces

import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.my_schedule.model.*

interface MyScheduleEditFragView {

    fun postMyScheduleSuccess(postScheduleResponse: PostScheduleResponse)
    fun postMyScheduleError(errorResponse: ErrorResponse)
    fun postMyScheduleFailure(message: Throwable?)

    fun putMyScheduleSuccess(putScheduleResponse: PostScheduleResponse)
    fun putMyScheduleError(errorResponse: ErrorResponse)
    fun putMyScheduleFailure(message: Throwable?)

}