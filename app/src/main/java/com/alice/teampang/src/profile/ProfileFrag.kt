package com.alice.teampang.src.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alice.teampang.R
import com.alice.teampang.databinding.FragProfileBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_GRADE
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_MAJOR
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_NAME
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_NUM
import com.alice.teampang.src.GlobalApplication.Companion.USER_NICKNAME
import com.alice.teampang.src.GlobalApplication.Companion.prefs

class ProfileFrag : BaseFrag(), View.OnClickListener {

    private var _binding: FragProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.name.text = prefs.getString(USER_NICKNAME, "")

        if (prefs.getString(UNIV_NAME, "") != "") {
            //저장된 값이 대학생인 경우
            binding.univBox1.visibility = View.VISIBLE
            binding.univBox2.visibility = View.VISIBLE

            val univInfo = prefs.getString(UNIV_NAME, "") + " " + prefs.getString(UNIV_MAJOR, "")
            binding.univInfo.text = univInfo
            binding.univ.hint = prefs.getString(UNIV_NAME, "")
            binding.univNum.hint = "${prefs.getInt(UNIV_NUM, 0)}학번"
            binding.univMajor.hint = prefs.getString(UNIV_MAJOR, "")
            binding.univGrade.hint = "${prefs.getInt(UNIV_GRADE, 0)}학년"
        } else binding.univInfo.visibility = View.GONE

        //이 화면에서 프로필 수정할 수 x
        binding.univInfo.isEnabled = false
        binding.univNum.isEnabled = false
        binding.univMajor.isEnabled = false
        binding.univGrade.isEnabled = false

        binding.btnBack.setOnClickListener(this)
        binding.btnEditProfile.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v) {
            binding.btnBack -> navController.popBackStack()
            binding.btnEditProfile -> navController.navigate(R.id.action_profileFrag_to_profileEditFrag)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

