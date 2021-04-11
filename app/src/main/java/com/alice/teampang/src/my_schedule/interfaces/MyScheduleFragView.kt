package com.alice.teampang.src.my_schedule.interfaces

import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.my_schedule.model.*

interface MyScheduleFragView {

    fun myScheduleSuccess(myScheduleResponse: MyScheduleResponse)
    fun myScheduleError(errorResponse: ErrorResponse)
    fun myScheduleFailure(message: Throwable?)

    fun deleteScheduleSuccess()
    fun deleteScheduleError(errorResponse: ErrorResponse)
    fun deleteScheduleFailure(message: Throwable?)

}