package com.example.testbase.ui.main

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {


    override fun getContentLayout(): Int {
        return R.layout.activity_main
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun initView() {
        setUpNavigation()
    }

    override fun initListener() {

    }

    override fun observerLiveData() {

    }

    private fun setUpNavigation() {
        val host =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment?
        NavigationUI.setupWithNavController(binding.bottomView, host!!.navController)
    }
}