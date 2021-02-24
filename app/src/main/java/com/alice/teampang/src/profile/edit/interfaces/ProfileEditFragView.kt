package com.alice.teampang.src.profile.interfaces

import com.alice.teampang.src.profile.model.*

interface ProfileEditFragView {

    fun patchProfileSuccess(patchProfileResponse: PatchProfileResponse)

    fun patchProfileFailure(message: Throwable?)
}