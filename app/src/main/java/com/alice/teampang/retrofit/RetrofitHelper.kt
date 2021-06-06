package com.alice.teampang.retrofit

import com.alice.teampang.BuildConfig
import com.alice.teampang.util.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// 레트로핏 객체 생성 클래스
object RetrofitHelper {

    private val ALL_TIMEOUT = 10L
    private var okHttpClient: OkHttpClient
    private var retrofit: Retrofit

    init {
        /**
         * 로깅 인터셉터 연결
         */
        val httpLogging = HttpLoggingInterceptor()
        httpLogging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        okHttpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLogging)
//            addInterceptor(HeaderSettingInterceptor())
//            connectTimeout(1, TimeUnit.MINUTES)
//            connectTimeout(30, TimeUnit.SECONDS)
            connectTimeout(ALL_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(ALL_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(ALL_TIMEOUT, TimeUnit.SECONDS)
        }.build()
        /**
         * Retrofit2 + OKHttp3를 연결한다
         */
        retrofit = Retrofit.Builder().apply {
            baseUrl(Const.BASE_URL)
            client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
        }.build()


    }
    fun RetrofitForInterface(): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }
}

