package com.example.testbase.ui_seller.statistic

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.*
import com.example.testbase.model.*
import com.example.testbase.model_firebase.NotificationSend
import com.example.testbase.ui.cart.CartActivity
import com.example.testbase.ui.confirm_order.adapter.OrderProductAdapter
import com.example.testbase.ui.detail_order.adapter.OrderDetailAdapter
import com.example.testbase.ui.order.adapter.OrderAdapter
import com.example.testbase.util.Const
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.util.LogUtil
import com.example.testbase.util.NotificationUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.restaurant.exam.ui.detail_statistic.DetailStatisticActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StatisticSellerActivity :
    BaseActivity<StatisticSellerViewModel, ActivityStatisticOrderSellerBinding>() {


    @Inject
    lateinit var mAdapter: OrderDetailAdapter

    override fun getContentLayout(): Int {
        return R.layout.activity_statistic_order_seller
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(StatisticSellerViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
//        val layoutManager = LinearLayoutManager(
//            this,
//            LinearLayoutManager.VERTICAL,
//            false
//        )
//        binding.rcv.layoutManager = layoutManager
//        binding.rcv.adapter = mAdapter


    }

    override fun initListener() {
        binding.btnKhoangNgay.setOnClickListener {
            var intent = Intent(
                this@StatisticSellerActivity,
                DetailStatisticActivity::class.java
            )
            startActivity(intent)
        }
    }

    override fun observerLiveData() {

    }


}