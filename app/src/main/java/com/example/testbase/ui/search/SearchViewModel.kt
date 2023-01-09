package com.example.testbase.ui.search

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val api: Api, val apiFcm: ApiFcm) :
    BaseViewModel() {

    val dataListProductSearch = MutableLiveData<List<Product>>()

    fun searchProduct(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataListProductSearch.postValue(api.searchProduct(keyword))
        }
    }

}

