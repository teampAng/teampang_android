package com.alice.teampang.src.profile

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragProfileEditBinding
import com.alice.teampang.src.BaseFrag

class ProfileEditFrag : BaseFrag(), View.OnClickListener {

    lateinit var navController : NavController

    var nickname = ""
    var gender = ""
    var is_univ = ""
    var univ = ""
    var univ_major = ""
    var univ_num = ""
    var univ_year = ""
    var univ_o_cheked = false
    var univ_x_cheked = false

    private var _binding: FragProfileEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragProfileEditBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        //스플래시화면에서 sharedPreference 에 저장했다가, 저장된 거 불러와서 뿌려주기

        //저장된 값이 대학생인 경우
        binding.univBox1.visibility = View.VISIBLE
        binding.univBox2.visibility = View.VISIBLE

        editTextChanged()

        binding.btnBack.setOnClickListener(this)
        binding.btnUnivO.setOnClickListener(this)
        binding.btnUnivX.setOnClickListener(this)
        binding.btnFinish.setOnClickListener(this)
        binding.btnEditProfile.setOnClickListener(this)
        binding.layout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnBack -> navController.popBackStack()
            binding.btnUnivO -> {
                if (!univ_o_cheked) {
                    if (univ_x_cheked) {
                        binding.btnUnivX.setTextColor(Color.parseColor("#bdbdbd"))
                        binding.btnUnivX.setBackgroundResource(R.drawable.btn_grey_line_round)
                    }
                    binding.btnUnivO.setTextColor(Color.parseColor("#5aa6f8"))
                    binding.btnUnivO.setBackgroundResource(R.drawable.btn_blue_line_round)
                    binding.univBox1.visibility = View.VISIBLE
                    binding.univBox2.visibility = View.VISIBLE
                    univ_o_cheked = true
                    univ_x_cheked = false
                    //대학생이에요 상태로 is_univ
                }
            }
            binding.btnUnivX -> {
                if (!univ_x_cheked) {
                    if (univ_o_cheked) {
                        binding.btnUnivO.setTextColor(Color.parseColor("#bdbdbd"))
                        binding.btnUnivO.setBackgroundResource(R.drawable.btn_grey_line_round)
                    }
                    binding.btnUnivX.setTextColor(Color.parseColor("#5aa6f8"))
                    binding.btnUnivX.setBackgroundResource(R.drawable.btn_blue_line_round)
                    binding.univBox1.visibility = View.GONE
                    binding.univBox2.visibility = View.GONE
                    univ_x_cheked = true
                    univ_o_cheked = false
                    //대학생이 아니에요 상태로
                }
            }
            binding.btnFinish -> {
                when {
                    nickname == "" -> {
                        showCustomToast("닉네임을 입력해주세요")
                        //나중에 대학생/대학생 아니에요 선택했는지 확인하는 거 추가
                    }
                    gender == "" -> {
                        showCustomToast("성별을 선택해주세요")
                    }
                    univ_o_cheked -> {
                        when {
                            univ == "" -> {
                                showCustomToast("대학교를 입력해주세요")
                            }
                            univ_major == "" -> {
                                showCustomToast("학과를 입력해주세요")
                            }
                            univ_num == "" -> {
                                showCustomToast("학번을 입력해주세요")
                            }
                            univ_year == "" -> {
                                showCustomToast("학년을 입력해주세요")
                            }
                            else -> {
                                //api 쏘기
                                //닉네임 중복되면 블록 띄우기
                            }
                        }
                    }
                    univ_x_cheked -> {
                        //univ 정보다 null로 바꾸고 api 쏘기
                        //닉네임 중복되면 블록 띄우기
                    }
                }
            }
            binding.btnEditProfile -> {
                //갤러리 접근해서 이미지 uri 가져오기?
                //아직 이미지 uri 보내는 api 가 없음.
            }
            binding.layout -> hideKeyBoard()
        }
    }

    private fun hideKeyBoard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.univMajor.windowToken, 0)
    }

    private fun editTextChanged() {
        //nickname 유효성 검사
        val nicknameValidation = Regex("^[A-Za-z0-9가-힣]{2,10}$")
        binding.nickname.addTextChangedListener(object : TextWatcher {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun afterTextChanged(p0: Editable?) {
                nickname = p0.toString().trim()
                if (!nickname.matches(nicknameValidation)) {
                    binding.nicknameCon1.visibility = View.VISIBLE
                    binding.nicknameCon2.visibility = View.GONE
                    binding.nickname.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.orangey_red)
                    nickname = ""
                } else {
                    binding.nicknameCon1.visibility = View.INVISIBLE
                    binding.nicknameCon2.visibility = View.GONE
                    binding.nickname.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.primary)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        binding.univ.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                univ = p0.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        binding.univMajor.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                univ_major = p0.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        binding.univNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                univ_num = p0.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        binding.univYear.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                univ_year = p0.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

