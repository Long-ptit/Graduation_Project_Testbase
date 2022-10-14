package com.example.testbase.ui.confirm_order

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.base.BaseResponse
import com.example.testbase.model.Cart
import com.example.testbase.model.CartItem
import com.example.testbase.model.User
import com.example.testbase.model_response.CartItemResponse
import com.example.testbase.network.Api
import com.example.testbase.util.Const
import com.example.testbase.view_model.BaseViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ConfirmOrderViewModel @Inject constructor(val api: Api) : BaseViewModel() {

    val stateListAllProduct = MutableLiveData<List<CartItem>>()
    val stateCart = MutableLiveData<Cart>()

    fun getAllCartItemInCart(idUser: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listAllProduct = api.getListCartItemByIdUser(idUser)
            stateListAllProduct.postValue(listAllProduct)
        }
    }

    fun getCart(idUser: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val cart = api.getCartByIdUser(idUser)
            stateCart.postValue(cart.data)
        }

    }

}