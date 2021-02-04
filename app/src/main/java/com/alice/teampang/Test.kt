package com.alice.teampang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alice.teampang.src.plan_create.name.PlanCreateNameFrag
import com.alice.teampang.ui.fragment.PlanFragment

class Test : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.test_framlayout, PlanCreateNameFrag()).commit()



    }
}