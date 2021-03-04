package com.alice.teampang.src

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentContainerView
import com.alice.teampang.R
import com.alice.teampang.src.main.MainFrag
import com.alice.teampang.token.TokenService
import com.prolificinteractive.materialcalendarview.CalendarDay

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

}