package com.francis.machinetest.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.francis.machinetest.db.RoomDb
import javax.inject.Inject

open class BaseFragment : Fragment() {


    @set:Inject
    lateinit var roomDb: RoomDb

    @set:Inject
    lateinit var repo: Repo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppController.dagger.inject(this)
    }




}