package com.example.testbase.ui_seller.account

import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.testbase.R
import com.example.testbase.base.BaseResponse
import com.example.testbase.model.User
import com.example.testbase.view_model.BaseViewModel
import com.example.testbase.network.Api
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(val api: Api) : BaseViewModel() {
    private val auth = Firebase.auth
    val stateLogin = MutableLiveData<String>()
    val stateUser = MutableLiveData<User>()

    fun getUserInfo(id: String) {
        api.getUserInfor(id).enqueue(object : Callback<BaseResponse?> {
            override fun onResponse(call: Call<BaseResponse?>, response: Response<BaseResponse?>) {
                val user = response.body()?.data as User
                Log.d("ptit", "onResponse: ${user.name}")
            }

            override fun onFailure(call: Call<BaseResponse?>, t: Throwable) {
                errorMessage.value = R.string.default_error
            }
        })

    }

    fun loggout() {
        auth.signOut()
    }

}