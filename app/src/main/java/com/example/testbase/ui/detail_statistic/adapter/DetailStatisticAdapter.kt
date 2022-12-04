package com.restaurant.exam.ui.detail_statistic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testbase.databinding.ItemStatisticBillBinding
import com.example.testbase.model.Order


class DetailStatisticAdapter() : RecyclerView.Adapter<DetailStatisticAdapter.ViewHolder>() {
    var listOrder: ArrayList<Order> = arrayListOf()

    fun setData(listOrder: ArrayList<Order>) {
        this.listOrder = listOrder
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemStatisticBillBinding) : RecyclerView.ViewHolder(binding.root) {
        fun fillData(data: Order, position: Int){

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStatisticBillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
           val table = listOrder[position]
            fillData(table, position)
        }
    }

    override fun getItemCount(): Int {
        return listOrder!!.size
    }

}