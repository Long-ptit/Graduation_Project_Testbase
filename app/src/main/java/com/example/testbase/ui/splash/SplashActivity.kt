package com.example.testbase.ui.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivitySplashBinding
import com.example.testbase.ui.detail_product.DetailProductActivity
import com.example.testbase.ui.login.LoginActivity
import com.example.testbase.ui.login.LoginViewModel
import com.example.testbase.ui.main.MainActivity
import com.example.testbase.ui.main.MainAdminActivity
import com.example.testbase.ui.main.MainSellerActivity
import com.example.testbase.util.Const
import com.google.firebase.auth.ktx.auth
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        mHandler = Handler(Looper.getMainLooper())
        mHandler.postDelayed({
            // checkUserRole()
            checkDeeplink()
        }, 1000);
    }

    override fun initListener() {


    }

    override fun observerLiveData() {
        viewModel.stateUserInfor.observe(this) {
            when (it.userType) {
                "USER" -> startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                "ADMIN" -> startActivity(Intent(this@SplashActivity, MainAdminActivity::class.java))
                "SELLER" -> startActivity(
                    Intent(
                        this@SplashActivity,
                        MainSellerActivity::class.java
                    )
                )
            }
            finishAffinity()

        }
    }

    fun checkUser() {
        val currentUSer = auth.currentUser
        if (currentUSer != null) {
            viewModel.getUserInfor()
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
                        intent.putExtra(
                            Const.PRODUCT_ID,
                            deepLink.getQueryParameter(Const.PRODUCT_ID)!!.toInt()
                        )
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