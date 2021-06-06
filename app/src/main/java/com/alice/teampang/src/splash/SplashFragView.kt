package com.alice.teampang.src.splash

import com.alice.teampang.model.GetProfileResponse
import com.alice.teampang.src.error.model.*

interface SplashFragView {

    fun getProfileSuccess(getProfileResponse: GetProfileResponse)
    fun getProfileError(errorResponse: ErrorResponse)
    fun getProfileFailure(message: Throwable?)

}