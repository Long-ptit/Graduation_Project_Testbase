package com.example.testbase.ui_seller.home


import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testbase.R
import com.example.testbase.adapter.ProductHomeAdapter
import com.example.testbase.base.BaseFragment
import com.example.testbase.databinding.FragmentHomeBinding
import com.example.testbase.model.Product
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeSellerFragment : BaseFragment<HomeSellerViewModel, FragmentHomeBinding>() {

    @Inject
    lateinit var mAdapter: ProductHomeAdapter


    override fun getContentLayout(): Int {
       return R.layout.fragment_home
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
        mAdapter.setData(getFakeData())
    }

    private fun getFakeData(): MutableList<Product> {
        val list = mutableListOf<Product>()
        list.add(Product("One"))
        list.add(Product("Two"))
        list.add(Product("Three"))
        list.add(Product("Four"))
        list.add(Product("Five"))


        return list;
    }

    override fun initListener() {

    }

    override fun observerLiveData() {

    }


}