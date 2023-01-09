package com.example.testbase.ui.detail_statistic

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.model.Product
import com.example.testbase.model.ResponseObject
import com.example.testbase.network.Api
import com.example.testbase.util.Const
import com.example.testbase.base.BaseViewModel
import com.example.testbase.model.Order
import com.example.testbase.model_response.ListReviewResponse
import com.example.testbase.model_response.StatisReviewResponse
import com.example.testbase.util.FirebaseUtil
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailStatisticViewModel @Inject constructor(val api: Api) : BaseViewModel() {

    val stateListOrderStatistic = MutableLiveData<List<Order>>()

    fun getStatisticByTime(start: String, end: String, sort: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val listReview =  api.getStatisticByTime(FirebaseUtil.getUid(), start, end, sort)
            stateListOrderStatistic.postValue(listReview)
        }
    }





}