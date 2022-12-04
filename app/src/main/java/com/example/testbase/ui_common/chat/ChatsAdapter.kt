package com.example.testbase.ui_common.chat

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testbase.databinding.ItemChatBinding
import com.example.testbase.model.ChatMessage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class ChatsAdapter @Inject constructor() : RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {
    var listProduct: ArrayList<ChatMessage> = arrayListOf()
    fun setData(listTable: ArrayList<ChatMessage>) {
        this.listProduct = listTable
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun fillData(data: ChatMessage, position: Int){
            if (Firebase.auth.uid.equals(data.fromID)) {
                binding.tvMyChat.text = data.text
                binding.layoutOppositeMessage.visibility = View.GONE
                binding.layoutMyMessage.visibility = View.VISIBLE
            } else {
                binding.tvOppositeChat.text = data.text
                binding.layoutOppositeMessage.visibility = View.VISIBLE
                binding.layoutMyMessage.visibility = View.GONE
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
           val table = listProduct[position]
            fillData(table, position)
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return listProduct.size
    }

}