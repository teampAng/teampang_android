package com.alice.teampang.src.profile.edit

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
import com.alice.teampang.src.GlobalApplication
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_GRADE
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_MAJOR
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_NAME
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_NUM
import com.alice.teampang.src.GlobalApplication.Companion.USER_GENDER
import com.alice.teampang.src.GlobalApplication.Companion.USER_ID
import com.alice.teampang.src.GlobalApplication.Companion.USER_NICKNAME
import com.alice.teampang.src.GlobalApplication.Companion.prefs
import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.profile.interfaces.ProfileEditFragView
import com.alice.teampang.src.profile.model.*
import org.threeten.bp.LocalDate

class ProfileEditFrag : BaseFrag(), ProfileEditFragView, View.OnClickListener {

    val pNickname = prefs.getString(USER_NICKNAME, "")
    val pUniv = prefs.getString(UNIV_NAME, "")
    val pUnivMajor = prefs.getString(UNIV_MAJOR, "")
    val pUnivNum = prefs.getInt(UNIV_NUM, 0)
    val pUnivGrade = prefs.getInt(UNIV_GRADE, 0)
    var nickname = pNickname
    var gender = prefs.getInt(USER_GENDER, 0)
    var univ = pUniv
    var univ_major = pUnivMajor
    var univ_num = pUnivNum+2000
    var univ_grade = pUnivGrade
    var univ_o_cheked = false
    var univ_x_cheked = false

    private lateinit var patchProfileBody: PatchProfileBody

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

        if (prefs.getString(UNIV_NAME, "") != "") {
            //저장된 값이 대학생인 경우
            univ_o_cheked = true
            binding.univBox1.visibility = View.VISIBLE
            binding.univBox2.visibility = View.VISIBLE

            binding.btnUnivO.setTextColor(Color.parseColor("#5aa6f8"))
            binding.btnUnivO.setBackgroundResource(R.drawable.btn_blue_line_round)

            binding.nickname.hint = pNickname
            binding.univ.hint = pUniv
            binding.univNum.hint = "${pUnivNum}학번"
            binding.univMajor.hint = pUnivMajor
            binding.univGrade.hint = "${pUnivGrade}학년"

            binding.nickname.setHintTextColor(Color.parseColor("#333333"))
            binding.univ.setHintTextColor(Color.parseColor("#333333"))
            binding.univNum.setHintTextColor(Color.parseColor("#333333"))
            binding.univMajor.setHintTextColor(Color.parseColor("#333333"))
            binding.univGrade.setHintTextColor(Color.parseColor("#333333"))
        } else {
            univ_x_cheked = true
            binding.btnUnivX.setTextColor(Color.parseColor("#5aa6f8"))
            binding.btnUnivX.setBackgroundResource(R.drawable.btn_blue_line_round)
        }

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
                if (nickname == "") nickname = pNickname!!
                else if (univ_o_cheked) {
                    if (univ_grade < 0) showCustomToast("올바른 학번을 입력해주세요")
                    else if (univ_grade > 6) showCustomToast("학년은 최대 6학년까지 가능합니다")
                    else {
                        if (nickname == pNickname && univ == pUniv && univ_major == pUnivMajor
                            && univ_num == pUnivNum && univ_grade == pUnivGrade
                        ) {
                            showCustomToast("수정된 프로필이 없습니다.")
                        } else {
                            val currentYear = LocalDate.now().year
                            if (univ_num >= currentYear || univ_num <= 2000) {
                                showCustomToast("가능한 학번이 아닙니다.")
                            } else {
                                patchProfileBody = PatchProfileBody(
                                    nickname, gender,
                                    University(univ, univ_num, univ_major, univ_grade)
                                )
                                tryPatchProfile()
                            }
                        }
                    }
                }
                else if (univ_x_cheked) {
                    when {
                        pUniv != "" -> {
                            //대학생->비대학생
                            patchProfileBody = PatchProfileBody(nickname, gender, null)
                            tryPatchProfile()
                        }
                        nickname == pNickname -> showCustomToast("수정된 프로필이 없습니다.")
                        else -> {
                            //닉네임만 바꾼 경우
                            patchProfileBody = PatchProfileBody(nickname, gender, null)
                            tryPatchProfile()
                        }
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

    private fun tryPatchProfile() {
        val userId: Int = prefs.getInt(USER_ID, 0)
        val patchProfileService = ProfileEditService(this)
        patchProfileService.patchProfile(userId, patchProfileBody)
    }

    override fun patchProfileSuccess(patchProfileResponse: PatchProfileResponse) {
        when (patchProfileResponse.status) {
            200 -> {
                //프로필 수정 성공
                prefs.setInt(USER_ID, patchProfileResponse.data.id)
                prefs.setString(USER_NICKNAME, patchProfileResponse.data.nickname)
                prefs.setInt(USER_GENDER, patchProfileResponse.data.gender)
                if (patchProfileResponse.data.university != null) {
                    prefs.setString(
                        UNIV_NAME,
                        patchProfileResponse.data.university!!.univ
                    )
                    prefs.setString(
                        UNIV_MAJOR,
                        patchProfileResponse.data.university!!.major
                    )
                    prefs.setInt(
                        UNIV_GRADE,
                        patchProfileResponse.data.university!!.grade!!
                    )
                    prefs.setInt(
                        UNIV_NUM,
                        patchProfileResponse.data.university!!.univNum!!-2000
                    )
                }
                showCustomToast("프로필이 수정 되었습니다.")
                navController.popBackStack()
            }
            else -> showCustomToast(patchProfileResponse.message)
        }
    }

    override fun patchProfileError(errorResponse: ErrorResponse) {
        when (errorResponse.status) {
            400 -> {
                //닉네임 중복 & 기타 예외처리 -> status 로 나눠줄 예정
                binding.nicknameCon1.visibility = View.GONE
                binding.nicknameCon2.visibility = View.VISIBLE
                binding.btnFinish.setBackgroundResource(R.drawable.btn_grey)
                binding.tvFinish.setTextColor(Color.parseColor("#9d9d9d"))

                showCustomToast(errorResponse.message)
            }
            401 -> tryPostRefreshToken { tryPatchProfile() }
            else -> showCustomToast(errorResponse.message)
        }
    }

    override fun patchProfileFailure(message: Throwable?) {
        showCustomToast(message.toString())
    }


    private fun hideKeyBoard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.univMajor.windowToken, 0)
    }

    private fun editTextChanged() {
        //nickname 유효성 검사
        val nicknameValidation = Regex("^[A-Za-z0-9가-힣]{2,10}$")
        binding.nickname.addTextChangedListener(object : TextWatcher {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun afterTextChanged(p0: Editable?) {
                nickname = p0.toString().trim()
                if (!nickname?.matches(nicknameValidation)!!) {
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
                val num = p0.toString().trim()
                if (num != "") univ_num = num.toInt()+2000
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

