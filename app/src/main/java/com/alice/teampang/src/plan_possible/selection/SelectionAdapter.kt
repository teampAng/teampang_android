package com.alice.teampang.src.plan_possible.selection

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.R
import com.ddd.androidutils.DoubleClick
import com.ddd.androidutils.DoubleClickListener
import kotlin.properties.Delegates


class SelectionAdapter() : RecyclerView.Adapter<SelectionAdapter.SelectionViewHolder>() {
    var List:MutableList<Selection> = mutableListOf()
    var List2 = mutableListOf<Int>(16, 17, 18, 19) //main에서 서버에서 받아와서 다음처럼 데이터를 전달 받았다고 가정
    var a :Boolean = false
    companion object{
        var temp by Delegates.notNull<Int>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.selection_item, parent, false)
        return SelectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectionViewHolder, position: Int) {
        val startposition = 15      //서버에서 가져옴
        val endposition = 20        //22

        holder.description.setText(List[position].text)

        if (position in (startposition ) until endposition) {
            holder.icon.setImageResource(R.drawable.empty_box2)
        }
        else{
            holder.icon.setImageResource(R.drawable.empty_box)
        }

        val doubleClick = DoubleClick(object : DoubleClickListener {    //나중에 onclick으로 바꿀꺼임
            override fun onDoubleClickEvent(view: View?) {
            }

            override fun onSingleClickEvent(view: View?) {

                if (position in (startposition ) until endposition && a.equals(false)) {
                    holder.icon.setImageResource(R.drawable.ic_check2)
                    holder.icon.setBackgroundColor(Color.BLUE)
                    a=true
                    temp = position
                    Log.d("log2",temp.toString())
                }

                else if(position in (startposition) until endposition && a.equals(true)){
                    holder.icon.setImageResource(R.drawable.ic_check3)
                    holder.icon.setBackgroundColor(Color.RED)
                    a=false
                }

                else{
                    holder.icon.resources.getColor(R.color.color12)
                }
            }
        })
        holder.icon.setOnClickListener(doubleClick)

    }

    override fun getItemCount(): Int {
        return List.size
    }

    inner class SelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var icon = itemView.findViewById<ImageView>(R.id.horizon_icon)
        var description = itemView.findViewById<TextView>(R.id.horizon_description)

    }
}

/*
onBindViewHolder

if(list.contains(position))
    holder.칸.setImage(블루)
else
    holder.칸.setImage(흰색)

클릭이벤트
if(list.contains(position))
    list.remove(position)
else
    list.add(position)
 */