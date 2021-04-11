package com.alice.teampang.src.error

import com.alice.teampang.src.error.model.ErrorResponse
import com.google.gson.Gson
import okhttp3.ResponseBody


object ErrorUtils {
    fun parseError(response: ResponseBody?): ErrorResponse {
        val gson = Gson()
        return gson.fromJson(
            response!!.charStream(),
            ErrorResponse::class.java)
    }
}
