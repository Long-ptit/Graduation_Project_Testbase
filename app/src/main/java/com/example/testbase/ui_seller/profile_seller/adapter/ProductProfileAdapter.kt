package com.example.testbase.ui_seller.profile_seller.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testbase.databinding.LayoutItemProductBinding
import com.example.testbase.databinding.LayoutItemProductProfileBinding
import com.example.testbase.model.Product
import com.example.testbase.ui.detail_product.DetailProductActivity
import com.example.testbase.ui_seller.edit_product.EditProductActivity
import com.example.testbase.util.Const
import com.example.testbase.util.Util
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class ProductProfileAdapter @Inject constructor() : RecyclerView.Adapter<ProductProfileAdapter.ViewHolder>() {
    var listProduct: ArrayList<Product> = arrayListOf()
    fun setData(listTable: ArrayList<Product>) {
        this.listProduct = listTable
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: LayoutItemProductProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun fillData(data: Product, position: Int){

            binding.tvNameProduct.text = data.name
            Glide
                .with(binding.root.context)
                .load(Const.BASE_URL + Const.PATH_IMAGE + data.id + ".jpg")
                .into(binding.imgProduct)
            itemView.setOnClickListener {
                var intent =  Intent(
                    binding.root.context,
                    DetailProductActivity::class.java
                )
                Log.d("ptit", "id product: " + data.id)
                intent.putExtra(Const.PRODUCT_ID, data.id)
                binding.root.context.startActivity(intent)
            }
            binding.tvPrice.text = Util.converCurrency(data.getPriceAfterDiscount().toDouble())
            binding.tvSale.text = "Sale Off " + data.discountPoint.toString() + "%"
            binding.tvPriceOrgin.text =  Util.converCurrency(data.price.toDouble())
            binding.tvPriceOrgin.visibility = if (data.discount) View.VISIBLE else View.GONE
            binding.tvSale.visibility = if(data.discount) View.VISIBLE else View.GONE
            binding.tvSoldNumber.text = "Đã bán " + data.soldNumber.toString()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemProductProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
           val table = listProduct[position]
            fillData(table, position)
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return listProduct.size
    }

}