package com.example.testbase.ui_seller.statistic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.model.Order
import com.example.testbase.model_firebase.NotificationSend
import com.example.testbase.model_response.ListOrderItemResponse
import com.example.testbase.model_response.OrderResponse
import com.example.testbase.network.Api
import com.example.testbase.service.ApiFcm
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.base.BaseViewModel
import com.example.testbase.model.EStatusOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticSellerViewModel @Inject constructor(val api: Api, val apiFcm: ApiFcm) : BaseViewModel() {


    val stateOrderById = MutableLiveData<OrderResponse>()
    var mOrder = Order()
    val stateListOrderItem = MutableLiveData<ListOrderItemResponse>()
    val stateChangeOrder = MutableLiveData<OrderResponse>()


    fun getOrderById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = api.getOrderById(id)
            mOrder = data.data
            stateOrderById.postValue(data)
        }

    }

    fun getOrderItemByOrder(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            stateListOrderItem.postValue(api.getOrderItemByOrder(id))
        }

    }

    fun changeStatusTypeOrder(id: Int, type: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            stateChangeOrder.postValue(api.changeStatus(id, type))
        }
    }

    fun changeStatusOrder(eStatus: EStatusOrder, idOrder: Int, strMessage: String) {
        viewModelScope.launch(Dispatchers.IO) {
            stateChangeOrder.postValue(api.changeStatus(idOrder, eStatus.id))

            FirebaseUtil.changeStatusOrderTest(eStatus, idOrder) {
                FirebaseUtil.sendNotification(mOrder.cart.user.id, NotificationSend(strMessage), apiFcm)
            }
        }

    }


}