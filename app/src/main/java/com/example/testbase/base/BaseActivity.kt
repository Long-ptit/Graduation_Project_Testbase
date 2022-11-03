package com.example.testbase.base

import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<VM: BaseViewModel, BINDING: ViewDataBinding> : AppCompatActivity(){
    private val TAG = "ptit"
    lateinit var viewModel: VM
    lateinit var binding: BINDING
    lateinit var loadingDialog: LoadingDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getContentLayout())
        initViewModel()
        observerDefaultLiveData()
        loadingDialog = LoadingDialog.newInstance();
        initView()
        initListener()
        observerLiveData()
    }

    private fun observerDefaultLiveData() {
        viewModel.isLoading.observe(this@BaseActivity) {
            if (it) {
                if (!loadingDialog.isAdded)
                    loadingDialog.show(supportFragmentManager, null)
            } else {
                loadingDialog.dismiss()
            }

        }

        viewModel.errorMessage.observe(this@BaseActivity) {
            if (it != null) {
                showError(it.toInt())
            }
        }

    }

    protected fun showError(@StringRes id: Int) {
        var errorSnackbar = Snackbar.make(binding.root, id, Snackbar.LENGTH_LONG)
        errorSnackbar.setAction("", null)
        errorSnackbar.show()
    }

    protected fun showErrorStr(string: String) {
        Log.d(TAG, "showErrorStr: ")
        var errorSnackbar = Snackbar.make(binding.root, string, Snackbar.LENGTH_LONG)
        errorSnackbar.setAction("", null)
        errorSnackbar.show()
    }

    abstract fun getContentLayout(): Int

    abstract fun initViewModel()

    abstract fun initView()

    abstract fun initListener()

    abstract fun observerLiveData()


}