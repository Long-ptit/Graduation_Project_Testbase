package com.example.testbase.network

import com.example.testbase.base.BaseResponse
import com.example.testbase.model.*
import com.example.testbase.model_response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface Api {

    val path: String
        get() = "api/v1/user"

    @GET("api/v1/user/getInforUser/{id}")
    fun getUserInfor(@Path("id") id: String): Call<UserResponse>

    @GET("api/v1/user/getInforSeller/{id}")
    suspend fun getInforSeller(@Path("id") id: String): SellerResponse

    @POST("api/v1/user/saveUser")
    fun saveUser(@Body user: User): Call<BaseResponse>

    @Multipart
    @POST("api/v1/user/saveImage")
    suspend fun saveImage(
        @Part user_img: MultipartBody.Part,
        @Part("user_id") user_id: RequestBody,
    )
            : ResponseObject


    //Section Seller
    @POST("api/v1/seller/saveSeller")
    fun saveSeller(@Body seller: Seller): Call<BaseResponse>


    //Product
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
    suspend fun getListProductBySeller(@Path("id") id: String): List<Product>

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

    //order
    /**
     * type
     * 1 -> waiting for confirm
     * 2 -> prepare product
     * 3 -> send to shipper
     * 4 -> cancel
     */

    @POST("api/v1/order/changeStatus")
    suspend fun changeStatus(
        @Query("id_order") id_order: Int,
        @Query("type_status") type_status: Int,
    ): OrderResponse

    @POST("api/v1/order/createOrder")
    suspend fun createOrder(@Body order: Order): OrderResponse

    @GET("api/v1/order/getOrderByUser/{id}")
    suspend fun getOrderByUser(@Path("id") id: String): ListOrderResponse

    @GET("api/v1/order/getOrderById/{id}")
    suspend fun getOrderById(@Path("id") id: Int): OrderResponse

    @GET("api/v1/order/getOrderItemByOrder/{id}")
    suspend fun getOrderItemByOrder(@Path("id") id: Int): ListOrderItemResponse

    @GET("api/v1/order/getOrderByIdSeller/{id}")
    suspend fun getOrderByIdSeller(@Path("id") idSeller: String): ListOrderResponse

    @POST("api/v1/review/createReview")
    suspend fun createReview(@Body review: Review): BaseResponse

    @GET("api/v1/review/getRepresentReviewByProduct/{id}")
    suspend fun getRepresentReviewByProduct(@Path("id") idProduct: Int): ListReviewResponse

    @GET("api/v1/review/getStatisticReview/{id}")
    suspend fun getStatisticReview(@Path("id") idProduct: Int): StatisReviewResponse

    @GET("api/v1/review/getAllReviewById/{id}")
    suspend fun getAllReviewById(@Path("id") idProduct: Int): ListReviewResponse

    @GET("api/v1/ship/getDefault/{id}")
    suspend fun getDefault(@Path("id") idUser: String): ShippingInformation

    @GET("api/v1/ship/getAllShip/{id}")
    suspend fun getAll(@Path("id") idUser: String): List<ShippingInformation>
}