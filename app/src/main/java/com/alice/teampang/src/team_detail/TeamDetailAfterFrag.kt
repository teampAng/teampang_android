package com.alice.teampang.src.team_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alice.teampang.R
import com.alice.teampang.databinding.FragTeamDetailAfterBinding
import com.alice.teampang.databinding.FragTeamDetailBeforeBinding
import com.alice.teampang.src.BaseFrag

class TeamDetailAfterFrag : BaseFrag(), View.OnClickListener {

    lateinit var navController : NavController

    private var _binding: FragTeamDetailAfterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragTeamDetailAfterBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.btnBack.setOnClickListener(this)
        binding.btnInvite.setOnClickListener(this)
        binding.btnShare.setOnClickListener(this)
    }

    override fun onClick(v: View) {

        when(v.id) {
            R.id.btn_back -> navController.popBackStack()
            R.id.btn_invite -> {}
            R.id.btn_share -> {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

