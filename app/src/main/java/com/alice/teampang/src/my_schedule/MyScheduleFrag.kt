package com.alice.teampang.src.my_schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alice.teampang.R
import com.alice.teampang.databinding.FragMyScheduleBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.my_schedule.model.MyScheduleData
import com.alice.teampang.src.my_schedule.model.Times

class MyScheduleFrag : BaseFrag(), View.OnClickListener {

    lateinit var navController: NavController

    private var _binding: FragMyScheduleBinding? = null
    private val binding get() = _binding!!

    private lateinit var myScheduleAdapter: MyScheduleAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragMyScheduleBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        myScheduleAdapter = MyScheduleAdapter(requireContext())

        setRcvMySchedule()

        myScheduleAdapter.setDeliverListTimes(object : MyScheduleAdapter.DeliverListTimes {
            override fun deliverListTimes(name: String, timesList: ArrayList<Times>) {
                setFragmentResult("requestKey", bundleOf("times" to timesList, "name" to name))
//                val frag = MyScheduleEditFrag()
//                bundle.putSerializable("times", timesList)
//                frag.arguments = bundle
                navController.navigate(R.id.action_myScheduleFrag_to_myScheduleEditFrag)
            }
        })

        binding.btnBack.setOnClickListener(this)
        binding.btnEdit.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnBack -> navController.popBackStack()
            binding.btnEdit -> navController.navigate(R.id.action_myScheduleFrag_to_myScheduleEditFrag)
        }
    }

    fun Fragment.setFragmentResult(
        requestKey: String,
        result: Bundle
    ) = parentFragmentManager.setFragmentResult(requestKey, result)

    private fun setRcvMySchedule() {

        binding.rvSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSchedule.adapter = myScheduleAdapter
        val timesList: ArrayList<Times> = arrayListOf(
            Times("MON", "08:00", "09:00"),
            Times("MON", "08:00", "09:00"),
            Times("MON", "08:00", "09:00")
        )
        myScheduleAdapter.data = listOf(
            MyScheduleData(0, "알바", timesList),
            MyScheduleData(1, "학원", timesList)
        )
        myScheduleAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}