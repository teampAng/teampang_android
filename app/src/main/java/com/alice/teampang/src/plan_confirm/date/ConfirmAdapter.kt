package com.alice.teampang.src.plan_confirm.date

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

class ConfirmAdapter() : RecyclerView.Adapter<ConfirmAdapter.ConfirmViewHolder>() {
    private var listener: ItemClickListener? = null
    private var mList //recyclerview를 생성하기 위한 0~23이 담기는 리스트
            : ArrayList<Confirm>? = null
    private var context : Context? = null
    constructor(context: Context?, selections: ArrayList<Confirm>) : this() {
        // 프래그먼트에서 넘어온 Selections를 Adapter의 mList에 대입
        mList = selections
        this.context = context
    }

    // position과 check 여부를 알려주는 리스너 콜백을 정의
    interface ItemClickListener {
        fun onMemberNoneClick(v: View?, position: Int, check: Boolean)
        fun onMemberNotNoneClick(v: View?, position: Int, check: Boolean)
    }

    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    inner class ConfirmViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id: TextView = view.findViewById<View>(R.id.horizon_description) as TextView
        var icon: ImageView = view.findViewById<View>(R.id.horizon_icon) as ImageView
        var txtPersonalSchedule: TextView = view.findViewById(R.id.txt_schedule)

        init {
            // View에 클릭 리스너를 붙인다
            view.setOnClickListener { v ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {

                    if (!mList!![pos].membernotnone) {
                        // 체크가 가능한 시간대이면 체크 선택/해제 처리 후 프래그먼트에게 알린다.
                        if (listener != null) {
                            //불가능한 사람이 없음
                            mList!![pos].membernone = !mList!![pos].membernone
                            listener!!.onMemberNoneClick(v, pos, mList!![pos].membernone)
                        }
                    } else{
                        //불가능한 사람이 있음
                        // carsung membernotnone의 값은 변경되지 않아야 한다.
                        // 그리고 다이얼로그를 띄우고 yes를 선택하면 membernone의 값을 변경하여야 한다.
                        // mList!![pos].membernotnone = !mList!![pos].membernotnone
                        listener!!.onMemberNotNoneClick(v, pos, mList!![pos].membernotnone)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfirmViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.selection_item, parent, false)
        return ConfirmViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ConfirmViewHolder, position: Int) {
        val selection = mList!![position]
        holder.id.text = selection.id.toString() //1~24을 text에 띄워줌

        holder.icon.setImageResource(R.drawable.empty_box)
        // empty_box와 같은 색으로 백그라운드 컬러 초기화
        holder.icon.setBackgroundResource(R.color.pinkish_grey_two)
        holder.icon.setPadding(0, 0,0, 0)
        holder.txtPersonalSchedule.text = null

        selection.apply {
            if (isAvailableTime) {
                holder.icon.setImageResource(R.drawable.selectpossible)

                if (membernone){
                    holder.icon.setImageResource(R.drawable.ic_check)
                    holder.icon.setBackgroundColor(ContextCompat.getColor(context!!,R.color.primary))
                    holder.icon.setPadding(30,30,30,30)
                } else if (membernotnone){
                    holder.icon.setBackgroundResource(R.drawable.selectpossible)
                    holder.icon.setImageResource(R.drawable.ic_exclamation)
                    holder.icon.setPadding(40,40,40,40)
                }
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