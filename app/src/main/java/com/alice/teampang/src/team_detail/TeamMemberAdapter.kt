package com.alice.teampang.src.team_detail

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.databinding.TeamMemberListBinding
import com.alice.teampang.src.team_detail.model.TeamMemberData

class TeamMemberAdapter (private val context : Context) : RecyclerView.Adapter<TeamMemberAdapter.ProfileVH>() {

    var data = listOf<TeamMemberData>()
    var color = listOf("#acdbdb", "#f8bdbb", "#e5efbb", "#d8c5e8", "#e8cbcb", "#d1d1d1")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileVH {
        val binding = TeamMemberListBinding.inflate(
            LayoutInflater.from(context), parent, false
        )

        return ProfileVH(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ProfileVH, position: Int) {
        holder.onBind(data[position])
        holder.binding.iv.setBackgroundColor(Color.parseColor(color[position%6]))
    }

    class ProfileVH(val binding: TeamMemberListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: TeamMemberData) {
            binding.team = data
        }
    }
}