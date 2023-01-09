package com.example.testbase.ui_admin.home.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testbase.databinding.ActivityEditCategoryBinding
import com.example.testbase.databinding.LayoutItemCategoryHomeBinding
import com.example.testbase.databinding.LayoutItemProductBinding
import com.example.testbase.model.Category
import com.example.testbase.model.Product
import com.example.testbase.ui.detail_product.DetailProductActivity
import com.example.testbase.ui_admin.edit_category.EditCategoryActivity
import com.example.testbase.ui_seller.edit_product.EditProductActivity
import com.example.testbase.util.Const
import javax.inject.Inject

class CategoryHomeAdapter @Inject constructor() : RecyclerView.Adapter<CategoryHomeAdapter.ViewHolder>() {
    var itemClickListener: ((id: Int) -> Unit)? = null
    var listCategory: ArrayList<Category> = arrayListOf()
    fun setData(listTable: ArrayList<Category>) {
        this.listCategory = listTable
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: LayoutItemCategoryHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun fillData(data: Category, position: Int){

            Log.d("ptit", "fillData: " + data.id)
            binding.tvNameCategory.text = data.name
            binding.tvDescription.text = data.description
            Glide
                .with(binding.root.context)
                .load(Const.BASE_URL + Const.PATH_IMAGE + data.id + ".jpg")
                .into(binding.imgProduct)

            itemView.setOnClickListener {
                var intent =  Intent(
                    binding.root.context,
                    EditCategoryActivity::class.java
                )
                intent.putExtra(Const.PRODUCT_ID, data.id)
                binding.root.context.startActivity(intent)
            }

            binding.imgDelete.setOnClickListener {
                itemClickListener?.invoke(data.id)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemCategoryHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
           val table = listCategory[position]
            fillData(table, position)
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return listCategory.size
    }

}