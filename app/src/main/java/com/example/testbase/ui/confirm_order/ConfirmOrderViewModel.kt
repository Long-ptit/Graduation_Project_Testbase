package com.example.testbase.ui.confirm_order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.model_firebase.NotificationSend
import com.example.testbase.model_response.OrderResponse
import com.example.testbase.network.Api
import com.example.testbase.service.ApiFcm
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.base.BaseViewModel
import com.example.testbase.model.*
import com.example.testbase.util.LogUtil
import com.example.testbase.util.SharePreferenceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmOrderViewModel @Inject constructor(val api: Api, val apiFcm: ApiFcm) :
    BaseViewModel() {

    val stateListAllProduct = MutableLiveData<List<CartItem>>()
    val stateCart = MutableLiveData<Cart>()
    val stateCreateOrder = MutableLiveData(false)
    val stateDefaultShip = MutableLiveData<ShippingInformation>()
    val stateShip = MutableLiveData<ShippingInformation>()
    private var mIdShip = 0

    //cai nay de tao hoa don
    var listConfirmOrder = arrayListOf<CartItemConfirm>()

    fun getDefaultShip() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = api.getDefault(FirebaseUtil.getUid())
            mIdShip = data.id!!
            stateDefaultShip.postValue(data)
        }
    }

    fun getShip(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = api.getById(id)
            mIdShip = data.id!!
            stateShip.postValue(data)
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

    fun createOrder(textDescription: String, typePayment: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var order: Order
            listConfirmOrder.forEach {
                order = Order()
                order.typeStatus = 1
                order.shippingInformation = ShippingInformation(id = mIdShip)
                order.description = textDescription
                order.seller = it.seller
                order.paymentType = typePayment
                order.totalQuantity = it.listCartItem.sumOf { it.quantity }.toInt()
                order.cart = stateCart.value!!
                order.totalPrice =
                    it.listCartItem.sumOf { it.quantity * it.product.price }.toInt().toLong()

                LogUtil.log(order.toString())

                val data = api.createOrder(order)
                FirebaseUtil.sendNotification(
                    data.data.seller.id,
                    NotificationSend("${data.data.cart.user.name} vừa đặt hàng", data.data.id!!, "SELLER"),
                    apiFcm = apiFcm
                )
                FirebaseUtil.changeStatusOrderTest(EStatusOrder.CONFIRM, data.data.id!!) {

                }
            }
        }
        stateCreateOrder.postValue(true)
    }
}

