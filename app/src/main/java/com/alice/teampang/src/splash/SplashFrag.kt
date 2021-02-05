package com.alice.teampang.src.splash

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragSplashBinding
import com.alice.teampang.src.BaseFrag


class SplashFrag: BaseFrag() {

    lateinit var navController : NavController

    private var _binding: FragSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragSplashBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                navController.navigate(R.id.action_splashFrag_to_loginFrag)
            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

