package com.example.testbase.ui_seller.order

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testbase.R
import com.example.testbase.base.BaseFragment
import com.example.testbase.databinding.FragmentOrderBinding
import com.example.testbase.model.Order
import com.example.testbase.ui.detail_order.DetailOrderActivity
import com.example.testbase.ui.order.OrderViewModel
import com.example.testbase.ui.order.adapter.OrderAdapter
import com.example.testbase.ui_seller.detail_order_seller.DetailOrderSellerActivity
import com.example.testbase.util.Const
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderSellerFragment : BaseFragment<OrderSellerViewModel, FragmentOrderBinding>() {

    @Inject
    lateinit var mAdapter: OrderAdapter

    override fun getContentLayout(): Int {
        return R.layout.fragment_order
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(OrderSellerViewModel::class.java)
    }

    override fun initView() {
        val layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rcv.layoutManager = layoutManager
        binding.rcv.adapter = mAdapter
        viewModel.getOrderByIdSeller()
    }

    override fun initListener() {
        mAdapter.itemClickListener = { id ->
            val intent = Intent(activity, DetailOrderSellerActivity::class.java)
            intent.putExtra(Const.ORDER_ID, id)
            activity?.startActivity(intent)
        }
    }

    override fun observerLiveData() {
        viewModel.stateListOrder.observe(viewLifecycleOwner) {
            mAdapter.setData(it.data as ArrayList<Order>)
        }
    }

}

