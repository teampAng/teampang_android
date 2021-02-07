package com.alice.teampang.src.signup

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
import com.alice.teampang.databinding.FragSignupBinding
import com.alice.teampang.src.BaseFrag
import java.util.regex.Pattern

class SignupFrag: BaseFrag(), View.OnClickListener {

    lateinit var navController : NavController

    private var _binding: FragSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragSignupBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

    }

    override fun onClick(v: View) {
        when(v.id) {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}