package com.alice.teampang.src.my_schedule

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alice.teampang.R
import com.alice.teampang.databinding.FragMyScheduleBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.my_schedule.interfaces.MyScheduleFragView
import com.alice.teampang.src.my_schedule.model.*
import com.alice.teampang.src.my_schedule.model.Times
import com.alice.teampang.src.splash.SplashService

class MyScheduleFrag : BaseFrag(), MyScheduleFragView, View.OnClickListener {

    private var _binding: FragMyScheduleBinding? = null
    private val binding get() = _binding!!

    private var mId = 0
    private lateinit var mTimesList: ArrayList<Times>
    private lateinit var mName: String
    private var mPosition = 0

    private var dataList = ArrayList<Data>()
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

        tryMySchedule()

        myScheduleAdapter.setDeliverListTimes(object : MyScheduleAdapter.DeliverListTimes {
            override fun deliverListTimes(
                id: Int,
                name: String,
                timesList: ArrayList<Times>,
                position: Int
            ) {
                mId = id
                mTimesList = timesList
                mName = name
                mPosition = position
                editDialog()
            }
        })

        binding.btnBack.setOnClickListener(this)
        binding.btnAdd.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnBack -> navController.popBackStack()
            binding.btnAdd -> {
                val bundle = bundleOf("isAdd" to true)
                navController.navigate(R.id.action_myScheduleFrag_to_myScheduleEditFrag, bundle)
            }
        }
    }

    private fun Fragment.setFragmentResult(
        requestKey: String,
        result: Bundle
    ) = parentFragmentManager.setFragmentResult(requestKey, result)


    //--------------------------개인 일정 GET----------------------------
    private fun tryMySchedule() {
        val myScheduleService = MyScheduleService(this)
        myScheduleService.getMySchedule()
    }

    override fun myScheduleSuccess(myScheduleResponse: MyScheduleResponse) {
        dataList.clear()
        when (myScheduleResponse.status) {
            200 -> {
                if (myScheduleResponse.data != null) {
                    binding.rvSchedule.layoutManager = LinearLayoutManager(requireContext())
                    dataList = myScheduleResponse.data
                    myScheduleAdapter.data = dataList
                    binding.rvSchedule.adapter = myScheduleAdapter
                }
            }
            else -> showCustomToast(myScheduleResponse.message)
        }
    }

    override fun myScheduleError(errorResponse: ErrorResponse) {
        when (errorResponse.status) {
            401 -> tryPostRefreshToken { tryMySchedule() }
            else -> showCustomToast(errorResponse.message)
        }
    }

    override fun myScheduleFailure(message: Throwable?) {
        showCustomToast(message.toString())
    }

    //--------------------------개인 일정 DELETE----------------------------
    private fun tryDeleteSchedule() {
        val myScheduleService = MyScheduleService(this)
        myScheduleService.deleteMySchedule(mId)
    }

    override fun deleteScheduleSuccess() {
        dataList.removeAt(mPosition)
        myScheduleAdapter.notifyDataSetChanged()
    }

    override fun deleteScheduleError(errorResponse: ErrorResponse) {
        when (errorResponse.status) {
            401 -> tryPostRefreshToken { tryDeleteSchedule() }
            else -> showCustomToast(errorResponse.message)
        }
    }

    override fun deleteScheduleFailure(message: Throwable?) {
        showCustomToast("개인일정 삭제에 실패하였습니다")
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
            setFragmentResult("requestKey", bundleOf("id" to mId, "times" to mTimesList, "name" to mName))
            val bundle = bundleOf("isAdd" to false)
            navController.navigate(R.id.action_myScheduleFrag_to_myScheduleEditFrag, bundle)
        }

        btn_delete.setOnClickListener {
            tryDeleteSchedule()
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