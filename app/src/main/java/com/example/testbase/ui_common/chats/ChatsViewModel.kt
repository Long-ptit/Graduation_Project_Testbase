package com.example.testbase.ui_common.chats

import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.testbase.R
import com.example.testbase.base.BaseResponse
import com.example.testbase.model.ChatMessage
import com.example.testbase.model.LastestMessage
import com.example.testbase.model.Seller
import com.example.testbase.model.User
import com.example.testbase.view_model.BaseViewModel
import com.example.testbase.network.Api
import com.example.testbase.util.Const
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(val api: Api) : BaseViewModel() {

    private val database: DatabaseReference = Firebase.database.reference

    val _ListChat = MutableLiveData<ArrayList<LastestMessage>>()

    fun getContactListChat() {
        database
            .child(Const.LATEST_MESSAGE)
            .child(Firebase.auth.uid.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listData = arrayListOf<LastestMessage>()
                    for (item in snapshot.children) {
                        val strID = item.key
                        val msg = item.getValue(ChatMessage::class.java)
                        database.child(Const.PATH_USER).child(Const.PATH_INFOR)
                            .child(strID.toString())
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val dataSeller = snapshot.getValue(Seller::class.java)
                                    if (dataSeller != null) {
                                        listData.add(
                                            LastestMessage(
                                                chatMsg = msg!!,
                                                seller = dataSeller
                                            )
                                        )
                                    }
                                    _ListChat.postValue(listData)
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }
                            })

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


    }


}