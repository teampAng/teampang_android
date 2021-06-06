package com.alice.teampang.retrofit

import com.alice.teampang.model.KakaoTokenBody
import com.alice.teampang.model.KakaoTokenResponse
import com.alice.teampang.model.WhenResponse
import com.alice.teampang.model.MyScheduleBody
import com.alice.teampang.model.MyScheduleResponse
import com.alice.teampang.model.PostScheduleResponse
import com.alice.teampang.src.plan_create.time.model.PlanCreateData
import com.alice.teampang.src.plan_create.time.model.PlanCreateResponse
import com.alice.teampang.model.PatchProfileBody
import com.alice.teampang.model.PatchProfileResponse
import com.alice.teampang.model.LogoutBody
import com.alice.teampang.model.LogoutResponse
import com.alice.teampang.model.SignUpBody
import com.alice.teampang.model.SignUpResponse
import com.alice.teampang.model.GetProfileResponse
import com.alice.teampang.model.RefreshTokenBody
import com.alice.teampang.model.TokenResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET("/meetings")
    fun getWhen(): Call<WhenResponse>

    @POST("/user/login/kakao")
    fun postKakoToken(@Body params: KakaoTokenBody): Call<KakaoTokenResponse>

    @GET("user/schedules")
    fun getMySchedule(): Call<MyScheduleResponse>

    @POST("/user/schedules")
    fun postMySchedule(@Body params: MyScheduleBody): Call<PostScheduleResponse>

    @PUT("/user/schedules/{id}")
    fun putMySchedule(@Path("id") id: Int, @Body params: MyScheduleBody): Call<PostScheduleResponse>

    @POST("/meetings")
    fun PostCreatePlan(@Body params: PlanCreateData): Call<PlanCreateResponse>

    @PATCH("/user/profiles/{id}")
    fun patchProfile(
        @Path("id") userId: Int,
        @Body params: PatchProfileBody
    )
            : Call<PatchProfileResponse>

    @POST("/user/logout")
    fun postLogout(@Body params: LogoutBody): Call<LogoutResponse>

    @POST("/user/profiles")
    fun postSignUp(@Body params: SignUpBody): Call<SignUpResponse>

    @GET("user/profiles/me")
    fun getProfile(): Call<GetProfileResponse>

    @POST("/user/token")
    fun postRefreshToken(@Body params: RefreshTokenBody): Call<TokenResponse>
}