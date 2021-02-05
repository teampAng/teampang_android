package com.alice.teampang.src.plan_create.time

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.databinding.TimeListBinding


class TimeAdapter(val DataList: MutableList<Int>) : RecyclerView.Adapter<TimeViewHolder>() {

    lateinit var binding: TimeListBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val binding = TimeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TimeViewHolder(binding)
    }


    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.time_number.text = DataList[position].toString()
    }

    override fun getItemCount(): Int = 49
}

// val 예약어로 바인딩을 전달 받아서 전역으로 사용합니다.
// 그리고 상속받는 ViewHolder 생성자에는 꼭 binding.root를 전달해야 합니다.
class TimeViewHolder(val binding: TimeListBinding) : RecyclerView.ViewHolder(binding.root) {
    val time_number = binding.tvTime
}