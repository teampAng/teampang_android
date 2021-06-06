package com.alice.teampang.src.plan_confirm.planshare

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alice.teampang.databinding.FragPlanConfirmFinalBinding
import com.alice.teampang.base.BaseFrag
import org.json.JSONObject


class Confirmfinal : BaseFrag(), View.OnClickListener {
    private var _binding: FragPlanConfirmFinalBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):  View {
        _binding = FragPlanConfirmFinalBinding.inflate(inflater, container, false)
        val view = binding.root
        val json = JSONObject(arguments?.getString("data_string"))
        Log.d("json", "json_data = $json" )

        val adapter = FinalAdapter(requireContext(), json)

        binding.finalRecyclerview.adapter = adapter
        binding.finalRecyclerview.layoutManager =
            LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL,false
            )

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

}