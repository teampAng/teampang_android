package com.alice.teampang.src.main.`when`


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanCreateNameBinding

import com.alice.teampang.databinding.FragmentDateBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.ui.adapter.CustomAdapter

class WhenFrag :BaseFrag(),View.OnClickListener{

    private var _binding: FragmentDateBinding? = null

    private val binding get() = _binding!!
    private var count = -1

    private lateinit var myContext: Context
    private var adapter = CustomAdapter()
    private var data2 : MutableList<Schedule> = adapter.mList

    // MutableList<Schedule>

    @SuppressLint("CutPasteId")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDateBinding.inflate(inflater, container, false)
        val view = binding.root

        if (container != null) {
            myContext = container.context
        }

        settings()
        view.findViewById<RecyclerView>(R.id.recyclerview_main_list).adapter = adapter
        view.findViewById<RecyclerView>(R.id.recyclerview_main_list).layoutManager =
            LinearLayoutManager(
                myContext
            )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.btnMypage.setOnClickListener(this)
        binding.btnPlanCreate.setOnClickListener(this)

    }

    override fun onClick(v: View){
    when (v) {
        binding.btnMypage -> navController.navigate(R.id.action_mainFrag_to_myPageFrag)
        binding.btnPlanCreate -> {
            navController.navigate(R.id.action_mainFrag_to_planCreateNameFrag)
            //일정 생성 완료 후 돌아오는 화면에서 api를 쏘든가, 값 넘겨받아서 추가
//                count++
//                val data = Schedule(
//                    count.toString() + "", "팀프앙 백엔드 스터디",
//                    "11 August2020$count"
//                )
//                //mArrayList.add(0, dict); //RecyclerView의 첫 줄에 삽입
//                data2.add(data) // RecyclerView의 마지막 줄에 삽입
//                adapter.notifyDataSetChanged()
            }
        }
    }

    fun settings() {
        binding.dateText4.setText("11 August 2020")
        binding.dateText5.setText("중앙대학교 팀플실 4")
        binding.dateText6.setText("전체 팀원 알림 전송")
    }
}