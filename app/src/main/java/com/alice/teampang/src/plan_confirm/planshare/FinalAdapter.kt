package com.alice.teampang.src.plan_confirm.planshare

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.R
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class FinalAdapter() : RecyclerView.Adapter<FinalAdapter.FinalViewHolder>() {

    private var context : Context? = null
    var mList : ArrayList<Final>? = ArrayList()
    var date : Array<String> = arrayOf()
    var time : String = ""
    var time2 : String = ""
    var time3 : String = ""
    var time4 : String = ""
    constructor(context: Context, jsonData: JSONObject) : this(){
        this.context = context

        val timeSchedules = jsonData.get("confirmed_times") as JSONArray

        Log.d("json", "adapter get $timeSchedules")

        for (i in 0 until timeSchedules.length()+1) {
            // get {"start_datetime":"2021-04-05 17:00","end_datetime":"2021-04-05 20:00"}

            try{
                val item = timeSchedules.get(i) as JSONObject
                Log.d("json", "get ($i) = $item")

                // get 2021-04-07 17:00
                val startDateAndTime = item.get("start_datetime") as String
                val endDateAndTime = item.get("end_datetime") as String
                Log.d("json", "start = $startDateAndTime")

                val startDate = startDateAndTime.substringBefore(" ")
                date = startDate.split("-").toTypedArray()
                time = startDateAndTime.substringAfterLast(" ", ":")
                time3 = time.substringBeforeLast(":")
                time2 = endDateAndTime.substringAfterLast(" ",":")
                time4 = time2.substringBeforeLast(":")
                val x = Integer.parseInt(date[1])
                val y = Integer.parseInt(date[2])
                mList!!.add(Final("${x}월 ${y}일 ${time3}~${time4}시(${time4.toInt()-time3.toInt()}시간)"))

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
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