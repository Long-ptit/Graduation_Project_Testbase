package com.example.testbase.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.base.BaseResponse
import com.example.testbase.model.User
import com.example.testbase.network.Api
import com.example.testbase.util.Const
import com.example.testbase.base.BaseViewModel
import com.example.testbase.util.FirebaseUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val api: Api) : BaseViewModel() {
    val stateUserInfor = MutableLiveData<User>()

//    fun getUserInfo(id: String) {
//        api.getUserInfor(id).enqueue(object : Callback<BaseResponse?> {
//            override fun onResponse(call: Call<BaseResponse?>, response: Response<BaseResponse?>) {
//                val user = response.body()?.data as User
//                Log.d("ptit", "onResponse: ${user.name}")
//            }
//
//            override fun onFailure(call: Call<BaseResponse?>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })
//
//    }

    fun getUserInfor() {
        viewModelScope.launch {
            val dataInfor = api.getUserInfor(FirebaseUtil.getUid())
            stateUserInfor.value = dataInfor
            showSnackBar.value = "Hi ${dataInfor.name}"
        }
    }




}