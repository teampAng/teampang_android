package com.alice.teampang.src.profile.interfaces

import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.profile.model.*

interface ProfileEditFragView {

    fun patchProfileSuccess(patchProfileResponse: PatchProfileResponse)
    fun patchProfileError(errorResponse: ErrorResponse)
    fun patchProfileFailure(message: Throwable?)
}