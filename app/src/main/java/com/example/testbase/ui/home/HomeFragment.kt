package com.example.testbase.ui.home


import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testbase.R
import com.example.testbase.adapter.ProductHomeAdapter
import com.example.testbase.base.BaseFragment
import com.example.testbase.databinding.FragmentHomeBinding
import com.example.testbase.model.Product
import com.example.testbase.ui.home.adapter.ProductUserHomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    @Inject
    lateinit var mAdapter: ProductUserHomeAdapter


    override fun getContentLayout(): Int {
       return R.layout.fragment_home
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun initView() {
        val layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rcv.layoutManager = layoutManager
        binding.rcv.adapter = mAdapter
        viewModel.getAllProduct()
    }

    private fun getFakeData(): MutableList<Product> {
        val list = mutableListOf<Product>()
//        list.add(Product("One"))
//        list.add(Product("Two"))
//        list.add(Product("Three"))
//        list.add(Product("Four"))
//        list.add(Product("Five"))


        return list;
    }

    override fun initListener() {

    }

    override fun observerLiveData() {
        viewModel.stateListAllProduct.observe(viewLifecycleOwner) {
            mAdapter.setData(it as ArrayList<Product>)
        }
    }


}