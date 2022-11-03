package com.example.testbase.ui.detail_order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.model_response.ListOrderItemResponse
import com.example.testbase.model_response.OrderResponse
import com.example.testbase.network.Api
import com.example.testbase.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailOrderViewModel @Inject constructor(val api: Api) : BaseViewModel() {


    val stateOrderById = MutableLiveData<OrderResponse>()
    val stateListOrderItem = MutableLiveData<ListOrderItemResponse>()


    fun getOrderById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            stateOrderById.postValue(api.getOrderById(id))
        }

    }

    fun getOrderItemByOrder(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            stateListOrderItem.postValue(api.getOrderItemByOrder(id))
        }
    }



}