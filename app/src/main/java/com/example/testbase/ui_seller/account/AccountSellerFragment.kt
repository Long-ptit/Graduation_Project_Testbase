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
import com.example.testbase.ui.login.LoginActivity
import com.example.testbase.ui.login.LoginViewModel
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

    }

    override fun initListener() {

        binding.btnLoggout.setOnClickListener {
            Log.d("ptit", "click dang xuat: ")
            viewModel.loggout()
        }

        binding.btnLoggout1.setOnClickListener {
            Log.d("ptit", "click dang xuat: ")
            viewModel.loggout()
            startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun observerLiveData() {

    }

}