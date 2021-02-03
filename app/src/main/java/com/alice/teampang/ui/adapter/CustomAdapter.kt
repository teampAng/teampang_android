package com.alice.teampang.ui.adapter


import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.R
import com.alice.teampang.model.Schedule


class CustomAdapter() :
    RecyclerView.Adapter<CustomViewHolder>() {

    var mList = mutableListOf<Schedule>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(viewholder: CustomViewHolder, position: Int) {
//        viewholder.id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
//        viewholder.english.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
//        viewholder.korean.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
//        viewholder.id.gravity = Gravity.CENTER
//        viewholder.english.gravity = Gravity.CENTER
//        viewholder.korean.gravity = Gravity.CENTER
        viewholder.id.setText(mList[position].Dday)
        viewholder.english.setText(mList[position].plansubject)
        viewholder.korean.setText(mList[position].time)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}

class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val id = itemView.findViewById<TextView>(R.id.item_d_day)
    val english = itemView.findViewById<TextView>(R.id.item_subject)
    val korean = itemView.findViewById<TextView>(R.id.item_time_loaction)
}