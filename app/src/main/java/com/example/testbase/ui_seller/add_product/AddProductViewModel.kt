package com.example.testbase.ui_seller.add_product

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.model.Category
import com.example.testbase.model.Product
import com.example.testbase.base.BaseViewModel
import com.example.testbase.model.Manufacturer
import com.example.testbase.network.Api
import com.example.testbase.util.LogUtil
import com.example.testbase.util.RealPathUtils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(val api: Api,val application: Application) : BaseViewModel() {

    val statusAddProduct = MutableLiveData<Product>()
    val statusCategory = MutableLiveData<List<Category>>()
    val statusManu = MutableLiveData<List<Manufacturer>>()

    fun saveProductWithoutImage(product: Product, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
           val data = api.saveProductWithoutImage(product)
            val sellerId = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), data.id.toString())
            val strRealPath = RealPathUtils.getRealPath(application, uri)
            val file = File(strRealPath)
            val requestFile = file
                .asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData(
                "product_img",
                file.name,
                requestFile
            )
            LogUtil.log("kaka " + data.id.toString())
            statusAddProduct.postValue(api.saveProduct(body, sellerId))
        }
    }

    fun getAllCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            statusCategory.postValue(api.getAllCategory())
        }
    }

    fun getAllManu() {
        viewModelScope.launch(Dispatchers.IO) {
            statusManu.postValue(api.getAllManu())
        }
    }
}