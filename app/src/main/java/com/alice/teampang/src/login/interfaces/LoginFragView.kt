package com.alice.teampang.src.login.interfaces

import com.alice.teampang.src.login.model.*
import com.alice.teampang.src.splash.model.GetProfileResponse

interface LoginFragView {

    fun kakaoTokenSuccess(kakaoTokenResponse: KakaoTokenResponse)

    fun kakaoTokenFailure(message: Throwable?)

    fun getProfileSuccess(getProfileResponse: GetProfileResponse)

    fun getProfileFailure(message: Throwable?)
}