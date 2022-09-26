package com.example.testbase.ui.detail_product

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivityDetailProductBinding
import com.example.testbase.databinding.FragmentDetailProductBinding
import com.example.testbase.ui.login.LoginViewModel
import com.example.testbase.util.Const

class DetailProductActivity : BaseActivity<DetailProductViewModel, ActivityDetailProductBinding>() {

    private lateinit var mIdProduct: String;

    override fun getContentLayout(): Int {
        return R.layout.activity_detail_product
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(DetailProductViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        mIdProduct = intent.getStringExtra(Const.PRODUCT_ID).toString()

        binding.tvId.text = "$mIdProduct Kaaka"

    }

    override fun initListener() {

    }

    override fun observerLiveData() {

    }

}