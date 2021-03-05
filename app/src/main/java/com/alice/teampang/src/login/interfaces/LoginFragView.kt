package com.alice.teampang.src.login.interfaces

import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.login.model.*
import com.alice.teampang.src.splash.model.GetProfileResponse

interface LoginFragView {

    fun kakaoTokenSuccess(kakaoTokenResponse: KakaoTokenResponse)
    fun kakaoTokenError(errorResponse: ErrorResponse)
    fun kakaoTokenFailure(message: Throwable?)

    fun getProfileSuccess(getProfileResponse: GetProfileResponse)
    fun getProfileError(errorResponse: ErrorResponse)
    fun getProfileFailure(message: Throwable?)

}