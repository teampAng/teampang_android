package com.alice.teampang.src.plan_confirm.planshare

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanConfirmDateBinding
import com.alice.teampang.databinding.FragPlanConfirmFinalBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.plan_possible.selection.PlanPossibleSelectionFrag
import com.alice.teampang.ui.adapter.CustomAdapter


class Confirmfinal : BaseFrag(), View.OnClickListener {
    private var _binding: FragPlanConfirmFinalBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : FinalAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanConfirmFinalBinding.inflate(inflater, container, false)
        val view = binding.root
        val mValue1 = arguments?.getStringArrayList("strings3")
        val mValue2 = arguments?.getString("json")
        Log.i(TAG3, "mValue1 = $mValue1")
        Log.i(TAG3, "mValue2 = $mValue2")
      //  Log.i(TAG3, "mValue2 = $mValue2")
//        val adapter = FinalAdapter()
//        //maindata = adapter.mList
//        view.findViewById<RecyclerView>(R.id.final_recyclerview).adapter = adapter
//        view.findViewById<RecyclerView>(R.id.final_recyclerview).layoutManager =
//            LinearLayoutManager(
//                context, LinearLayoutManager.VERTICAL,false
//            )
        binding.btnBack.setOnClickListener{
            navController.popBackStack()
        }
        return view
    }

    override fun onClick(v: View?) {

    }

    companion object {
        const val TAG3 = "Confirmfinal"
    }

//    private fun abcde(){
//        mValue1?.get(0)
//    }
}
/*
id: 1
confirmed_times : [
   {
    start_datetime: "2021-03-12 14:00"
    end_datetime: "2021-03-12 15:00"
   },
{
    start_datetime: "2021-03-12 17:00"
    end_datetime: "2021-03-12 18:00"
   },
   {
    start_datetime: "2021-03-13 15:00"
    end_datetime: "2021-03-13 18:00"
   }
]

 */