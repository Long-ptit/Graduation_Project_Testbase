package com.example.testbase.ui.detail_order.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testbase.databinding.LayoutItemConfirmOrderBinding
import com.example.testbase.databinding.LayoutItemProductCartBinding
import com.example.testbase.model.CartItem
import com.example.testbase.model.OrderItem
import com.example.testbase.model.Product
import com.example.testbase.util.Const
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.util.Util
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderDetailAdapter @Inject constructor() :
    RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {
    var itemClickListener: ((id: Int) -> Unit)? = null
    var statusOrder = 1

    var listOrderItem: ArrayList<OrderItem> = arrayListOf()
    fun setData(listOrderItem: ArrayList<OrderItem>) {
        this.listOrderItem = listOrderItem
        notifyDataSetChanged()
    }

    fun setStateReview(status: Int) {
        statusOrder = status
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: LayoutItemConfirmOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun fillData(data: OrderItem, position: Int) {
            Glide
                .with(binding.root.context)
                .load(Const.BASE_URL + Const.PATH_IMAGE + data.idProduct + ".jpg")
                .into(binding.imgProduct)
            binding.tvNameProduct.text = data.name
            binding.tvPrice.text = data.price.toString() + "vnd"
            binding.tvQuantity.text = "x" + data.quantity
            binding.btnReview.visibility = if (statusOrder == 3) View.VISIBLE else View.GONE
            binding.btnReview.setOnClickListener {
                itemClickListener?.invoke(data.idProduct)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutItemConfirmOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val orderItem = listOrderItem[position]
            fillData(orderItem, position)
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return listOrderItem.size
    }

    enum class TypeClick {
        INC, DEC, DELETE
    }

}