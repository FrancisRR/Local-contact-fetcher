package com.francis.machinetest.dagger

import com.francis.machinetest.base.AppController
import com.francis.machinetest.base.Repo
import com.francis.machinetest.db.RoomDbController
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {


    @Provides
    @Singleton
    fun getAppControllerInstance() = AppController.instance


    @Provides
    @Singleton
    fun getRoomDbInstance() = RoomDbController.getRoomInstance()

    @Provides
    @Singleton
    fun getRepoInstance() = Repo()

}