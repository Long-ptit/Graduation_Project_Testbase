package com.example.testbase.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testbase.R
import com.example.testbase.util.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val showSnackBar: SingleLiveEvent<String> = SingleLiveEvent()
    val showLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()

    protected fun showLoading() {
        isLoading.value = true
       // errorMessage.value = null
    }

    protected fun hideLoading() {
        isLoading.value = false
    }

    protected fun handleApiError(error: Throwable?) {
        if (error == null) {
            errorMessage.value = R.string.app_name
        }
    }

}