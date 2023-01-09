package com.example.testbase.ui_seller.edit_product

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.testbase.R
import com.example.testbase.adapter.CategoryAdapter
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivityEditProductBinding
import com.example.testbase.model.Category
import com.example.testbase.model.Product
import com.example.testbase.model.Seller
import com.example.testbase.ui_seller.add_product.AddProductViewModel
import com.example.testbase.ui_seller.home.HomeSellerViewModel
import com.example.testbase.util.Const
import com.example.testbase.util.FirebaseUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProductActivity : BaseActivity<EditProductViewModel, ActivityEditProductBinding>() {
    private val pickImage = 100
    private var imageUri: Uri? = null
    private var mIdProduct = 0
    private lateinit var mCategoryAdapter: CategoryAdapter
    override fun getContentLayout(): Int {
       return R.layout.activity_edit_product
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(EditProductViewModel::class.java)
    }

    override fun initView() {
        mIdProduct = intent.getIntExtra(Const.PRODUCT_ID, 0)
        viewModel.getInforProduct(mIdProduct)
        viewModel.getAllCategory()

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
            val seller = Seller()
            seller.id = FirebaseUtil.getUid()
            val data = Product(
                name = binding.edtName.text.toString(),
                description = binding.edtDescription.text.toString(),
                quantity = binding.edtQuantity.text.toString().toInt(),
                price = binding.edtPrice.text.toString().toInt(),
                seller = seller,
                productCategory = Category(id = mCategoryAdapter.getItem(binding.sp.selectedItemPosition)!!.id),
                discountPoint = binding.edtDiscountPoint.text.toString().toInt(),
                discount = binding.swDiscount.isChecked,
                msgDiscount = binding.edtMsgDiscount.text.toString(),
                id = mIdProduct
            )
            viewModel.saveProductWithoutImage(data, uri = imageUri!!)
        }
        handlleBtnSwitch()
    }

    private fun handlleBtnSwitch() {
        binding.edtDiscountPoint.setText("0")
        binding.edtMsgDiscount.setText("")
        binding.swDiscount.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.edtDiscountPoint.visibility = View.VISIBLE
                binding.edtMsgDiscount.visibility = View.VISIBLE
            } else {
                binding.edtDiscountPoint.visibility = View.GONE
                binding.edtMsgDiscount.visibility = View.GONE
            }
        }
    }

    override fun observerLiveData() {
        viewModel.apply {
            statusAddProduct.observe(this@EditProductActivity) {
                finish()
                showErrorStr("Add product success with id ${it.id}")
            }

            statusCategory.observe(this@EditProductActivity) {
                mCategoryAdapter = CategoryAdapter(this@EditProductActivity, it, R.layout.item_sp_selected_main)
                binding.sp.adapter = mCategoryAdapter
            }

            stateProduct.observe(this@EditProductActivity) {
                showData(it)
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

    private fun showData(it: Product) {
        binding.edtName.setText(it.name)
        binding.edtDescription.setText(it.description)
        binding.edtQuantity.setText(it.quantity.toString())
        binding.edtPrice.setText(it.price.toString())
    }

}