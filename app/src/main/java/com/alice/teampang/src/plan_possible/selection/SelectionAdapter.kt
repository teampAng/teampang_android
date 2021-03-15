package com.alice.teampang.src.plan_possible.selection

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.R
import java.util.ArrayList

class SelectionAdapter() : RecyclerView.Adapter<SelectionAdapter.SelectionViewHolder>() {
    private var listener: ItemClickListener? = null
    private var mList //recyclerview를 생성하기 위한 0~23이 담기는 리스트
            : ArrayList<Selection>? = null
    private var context : Context? = null
    constructor(context: Context?, selections: ArrayList<Selection>) : this() {
        // 프래그먼트에서 넘어온 Selections를 Adapter의 mList에 대입
        mList = selections
        this.context = context
    }

    // position과 check 여부를 알려주는 리스너 콜백을 정의
    interface ItemClickListener {
        fun onItemClick(v: View?, position: Int, check: Boolean)
    }

    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    inner class SelectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id: TextView = view.findViewById(R.id.horizon_description)
        var icon: ImageView = view.findViewById(R.id.horizon_icon)
        var txtPersonalSchedule: TextView = view.findViewById(R.id.txt_schedule)

        init {
            // View에 클릭 리스너를 붙인다
            view.setOnClickListener { v ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    if (listener != null) {

                        // 체크가 가능한 시간대이면 체크 선택/해제 처리 후 프래그먼트에게 알린다.
                        // 20210310_carsung 개인 스케줄이 있어도 선택할 수 있도록 조건 추가
                        if (mList!![pos].isAvailableTime || mList!![pos].isPersonalSchedule) {
                            mList!![pos].availableChecked = !mList!![pos].availableChecked
                            listener!!.onItemClick(v, pos, mList!![pos].availableChecked)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.selection_item, parent, false)
        return SelectionViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: SelectionViewHolder, position: Int) {
        val selection = mList!![position]
        holder.id.text = selection.id.toString() //1~24을 text에 띄워줌

        holder.icon.setImageResource(R.drawable.empty_box)
        // empty_box와 같은 색으로 백그라운드 컬러 초기화
        holder.icon.setBackgroundResource(R.color.pinkish_grey_two)
        holder.icon.setPadding(0, 0, 0, 0)
        holder.txtPersonalSchedule.text = null

        selection.apply {
            if (availableChecked) {
                holder.icon.setImageResource(R.drawable.ic_check)
                holder.icon.setBackgroundColor(ContextCompat.getColor(context!!,R.color.primary))
                holder.icon.setPadding(30,30,30,30) //단위 dp랑 맞춰서
            } else if (isPersonalSchedule) {
                holder.icon.setBackgroundResource(R.drawable.selectpossible)
                holder.icon.setImageResource(R.drawable.personalschedule)
                holder.icon.setPadding(5,5,90,90)
                holder.txtPersonalSchedule.text = selection.titleOfSchedule
            } else if (isAvailableTime) {
                holder.icon.setImageResource(R.drawable.selectpossible)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    companion object {
        const val TAG = "SelectionAdapter"
    }
}