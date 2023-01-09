//package com.example.testbase.ui_seller.edit_seller
//
//import android.content.Intent
//import android.net.Uri
//import android.provider.MediaStore
//import androidx.lifecycle.ViewModelProvider
//import com.bumptech.glide.Glide
//import com.example.testbase.R
//import com.example.testbase.base.BaseActivity
//import com.example.testbase.databinding.ActivityEditSellerBinding
//import com.example.testbase.databinding.ActivitySignUpSeller1Binding
//import com.example.testbase.model.Seller
//import com.example.testbase.ui.login.LoginActivity
//import com.example.testbase.ui.main.MainActivity
//import com.example.testbase.ui.main.MainSellerActivity
//import com.example.testbase.ui.sign_up.SignUpViewModel
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class EditSellerActivity : BaseActivity<SignUpViewModel, ActivityEditSellerBinding>() {
//
//    lateinit var mUri: Uri
//    private val pickImage = 100
//
//    override fun getContentLayout(): Int {
//        return R.layout.activity_edit_seller
//    }
//
//    override fun initViewModel() {
//        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
//    }
//
//    override fun initView() {
//
//    }
//
//    override fun initListener() {
//        binding.apply {
//            btnSave.setOnClickListener {
//                viewModel.signUpCustomer(
//                    binding.edtEmail.text.toString(),
//                    binding.edtPassword.text.toString()
//                )
//            }
//        }
//
//        binding.img.setOnClickListener {
//            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//            startActivityForResult(gallery, pickImage)
//        }
//
//    }
//
//    override fun observerLiveData() {
//        viewModel.apply {
//            firebaseResponse.observe(this@EditSellerActivity) {
//                val sellerPush = Seller()
//                sellerPush.name = binding.edtName.text.toString()
//                sellerPush.address = binding.edtAddress.text.toString()
//                sellerPush.phone = binding.edtPhone.text.toString()
//                sellerPush.id = it
//                sellerPush.shopName = binding.edtShopeName.text.toString()
//                saveSellerToDatabase(sellerPush, mUri)
//            }
//
//            stateSaveToDatabase.observe(this@EditSellerActivity) {
//                if (it) {
//                    closeActivity()
//                }
//            }
//        }
//    }
//
//    private fun closeActivity() {
//        finishAffinity()
//        val intent = Intent(this@EditSellerActivity, LoginActivity::class.java)
//        startActivity(intent)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK && requestCode == pickImage) {
//            mUri = data?.data!!
//            Glide.with(this).load(mUri).into(binding.img)
//        }
//    }
//}