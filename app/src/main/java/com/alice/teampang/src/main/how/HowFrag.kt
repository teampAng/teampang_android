package com.alice.teampang.src.main.how


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alice.teampang.R
import com.alice.teampang.src.BaseFrag

class HowFrag : BaseFrag() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_how, container,false)
        // 처리
        return view
    }
}

