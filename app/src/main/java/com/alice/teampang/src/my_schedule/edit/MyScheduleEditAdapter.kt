package com.alice.teampang.src.my_schedule.edit

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.databinding.MyScheduleEditListBinding
import com.alice.teampang.src.my_schedule.model.Times

class MyScheduleEditAdapter (private val context : Context) : RecyclerView.Adapter<MyScheduleEditAdapter.ViewHolder>() {

    var data = arrayListOf<Times>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyScheduleEditListBinding.inflate(
            LayoutInflater.from(context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(data[position])
        holder.binding.btnDelete.setOnClickListener {
            data.removeAt(position)
            this.notifyItemRemoved(position)
        }
    }

    class ViewHolder(val binding: MyScheduleEditListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Times) {
            binding.times = data
        }
    }
}