package com.example.testbase.ui.confirm_order.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testbase.databinding.LayoutItemConfirmOrderBinding
import com.example.testbase.databinding.LayoutItemProductCartBinding
import com.example.testbase.model.CartItem
import com.example.testbase.model.Product
import com.example.testbase.util.Const
import com.example.testbase.util.Util
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderProductAdapter @Inject constructor() :
    RecyclerView.Adapter<OrderProductAdapter.ViewHolder>() {
    var itemClickListener: ((id: Int, type: TypeClick) -> Unit)? = null

    var listProduct: ArrayList<CartItem> = arrayListOf()
    fun setData(listProduct: ArrayList<CartItem>) {
        this.listProduct = listProduct
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: LayoutItemConfirmOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun fillData(data: CartItem, position: Int) {

            binding.tvNameProduct.text = data.product.name
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutItemConfirmOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val table = listProduct[position]
            fillData(table, position)
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return listProduct.size
    }

    enum class TypeClick {
        INC, DEC, DELETE
    }

}