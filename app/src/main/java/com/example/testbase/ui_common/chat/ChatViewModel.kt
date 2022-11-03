package com.example.testbase.ui_common.chat

import android.util.Log
import com.example.testbase.model.ChatMessage
import com.example.testbase.base.BaseViewModel
import com.example.testbase.network.Api
import com.example.testbase.util.Const
import com.example.testbase.util.Util
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(val api: Api) : BaseViewModel() {

    private lateinit var database: DatabaseReference

    fun sendMsg(msgData: ChatMessage, idUser: String) {
        database = Firebase.database.reference
        Log.d("ptit", "sendMsg: " + idUser)
        database.child(Const.LATEST_MESSAGE).child(Firebase.auth.currentUser!!.uid).child(idUser).setValue(msgData)
        database.child(Const.LATEST_MESSAGE).child(idUser).child(Firebase.auth.uid.toString()).setValue(msgData)

        database.child(Const.CHATS).child(Util.convertTwoUserIDs(idUser, Firebase.auth.uid.toString())).child(msgData.timestamp.toString()).setValue(msgData)
    }

}