package com.alice.teampang.src.plan_possible.selection

data class Selection(val id : Int){
    // 개인 스케줄 여부
    var isPersonalSchedule = false

    // 개인 스케줄 타이틀
    var titleOfSchedule: String? = null

    // 팀장이 내려준 스케줄
    var isTeamSchedule = false

    // 팀장이 내려준 스케줄 중 개인 스케줄이 아닌 것
    var isAvailableTime = false

    // 사용자 체크 여부
    var availableChecked = false

    override fun toString(): String {
        return "$id:00"
    }
}