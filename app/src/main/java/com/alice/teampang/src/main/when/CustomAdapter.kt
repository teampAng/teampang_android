package com.alice.teampang.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.R
import com.alice.teampang.databinding.ItemListBinding
import com.alice.teampang.model.Results
import java.util.*
import kotlin.collections.ArrayList


class CustomAdapter(list : ArrayList<Results>, val itemClick: (Results) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
     var mList2 : ArrayList<Results> = list
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val images = intArrayOf(R.drawable.list__icon1,R.drawable.list__icon2,R.drawable.list__icon3,R.drawable.list__icon4,R.drawable.list__icon5,R.drawable.list__icon6)
        val rand = Random()
        if(holder is CustomViewHolder){
            val data = mList2.get(position)
            holder.itemMainBinding?.mainImage?.setImageResource(images[rand.nextInt(images.size)])
            holder.itemMainBinding?.data =data
            holder.itemMainBinding?.index = position.toString()
            holder.itemMainBinding?.data2 = mList2[position].confirmedTimes[0]
            //아이템 클릭시 상세화면 오픈
            holder.itemMainBinding?.itemBody?.setOnClickListener{
                    itemClick(data)
            }
        }


//        viewholder.time.setText(mList2!![position].plantime)
//        viewholder.place.setText(mList2!![position].planplace)
//        viewholder.d_day.setText(("D-" + mList2!![position].d_day))
//        if(mList2!![position].d_day!!.toInt()<4){
//            viewholder.d_day.setText(("D-" + mList2!![position].d_day))
//            viewholder.d_day.setTextColor(getColor(context!!, R.color.d_day_red))
//        }
//        if(mList2!![position].planplace==null){
//            viewholder.place.setText("장소 미정")
//        }
//        if(mList2!![position].plantime==null){
//            viewholder.place.setText("시간 미정")
//            viewholder.d_day.setText("")
//        }
        
    }

    override fun getItemCount(): Int {
        return mList2.size
    }

    override fun getItemId(position: Int): Long {
        super.getItemId(position)
        return position.toLong()
    }

    internal inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemMainBinding : ItemListBinding? = DataBindingUtil.bind(itemView)
    }
}



