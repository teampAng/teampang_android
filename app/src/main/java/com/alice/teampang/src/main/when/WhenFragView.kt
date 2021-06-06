package com.alice.teampang.src.main.`when`

import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.model.WhenResponse

interface WhenFragView {

    fun WhenSuccess(whenResponse: WhenResponse)
    fun WhenError(errorResponse: ErrorResponse)
    fun WhenFailure(message: Throwable?)

}