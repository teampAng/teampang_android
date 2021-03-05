package com.alice.teampang.src.splash.interfaces

import com.alice.teampang.src.error.model.*
import com.alice.teampang.src.splash.model.*

interface SplashFragView {

    fun getProfileSuccess(getProfileResponse: GetProfileResponse)
    fun getProfileError(errorResponse: ErrorResponse)
    fun getProfileFailure(message: Throwable?)

}