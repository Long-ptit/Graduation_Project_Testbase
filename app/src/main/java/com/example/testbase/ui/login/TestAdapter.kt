package com.example.testbase.ui.login

import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.testbase.R
import com.example.testbase.base.BaseRecycleAdapter
import com.example.testbase.databinding.LayoutErrorBinding
import javax.inject.Inject

class TestAdapter constructor(data: MutableList<String>)
    : BaseRecycleAdapter<String, TestAdapter.ViewHolder>(data) {

    inner class ViewHolder(val binding: LayoutErrorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun fillData(data: String, position: Int) {
            Log.d("ptit", "fillData: " + data)
            binding.tvError.text = data
        }
    }

    override fun getLayoutResourceItem(): Int {
        return R.layout.layout_error
    }

    override fun onCreateBasicViewHolder(binding: ViewDataBinding?): ViewHolder {
        return ViewHolder(binding as LayoutErrorBinding)



    }

    override fun onBindBasicItemView(holder: ViewHolder, position: Int) {

        val data  = getDataSet().get(position)
        holder.fillData(data, position)
    }
}