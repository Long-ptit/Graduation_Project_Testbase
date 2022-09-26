package com.example.testbase.util

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.example.testbase.R
import com.example.testbase.ui.login.LoginActivity
import com.example.testbase.ui.main.MainActivity
import com.example.testbase.ui.sign_up.SignUpCustomerActivity

object NavigateUtil {

    fun navigateFragment(activity: FragmentActivity, id: Int) {
        val navController = Navigation.findNavController(activity, R.id.fragmentContainerView)
        navController.navigate(id)
    }

    fun navigateActivity(context: Context, act: ACT) {
        var intent: Intent? = null
        when(act) {
            ACT.MAIN -> intent = Intent(context, MainActivity::class.java)
            ACT.LOGIN -> intent = Intent(context, LoginActivity::class.java)
            ACT.SIGNUP_CUSTOMER -> intent = Intent(context, SignUpCustomerActivity::class.java)
            else -> {}
        }
        context.startActivity(intent)
    }

    enum class ACT{
        MAIN, LOGIN, SIGNUP_CUSTOMER
    }
}