package com.example.testbase.ui.sign_up

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.testbase.R
import com.example.testbase.base.BaseResponse
import com.example.testbase.model.Seller
import com.example.testbase.view_model.BaseViewModel
import com.example.testbase.model.User
import com.example.testbase.network.Api
import com.example.testbase.util.Const
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
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
    val stateSaveToDatabase: MutableLiveData<Boolean> = MutableLiveData()
    private val database = Firebase.database.reference

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

        database
            .child(Const.PATH_ACCOUNT)
            .child(Const.PATH_TYPE)
            .child(user.id)
            .setValue(Const.USER)

        database.child(Const.PATH_USER).child(Const.PATH_INFOR).child(user.id).setValue(user).addOnCompleteListener {
            api.saveUser(user).enqueue(object : Callback<BaseResponse?> {
                override fun onResponse(call: Call<BaseResponse?>, response: Response<BaseResponse?>) {
                    errorMessage.value = R.string.default_success
                    hideLoading()
                    stateSaveToDatabase.postValue(true)
                }

                override fun onFailure(call: Call<BaseResponse?>, t: Throwable) {
                    errorMessage.value = R.string.default_error
                    hideLoading()
                    stateSaveToDatabase.postValue(false)
                }
            })
        }
    }

    fun saveSellerToDatabase(seller: Seller) {
        Log.d("ptit", "saveSellerToDatabase: ")
        database
            .child(Const.PATH_ACCOUNT)
            .child(Const.PATH_TYPE)
            .child(seller.id)
            .setValue(Const.SELLER)

        database.child(Const.PATH_USER).child(Const.PATH_INFOR).child(seller.id).setValue(seller).addOnCompleteListener {
            api.saveSeller(seller).enqueue(object : Callback<BaseResponse?> {
                override fun onResponse(call: Call<BaseResponse?>, response: Response<BaseResponse?>) {
                    errorMessage.value = R.string.default_success
                    hideLoading()
                    stateSaveToDatabase.postValue(true)
                }

                override fun onFailure(call: Call<BaseResponse?>, t: Throwable) {
                    errorMessage.value = R.string.default_error
                    hideLoading()
                    stateSaveToDatabase.postValue(false)
                }
            })
        }
    }
}