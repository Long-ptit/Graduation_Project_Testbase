package com.example.testbase.util

import java.text.NumberFormat
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
        if (minutes > 0) return "$minutes phút trước"
        return "$time giây trước"
    }

    fun converCurrency(args: Double): String {

        val localeVN = Locale("vi", "VN")
        val vn: NumberFormat = NumberFormat.getInstance(localeVN)

        val str2: String = vn.format(args)
        return str2
    }
}