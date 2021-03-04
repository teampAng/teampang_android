package com.alice.teampang.src.plan_possible.name

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanCreateNameBinding
import com.alice.teampang.databinding.FragPlanPossibleNameBinding
import com.alice.teampang.src.BaseFrag

class PlanPossibleNameFrag : BaseFrag(), View.OnClickListener {

    private var _binding: FragPlanPossibleNameBinding? = null
    private val binding get() = _binding!!

    var membername = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanPossibleNameBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val listener = EnterListener()
        binding.possibeName.setOnEditorActionListener(listener)

        binding.possibeName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                membername = p0.toString()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        binding.possibleBtn.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when(v.id) {
            R.id.possible_btn -> {
                if (membername != "") {
                    hideKeyBoard()
                    navController.navigate(R.id.action_planPossibleNameFrag_to_planPossibleSelectionFrag)
                } else {
                    showCustomToast("이름을 입력해주세요.")
                }
            }
            R.id.layout -> hideKeyBoard()
        }
    }

    fun hideKeyBoard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.possibeName.windowToken, 0)
    }

    inner class EnterListener : TextView.OnEditorActionListener {
        override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {

            return false
        }
    }
}