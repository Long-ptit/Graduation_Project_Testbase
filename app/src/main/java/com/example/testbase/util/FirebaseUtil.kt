package com.example.testbase.util

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

object FirebaseUtil {
    private val mFirebaseAuth = Firebase.auth
    private val mDatabase = Firebase.database.reference

    fun getUid(): String {
        return mFirebaseAuth.uid.toString()
    }

    fun saveToken() {
        mDatabase.child(Const.PATH_TOKEN_FCM).child(getUid()).setValue(FirebaseMessaging.getInstance().token)
    }
}