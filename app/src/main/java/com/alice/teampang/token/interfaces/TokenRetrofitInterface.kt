package com.alice.teampang.src.token.interfaces

import com.alice.teampang.src.token.model.*
import retrofit2.Call
import retrofit2.http.*

interface TokenRetrofitInterface {

    @POST("/user/token")
    fun postRefreshToken(@Body params: RefreshTokenBody): Call<TokenResponse>
}