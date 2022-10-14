package com.example.testbase.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import com.example.testbase.R
import com.example.testbase.databinding.LayoutToolbarBinding

class ToolbarApp constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : RelativeLayout(context, attrs) {

    private var binding: LayoutToolbarBinding
    private var mTextTitle: String

    init {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      binding = LayoutToolbarBinding.inflate(layoutInflater, this, true)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ToolbarApp,
            0, 0).apply {

            try {
               mTextTitle = getString(R.styleable.ToolbarApp_tb_title) ?: "Screen"
                binding.tvTitle.text = mTextTitle
            } finally {
                recycle()
            }
        }


    }

    fun getBackButton() : View {
        return binding.btnBack
    }



}