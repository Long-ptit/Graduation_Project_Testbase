package com.example.testbase.ui_seller.add_product

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.testbase.R
import com.example.testbase.adapter.CategoryAdapter
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivityAddProductBinding
import com.example.testbase.model.Category
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductActivity : BaseActivity<AddProductViewModel, ActivityAddProductBinding>() {

    private val pickImage = 100
    private var imageUri: Uri? = null
    private lateinit var mCategoryAdapter: CategoryAdapter

    override fun getContentLayout(): Int {
        return R.layout.activity_add_product
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AddProductViewModel::class.java)
    }

    override fun initView() {
        setUpSp()
        viewModel.getAllCategory()
    }

    private fun setUpSp() {

    }


    override fun initListener() {
        binding.img.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        binding.toolbar.getBackButton().setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            viewModel.sendProduct(
                name = binding.edtName.text.toString(),
                description = binding.edtDescription.text.toString(),
                quantity = binding.edtQuantity.text.toString().toInt(),
                price = binding.edtPrice.text.toString().toInt(),
                uri = imageUri!!,
                idCategory = mCategoryAdapter.getItem(binding.sp.selectedItemPosition)!!.id
            )
        }
    }

    override fun observerLiveData() {
        viewModel.apply {
            statusAddProduct.observe(this@AddProductActivity) {
                finish()
                showErrorStr("Add product success with id ${it.id}")
            }

            statusCategory.observe(this@AddProductActivity) {
                mCategoryAdapter = CategoryAdapter(this@AddProductActivity, it, R.layout.item_sp_selected_main)
                binding.sp.adapter = mCategoryAdapter
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            Glide.with(this).load(imageUri).into(binding.img)
        }
    }


}