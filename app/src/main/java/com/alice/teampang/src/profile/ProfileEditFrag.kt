package com.alice.teampang.src.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.databinding.FragProfileEditBinding
import com.alice.teampang.src.BaseFrag

class ProfileEditFrag : BaseFrag(), View.OnClickListener {

    lateinit var navController : NavController

    private var _binding: FragProfileEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragProfileEditBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

    }

    override fun onClick(v: View) {
        when(v) {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

