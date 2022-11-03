package com.example.testbase.ui.detail_product

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.model.Product
import com.example.testbase.model.ResponseObject
import com.example.testbase.network.Api
import com.example.testbase.util.Const
import com.example.testbase.base.BaseViewModel
import com.example.testbase.model_response.ListReviewResponse
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(val api: Api) : BaseViewModel() {

    val stateProductById = MutableLiveData<Product>()
    val stateSaveCartItem = MutableLiveData<ResponseObject>()
    val stateCreateDeeplink = MutableLiveData<String>()
    val stateRepresentReview = MutableLiveData<ListReviewResponse>()

    fun getProductById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
           val product =  api.getProductById(id)
            stateProductById.postValue(product)
        }
    }

    fun addItemToCart(productId: Int, userId: String, quantity: Int) {
        showLoading()
        viewModelScope.launch(Dispatchers.IO) {
             stateSaveCartItem.postValue(api.addItemToCart(
                product_id = productId,
                user_id = userId,
                quantity = quantity
            ))
        }
        hideLoading()
    }

    fun createDeeplink(productId: Int) {
        val shortLinkTask = Firebase.dynamicLinks.shortLinkAsync {
            link = Uri.parse("https://www.doan.com/?${Const.PRODUCT_ID}=$productId")
            domainUriPrefix = Const.DEEPLINK_PREFIX
            androidParameters("com.example.testbase") {}
            // Set parameters
            // ...
        }.addOnSuccessListener {
            Log.d("ptit", "createDeeplink: " + it.shortLink.toString())
            stateCreateDeeplink.postValue(it.shortLink.toString())

        }.addOnFailureListener {
            // Error
            // ...
        }
    }

    fun getRepresentReviewByProduct(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val listReview =  api.getRepresentReviewByProduct(id)
            stateRepresentReview.postValue(listReview)
        }
    }


}