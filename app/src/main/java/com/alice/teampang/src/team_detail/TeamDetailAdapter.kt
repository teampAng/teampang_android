package com.alice.teampang.src.team_detail

import android.content.Context
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.databinding.TeamDetailListBinding
import com.alice.teampang.src.team_detail.model.TeamDetailData

class TeamDetailAdapter (private val context : Context) : RecyclerView.Adapter<TeamDetailAdapter.ProfileVH>() {

    var data = listOf<TeamDetailData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileVH {
        val binding = TeamDetailListBinding.inflate(
            LayoutInflater.from(context), parent, false
        )

        return ProfileVH(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ProfileVH, position: Int) {
        holder.onBind(data[position])
        if (holder.binding.shareZoom.visibility == View.VISIBLE) {
            holder.binding.shareZoom.setOnClickListener{

            }
        }
    }

    class ProfileVH(val binding: TeamDetailListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: TeamDetailData) {
            binding.team = data

            val spannableString1 =
                SpannableString("ZOOM 링크 공유하기") //나중에 string resource 파일로 바꾸기 getString(resId)
            spannableString1.setSpan(UnderlineSpan(), 0, spannableString1.length, 0)
            binding.shareZoom.text = spannableString1

            val spannableString2 =
                SpannableString("ZOOM 예약하기")
            spannableString2.setSpan(UnderlineSpan(), 0, spannableString2.length, 0)
            binding.makeZoom.text = spannableString2
        }
    }
}