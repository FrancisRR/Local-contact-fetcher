package com.francis.machinetest.base

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.francis.machinetest.db.RoomDb
import javax.inject.Inject

abstract class BaseWorker(val appContext: Context, val workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    @set:Inject
    lateinit var roomDb: RoomDb

    @set:Inject
    lateinit var repo: Repo

    init {
        AppController.dagger.inject(this)
    }

}