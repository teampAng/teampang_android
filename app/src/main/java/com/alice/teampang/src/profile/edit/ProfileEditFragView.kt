package com.alice.teampang.src.profile.edit

import com.alice.teampang.model.PatchProfileResponse
import com.alice.teampang.src.error.model.ErrorResponse

interface ProfileEditFragView {

    fun patchProfileSuccess(patchProfileResponse: PatchProfileResponse)
    fun patchProfileError(errorResponse: ErrorResponse)
    fun patchProfileFailure(message: Throwable?)
}