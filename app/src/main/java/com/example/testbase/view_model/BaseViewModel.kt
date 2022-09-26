package com.example.testbase.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testbase.R

abstract class BaseViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    protected fun showLoading() {
        isLoading.value = true
        errorMessage.value = null
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