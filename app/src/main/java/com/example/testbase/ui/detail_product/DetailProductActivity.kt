package com.example.testbase.ui.detail_product

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
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
import com.example.testbase.databinding.LayoutBottomAddCartBinding
import com.example.testbase.model.Product
import com.example.testbase.model.Review
import com.example.testbase.ui.cart.CartActivity
import com.example.testbase.ui.detail_review.DetailReviewActivity
import com.example.testbase.ui.rate_dialog.adapter.CommentAdapter
import com.example.testbase.ui_seller.profile_seller.ProfileSellerActivity
import com.example.testbase.util.Const
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailProductActivity : BaseActivity<DetailProductViewModel, ActivityDetailProductBinding>() {

    private var mIdProduct = 0
    private lateinit var mIdSeller: String
    @Inject
    lateinit var mAdapter: CommentAdapter

    override fun getContentLayout(): Int {
        return R.layout.activity_detail_product
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(DetailProductViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        setUpAdapter()

        mIdProduct = intent.getIntExtra(Const.PRODUCT_ID, 0)
        viewModel.getProductById(mIdProduct)
        viewModel.getRepresentReviewByProduct(mIdProduct)

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

        binding.btnSeeAllReview.setOnClickListener {
            var intent =  Intent(
                this@DetailProductActivity,
                DetailReviewActivity::class.java
            )
            intent.putExtra(Const.PRODUCT_ID, mIdProduct)
            startActivity(intent)
        }

        binding.apply {
            tvShopeName.setOnClickListener {
                handleClickShopName()
            }
        }
    }

    private fun handleClickShopName() {
        val intent = Intent(this@DetailProductActivity, ProfileSellerActivity::class.java)
        intent.putExtra(Const.SELLER_ID, mIdSeller)
        startActivity(intent)
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

        viewModel.stateCreateDeeplink.observe(this@DetailProductActivity) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, it)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Chon 1 loai"))
        }

        viewModel.stateRepresentReview.observe(this@DetailProductActivity) {
            mAdapter.setData(it.data as ArrayList<Review>)
        }
    }


    @SuppressLint("CheckResult")
    private fun showData(it: Product) {
        mIdSeller = it.seller.id
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