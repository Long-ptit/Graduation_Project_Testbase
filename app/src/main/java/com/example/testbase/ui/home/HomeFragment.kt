package com.example.testbase.ui.home


import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testbase.R
import com.example.testbase.base.BaseFragment
import com.example.testbase.databinding.FragmentHomeBinding
import com.example.testbase.model.EStatusOrder
import com.example.testbase.model.StatusOrder
import com.example.testbase.model.Product
import com.example.testbase.ui.home.adapter.ProductUserHomeAdapter
import com.example.testbase.ui.search.SearchActivity
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.util.LogUtil
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

    override fun initListener() {
        binding.edtSearch.setOnClickListener {
            activity?.startActivity(Intent(requireContext(),SearchActivity::class.java ))
        }
    }

    override fun observerLiveData() {
        viewModel.stateListAllProduct.observe(viewLifecycleOwner) {
            mAdapter.setData(it as ArrayList<Product>)
        }
    }


}