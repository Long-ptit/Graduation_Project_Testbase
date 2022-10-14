package com.example.testbase.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.base.BaseResponse
import com.example.testbase.model.Cart
import com.example.testbase.model.CartItem
import com.example.testbase.model.Product
import com.example.testbase.model.ResponseObject
import com.example.testbase.model_response.CartItemResponse
import com.example.testbase.network.Api
import com.example.testbase.view_model.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(val api: Api) : BaseViewModel() {
    val stateListAllProduct = MutableLiveData<List<CartItem>>()
    val stateCart = MutableLiveData<Cart>()
    val stateChangeQuantity = MutableLiveData<CartItemResponse>()
    val stateDeleteCartItem = MutableLiveData<BaseResponse>()

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

    fun changeQuantityProduct(idProductItem: Int, type: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            stateChangeQuantity.postValue(api.changeQuantity(idProductItem, type))
        }
    }

    fun deleteCartItem(idProductItem: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            stateDeleteCartItem.postValue(api.deleteCartItem(idProductItem))
        }
    }

}