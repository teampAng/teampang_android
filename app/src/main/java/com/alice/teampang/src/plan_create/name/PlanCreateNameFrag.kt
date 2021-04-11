package com.alice.teampang.src.plan_create.name

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanCreateNameBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.GlobalApplication.Companion.PLAN_NAME
import com.alice.teampang.src.GlobalApplication.Companion.prefs

class PlanCreateNameFrag : BaseFrag(), View.OnClickListener {

    private var _binding: FragPlanCreateNameBinding? = null
    private val binding get() = _binding!!

    //나중에 넘기기
    var planName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanCreateNameBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val listener = EnterListener()
        binding.tvEdit.setOnEditorActionListener(listener)

        binding.tvEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                planName = p0.toString()
                if (planName != "") {
                    binding.btnNext.setBackgroundResource(R.drawable.btn_blue)
                    binding.tvNext.setTextColor(Color.parseColor("#FFFFFFFF"))
                } else {
                    binding.btnNext.setBackgroundResource(R.drawable.btn_grey)
                    binding.tvNext.setTextColor(Color.parseColor("#9d9d9d"))
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
        binding.layout.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v) {
            binding.btnBack -> navController.popBackStack()
            binding.btnNext -> {
                if (planName != "") {
                    hideKeyBoard()
                    prefs.setString(PLAN_NAME, planName)
                    navController.navigate(R.id.action_planCreateNameFrag_to_planCreateCalendarFrag)
                } else {
                    showCustomToast("일정 이름을 입력해주세요.")
                }
            }
            binding.layout -> hideKeyBoard()
        }
    }

    private fun hideKeyBoard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.tvEdit.windowToken, 0)
    }

    //이너 클래스 리스너를 이용해서 setOnEditorActionListener를 사용하는 방법
    inner class EnterListener : TextView.OnEditorActionListener {
        override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {

            // 키보드 내리겠다고 하면
            return false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}