package com.francis.machinetest.base

import android.app.Application
import com.francis.machinetest.dagger.AppComponent
import com.francis.machinetest.dagger.AppModule
import com.francis.machinetest.dagger.DaggerAppComponent

class AppController : Application() {


    companion object {
        internal lateinit var instance: AppController
        internal lateinit var dagger: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        dagger = DaggerAppComponent.builder().appModule(AppModule()).build()
    }

}