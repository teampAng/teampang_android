package com.alice.teampang.src.my_schedule

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.databinding.MyScheduleTimeListBinding
import com.alice.teampang.src.my_schedule.model.Times

class MyScheduleTimeAdapter (private val context : Context) : RecyclerView.Adapter<MyScheduleTimeAdapter.ViewHolder>() {

    var data = listOf<Times>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyScheduleTimeListBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.day.text = data[position].day
        holder.binding.time.text = "${data[position].startTime.substring(0,5)} - ${data[position].endTime.substring(0,5)}"

    }

    class ViewHolder(val binding: MyScheduleTimeListBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}