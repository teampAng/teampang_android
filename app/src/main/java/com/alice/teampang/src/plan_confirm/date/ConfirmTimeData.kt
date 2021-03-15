package com.alice.teampang.src.plan_confirm.date

data class ConfirmTimeData(val date: String, val time: String, val unavailable_member: String?) {

    fun getStartTime(): Int {
        val time = time.substringBefore(":")
        return time.toInt()
    }
}