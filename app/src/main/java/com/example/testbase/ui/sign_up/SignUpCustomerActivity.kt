package com.example.testbase.ui.sign_up

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivitySignUpCustomerBinding
import com.example.testbase.model.User
import com.example.testbase.network.Api
import com.example.testbase.network.ApiClient
import com.example.testbase.ui.main.MainActivity
import com.example.testbase.ui.main.MainViewModel
import com.example.testbase.view_model.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpCustomerActivity : BaseActivity<SignUpViewModel, ActivitySignUpCustomerBinding>() {


    override fun getContentLayout(): Int {
        return R.layout.activity_sign_up_customer
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
    }

    override fun initView() {

    }

    override fun initListener() {

        binding.btnSave.setOnClickListener {
            viewModel.signUpCustomer(
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString()
            )


        }


    }

    override fun observerLiveData() {
        viewModel.apply {
            firebaseResponse.observe(this@SignUpCustomerActivity) {
                val user = User(
                    name = binding.edtName.text.toString(),
                    address = binding.edtAddress.text.toString(),
                    email = binding.edtEmail.text.toString(),
                    phone = binding.edtPhone.text.toString(),
                    uid = it,
                    id = it
                )
                saveUserToDatabase(user)
            }

            stateSaveUser.observe(this@SignUpCustomerActivity)  {
                finishAffinity()
                val intent = Intent(this@SignUpCustomerActivity, MainActivity::class.java)
                startActivity(intent)
            }


        }
    }
}