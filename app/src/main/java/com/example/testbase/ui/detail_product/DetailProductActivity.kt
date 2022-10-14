package com.example.testbase.ui.detail_product

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivityDetailProductBinding
import com.example.testbase.databinding.LayoutBottomAddCartBinding
import com.example.testbase.model.Product
import com.example.testbase.ui.cart.CartActivity
import com.example.testbase.util.Const
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProductActivity : BaseActivity<DetailProductViewModel, ActivityDetailProductBinding>() {

    private var mIdProduct = 0

    override fun getContentLayout(): Int {
        return R.layout.activity_detail_product
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(DetailProductViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        mIdProduct = intent.getIntExtra(Const.PRODUCT_ID, 0)
        viewModel.getProductById(mIdProduct)

    }

    override fun initListener() {
        binding.toolbar.getBackButton().setOnClickListener {
            finish()
        }

        binding.btnAddToCart.setOnClickListener {
            val dialog = BottomSheetDialog(this@DetailProductActivity, R.style.DialogCustomTheme)
            val bindingSheet: LayoutBottomAddCartBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_bottom_add_cart, null, false)
            dialog.setContentView(bindingSheet.root)
            bindingSheet.btnAdd.setOnClickListener {
                dialog.dismiss()
                viewModel.addItemToCart(
                    productId = mIdProduct,
                    userId = Firebase.auth.uid.toString(),
                    quantity = bindingSheet.edtName.text.toString().toInt()
                )
            }
            dialog.show()
        }

        binding.tvShare.setOnClickListener {
            viewModel.createDeeplink(mIdProduct)
        }
    }

    override fun observerLiveData() {
        viewModel.stateProductById.observe(this) {
            showData(it)
        }

        viewModel.stateSaveCartItem.observe(this@DetailProductActivity) {
            startActivity(
                Intent(this@DetailProductActivity, CartActivity::class.java)
            )
        }
    }


    @SuppressLint("CheckResult")
    private fun showData(it: Product) {
        binding.tvNameProduct.text = it.name
        binding.tvPriceProduct.text = it.price.toString()
        binding.tvShopeName.text = it.seller.shopName
        binding.tvDescription.text = it.description
        binding.tvCategory.text = it.productCategory.name
        Glide
            .with(this)
            .load(Const.BASE_URL + Const.PATH_IMAGE + mIdProduct + ".jpg")
            .into(binding.imgProduct)
    }

}