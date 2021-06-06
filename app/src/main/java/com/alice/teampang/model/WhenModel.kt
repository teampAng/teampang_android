package com.alice.teampang.model

import com.google.gson.annotations.SerializedName

data class WhenResponse (
    @SerializedName("status") var status: Int,
    @SerializedName("success") var success: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("data") var data: ArrayList<WhenData>
    )

data class WhenData (
    @SerializedName("count") var count : Int,               //일정 갯수
    @SerializedName("next") var next : String,              //페에지 다음 url
    @SerializedName("previous") var previous : String,      //페이지 이전 url
    @SerializedName("results") var results : ArrayList<Results>,
)

data class Results (
    @SerializedName("id") var id : Int,          //일정id
    @SerializedName("name") var name : String,   //일정 이름
    @SerializedName("confirmed_times") var confirmedTimes : ArrayList<ConfirmedTimes>
)                                                       //확정 시간 데이터

data class ConfirmedTimes(
    @SerializedName("id") var id : Int,     //확정 정보id
    @SerializedName("start_datetime") var startDateTime : String,    //확정 시작 시간
    @SerializedName("place") var place : String,   //확정 장소
    @SerializedName("link") var link : String,     //줌 링크
)
