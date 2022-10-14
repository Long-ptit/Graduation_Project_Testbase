package com.example.testbase.ui.confirm_order

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivityConfirmOrderBinding
import com.example.testbase.model.CartItem
import com.example.testbase.ui.cart.adapter.CartProductAdapter
import com.example.testbase.ui.main.MainActivity
import com.example.testbase.ui.main.MainSellerActivity
import com.example.testbase.util.Const
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.util.NotificationUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ConfirmOrderActivity : BaseActivity<ConfirmOrderViewModel, ActivityConfirmOrderBinding>() {

    @Inject
    lateinit var mAdapter: CartProductAdapter

    override fun getContentLayout(): Int {
       return R.layout.activity_confirm_order
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ConfirmOrderViewModel::class.java)
    }

    override fun initView() {
        NotificationUtil.sendNotification(this, "Acivity", "Activity")

        val layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rcv.layoutManager = layoutManager
        binding.rcv.adapter = mAdapter
        viewModel.getCart(FirebaseUtil.getUid())
        viewModel.getAllCartItemInCart(FirebaseUtil.getUid())
    }



    @SuppressLint("SetTextI18n")
    override fun initListener() {
        viewModel.stateCart.observe(this@ConfirmOrderActivity) {
            binding.tvTotalPrice.text = it.totalPrice.toString() + getString(R.string.str_vnd)
        }

        viewModel.stateListAllProduct.observe(this@ConfirmOrderActivity) {
            mAdapter.setData(it as ArrayList<CartItem>)
        }

//
    }
    override fun observerLiveData() {
    }
}