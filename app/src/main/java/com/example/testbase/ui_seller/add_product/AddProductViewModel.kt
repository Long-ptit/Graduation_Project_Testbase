package com.example.testbase.ui_seller.add_product

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.model.Category
import com.example.testbase.model.Product
import com.example.testbase.base.BaseViewModel
import com.example.testbase.network.Api
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
    fun sendProduct(name: String, description: String, quantity: Int, price: Int, uri: Uri, idCategory: Int) {


        val nameProduct = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), name)
        val descriptionProduct = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), description)
        val quantityProduct = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), quantity.toString())
        val priceProduct = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), price.toString())
        val sellerId = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), Firebase.auth.uid.toString())
        val categoryId = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), idCategory.toString())
        val strRealPath = RealPathUtils.getRealPath(application, uri)
        val file = File(strRealPath)
        val requestFile = file
            .asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData(
            "product_img",
            file.name,
            requestFile
        )

        api.saveProduct(
            product_img = body,
            product_name = nameProduct,
            product_description = descriptionProduct,
            product_price = priceProduct,
            product_quantity = quantityProduct,
            seller_id = sellerId,
            category_id = categoryId
        ).enqueue(object : Callback<Product?> {
            override fun onResponse(call: Call<Product?>, response: Response<Product?>) {
                statusAddProduct.postValue(response.body())
                Log.d("ptit", "onResponse: ")
            }

            override fun onFailure(call: Call<Product?>, t: Throwable) {
                Log.d("ptit", "onFaild: " + t.message)
            }
        })
    }

    fun getAllCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            statusCategory.postValue(api.getAllCategory())
        }
    }
}