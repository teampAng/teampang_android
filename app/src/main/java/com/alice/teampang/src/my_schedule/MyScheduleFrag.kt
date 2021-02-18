package com.alice.teampang.src.my_schedule

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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

    private lateinit var mTimesList: ArrayList<Times>
    private lateinit var mName: String
    private var mPosition = 0

    var dataList = ArrayList<MyScheduleData>()
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

        //일정 get api
        //성공하면 아래 코드
        setRcvMySchedule()

        myScheduleAdapter.setDeliverListTimes(object : MyScheduleAdapter.DeliverListTimes {
            override fun deliverListTimes(
                name: String,
                timesList: ArrayList<Times>,
                position: Int
            ) {
                mTimesList = timesList
                mName = name
                mPosition = position
                editDialog()
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
        dataList.clear() //api 엮으면 필요없을 수도
        binding.rvSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSchedule.adapter = myScheduleAdapter
        val timesList: ArrayList<Times> = arrayListOf(
            Times("MON", "08:00", "09:00"),
            Times("MON", "08:00", "09:00"),
            Times("MON", "08:00", "09:00")
        )

        dataList.add(MyScheduleData(0, "알바", timesList))
        dataList.add(MyScheduleData(1, "학원", timesList))
        myScheduleAdapter.data = dataList
        myScheduleAdapter.notifyDataSetChanged()
    }

    private fun editDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_my_schedule, null)
        val btn_edit = dialogView.findViewById<TextView>(R.id.btn_edit)
        val btn_delete = dialogView.findViewById<TextView>(R.id.btn_delete)
        val btn_pop = dialogView.findViewById<TextView>(R.id.btn_pop)
        val dialog: AlertDialog = builder.setView(dialogView).create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.show()

        btn_edit.setOnClickListener {
            dialog.dismiss()
            setFragmentResult("requestKey", bundleOf("times" to mTimesList, "name" to mName))
            navController.navigate(R.id.action_myScheduleFrag_to_myScheduleEditFrag)
        }

        btn_delete.setOnClickListener {
            //일정 삭제 api 엮기
            dataList.removeAt(mPosition)
            myScheduleAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }

        btn_pop.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}