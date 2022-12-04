package com.example.testbase.ui.select_address

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivityDetailProductBinding
import com.example.testbase.databinding.ActivityDetailReviewBinding
import com.example.testbase.databinding.ActivitySelectAddressBinding
import com.example.testbase.databinding.LayoutBottomAddCartBinding
import com.example.testbase.model.Product
import com.example.testbase.model.Review
import com.example.testbase.model.ShippingInformation
import com.example.testbase.model_response.StatisReviewResponse
import com.example.testbase.ui.cart.CartActivity
import com.example.testbase.ui.cart.adapter.CartProductAdapter
import com.example.testbase.ui.rate_dialog.adapter.CommentAdapter
import com.example.testbase.ui.select_address.adapter.AddressAdapter
import com.example.testbase.util.Const
import com.example.testbase.util.LogUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SelectAddressActivity : BaseActivity<SelectAddressViewmodel, ActivitySelectAddressBinding>() {

    @Inject
    lateinit var mAdapter: AddressAdapter

    override fun getContentLayout(): Int {
        return R.layout.activity_select_address
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(SelectAddressViewmodel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        setUpAdapter()
        viewModel.getAll()
    }

    private fun setUpAdapter() {
        val layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rcv.layoutManager = layoutManager
        binding.rcv.adapter = mAdapter
        mAdapter.setData(getFakeData())
    }

    private fun getFakeData(): ArrayList<ShippingInformation> {
        val list: ArrayList<ShippingInformation> = arrayListOf()
        list.add(ShippingInformation(name = "Trinh long", phone = "0373443053", address = "xala da hong", isDefault = true))
        list.add(ShippingInformation(name = "Trinh long1", phone = "0373443053", address = "xala da hong", isSelected = true))
        list.add(ShippingInformation(name = "Trinh long2", phone = "0373443053", address = "xala da hong"))
        list.add(ShippingInformation(name = "Trinh long3", phone = "0373443053", address = "xala da hong"))
        list.add(ShippingInformation(name = "Trinh long4", phone = "0373443053", address = "xala da hong"))


        return list
    }

    override fun initListener() {

        binding.toolbar.getBackButton().setOnClickListener {
            finish()
        }

        mAdapter.itemClickListener = {
                id, type ->
            when (type) {
                AddressAdapter.TypeClick.CLICK_ITEM -> LogUtil.log("kaka")
                else -> LogUtil.log("co con caw")
            }
        }



    }

    override fun observerLiveData() {
        viewModel.apply {
            stateAllShip.observe(this@SelectAddressActivity) {
                mAdapter.setData(it as ArrayList<ShippingInformation>)
            }
        }
    }


}