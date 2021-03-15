package com.alice.teampang.src.plan_confirm.date

data class Confirm(val id : Int){
    // 안되는 사람 이름
    var titleOfSchedule: String? = null

    // 한명이 안되는 스케쥴
    var membernone = false
    // 모두가 되는 스케쥴
    var membernotnone = false

    var isAvailableTime = false

}