package com.alice.teampang.src.my_schedule

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.databinding.MyScheduleTimeListBinding
import com.alice.teampang.src.my_schedule.model.Times

class MyScheduleTimeAdapter (private val context : Context) : RecyclerView.Adapter<MyScheduleTimeAdapter.ProfileVH>() {

    var data = listOf<Times>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileVH {
        val binding = MyScheduleTimeListBinding.inflate(
            LayoutInflater.from(context), parent, false
        )

        return ProfileVH(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ProfileVH, position: Int) {
        holder.onBind(data[position])
    }

    class ProfileVH(val binding: MyScheduleTimeListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Times) {
            binding.times = data
        }
    }
}