package com.alice.teampang.src.splash.interfaces

import com.alice.teampang.src.splash.model.*

interface SplashFragView {

    fun getProfileSuccess(getProfileResponse: GetProfileResponse?)

    fun getProfileFailure(message: Throwable?)
}