package com.example.testbase.ui_seller.add_product

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.testbase.R
import com.example.testbase.databinding.ItemSpSelectedBinding
import com.example.testbase.databinding.ItemSpSelectedMainBinding
import com.example.testbase.model.Category
import com.example.testbase.model.Manufacturer

class ManufacturerAdapter(context: Context, list: List<Manufacturer>,
                          resource: Int,
) : ArrayAdapter<Manufacturer>(context, resource, list) {


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpSelectedMainBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.tvSelected.text = this.getItem(position)?.name

        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemSpSelectedBinding = ItemSpSelectedBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.tvCategoryName.text = this.getItem(position)?.name

        return binding.root
    }

}