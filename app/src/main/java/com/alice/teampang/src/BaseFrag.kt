package com.alice.teampang.src

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.alice.teampang.R


@SuppressLint("Registered")
open class BaseFrag : Fragment() {
    var dialog: AlertDialog? = null
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

    override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }
}