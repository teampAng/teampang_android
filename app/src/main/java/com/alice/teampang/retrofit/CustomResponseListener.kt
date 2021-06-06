package com.alice.teampang.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
// 인터페이스 호출 시, 가져올 interface
// 인터페이스 결과에 따라 호출한 UI에 결과를 보내준다.
interface CustomResponseListener<T> : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>)

    override fun onFailure(call: Call<T>, t: Throwable)

    fun onCustomFailed(call: Call<T>, response: Response<T>)

    fun onCustomSuccess(call: Call<T>, response: Response<T>)
}
