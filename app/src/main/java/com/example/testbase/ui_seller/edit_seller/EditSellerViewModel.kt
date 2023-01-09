//package com.example.testbase.ui_seller.edit_seller
//
//import android.app.Application
//import android.net.Uri
//import android.util.Log
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.viewModelScope
//import com.example.testbase.R
//import com.example.testbase.base.BaseResponse
//import com.example.testbase.model.Seller
//import com.example.testbase.base.BaseViewModel
//import com.example.testbase.model.User
//import com.example.testbase.network.Api
//import com.example.testbase.util.Const
//import com.example.testbase.util.FirebaseUtil
//import com.example.testbase.util.RealPathUtils
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import okhttp3.RequestBody.Companion.asRequestBody
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.io.File
//import javax.inject.Inject
//
//@HiltViewModel
//class EditSellerViewModel @Inject constructor(val api: Api, val application: Application) :
//    BaseViewModel() {
//
//
//
//
//    fun saveInforSeller(seller: Seller, uri: Uri) {
//
//        saveImage(uri, seller.id)
//
//        api.saveSeller(seller).enqueue(object : Callback<BaseResponse?> {
//            override fun onResponse(call: Call<BaseResponse?>, response: Response<BaseResponse?>) {
//                errorMessage.value = R.string.default_success
//                hideLoading()
//                stateSaveToDatabase.postValue(true)
//            }
//
//            override fun onFailure(call: Call<BaseResponse?>, t: Throwable) {
//                errorMessage.value = R.string.default_error
//                hideLoading()
//                stateSaveToDatabase.postValue(false)
//            }
//        })
//    }
//
//    private fun saveImage(uri: Uri, id: String) {
//
//        viewModelScope.launch(Dispatchers.IO) {
//            val userId = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), id)
//            val strRealPath = RealPathUtils.getRealPath(application, uri)
//            val file = File(strRealPath)
//            val requestFile = file
//                .asRequestBody("multipart/form-data".toMediaTypeOrNull())
//            val body = MultipartBody.Part.createFormData(
//                "user_img",
//                file.name,
//                requestFile
//            )
//
//            api.saveImage(
//                user_img = body,
//                user_id = userId
//            )
//        }
//    }
//
//    private fun getSellerInfor() {
//        viewModelScope.launch(Dispatchers.IO) {
//            api.getInforSeller(FirebaseUtil.getUid())
//        }
//    }
//}