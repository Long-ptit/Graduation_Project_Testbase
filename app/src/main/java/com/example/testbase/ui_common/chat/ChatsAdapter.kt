package com.example.testbase.ui_common.chat

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testbase.databinding.ItemChatBinding
import com.example.testbase.model.ChatMessage
import com.example.testbase.util.Const
import com.example.testbase.util.LogUtil
import com.example.testbase.util.Util
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
            LogUtil.log("time: " + data.timestamp.toString())
            val time = Util.convertLongToDate(data.timestamp)
            binding.tvMyTime.text = time
            binding.tvOppositeTime.text = time

            if (Firebase.auth.uid.equals(data.fromID)) {
                Glide
                    .with(binding.root.context)
                    .load(Const.BASE_URL + Const.PATH_IMAGE + data.fromID + ".jpg")
                    .into(binding.imgMyUser)
                binding.tvMyChat.text = data.text
                binding.layoutOppositeMessage.visibility = View.GONE
                binding.layoutMyMessage.visibility = View.VISIBLE
            } else {
                Glide
                    .with(binding.root.context)
                    .load(Const.BASE_URL + Const.PATH_IMAGE + data.fromID + ".jpg")
                    .into(binding.imgUser)
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