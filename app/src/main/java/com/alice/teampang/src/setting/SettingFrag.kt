package com.alice.teampang.src.setting

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragSettingBinding
import com.alice.teampang.src.BaseFrag

class SettingFrag : BaseFrag(), View.OnClickListener {

    private var _binding: FragSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragSettingBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.btnBack.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
        binding.btnSignout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v) {
            binding.btnBack -> navController.popBackStack()
            binding.btnLogout -> logoutDialog()
            binding.btnSignout -> signoutDialog()
        }
    }

    private fun logoutDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_logout, null)
        val btn_logout = dialogView.findViewById<TextView>(R.id.btn_logout)
        val btn_pop = dialogView.findViewById<TextView>(R.id.btn_pop)
        val dialog: AlertDialog = builder.setView(dialogView).create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.show()

        val params: WindowManager.LayoutParams? = dialog.window?.attributes
        val dm = resources.displayMetrics
        params?.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300F, dm).toInt()
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = params

        btn_logout.setOnClickListener {
            //api 엮기
            dialog.dismiss()
        }

        btn_pop.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun signoutDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_signout, null)
        val btn_signout = dialogView.findViewById<TextView>(R.id.btn_signout)
        val btn_pop = dialogView.findViewById<TextView>(R.id.btn_pop)
        val dialog: AlertDialog = builder.setView(dialogView).create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.show()

        val params: WindowManager.LayoutParams? = dialog.window?.attributes
        val dm = resources.displayMetrics
        params?.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300F, dm).toInt()
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = params

        btn_signout.setOnClickListener {
            //api 엮기
            dialog.dismiss()
        }

        btn_pop.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

