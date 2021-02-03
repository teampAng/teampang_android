package com.alice.teampang.src

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alice.teampang.R
import com.alice.teampang.ui.fragment.DateFragment
import com.alice.teampang.ui.fragment.LocationFragment
import com.alice.teampang.ui.fragment.PlanFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity2 : AppCompatActivity() {
    var test : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.linearLayout, PlanFragment()).commit()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { it->
            val transaction = supportFragmentManager.beginTransaction()
            when (it.itemId) {
                R.id.bottom_plan -> {
                    if (!test.equals("plan")) {
                        transaction.replace(    //fragment가 해당 layout으로 대체됨
                                R.id.linearLayout,
                                PlanFragment()
                        ).commit()
                        test = "plan"
                    }
                    false
                }
                R.id.bottom_date -> {
                    if (!test.equals("date")) {
                        transaction.replace(    //fragment가 해당 layout으로 대체됨
                                R.id.linearLayout,
                                DateFragment()
                        ).commit()
                        test = "date"
                    }
                    false
                }
                R.id.bottom_location -> {
                    if (!test.equals("location")) {
                        transaction.replace(    //fragment가 해당 layout으로 대체됨
                                R.id.linearLayout,
                                LocationFragment()
                        ).commit()
                        test = "location"
                    }
                    false
                }
                else -> {
                    false
                }
            }
        }
    }
}
