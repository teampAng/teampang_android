package com.alice.teampang.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.R
import com.alice.teampang.model.Schedule
import com.alice.teampang.ui.adapter.CustomAdapter


private lateinit var myContext: Context
private var count = -1
class DateFragment : Fragment() {

    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_date, container, false)
        myContext = requireContext()
        val adapter = CustomAdapter()
        val data2 : MutableList<Schedule> = adapter.mList
        view.findViewById<RecyclerView>(R.id.recyclerview_main_list).adapter = adapter
        view.findViewById<RecyclerView>(R.id.recyclerview_main_list).layoutManager = LinearLayoutManager(myContext)

        view.findViewById<Button>(R.id.button_main_insert).setOnClickListener{

            count++

            val data = Schedule(
                count.toString() + "", "팀프앙 백엔드 스터디",
                "11 August2020$count"
            )
            //mArrayList.add(0, dict); //RecyclerView의 첫 줄에 삽입
            data2.add(data) // RecyclerView의 마지막 줄에 삽입

            adapter.notifyDataSetChanged()
        }
        return view
    }
}