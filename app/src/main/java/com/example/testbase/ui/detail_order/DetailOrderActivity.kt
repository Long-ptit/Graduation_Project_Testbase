package com.example.testbase.ui.detail_order

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivityDetailOrderBinding
import com.example.testbase.databinding.ActivityDetailProductBinding
import com.example.testbase.databinding.LayoutBottomAddCartBinding
import com.example.testbase.model.EStatusOrder
import com.example.testbase.model.Order
import com.example.testbase.model.OrderItem
import com.example.testbase.model.Product
import com.example.testbase.ui.cart.CartActivity
import com.example.testbase.ui.confirm_order.adapter.OrderProductAdapter
import com.example.testbase.ui.detail_order.adapter.OrderDetailAdapter
import com.example.testbase.ui.order.adapter.OrderAdapter
import com.example.testbase.ui.rate_dialog.RatingDialog
import com.example.testbase.ui_common.chat.ChatActivity
import com.example.testbase.util.Const
import com.example.testbase.util.FirebaseUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailOrderActivity : BaseActivity<DetailOrderViewModel, ActivityDetailOrderBinding>() {

    private var mIdOrder = 0
    private var mIdOrderItem = 0
    private var mIdSeller = ""

    @Inject
    lateinit var mAdapter: OrderDetailAdapter

    override fun getContentLayout(): Int {
        return R.layout.activity_detail_order
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(DetailOrderViewModel::class.java)
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

        mAdapter.itemClickListener = {
            viewModel.checkReview(it)
            mIdOrderItem = it
        }

        binding.btnCancelOrder.setOnClickListener {
            viewModel.deleteOrder(mIdOrder, mIdSeller, EStatusOrder.CANCEL)
        }

        binding.btnChatWithSeller.setOnClickListener {
            val intent = Intent(binding.root.context, ChatActivity::class.java)
            intent.putExtra(Const.USER_ID, mIdSeller)
            startActivity(intent)
        }
    }

    override fun observerLiveData() {

        viewModel.stateOrderById.observe(this) {
            showData(it.data)
            mAdapter.setStateReview(it.data.typeStatus)
            mIdSeller = it.data.seller.id
        }

        viewModel.stateListOrderItem.observe(this) {
            mAdapter.setData(it.data as ArrayList<OrderItem>)
        }

        viewModel.stateCheckReview.observe(this) {
            val response = it.data
            if (response.equals("YES")) {
                val dialog = RatingDialog.newInstance(mIdOrderItem)
                dialog.show(
                    supportFragmentManager, null
                )
            } else {
                Toast.makeText(this, "Bạn đã đánh giá sản phẩm này rồi", Toast.LENGTH_SHORT).show()
            }
        }

        FirebaseUtil.getStatusOrder(mIdOrder) {
            binding.tvStatus.text = it?.status
            viewModel.getOrderById(mIdOrder)
        }
    }


    @SuppressLint("CheckResult", "SetTextI18n")
    private fun showData(it: Order) {
        it.apply {
            binding.tvName.text = shippingInformation.name
            binding.tvAddress.text = shippingInformation.address
            binding.tvPhone.text = shippingInformation.phone
            binding.tvShopName.text = it.seller.shopName
            binding.tvPrice.text = totalPrice.toString()
            binding.tvQuantity.text = totalQuantity.toString()
            binding.tvPaymentMethod.text = paymentType

            if (!typeStatus.equals(EStatusOrder.CONFIRM.id)) {
                binding.btnCancelOrder.visibility = View.GONE
            }
        }
    }

}