package com.alice.teampang.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.R
import com.alice.teampang.src.main.`when`.homedata
import java.util.*
import kotlin.collections.ArrayList


class CustomAdapter() : RecyclerView.Adapter<CustomViewHolder>() {
    private var context : Context? = null
     var mList2 : ArrayList<homedata>? = null
    constructor(context: Context, homedatas: ArrayList<homedata>) : this(){
        this.context = context
        mList2 = homedatas
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(viewholder: CustomViewHolder, position: Int) {
        val images = intArrayOf(R.drawable.list__icon1,R.drawable.list__icon2,R.drawable.list__icon3,R.drawable.list__icon4,R.drawable.list__icon5,R.drawable.list__icon6)
        val rand = Random()
        viewholder.mainimage.setImageResource(images[rand.nextInt(images.size)])
        viewholder.subject.setText(mList2!![position].planname)
        viewholder.time.setText(mList2!![position].plantime)
        viewholder.place.setText(mList2!![position].planplace)
        viewholder.d_day.setText(("D-" + mList2!![position].d_day))
        if(mList2!![position].d_day!!.toInt()<4){
            viewholder.d_day.setText(("D-" + mList2!![position].d_day))
            viewholder.d_day.setTextColor(getColor(context!!, R.color.d_day_red))
        }
        if(mList2!![position].planplace==null){
            viewholder.place.setText("장소 미정")
        }
        if(mList2!![position].plantime==null){
            viewholder.place.setText("시간 미정")
            viewholder.d_day.setText("")
        }
    }

    override fun getItemCount(): Int {
        return mList2!!.size
    }
    }
class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val subject = itemView.findViewById<TextView>(R.id.item_subject)
    val time = itemView.findViewById<TextView>(R.id.item_time)
    val place = itemView.findViewById<TextView>(R.id.item_location)
    val d_day = itemView.findViewById<TextView>(R.id.item_d_day)
    val mainimage = itemView.findViewById<ImageView>(R.id.main_image)
}