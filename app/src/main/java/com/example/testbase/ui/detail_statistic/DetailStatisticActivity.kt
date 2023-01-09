package com.restaurant.exam.ui.detail_statistic

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.LayoutDetailStatisticBinding
import com.example.testbase.model.Order
import com.example.testbase.ui.detail_statistic.DetailStatisticViewModel
import com.example.testbase.ui_seller.account.AccountViewModel
import com.example.testbase.util.LogUtil
import com.example.testbase.util.Util
import com.google.android.material.datepicker.MaterialDatePicker
import com.restaurant.exam.ui.detail_statistic.adapter.DetailStatisticAdapter
import dagger.hilt.android.AndroidEntryPoint

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class DetailStatisticActivity :
    BaseActivity<DetailStatisticViewModel, LayoutDetailStatisticBinding>() {

    @Inject
    lateinit var mAdapter: DetailStatisticAdapter

    var from: String = ""
    var to: String = ""
    var totalAmount = 0

    override fun getContentLayout(): Int {
        return R.layout.layout_detail_statistic
    }

    override fun observerLiveData() {
        obserListOrderStatistic()
    }

    private fun obserListOrderStatistic() {
        viewModel.stateListOrderStatistic.observe(this@DetailStatisticActivity) {
            mAdapter.setData(it as ArrayList<Order>)
            totalAmount = it.sumOf { it.totalPrice }.toInt()
            binding.tvSum.text = "Tổng tiền thu được: " + Util.converCurrency(totalAmount.toDouble())
            binding.tvSum.visibility = View.VISIBLE
        }
    }

    override fun initView() {
        setUpRcv()
        binding.tvSum.visibility = View.GONE
    }

    private fun setUpRcv() {
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
        binding.btnGo.setOnClickListener {
            LogUtil.log("Check spinner: " + binding.sp.selectedItemPosition)
            viewModel.getStatisticByTime(from, to, binding.sp.selectedItemPosition)
        }
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