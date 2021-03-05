package com.alice.teampang.src.mypage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.alice.teampang.R
import com.alice.teampang.databinding.FragMyPageBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_MAJOR
import com.alice.teampang.src.GlobalApplication.Companion.UNIV_NAME
import com.alice.teampang.src.GlobalApplication.Companion.USER_NICKNAME
import com.alice.teampang.src.GlobalApplication.Companion.prefs

class MyPageFrag : BaseFrag(), View.OnClickListener {

    private var _binding: FragMyPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragMyPageBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.name.text = prefs.getString(USER_NICKNAME, "")
        val univInfo = prefs.getString(UNIV_NAME, "") + " " + prefs.getString(UNIV_MAJOR, "")
        binding.univInfo.text = univInfo

        binding.btnBack.setOnClickListener(this)
        binding.btnProfile.setOnClickListener(this)
        binding.btnMySchedule.setOnClickListener(this)
        binding.btnCoffee.setOnClickListener(this)
        binding.btnContact.setOnClickListener(this)
        binding.btnSetting.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v) {
            binding.btnBack -> navController.popBackStack()
            binding.btnProfile -> navController.navigate(R.id.action_myPageFrag_to_profileFrag)
            binding.btnMySchedule -> navController.navigate(R.id.action_myPageFrag_to_myScheduleFrag)
            binding.btnCoffee -> coffeeDialog()
            binding.btnContact -> contactDialog()
            binding.btnSetting -> navController.navigate(R.id.action_myPageFrag_to_settingFrag)
        }
    }

    private fun coffeeDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_coffee, null)
        val btn_ok = dialogView.findViewById<TextView>(R.id.btn_ok)
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

        btn_ok.setOnClickListener {
            //나중에 정하기
            dialog.dismiss()
        }

        btn_pop.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun contactDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_contact_us, null)
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

        btn_pop.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

