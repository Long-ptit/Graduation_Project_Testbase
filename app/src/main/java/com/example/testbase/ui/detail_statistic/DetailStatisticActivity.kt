package com.restaurant.exam.ui.detail_statistic

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.LayoutDetailStatisticBinding
import com.example.testbase.ui.detail_statistic.DetailStatisticViewModel
import com.example.testbase.ui_seller.account.AccountViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.restaurant.exam.ui.detail_statistic.adapter.DetailStatisticAdapter
import dagger.hilt.android.AndroidEntryPoint

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class DetailStatisticActivity :
    BaseActivity<DetailStatisticViewModel, LayoutDetailStatisticBinding>() {

    lateinit var mAdapter: DetailStatisticAdapter

    var from: String = ""
    var to: String = ""

    override fun getContentLayout(): Int {
        return R.layout.layout_detail_statistic
    }

    override fun observerLiveData() {

    }

    override fun initView() {


        mAdapter = DetailStatisticAdapter()
        binding.rcv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rcv.adapter = mAdapter

    }


    override fun initListener() {
        binding.edtTime.setOnClickListener {
            showDataRangePicker()
        }
//        binding.btnGo.setOnClickListener {
//            Log.d("ptit", "show spin: " + binding.sp.selectedItemPosition)
//            viewModel.getListBill(from, to, binding.sp.selectedItemPosition)
//        }
    }

    private fun showDataRangePicker() {

        val dateRangePicker =
            MaterialDatePicker
                .Builder.dateRangePicker()
                .setTitleText("Select Date")
                .build()

        dateRangePicker.show(
            supportFragmentManager,
            "date_range_picker"
        )

        dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->

            val startDate = TimeConverday2(dateSelected.first)
            val endDate = TimeConverday2(dateSelected.second)
            from = startDate.toString()
            to = endDate.toString()
            binding.edtTime.setText(startDate + " Đến " + endDate)
        }

    }

    fun TimeConverday2(unixTime: Long): String? {
        val date = Date(unixTime)
        // format of the date
        val jdf = SimpleDateFormat("yyyy-MM-dd")
        return jdf.format(date)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(DetailStatisticViewModel::class.java)

    }
}