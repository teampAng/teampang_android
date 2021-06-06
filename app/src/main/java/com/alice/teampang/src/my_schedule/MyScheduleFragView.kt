package com.alice.teampang.src.my_schedule

import com.alice.teampang.model.MyScheduleResponse
import com.alice.teampang.src.error.model.ErrorResponse

interface MyScheduleFragView {

    fun myScheduleSuccess(myScheduleResponse: MyScheduleResponse)
    fun myScheduleError(errorResponse: ErrorResponse)
    fun myScheduleFailure(message: Throwable?)

//    fun kakaoTokenSuccess(kakaoTokenResponse: KakaoTokenResponse)
//    fun kakaoTokenError(errorResponse: ErrorResponse)
//    fun kakaoTokenFailure(message: Throwable?)

}