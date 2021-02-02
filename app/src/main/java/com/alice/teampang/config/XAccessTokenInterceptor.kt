package com.alice.teampang.config

import com.alice.teampang.src.GlobalApplication.Companion.X_ACCESS_TOKEN
import com.alice.teampang.src.GlobalApplication.Companion.prefs
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class XAccessTokenInterceptor : Interceptor {
    @kotlin.jvm.Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val jwtToken: String? = prefs.getString(X_ACCESS_TOKEN, null)
        if (jwtToken != null) {
            builder.addHeader("X-ACCESS-TOKEN", jwtToken)
        }
        return chain.proceed(builder.build())
    }
}