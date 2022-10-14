package com.example.testbase.ui_seller.home


import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testbase.R
import com.example.testbase.adapter.ProductHomeAdapter
import com.example.testbase.base.BaseFragment
import com.example.testbase.databinding.FragmentHomeBinding
import com.example.testbase.databinding.FragmentHomeSellerBinding
import com.example.testbase.model.Product
import com.example.testbase.ui_seller.add_product.AddProductActivity
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.system.measureTimeMillis


@AndroidEntryPoint
class HomeSellerFragment : BaseFragment<HomeSellerViewModel, FragmentHomeSellerBinding>() {

    private lateinit var database: DatabaseReference

    @Inject
    lateinit var mAdapter: ProductHomeAdapter


    override fun getContentLayout(): Int {
       return R.layout.fragment_home_seller
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(HomeSellerViewModel::class.java)
    }

    override fun initView() {
        val layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rcv.layoutManager = layoutManager
        binding.rcv.adapter = mAdapter

        viewModel.getListProductBySeller()
    }


    override fun initListener() {
        binding.btnAddProduct.setOnClickListener {
            startActivity(Intent(activity, AddProductActivity::class.java))
        }
    }

    override fun observerLiveData() {
        viewModel.stateListProduct.observe(this@HomeSellerFragment) {
            mAdapter.setData(it as ArrayList<Product>)
        }
    }


}