package com.example.testbase.util

import com.example.testbase.model.EStatusOrder
import com.example.testbase.model.Seller
import com.example.testbase.model.StatusOrder
import com.example.testbase.model.SendNotiFcmRequest
import com.example.testbase.model_firebase.NotificationSend
import com.example.testbase.model_response.SendNotifiFcmResponse
import com.example.testbase.service.ApiFcm
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object FirebaseUtil {
    private val mFirebaseAuth = Firebase.auth
    private val mDatabase = Firebase.database.reference
//
//    @Inject
//    lateinit var apiFcm: ApiFcm

    fun getUid(): String {
        return mFirebaseAuth.uid.toString()
    }

    fun saveToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            val token = it.result
            mDatabase.child(Const.PATH_TOKEN_FCM).child(getUid()).setValue(token)
        }
    }

    fun deleteToken() {
        mDatabase
            .child(Const.PATH_TOKEN_FCM)
            .child(getUid())
            .setValue(null)
    }

    fun changeStatusOrder(idOrder: Int, status: String, callBackSuccess: () -> Unit) {
        mDatabase
            .child(Const.PATH_STATUS_ORDER)
            .child(idOrder.toString())
            .setValue(status)
            .addOnCompleteListener {
                callBackSuccess.invoke()
            }

    }

    fun changeStatusOrderTest(enumStatus: EStatusOrder, idOrder: Int, callBackSuccess: () -> Unit) {
        mDatabase
            .child(Const.PATH_STATUS_ORDER)
            .child(idOrder.toString())
            .setValue(StatusOrder(enumStatus.status, enumStatus.id))
            .addOnCompleteListener {
                callBackSuccess.invoke()
            }

    }

    fun getStatusOrder(idOrder: Int, callBackSuccess: (status: StatusOrder?) -> Unit) {
        mDatabase
            .child(Const.PATH_STATUS_ORDER)
            .child(idOrder.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value == null) return
                    val dataStatusOrder = snapshot.getValue(StatusOrder::class.java)
                    LogUtil.log("GET STATUS ORDER ${dataStatusOrder}")
                    callBackSuccess.invoke(dataStatusOrder)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    fun sendNotification(idTarget: String, data: NotificationSend, apiFcm: ApiFcm) {
        mDatabase.child(Const.PATH_TOKEN_FCM).child(idTarget).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    apiFcm.sendNotification( SendNotiFcmRequest(
                        data,
                        snapshot.value.toString()
                    )).enqueue(object : Callback<SendNotifiFcmResponse?> {
                        override fun onResponse(
                            call: Call<SendNotifiFcmResponse?>,
                            response: Response<SendNotifiFcmResponse?>
                        ) {
                            LogUtil.log("Send Notification Success!")
                        }

                        override fun onFailure(call: Call<SendNotifiFcmResponse?>, t: Throwable) {

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