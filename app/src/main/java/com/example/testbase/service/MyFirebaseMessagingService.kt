//package com.example.testbase.service
//import android.annotation.SuppressLint
//import android.content.Intent
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.google.firebase.messaging.RemoteMessage
//
//@SuppressLint("MissingFirebaseInstanceTokenRefresh")
//class MyFirebaseMessagingService : FirebaseMessagingService() {
//
//
//    @SuppressLint("LongLogTag")
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        super.onMessageReceived(remoteMessage)
//        handleNotification(remoteMessage)
//    }
//
//    private fun handleNotification(remoteMessage: RemoteMessage){
//        val remoteMessageData = remoteMessage.data
//        val title = remoteMessageData[KEY_TITLE]
//        val body = remoteMessageData[KEY_BODY]
//        val notificationUtils = NotificationUtils(applicationContext)
//        val intent = Intent(this, SplashActivity::class.java)
//        notificationUtils.showNotificationMessage(
//            title,
//            body,
//            System.currentTimeMillis().toString(), intent
//        )
//    }
//
//    override fun onNewToken(token: String) {
//        EventBus.getDefault().post(MessageEvent(SENT_TOKEN_FCM, token))
//    }
//
//}
