package com.example.testbase.ui_common.chats

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testbase.databinding.ItemChatsBinding
import com.example.testbase.model.LastestMessage
import com.example.testbase.model.Seller
import com.example.testbase.model.User
import com.example.testbase.ui_common.chat.ChatActivity
import com.example.testbase.util.Const
import com.example.testbase.util.Util
import javax.inject.Inject

class ChatHomeAdapter @Inject constructor() : RecyclerView.Adapter<ChatHomeAdapter.ViewHolder>() {
    var listProduct: ArrayList<LastestMessage> = arrayListOf()

    fun setData(listSeller: ArrayList<LastestMessage>) {
        this.listProduct = listSeller
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: ItemChatsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun fillData(data: LastestMessage, position: Int){
            binding.tvName.text = data.seller.shopName ?: data.seller.name
            binding.tvNewest.text = data.chatMsg.text
            binding.tvTime.text = Util.convertMiliToDate(System.currentTimeMillis() - data.chatMsg.timestamp)

            itemView.setOnClickListener {
                val intent = Intent(binding.root.context, ChatActivity::class.java)
                intent.putExtra(Const.USER_ID, data.seller.id)
                binding.root.context.startActivity(intent)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChatsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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