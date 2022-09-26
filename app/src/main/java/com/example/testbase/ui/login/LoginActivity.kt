package com.example.testbase.ui.login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.LayoutLoginBinding
import com.example.testbase.ui.sign_up.SignUpCustomerActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : BaseActivity<LoginViewModel, LayoutLoginBinding>() {

    private lateinit var auth: FirebaseAuth
    lateinit var adapter: TestAdapter
    override fun getContentLayout(): Int {
       return R.layout.layout_login
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun initView() {
        val listData = mutableListOf("1", "2", "3")
        adapter = TestAdapter(listData)
        auth = Firebase.auth

        login()



    }


    private fun login() {
        auth.signInWithEmailAndPassword("haha123@gmail.com", "qwertyu")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("ptit", "signInWithEmail:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("ptit", "signInWithEmail:failure", task.exception)
                }
            }
    }
    override fun initListener() {
        binding.tvSignUp.setOnClickListener {
            Log.d("ptit", "kakaka")
//            val currentUser = auth.currentUser
//            if(currentUser != null){
//                showError(R.string.app_name)
//            }
            val intent = Intent(this@LoginActivity, SignUpCustomerActivity::class.java)
            startActivity(intent)
            //viewModel.callApi()

        }



    }


    override fun observerLiveData() {
        viewModel.test.observe(this@LoginActivity, {
            Log.d("ptit", "observerLiveData: " + it)
        })
    }
}