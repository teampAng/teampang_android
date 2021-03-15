package com.alice.teampang.src.plan_confirm.date.Dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import com.alice.teampang.R
import com.alice.teampang.src.plan_confirm.date.Confirm
import java.util.*

class YesNoDialogFragment() : DialogFragment(),
        View.OnClickListener {
    private var listener2 : DialClickListener? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_layout, container, false)
        //어느 다이어로그에서 왔는지
//        val bundle = arguments
//        val notice = bundle.getParcelable<FavoriteNotice>(EXTRA_NOTICE_SAVE)
        val yes_text = view.findViewById<CardView>(R.id.dialog_tv_yes)
        val no_text = view.findViewById<TextView>(R.id.dialog_tv_no)
        val mArgs = arguments
        val mValue = mArgs!!.getString("key")
        val text = view.findViewById<TextView>(R.id.dialog_text)
        text.setText(mValue+"님이\n불가능한 시간입니다.\n그래도 선택하시겠습니까?")

        yes_text.setOnClickListener {
            listener2?.DialMemberClick(true)  //yes를 클릭시에
            dismiss()
        }

        no_text.setOnClickListener {
            dismiss()
        }

        return view
    }


    override fun onClick(p0: View?) {
        dismiss()
    }

    interface DialClickListener {       //리스너 인터페이스를 만듬
        fun DialMemberClick(check: Boolean)
    }
    fun setOnItemClickListener(listener2: DialClickListener) {
        this.listener2 = listener2
    }

}