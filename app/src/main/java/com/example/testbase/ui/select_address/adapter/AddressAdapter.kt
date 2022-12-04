package com.example.testbase.ui.select_address.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testbase.databinding.ItemAddressBinding
import com.example.testbase.model.ShippingInformation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressAdapter @Inject constructor() :
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    var itemClickListener: ((id: Int,type: TypeClick) -> Unit)? = null

    var listAddress: ArrayList<ShippingInformation> = arrayListOf()
    fun setData(listAddress: ArrayList<ShippingInformation>) {
        this.listAddress = listAddress
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun fillData(data: ShippingInformation, position: Int) {
            binding.tvName.text = data.name
            binding.tvAddress.text = data.address
            binding.tvPhone.text = data.phone
            binding.tvDefault.visibility = if(data.isDefault) View.VISIBLE else View.GONE
           binding.cb.isChecked = data.isSelected
            itemView.setOnClickListener {
                itemClickListener?.invoke(0, TypeClick.CLICK_ITEM)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val address = listAddress[position]
            fillData(address, position)
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return listAddress.size
    }

    enum class TypeClick {
        CLICK_ITEM, CLICK_FIX
    }

}