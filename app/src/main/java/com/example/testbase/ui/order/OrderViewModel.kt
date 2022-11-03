package com.example.testbase.ui.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.model_response.ListOrderResponse
import com.example.testbase.network.Api
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(val api: Api) : BaseViewModel() {

    val stateListOrder = MutableLiveData<ListOrderResponse>()

    fun getOrderByIdUser() {
        viewModelScope.launch(Dispatchers.IO) {
           stateListOrder.postValue(api.getOrderByUser(FirebaseUtil.getUid()))
        }
    }

}