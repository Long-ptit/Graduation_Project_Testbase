package com.example.testbase.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.testbase.R
import com.example.testbase.base.BaseFragment
import com.example.testbase.databinding.FragmentAccountBinding
import com.example.testbase.ui.cart.CartActivity
import com.example.testbase.ui.home.HomeViewModel
import com.example.testbase.ui.login.LoginActivity
import com.example.testbase.ui_seller.account.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : BaseFragment<AccountViewModel, FragmentAccountBinding>() {

//    private val args: AccountFragmentA
    override fun getContentLayout(): Int {
        return R.layout.fragment_account
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
    }

    override fun initView() {

    }

    override fun initListener() {
        binding.tvLoggout.setOnClickListener {
            viewModel.loggout()
            startActivity(Intent(context, LoginActivity::class.java))
        }

        binding.btnGoToCart.setOnClickListener {
            activity?.startActivity(Intent(context, CartActivity::class.java))
        }

    }

    override fun observerLiveData() {

    }

}