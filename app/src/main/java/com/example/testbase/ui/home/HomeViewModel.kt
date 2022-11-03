package com.example.testbase.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.model.Product
import com.example.testbase.network.Api
import com.example.testbase.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val api: Api) : BaseViewModel() {
    val stateListAllProduct = MutableLiveData<List<Product>>()

    fun getAllProduct() {
        viewModelScope.launch() {
            val listAllProduct = api.getAllProduct()
            stateListAllProduct.postValue(listAllProduct)
        }
    }

}