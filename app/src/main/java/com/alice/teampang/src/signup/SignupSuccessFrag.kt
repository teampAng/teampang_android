package com.alice.teampang.src.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragSignupSuccessBinding
import com.alice.teampang.src.BaseFrag

class SignupSuccessFrag : BaseFrag(), View.OnClickListener {

    private var _binding: FragSignupSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragSignupSuccessBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.btnStart.setOnClickListener(this)
    }

    override fun onClick(v: View) {

        when(v) {
            binding.btnStart -> navController.navigate(R.id.action_signupSuccessFrag_to_mainFrag)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

