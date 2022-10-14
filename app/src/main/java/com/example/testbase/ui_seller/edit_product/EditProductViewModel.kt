package com.example.testbase.ui_seller.edit_product

import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.R
import com.example.testbase.base.BaseResponse
import com.example.testbase.model.Product
import com.example.testbase.model.User
import com.example.testbase.view_model.BaseViewModel
import com.example.testbase.network.Api
import com.example.testbase.util.Const
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(val api: Api) : BaseViewModel() {
    val stateProduct = MutableLiveData<Product>()

    fun getInforProduct(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = api.getProductById(id)
            stateProduct.postValue(data)
        }
    }

}