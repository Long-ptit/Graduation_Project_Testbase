package com.example.testbase.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.testbase.R
import com.example.testbase.base.MyApp
import com.example.testbase.ui.main.MainActivity

object NotificationUtil {

    fun sendNotification(context: Context, strTitle: String?, strMessage: String?) {
        val intent = Intent(context, MainActivity::class.java)
        //val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, MyApp.CHANNEL_ID)
            .setContentTitle(strTitle)
            .setContentText(strMessage)
            .setSmallIcon(R.drawable.ic_logo)
//            .setContentIntent(pendingIntent)
        val notification = builder.build()
        val notiManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notiManager.notify(1, notification)

    }

}