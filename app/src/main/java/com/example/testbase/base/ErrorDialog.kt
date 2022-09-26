package com.example.testbase.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.testbase.R
import com.example.testbase.databinding.LayoutErrorBinding

class ErrorDialog : DialogFragment() {

    lateinit var binding: LayoutErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.layout_error, container, false);
        if (this.dialog!!.window != null) {
            this.dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            this.dialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
       // setSizeForDialog(90)
    }

    private fun setSizeForDialog(width: Int) {
        if (dialog!!.window != null) {
            dialog!!.window!!.setLayout(
                requireActivity().getWindow().getDecorView().getWidth() * width / 10,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    companion object {
        fun newInstance() =
            ErrorDialog().apply {
                arguments = Bundle().apply {

                }
            }
    }
}