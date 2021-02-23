package com.alice.teampang.src.login.interfaces

import com.alice.teampang.src.login.model.*

interface LoginFragView {

    fun kakaoTokenSuccess(kakaoTokenResponse: KakaoTokenResponse)

    fun kakaoTokenFailure(message: Throwable?)
}