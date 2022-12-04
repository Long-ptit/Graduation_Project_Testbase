package com.example.testbase.ui_common.chat

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivityChatBinding
import com.example.testbase.model.ChatMessage
import com.example.testbase.util.Const
import com.example.testbase.util.Util
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@AndroidEntryPoint
class ChatActivity : BaseActivity<ChatViewModel, ActivityChatBinding>() {

    private lateinit var database: DatabaseReference
    private lateinit var idUser: String

    @Inject
    lateinit var mAdapter: ChatsAdapter

    override fun getContentLayout(): Int {
        return R.layout.activity_chat
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
    }

    override fun initView() {
        idUser = intent.getStringExtra(Const.USER_ID).toString()
        val layoutManager = LinearLayoutManager(
            this@ChatActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rcv.layoutManager = layoutManager
        binding.rcv.adapter = mAdapter

        database = Firebase.database.reference
        Log.d("ptit", "initView: " + Util.convertTwoUserIDs(idUser, Firebase.auth.uid.toString()))
        database.child(Const.CHATS).child(Util.convertTwoUserIDs(idUser, Firebase.auth.uid.toString())).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listData = ArrayList<ChatMessage>()
                for (dataSnapshot in snapshot.children) {
                    val wishlistAdd = dataSnapshot.getValue(ChatMessage::class.java)
                    wishlistAdd?.let { listData.add(it) }
                }
                mAdapter.setData(listData)
                binding.rcv.scrollToPosition(listData.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun initListener() {
        binding.btnSend.setOnClickListener {
            val dataChat = ChatMessage(
                timestamp = System.currentTimeMillis(),
                fromID = Firebase.auth.uid.toString(),
                toID = idUser,
                text = binding.edtContent.text.toString()
            )
            viewModel.sendMsg(dataChat, idUser)
            it.hideKeyboard()
        }
    }

    override fun observerLiveData() {
        binding.toolbar.getBackButton().setOnClickListener {
            finish()
        }
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}