package com.example.testbase.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.testbase.R

class LoadingDialog : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_loading, container, false)

        isCancelable = false
        return view;
    }

    companion object {
        fun newInstance() =
            LoadingDialog().apply {
                arguments = Bundle().apply {

                }
            }
    }

}