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
import com.example.testbase.databinding.ActivityDetailOrderBinding
import com.example.testbase.databinding.ActivityDetailOrderSellerBinding
import com.example.testbase.databinding.ActivityDetailProductBinding
import com.example.testbase.databinding.LayoutBottomAddCartBinding
import com.example.testbase.model.Order
import com.example.testbase.model.OrderItem
import com.example.testbase.model.Product
import com.example.testbase.model_firebase.NotificationSend
import com.example.testbase.ui.cart.CartActivity
import com.example.testbase.ui.confirm_order.adapter.OrderProductAdapter
import com.example.testbase.ui.detail_order.adapter.OrderDetailAdapter
import com.example.testbase.ui.order.adapter.OrderAdapter
import com.example.testbase.util.Const
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.util.NotificationUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileSellerActivity : BaseActivity<ProfileSellerViewModel, ActivityDetailOrderSellerBinding>() {

    private var mIdOrder = 0
    private var mOrder: Order = Order()
    @Inject
    lateinit var mAdapter: OrderDetailAdapter

    override fun getContentLayout(): Int {
        return R.layout.activity_detail_order_seller
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ProfileSellerViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        val layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rcv.layoutManager = layoutManager
        binding.rcv.adapter = mAdapter

        mIdOrder = intent.getIntExtra(Const.ORDER_ID, 0)
        viewModel.getOrderById(mIdOrder)
        viewModel.getOrderItemByOrder(mIdOrder)

    }

    override fun initListener() {
        binding.toolbar.getBackButton().setOnClickListener {
            finish()
        }

        binding.btnConfirmOrder.setOnClickListener {
            viewModel.changeStatusOrder(Const.STATUS_ORDER_CONFIRM, mIdOrder, "Đã xác nhận đơn hàng")
            binding.btnConfirmOrder.visibility = View.GONE
        }

        binding.btnSendShip.setOnClickListener {
            viewModel.changeStatusOrder(Const.STATUS_ORDER_SHIPPING, mIdOrder, "Đã được gửi tới đơn vị vận chuyển")

        }

        binding.btnChat.setOnClickListener {
           // FirebaseUtil.changeStatusOrder(mIdOrder, Const.STATUS_ORDER_PROCESSING)
        }

        binding.btnCancelOrder.setOnClickListener {
            viewModel.changeStatusOrder(Const.STATUS_ORDER_CANCEL, mIdOrder, "Đơn hàng của bạn đã bị hủy")
            binding.btnConfirmOrder.visibility = View.GONE
            binding.btnSendShip.visibility = View.GONE
        }


    }

    override fun observerLiveData() {

        viewModel.stateOrderById.observe(this) {
            mOrder = it.data
            showData(it.data)
        }

        viewModel.stateListOrderItem.observe(this) {
            mAdapter.setData(it.data as ArrayList<OrderItem>)
        }

        FirebaseUtil.getStatusOrder(mIdOrder) {
            binding.tvStatus.text = it
        }
    }


    @SuppressLint("CheckResult", "SetTextI18n")
    private fun showData(it: Order) {
        it.apply {
            binding.tvName.text = shippingInformation.name
            binding.tvAddress.text = shippingInformation.address
            binding.tvPhone.text = shippingInformation.phone
            binding.tvShopName.text = seller.shopName
            binding.tvPrice.text = totalPrice.toString() + getString(R.string.str_vnd)
            binding.tvQuantity.text = totalQuantity.toString()
           // binding.tvStatus.text = status
        }
    }

}