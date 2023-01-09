package com.example.testbase.ui.search

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivitySearchBinding
import com.example.testbase.model.Product
import com.example.testbase.ui.rate_dialog.adapter.CommentAdapter
import com.example.testbase.ui_seller.profile_seller.adapter.ProductProfileAdapter
import com.example.testbase.util.LogUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : BaseActivity<SearchViewModel, ActivitySearchBinding>() {


    @Inject
    lateinit var mAdapter: ProductProfileAdapter

    override fun getContentLayout(): Int {
        return R.layout.activity_search
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        setUpAdapter()

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
        binding.imgBack.setOnClickListener {
            finish()
        }
        setClickEdt()

    }

    private fun setClickEdt() {
        binding.inputsearchClinic.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                LogUtil.log("OnTextchanged" + s.toString())
                viewModel.searchProduct(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    override fun observerLiveData() {
        obserSearchData()
    }

    private fun obserSearchData() {
        viewModel.dataListProductSearch.observe(this@SearchActivity) {
            mAdapter.setData(it as ArrayList<Product>)
        }
    }

}