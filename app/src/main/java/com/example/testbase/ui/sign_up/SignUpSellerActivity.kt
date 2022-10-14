package com.example.testbase.ui.sign_up

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivitySignUpSeller1Binding
import com.example.testbase.model.Seller
import com.example.testbase.ui.main.MainActivity
import com.example.testbase.ui.main.MainSellerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpSellerActivity : BaseActivity<SignUpViewModel, ActivitySignUpSeller1Binding>() {


    override fun getContentLayout(): Int {
        return R.layout.activity_sign_up_seller_1
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
    }

    override fun initView() {

    }

    override fun initListener() {
        binding.apply {
            btnSave.setOnClickListener {
                viewModel.signUpCustomer(
                    binding.edtEmail.text.toString(),
                    binding.edtPassword.text.toString()
                )
            }

        }

    }

    override fun observerLiveData() {
        viewModel.apply {
            firebaseResponse.observe(this@SignUpSellerActivity) {
                val sellerPush = Seller()
                sellerPush.name = binding.edtName.text.toString()
                sellerPush.address = binding.edtAddress.text.toString()
                sellerPush.email = binding.edtEmail.text.toString()
                sellerPush.phone = binding.edtPhone.text.toString()
                sellerPush.id = it
                sellerPush.shopName = binding.edtShopeName.text.toString()

                saveSellerToDatabase(sellerPush)
            }

            stateSaveToDatabase.observe(this@SignUpSellerActivity) {
                if (it) {
                    closeActivity()
                }
            }
        }
    }

    private fun closeActivity() {
        finishAffinity()
        val intent = Intent(this@SignUpSellerActivity, MainSellerActivity::class.java)
        startActivity(intent)
    }
}