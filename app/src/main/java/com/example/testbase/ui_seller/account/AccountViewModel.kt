package com.example.testbase.ui_seller.account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.R
import com.example.testbase.base.BaseResponse
import com.example.testbase.model.User
import com.example.testbase.base.BaseViewModel
import com.example.testbase.model_response.SellerResponse
import com.example.testbase.model_response.UserResponse
import com.example.testbase.network.Api
import com.example.testbase.service.ApiFcm
import com.example.testbase.util.FirebaseUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(val api: Api, val apiFcm: ApiFcm) : BaseViewModel() {
    private val auth = Firebase.auth
    val stateLogin = MutableLiveData<String>()
    val stateUser = MutableLiveData<User>()
    val stateSeller = MutableLiveData<SellerResponse>()

    fun getUserInfo(id: String) {
        viewModelScope.launch {
            stateUser.value = api.getUserInfor(id)
        }
    }

    fun getSellerInfo(id: String) {
        viewModelScope.launch {
            stateSeller.postValue(api.getInforSeller(id))
        }
    }

    fun loggout() {
        FirebaseUtil.deleteToken()
    }
}