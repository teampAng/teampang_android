package com.alice.teampang.token

import com.alice.teampang.model.TokenResponse
import com.alice.teampang.src.error.model.*

interface TokenFragView {

    fun tokenSuccess(tokenResponse: TokenResponse)
    fun tokenError(errorResponse: ErrorResponse)
    fun tokenFailure(message: Throwable?)
}