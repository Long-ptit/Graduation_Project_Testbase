package com.example.testbase.ui_seller.edit_product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivityEditProductBinding
import com.example.testbase.model.Product
import com.example.testbase.ui_seller.home.HomeSellerViewModel
import com.example.testbase.util.Const
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProductActivity : BaseActivity<EditProductViewModel, ActivityEditProductBinding>() {
    override fun getContentLayout(): Int {
       return R.layout.activity_edit_product
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(EditProductViewModel::class.java)
    }

    override fun initView() {
        val id = intent.getIntExtra(Const.PRODUCT_ID, 0)
        viewModel.getInforProduct(id)
    }

    override fun initListener() {
        binding.toolbar.getBackButton().setOnClickListener {
            finish()
        }
    }

    override fun observerLiveData() {
        viewModel.stateProduct.observe(this@EditProductActivity) {
//            showErrorStr(it.name)
            showData(it)
        }
    }

    private fun showData(it: Product) {
        binding.edtName.setText(it.name)
        binding.edtDescription.setText(it.description)
        binding.edtQuantity.setText(it.quantity.toString())
        binding.edtPrice.setText(it.price.toString())
    }

}