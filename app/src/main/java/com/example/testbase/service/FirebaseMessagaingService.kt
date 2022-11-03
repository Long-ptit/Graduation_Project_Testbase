package com.example.testbase.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.testbase.R
import com.example.testbase.base.MyApp
import com.example.testbase.ui.main.MainActivity
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.util.LogUtil
import com.example.testbase.util.NotificationUtil
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FirebaseMessagaingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val dataMap = message.data
        val strText = dataMap["time"]
       // val notification = message.notification ?: return
//        val strTitle = notification.title
//        val strMessage = notification.body

        NotificationUtil.sendNotification(this, "dep trai", strText)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        LogUtil.log(token)
    }


}