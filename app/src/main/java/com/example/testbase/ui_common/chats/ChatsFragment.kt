package com.example.testbase.ui_common.chats

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testbase.R
import com.example.testbase.base.BaseFragment
import com.example.testbase.databinding.FragmentChatsBinding
import com.example.testbase.model.User
import com.example.testbase.ui_common.chat.ChatActivity
import com.example.testbase.util.LogUtil
import com.example.testbase.util.SharePreferenceUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChatsFragment : BaseFragment<ChatsViewModel, FragmentChatsBinding>() {

    private lateinit var database: DatabaseReference

    @Inject
    lateinit var mAdapter: ChatHomeAdapter


    override fun getContentLayout(): Int {
        return R.layout.fragment_chats
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ChatsViewModel::class.java)
        LogUtil.log(SharePreferenceUtil.getUserType(requireActivity().applicationContext))
    }

    override fun initView() {
       // showLoading()
        testFirebase()
        val layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rcv.layoutManager = layoutManager
        binding.rcv.adapter = mAdapter

    }

    private fun testFirebase() {
        database = Firebase.database.reference
        viewModel.getContactListChat()

    }

    override fun initListener() {


    }

    override fun observerLiveData() {
        viewModel._ListChat.observe(viewLifecycleOwner) {
            ///hideLoading()
            Log.d("ptit", "observerLiveData: "   + it.size)
            mAdapter.setData(it)
        }
    }


}