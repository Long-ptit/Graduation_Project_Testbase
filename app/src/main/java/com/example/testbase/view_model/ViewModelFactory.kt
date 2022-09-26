package com.example.testbase.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.testbase.network.Api
import com.example.testbase.ui.sign_up.SignUpViewModel


class ViewModelFactory(private val apiHelper: Api) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(apiHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}