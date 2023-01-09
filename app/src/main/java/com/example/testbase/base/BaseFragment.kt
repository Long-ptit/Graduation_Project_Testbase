package com.example.testbase.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VM: BaseViewModel, BINDING: ViewDataBinding> : Fragment() {

    lateinit var viewModel: VM

    lateinit var binding: BINDING
    lateinit var loadingDialog: LoadingDialog;

    override fun onStart() {
        super.onStart()
        viewModel.showLoading.observe(this) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog.newInstance();
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, getContentLayout(), container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
        initListener()
        observerLiveData()
    }

    fun showLoading() {
        activity?.let { loadingDialog.show(it.supportFragmentManager, null) }
    }

    fun hideLoading() {
        loadingDialog.dismiss()
    }

    fun hideKeyboard() {
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        val view = requireActivity().currentFocus
        if (view != null) {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }



    abstract fun getContentLayout(): Int

    abstract fun initViewModel()

    abstract fun initView()

    abstract fun initListener()

    abstract fun observerLiveData()

}