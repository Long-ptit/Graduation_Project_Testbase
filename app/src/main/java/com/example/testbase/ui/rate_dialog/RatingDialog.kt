package com.example.testbase.ui.rate_dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.testbase.R
import com.example.testbase.databinding.LayoutRatingDialogBinding
import com.example.testbase.model.Product
import com.example.testbase.model.Review
import com.example.testbase.model.User
import com.example.testbase.network.Api
import com.example.testbase.ui.rate_dialog.adapter.CommentAdapter
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.util.LogUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RatingDialog : DialogFragment() {

    lateinit var binding: LayoutRatingDialogBinding

    @Inject
    lateinit var api: Api

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutRatingDialogBinding.inflate(inflater, container, false)

        return binding.root;
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSend.setOnClickListener {
            LogUtil.log("kakak")
            GlobalScope.launch(Dispatchers.IO) {
                api.createReview(Review(
                    numStars = binding.ratingBar.rating.toInt(),
                    content = binding.edt.text.toString(),
                    createAt = System.currentTimeMillis(),
                    user = User(id = FirebaseUtil.getUid()),
                    product = Product(id = arguments?.getInt(ARG_ID_PRODUCT)!!)
                ))
            }
            dismiss()
        }
    }


    companion object {
        const val ARG_ID_PRODUCT = "ARG_ID_PRODUCT"

        fun newInstance(id: Int) =

            RatingDialog().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID_PRODUCT, id)
                }
            }
    }

}