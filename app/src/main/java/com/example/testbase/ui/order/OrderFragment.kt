package com.example.testbase.ui.order

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testbase.R
import com.example.testbase.base.BaseFragment
import com.example.testbase.databinding.FragmentOrderBinding
import com.example.testbase.model.Order
import com.example.testbase.ui.detail_order.DetailOrderActivity
import com.example.testbase.ui.order.adapter.OrderAdapter
import com.example.testbase.util.Const
import com.example.testbase.util.LogUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderFragment : BaseFragment<OrderViewModel, FragmentOrderBinding>() {

    @Inject
    lateinit var mAdapter: OrderAdapter

    override fun getContentLayout(): Int {
        return R.layout.fragment_order
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
    }

    override fun initView() {
        val layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rcv.layoutManager = layoutManager
        binding.rcv.adapter = mAdapter
        viewModel.getOrderByIdUser()
    }

    override fun initListener() {
        mAdapter.itemClickListener = { id ->
            val intent = Intent(activity, DetailOrderActivity::class.java)
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