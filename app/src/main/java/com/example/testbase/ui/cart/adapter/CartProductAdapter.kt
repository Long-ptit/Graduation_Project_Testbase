package com.example.testbase.ui.cart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testbase.databinding.LayoutItemProductCartBinding
import com.example.testbase.model.CartItem
import com.example.testbase.model.Product
import com.example.testbase.util.Const
import com.example.testbase.util.Util
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartProductAdapter @Inject constructor() :
    RecyclerView.Adapter<CartProductAdapter.ViewHolder>() {
    var itemClickListener: ((id: Int, type: TypeClick) -> Unit)? = null

    var listProduct: ArrayList<CartItem> = arrayListOf()
    fun setData(listProduct: ArrayList<CartItem>) {
        this.listProduct = listProduct
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: LayoutItemProductCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun fillData(data: CartItem, position: Int) {

            binding.tvNameProduct.text = data.product.name
            binding.tvPriceProduct.text = Util.converCurrency(data.product.price.toDouble()) + "VND"
            Glide
                .with(binding.root.context)
                .load(Const.BASE_URL + Const.PATH_IMAGE + data.product.id + ".jpg")
                .into(binding.imgProduct)
            binding.tvQuantity.text = data.quantity.toString()

            binding.tvDec.setOnClickListener {
                itemClickListener?.invoke(data.id, TypeClick.DEC)
            }
            binding.tvInc.setOnClickListener {
                itemClickListener?.invoke(data.id, TypeClick.INC)
            }
            binding.imgDelete.setOnClickListener {
                itemClickListener?.invoke(data.id, TypeClick.DELETE)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutItemProductCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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