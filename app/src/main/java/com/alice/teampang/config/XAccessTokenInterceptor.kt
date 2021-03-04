package com.alice.teampang.config

import com.alice.teampang.src.GlobalApplication.Companion.ACCESS_TOKEN
import com.alice.teampang.src.GlobalApplication.Companion.prefs
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class XAccessTokenInterceptor : Interceptor {
    @kotlin.jvm.Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val jwtToken: String? = prefs.getString(ACCESS_TOKEN, null)
        if (jwtToken != null) {
            val token = "Bearer $jwtToken"
            builder.addHeader("Authorization", token)
        }
        return chain.proceed(builder.build())
    }
}