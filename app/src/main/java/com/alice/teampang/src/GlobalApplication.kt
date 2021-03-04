package com.alice.teampang.src

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.preference.PreferenceManager
import com.alice.teampang.R
import com.alice.teampang.config.XAccessTokenInterceptor
import com.google.gson.GsonBuilder
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.link.LinkClient
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit


class GlobalApplication : Application() {

    companion object {
        var MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=uft-8")
        var MEDIA_TYPE_JPEG = MediaType.parse("image/jpeg")

        // 서버 주소
        val BASE_URL = "http://ec2-3-36-74-8.ap-northeast-2.compute.amazonaws.com:80"

        lateinit var prefs: PreferenceUtil

        // SharedPreferences 키 값
        val TAG = "TEMPLATE_APP"

        // JWT Token 값
        const val ACCESS_TOKEN = "ACCESS-TOKEN"
        const val REFRESH_TOKEN = "REFRESH-TOKEN"

        // USER PROFILE 키 값
        const val USER_ID = "USER-ID"
        const val USER_NICKNAME = "USER-NICKNAME "
        const val USER_GENDER = "USER-GENDER"
        const val UNIV_NAME = "UNIV-NAME"
        const val UNIV_MAJOR = "UNIV-MAJOR"
        const val UNIV_GRADE = "UNIV-GRADE"
        const val UNIV_NUM = "UNIV-NUM"

        //날짜 형식
        var df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        private var retrofit: Retrofit? = null

        @JvmName("getRetrofit")
        fun getRetrofit(): Retrofit? {

            if (retrofit == null) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                Log.d(TAG, "initMyAPI : $BASE_URL")
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                val client = OkHttpClient.Builder()
                    .readTimeout(10000, TimeUnit.MILLISECONDS)
                    .connectTimeout(10000, TimeUnit.MILLISECONDS)
                    .writeTimeout(10000, TimeUnit.MILLISECONDS)
                    .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
                    .addInterceptor(loggingInterceptor) //for test
                    .build()
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit
        }

        fun kakaoLink(
            templateId: Long,
            userName: String,
            timeDay: String,
            timeHour: String,
            timeMin: String,
            v: View?
        ) {
            val tag = "link"
            val templateArgs = mapOf(
                "user_name" to userName,
                "time_day" to timeDay,
                "time_hour" to timeHour,
                "time_min" to timeMin
            )

            LinkClient.instance.customTemplate(
                v?.context!!,
                templateId,
                templateArgs
            ) { linkResult, error ->
                if (error != null) {
                    Log.e(tag, "카카오링크 보내기 실패", error)
                } else if (linkResult != null) {
                    Log.d(tag, "카카오링크 보내기 성공 ${linkResult.intent}")
                    v.context.startActivity(linkResult.intent)

                    // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    Log.w(tag, "Warning Msg: ${linkResult.warningMsg}")
                    Log.w(tag, "Argument Msg: ${linkResult.argumentMsg}")
                }
            }
        }

    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, getString(R.string.kakao_key))
    }

    class PreferenceUtil(context: Context) {
        private val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        var editor: SharedPreferences.Editor = prefs.edit()

        fun getString(key: String, defValue: String?): String? {
            return prefs.getString(key, defValue)
        }

        fun setString(key: String, str: String?) {
            prefs.edit().putString(key, str).apply()
        }

        fun getInt(key: String, int: Int): Int {
            return prefs.getInt(key, int)
        }

        fun setInt(key: String, int: Int) {
            prefs.edit().putInt(key, int).apply()
        }

        fun getBoolean(key: String, boolean: Boolean): Boolean {
            return prefs.getBoolean(key, boolean)
        }

        fun setBoolean(key: String, boolean: Boolean) {
            prefs.edit().putBoolean(key, boolean).apply()
        }

        fun clear() {
            editor.clear().apply()
            Log.d("prefs", prefs.all.toString())
        }
    }

}