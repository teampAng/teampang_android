package com.alice.teampang.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.R
import com.alice.teampang.Test
import com.alice.teampang.model.Schedule
import com.alice.teampang.src.HomeActivity2
import com.alice.teampang.src.plan_create.name.PlanCreateNameFrag
import com.alice.teampang.ui.adapter.CustomAdapter
import java.util.*


private lateinit var myContext: Context
private var count = -1

class DateFragment : Fragment() {

    fun newInstance(): DateFragment {
        return DateFragment()
    }


    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_date, container, false)
        myContext = requireContext()
        val adapter = CustomAdapter()
        val data2: MutableList<Schedule> = adapter.mList
        view.findViewById<RecyclerView>(R.id.recyclerview_main_list).adapter = adapter
        view.findViewById<RecyclerView>(R.id.recyclerview_main_list).layoutManager =
            LinearLayoutManager(
                myContext
            )
        val textview4 = view.findViewById<TextView>(R.id.date_text4)
        textview4.setText("11 August 2020")
        val textview5 = view.findViewById<TextView>(R.id.date_text5)
        textview5.setText("중앙대학교 팀플실 4")
        val textview6 = view.findViewById<TextView>(R.id.date_text6)
        textview6.setText("전체 팀원 알림 전송")

        view.findViewById<CardView>(R.id.button_main_insert2).setOnClickListener {
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


