package com.example.testbase.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivitySplashBinding
import com.example.testbase.ui.detail_product.DetailProductActivity
import com.example.testbase.ui.login.LoginActivity
import com.example.testbase.ui.login.LoginViewModel
import com.example.testbase.ui.main.MainActivity
import com.example.testbase.ui.main.MainSellerActivity
import com.example.testbase.util.Const
import com.example.testbase.util.LogUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<LoginViewModel, ActivitySplashBinding>() {

    private lateinit var mHandler: Handler
    private val auth = Firebase.auth


    override fun getContentLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun initView() {

        mHandler = Handler(Looper.getMainLooper())
        mHandler.postDelayed({
            checkUser()
        }, 1000);
    }

    override fun initListener() {


    }

    override fun observerLiveData() {
    }

    fun checkUser() {
        val currentUSer = auth.currentUser
        if (currentUSer != null) {
            val listener =  Firebase.database.reference
                .child(Const.PATH_ACCOUNT)
                .child(Const.PATH_TYPE)
                .child(currentUSer.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        LogUtil.log("Co data")
                        when (snapshot.getValue(String::class.java)) {
                            Const.USER -> startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                            Const.SELLER -> startActivity(Intent(this@SplashActivity, MainSellerActivity::class.java))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
        } else {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
    }

    fun checkDeeplink() {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                // Get deep link from result (may be null if no link is found)
                if (pendingDynamicLinkData != null) {
                    val deepLink = pendingDynamicLinkData.link
                    Log.d("DEEP_LINK", "value: $deepLink")

                    if (deepLink?.getQueryParameter(Const.PRODUCT_ID) != null) {
                        val intent = Intent(this, DetailProductActivity::class.java)
                        intent.putExtra(Const.PRODUCT_ID, deepLink.getQueryParameter(Const.PRODUCT_ID)!!.toInt())
                        startActivity(intent)
                        finish()
                    }

                } else {
                    checkUser()
                }

            }
            .addOnFailureListener(this) { e ->
                Log.w(
                    "FAIL_DEEP_LINK",
                    "getDynamicLink:onFailure ",
                    e
                )
            }
    }

}