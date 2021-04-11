package com.alice.teampang.src

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.src.GlobalApplication.Companion.ACCESS_TOKEN
import com.alice.teampang.src.GlobalApplication.Companion.REFRESH_TOKEN
import com.alice.teampang.src.GlobalApplication.Companion.prefs
import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.src.token.interfaces.TokenFragView
import com.alice.teampang.src.token.model.*
import com.alice.teampang.token.TokenService


@SuppressLint("Registered")
open class BaseFrag : Fragment(), TokenFragView {
    private lateinit var refreshTokenBody: RefreshTokenBody

    lateinit var navController: NavController
    lateinit var mFunction: () -> Unit
    var dialog: AlertDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

    }

    fun showCustomToast(message: String?) {
        if (message != null) {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun showProgressDialog() {
        if (dialog == null) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setCancelable(false) // if you want user to wait for some process to finish,
            val dialogView = layoutInflater.inflate(R.layout.dialog_loading, null)
            dialog = builder.setView(dialogView).create()
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        if (dialog != null) {
            dialog!!.show()
        }
    }

    fun hideProgressDialog() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    fun tryPostRefreshToken(function: () -> Unit) {
        mFunction = function
        val tokenService = TokenService(this)
        val refreshToken: String? = prefs.getString(REFRESH_TOKEN, null)
        if (refreshToken != null) {
            refreshTokenBody = RefreshTokenBody(refreshToken)
            tokenService.postRefreshToken(refreshTokenBody)
        } else {
            navController.navigate(R.id.action_global_loginFrag)
        }
    }

    override fun tokenSuccess(tokenResponse: TokenResponse) {
        when (tokenResponse.status) {
            200 -> {
                //액세스 토큰 재발급 성공
                prefs.setString(ACCESS_TOKEN, tokenResponse.data.access)
                prefs.setString(REFRESH_TOKEN, tokenResponse.data.refresh)
                mFunction()
            }
            401 -> {
                //리프레쉬 토큰이 유효하지 않음
                navController.navigate(R.id.action_global_loginFrag)
                showCustomToast("로그인 화면으로 넘어가야 할 듯?")
            }
            else -> {
                //예상치 못한 서버 응답
                showCustomToast(tokenResponse.message)
            }
        }
    }

    override fun tokenError(errorResponse: ErrorResponse) {
        when (errorResponse.status) {
            401 -> {
                //리프레쉬 토큰이 유효하지 않음
                navController.navigate(R.id.action_global_loginFrag)
            }
            else -> {
                //예상치 못한 서버 응답
                showCustomToast(errorResponse.message)
            }
        }
    }

    override fun tokenFailure(message: Throwable?) {
        showCustomToast(message.toString())
    }

    override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }


}