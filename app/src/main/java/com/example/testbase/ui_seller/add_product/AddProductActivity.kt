package com.example.testbase.ui_seller.add_product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testbase.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
    }
}