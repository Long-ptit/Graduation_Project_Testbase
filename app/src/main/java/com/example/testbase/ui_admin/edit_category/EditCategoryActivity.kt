package com.example.testbase.ui_admin.edit_category

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.testbase.R
import com.example.testbase.adapter.CategoryAdapter
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivityAddCategoryBinding
import com.example.testbase.databinding.ActivityAddProductBinding
import com.example.testbase.databinding.ActivityEditCategoryBinding
import com.example.testbase.model.Category
import com.example.testbase.model.Product
import com.example.testbase.model.Seller
import com.example.testbase.ui_admin.add_category.AddCategoryViewModel
import com.example.testbase.util.Const
import com.example.testbase.util.FirebaseUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCategoryActivity : BaseActivity<AddCategoryViewModel, ActivityEditCategoryBinding>() {

    private val pickImage = 100
    private var imageUri: Uri? = null
    private var mIdCategory = 0

    override fun getContentLayout(): Int {
        return R.layout.activity_edit_category
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AddCategoryViewModel::class.java)
    }

    override fun initView() {
        setUpSp()
    }

    private fun setUpSp() {
        mIdCategory = intent.getIntExtra(Const.PRODUCT_ID, 0)
        viewModel.getInforCategory(mIdCategory)
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
            val category = Category(name = binding.edtName.text.toString(), description = binding.edtDescription.text.toString(), id = mIdCategory)
            viewModel.editCategory(category, uri = imageUri!!)
        }
    }


    override fun observerLiveData() {
        viewModel.apply {
            stateEditCategory.observe(this@EditCategoryActivity) {
                finish()
            }

            statusCategory.observe(this@EditCategoryActivity) {
                showData(it)
            }
        }
    }

    private fun showData(it: Category) {
        binding.edtName.setText(it.name)
        binding.edtDescription.setText(it.description)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            Glide.with(this).load(imageUri).into(binding.img)
        }
    }


}