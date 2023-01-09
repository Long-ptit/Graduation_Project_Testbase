package com.example.testbase.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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
        loadingDialog = LoadingDialog.newInstance();
        observerDefaultLiveData()
        initView()
        initListener()
        observerLiveData()
    }

    private fun observerDefaultLiveData() {
        viewModel.showSnackBar.observe(this) {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }


        viewModel.showLoading.observe(this@BaseActivity) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }

        }

        viewModel.errorMessage.observe(this@BaseActivity) {
            if (it != null) {
                showError(it.toInt())
            }
        }

    }

    fun showLoading() {
//        if (!loadingDialog.isAdded) {
//            loadingDialog.show(supportFragmentManager, null)
//        }
    }

    fun hideLoading() {
//

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

    fun hideKeyboard() {
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        val view = this.currentFocus
        if (view != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    abstract fun getContentLayout(): Int

    abstract fun initViewModel()

    abstract fun initView()

    abstract fun initListener()

    abstract fun observerLiveData()


}