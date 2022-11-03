package com.example.testbase.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.testbase.R
import com.example.testbase.databinding.LayoutButtonBinding
import com.example.testbase.util.LogUtil

class ButtonApp constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {

    private var binding: LayoutButtonBinding
    private var mTextTitle: String

    init {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = LayoutButtonBinding.inflate(layoutInflater, this, true)
        isClickable = true
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ButtonApp,
            0, 0
        ).apply {

            try {
                mTextTitle = getString(R.styleable.ButtonApp_btn_title) ?: "Hello"
                binding.btnLogin.text = mTextTitle
            } finally {
                recycle()
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        LogUtil.log("Touch")
        return super.dispatchTouchEvent(ev)
    }



}