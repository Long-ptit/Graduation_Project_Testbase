package com.example.testbase.ui_seller.profile_seller

import android.annotation.SuppressLint
import android.content.Intent
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
import com.example.testbase.model.Order
import com.example.testbase.model.OrderItem
import com.example.testbase.model.Product
import com.example.testbase.model.Seller
import com.example.testbase.model_firebase.NotificationSend
import com.example.testbase.ui.cart.CartActivity
import com.example.testbase.ui.confirm_order.adapter.OrderProductAdapter
import com.example.testbase.ui.detail_order.adapter.OrderDetailAdapter
import com.example.testbase.ui.order.adapter.OrderAdapter
import com.example.testbase.ui_common.chat.ChatActivity
import com.example.testbase.ui_seller.profile_seller.adapter.ProductProfileAdapter
import com.example.testbase.util.Const
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.util.LogUtil
import com.example.testbase.util.NotificationUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileSellerActivity : BaseActivity<ProfileSellerViewModel, ActivityProfileSellerBinding>() {


    @Inject
    lateinit var mAdapter: ProductProfileAdapter
    private lateinit var idSeller: String

    override fun getContentLayout(): Int {
        return R.layout.activity_profile_seller
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ProfileSellerViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        idSeller = intent.getStringExtra(Const.SELLER_ID).toString()
        binding.rcv.adapter = mAdapter
        viewModel.getListProductSeller(idSeller)
        viewModel.getInforSeller(idSeller)

    }

    override fun initListener() {
        binding.apply {
            tvChat.setOnClickListener {
                val intent = Intent(this@ProfileSellerActivity, ChatActivity::class.java)
                intent.putExtra(Const.USER_ID, idSeller)
                startActivity(intent)
                LogUtil.log(idSeller)
            }
        }
    }

    override fun observerLiveData() {
        viewModel.apply {
            listProductSeller.observe(this@ProfileSellerActivity) {
                mAdapter.setData(it as ArrayList<Product>)
            }
            sellerInfor.observe(this@ProfileSellerActivity) {
                showData(it.data)
            }
        }
    }

    private fun showData(it: Seller) {
        binding.tvShopName.text = it.shopName
        binding.tvAddress.text = it.address
    }


}