package com.alice.teampang.src.main.how


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.appcompat.app.AlertDialog
import com.alice.teampang.R
import com.alice.teampang.base.BaseFrag

class HowFrag : BaseFrag() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_how, container,false)
        HowDialog()
        return view
    }

    private fun HowDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_where, null)
        val dialog: AlertDialog = builder.setView(dialogView).create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.show()

        val params: WindowManager.LayoutParams? = dialog.window?.attributes
        val dm = resources.displayMetrics
        params?.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300F, dm).toInt()
//        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = params

    }
}

