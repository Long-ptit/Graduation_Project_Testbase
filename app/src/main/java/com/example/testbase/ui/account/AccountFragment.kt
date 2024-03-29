package com.example.testbase.ui.account

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.testbase.R
import com.example.testbase.base.BaseFragment
import com.example.testbase.databinding.FragmentAccountBinding
import com.example.testbase.ui.cart.CartActivity
import com.example.testbase.ui.login.LoginActivity
import com.example.testbase.ui_seller.account.AccountViewModel
import com.example.testbase.util.Const
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.util.LogUtil
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
        viewModel.getUserInfo(FirebaseUtil.getUid())
    }

    override fun initListener() {
        binding.btnLoggout.setOnClickListener {
            viewModel.loggout()
            startActivity(Intent(context, LoginActivity::class.java))
            requireActivity().finishAffinity()
        }

        binding.layoutCart.setOnClickListener {
            activity?.startActivity(Intent(context, CartActivity::class.java))
        }
    }

    override fun observerLiveData() {
        viewModel.stateUser.observe(viewLifecycleOwner) {
          val user = it
            binding.tvName.text = user.name
            Glide
                .with(binding.root.context)
                .load(Const.BASE_URL + Const.PATH_IMAGE + user.id + ".jpg")
                .into(binding.imgUser)
            binding.tvPhone.text = user.phone
            binding.tvAddress.text = user.address
        }
    }

}