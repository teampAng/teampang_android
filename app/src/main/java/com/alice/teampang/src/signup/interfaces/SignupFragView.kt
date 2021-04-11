package com.alice.teampang.src.signup.interfaces

import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.signup.model.*

interface SignupFragView {

    fun signUpSuccess(signUpResponse: SignUpResponse)
    fun signUpError(errorResponse: ErrorResponse)
    fun signUpFailure(message: Throwable?)
}