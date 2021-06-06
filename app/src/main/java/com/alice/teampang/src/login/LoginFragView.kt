package com.alice.teampang.src.login

import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.model.KakaoTokenResponse
import com.alice.teampang.model.GetProfileResponse

interface LoginFragView {

    fun kakaoTokenSuccess(kakaoTokenResponse: KakaoTokenResponse)
    fun kakaoTokenError(errorResponse: ErrorResponse)
    fun kakaoTokenFailure(message: Throwable?)

    fun getProfileSuccess(getProfileResponse: GetProfileResponse)
    fun getProfileError(errorResponse: ErrorResponse)
    fun getProfileFailure(message: Throwable?)

}