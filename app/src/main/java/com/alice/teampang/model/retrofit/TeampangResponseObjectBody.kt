package com.alice.teampang.model.retrofit

// 인터페이스 기본 구조
// code : 인터페이스 네트워크 성공여부
// message : 오류 메시지
// data : 인터페이스 호출 결과 (string인 경우)
data class TeampangResponseObjectBody (
    var code :String,
    var message :String,
    var data : String
)