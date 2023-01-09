package com.example.testbase.util

object Const {
    const val BASE_URL = "http://172.20.10.3:8080"
    const val BASE_URL_FCM = "https://fcm.googleapis.com/"
    const val PRODUCT_ID = "PRODUCT_ID"
    const val ORDER_ID = "ORDER_ID"
    const val SELLER_ID = "eVR5C3l93sadIexIewsZFmDAzCl1"

    const val PUB_KEY_PAYMENT = "pk_test_51KwzuDJMW3MKEexIASee5Um024S0SBHV14WN2b8vBjXZfNezVDe2UEWkabyvdyhQKWt2BakDKZ8Z6wKGENW3VbZ700Mb92lXjY"
    const val SECRET_KEY_PAYMENT = "Bearer sk_test_51KwzuDJMW3MKEexITRhkSptoJBfKwN7vMImCYpZGo7IH62NyBPou8OVUT4zo97cUIVNWrvGMRJezjV3fFnTI6uNt00MPnZg5fr"
    const val SERVER_KEY = "AAAAn86AZjA:APA91bF1884gwnkAM9RIgKV3nXIlRCE0rag0h8IULr4tQs_n2O2mSu8dvNvelcFII1jC7njW3dKrPAMblUlmfomIeUIGyLIAloJnKCyM9kcQ11iKTiPBYY6Roww1jhRxxl9kCKv7rWiu"
    const val USER_ID = "USER_ID"

    //firebase
    const val PATH_USER = "USER"
    const val PATH_TOKEN_FCM = "TOKEN_FCM"
    const val PATH_INFOR = "INFOR"
    const val PATH_ACCOUNT = "ACCOUNT"
    const val PATH_TYPE = "TYPE"
    const val CHATS = "CHATS"
    const val LATEST_MESSAGE = "LATEST_MESSAGE"
    const val PATH_STATUS_ORDER = "STATUS_ORDER"

    //value
    const val USER = "USER"
    const val SELLER = "SELLER"
    const val PATH_IMAGE = "/api/v1/product/getImage/"
    //Type_Click
    const val TYPE_CLICK_DEC = "TYPE_CLICK_DEC"
    const val TYPE_CLICK_INC = "TYPE_CLICK_INC"
    const val TYPE_CLICK_DELETE = "TYPE_CLICK_DELETE"
    const val DEEPLINK_PREFIX = "https://tlong.page.link"


    /**
     * status of order
     */

    const val STATUS_ORDER_CONFIRM = "Đang chờ xác nhận"
    const val STATUS_ORDER_PROCESSING = "Đang chuẩn bị hàng"
    const val STATUS_ORDER_SHIPPING = "Đang giao hàng"
    const val STATUS_ORDER_COMPLETED = "Giao hàng thành công"
    const val STATUS_ORDER_CANCEL = "Hủy đơn hàng"


}