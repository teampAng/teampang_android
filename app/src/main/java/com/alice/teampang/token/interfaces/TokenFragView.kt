package com.alice.teampang.src.token.interfaces

import com.alice.teampang.src.error.model.*
import com.alice.teampang.src.token.model.*

interface TokenFragView {

    fun tokenSuccess(tokenResponse: TokenResponse)
    fun tokenError(errorResponse: ErrorResponse)
    fun tokenFailure(message: Throwable?)
}