package com.alice.teampang.src.my_schedule

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.databinding.MyScheduleEditListBinding
import com.alice.teampang.src.my_schedule.model.Times

class MyScheduleEditAdapter (private val context : Context) : RecyclerView.Adapter<MyScheduleEditAdapter.ProfileVH>() {

    var data = arrayListOf<Times>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileVH {
        val binding = MyScheduleEditListBinding.inflate(
            LayoutInflater.from(context), parent, false
        )

        return ProfileVH(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ProfileVH, position: Int) {
        holder.onBind(data[position])
        holder.binding.btnDelete.setOnClickListener {
            //개인일정 삭제 api 쏘기
            data.removeAt(position)
            this.notifyDataSetChanged()
        }
    }

    class ProfileVH(val binding: MyScheduleEditListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Times) {
            binding.times = data
        }
    }
}