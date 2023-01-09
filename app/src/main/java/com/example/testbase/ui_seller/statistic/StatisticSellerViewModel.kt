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
import com.example.testbase.model.Statistic
import com.example.testbase.util.LogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticSellerViewModel @Inject constructor(val api: Api, val apiFcm: ApiFcm) : BaseViewModel() {


    val stateStatistic = MutableLiveData<Statistic>()

    init {
        getStatistic()
    }

    private fun getStatistic() {
        viewModelScope.launch(Dispatchers.IO) {
            LogUtil.log(FirebaseUtil.getUid())
            val data = api.getStatistic(FirebaseUtil.getUid())
            stateStatistic.postValue(data)
        }

    }




}