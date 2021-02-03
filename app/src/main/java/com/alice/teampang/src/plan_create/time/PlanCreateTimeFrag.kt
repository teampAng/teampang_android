package com.alice.teampang.src.plan_create.time

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.ViewCompat.canScrollHorizontally
import androidx.core.view.ViewCompat.canScrollVertically
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanCreateTimeBinding
import com.alice.teampang.src.BaseFrag

class PlanCreateTimeFrag : BaseFrag(), View.OnClickListener {

    lateinit var navController : NavController



    private var _binding: FragPlanCreateTimeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanCreateTimeBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val time_array = mutableListOf<Int>()

        for (i in 0..48) {
            if (i<25) { time_array.add(i, i) }
            else { time_array.add(i, i-24) }
        }



        binding.rv1.layoutManager = LinearLayoutManager(requireContext())
            .also { it.orientation = LinearLayoutManager.HORIZONTAL }
        binding.rv1.adapter = TimeBoxAdapter(time_array)
        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_back -> navController.popBackStack()
            R.id.btn_next -> navController.navigate(R.id.action_planCreateTimeFrag_to_planCreateShareFrag)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}