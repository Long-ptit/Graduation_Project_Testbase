package com.example.testbase.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecycleAdapter<T, V: RecyclerView.ViewHolder?>(var data: MutableList<T>)
    : RecyclerView.Adapter<V>(){



    protected abstract fun getLayoutResourceItem(): Int

    abstract fun onCreateBasicViewHolder(binding: ViewDataBinding?): V
    abstract fun onBindBasicItemView(holder: V, position: Int)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate(layoutInflater, getLayoutResourceItem(), parent, false) as ViewDataBinding
        return onCreateBasicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: V, position: Int) {
        onBindBasicItemView(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun getDataSet(): List<T> {
        return data
    }
}