package com.example.testbase.network

import com.example.testbase.base.BaseResponse
import com.example.testbase.model.*
import com.example.testbase.model_response.CartItemResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface Api {

    val path: String
        get() = "api/v1/user"

    @GET("api/v1/user/getUser/{id}")
    fun getUserInfor(@Path("id") id: String): Call<BaseResponse>

    @POST("api/v1/user/saveUser")
    fun saveUser(@Body user: User): Call<BaseResponse>

    @POST("api/v1/seller/saveSeller")
    fun saveSeller(@Body seller: Seller): Call<BaseResponse>

    @Multipart
    @POST("api/v1/product/saveProduct")
    fun saveProduct(
        @Part product_img: MultipartBody.Part,
        @Part("seller_id") seller_id: RequestBody,
        @Part("product_name") product_name: RequestBody,
        @Part("product_description") product_description: RequestBody,
        @Part("product_quantity") product_quantity: RequestBody,
        @Part("product_price") product_price: RequestBody,
        @Part("category_id") category_id: RequestBody,
    )
            : Call<Product>


    @GET("api/v1/product/getProductBySeller/{id}")
    fun getListProductBySeller(@Path("id") id: String): Call<List<Product>>

    ///api/v1/product/getProductById/3

    @GET("api/v1/product/getProductById/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

    @GET("api/v1/product/getAllProduct")
    suspend fun getAllProduct(): List<Product>

    @GET("api/v1/category/getAllCategory")
    suspend fun getAllCategory(): List<Category>

    @GET("api/v1/cart/addItemToCart")
    suspend fun addItemToCart(
        @Query("product_id") product_id: Int,
        @Query("user_id") user_id: String,
        @Query("quantity") quantity: Int,
    ): ResponseObject

    @GET("api/v1/cart/getCartById/{id}")
    suspend fun getCartByIdUser(@Path("id") id: String): CartResponse

    @GET("api/v1/cart/getListCartItemByIdUser/{id}")
    suspend fun getListCartItemByIdUser(@Path("id") id: String): List<CartItem>

    /**
     * type
     * 0 -> tang
     * 1 -> giam
     */
    @GET("api/v1/cart/changeQuantity")
    suspend fun changeQuantity(@Query("id") id: Int, @Query("type") type: Int): CartItemResponse

    @GET("api/v1/cart/deleteCartItem")
    suspend fun deleteCartItem(@Query("id") id: Int): BaseResponse


}