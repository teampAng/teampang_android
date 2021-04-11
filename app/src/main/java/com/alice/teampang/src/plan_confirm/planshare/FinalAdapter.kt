package com.alice.teampang.src.plan_confirm.planshare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.R
import java.util.ArrayList

class FinalAdapter() : RecyclerView.Adapter<FinalAdapter.FinalViewHolder>() {
    private var mList: ArrayList<Final>? = null
    inner class FinalViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var text : TextView = view.findViewById<View>(R.id.confirm_text) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.confirm_item, parent, false)
        return FinalViewHolder(view)
    }

    override fun onBindViewHolder(holder: FinalViewHolder, position: Int) {
        holder.text.setText(mList!![position].confirmtext)
    }

    override fun getItemCount(): Int {
       return mList!!.size
    }

}