package com.example.testbase.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.testbase.base.BaseResponse
import com.example.testbase.model.User
import com.example.testbase.network.Api
import com.example.testbase.util.Const
import com.example.testbase.base.BaseViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val api: Api) : BaseViewModel() {
    private val auth = Firebase.auth
    private val database = Firebase.database.reference
    val stateLogin = MutableLiveData<String>()
    val stateUser = MutableLiveData<User>()
    val stateTypeLogin = MutableLiveData<String>()

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

    fun checkType(id: String) {
        database
            .child(Const.PATH_ACCOUNT)
            .child(Const.PATH_TYPE)
            .child(id)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value.toString().equals(Const.SELLER)) {
                        stateTypeLogin.value = Const.SELLER
                    } else if (snapshot.value.toString().equals(Const.USER)) {
                        Log.d("ptit", "onDataChange: user")
                        stateTypeLogin.value = Const.USER
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

    }

}