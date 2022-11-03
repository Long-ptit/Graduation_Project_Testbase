package com.example.testbase.ui.sign_up

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivitySignUpCustomerBinding
import com.example.testbase.model.User
import com.example.testbase.ui.login.LoginActivity
import com.example.testbase.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpCustomerActivity : BaseActivity<SignUpViewModel, ActivitySignUpCustomerBinding>() {


    lateinit var mUri: Uri
    private val pickImage = 100

    override fun getContentLayout(): Int {
        return R.layout.activity_sign_up_customer
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
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

            tvSignUpSeller.setOnClickListener {
                startActivity(Intent(this@SignUpCustomerActivity, SignUpSellerActivity::class.java))
            }


        }

        binding.img.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }


    }

    override fun observerLiveData() {
        viewModel.apply {
            firebaseResponse.observe(this@SignUpCustomerActivity) {
                Log.d("ptit", "Customẻ: ")
                val user = User(
                    name = binding.edtName.text.toString(),
                    address = binding.edtAddress.text.toString(),
                    email = binding.edtEmail.text.toString(),
                    phone = binding.edtPhone.text.toString(),
                    id = it
                )
                saveUserToDatabase(user, mUri)
            }

            stateSaveToDatabase.observe(this@SignUpCustomerActivity)  {
               if (it) {
                   finishAffinity()
                   val intent = Intent(this@SignUpCustomerActivity, LoginActivity::class.java)
                   startActivity(intent)
               }
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            mUri = data?.data!!
            Glide.with(this).load(mUri).into(binding.img)
        }
    }
}