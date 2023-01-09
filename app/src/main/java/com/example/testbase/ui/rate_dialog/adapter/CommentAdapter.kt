package com.example.testbase.ui.rate_dialog.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testbase.databinding.ItemRateBinding
import com.example.testbase.databinding.LayoutItemConfirmOrderBinding
import com.example.testbase.databinding.LayoutItemProductCartBinding
import com.example.testbase.model.CartItem
import com.example.testbase.model.OrderItem
import com.example.testbase.model.Product
import com.example.testbase.model.Review
import com.example.testbase.util.Const
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.util.Util
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentAdapter @Inject constructor() :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    var itemClickListener: ((id: Int) -> Unit)? = null

    var listReviewItem: ArrayList<Review> = arrayListOf()
    fun setData(listReviewItem: ArrayList<Review>) {
        this.listReviewItem = listReviewItem
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: ItemRateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun fillData(data: Review, position: Int) {
            binding.tvContent.text = data.content
            binding.myRatingBar.rating = data.numStars.toFloat()
            binding.tvDes.text = Util.convertLongToDate(data.createAt)
            Glide
                .with(binding.root.context)
                .load(Const.BASE_URL + Const.PATH_IMAGE + data.user.id + ".jpg")
                .into(binding.imgItem)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val review = listReviewItem[position]
            fillData(review, position)
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return listReviewItem.size
    }

}