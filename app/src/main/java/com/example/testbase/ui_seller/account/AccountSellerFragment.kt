package com.example.testbase.ui_seller.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.testbase.R
import com.example.testbase.base.BaseFragment
import com.example.testbase.databinding.FragmentAccountSellerBinding
import com.example.testbase.model.Seller
import com.example.testbase.ui.login.LoginActivity
import com.example.testbase.ui.login.LoginViewModel
import com.example.testbase.ui_seller.statistic.StatisticSellerActivity
import com.example.testbase.util.FirebaseUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountSellerFragment : BaseFragment<AccountViewModel, FragmentAccountSellerBinding>() {
    override fun getContentLayout(): Int {
       return R.layout.fragment_account_seller
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
    }

    override fun initView() {
        viewModel.getSellerInfo(FirebaseUtil.getUid())
    }

    override fun initListener() {

        binding.btnLoggout.setOnClickListener {
            viewModel.loggout()
        }


        binding.tvName.setOnClickListener {
            viewModel.loggout()
            startActivity(Intent(context, LoginActivity::class.java))
        }

        binding.tvStatistic.setOnClickListener {
            viewModel.loggout()
            startActivity(Intent(context, StatisticSellerActivity::class.java))
        }
    }

    override fun observerLiveData() {
        viewModel.stateSeller.observe(viewLifecycleOwner) {
            val seller = it.data
            showData(seller)
        }

    }

    private fun showData(seller: Seller) {
        binding.tvName.text = seller.shopName
        binding.tvPhone.text = seller.phone
        binding.tvAddress.text = seller.address
    }

}