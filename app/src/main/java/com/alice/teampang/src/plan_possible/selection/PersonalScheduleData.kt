package com.alice.teampang.src.plan_possible.selection

data class PersonalScheduleData(val day : String, val scheduleName : String, val startTime : String, val endTime : String) {
    fun getStartTime(): Int {
        val time = startTime.substringBefore(":")
        return time.toInt()
    }

    fun getEndTime(): Int {
        val time = endTime.substringBefore(":")
        return time.toInt()
    }
}