package com.example.testbase.ui_seller.edit_product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.model.Product
import com.example.testbase.base.BaseViewModel
import com.example.testbase.network.Api
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(val api: Api) : BaseViewModel() {
    val stateProduct = MutableLiveData<Product>()

    fun getInforProduct(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = api.getProductById(id)
            stateProduct.postValue(data)
        }
    }

}