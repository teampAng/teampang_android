package com.alice.teampang.src.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragProfileBinding
import com.alice.teampang.src.BaseFrag

class ProfileFrag : BaseFrag(), View.OnClickListener {

    lateinit var navController : NavController

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

        navController = Navigation.findNavController(view)

        //스플래시화면에서 sharedPreference 에 저장했다가, 저장된 거 불러와서 뿌려주기
        //프로필 수정이후에 돌아올 때에도, 프로필 수정할 때 저장된 값 수정해서 잘 업데이트 되게끔

        //저장된 값이 대학생인 경우
        binding.univBox1.visibility = View.VISIBLE
        binding.univBox2.visibility = View.VISIBLE

        //이 화면에서 프로필 수정할 수 x
        binding.univInfo.isEnabled = false
        binding.univNum.isEnabled = false
        binding.univMajor.isEnabled = false
        binding.univYear.isEnabled = false

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

