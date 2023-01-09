package com.example.testbase.ui_admin.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.model_response.ListOrderResponse
import com.example.testbase.network.Api
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.base.BaseViewModel
import com.example.testbase.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(val api: Api) : BaseViewModel() {

    val stateListOrder = MutableLiveData<ListOrderResponse>()
    val stateListCategory = MutableLiveData<List<Category>>()

    fun getOrderByIdUser() {
        viewModelScope.launch(Dispatchers.IO) {
            stateListOrder.postValue(api.getOrderByUser(FirebaseUtil.getUid()))
        }
    }

    fun loggout() {
        FirebaseUtil.deleteToken()
    }

    fun getAllCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            stateListCategory.postValue(api.getAllCategory())
        }
    }

    fun deleteCategory(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            api.deleteCategory(idCategory = id)
            getAllCategory()
        }
    }

}