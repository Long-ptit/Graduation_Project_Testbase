package com.example.testbase.ui.confirm_order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.model.Cart
import com.example.testbase.model.CartItem
import com.example.testbase.model.Order
import com.example.testbase.model_firebase.NotificationSend
import com.example.testbase.model_response.OrderResponse
import com.example.testbase.network.Api
import com.example.testbase.service.ApiFcm
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.base.BaseViewModel
import com.example.testbase.model.ShippingInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmOrderViewModel @Inject constructor(val api: Api, val apiFcm: ApiFcm) : BaseViewModel() {

    val stateListAllProduct = MutableLiveData<List<CartItem>>()
    val stateCart = MutableLiveData<Cart>()
    val stateCreateOrder = MutableLiveData<OrderResponse>()
    val stateDefaultShip = MutableLiveData<ShippingInformation>()

    fun getDefaultShip() {
        viewModelScope.launch(Dispatchers.IO) {
            stateDefaultShip.postValue(api.getDefault(FirebaseUtil.getUid()))
        }
    }

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

    fun createOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = api.createOrder(order)
            FirebaseUtil.sendNotification(data.data.seller.id, NotificationSend("${data.data.cart.user.name} vừa đặt hàng"), apiFcm = apiFcm)
            stateCreateOrder.postValue(data)
        }
    }



}