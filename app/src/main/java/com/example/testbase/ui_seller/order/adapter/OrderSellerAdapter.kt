package com.example.testbase.ui_seller.order.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testbase.databinding.LayoutItemOrderBinding
import com.example.testbase.model.CartItem
import com.example.testbase.model.Order
import com.example.testbase.ui.cart.adapter.CartProductAdapter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderSellerAdapter @Inject constructor() :
    RecyclerView.Adapter<OrderSellerAdapter.ViewHolder>() {
    var itemClickListener: ((id: Int,) -> Unit)? = null

    var listOrder: ArrayList<Order> = arrayListOf()
    fun setData(listOrder: ArrayList<Order>) {
        this.listOrder = listOrder
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: LayoutItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun fillData(data: Order, position: Int) {
            binding.tvName.text = data.cart.user.name
            binding.tvQuantity.text = data.totalQuantity.toString()
            binding.tvPrice.text = data.totalPrice.toString() + "vnd"
            binding.tvStatus.text = data.status

            itemView.setOnClickListener {
                itemClickListener?.invoke(data.id!!)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val order = listOrder[position]
            fillData(order, position)
        }
    }

    override fun getItemCount(): Int {
        return listOrder.size
    }

    enum class TypeClick {
        INC, DEC, DELETE
    }

}