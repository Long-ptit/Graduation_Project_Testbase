package com.example.testbase.ui_admin.add_category

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.model.Category
import com.example.testbase.model.Product
import com.example.testbase.base.BaseViewModel
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
class AddCategoryViewModel @Inject constructor(val api: Api, val application: Application) : BaseViewModel() {

    val statusAddCategory = MutableLiveData<Category>()
    val statusCategory = MutableLiveData<Category>()
    val stateEditCategory = MutableLiveData<Category>()


    fun saveProductWithImage(category: Category, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val name = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), category.name)
            val description = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), category.description)
            val strRealPath = RealPathUtils.getRealPath(application, uri)
            val file = File(strRealPath)
            val requestFile = file
                .asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData(
                "category_img",
                file.name,
                requestFile
            )
            statusAddCategory.postValue(api.saveCategory(body, name, description))
            showSnackBar.postValue("Thêm thành công")
        }
    }

    fun getInforCategory(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            statusCategory.postValue(api.getCategory(idCategory = id))
        }
    }



    fun editCategory(category: Category, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val name = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), category.name)
            val description = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), category.description)
            val idCategory = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), category.id.toString())
            val strRealPath = RealPathUtils.getRealPath(application, uri)
            val file = File(strRealPath)
            val requestFile = file
                .asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData(
                "category_img",
                file.name,
                requestFile
            )
            stateEditCategory.postValue(api.editCategory(body, name, description, idCategory))
            showSnackBar.postValue("Thêm thành công")
        }
    }

}