package com.alice.teampang.src.plan_possible.selection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.R
import com.alice.teampang.model.Selection

class SelectionAdapter :  RecyclerView.Adapter<SelectionAdapter.SelectionViewHolder>(){

    var List = mutableListOf<Selection>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.selection_item, parent, false)
        return SelectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectionViewHolder, position: Int) {
            holder.number2.setText(List[position].number)
    }

    override fun getItemCount(): Int {
        return List.size
    }

   inner class SelectionViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
       val number2 = itemView.findViewById<TextView>(R.id.tv_day_number)
    }
}