package com.alice.teampang.src.my_schedule

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragLoginBinding
import com.alice.teampang.databinding.FragMyScheduleBinding
import com.alice.teampang.src.BaseFrag
import java.util.regex.Pattern

class MyScheduleFrag: BaseFrag(), View.OnClickListener {

    lateinit var navController : NavController

    private var _binding: FragMyScheduleBinding? = null
    private val binding get() = _binding!!

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

        binding.btnBack.setOnClickListener(this)
        binding.btnEdit.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_back -> navController.popBackStack()
            R.id.btn_edit -> navController.navigate(R.id.action_myScheduleFrag_to_myScheduleEditFrag)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}