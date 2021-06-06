package com.alice.teampang.src.setting

import com.alice.teampang.model.LogoutResponse
import com.alice.teampang.src.error.model.ErrorResponse

interface SettingFragView {

    fun logoutSuccess(logoutResponse: LogoutResponse)
    fun logoutError(errorResponse: ErrorResponse)
    fun logoutFailure(message: Throwable?)

}