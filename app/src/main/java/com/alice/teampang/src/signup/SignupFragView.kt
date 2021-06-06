package com.alice.teampang.src.signup

import com.alice.teampang.model.SignUpResponse
import com.alice.teampang.src.error.model.ErrorResponse

interface SignupFragView {

    fun signUpSuccess(signUpResponse: SignUpResponse)
    fun signUpError(errorResponse: ErrorResponse)
    fun signUpFailure(message: Throwable?)
}