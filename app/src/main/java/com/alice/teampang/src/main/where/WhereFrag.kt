package com.alice.teampang.src.main.where


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alice.teampang.R
import com.alice.teampang.src.BaseFrag

class WhereFragS : BaseFrag() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_location, container,false)
        return view
    }
}

