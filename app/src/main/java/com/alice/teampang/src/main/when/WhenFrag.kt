package com.alice.teampang.src.main.`when`


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alice.teampang.R
import com.alice.teampang.databinding.FragmentDateBinding
import com.alice.teampang.src.BaseFrag
import com.alice.teampang.ui.adapter.CustomAdapter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.days

class WhenFrag : BaseFrag(), View.OnClickListener {

    private var _binding: FragmentDateBinding? = null
    private val binding get() = _binding!!
    private var maindata: ArrayList<homedata>? = ArrayList()
    private var data2: homedata? = null


    @SuppressLint("CutPasteId")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDateBinding.inflate(inflater, container, false)
        val view = binding.root

        settings()
        putdata()
        setUrgentPlan()
        val adapter = CustomAdapter(requireContext(), maindata!!)
        view.findViewById<RecyclerView>(R.id.recyclerview_main_list).adapter = adapter
        view.findViewById<RecyclerView>(R.id.recyclerview_main_list).layoutManager =
            LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.btnMypage.setOnClickListener(this)
        binding.btnPlanCreate.setOnClickListener(this)

    }


    fun settings() {
        binding.dateText6.setText("전체 팀원 알림 전송")
    }

    @SuppressLint("SimpleDateFormat")
    fun putdata() {
        maindata!!.add(homedata(1, "팀프앙 회의1", "2021-03-15", "우리집1", countdday("2021-03-15")))
        maindata!!.add(homedata(2, "팀프앙 회의2", "2021-03-14", null, countdday("2021-03-14")))
        maindata!!.add(homedata(3, "팀프앙 회의3", "2021-03-17", "우리집3", countdday("2021-03-17")))
        maindata!!.add(homedata(4, "팀프앙 회의4", "2021-03-18", "우리집4", countdday("2021-03-18")))

        maindata!!.sortWith(Comparator { o1, o2 ->
            val firstplantime = o1?.plantime
            val secondplantime = o2?.plantime
            val format = SimpleDateFormat("yyyy-MM-dd")
            val date: Date? = format.parse(firstplantime)
            val date2: Date? = format.parse(secondplantime)
            //날짜값 변환 후 비교
            date!!.compareTo(date2)
        })
    }

    //현재날짜 - plantime 현재날짜
    fun countdday(plantime: String): String {
        val calendar = Calendar.getInstance()
        val dCalendar = Calendar.getInstance()
        val arr = plantime.split("-").toTypedArray()
        val now: Long = System.currentTimeMillis()
        val date = Date(now)
        val curYearFormat = SimpleDateFormat("yyyy", Locale.KOREA)
        val curMonthFormat = SimpleDateFormat("MM", Locale.KOREA)
        val curDayFormat = SimpleDateFormat("dd", Locale.KOREA)
        calendar.set(
            curYearFormat.format(date).toInt(),
            curMonthFormat.format(date).toInt(),
            curDayFormat.format(date).toInt()
        )
        dCalendar.set(arr[0].toInt(), arr[1].toInt(), arr[2].toInt())
        val t = calendar.getTimeInMillis()                //오늘 날짜를 밀리타임으로 바꿈
        val d = dCalendar.getTimeInMillis()               //디데이날짜를 밀리타임으로 바꿈
        val r = (d - t) / (24 * 60 * 60 * 1000)
        return r.toString()
    }


    fun setUrgentPlan() {
        val images = intArrayOf(
            R.drawable.list__icon1,
            R.drawable.list__icon2,
            R.drawable.list__icon3,
            R.drawable.list__icon4,
            R.drawable.list__icon5,
            R.drawable.list__icon6
        )
        val rand = Random()
        if (maindata!!.size > 1) {
            binding.dateText5.setText(maindata!![0].planplace)
            binding.dateText4.setText(maindata!![0].plantime)
            binding.dateText0.setText(maindata!![0].planname)
            binding.urgentImage.setImageResource(images[rand.nextInt(images.size)])
            if (maindata!![0].planplace == null) {
                binding.dateText5.setText("장소 미정")
            } else {
                binding.dateText4.setText("시간 정보가 없습니다.")
                binding.dateText5.setText("장소 정보가 없습니다.")
                binding.dateText0.setText("임박한 일정이 없습니다.")
            }
        }
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnMypage -> navController.navigate(R.id.action_mainFrag_to_myPageFrag)
            binding.btnPlanCreate -> {
                navController.navigate(R.id.action_mainFrag_to_planCreateNameFrag)
            }
        }
    }
}


// adapter.notifyDataSetChanged()
