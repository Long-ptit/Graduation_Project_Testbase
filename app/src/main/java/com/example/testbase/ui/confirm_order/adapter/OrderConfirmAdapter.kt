package com.example.testbase.ui.confirm_order.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testbase.databinding.ItemOrderConfirmBinding
import com.example.testbase.databinding.LayoutItemConfirmOrderBinding
import com.example.testbase.model.CartItem
import com.example.testbase.model.CartItemConfirm
import com.example.testbase.model.Order
import com.example.testbase.model.Seller
import com.example.testbase.util.Const
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderConfirmAdapter @Inject constructor() :
    RecyclerView.Adapter<OrderConfirmAdapter.ViewHolder>() {
    var itemClickListener: ((id: Int) -> Unit)? = null
    lateinit var mAdapter: OrderProductAdapter

    var listOrder: ArrayList<CartItemConfirm> = arrayListOf()
    fun setData(listOrder: ArrayList<CartItemConfirm>) {
        this.listOrder = listOrder
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: ItemOrderConfirmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun fillData(data: CartItemConfirm, position: Int) {
            binding.tvNameShop.text = data.seller.shopName
            mAdapter = OrderProductAdapter()
            Glide
                .with(binding.root.context)
                .load(Const.BASE_URL + Const.PATH_IMAGE + data.seller.id + ".jpg")
                .into(binding.imgLogoShop)
            binding.rcv.layoutManager = LinearLayoutManager(
                binding.root.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            binding.rcv.adapter = mAdapter
            mAdapter.setData(data.listCartItem)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOrderConfirmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val table = listOrder[position]
            fillData(table, position)
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return listOrder.size
    }

}