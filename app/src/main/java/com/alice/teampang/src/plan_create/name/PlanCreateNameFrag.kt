package com.alice.teampang.src.plan_create.name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanCreateNameBinding
import com.alice.teampang.src.BaseFrag

class PlanCreateNameFrag : BaseFrag(), View.OnClickListener {

    lateinit var navController : NavController

    private var _binding: FragPlanCreateNameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanCreateNameBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_back -> navController.popBackStack()
            R.id.btn_next -> navController.navigate(R.id.action_planCreateNameFrag_to_planCreateCalendarFrag)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}