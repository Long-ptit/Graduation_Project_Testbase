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
    suspend fun getUserInfor(@Path("id") id: String): User

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
    suspend fun saveProduct(
        @Part product_img: MultipartBody.Part,
        @Part("product_id") product_id: RequestBody,

        )        : Product

    @POST("api/v1/product/saveProductWithoutImage")
    suspend fun saveProductWithoutImage(
        @Body product: Product,
    )
            : Product


    @GET("api/v1/product/getProductBySeller/{id}")
    suspend fun getListProductBySeller(@Path("id") id: String): List<Product>

    ///api/v1/product/getProductById/3

    @GET("api/v1/product/getProductById/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

    @GET("api/v1/product/getAllProduct")
    suspend fun getAllProduct(): List<Product>

    @POST("api/v1/product/searchProduct")
    suspend fun searchProduct(@Query("keyword") key: String): List<Product>


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

    @GET("api/v1/review/testReview/{id}")
    suspend fun testReview(@Path("id") idOrderItem: Int): ReviewResponse


    @GET("api/v1/review/getAllReviewById/{id}")
    suspend fun getAllReviewById(@Path("id") idProduct: Int): ListReviewResponse


    @GET("api/v1/ship/getDefault/{id}")
    suspend fun getDefault(@Path("id") idUser: String): ShippingInformation

    @GET("api/v1/ship/getById/{id}")
    suspend fun getById(@Path("id") idShip: Int): ShippingInformation

    @GET("api/v1/ship/deleteAddress/{id}")
    suspend fun deleteAddress(@Path("id") idShip: Int): ShippingInformation

    @POST("api/v1/ship/saveAddress")
    suspend fun saveAddress(@Body ship: ShippingInformation): ShippingInformation

    @GET("api/v1/ship/getAllShip/{id}")
    suspend fun getAll(@Path("id") idUser: String): List<ShippingInformation>

    //statistic

    @GET("api/v1/statistic/getStatistic/{id}")
    suspend fun getStatistic(@Path("id") idSeller: String): Statistic

    @GET("api/v1/statistic/getStatisticByTime")
    suspend fun getStatisticByTime(
        @Query("id_seller") idSeller: String,
        @Query("start") start: String,
        @Query("end") end: String,
        @Query("sort") sort: Int,
    ): List<Order>

    //admin api

    @Multipart
    @POST("api/v1/category/saveCategory")
    suspend fun saveCategory(
        @Part category_img: MultipartBody.Part,
        @Part("category_name") category_name: RequestBody,
        @Part("category_description") category_description: RequestBody,

        )        : Category

    @Multipart
    @POST("api/v1/category/editCategory")
    suspend fun editCategory(
        @Part category_img: MultipartBody.Part,
        @Part("category_name") category_name: RequestBody,
        @Part("category_description") category_description: RequestBody,
        @Part("category_id") category_id: RequestBody,

        )        : Category

    @GET("api/v1/category/getAllCategory")
    suspend fun getAllCategory(): List<Category>

    @GET("api/v1/category/getCategory/{id}")
    suspend fun getCategory(@Path("id") idCategory: Int): Category

    @GET("api/v1/category/deleteCategory/{id}")
    suspend fun deleteCategory(@Path("id") idCategory: Int): Category

    @GET("api/v1/category/getAllManu")
    suspend fun getAllManu(): List<Manufacturer>



}