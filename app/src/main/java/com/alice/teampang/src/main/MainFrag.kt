package com.alice.teampang.src.main


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import com.alice.teampang.R
import com.alice.teampang.databinding.FragMainBinding
import com.alice.teampang.base.BaseFrag
import com.alice.teampang.src.main.`when`.WhenFrag
import com.alice.teampang.src.main.how.HowFrag
import com.alice.teampang.src.main.where.WhereFrag

lateinit var navController: NavController
private lateinit var myContext: Context
private var _binding: FragMainBinding? = null
private val binding get() = _binding!!

class MainFrag : BaseFrag() {
    private var test = ""

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragMainBinding.inflate(inflater, container, false)
        val view = binding.root
        myContext = requireContext()
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout, WhenFrag()).commit()
        binding.bottomNavigationView.selectedItemId = R.id.bottom_date
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { it ->
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            when (it.itemId) {
                R.id.bottom_plan -> {
                    if (!test.equals("plan")) {
                        transaction.replace(    //fragment가 해당 layout으로 대체됨
                                R.id.framelayout,
                        HowFrag()
                        ).commit()
                        test = "plan"
                    }
                    true
                }
                R.id.bottom_date -> {
                    if (!test.equals("date")) {
                        transaction.replace(    //fragment가 해당 layout으로 대체됨
                            R.id.framelayout,
                            WhenFrag()
                        ).commit()
                        test = "date"
                    }
                    true
                }
                R.id.bottom_location -> {
                    if (!test.equals("location")) {
                        transaction.replace(    //fragment가 해당 layout으로 대체됨
                            R.id.framelayout,
                            WhereFrag()
                        ).commit()
                        test = "location"
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments?.getBoolean("isPlan") == true) {
            val bundle = bundleOf("isPlan" to true)
            navController.navigate(R.id.action_mainFrag_to_planPossibleInvitationFrag, bundle)
        }
    }
}


