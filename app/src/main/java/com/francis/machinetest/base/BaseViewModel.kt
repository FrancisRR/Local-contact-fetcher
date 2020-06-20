package com.francis.machinetest.base

import androidx.lifecycle.ViewModel
import com.francis.machinetest.db.RoomDb
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    private val TAG by lazy { BaseViewModel::class.java.simpleName }

    @set:Inject
    internal lateinit var repo: Repo

    @set:Inject
    lateinit var roomDb: RoomDb

    init {
        AppController.dagger.inject(this)
    }


}