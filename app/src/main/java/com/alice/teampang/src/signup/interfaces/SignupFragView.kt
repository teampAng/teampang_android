package com.alice.teampang.src.signup.interfaces

import com.alice.teampang.src.signup.model.*

interface SignupFragView {

    fun signUpSuccess(signUpResponse: SignUpResponse)

    fun signUpFailure(message: Throwable?)
}