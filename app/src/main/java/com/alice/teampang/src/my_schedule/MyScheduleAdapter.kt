package com.alice.teampang.src.my_schedule

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.databinding.MyScheduleListBinding
import com.alice.teampang.src.my_schedule.model.*


class MyScheduleAdapter(private val context: Context) :
    RecyclerView.Adapter<MyScheduleAdapter.ViewHolder>() {

    var data = arrayListOf<Data>()
    private val myScheduleTimeAdapter = MyScheduleTimeAdapter(context)

    interface DeliverListTimes {
        fun deliverListTimes(id: Int, name: String, timesList: ArrayList<Times>, position: Int)
    }

    private lateinit var deliverListTimes: DeliverListTimes

    fun setDeliverListTimes(deliverListTimes: DeliverListTimes) {
        this.deliverListTimes = deliverListTimes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyScheduleListBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        binding.rvTime.layoutManager = LinearLayoutManager(context)
        binding.rvTime.adapter = myScheduleTimeAdapter

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data[position].let { holder.onBind(it) }
        holder.binding.btnEdit.setOnClickListener {
            //position 에 따른 times 넘겨서 데이터 좌라락
            deliverListTimes.deliverListTimes(
                data[position].id,
                data[position].name,
                data[position].times,
                position
            )
        }
    }

    inner class ViewHolder(val binding: MyScheduleListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Data) {
            binding.schedule = data
            myScheduleTimeAdapter.data = data.times
            binding.rvTime.setHasFixedSize(false)
            binding.rvTime.layoutManager = LinearLayoutManager(context)
            binding.rvTime.adapter = myScheduleTimeAdapter
        }
    }
}
