package com.example.testbase.ui.detail_order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.model_response.ListOrderItemResponse
import com.example.testbase.model_response.OrderResponse
import com.example.testbase.network.Api
import com.example.testbase.base.BaseViewModel
import com.example.testbase.model.EStatusOrder
import com.example.testbase.model.ReviewResponse
import com.example.testbase.model_firebase.NotificationSend
import com.example.testbase.service.ApiFcm
import com.example.testbase.util.FirebaseUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailOrderViewModel @Inject constructor(val api: Api, val apiFcm: ApiFcm) : BaseViewModel() {


    val stateOrderById = MutableLiveData<OrderResponse>()
    val stateListOrderItem = MutableLiveData<ListOrderItemResponse>()
    val stateCheckReview = MutableLiveData<ReviewResponse>()
    val stateDeleteOrder = MutableLiveData<OrderResponse>()

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

    fun checkReview(idOrderItem: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            stateCheckReview.postValue(api.testReview(idOrderItem))
        }
    }

    fun deleteOrder(idOrder: Int,idSeller: String, status: EStatusOrder) {
        viewModelScope.launch {
            api.changeStatus(idOrder, status.id)
            val dataSend = "Đơn hàng ${idOrder} đã được bị hủy"
            FirebaseUtil.changeStatusOrderTest(status, idOrder) {
                FirebaseUtil.sendNotification(idSeller, NotificationSend(dataSend, idOrder, "SELLER"), apiFcm)
            }
        }
    }



}