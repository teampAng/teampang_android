package com.alice.teampang.src.main.`when`


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alice.teampang.R
import com.alice.teampang.databinding.FragmentDateBinding
import com.alice.teampang.base.BaseFrag
import com.alice.teampang.src.error.model.ErrorResponse
import com.alice.teampang.model.ConfirmedTimes
import com.alice.teampang.model.Results
import com.alice.teampang.model.WhenResponse
import com.alice.teampang.ui.adapter.CustomAdapter
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WhenFrag : BaseFrag(), View.OnClickListener, WhenFragView {

    private var _binding: FragmentDateBinding? = null
    private val binding get() = _binding!!
    private var mainData: ArrayList<homedata>? = ArrayList()
    private var mainData2: ArrayList<Results> = ArrayList()
    private var confirmedTimes: ArrayList<ConfirmedTimes> = ArrayList()
    private lateinit var adapter: CustomAdapter
    private var id : Int? = null
    @SuppressLint("CutPasteId")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDateBinding.inflate(inflater, container, false)
        val view = binding.root
        settings()
        getRecyclerView()
        setUrgentPlan()
        getData()

//        binding.dateText12.setOnClickListener {
//            navController.navigate(R.id.action_mainFrag_to_teamDetailBeforeFrag)
//        }
//        binding.test.setOnClickListener {
//            navController.navigate(R.id.action_mainFrag_to_teamDetailAfterFrag)
//        }

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
        mainData!!.add(homedata(1, "팀프앙 회의1", "2021-03-15", "우리집1", countdday("2021-03-15")))
        mainData!!.add(homedata(2, "팀프앙 회의2", "2021-03-14", null, countdday("2021-03-14")))
        mainData!!.add(homedata(3, "팀프앙 회의3", "2021-03-17", "우리집3", countdday("2021-03-17")))
        mainData!!.add(homedata(4, "팀프앙 회의4", "2021-03-18", "우리집4", countdday("2021-03-18")))

        mainData!!.sortWith(Comparator { o1, o2 ->
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
        if (mainData!!.size > 1) {
            binding.dateText5.setText(mainData!![0].planplace)
            binding.dateText4.setText(mainData!![0].plantime)
            binding.dateText0.setText(mainData!![0].planname)
            binding.urgentImage.setImageResource(images[rand.nextInt(images.size)])
            if (mainData!![0].planplace == null) {
                binding.dateText5.setText("장소 미정")
            } else {
                binding.dateText4.setText("시간 정보가 없습니다.")
                binding.dateText5.setText("장소 정보가 없습니다.")
                binding.dateText0.setText("임박한 일정이 없습니다.")
            }
        }
    }

    private fun getData() {
        val whenService = WhenService(this)
        whenService.getWhen()
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnMypage -> {
                navController.navigate(R.id.action_mainFrag_to_myPageFrag)
            }
//            binding.test -> {navController.navigate(R.id.action_mainFrag_to_teamDetailBeforeFrag)}
//            binding.dateText12 -> {navController.navigate(R.id.action_mainFrag_to_teamDetailAfterFrag)}
            binding.btnPlanCreate -> {
                navController.navigate(R.id.action_mainFrag_to_planCreateNameFrag)
            }
        }
    }

    private fun getRecyclerView() {
        adapter = CustomAdapter(mainData2){
            //Todo 상세화면 이동 코드
        }
        binding.recyclerviewMainList.adapter = adapter

        binding.recyclerviewMainList.layoutManager =
            LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
        binding.recyclerviewMainList.setHasFixedSize(true)
    }

    override fun WhenSuccess(whenResponse: WhenResponse) {
        when (whenResponse.status) {
            200 -> {
                try {
                    mainData2.addAll(whenResponse.data[0].results)
                    id = mainData2[0].id
                    adapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            else -> showCustomToast(whenResponse.message)
        }
    }

    override fun WhenError(errorResponse: ErrorResponse) {
    }

    override fun WhenFailure(message: Throwable?) {
    }
}



