package com.alice.teampang.src.team_detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alice.teampang.R
import com.alice.teampang.databinding.FragTeamDetailBeforeBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.src.team_detail.model.TeamMemberData


class TeamDetailBeforeFrag : BaseFrag(), View.OnClickListener {

    private var _binding: FragTeamDetailBeforeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragTeamDetailBeforeBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        setRcvMember()

        binding.btnBack.setOnClickListener(this)
        binding.btnShowList.setOnClickListener(this)
        binding.btnInvite.setOnClickListener(this)
        binding.btnConfirm.setOnClickListener(this)
    }

    override fun onClick(v: View) {

        when(v.id) {
            R.id.btn_back -> navController.popBackStack()
            R.id.btn_show_list -> {
                if (binding.arrowUp.visibility == View.VISIBLE) {
                    binding.arrowUp.visibility = View.GONE
                    binding.arrowDown.visibility = View.VISIBLE
                    binding.rvMember.visibility = View.GONE
                } else {
                    binding.arrowUp.visibility = View.VISIBLE
                    binding.arrowDown.visibility = View.GONE
                    binding.rvMember.visibility = View.VISIBLE
                }
            }
            R.id.btn_invite -> inviteDialog()
            R.id.btn_confirm -> {
            }
        }
    }

    private fun setRcvMember() {
        val teamMemberAdapter = TeamMemberAdapter(requireContext())
        binding.rvMember.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMember.adapter = teamMemberAdapter
        teamMemberAdapter.data = listOf(
            TeamMemberData("sk"),
            TeamMemberData("sk"),
            TeamMemberData("sk"),
            TeamMemberData("sk"),
            TeamMemberData("sk"),
            TeamMemberData("sk"),
            TeamMemberData("sk"),
            TeamMemberData("sk")
        )
        teamMemberAdapter.notifyDataSetChanged()
    }

    private fun inviteDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_invite_member, null)
        val btn_kakao = dialogView.findViewById<TextView>(R.id.btn_kakao)
        val btn_link = dialogView.findViewById<TextView>(R.id.btn_link)
        val btn_pop = dialogView.findViewById<TextView>(R.id.btn_pop)
        val dialog: AlertDialog = builder.setView(dialogView).create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.show()

        btn_kakao.setOnClickListener {
            dialog.dismiss()
        }
        btn_link.setOnClickListener {
            val clipboard: ClipboardManager =
                requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", "Text to copy")
            clipboard.setPrimaryClip(clip)
            showCustomToast("초대링크가 복사되었습니다")
            dialog.dismiss()
        }
        btn_pop.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

