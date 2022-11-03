package com.example.testbase.ui.detail_review

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
import com.example.testbase.databinding.LayoutBottomAddCartBinding
import com.example.testbase.model.Product
import com.example.testbase.model.Review
import com.example.testbase.model_response.StatisReviewResponse
import com.example.testbase.ui.cart.CartActivity
import com.example.testbase.ui.rate_dialog.adapter.CommentAdapter
import com.example.testbase.util.Const
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailReviewActivity : BaseActivity<DetailReviewViewModel, ActivityDetailReviewBinding>() {

    private var mIdProduct = 0

    @Inject
    lateinit var mAdapter: CommentAdapter

    override fun getContentLayout(): Int {
        return R.layout.activity_detail_review
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(DetailReviewViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        setUpAdapter()

        mIdProduct = intent.getIntExtra(Const.PRODUCT_ID, 0)
        viewModel.getRepresentReviewByProduct(mIdProduct)
        viewModel.getAllReviewById(mIdProduct)

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


    }

    override fun observerLiveData() {
        viewModel.stateStatisReview.observe(this@DetailReviewActivity) {
            showDataStatistic(it)
        }

        viewModel.stateListReviewResponse.observe(this@DetailReviewActivity) {
            mAdapter.setData(it.data as ArrayList<Review>)
        }
    }

    private fun showDataStatistic(it: StatisReviewResponse) {
        binding.tvTotalReview.text = it.data.totalReview.toString() + "Đánh giá"
        val listData = it.data.listStaReview

        binding.tvQuantityReview1.text = listData[0].quantity.toString()
        binding.tvQuantityReview2.text = listData[1].quantity.toString()
        binding.tvQuantityReview3.text = listData[2].quantity.toString()
        binding.tvQuantityReview4.text = listData[3].quantity.toString()
        binding.tvQuantityReview5.text = listData[4].quantity.toString()

    }


    @SuppressLint("CheckResult")
    private fun showData(it: Product) {
//        Glide
//            .with(this)
//            .load(Const.BASE_URL + Const.PATH_IMAGE + mIdProduct + ".jpg")
//            .into(binding.imgProduct)
    }

}