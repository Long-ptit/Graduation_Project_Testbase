package com.example.testbase.util

import android.content.Context
import com.example.testbase.model.ShippingInformation
import dagger.hilt.android.qualifiers.ApplicationContext

object SharePreferenceUtil {

    const val NAME = "LongTX"
    const val USER_TYPE = "TYPE"
    const val ADDRESS_TYPE = "ADDRESS_TYPE"

    fun addUserType(type: String, context: Context) {
        val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString(USER_TYPE,type)
        editor.commit()
    }

    fun getUserType(context: Context): String {
        val sharedPreference = context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        return sharedPreference.getString(USER_TYPE, "").toString()
    }

    fun addAdress(idShip: Int, context: Context) {
        val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putInt(ADDRESS_TYPE,idShip)
        editor.commit()
    }

    fun getAddress(context: Context): Int {
        val sharedPreference = context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        return sharedPreference.getInt(ADDRESS_TYPE, -1)
    }

}