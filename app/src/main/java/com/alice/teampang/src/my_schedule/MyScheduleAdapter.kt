package com.alice.teampang.src.my_schedule

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.R
import com.alice.teampang.databinding.FragMyScheduleBinding
import com.alice.teampang.databinding.MyScheduleListBinding
import com.alice.teampang.src.my_schedule.model.MyScheduleData
import com.alice.teampang.src.my_schedule.model.Times
import com.google.gson.Gson


class MyScheduleAdapter (private val context : Context) : RecyclerView.Adapter<MyScheduleAdapter.ProfileVH>() {

    var data = listOf<MyScheduleData>()
    private val myScheduleTimeAdapter = MyScheduleTimeAdapter(context)

    interface DeliverListTimes {
        fun deliverListTimes(name: String, timesList: ArrayList<Times>, position: Int)
    }

    private lateinit var deliverListTimes: DeliverListTimes

    fun setDeliverListTimes(deliverListTimes: DeliverListTimes) {
        this.deliverListTimes = deliverListTimes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileVH {
        val binding = MyScheduleListBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ProfileVH(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ProfileVH, position: Int) {
        holder.onBind(data[position])
        holder.binding.rvTime.layoutManager = LinearLayoutManager(context)
        holder.binding.rvTime.adapter = myScheduleTimeAdapter
        myScheduleTimeAdapter.data = data[position].times
        myScheduleTimeAdapter.notifyDataSetChanged()
        holder.binding.btnEdit.setOnClickListener {
            //position에 따른 times 넘겨서 데이터 좌라락
            deliverListTimes.deliverListTimes(data[position].name, data[position].times, position)
        }
    }

    class ProfileVH(val binding: MyScheduleListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: MyScheduleData) {
            binding.schedule = data
        }
    }


}
