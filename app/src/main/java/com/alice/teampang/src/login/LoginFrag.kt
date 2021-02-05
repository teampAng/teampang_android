package com.alice.teampang.src.login

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragLoginBinding
import com.alice.teampang.src.BaseFrag
import java.util.regex.Pattern

class LoginFrag: BaseFrag(), View.OnClickListener {

    lateinit var navController : NavController

    private var _binding: FragLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        //TextView 밑줄 긋기
        val spannableString1 =
            SpannableString("서비스 이용약관") //나중에 string resource 파일로 바꾸기 getString(resId)
        spannableString1.setSpan(UnderlineSpan(), 0, spannableString1.length, 0)
        binding.tvCondition1.text = spannableString1

        val spannableString2 =
            SpannableString("개인정보 처리방침")
        spannableString2.setSpan(UnderlineSpan(), 0, spannableString2.length, 0)
        binding.tvCondition2.text = spannableString2

        val mTransform = Linkify.TransformFilter { match, url -> "" }

        val pattern1 = Pattern.compile("서비스 이용약관")
        val pattern2 = Pattern.compile("개인정보 처리방침")

        Linkify.addLinks(binding.tvCondition1, pattern1, "https://gun0912.tistory.com/66",null,mTransform)
        Linkify.addLinks(binding.tvCondition2, pattern2, "https://gun0912.tistory.com/66",null,mTransform)


        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_login -> {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}