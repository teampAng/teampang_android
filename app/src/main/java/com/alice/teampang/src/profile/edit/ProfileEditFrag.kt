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
import com.alice.teampang.src.profile.interfaces.ProfileEditFragView
import com.alice.teampang.src.profile.model.*

class ProfileEditFrag : BaseFrag(), ProfileEditFragView, View.OnClickListener {

    lateinit var navController: NavController

    var nickname: String? = ""
    var gender = prefs.getInt(USER_GENDER, 0)
    var univ: String? = ""
    var univ_major: String? = ""
    var univ_num: Int? = -1
    var univ_grade: Int? = 0
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
                val pNickname = prefs.getString(USER_NICKNAME, null)
                val pUniv = prefs.getString(UNIV_NAME, null)
                val pUnivMajor = prefs.getString(UNIV_MAJOR, null)
                val pUnivNum = prefs.getInt(UNIV_NUM, -1)
                val pUnivGrade = prefs.getInt(UNIV_GRADE, -1)
                when {
                    nickname == "" -> nickname = pNickname
                    univ_o_cheked -> {
                        when {
                            univ == "" -> univ = pUniv
                            univ_major == "" -> univ_major = pUnivMajor
                            univ_num == -1 -> univ_num = pUnivNum
                            univ_grade == 0 -> univ_grade = pUnivGrade
                            else -> {
                                if (nickname == pNickname && univ == pUniv && univ_major == pUnivMajor
                                    && univ_num == pUnivNum && univ_grade == pUnivGrade
                                ) {
                                    showCustomToast("수정된 프로필이 없습니다.")
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
                    univ_x_cheked -> {
                        patchProfileBody = PatchProfileBody(nickname, null, null)
                        tryPatchProfile()
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
                        patchProfileResponse.data.university!!.univNum!!
                    )
                }
                showCustomToast("프로필이 수정 되었습니다.")
                navController.popBackStack()
            }
            400 -> {
                //닉네임 중복 & 기타 예외처리 -> status 로 나눠주면 좋겠음
                binding.nicknameCon1.visibility = View.GONE
                binding.nicknameCon2.visibility = View.VISIBLE
                binding.btnFinish.setBackgroundResource(R.drawable.btn_grey)
                binding.tvFinish.setTextColor(Color.parseColor("#9d9d9d"))

                showCustomToast(patchProfileResponse.message)
            }
            401 ->{
                //토큰이 만료된 경우
                showCustomToast(patchProfileResponse.message)
            }
            else -> {
                //예상치 못한 서버 응답
                showCustomToast(patchProfileResponse.message)
            }
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
                if (num != "") univ_num = num.toInt()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        binding.univYear.addTextChangedListener(object : TextWatcher {
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

