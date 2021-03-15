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
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragPlanPossibleNameBinding
import com.alice.teampang.src.BaseFrag

private lateinit var membername : String
class PlanPossibleNameFrag : BaseFrag(), View.OnClickListener {

    private var _binding: FragPlanPossibleNameBinding? = null
    private val binding get() = _binding!!

    var bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPlanPossibleNameBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.possibleNameBtn.isEnabled = false

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val listener = EnterListener()
        binding.possibeNameEdit1.setOnEditorActionListener(listener)

        binding.possibeNameEdit1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               membername = p0.toString()

            }
            //필요하면 정규식 활용
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val name = binding.possibeNameEdit1.text.toString()
                if(name.length<2&&!name.equals("")){
                    binding.possibleNameText2.setText("이름은 두 글자 이상이에요!")
                    binding.possibleNameBtn.isEnabled = false
                }
                else if(name.equals("")){
                    binding.possibleNameText2.setText("이름을 입력해주세요!")
                    binding.possibleNameBtn.isEnabled = false
                }
                else{
                    binding.possibleNameBtn.isEnabled = true
                    binding.possibleNameText2.setText("")
                }
            }
        })

        binding.possibleNameBtn.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when(v.id) {
            R.id.possible_name_btn -> {
                val name = bundleOf("membername" to membername)
                    navController.navigate(R.id.action_planPossibleNameFrag_to_planPossibleInvitedFrag, name)
            }
            R.id.layout -> hideKeyBoard()
        }
    }

    fun hideKeyBoard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.possibeNameEdit1.windowToken, 0)
    }

    inner class EnterListener : TextView.OnEditorActionListener {
        override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {

            return false
        }
    }
}