package com.alice.teampang.src.signup

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragSignupBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_GRADE
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_MAJOR
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_NAME
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_NUM
import com.alice.teampang.src.GlobalApplication.Companion.USER_GENDER
import com.alice.teampang.src.GlobalApplication.Companion.USER_ID
import com.alice.teampang.src.GlobalApplication.Companion.USER_NICKNAME
import com.alice.teampang.src.GlobalApplication.Companion.prefs
import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.signup.interfaces.SignupFragView
import com.alice.teampang.src.signup.model.*
import org.threeten.bp.LocalDate

class SignupFrag : BaseFrag(), SignupFragView, View.OnClickListener {

    var nickname = ""
    var gender = 0
    var univ = ""
    var univ_major = ""
    var univ_num = -1
    var univ_grade = 0
    var univ_o_cheked = false
    var univ_x_cheked = false


    private lateinit var signUpBody: SignUpBody

    private var _binding: FragSignupBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragSignupBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        editTextChanged()

        binding.btnBack.setOnClickListener(this)
        binding.ivMale.setOnClickListener(this)
        binding.ivFemale.setOnClickListener(this)
        binding.btnUnivO.setOnClickListener(this)
        binding.btnUnivX.setOnClickListener(this)
        binding.btnFinish.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnBack -> navController.popBackStack()
            binding.ivMale -> {
                if (gender == 2) {
                    binding.ivFemale.setBackgroundResource(R.drawable.ic_unchecked_round)
                    binding.tvFemale.setTextColor(Color.parseColor("#bdbdbd"))
                }
                binding.ivMale.setBackgroundResource(R.drawable.ic_checked_round)
                binding.tvMale.setTextColor(Color.parseColor("#5aa6f8"))
                gender = 1
            }
            binding.ivFemale -> {
                if (gender == 1) {
                    binding.ivMale.setBackgroundResource(R.drawable.ic_unchecked_round)
                    binding.tvMale.setTextColor(Color.parseColor("#bdbdbd"))
                }
                binding.ivFemale.setBackgroundResource(R.drawable.ic_checked_round)
                binding.tvFemale.setTextColor(Color.parseColor("#5aa6f8"))
                gender = 2

            }
            binding.btnUnivO -> {
                binding.progressBar.progress = 200 / 3
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
                }
                isAllChecked()
            }
            binding.btnUnivX -> {
                binding.progressBar.progress = 200 / 3
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
                }
                isAllChecked()
            }
            binding.btnFinish -> {

                when {
                    nickname == "" -> {
                        showCustomToast("닉네임을 입력해주세요")
                        //나중에 대학생/대학생 아니에요 선택했는지 확인하는 거 추가
                    }
                    gender == 0 -> {
                        showCustomToast("성별을 선택해주세요")
                    }
                    univ_o_cheked -> {
                        when {
                            univ == "" -> showCustomToast("대학교를 입력해주세요")
                            univ_major == "" -> showCustomToast("학과를 입력해주세요")
                            univ_num == -1 -> showCustomToast("학번을 입력해주세요")
                            univ_grade == 0 -> showCustomToast("학년을 입력해주세요")
                            univ_grade < 0 -> showCustomToast("올바른 학번을 입력해주세요")
                            univ_grade > 6 -> showCustomToast("학년은 최대 6학년까지 가능합니다")
                            else -> {
                                val currentYear = LocalDate.now().year
                                univ_num += 2000
                                if (univ_num >= currentYear || univ_num <= 2000) {
                                    showCustomToast("가능한 학번이 아닙니다.")
                                } else {
                                    signUpBody = SignUpBody(
                                        nickname, gender,
                                        University(univ, univ_num, univ_major, univ_grade)
                                    )
                                    tryPostSignUp()
                                    binding.progressBar.progress = 100
                                }
                            }
                        }
                    }
                    univ_x_cheked -> {
                        signUpBody = SignUpBody(nickname, gender, null)
                        tryPostSignUp()
                        binding.progressBar.progress = 100
                    }
                }
            }
        }
    }

    private fun tryPostSignUp() {
        val signUpService = SignupService(this)
        signUpService.postSignUp(signUpBody)
    }

    override fun signUpSuccess(signUpResponse: SignUpResponse) {
        when (signUpResponse.status) {
            201 -> {
                //회원가입 성공, 신규 회원
                prefs.setInt(USER_ID, signUpResponse.data.id)
                prefs.setString(USER_NICKNAME, signUpResponse.data.nickname)
                prefs.setInt(USER_GENDER, signUpResponse.data.gender)
                if (signUpResponse.data.university != null) {
                    prefs.setString(UNIV_NAME, signUpResponse.data.university!!.univ)
                    prefs.setString(UNIV_MAJOR, signUpResponse.data.university!!.major)
                    prefs.setInt(UNIV_GRADE, signUpResponse.data.university!!.grade)
                    prefs.setInt(UNIV_NUM, signUpResponse.data.university!!.univNum-2000)
                }
                navController.navigate(R.id.action_signupFrag_to_signupSuccessFrag)
            }
            else -> showCustomToast(signUpResponse.message)

        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun signUpError(errorResponse: ErrorResponse) {
        when (errorResponse.status) {
            400 -> showCustomToast(errorResponse.message)
            401 -> tryPostRefreshToken { tryPostSignUp() }
            409 -> {
                binding.nicknameCon1.visibility = View.GONE
                binding.nicknameCon2.visibility = View.VISIBLE
                binding.nickname.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.orangey_red)
                binding.btnFinish.setBackgroundResource(R.drawable.btn_grey)
                binding.tvFinish.setTextColor(Color.parseColor("#9d9d9d"))
            }
            else -> showCustomToast(errorResponse.message)
        }
    }

    override fun signUpFailure(message: Throwable?) {
        showCustomToast(message.toString())
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
                isAllChecked()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        binding.univ.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                univ = p0.toString().trim()
                isAllChecked()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        binding.univMajor.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                univ_major = p0.toString().trim()
                isAllChecked()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        binding.univNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val num = p0.toString().trim()
                if (num != "") univ_num = num.toInt()
                isAllChecked()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        binding.univGrade.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val grade = p0.toString().trim()
                if (grade != "") univ_grade = grade.toInt()
                isAllChecked()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }

    private fun isAllChecked() {
        //모든게 완료되면(대학생/비대학생) 완료 버튼 백그라운드 + 글자 색 바꾸기
        if (nickname != "" && gender != 0 && univ_o_cheked && univ != "" && univ_num != -1 &&
            univ_major != "" && univ_grade != 0
        ) {
            binding.btnFinish.setBackgroundResource(R.drawable.btn_blue)
            binding.tvFinish.setTextColor(Color.parseColor("#FFFFFF"))
        }
        else if (nickname != "" && gender != 0 && univ_x_cheked) {
            binding.btnFinish.setBackgroundResource(R.drawable.btn_blue)
            binding.tvFinish.setTextColor(Color.parseColor("#FFFFFF"))
        }
        else {
            binding.btnFinish.setBackgroundResource(R.drawable.btn_grey)
            binding.tvFinish.setTextColor(Color.parseColor("#9d9d9d"))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

