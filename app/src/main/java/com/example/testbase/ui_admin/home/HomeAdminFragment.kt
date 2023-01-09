package com.example.testbase.ui_admin.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.acom.service.user.payment.IPaymentService
import com.acom.service.user.payment.PaymentNetwork
import com.example.testbase.R
import com.example.testbase.adapter.ProductHomeAdapter
import com.example.testbase.base.BaseFragment
import com.example.testbase.databinding.FragmentAccountSellerBinding
import com.example.testbase.databinding.FragmentHomeAdminBinding
import com.example.testbase.model.Category
import com.example.testbase.model.Seller
import com.example.testbase.ui.login.LoginActivity
import com.example.testbase.ui.login.LoginViewModel
import com.example.testbase.ui_admin.account.AdminViewModel
import com.example.testbase.ui_admin.add_category.AddCategoryActivity
import com.example.testbase.ui_admin.home.adapter.CategoryHomeAdapter
import com.example.testbase.ui_seller.statistic.StatisticSellerActivity
import com.example.testbase.util.Const
import com.example.testbase.util.FirebaseUtil
import com.google.gson.JsonObject
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class HomeAdminFragment : BaseFragment<AdminViewModel, FragmentHomeAdminBinding>() {

    @Inject
    lateinit var mAdapter: CategoryHomeAdapter

    override fun getContentLayout(): Int {
        return R.layout.fragment_home_admin
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
    }


    override fun initView() {
        val layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rcv.layoutManager = layoutManager
        binding.rcv.adapter = mAdapter
        viewModel.getAllCategory()
    }


    override fun initListener() {
        binding.btnAddCategory.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    requireContext(),
                    AddCategoryActivity::class.java
                )
            )
        }
        mAdapter.itemClickListener = {
            viewModel.deleteCategory(it)
        }

    }

    override fun observerLiveData() {
        viewModel.stateListCategory.observe(viewLifecycleOwner) {
            mAdapter.setData(it as ArrayList<Category>)
        }
    }

}