package com.francis.machinetest.ui

import android.os.Bundle
import com.francis.machinetest.R
import com.francis.machinetest.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}