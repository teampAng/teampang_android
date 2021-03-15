package com.alice.teampang.src.setting.interfaces

import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.setting.model.*

interface SettingFragView {

    fun logoutSuccess(logoutResponse: LogoutResponse)
    fun logoutError(errorResponse: ErrorResponse)
    fun logoutFailure(message: Throwable?)

}