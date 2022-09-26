package com.example.testbase.ui.sign_up

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.testbase.R
import com.example.testbase.base.BaseResponse
import com.example.testbase.view_model.BaseViewModel
import com.example.testbase.model.User
import com.example.testbase.network.Api
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(val api: Api) : BaseViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    val firebaseResponse: MutableLiveData<String> = MutableLiveData()
    val stateSaveUser: MutableLiveData<Boolean> = MutableLiveData()

    fun signUpCustomer(email: String, password: String) {
        showLoading()
        Log.d("ptit", "kaka: ")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    firebaseResponse.postValue(user?.uid)

                } else {
                    errorMessage.value = R.string.default_error
                    hideLoading()
                }
            }
    }

    fun saveUserToDatabase(user: User) {
        api.saveUser(user).enqueue(object : Callback<BaseResponse?> {
            override fun onResponse(call: Call<BaseResponse?>, response: Response<BaseResponse?>) {
                errorMessage.value = R.string.default_success
                hideLoading()
                stateSaveUser.postValue(true)
            }

            override fun onFailure(call: Call<BaseResponse?>, t: Throwable) {
                errorMessage.value = R.string.default_error
                hideLoading()
                stateSaveUser.postValue(false)
            }
        })
    }
}