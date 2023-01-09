package com.example.testbase.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object Util {
    fun convertTwoUserIDs(userID1: String, userID2: String): String {
        return if (userID1 < userID2) {
            userID2 + userID1
        } else {
            userID1 + userID2
        }
    }

    fun convertMiliToDate(time: Long) :String{
        val minutes: Long = time / 1000 / 60
        LogUtil.log("minute" + minutes.toString())
        val hour: Long = minutes/60
        LogUtil.log(hour.toString())
        val day: Long = hour/24
        var result = ""
        if (day >= 1) result = "$day ngày trước"
        else if (hour >= 1) result = "$hour tiếng trước"
        else if (minutes >= 1) result = "$minutes phút trước"
        else result = "${time / 1000} giây trước"
        return result
    }

    fun convertLongToDate(time: Long) :String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(time)
    }

    fun converCurrency(args: Double): String {

        val localeVN = Locale("vi", "VN")
        val vn: NumberFormat = NumberFormat.getInstance(localeVN)

        val str2: String = vn.format(args) + "đ"
        return str2
    }
}