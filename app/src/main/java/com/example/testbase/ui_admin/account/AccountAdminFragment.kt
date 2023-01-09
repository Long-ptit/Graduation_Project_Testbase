package com.example.testbase.ui_admin.account

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
import com.example.testbase.databinding.FragmentAccountAdminBinding
import com.example.testbase.databinding.FragmentAccountSellerBinding
import com.example.testbase.model.Seller
import com.example.testbase.ui.login.LoginActivity
import com.example.testbase.ui.login.LoginViewModel
import com.example.testbase.ui_seller.statistic.StatisticSellerActivity
import com.example.testbase.util.FirebaseUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountAdminFragment : BaseFragment<AdminViewModel, FragmentAccountAdminBinding>() {
    override fun getContentLayout(): Int {
       return R.layout.fragment_account_admin
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
    }

    override fun initView() {

    }

    override fun initListener() {
        binding.btnLoggout.setOnClickListener {
            viewModel.loggout()
            startActivity(Intent(context, LoginActivity::class.java))
            requireActivity().finishAffinity()
        }

    }

    override fun observerLiveData() {


    }



}