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
import com.example.testbase.databinding.*
import com.example.testbase.model.Product
import com.example.testbase.model.Review
import com.example.testbase.model.ShippingInformation
import com.example.testbase.model.User
import com.example.testbase.model_response.StatisReviewResponse
import com.example.testbase.ui.cart.CartActivity
import com.example.testbase.ui.cart.adapter.CartProductAdapter
import com.example.testbase.ui.rate_dialog.adapter.CommentAdapter
import com.example.testbase.ui.select_address.adapter.AddressAdapter
import com.example.testbase.util.Const
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.util.LogUtil
import com.example.testbase.util.SharePreferenceUtil
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

    override fun onResume() {
        super.onResume()
        LogUtil.log("Go to resumse select activity")
        viewModel.getAll()
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
    }



    override fun initListener() {

        binding.toolbar.getBackButton().setOnClickListener {
            finish()
        }

        mAdapter.itemClickListener = {
                data, type ->
            when (type) {
                AddressAdapter.TypeClick.CLICK_ITEM -> {
                    SharePreferenceUtil.addAdress(data.id!!, applicationContext)
                    finish()
                }
                AddressAdapter.TypeClick.CLICK_FIX -> {
                    handleEditAddress(data)
                }
                AddressAdapter.TypeClick.CLICK_DELETE -> {
                    if (data.isDefault) {
                        showErrorStr("Bạn không được xóa")
                    } else {
                        viewModel.deleteAddress(data.id!!)
                    }
                }
                else -> LogUtil.log("co con caw")
            }
        }

        binding.btnAddNewProduct.setOnClickListener {
            val dialog = BottomSheetDialog(this@SelectAddressActivity, R.style.DialogCustomTheme)
            val bindingSheet: LayoutBottomAddAddressBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.layout_bottom_add_address,
                null,
                false
            )
            dialog.setContentView(bindingSheet.root)
            bindingSheet.btnAdd.setOnClickListener {
                dialog.dismiss()
                viewModel.saveAddress(
                    ShippingInformation(
                        name = bindingSheet.edtName.text.toString(),
                        phone = bindingSheet.edtPhone.text.toString(),
                        address = bindingSheet.edtAddress.text.toString(),
                        user = User(id = FirebaseUtil.getUid()),
                        isDefault = bindingSheet.swDefault.isChecked
                    )
                )
            }
            dialog.show()
        }



    }

    private fun handleEditAddress(data: ShippingInformation) {
        val dialog = BottomSheetDialog(this@SelectAddressActivity, R.style.DialogCustomTheme)
        val bindingSheet: LayoutBottomAddAddressBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_bottom_add_address,
            null,
            false
        )
        dialog.setContentView(bindingSheet.root)
        bindingSheet.edtName.setText(data.name)
        bindingSheet.edtAddress.setText(data.address)
        bindingSheet.edtPhone.setText(data.phone)
        bindingSheet.btnAdd.setOnClickListener {
            dialog.dismiss()
            viewModel.saveAddress(
                ShippingInformation(
                    name = bindingSheet.edtName.text.toString(),
                    phone = bindingSheet.edtPhone.text.toString(),
                    address = bindingSheet.edtAddress.text.toString(),
                    user = User(id = FirebaseUtil.getUid()),
                    isDefault = bindingSheet.swDefault.isChecked,
                    id = data.id!!
                )
            )
        }
        dialog.show()
    }

    override fun observerLiveData() {
        viewModel.apply {
            stateAllShip.observe(this@SelectAddressActivity) {
                mAdapter.setData(it as ArrayList<ShippingInformation>)
            }
        }
    }


}