package com.example.testbase.ui.cart

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivityCartBinding
import com.example.testbase.model.Cart
import com.example.testbase.model.CartItem
import com.example.testbase.ui.cart.adapter.CartProductAdapter
import com.example.testbase.ui.confirm_order.ConfirmOrderActivity
import com.example.testbase.ui.home.HomeViewModel
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.util.LogUtil
import com.example.testbase.util.Util
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartActivity : BaseActivity<CartViewModel, ActivityCartBinding>() {

    @Inject
    lateinit var mAdapter: CartProductAdapter

    override fun getContentLayout(): Int {
        return R.layout.activity_cart
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
    }

    override fun initView() {
        val layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rcv.layoutManager = layoutManager
        binding.rcv.adapter = mAdapter
        getData();
    }

    private fun getData() {
        viewModel.getCart(FirebaseUtil.getUid())
        viewModel.getAllCartItemInCart(FirebaseUtil.getUid())
    }


    override fun initListener() {
        mAdapter.itemClickListener = {
                id, type ->
            when (type) {
                CartProductAdapter.TypeClick.DEC -> viewModel.changeQuantityProduct(id, 1)
                CartProductAdapter.TypeClick.INC -> viewModel.changeQuantityProduct(id, 0)
                CartProductAdapter.TypeClick.DELETE -> viewModel.deleteCartItem(id)
            }
        }

        binding.layoutGoToPayment.setOnClickListener {
            startActivity(Intent(this@CartActivity, ConfirmOrderActivity::class.java))
        }
    }

    override fun observerLiveData() {
        viewModel.stateCart.observe(this@CartActivity) {
            showData(it)
        }

        viewModel.stateListAllProduct.observe(this@CartActivity) {
            mAdapter.setData(it as ArrayList<CartItem>)
        }

        viewModel.stateChangeQuantity.observe(this@CartActivity) {
            getData()
        }

        viewModel.stateDeleteCartItem.observe(this@CartActivity) {
            getData()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun showData(cart: Cart) {
        binding.tvSumPrice.text = Util.converCurrency(cart.totalPrice.toDouble()) + getString(R.string.str_vnd)
        binding.tvSumQuantity.text = cart.totalQuantity.toString() + " Sp"
    }
}