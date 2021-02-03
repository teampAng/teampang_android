package com.alice.teampang.src.plan_create.time

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.databinding.TimeboxListBinding


class TimeBoxAdapter(val DataList: MutableList<Int>) : RecyclerView.Adapter<TimeBoxViewHolder>() {

    lateinit var binding: TimeboxListBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeBoxViewHolder {
        val binding = TimeboxListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeBoxViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeBoxViewHolder, position: Int) {
        holder.time_number.text = DataList[position].toString()
    }

    override fun getItemCount(): Int = 49
}

class TimeBoxViewHolder(val binding: TimeboxListBinding) : RecyclerView.ViewHolder(binding.root) {
    val time_number = binding.tvTime
}