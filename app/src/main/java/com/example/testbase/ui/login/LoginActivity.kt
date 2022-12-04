package com.example.testbase.ui.login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.LayoutLoginBinding
import com.example.testbase.model.EStatusOrder
import com.example.testbase.model.StatusOrder
import com.example.testbase.model.Seller
import com.example.testbase.model.User
import com.example.testbase.ui.main.MainActivity
import com.example.testbase.ui.main.MainSellerActivity
import com.example.testbase.ui.sign_up.SignUpCustomerActivity
import com.example.testbase.util.Const
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.util.LogUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginViewModel, LayoutLoginBinding>() {

    private var auth: FirebaseAuth = Firebase.auth
    lateinit var adapter: TestAdapter
    override fun getContentLayout(): Int {
       return R.layout.layout_login
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun initView() {
        FirebaseUtil.changeStatusOrderTest(EStatusOrder.CANCEL, 99999) {
            LogUtil.log("successs")
        }

    }


    private fun login(email: String, password: String) {



        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //startActivity(Intent(this@LoginActivity, MainSellerActivity::class.java))
                    viewModel.checkType(auth.uid.toString())
                    FirebaseUtil.saveToken()
                    showErrorStr("Hi ${auth.currentUser?.email}")
                } else {
                    // If sign in fails, display a message to the user.
                    showErrorStr(task.exception?.message.toString())
                }
            }
    }
    override fun initListener() {
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpCustomerActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            login(
                email = binding.edtEmail.text.toString(),
                password = binding.edtPassword.text.toString()
            )
        }




    }


    override fun observerLiveData() {
        viewModel.stateTypeLogin.observe(this) {
            when (it) {
                Const.USER -> startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                Const.SELLER -> startActivity(Intent(this@LoginActivity, MainSellerActivity::class.java))
            }
        }
    }
}