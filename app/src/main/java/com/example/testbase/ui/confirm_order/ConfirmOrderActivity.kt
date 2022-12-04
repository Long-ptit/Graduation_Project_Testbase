package com.example.testbase.ui.confirm_order

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivityConfirmOrderBinding
import com.example.testbase.model.*
import com.example.testbase.model_firebase.NotificationSend
import com.example.testbase.ui.cart.adapter.CartProductAdapter
import com.example.testbase.ui.confirm_order.adapter.OrderProductAdapter
import com.example.testbase.ui.main.MainActivity
import com.example.testbase.ui.main.MainSellerActivity
import com.example.testbase.ui.select_address.SelectAddressActivity
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
    lateinit var mAdapter: OrderProductAdapter
    var order = Order()
    var seller = Seller()
    var shippingInformation = ShippingInformation()

    override fun getContentLayout(): Int {
       return R.layout.activity_confirm_order
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ConfirmOrderViewModel::class.java)
    }

    override fun initView() {

        val layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rcv.layoutManager = layoutManager
        binding.rcv.adapter = mAdapter
        viewModel.getCart(FirebaseUtil.getUid())
        viewModel.getAllCartItemInCart(FirebaseUtil.getUid())
        viewModel.getDefaultShip()
    }



    override fun initListener() {
        binding.btnSave.setOnClickListener {
            handleSendItem()
        }

        binding.tvSelectAddress.setOnClickListener {
            startActivity(Intent(this@ConfirmOrderActivity, SelectAddressActivity::class.java))
        }


    }

    private fun handleSendItem() {
        order.status = Const.STATUS_ORDER_CONFIRM
        order.typeStatus = 1
        order.description = binding.edtDescription.toString()
        order.shippingInformation = ShippingInformation(
            id = shippingInformation.id
        )
        order.description = binding.edtDescription.text.toString()
        order.seller = seller
        viewModel.createOrder(order)
    }

    @SuppressLint("SetTextI18n")
    override fun observerLiveData() {
        viewModel.stateCart.observe(this@ConfirmOrderActivity) {
            binding.tvTotalPrice.text = it.totalPrice.toString() + getString(R.string.str_vnd)
            order.totalPrice = it.totalPrice
            order.totalQuantity = it.totalQuantity
            order.cart = it
        }

        viewModel.stateListAllProduct.observe(this@ConfirmOrderActivity) {
            seller = it.get(0).product.seller
            mAdapter.setData(it as ArrayList<CartItem>)
        }

        viewModel.stateCreateOrder.observe(this@ConfirmOrderActivity) {
//            FirebaseUtil.changeStatusOrder(it.data.id!!, Const.STATUS_ORDER_CONFIRM)
            showErrorStr("Ban da dat hang thanh cong, xem trong phan don hang ban nhe")
            FirebaseUtil.changeStatusOrderTest(EStatusOrder.CONFIRM, it.data.id!!) {

            }
            startActivity(Intent(this@ConfirmOrderActivity, MainActivity::class.java))
            finishAffinity()
        }

        viewModel.stateDefaultShip.observe(this@ConfirmOrderActivity) {
            shippingInformation = it
            showDataDefault(it)
        }
    }

    private fun showDataDefault(it: ShippingInformation) {
        binding.tvName.text = it.name
        binding.tvPhone.text = it.phone
        binding.tvAddress.text = it.address
    }
}